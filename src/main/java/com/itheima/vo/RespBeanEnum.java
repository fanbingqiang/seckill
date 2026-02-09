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
    MOBILE_ERRO(500211, "手机号码不合法"),
    BIND_ERROR(500212, "参数校验异常"),
    USER_NOT_EXIST(500213, "用户不存在"),
    PASSWORD_ERROR(500214, "密码错误"),
    EMPTY_STOCK(500500, "库存不足"),
    REPEATE_ERROR(500501, "重复下单");



    private Integer code;
    private String message;
}
