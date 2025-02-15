package com.wlw.mapper;

import com.wlw.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author zsw
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Select("select * from user where username=#{username}")
    User findByUsername(String username);

    /**
     * 添加用户
     * @param username
     * @param md5String
     */
    @Select("insert into user(username,password,update_time,create_time) values(#{username},#{password},now(),now())")
    void add(@Param("username") String username, @Param("password") String md5String);

    /**
     * 修改用户信息
     * @param user
     */
    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(User user);

    /**
     * 修改用户头像
     * @param avatarUrl
     */
    @Update("update user set user_pic=#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl,Integer id);

    /**
     * 修改用户密码
     * @param md5String
     * @param id
     */
    @Update("update user set password=#{md5String},update_time=now() where id=#{id}")
    void updatePwd(String md5String, Integer id);
}
