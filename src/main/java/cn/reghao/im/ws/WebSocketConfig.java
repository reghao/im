package cn.reghao.im.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author reghao
 * @date 2022-03-11 16:43:25
 */
@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;
    private final WebSocketInterceptor webSocketInterceptor;

    public WebSocketConfig(WebSocketHandler webSocketHandler, WebSocketInterceptor webSocketInterceptor) {
        this.webSocketHandler = webSocketHandler;
        this.webSocketInterceptor = webSocketInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        String path = "/wss/default.io";
        registry.addHandler(webSocketHandler, path)
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("*");
    }
}
