package xurong.marinemode.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import xurong.marinemode.community.exception.CustomizeException;
import xurong.marinemode.community.model.User;
import xurong.marinemode.community.service.CommentService;
import xurong.marinemode.community.service.QuestionService;
import xurong.marinemode.community.utils.KeyHelper;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired(required=false)
    private QuestionService questionService;
    @Autowired(required=false)
    private CommentService commentService;
    @GetMapping("/profile/{action}")
    String profile(@PathVariable(name="action") String action,
                   @RequestParam(name="page", required=false, defaultValue="1") int page,
                   Model model, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute(KeyHelper.LoginUserKey);
        if (user == null) {
            /*  没登录 */
            throw new CustomizeException(KeyHelper.UserNotLogin);
        }
        if (KeyHelper.MyQuestion.equals(action)) {
            model.addAttribute(KeyHelper.SectionKey, KeyHelper.MyQuestion);
            model.addAttribute(KeyHelper.SectionNameKey, KeyHelper.MyQuestionSectionName);
            model.addAttribute(KeyHelper.PaginationKey, questionService.getPagination(page, 5, user.getId()));
        }
        if (KeyHelper.NewReply.equals(action)) {
            model.addAttribute(KeyHelper.SectionKey, KeyHelper.NewReply);
            model.addAttribute(KeyHelper.SectionNameKey, KeyHelper.NewReplySectionName);
            model.addAttribute(KeyHelper.PaginationKey, questionService.getPagination(page, 5, user.getId()));
        }
        return "profile";
    }
}
