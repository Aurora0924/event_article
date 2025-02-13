package com.wlw.controller;

import com.wlw.pojo.Result;
import com.wlw.pojo.User;
import com.wlw.service.UserService;
import com.wlw.utils.JwtUtil;
import com.wlw.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * @author zsw
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册接口
     *
     * @param username 用户名，长度为5-16个字符，不能包含空格
     * @param password 密码，长度为5-16个字符，不能包含空格
     * @return 返回注册结果，成功或失败
     */
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
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

    /**
     * 处理用户登录请求
     *
     * @param username 用户名，长度为5-16个字符，不能包含空格
     * @param password 密码，长度为5-16个字符，不能包含空格
     * @return 登录结果，包括生成的JWT令牌
     */
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //1.根据用户名查询用户
        User user = userService.findByUsername(username);
        if (user == null) {
            return Result.error("用户名不存在");
        }
        //2.用户存在，将密码进行加密后与数据库中的密码进行比较
        if (!Md5Util.getMD5String(password).equals(user.getPassword())) {
            return Result.error("密码错误");
        }
        //登录成功，生成JWT令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        String token = JwtUtil.genToken(claims);
        return Result.success(token);
    }
}

