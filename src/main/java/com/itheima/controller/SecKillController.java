package com.itheima.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.pojo.TOrder;
import com.itheima.pojo.TSeckillOrder;
import com.itheima.pojo.TUser;
import com.itheima.pojo.TUser;
import com.itheima.service.TGoodsService;
import com.itheima.service.TOrderService;
import com.itheima.service.TSeckillOrderService;
import com.itheima.vo.GoodsVo;
import com.itheima.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seckill")
public class SecKillController { // 修正类名拼写错误
    @Autowired
    private TGoodsService tGoodsService;
    @Autowired
    private TSeckillOrderService tSeckillOrderService;
    @Autowired
    private TOrderService tOrderService;

    @RequestMapping("/doseckill")
    public String doseckill(Model model, TUser user, @PathVariable Long goodsId) { // 参数名统一为小写
        if (user == null) {
            return "login";
        }
        model.addAttribute("user", user);

        GoodsVo goodsVo = tGoodsService.findGoodsVoById(goodsId);
        if (goodsVo.getStockCount() < 1) {
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "seckillFail";
        }

        // 使用 QueryWrapper 构建查询条件
        QueryWrapper<TSeckillOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId()).eq("goods_id", goodsId);

        TSeckillOrder seckillOrder = tSeckillOrderService.getOne(queryWrapper);
        if (seckillOrder != null) {
            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
            return "seckillFail";
        }
        TOrder order =tOrderService.seckill(user, goodsVo);
        model.addAttribute("order", order);
        model.addAttribute("goods", goodsVo);
        model.addAttribute("user", user);
        return "orderDetail";
    }
}
