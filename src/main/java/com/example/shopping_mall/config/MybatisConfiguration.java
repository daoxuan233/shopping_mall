package com.example.shopping_mall.config;

import com.example.shopping_mall.cache.RedisMybatisCache;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
public class MybatisConfiguration {
    @Resource
    RedisTemplate<Object,Object> redisTemplate;

    @PostConstruct
    public void init(){
        RedisMybatisCache.setTemplate(redisTemplate);
    }
}
