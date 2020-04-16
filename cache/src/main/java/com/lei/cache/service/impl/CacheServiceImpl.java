package com.lei.cache.service.impl;

import com.lei.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean save2Redis(String key, String value, long expireTime) throws Exception {
        redisTemplate.opsForValue().set(key, value);
        if (expireTime > 0) {
            return redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);//设置有效期,单位是毫秒
        }
        return false;
    }

    @Override
    public String getFromRedis(String key) throws Exception {
        return (String) redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean deleteKey(String key) throws Exception {
        return redisTemplate.delete(key);
    }

    @Override
    public boolean expire(String key, long expireTime) throws Exception {

        return redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public Map<Object, Object> hGetAll(String key) throws Exception {
        return redisTemplate.opsForHash().entries(key);
    }


}
