package com.itheima.service.impl;

import com.itheima.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements IRedisService {

    private static final String STOCK_KEY_PREFIX = "seckill:stock:";
    private static final String USER_ORDER_KEY_PREFIX = "seckill:order:";
    private static final String RATE_LIMIT_KEY_PREFIX = "seckill:ratelimit:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setStockCount(Long goodsId, Integer stockCount) {
        redisTemplate.opsForValue().set(STOCK_KEY_PREFIX + goodsId, stockCount);
    }

    @Override
    public Integer getStockCount(Long goodsId) {
        Object stock = redisTemplate.opsForValue().get(STOCK_KEY_PREFIX + goodsId);
        if (stock == null) {
            return null;
        }
        return (Integer) stock;
    }

    @Override
    public boolean decrementStock(Long goodsId) {
        String key = STOCK_KEY_PREFIX + goodsId;
        Long result = redisTemplate.opsForValue().decrement(key);
        if (result != null && result >= 0) {
            return true;
        }
        if (result != null && result < 0) {
            redisTemplate.opsForValue().increment(key);
            return false;
        }
        return false;
    }

    @Override
    public void incrementStock(Long goodsId) {
        redisTemplate.opsForValue().increment(STOCK_KEY_PREFIX + goodsId);
    }

    @Override
    public boolean tryRateLimit(Long userId, Long goodsId, int maxAttempts, int expireSeconds) {
        String key = RATE_LIMIT_KEY_PREFIX + userId + ":" + goodsId;
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, 1, expireSeconds, TimeUnit.SECONDS);
        if (Boolean.TRUE.equals(result)) {
            return true;
        }
        Long count = redisTemplate.opsForValue().increment(key);
        if (count != null && count <= maxAttempts) {
            return true;
        }
        return false;
    }

    @Override
    public void setUserSeckillOrder(Long userId, Long goodsId, Long orderId) {
        String key = USER_ORDER_KEY_PREFIX + userId + ":" + goodsId;
        redisTemplate.opsForValue().set(key, orderId);
    }

    @Override
    public boolean hasUserSeckillOrder(Long userId, Long goodsId) {
        String key = USER_ORDER_KEY_PREFIX + userId + ":" + goodsId;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
