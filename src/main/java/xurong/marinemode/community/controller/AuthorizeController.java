package xurong.marinemode.community.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xurong.marinemode.community.dto.GitHubUser;
import xurong.marinemode.community.mapper.UserMapper;
import xurong.marinemode.community.service.UserService;
import xurong.marinemode.community.utils.GitHubVisitor;
import xurong.marinemode.community.utils.KeyHelper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GitHubVisitor githubVisitor;
    @Autowired
    private UserService userService;
    /* 授权登录的callback函数 */
    @GetMapping("/callback")
    public String codeCallBack(@RequestParam(value="code", required=true) String code,
                        HttpServletRequest request, HttpServletResponse response) {
        String accessToken = githubVisitor.getAccessToken(code);
        GitHubUser githubUser = githubVisitor.getGitHubUser(accessToken);
        if (githubUser != null) {
            /* 登录成功 */
            String token = userService.userLogin(githubUser);
            if (token != null) {
                Cookie loginCookie = new Cookie(KeyHelper.TOKEN_KEY, token);
                loginCookie.setPath("/");
                response.addCookie(loginCookie);
            }
        } else {
            /* 登录失败 */
        }
        return "redirect:/index";
    }
}
