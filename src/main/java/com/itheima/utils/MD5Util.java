package com.itheima.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class MD5Util {

    private static final String FIXED_SALT = "1a2b3c4d";

    public static String md5(String src) {
        return DigestUtils.md5DigestAsHex(src.getBytes());
    }


    public static String inputPassToFromPass(String inputPass) {
        String str = FIXED_SALT.charAt(0) + FIXED_SALT.charAt(2) + inputPass + FIXED_SALT.charAt(5) + FIXED_SALT.charAt(4);
        return md5(str);
    }

    public static String formPassToDbPass(String formPass, String salt) {
        String str = salt + formPass + salt;
        return md5(str);
    }


    public static String inputPassToDbPass(String inputPass, String salt) {
        String fromPass = inputPassToFromPass(inputPass);
        return formPassToDbPass(fromPass, salt);
    }


//    public static void main(String[] args) {
//
//        String inputPass = "123456";
//        String userSalt = "1a2b3c4d";
//
//        String fromPass = inputPassToFromPass(inputPass);
//        System.out.println("第一次加密结果：" + fromPass);
//
//        String dbPass = inputPassToDbPass(inputPass, userSalt);
//        System.out.println("最终数据库密码：" + dbPass);
//    }
}
