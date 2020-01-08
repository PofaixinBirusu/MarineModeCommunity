package xurong.marinemode.community.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xurong.marinemode.community.dto.CommentDTO;
import xurong.marinemode.community.exception.CodeAndMsg;
import xurong.marinemode.community.exception.CustomizeException;
import xurong.marinemode.community.service.CommentService;
import xurong.marinemode.community.utils.KeyHelper;

@Controller
public class CommentController {
    @Autowired(required=false)
    private CommentService commentService;
    @RequestMapping(value="/comment", method = RequestMethod.POST)
    @ResponseBody
    public Object doComment(@RequestBody CommentDTO commentDTO) {
        CodeAndMsg commentResult = commentService.insertComment(commentDTO);
        return commentResult;
    }

    @RequestMapping(value="/notificate", method = RequestMethod.POST)
    @ResponseBody
    public Object notificate(@RequestBody CommentDTO commentDTO) {
        CodeAndMsg notificateResult = commentService.notificate(commentDTO);
        return notificateResult;
    }

    @GetMapping("/notification")
    @ResponseBody
    public Object getNotification() {
        return commentService.getUserNotification();
    }

    @GetMapping("/readnotification/{commentId}")
    public String readNotification(@PathVariable("commentId") int commentId, @RequestParam("nexturl") String nexturl) {
        if (StringUtils.isBlank(nexturl)) {
            throw new CustomizeException(KeyHelper.NextUrlIsBlank);
        }
        commentService.readNotification(commentId);
        return "redirect:"+nexturl;
    }
}
