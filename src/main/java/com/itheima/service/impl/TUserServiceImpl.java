package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.pojo.TUser;
import com.itheima.service.TUserService;
import com.itheima.mapper.TUserMapper;
import com.itheima.utils.CookieUtil;
import com.itheima.utils.MD5Util;
import com.itheima.utils.UUIDUtil;
import com.itheima.utils.ValidatorUtil;
import com.itheima.vo.LoginVo;
import com.itheima.vo.RespBean;
import com.itheima.vo.RespBeanEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author ZhuanZ（无密码）
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2026-02-03 13:45:27
*/
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser>
    implements TUserService{

    @Autowired
    private TUserMapper tUserMapper;

    //登录
    @Override
    public RespBean dologin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String moblie = loginVo.getMoblie();
        String password = loginVo.getPassword();
        //参数验证
        if(moblie==null||password==null){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        if (!ValidatorUtil.isMobile(moblie)){
            return RespBean.error(RespBeanEnum.MOBILE_ERRO);
        }
        //根据用户返回手机号
        TUser tUser = tUserMapper.selectById(moblie);
        if (tUser==null){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        //密码验证
        if (!MD5Util.formPassToDbPass(password,tUser.getSlat()).equals(tUser.getPassword())){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }

        //生成cookie
        String ticket = UUIDUtil.uuid();
        request.getSession().setAttribute(ticket,tUser);
        CookieUtil.setCookie(request,response,"userTicket",ticket);

        return RespBean.success();
    }
}




