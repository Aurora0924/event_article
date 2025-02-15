package com.wlw.anno;


import com.wlw.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author zsw
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StateValidation.class})//指定校验器
public @interface State {
    /**
     *  校验失败的提示信息
     * @return
     */
    String message() default "state参数的值只能是已发布或草稿";

    /**
     * 指定分组
     * @return
     */
    Class<?>[] groups() default {};

    /**
     * 负载，提取错误信息
     * @return
     */
    Class<? extends Payload>[] payload() default {};
}
