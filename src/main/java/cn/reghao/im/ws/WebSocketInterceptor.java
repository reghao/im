package cn.reghao.im.ws;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author reghao
 * @date 2022-03-11 16:42:44
 */
@Component
public class WebSocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // TODO 使用经过 HTTP 认证的用户信息
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest httpRequest = (ServletServerHttpRequest) request;
            return true;
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, @Nullable Exception exception) {
    }
}
