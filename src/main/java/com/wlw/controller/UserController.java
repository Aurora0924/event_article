package com.wlw.controller;

import com.wlw.pojo.Result;
import com.wlw.pojo.User;
import com.wlw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zsw
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public Result register(String username, String password) {
        //1.根据用户名查询用户
        User user = userService.findByUsername(username);
        if (user != null) {
            return Result.error("用户名已存在");
        }
        //2.用户不存在，注册
        userService.register(username, password);
        //3.返回结果
        return Result.success();
    }
}
