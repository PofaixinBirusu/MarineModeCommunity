package xurong.marinemode.community.utils;

public class KeyHelper {
    public static final String StateCodeKey = "state";
    /* 事实上状态码不能用0和1这样的, 要用200, 20003这样的规范码, 这个之后再说 */
    public static final int StateCodeSuccess = 1;
    public static final int StateCodeFaile = 0;
    public static final String JsonMessageKey = "msg";

    public static final String TOKEN_KEY = "MARINE_MODEL_USER_TOKEN";
    public static final String JSESSION_KEY = "JSESSIONID";

    public static final String LoginUserKey = "user";
    public static final String ErrorKey = "error";
    public static final String PaginationKey = "pagination";
    /* 个人主页 */
    public static final String SectionKey = "section";
    public static final String SectionNameKey = "sectionName";
    public static final String MyQuestion = "question";
    public static final String MyQuestionSectionName = "我的提问";
    public static final String NewReply = "reply";
    public static final String NewReplySectionName = "最新回复";

    public static final String QuestionKey = "question";
    public static final String RelativeQuestions = "relativeQuestions";

    public static final String ErrorPageKey = "error";
    public static final String ErrorMessageKey = "message";

    public static final String UnknowExceptionMessage = "page die...";
    public static final String QuestionNotExitMessage = "问题不存在";
    public static final String QuestionNotEditMessage = "该问题不可编辑";
    /* 评论 */
    public static final String DoCommentSuccess = "评论成功";
    public static final String NotificateSuccess = "通知成功";
    public static final String DoCommentFaild = "评论失败";
    public static final String CommentContentCantBlank = "评论内容不能为空";
    public static final String CommentTypeNotExit = "评论类型不存在";
    public static final String UserNotLogin = "用户未登录";
    public static final String CommentInsertDatebaseException = "评论插入数据库异常";
    public static final String CommentListKey = "commentList";

    public static final String NotReadNotificationCount = "notReadCount";

    public static final String NextUrlIsBlank = "下一步不知往哪走";
    public static final String NotificationNotExit = "消息不存在";
    public static final String ThisNotificationYouCantRead = "这条消息你不能读";

    public static final String UpLoadSuccess = "上传成功";
    public static final String UpLoadFaile = "上传失败";
    public static final String UpLoadFileFormatNotCorrect = "上传的文件格式不正确";

    /* 搜索 */
    public static final String SearchKeysKey = "searchKeys";
    public static final String OffsetKey = "offset";
    public static final String SizeKey = "size";
}
