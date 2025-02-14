package com.wlw.config;

import com.wlw.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
/**
 * @author zsw
 * WebConfig类实现了WebMvcConfigurer接口，用于自定义Spring MVC的应用程序配置
 * 主要用于添加拦截器、处理跨域请求等
 */

public class WebConfig implements WebMvcConfigurer {

    /**
     * 自动注入LoginInterceptor实例，用于后续的拦截器配置
     */
    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 添加拦截器配置
     * 此方法用于注册自定义的拦截器，并指定拦截器的拦截路径
     *
     * @param registry InterceptorRegistry实例，用于注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册一个新的LoginInterceptor实例到拦截器注册表中
        //拦截除登录注册页面之外的所有请求
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/user/login","/user/register")
                //可以继续排除其他不需要拦截的路径模式
                .excludePathPatterns();
    }
}

