package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mapper.TOrderMapper;
import com.itheima.pojo.TOrder;
import com.itheima.pojo.TSeckillGoods;
import com.itheima.pojo.TUser;
import com.itheima.service.TOrderService;
import com.itheima.service.TSeckillGoodsService;
import com.itheima.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author ZhuanZ（无密码）
 * @description 针对表【t_order】的数据库操作Service实现
 * @createDate 2026-02-07 20:48:03
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder>
        implements TOrderService {

    @Autowired
    private TSeckillGoodsService seckillGoodsService;

    /**
     * 秒杀商品购买逻辑
     *
     * @param user    用户信息
     * @param goodsVo 商品信息
     * @return 创建的订单对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TOrder seckill(TUser user, GoodsVo goodsVo) {
        // 1. 查询秒杀商品库存
        TSeckillGoods seckillGoods = seckillGoodsService.getOne(
                new QueryWrapper<TSeckillGoods>().eq("goods_id", goodsVo.getId())
        );

        // 2. 检查库存是否充足
        if (seckillGoods == null || seckillGoods.getStockCount() <= 0) {
            throw new RuntimeException("秒杀商品库存不足");
        }

        // 3. 扣减库存
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        boolean updateResult = seckillGoodsService.updateById(seckillGoods);
        if (!updateResult) {
            throw new RuntimeException("库存扣减失败");
        }

        // 4. 创建订单
        TOrder order = new TOrder();
        order.setUserId(user.getId());
        order.setGoodsId(goodsVo.getId());
        order.setDeliveryAddrId(0L); // 默认地址ID
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsCount(1); // 固定购买数量为1
        order.setGoodsPrice(goodsVo.getSeckillPrice());
        order.setOrderChannel(1); // 订单渠道：PC端
        order.setStatus(0); // 订单状态：未支付
        order.setCreateDate(new Date());

        // 5. 保存订单到数据库
        boolean saveResult = this.save(order);
        if (!saveResult) {
            throw new RuntimeException("订单创建失败");
        }

        return order;
    }
}
