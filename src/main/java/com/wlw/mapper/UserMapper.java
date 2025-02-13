package com.wlw.mapper;

import com.wlw.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
