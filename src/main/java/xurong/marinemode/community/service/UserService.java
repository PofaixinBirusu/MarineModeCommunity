package xurong.marinemode.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xurong.marinemode.community.mapper.UserMapper;
import xurong.marinemode.community.model.User;
import xurong.marinemode.community.utils.PlatformKey;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    @Autowired(required=false)
    private UserMapper userDao;
    public String userLogin(User user) {
        String token = UUID.randomUUID()
                .toString().replaceAll("-", "").substring(0, 30);
        /* 如果数据库里没有这个人, 就插入数据库
           如果有这个人, 就把token改掉
           那么问题来了, 如果第三方平台有人名字和本平台的重了怎么办?
           那岂不是把别人的token改掉了, 然后从第三方来的这个人用别人的账号登录了
           这个username重复的问题要想办法解决
           一个办法就是, user里面存平台信息, 如github, qq, native
           这样可以解决查得时候第三方平台不会和本地用户重复
           但是存的时候username重复了又怎么办                             */
        User userFindByAccountAndPlatform =
                userDao.findUserByAccountIdAndPlatform(user.getAccountId(), user.getPlatform());
        if (userFindByAccountAndPlatform == null) {
            if (!user.getPlatform().equals(PlatformKey.Native)) {
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setToken(token);
                int insertSuccess = userDao.insertUser(user);
                if (insertSuccess == 1) {
                    return token;
                }
            }
        } else {
            int state = userDao.updateTokenByUserId(userFindByAccountAndPlatform.getId(), token);
            if (state == 1) {
                return token;
            }
        }
        return null;
    }
    public Map<String, Integer> userLogout(int userId) {
        int state = userDao.updateTokenByUserId(userId, "");
        Map<String, Integer> logoutResult = new HashMap<>();
        logoutResult.put("state", state);
        return logoutResult;
    }
}
