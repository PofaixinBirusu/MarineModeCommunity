package xurong.marinemode.community.dto;

import com.alibaba.fastjson.JSON;
import xurong.marinemode.community.model.User;

public class WebSocketMsgDTO {
    private User user;
    private String content;

    public WebSocketMsgDTO() {}
    public WebSocketMsgDTO(User user, String content) {
        this.user = user;
        this.content = content;
    }

    public String toString() {
        return JSON.toJSONString(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
