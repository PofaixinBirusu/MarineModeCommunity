package xurong.marinemode.community.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xurong.marinemode.community.dto.PaginationDTO;
import xurong.marinemode.community.mapper.UserMapper;
import xurong.marinemode.community.service.CommentService;
import xurong.marinemode.community.service.QuestionService;
import xurong.marinemode.community.utils.KeyHelper;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired(required=false)
    private UserMapper userDao;
    @Autowired(required=false)
    private QuestionService questionService;
    @Autowired(required=false)
    private CommentService commentService;
    @GetMapping("/index")
    String index(HttpServletRequest request, Model model,
                 @RequestParam(name="page", required=false, defaultValue="1") int page,
                 @RequestParam(name="size", required=false, defaultValue="7") int size,
                 @RequestParam(name="searchKey", required=false, defaultValue="") String searchKey) {
        /* 登录完了也会跳回首页, 这时检查下登录了没有 */
        PaginationDTO pagination = questionService.getPagination(page, size, searchKey);
        model.addAttribute(KeyHelper.PaginationKey, pagination);
        if (!StringUtils.isBlank(searchKey)) {
            model.addAttribute(KeyHelper.SearchKeysKey, searchKey);
        }
        return "index";
    }
}
