package xurong.marinemode.community.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xurong.marinemode.community.dto.CommentDTO;
import xurong.marinemode.community.dto.CommentShowDTO;
import xurong.marinemode.community.dto.NotificationDTO;
import xurong.marinemode.community.exception.CodeAndMsg;
import xurong.marinemode.community.exception.CustomizeException;
import xurong.marinemode.community.mapper.CommentMapper;
import xurong.marinemode.community.mapper.UserMapper;
import xurong.marinemode.community.model.Comment;
import xurong.marinemode.community.utils.CommentType;
import xurong.marinemode.community.utils.KeyHelper;
import xurong.marinemode.community.utils.UserHolder;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private UserHolder userHolder;
    @Autowired(required=false)
    private CommentMapper commentDao;
    @Autowired(required=false)
    private UserMapper userDao;
    public CodeAndMsg insertComment(CommentDTO commentDTO) {
        /* 用户未登录 */
        if (userHolder.get() == null) {
            return new CodeAndMsg(KeyHelper.StateCodeFaile, KeyHelper.UserNotLogin);
        }
        /* 评论内容为空 */
        if (StringUtils.isBlank(commentDTO.getContent())) {
            return new CodeAndMsg(KeyHelper.StateCodeFaile, KeyHelper.CommentContentCantBlank);
        }
        /* 评论类型不存在 */
        if (!CommentType.commentTypes.contains(commentDTO.getParentType())) {
            return new CodeAndMsg(KeyHelper.StateCodeFaile, KeyHelper.CommentTypeNotExit);
        }
        /* 要评论的那个实体不存在
        *  这里先不处理吧, 实体不存在的话评论了也展示不出来, 以后再补 */
        Comment comment = new Comment();
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setParentId(commentDTO.getParentId());
        comment.setParentType(commentDTO.getParentType());
        comment.setRootId(commentDTO.getRootId());
        comment.setRootType(commentDTO.getRootType());
        comment.setSecondLevelRootId(commentDTO.getSecondLevelRootId());
        comment.setContent(commentDTO.getContent());
        comment.setUserId(userHolder.get().getId());
        if (commentDao.insertComment(comment) != 1) {
            /* 插入异常 */
            return new CodeAndMsg(KeyHelper.StateCodeFaile, KeyHelper.CommentInsertDatebaseException);
        } else {
            /* 评论成功, 如果是问题的评论, 就把评论数+1,
               暂时这么做, 以后用户量大了, 要把评论数增加座位缓存, 到了一定程度再写数据库
               但是有个问题, 如果是评论的评论, 那么怎么找到它所在的那个问题然后增加呢
               或者说评论数根本不写数据库, 而是依靠sql的查询来获得每个问题有几条评论  */

        }
        return new CodeAndMsg(KeyHelper.StateCodeSuccess, KeyHelper.DoCommentSuccess);
    }
    /* 这一段对数据库的压力很大, 评论数大的时候不要这样做,
       甚至二级评论都不要去加载, 等用户点开看的时候再用ajax加载 */
    public List<CommentShowDTO> getCommentListByQuestionId(int questionId) {
        List<Comment> commentList = commentDao.findFirstLevelCommentsByQuestionId(questionId);
        List<CommentShowDTO> commentDTOList = new ArrayList<>();
        for (Comment comment: commentList) {
            CommentShowDTO commentShowDTO = new CommentShowDTO();
            commentShowDTO.setComment(comment);
            commentShowDTO.setUser(userDao.findUserById(comment.getUserId()));
            List<Comment> secondLevelCommentList = commentDao.findSecondCommentsByParentCommentId(comment.getId());
            List<CommentShowDTO> secondCommentDTOList = new ArrayList<>();
            for (Comment sceondComment: secondLevelCommentList) {
                CommentShowDTO secondCommentShowDTO = new CommentShowDTO();
                secondCommentShowDTO.setComment(sceondComment);
                secondCommentShowDTO.setUser(userDao.findUserById(sceondComment.getUserId()));
                secondCommentDTOList.add(secondCommentShowDTO);
            }
            commentShowDTO.setSecondLevelComment(secondCommentDTOList);
            commentDTOList.add(commentShowDTO);
        }
        return commentDTOList;
    }

    public CodeAndMsg notificate(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setParentId(commentDTO.getParentId());
        comment.setParentType(commentDTO.getParentType());
        comment.setRootId(commentDTO.getRootId());
        comment.setRootType(commentDTO.getRootType());
        comment.setSecondLevelRootId(0);
        comment.setContent(commentDTO.getContent());
        comment.setUserId(0);
        if (commentDao.insertNotification(comment) != 1) {
            return new CodeAndMsg(KeyHelper.StateCodeFaile, KeyHelper.CommentInsertDatebaseException);
        }
        commentDao.insertNotificationRecord(comment.getParentId(), comment.getId());
        return new CodeAndMsg(KeyHelper.StateCodeSuccess, KeyHelper.NotificateSuccess);
    }

    public Object getUserNotification() {
        if (userHolder.get() == null) {
            return new CodeAndMsg(KeyHelper.StateCodeFaile, KeyHelper.UserNotLogin);
        }
        return commentDao.findNotificationByUserId(userHolder.get().getId());
    }

    public int getNotReadNotificationCount() {
        if (userHolder.get() == null) {
            return -1;
        }
        return commentDao.notReadCount(userHolder.get().getId());
    }

    public void readNotification(int commentId) {
        if (userHolder.get() == null) {
            throw new CustomizeException(KeyHelper.UserNotLogin);
        }
        if (commentDao.findCommentById(commentId) == null) {
            throw new CustomizeException(KeyHelper.NotificationNotExit);
        }
        if (userHolder.get().getId() != commentDao.findUserIdByNotificationId(commentId)) {
            throw new CustomizeException(KeyHelper.ThisNotificationYouCantRead);
        }
        commentDao.readNotificationByUserIdAndCommentId(userHolder.get().getId(), commentId);
    }
}
