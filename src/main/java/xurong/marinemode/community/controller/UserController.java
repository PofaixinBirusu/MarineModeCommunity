package xurong.marinemode.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xurong.marinemode.community.model.User;
import xurong.marinemode.community.service.UserService;
import xurong.marinemode.community.utils.CookieHelper;
import xurong.marinemode.community.utils.KeyHelper;
import xurong.marinemode.community.utils.UserHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired(required=false)
    private UserHolder userHolder;
    @Autowired(required=false)
    private UserService userService;
    @GetMapping("/logout")
    String logout(HttpServletRequest request, HttpServletResponse response) {
        User user = userHolder.get();
        if (user != null) {
            userService.userLogout(user.getId());
            request.getSession().setAttribute(KeyHelper.LoginUserKey, null);
            userHolder.clear();
            /* 把cookie删掉 */
            CookieHelper.responseRemoveCookie(response, KeyHelper.TOKEN_KEY);
        }
        return "redirect:/index";
    }
    @GetMapping("/get")
    @ResponseBody
    public Object getUser() {
        return userHolder.get();
    }
}
