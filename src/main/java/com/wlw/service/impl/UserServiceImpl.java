package com.wlw.service.impl;

import com.wlw.mapper.UserMapper;
import com.wlw.pojo.User;
import com.wlw.service.UserService;
import com.wlw.utils.Md5Util;
import com.wlw.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author zsw
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    /**
     * 注册用户方法
     * 本方法主要用于用户注册，将用户的用户名和密码进行处理后存入数据库
     *
     * @param username 用户名，应确保唯一性
     * @param password 明文密码，将被转换为MD5密文以增强安全性
     */
    @Override
    public void register(String username, String password) {
        //1.将用户信息进行加密封装
        String md5String = Md5Util.getMD5String(password);
        //2.调用mapper进行插入
        userMapper.add(username, md5String);
    }

    /**
     * 更新用户信息
     *
     * 此方法主要用于更新用户对象它首先将用户的更新时间设置为当前时间，
     * 然后调用userMapper的update方法来执行数据库更新操作
     *
     * @param user 待更新的用户对象，包含用户的相关信息
     */
    @Override
    public void update(User user) {
        // 设置用户的更新时间为当前系统时间
        user.setUpdateTime(LocalDateTime.now());
        // 调用Mapper层的方法执行数据库更新操作
        userMapper.update(user);
    }

    /**
     * 更新用户头像
     *
     * 此方法用于更新用户的头像URL，通过调用userMapper的updateAvatar方法来实现
     * 它不处理任何参数，也不返回任何结果，但可能会抛出由userMapper.updateAvatar引起的异常
     *
     * @param avatarUrl 新的头像URL，不能为空
     */
    @Override
    public void updateAvatar(String avatarUrl) {
        //1.获取当前登录用户的id
        Map<String,Object> map = ThreadLocalUtil.get();
        //2.调用mapper层进行更新
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd) {
        //1.获取当前登录用户的id
        Map<String,Object> map = ThreadLocalUtil.get();
        //2.调用mapper层进行更新
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd),id);
    }
}
