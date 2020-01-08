package xurong.marinemode.community.mapper;

import org.apache.ibatis.annotations.*;
import xurong.marinemode.community.model.User;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into user " +
            "(account_id, username, token, gmt_create, gmt_modified, profile_picture, platform, biography) " +
            "values(#{accountId}, #{username}, #{token}, #{gmtCreate}, #{gmtModified}, #{profilePicture}, #{platform}, #{biography})")
    int insertUser(User user);
    @Select("select * from user where token = #{token}")
    User findUserByToken(@Param("token") String token);
    @Select("select * from user where username = #{username} and platform = #{platform}")
    User findUserByUsernameAndPlatform(@Param("username") String username, @Param("platform") String platform);
    @Select("select * from user where account_id = #{accountId} and platform = #{platform}")
    User findUserByAccountIdAndPlatform(@Param("accountId") String accountId, @Param("platform") String platform);
    @Select("select * from user where id = #{id}")
    User findUserById(@Param("id") int id);
    @Update("update user set token=#{token} where id=#{userId}")
    int updateTokenByUserId(@Param("userId") int userId, @Param("token") String token);
    @Delete("delete from user where id = #{userId}")
    int deleteUserById(@Param("userId") int userId);

    /* 这是测试用的, 不要乱调 */
    @Select("select * from user")
    List<User> allUser();
}
