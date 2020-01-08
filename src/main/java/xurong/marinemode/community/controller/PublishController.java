package xurong.marinemode.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xurong.marinemode.community.exception.CustomizeException;
import xurong.marinemode.community.model.Question;
import xurong.marinemode.community.model.User;
import xurong.marinemode.community.service.QuestionService;
import xurong.marinemode.community.utils.KeyHelper;
import xurong.marinemode.community.utils.Message;
import xurong.marinemode.community.utils.UserHolder;

@Controller
public class PublishController {
    @Autowired(required=false)
    private QuestionService questionService;
    @Autowired(required=false)
    private UserHolder userHolder;
    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam("questionId") String questionId,
                            Model model) {
        if (title == null || title.equals("")) {
            model.addAttribute(KeyHelper.ErrorKey, Message.titleCantNull);
            return "publish";
        }
        model.addAttribute("title", title);
        if (description == null || description.equals("")) {
            model.addAttribute(KeyHelper.ErrorKey, Message.descriptionCantNull);
            return "publish";
        }
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        User user = userHolder.get();
        if (user != null) {
            if (questionId == null || "".equals(questionId)) {
                /* 这个问题没有发布过，就插进数据库 */
                questionService.doPublish(title, description, tag, user.getId());
            } else {
                /* 这个问题在库中是有的, 就修改 */
                questionService.doUpdate(title, description, tag, Integer.parseInt(questionId));
            }
            return "redirect:/index";
        } else {
            model.addAttribute(KeyHelper.ErrorKey, Message.userNotLogin);
        }
        return "publish";
    }
    @GetMapping("/publish/{questionId}")
    public String editQuestion(@PathVariable("questionId") int questionId, Model model) {
        Question question = questionService.getQuestionById(questionId);
        if (question == null || userHolder.get() == null || question.getUserId() != userHolder.get().getId()) {
            /* 问题的id不对，或者用户没登录，或者用户不是该问题的发布者，都是不能编辑的，直接让页面死掉 */
            throw  new CustomizeException(KeyHelper.QuestionNotEditMessage);
        }
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getLabels());
        model.addAttribute("questionId", question.getId());
        return "publish";
    }

}
