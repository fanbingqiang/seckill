package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.pojo.TSeckillGoods;
import com.itheima.service.TSeckillGoodsService;
import com.itheima.mapper.TSeckillGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TSeckillGoodsServiceImpl extends ServiceImpl<TSeckillGoodsMapper, TSeckillGoods>
        implements TSeckillGoodsService {

    @Autowired
    private TSeckillGoodsMapper seckillGoodsMapper;

    @Override
    public void setStockCount(int stockCount) {
        TSeckillGoods seckillGoods = this.getOne(
                new QueryWrapper<TSeckillGoods>().last("LIMIT 1")
        );
        if (seckillGoods != null) {
            seckillGoods.setStockCount(stockCount);
            this.updateById(seckillGoods);
        }
    }
}
