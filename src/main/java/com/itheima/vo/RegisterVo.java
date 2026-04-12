package com.itheima.vo;

import com.itheima.validator.IsMobile;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegisterVo {
    @NotNull
    @IsMobile(required = true)
    private String moblie;

    @NotNull
    @Length(min = 6, max = 16)
    private String password;

    @NotNull
    @Length(min = 2, max = 16)
    private String nickname;
}