package xurong.marinemode.community.mapper;

import org.apache.ibatis.annotations.*;
import xurong.marinemode.community.model.Question;

import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;

@Mapper
public interface QuestionMapper {
    final String cols = "title, description, gmt_create, " +
            "gmt_modified, user_id, comment_count, read_count, like_count, labels";
    final String valCols = "#{title}, #{description}, #{gmtCreate}, " +
            "#{gmtModified}, #{userId}, #{commentCount}, #{readCount}, #{likeCount}, #{labels}";

    @Insert({"insert into question (", cols, ") values (", valCols, ")"})
    int insertQuestion(Question question);
    @Update("update question " +
            "set title=#{title}, description=#{description}, gmt_modified=#{gmtModified}, labels=#{labels} " +
            "where id=#{id}")
    int updateQuestionByNewQuestion(Question newQuestion);
    @Update("update question set read_count=read_count+1 where id=#{questionId}")
    int questionReadCountInc(@Param("questionId") int questionId);
    @Select("select * from question limit #{offset}, #{size}")
    List<Question> findQuestionsByOffsetAndSize(@Param("offset") int offset, @Param("size") int size);

    @Select("select count(1) from question")
    int allQuestionNumber();

    @Select("select count(1) from question where user_id = #{userId}")
    int countQuestionByUserId(@Param("userId") int userId);

    @Select("select * from question where user_id = #{userId} limit #{offset}, #{size}")
    List<Question> findQuestionsByUserIdAndOffsetAndSize(@Param("userId") int userId, @Param("offset") int offset, @Param("size") int size);

    @Select("select * from question where id = #{questionId}")
    Question findQuestionById(@Param("questionId") int questionId);


    List<Question> findQuestionByLabelList(@Param("labels") List<String> labels);
    List<Question> findQuestionBySearchKeyList(Map<String, Object> searchKeysAndOffsetAndSize);
    int countQuestionBySearchKeys(@Param("searchKeys") List<String> searchKeys);

    /* 这是测试用的, 不要乱调 */
    @Select("select * from question")
    List<Question> allQuestion();
}
