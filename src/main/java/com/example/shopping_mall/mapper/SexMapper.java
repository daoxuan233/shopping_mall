package com.example.shopping_mall.mapper;

import com.example.shopping_mall.cache.RedisMybatisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 只需要修改缓存实现类implementation为我们的RedisMybatisCache即可
 */
@CacheNamespace(implementation = RedisMybatisCache.class)
@Mapper
public interface SexMapper {
    @Select("select xinfo from sex where xid=#{xid}")
    String sex(Integer xid);
}
