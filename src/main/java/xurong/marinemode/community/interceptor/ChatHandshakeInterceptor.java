package xurong.marinemode.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import xurong.marinemode.community.model.User;
import xurong.marinemode.community.utils.KeyHelper;
import xurong.marinemode.community.utils.UserHolder;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;

@Component
public class ChatHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler webSocketHandler,
                                   Map<String, Object> wenSocketSession) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            HttpSession session = ((ServletServerHttpRequest)request).getServletRequest().getSession();
            User user = (User)session.getAttribute(KeyHelper.LoginUserKey);
            if (user != null) {
                //System.out.println(user.getUsername()+" 来了");
            } else {
                /* 游客id为-1 */
                user = new User();
                user.setId(-1);
                user.setUsername("游客"+UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6));
                //System.out.println("一名游客来了");
            }
            wenSocketSession.put(KeyHelper.LoginUserKey, user);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
    }
}
