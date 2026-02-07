package com.itheima.service;

import com.itheima.pojo.TUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.vo.LoginVo;
import com.itheima.vo.RespBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
* @author ZhuanZ（无密码）
* @description 针对表【t_user】的数据库操作Service
* @createDate 2026-02-03 13:45:27
*/
public interface TUserService extends IService<TUser> {


    RespBean dologin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);
}
