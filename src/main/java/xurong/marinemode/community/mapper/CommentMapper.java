package xurong.marinemode.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import xurong.marinemode.community.dto.NotificationDTO;
import xurong.marinemode.community.model.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {
    String cols = "parent_id, parent_type, root_id, root_type, user_id, like_count, second_level_root_id, content, gmt_create, gmt_modified";
    String valCols = "#{parentId}, #{parentType}, #{rootId}, #{rootType}, #{userId}, #{likeCount}, #{secondLevelRootId}, #{content}, #{gmtCreate}, #{gmtModified}";
    String tableName = "comment";

    @Insert({"insert into ", tableName," (", cols, ") values (", valCols, ")"})
    int insertComment(Comment comment);

    @Select("select * from comment where id=#{commentId}")
    Comment findCommentById(@Param("commentId") int commentId);

    @Select("select count(1) from comment where root_type=1 and root_id=#{questionId}")
    int countCommentByQuestionID(@Param("questionId") int questionId);

    @Select("select * from comment where root_type=1 and root_id=#{questionId}")
    List<Comment> findCommentsByQuestionId(@Param("questionId") int questionId);

    /* second_level_root_id=0或没有就是一级评论, 否则代表二级评论, 代表他隶属于的那个评论的评论id */
    @Select("select * from comment where root_type=1 and root_id=#{questionId} and (second_level_root_id=0 or second_level_root_id is null)")
    List<Comment> findFirstLevelCommentsByQuestionId(@Param("questionId") int questionId);

    @Select("select * from comment where parent_type=2 and second_level_root_id=#{parentCommentId}")
    List<Comment> findSecondCommentsByParentCommentId(@Param("parentCommentId") int parentCommentId);

    /* 测试用的, 不要乱调 */
    @Select({"select * from comment"})
    List<Comment> allComments();

    int insertNotification(Comment notification);

    @Insert("insert into notification (user_id, comment_id, state) values (#{userId}, #{commentId}, 0)")
    int insertNotificationRecord(@Param("userId") int userId, @Param("commentId") int commentId);

    @Select("select comment.id, comment.content, notification.state " +
            "from comment, notification " +
            "where comment.id=notification.comment_id and notification.user_id=#{userId}")
    List<NotificationDTO> findNotificationByUserId(@Param("userId") int userId);

    @Select("select count(1) from notification where state=0 and user_id=#{userId}")
    int notReadCount(@Param("userId") int userId);

    @Select("select user_id from notification where comment_id=#{commentId}")
    int findUserIdByNotificationId(@Param("commentId") int commentId);

    @Update("update notification set state=1 where user_id=#{userId} and comment_id=#{commentId}")
    int readNotificationByUserIdAndCommentId(@Param("userId") int userId, @Param("commentId") int commentId);
}
