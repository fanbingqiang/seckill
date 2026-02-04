package com.itheima.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/login")
@Slf4j
public class LojinController {


    @RequestMapping("/login")
    public String tologin(){
        log.info("进入登录方法");
        return "login";
    }

    @RequestMapping("/dologin")
    public String dologin(){
        log.info("进入登录方法");
        return "index";
    }

}
