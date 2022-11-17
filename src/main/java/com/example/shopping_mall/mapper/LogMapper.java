package com.example.shopping_mall.mapper;

import com.example.shopping_mall.cache.RedisMybatisCache;
import com.example.shopping_mall.entity.log.ShoppingMallLog;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 只需要修改缓存实现类implementation为我们的RedisMybatisCache即可
 */
@CacheNamespace(implementation = RedisMybatisCache.class)
@Mapper
public interface LogMapper {
    @Insert("insert into systemlog(lname, type, info, affectedinfo, ltime) value(#{lname},#{type},#{info},#{affectedinfo},#{ltime})")
    void addLog(ShoppingMallLog log);
}
