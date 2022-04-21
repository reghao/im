package cn.reghao.im.ws;

import cn.reghao.im.db.mapper.UserContactMapper;
import cn.reghao.im.db.mapper.UserProfileMapper;
import cn.reghao.im.model.constant.EventType;
import cn.reghao.im.model.ws.resp.EventMessageResp;
import cn.reghao.im.model.ws.resp.EvtLoginResp;
import cn.reghao.im.util.Jwt;
import cn.reghao.jutil.jdk.serializer.JsonConverter;
import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author reghao
 * @date 2022-04-21 10:32:37
 */
@Slf4j
@Component
public class EventDispatcher {
    private final Map<Long, WebSocketSession> sessionMap = new HashMap<>();
    private final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    private final UserProfileMapper userProfileMapper;
    private final UserContactMapper userContactMapper;

    public EventDispatcher(UserProfileMapper userProfileMapper, UserContactMapper userContactMapper) {
        this.userProfileMapper = userProfileMapper;
        this.userContactMapper = userContactMapper;
    }

    public void online(WebSocketSession session) throws IOException {
        long userId = getUserId(session);
        loginEvent(userId, true);
        sessionMap.put(userId, session);
    }

    public void offline(WebSocketSession session) throws IOException {
        long userId = getUserId(session);
        loginEvent(userId, false);
        sessionMap.remove(userId, session);
    }

    private long getUserId(WebSocketSession webSocketSession) {
        String query = webSocketSession.getUri().getQuery();
        String token = query.replace("token=", "");
        return Long.parseLong(Jwt.parse(token).getUserId());
    }

    private void loginEvent(long userId, boolean online) throws IOException {
        userProfileMapper.updateSetStatus(userId, online);

        EvtLoginResp evtLoginResp = new EvtLoginResp(userId, online);
        EventMessageResp<EvtLoginResp> resp = new EventMessageResp<>(EventType.event_login, evtLoginResp);
        TextMessage textMessage = new TextMessage(gson.toJson(resp));
        List<Long> friendIds = userContactMapper.getOnlineFriends(userId);
        for (Long friendId : friendIds) {
            WebSocketSession session = sessionMap.get(friendId);
            if (session != null) {
                session.sendMessage(textMessage);
            }
        }
    }

    public void dispatch(WebSocketSession session, String payload) throws IOException {
        JsonObject jsonObject = JsonConverter.jsonToJsonElement(payload).getAsJsonObject();
        String event = jsonObject.get("event").getAsString();
        switch (EventType.valueOf(event)) {
            case heartbeat:
                log.info("heartbeat event");
                EventMessageResp<String> resp = new EventMessageResp<>(EventType.heartbeat, "pong");
                sendMessage(session, resp);
                break;
            case event_contact_apply:
                log.info("contact_apply event");
                break;
            case event_talk_keyboard:
                JsonObject data = jsonObject.get("data").getAsJsonObject();
                data.get("sender_id").getAsLong();
                data.get("receiver_id").getAsLong();
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

    public void sendMessage(long userId, Object payload) throws IOException {
        WebSocketSession session = sessionMap.get(userId);
        if (session != null) {
            TextMessage textMessage = new TextMessage(gson.toJson(payload));
            session.sendMessage(textMessage);
        }
    }

    public void sendMessage(WebSocketSession session, Object payload) throws IOException {
        TextMessage textMessage = new TextMessage(gson.toJson(payload));
        session.sendMessage(textMessage);
    }
}
