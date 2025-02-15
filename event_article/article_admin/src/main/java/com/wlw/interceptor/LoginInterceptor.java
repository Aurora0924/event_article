package com.wlw.interceptor;

import com.wlw.constant.RedisConstant;
import com.wlw.pojo.User;
import com.wlw.utils.JwtUtil;
import com.wlw.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.Optional;

/**
 * @author zsw
 * 登录拦截器，用于验证请求中的Token合法性。
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 在请求处理之前进行拦截验证。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理器对象
     * @return 如果Token合法返回true，否则返回false并设置响应状态码为401
     * @throws Exception 可能抛出的异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头中的token
        String token = request.getHeader("Authorization");
        //1.获取当前登录用户的id
        Map<String,Object> map = ThreadLocalUtil.get();

        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(RedisConstant.USER_TOKEN+map.get("id"));
            if (redisToken == null || !redisToken.equals(token)){
                return false;
            }
            // 验证token，解析成功则放行请求
            Map<String, Object> claims = JwtUtil.parseToken(token);
            ThreadLocalUtil.set(claims);
            return true;
        } catch (Exception e) {
            // Token解析失败，设置响应状态码为401（未授权）
            response.setStatus(401);
            return false;
        }
    }

    /**
     * 在请求完成后执行的操作
     *
     * @param request  HTTP请求对象，用于获取请求相关的信息
     * @param response HTTP响应对象，用于获取响应相关的信息
     * @param handler  处理请求的处理器对象，可以是任何对象，取决于具体的实现
     * @param ex       在请求处理过程中发生的异常，如果没有异常，则为null
     * @throws Exception 可以抛出异常，需要由调用者处理
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清空ThreadLocal
        ThreadLocalUtil.remove();
    }
}
