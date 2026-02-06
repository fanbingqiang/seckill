package com.itheima.vo;

import com.itheima.validator.IsMobile;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginVo {
    @NotNull
    @IsMobile(required = true)
    private String moblie;

    @NotNull
    @Length(min = 32)
    private String password;
}
