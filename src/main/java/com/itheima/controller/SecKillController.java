package com.itheima.controller;

import com.itheima.pojo.TUser;
import com.itheima.service.IRedisService;
import com.itheima.service.TGoodsService;
import com.itheima.service.TOrderService;
import com.itheima.service.TSeckillOrderService;
import com.itheima.vo.GoodsVo;
import com.itheima.vo.RespBean;
import com.itheima.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/seckill")
@Slf4j
public class SecKillController {

    private static final int RATE_LIMIT_MAX_ATTEMPTS = 5;
    private static final int RATE_LIMIT_EXPIRE_SECONDS = 60;

    @Autowired
    private TGoodsService tGoodsService;
    @Autowired
    private TSeckillOrderService tSeckillOrderService;
    @Autowired
    private TOrderService tOrderService;
    @Autowired
    private IRedisService redisService;

    @RequestMapping("/doseckill/{goodsId}")
    @ResponseBody
    public RespBean doseckill(TUser user, @PathVariable("goodsId") Long goodsId) {
        if (user == null) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }

        if (!redisService.tryRateLimit(user.getId(), goodsId, RATE_LIMIT_MAX_ATTEMPTS, RATE_LIMIT_EXPIRE_SECONDS)) {
            return RespBean.error(RespBeanEnum.ERROR, "请求过于频繁，请稍后再试");
        }

        GoodsVo goodsVo = tGoodsService.findGoodsVoById(goodsId);
        if (goodsVo == null) {
            return RespBean.error(RespBeanEnum.ERROR, "商品不存在");
        }

        if (redisService.hasUserSeckillOrder(user.getId(), goodsId)) {
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }

        Integer stockCount = redisService.getStockCount(goodsId);
        if (stockCount == null) {
            stockCount = goodsVo.getStockCount();
            redisService.setStockCount(goodsId, stockCount);
        }

        if (stockCount <= 0) {
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }

        boolean success = redisService.decrementStock(goodsId);
        if (!success) {
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }

        try {
            Long orderId = tOrderService.createSeckillOrder(user, goodsVo);
            redisService.setUserSeckillOrder(user.getId(), goodsId, orderId);
            return RespBean.success(orderId);
        } catch (Exception e) {
            redisService.incrementStock(goodsId);
            log.error("秒杀下单失败: userId={}, goodsId={}, error={}", user.getId(), goodsId, e.getMessage());
            return RespBean.error(RespBeanEnum.ERROR, "秒杀失败，请重试");
        }
    }

    @RequestMapping("/result/{goodsId}")
    @ResponseBody
    public RespBean getResult(TUser user, @PathVariable("goodsId") Long goodsId) {
        if (user == null) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        Long orderId = tOrderService.getSeckillOrderId(user.getId(), goodsId);
        if (orderId != null) {
            return RespBean.success(orderId);
        }
        return RespBean.error(RespBeanEnum.ERROR, "未查询到订单");
    }
}
