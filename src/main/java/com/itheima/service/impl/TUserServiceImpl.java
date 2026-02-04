package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.pojo.TUser;
import com.itheima.service.TUserService;
import com.itheima.mapper.TUserMapper;
import org.springframework.stereotype.Service;

/**
* @author ZhuanZ（无密码）
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2026-02-03 13:45:27
*/
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser>
    implements TUserService{

}




