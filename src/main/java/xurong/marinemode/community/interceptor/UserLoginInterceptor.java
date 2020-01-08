package xurong.marinemode.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xurong.marinemode.community.mapper.CommentMapper;
import xurong.marinemode.community.mapper.UserMapper;
import xurong.marinemode.community.model.Comment;
import xurong.marinemode.community.model.User;
import xurong.marinemode.community.utils.CookieHelper;
import xurong.marinemode.community.utils.KeyHelper;
import xurong.marinemode.community.utils.UserHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserLoginInterceptor implements HandlerInterceptor {
    @Autowired(required=false)
    private UserMapper userDao;
    @Autowired
    private UserHolder userHolder;
    @Autowired(required=false)
    private CommentMapper commentDao;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        User user = (User)request.getSession().getAttribute(KeyHelper.LoginUserKey);
        if (user == null) {
            Cookie []cookies = request.getCookies();
            if (cookies == null || cookies.length == 0)
                return true;
            for (Cookie cookie: cookies)
                if (cookie.getName().equals(KeyHelper.TOKEN_KEY)) {
                    User userByToken = userDao.findUserByToken(cookie.getValue());
                    if (userByToken != null) {
                        request.getSession().setAttribute(KeyHelper.LoginUserKey, userByToken);
                        userHolder.setUser(userByToken);
                        break;
                    }
                }
        } else if (userHolder.get() == null) {
            userHolder.setUser(user);
        }
        if (userHolder.get() != null) {
            request.getSession().setAttribute(KeyHelper.NotReadNotificationCount, commentDao.notReadCount(userHolder.get().getId()));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        userHolder.clear();
    }
}
