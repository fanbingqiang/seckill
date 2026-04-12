package com.itheima.service;

public interface IRedisService {
    void setStockCount(Long goodsId, Integer stockCount);

    Integer getStockCount(Long goodsId);

    boolean decrementStock(Long goodsId);

    void incrementStock(Long goodsId);

    boolean tryRateLimit(Long userId, Long goodsId, int maxAttempts, int expireSeconds);

    void setUserSeckillOrder(Long userId, Long goodsId, Long orderId);

    boolean hasUserSeckillOrder(Long userId, Long goodsId);
}
