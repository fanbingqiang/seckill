package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mapper.TUserMapper;
import com.itheima.pojo.TUser;
import com.itheima.service.TUserService;
import com.itheima.utils.CookieUtil;
import com.itheima.utils.MD5Util;
import com.itheima.utils.UUIDUtil;
import com.itheima.utils.ValidatorUtil;
import com.itheima.vo.LoginVo;
import com.itheima.vo.RegisterVo;
import com.itheima.vo.RespBean;
import com.itheima.vo.RespBeanEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser>
        implements TUserService {

    @Autowired
    private TUserMapper tUserMapper;

    @Override
    public RespBean dologin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String moblie = loginVo.getMoblie();
        String password = loginVo.getPassword();
        if (moblie == null || password == null) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        if (!ValidatorUtil.isMobile(moblie)) {
            return RespBean.error(RespBeanEnum.MOBILE_ERRO);
        }
        TUser tUser = tUserMapper.selectById(moblie);
        if (tUser == null) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        if (!MD5Util.formPassToDbPass(password, tUser.getSlat()).equals(tUser.getPassword())) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        String ticket = UUIDUtil.uuid();
        request.getSession().setAttribute(ticket, tUser);
        CookieUtil.setCookie(request, response, "userTicket", ticket);
        return RespBean.success();
    }

    @Override
    public RespBean register(RegisterVo registerVo) {
        String moblie = registerVo.getMoblie();
        String password = registerVo.getPassword();
        String nickname = registerVo.getNickname();

        if (moblie == null || password == null || nickname == null) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        if (!ValidatorUtil.isMobile(moblie)) {
            return RespBean.error(RespBeanEnum.MOBILE_ERRO);
        }

        TUser existUser = tUserMapper.selectById(moblie);
        if (existUser != null) {
            return RespBean.error(RespBeanEnum.MOBILE_EXIST);
        }

        TUser tUser = new TUser();
        tUser.setId(Long.parseLong(moblie));
        tUser.setNickname(nickname);
        tUser.setSlat("1a2b3c4d");
        tUser.setPassword(MD5Util.inputPassToDbPass(password, tUser.getSlat()));
        tUser.setHead("https://img.yzcdn.cn/vant/cat.jpeg");
        tUser.setRegisterDate(new Date());
        tUser.setLastLoginDate(new Date());
        tUser.setLoginCount(0);

        boolean result = this.save(tUser);
        if (result) {
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.REGISTER_ERROR);
    }
}




