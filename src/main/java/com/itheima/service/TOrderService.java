package com.itheima.service;

import com.itheima.pojo.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pojo.TUser;
import com.itheima.vo.GoodsVo;

public interface TOrderService extends IService<TOrder> {

    TOrder seckill(TUser user, GoodsVo goodsVo);

    Long createSeckillOrder(TUser user, GoodsVo goodsVo);

    Long getSeckillOrderId(Long userId, Long goodsId);
}
