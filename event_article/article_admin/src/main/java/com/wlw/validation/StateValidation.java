package com.wlw.validation;

import com.wlw.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author zsw
 */
public class StateValidation implements ConstraintValidator<State, String> {
    /**
     * 判断状态是否正确
     * @param s
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s==null) {
            return false;
        }
        if(s.equals("已发布")||s.equals("草稿")) {
            return true;
        }
        return false;
    }
}
