package com.wlw.exceptionhandler;

import com.wlw.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * @author zsw
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常处理方法，捕获所有未处理的异常并返回统一的错误响应。
     *
     * @param e 异常对象
     * @return 返回包含错误信息的结果对象
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        // 打印异常堆栈信息，便于调试和排查问题
        e.printStackTrace();

        // 根据异常消息返回相应的错误信息，若无具体消息则返回默认提示
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}

