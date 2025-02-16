package com.wlw.controller;

import com.wlw.constant.RedisConstant;
import com.wlw.pojo.Result;
import com.wlw.pojo.User;
import com.wlw.service.UserService;
import com.wlw.utils.JwtUtil;
import com.wlw.utils.Md5Util;
import com.wlw.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


/**
 * @author zsw
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String token) {
        //1.校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        // 检查密码参数是否为空
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("参数错误");
        }
        //通过用户名查询用户
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        // 检查旧密码是否正确
        if (!user.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("旧密码错误");
        }
        // 检查新密码和确认密码是否一致
        if (!newPwd.equals(rePwd)) {
            return Result.error("两次密码不一致");
        }
        //2.调用用户服务进行密码更新
        userService.updatePwd(newPwd);
        //删除redis中的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }

    /**
     * 更新用户头像地址
     *
     * @param avatarUrl 用户头像的URL地址
     * @return 返回更新操作的结果
     */
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    /**
     * 更新用户信息
     * <p>
     * 该方法通过HTTP PUT请求接收用户对象，然后调用userService的update方法来更新用户信息
     *
     * @param user 用户对象，包含需要更新的用户信息
     * @return 返回更新操作的结果，本例中返回成功结果
     */
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    /**
     * 获取用户信息接口
     * 该方法用于获取经过身份验证的用户的信息
     * 它首先从线程局部变量中提取用户标识（用户名），然后调用用户服务根据用户名查询用户信息
     * 使用Get请求映射到"/userInfo"路径
     *
     * @return 返回包含用户信息的Result对象如果用户不存在或出现其他错误，Result对象将指示相应的错误情况
     */
    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        //1.通过线程对象获取token 解析token，获取用户名
        Map<String, Object> map = ThreadLocalUtil.get();
        //2.根据用户名查询用户
        return Result.success(userService.findByUsername(map.get("username").toString()));
    }

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

        ThreadLocalUtil.set(claims);
        // 将token写入Redis时
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(token,token, RedisConstant.USER_TOKEN_EXPIRE_TIME, TimeUnit.HOURS);
        return Result.success(token);
    }
}

