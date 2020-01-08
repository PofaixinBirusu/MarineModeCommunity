package xurong.marinemode.community.utils;

import org.springframework.stereotype.Component;
import xurong.marinemode.community.model.User;

@Component
public class UserHolder {
    private static final ThreadLocal<User> hoster = new ThreadLocal<User>();
    public void setUser(User user) {
        hoster.set(user);
    }
    public User get() {
        return hoster.get();
    }
    public void clear() {
        hoster.remove();
    }
}
