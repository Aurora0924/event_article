package com.wlw.interceptor;

import com.wlw.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * @author zsw
 * 登录拦截器，用于验证请求中的Token合法性。
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

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

        try {
            // 验证token，解析成功则放行请求
            Map<String, Object> claims = JwtUtil.parseToken(token);
            return true;
        } catch (Exception e) {
            // Token解析失败，设置响应状态码为401（未授权）
            response.setStatus(401);
            return false;
        }
    }
}
