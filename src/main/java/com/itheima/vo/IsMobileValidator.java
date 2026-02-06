// IsMobileValidator.java
package com.itheima.vo;

import com.itheima.validator.IsMobile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required && (value == null || value.isEmpty())) {
            return false;
        }
        // 添加手机号校验逻辑（示例）
        return value == null || value.matches("^1[3-9]\\d{9}$");
    }
}
