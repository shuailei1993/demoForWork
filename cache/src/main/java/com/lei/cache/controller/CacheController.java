package com.lei.cache.controller;

import com.lei.cache.exception.RedisException;
import com.lei.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    private void checkNull(String source) {
        if (StringUtils.isEmpty(source)) {
            throw new RedisException("内容为空", "404");
        }
    }


    @PostMapping("/set")
    public boolean save2Cache(String key, String value,long expiretime) {
        checkNull(key);
        checkNull(value);

        try {
            return cacheService.save2Redis(key, value, expiretime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("/get")
    public String getFromRedis(String key) throws Exception {
        checkNull(key);
        return cacheService.getFromRedis(key);
    }

    @PostMapping("/delete")
    public  boolean deleteKey(String key) throws Exception {
        checkNull(key);
        return cacheService.deleteKey(key);
    }

    @PostMapping("/expire")
    public  boolean expire(String key,long expireTime) throws Exception {
        checkNull(key);
        return cacheService.expire(key, expireTime);
    }

    @GetMapping("/hgetall")
    public Map<Object, Object> hGetAll(String key) throws Exception {
        checkNull(key);
        return cacheService.hGetAll(key);
    }







}
