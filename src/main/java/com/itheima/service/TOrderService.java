package com.itheima.service;

import com.itheima.pojo.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pojo.TUser;
import com.itheima.vo.GoodsVo;

/**
* @author ZhuanZ（无密码）
* @description 针对表【t_order】的数据库操作Service
* @createDate 2026-02-07 20:48:03
*/
public interface TOrderService extends IService<TOrder> {

    TOrder seckill(TUser user, GoodsVo goodsVo);
}
