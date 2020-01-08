package xurong.marinemode.community.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {
    public static void responseRemoveCookie(HttpServletResponse response, String cookieKey) {
        Cookie cookieWillBeRemove = new Cookie(cookieKey, null);
        cookieWillBeRemove.setPath("/");
        cookieWillBeRemove.setMaxAge(0);
        response.addCookie(cookieWillBeRemove);
    }
}
