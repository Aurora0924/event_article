package com.wlw.service;

import com.wlw.pojo.User;

/**
 * @author zsw
 */
public interface UserService {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    void register(String username, String password);
}
