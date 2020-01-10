package xurong.marinemode.community.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xurong.marinemode.community.mapper.CommentMapper;
import xurong.marinemode.community.mapper.QuestionMapper;
import xurong.marinemode.community.mapper.UserMapper;
import xurong.marinemode.community.model.Question;
import xurong.marinemode.community.model.User;
import xurong.marinemode.community.service.UserService;
import xurong.marinemode.community.utils.KeyHelper;
import xurong.marinemode.community.utils.PlatformKey;
import xurong.marinemode.community.utils.UserHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/* 测试下环境搭好了没有, 一个没屁用的controller */
@Controller
public class TestController {
    @Autowired(required=false)
    private UserMapper userDao;
    @Autowired(required=false)
    private QuestionMapper questionDao;
    @Autowired(required=false)
    private UserHolder userHolder;
    @Autowired(required=false)
    private CommentMapper commentDao;
    @Autowired(required=false)
    private UserService userService;

    @GetMapping("/hello")
    /* command + p 可以看参数 */
    String hello(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        /* 会去templates里面找同名的html */
        return "Hello";
    }
    @GetMapping("/testInsertUser")
    @ResponseBody
    String insertUser() {
        User user = new User();
        user.setAccountId("12345678");
        user.setUsername("测试用户4");
        System.out.println(System.currentTimeMillis());
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        user.setToken(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 30));
        int success = userDao.insertUser(user);
        return JSON.toJSONString(userDao.allUser());
    }
    @GetMapping("/testAllUser")
    @ResponseBody
    String allUser() {
        return JSON.toJSONString(userDao.allUser());
    }
    @GetMapping("/findByNameAndPlatform")
    @ResponseBody
    String findUserByNameAndPlatform(@RequestParam("username") String username,
                                     @RequestParam("platform") String platform) {
        return JSON.toJSONString(userDao.findUserByUsernameAndPlatform(username, platform));
    }
    @GetMapping("/updateToken")
    @ResponseBody
    String updateToken(@RequestParam("userId") int userId,
                       @RequestParam("token") String token) {
        userDao.updateTokenByUserId(userId, token);
        return JSON.toJSONString(userDao.allUser());
    }
    @GetMapping("/deleteUserById")
    @ResponseBody
    String deleteUserById(@RequestParam("userId") int userId) {
        userDao.deleteUserById(userId);
        return JSON.toJSONString(userDao.allUser());
    }
    @GetMapping("/testLogin")
    @ResponseBody
    String testLogin(HttpServletRequest request) {
        User user = userHolder.get();
        Map<String, User> queryResult = new HashMap<>();
        queryResult.put("user", user);
        return JSON.toJSONString(queryResult);
    }

    @GetMapping("/testAllQuestion")
    @ResponseBody
    String testAllQUestion() {
        return JSON.toJSONString(questionDao.allQuestion());
    }

    @GetMapping("/autoInsertQuestion")
    @ResponseBody
    String autoInsertQuestion() {
        for (int i = 1; i <= 50; i++) {
            Question question = new Question();
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setTitle("测试标题"+i);
            question.setDescription("测试问题描述信息"+i);
            question.setLabels("深度学习");
            question.setUserId(2);
            questionDao.insertQuestion(question);
        }
        return JSON.toJSONString(questionDao.allQuestion());
    }
    @GetMapping("/testAllComment")
    @ResponseBody
    String allComment() {
        return JSON.toJSONString(commentDao.allComments());
    }

    @GetMapping("/testMapper")
    @ResponseBody
    Object testMapper() {
        return questionDao.findQuestionByLabelList(Arrays.asList("机器学习", "深度学习"));
    }

    @GetMapping("/pikachuLogin")
    @ResponseBody
    String pikachuLogin(HttpServletResponse response) {
        User pikachu = new User();
        pikachu.setAccountId("4");
        pikachu.setPlatform(PlatformKey.Native);
        Map<String, String> loginRes = new HashMap<>();
        String token = userService.userLogin(pikachu);
        if (token == null) {
            token = "";
        } else {
            Cookie loginCookie = new Cookie(KeyHelper.TOKEN_KEY, token);
            loginCookie.setPath("/");
            response.addCookie(loginCookie);
        }
        loginRes.put("res", token);
        return JSON.toJSONString(loginRes);
    }

    @RequestMapping("/testsocket")
    public String testsocket() {
        return "testsocket";
    }
}
