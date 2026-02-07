package com.itheima.vo;

import com.itheima.pojo.TGoods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVo extends TGoods {

    private BigDecimal seckillPrice;

    private Integer stockCount;

    private Long startDate;

    private Long endDate;
}
