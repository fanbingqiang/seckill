package com.itheima.controller;

import com.itheima.service.TUserService;
import com.itheima.vo.LoginVo;
import com.itheima.vo.RespBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private TUserService tUserService;

    /**
     * 跳转到登录页面
     */
    @RequestMapping("/tologin")
    public String tologin(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        log.info("用户访问登录页面，客户端 IP: {}", clientIp);
        return "login";
    }

    /**
     * 处理用户登录请求
     *
     * @param loginVo 用户登录信息
     * @return 登录结果响应体
     */
    @RequestMapping("/dologin")
    @ResponseBody
    public RespBean dologin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        return tUserService.dologin(loginVo,request,response);
    }

}
