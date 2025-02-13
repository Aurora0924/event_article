package com.wlw.service.impl;

import com.wlw.mapper.UserMapper;
import com.wlw.pojo.User;
import com.wlw.service.UserService;
import com.wlw.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void register(String username, String password) {
        //1.将用户信息进行加密封装
        String md5String = Md5Util.getMD5String(password);
        //2.调用mapper进行插入
        userMapper.add(username, md5String);
    }
}
