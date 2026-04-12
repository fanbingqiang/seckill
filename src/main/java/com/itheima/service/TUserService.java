package com.itheima.service;

import com.itheima.pojo.TUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.vo.LoginVo;
import com.itheima.vo.RegisterVo;
import com.itheima.vo.RespBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface TUserService extends IService<TUser> {

    RespBean dologin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    RespBean register(RegisterVo registerVo);
}
