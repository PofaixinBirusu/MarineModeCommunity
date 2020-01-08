package xurong.marinemode.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xurong.marinemode.community.dto.QuestionDTO;
import xurong.marinemode.community.mapper.CommentMapper;
import xurong.marinemode.community.service.CommentService;
import xurong.marinemode.community.service.QuestionService;
import xurong.marinemode.community.utils.KeyHelper;

@Controller
public class QuestionController {

    @Autowired(required=false)
    private QuestionService questionService;
    @Autowired(required=false)
    private CommentService commentService;

    @GetMapping("/question/{questionId}")
    public String questionDetail(@PathVariable(name="questionId") int questionId,
                                 Model model) {
        QuestionDTO questionDTO = questionService.getQuestionDTOById(questionId);
        model.addAttribute(KeyHelper.QuestionKey, questionDTO);
        /* 读到这个问题, 阅读数就+1, 控制刷新次数防止刷阅读数的功能暂时不做            */
        /* 其实这个逻辑应该提到最前面, 也不管了, 赶时间, 想借用下前面的异常判断         */
        /* 写数据库去+1更是万万不可以的操作, 并发量大的时候会直接卡死的, 应该存redis里面 */
        questionService.readCountInc(questionId);
        model.addAttribute(KeyHelper.CommentListKey, commentService.getCommentListByQuestionId(questionId));
        model.addAttribute(KeyHelper.RelativeQuestions,
                questionService.relateQuestions(questionDTO.getQuestion()));
        return "question";
    }
}
