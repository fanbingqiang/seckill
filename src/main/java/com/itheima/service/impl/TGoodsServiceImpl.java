package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.pojo.TGoods;
import com.itheima.service.TGoodsService;
import com.itheima.mapper.TGoodsMapper;
import com.itheima.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ZhuanZ（无密码）
* @description 针对表【t_goods】的数据库操作Service实现
* @createDate 2026-02-07 20:47:55
*/
@Service
public class TGoodsServiceImpl extends ServiceImpl<TGoodsMapper, TGoods>
    implements TGoodsService{

    @Autowired
    private TGoodsMapper tGoodsMapper;
    @Autowired
    private TGoodsMapper goodsMapper;

    @Override
    public List<GoodsVo> findGoodsVo() {
        return tGoodsMapper.findGoodsVo();
    }

    @Override
    public GoodsVo findGoodsVoById(Long GoodsId) {
        return goodsMapper.findGoodsVoById(GoodsId);
    }
}




