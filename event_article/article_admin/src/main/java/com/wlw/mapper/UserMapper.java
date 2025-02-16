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
    User findByUsername(String username);

    /**
     * 添加用户
     * @param username
     * @param md5String
     */
    void add(@Param("username") String username, @Param("password") String md5String);

    /**
     * 修改用户信息
     * @param user
     */

    void update(User user);

    /**
     * 修改用户头像
     * @param avatarUrl
     */

    void updateAvatar(String avatarUrl,Integer id);

    /**
     * 修改用户密码
     * @param md5String
     * @param id
     */

    void updatePwd(String md5String, Integer id);
}
