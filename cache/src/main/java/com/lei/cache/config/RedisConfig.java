package com.lei.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Bean//模板对象
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory factory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);//设置要连接的 redis

        //设置 key 的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();//创建一个字符串的序列化方式,所有的数据会被序列为字符串
        redisTemplate.setKeySerializer(stringRedisSerializer);//设置 key 的序列化方式为字符串
        redisTemplate.setHashKeySerializer(stringRedisSerializer);//设置 hash 的 key 序列化方式为 string


        //value 使用 json进行序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);//创建一个解析为 json 的序列化对象
        ObjectMapper objectMapper = new ObjectMapper();//jackson 中是使用ObjectMapper来进行序列化,设置给ObjectMapper
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);//设置所有非 final 修饰的变量都可以被序列化
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);//指定objectmapper

        //设置 value 的序列化方式
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);//设置值的序列化方式为 json
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

}
