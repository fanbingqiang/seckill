package com.itheima.vo;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public enum RespBeanEnum {
    SUCCESS(200, "操作成功"),
    ERROR(500, "操作失败"),
    LOGIN_ERROR(500210, "请先登录"),
    MOBILE_ERRO(500211, "手机号码不合法");


    private Integer code;
    private String message;
}
