package com.itheima.controller;

import com.itheima.service.TUserService;
import com.itheima.vo.LoginVo;
import com.itheima.vo.RespBean;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping( "/login")
@Slf4j
public class LojinController {


    @Autowired
    private TUserService tUserService;

    @RequestMapping("/tologin")
    public String tologin(){
        log.info("进入登录方法");
        return "login";
    }

    //login
    @RequestMapping("/dologin")
    @ResponseBody
    public RespBean dologin(@Valid LoginVo loginVo){
        log.info("{}",loginVo);
        return tUserService.dologin(loginVo);
    }
}
