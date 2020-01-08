package xurong.marinemode.community.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommentType {
    /**
     *    1: 问题的评论
     *    2：评论的评论
     *    3：系统通知，即评论的对象为用户
     **/
    public static List<Integer> commentTypes = new ArrayList<>(Arrays.asList(1, 2, 3));
    public static int QuestionCommentType = 1;
    public static int CommentCommentType = 2;
    public static int NotificationCommentType = 3;
}
