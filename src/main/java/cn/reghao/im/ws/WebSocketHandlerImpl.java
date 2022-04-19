package cn.reghao.im.ws;

import cn.reghao.im.model.ws.LoginEvtMsg;
import cn.reghao.jutil.jdk.serializer.JsonConverter;
import cn.reghao.im.util.Jwt;
import cn.reghao.im.ws.msg.ImEvent;
import cn.reghao.im.ws.msg.TalkKeyboard;
import cn.reghao.im.ws.msg.WsMsg;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author reghao
 * @date 2022-03-11 16:45:52
 */
@Slf4j
@Component
public class WebSocketHandlerImpl implements WebSocketHandler {
    private final Map<String, WebSocketSession> map = new HashMap<>();
    private final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    public void sendMessage(long userId, Object payload) throws IOException {
        WebSocketSession session = map.get(String.valueOf(userId));
        TextMessage textMessage = new TextMessage(gson.toJson(payload));
        session.sendMessage(textMessage);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws IOException {
        String query = webSocketSession.getUri().getQuery();
        String token = query.replace("token=", "");
        String userId = Jwt.parse(token).getUserId();
        map.put(userId, webSocketSession);
        loginEvent(Long.parseLong(userId), webSocketSession);
        log.info("WebSocket 建立连接");
    }

    private void loginEvent(long userId, WebSocketSession webSocketSession) throws IOException {
        LoginEvtMsg loginEvtMsg = new LoginEvtMsg(userId, true);
        WsMsg<LoginEvtMsg> wsMsg = new WsMsg<>(ImEvent.event_login, loginEvtMsg);
        TextMessage textMessage = new TextMessage(gson.toJson(wsMsg));
        webSocketSession.sendMessage(textMessage);
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
        switch (ImEvent.valueOf(event)) {
            case heartbeat:
                log.info("heartbeat event");
                WsMsg<String> wsMsg = new WsMsg<>(ImEvent.heartbeat, "");
                TextMessage textMessage = new TextMessage(gson.toJson(wsMsg));
                session.sendMessage(textMessage);
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
                TalkKeyboard talkKeyboard = new TalkKeyboard(senderId, receiverId);

                WsMsg<TalkKeyboard> wsMsg1 = new WsMsg<>(ImEvent.event_talk_keyboard, talkKeyboard);
                TextMessage textMessage1 = new TextMessage(gson.toJson(wsMsg1));
                session.sendMessage(textMessage1);
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
        String wsSessionId = webSocketSession.getId();
        map.remove(wsSessionId);
        log.error("WebSocket 数据传输错误");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        String wsSessionId = webSocketSession.getId();
        map.remove(wsSessionId);
        log.info("WebSocket 断开连接");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
