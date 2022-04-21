package cn.reghao.im.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;

/**
 * @author reghao
 * @date 2022-03-11 16:45:52
 */
@Slf4j
@Component
public class WebSocketHandlerImpl implements WebSocketHandler {
    private final EventDispatcher eventDispatcher;

    public WebSocketHandlerImpl(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws IOException {
        eventDispatcher.online(webSocketSession);
        log.info("WebSocket 建立连接");
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage)  {
        if (webSocketMessage instanceof TextMessage) {
            String payload = (String) webSocketMessage.getPayload();
            try {
                eventDispatcher.dispatch(webSocketSession, payload);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
        log.error("WebSocket 数据传输错误");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws IOException {
        eventDispatcher.offline(webSocketSession);
        log.info("WebSocket 断开连接");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
