package com.itheima.service;

import com.itheima.pojo.TGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.vo.GoodsVo;

import java.util.List;

/**
* @author ZhuanZ（无密码）
* @description 针对表【t_goods】的数据库操作Service
* @createDate 2026-02-07 20:47:55
*/
public interface TGoodsService extends IService<TGoods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoById(Long GoodsId);
}
