package xurong.marinemode.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Component("webSocketConfig")
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    /* 处理器, 处理消息的 */
    @Autowired(required=false)
    private ChatWebSocketHandler chatHandler;
    /* 拦截器对象, 是对握手动作进行增强的, 这里也就简单用来调试一下 */
    @Autowired
    private ChatHandshakeInterceptor chatHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "liveroom/1")
                .addInterceptors(chatHandshakeInterceptor);
    }
}
