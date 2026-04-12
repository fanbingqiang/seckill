package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mapper.TOrderMapper;
import com.itheima.pojo.TOrder;
import com.itheima.pojo.TSeckillGoods;
import com.itheima.pojo.TSeckillOrder;
import com.itheima.pojo.TUser;
import com.itheima.service.IRedisService;
import com.itheima.service.TOrderService;
import com.itheima.service.TSeckillGoodsService;
import com.itheima.service.TSeckillOrderService;
import com.itheima.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder>
        implements TOrderService {

    @Autowired
    private TSeckillGoodsService seckillGoodsService;
    @Autowired
    private TSeckillOrderService seckillOrderService;
    @Autowired
    private IRedisService redisService;

    @Override
    public TOrder seckill(TUser user, GoodsVo goodsVo) {
        TSeckillGoods seckillGoods = seckillGoodsService.getOne(
                new QueryWrapper<TSeckillGoods>().eq("goods_id", goodsVo.getId())
        );

        if (seckillGoods == null || seckillGoods.getStockCount() <= 0) {
            throw new RuntimeException("秒杀商品库存不足");
        }

        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        boolean updateResult = seckillGoodsService.updateById(seckillGoods);
        if (!updateResult) {
            throw new RuntimeException("库存扣减失败");
        }

        TOrder order = new TOrder();
        order.setUserId(user.getId());
        order.setGoodsId(goodsVo.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(goodsVo.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());

        boolean saveResult = this.save(order);
        if (!saveResult) {
            throw new RuntimeException("订单创建失败");
        }

        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSeckillOrder(TUser user, GoodsVo goodsVo) {
        TSeckillGoods seckillGoods = seckillGoodsService.getOne(
                new QueryWrapper<TSeckillGoods>().eq("goods_id", goodsVo.getId())
        );

        if (seckillGoods == null || seckillGoods.getStockCount() <= 0) {
            throw new RuntimeException("秒杀商品库存不足");
        }

        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        seckillGoodsService.updateById(seckillGoods);

        TOrder order = new TOrder();
        order.setUserId(user.getId());
        order.setGoodsId(goodsVo.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(goodsVo.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        this.save(order);

        TSeckillOrder seckillOrder = new TSeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setGoodsId(goodsVo.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrderService.save(seckillOrder);

        return order.getId();
    }

    @Override
    public Long getSeckillOrderId(Long userId, Long goodsId) {
        TSeckillOrder seckillOrder = seckillOrderService.getOne(
                new QueryWrapper<TSeckillOrder>()
                        .eq("user_id", userId)
                        .eq("goods_id", goodsId)
        );
        return seckillOrder != null ? seckillOrder.getOrderId() : null;
    }
}
