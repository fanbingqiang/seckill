package com.itheima.mapper;

import com.itheima.pojo.TGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.vo.GoodsVo;

import java.util.List;

/**
* @author ZhuanZ（无密码）
* @description 针对表【t_goods】的数据库操作Mapper
* @createDate 2026-02-07 20:47:55
* @Entity com.itheima.pojo.TGoods
*/
public interface TGoodsMapper extends BaseMapper<TGoods> {

    List<GoodsVo> findGoodsVo();


    GoodsVo findGoodsVoById(Long goodsId);
}




