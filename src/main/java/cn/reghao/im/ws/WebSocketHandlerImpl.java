package cn.reghao.im.ws;

import cn.reghao.im.db.mapper.UserContactMapper;
import cn.reghao.im.db.mapper.UserProfileMapper;
import cn.reghao.im.model.ws.EventContent;
import cn.reghao.im.model.ws.EventMessage;
import cn.reghao.im.model.ws.LoginEvent;
import cn.reghao.jutil.jdk.serializer.JsonConverter;
import cn.reghao.im.util.Jwt;
import cn.reghao.im.model.constant.EventType;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author reghao
 * @date 2022-03-11 16:45:52
 */
@Slf4j
@Component
public class WebSocketHandlerImpl implements WebSocketHandler {
    private final Map<Long, WebSocketSession> sessionMap = new HashMap<>();
    private final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    private final UserProfileMapper userProfileMapper;
    private final UserContactMapper userContactMapper;

    public WebSocketHandlerImpl(UserProfileMapper userProfileMapper, UserContactMapper userContactMapper) {
        this.userProfileMapper = userProfileMapper;
        this.userContactMapper = userContactMapper;
    }

    public void sendMessage(long userId, Object payload) throws IOException {
        WebSocketSession session = sessionMap.get(userId);
        if (session != null) {
            TextMessage textMessage = new TextMessage(gson.toJson(payload));
            session.sendMessage(textMessage);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws IOException {
        long userId = getUserId(webSocketSession);
        sessionMap.put(userId, webSocketSession);
        loginEvent(userId, true);
        log.info("WebSocket 建立连接");
    }

    private long getUserId(WebSocketSession webSocketSession) {
        String query = webSocketSession.getUri().getQuery();
        String token = query.replace("token=", "");
        return Long.parseLong(Jwt.parse(token).getUserId());
    }

    private void loginEvent(long userId, boolean online) throws IOException {
        userProfileMapper.updateSetStatus(userId, online);

        LoginEvent loginEvent = new LoginEvent(userId, online);
        EventMessage<LoginEvent> eventMessage = new EventMessage<>(EventType.event_login, loginEvent);
        TextMessage textMessage = new TextMessage(gson.toJson(eventMessage));
        List<Long> friendIds = userContactMapper.getOnlineFriends(userId);
        for (Long friendId : friendIds) {
            WebSocketSession session = sessionMap.get(friendId);
            if (session != null) {
                session.sendMessage(textMessage);
            }
        }
    }
    
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws IOException {
        if (webSocketMessage instanceof TextMessage) {
            String payload = (String) webSocketMessage.getPayload();
            dispatchEvent(webSocketSession, payload);
        } else if (webSocketMessage instanceof BinaryMessage) {
            log.info("WebSocket 二进制消息: {}", webSocketMessage.getPayload());
        } else if (webSocketMessage instanceof PingMessage) {
            log.info("WebSocket PingMessage: {}", webSocketMessage.getPayload());
        } else if (webSocketMessage instanceof PongMessage) {
            log.info("WebSocket PongMessage: {}", webSocketMessage.getPayload());
        } else {
            log.error("未知类型的 WebSocket 消息");
        }
    }

    private void dispatchEvent(WebSocketSession session, String payload) throws IOException {
        JsonObject jsonObject = JsonConverter.jsonToJsonElement(payload).getAsJsonObject();
        String event = jsonObject.get("event").getAsString();
        String content = jsonObject.get("content").getAsString();
        switch (EventType.valueOf(event)) {
            case heartbeat:
                log.info("heartbeat event");
                break;
            case event_login:
                log.info("login event");
                break;
            case event_contact_apply:
                log.info("contact_apply event");
                break;
            case event_talk_keyboard:
                log.info("talk_keyboard event");
                long senderId = jsonObject.get("data").getAsJsonObject().get("sender_id").getAsLong();
                long receiverId = jsonObject.get("data").getAsJsonObject().get("receiver_id").getAsLong();
                break;
            case event_talk:
                log.info("talk event");
                break;
            case event_talk_revoke:
                log.info("talk_revoke event");
                break;
            case event_talk_join_group:
                log.info("talk_join_group event");
                break;
            case event_error:
                log.info("error event");
                break;
            default:
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
        log.error("WebSocket 数据传输错误");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws IOException {
        long userId = getUserId(webSocketSession);
        sessionMap.remove(userId);
        loginEvent(userId, false);
        log.info("WebSocket 断开连接");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
