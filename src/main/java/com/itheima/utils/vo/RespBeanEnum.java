package com.itheima.utils.vo;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public enum RespBeanEnum {
    SUCCESS(200, "操作成功"),
    ERROR(500, "操作失败");


    private Integer code;
    private String message;
}
