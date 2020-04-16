package com.lei.cache.service;

import java.util.Map;
import java.util.Set;

public interface CacheService {
    /**
     * 保存数据到 redis
     * @param key
     * @param value
     * @param expireTime
     * @return
     * @throws Exception
     */
    boolean save2Redis(String key, String value, long expireTime) throws Exception;

    /**
     * 从 redis 中获取数据
     * @param key
     * @return
     * @throws Exception
     */
    String getFromRedis(String key) throws Exception;

    /**
     * 删除一个 key
     * @param key
     * @return
     * @throws Exception
     */
    boolean deleteKey(String key) throws Exception;

    /**
     * 给指定的 key 设置过期时间
     * @param key
     * @param expireTime
     * @return
     * @throws Exception
     */
    boolean expire(String key, long expireTime) throws Exception;

    /**
     * 获取 hash 中所有的数据
     * @param key
     * @return
     * @throws Exception
     */
    Map<Object, Object> hGetAll(String key) throws Exception;

}
