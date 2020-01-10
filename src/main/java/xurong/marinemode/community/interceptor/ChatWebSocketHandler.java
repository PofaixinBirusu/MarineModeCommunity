package xurong.marinemode.community.interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import xurong.marinemode.community.dto.WebSocketMsgDTO;
import xurong.marinemode.community.model.User;
import xurong.marinemode.community.utils.KeyHelper;

import java.io.IOException;
import java.util.*;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {
    //private List<User> userList = new ArrayList<>();
    private Map<String, WebSocketSession> userToWebSocketSessionMap = new HashMap<>();

    private void sendToAll(final String msg) {
        for (Map.Entry<String, WebSocketSession> entry: userToWebSocketSessionMap.entrySet()) {
            final WebSocketSession session = entry.getValue();
            if (session.isOpen()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            session.sendMessage(new TextMessage(msg.getBytes()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }
    /* 连接成功 */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        User user = (User)webSocketSession.getAttributes().get(KeyHelper.LoginUserKey);
        //userList.add(user);
        /* 用户就用id做key, 游客就拿随机出来的用户名做key, 反正游客没地位 */
        String key = (user.getId() != -1 ? Integer.toString(user.getId()) : user.getUsername());
        userToWebSocketSessionMap.put(key , webSocketSession);
        if (user.getId() != -1) {
            User notifyUser = new User();
            notifyUser.setId(0);
            notifyUser.setUsername("系统通知");
            sendToAll(new WebSocketMsgDTO(notifyUser, user.getUsername()+" 他来了").toString());
        }
    }
    /* 客户端发的消息从这里接 */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> msg) throws Exception {
        User user = (User)webSocketSession.getAttributes().get(KeyHelper.LoginUserKey);
        /* 游客的消息不做处理 */
        if (user == null || user.getId() == -1 || msg == null) {
            return;
        }
        String userSpeakContent = msg.getPayload().toString();
        /* 消息没内容 */
        if (msg.getPayloadLength() == 0 || StringUtils.isBlank(userSpeakContent)) {
            return;
        }
        /* 把这位用户说的话广播给各位 */
        sendToAll(new WebSocketMsgDTO(user, userSpeakContent).toString());
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        User user = (User)webSocketSession.getAttributes().get(KeyHelper.LoginUserKey);
        System.out.println(user.getUsername()+"出异常了");
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        if (user.getId() == -1) {
            userToWebSocketSessionMap.remove(user.getUsername());
        } else {
            userToWebSocketSessionMap.remove(Integer.toString(user.getId()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        User user = (User)webSocketSession.getAttributes().get(KeyHelper.LoginUserKey);
        System.out.println(user.getUsername()+"走了");
        if (user.getId() == -1) {
            userToWebSocketSessionMap.remove(user.getUsername());
        } else {
            userToWebSocketSessionMap.remove(Integer.toString(user.getId()));
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
