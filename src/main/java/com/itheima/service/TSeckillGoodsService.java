package com.itheima.service;

import com.itheima.pojo.TSeckillGoods;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ZhuanZ（无密码）
* @description 针对表【t_seckill_goods】的数据库操作Service
* @createDate 2026-02-07 20:48:13
*/
public interface TSeckillGoodsService extends IService<TSeckillGoods> {

    void setStockCount(int i);
}
