package com.example.shopping_mall.mapper;

import com.example.shopping_mall.cache.RedisMybatisCache;
import com.example.shopping_mall.entity.Pay;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 只需要修改缓存实现类implementation为我们的RedisMybatisCache即可
 */
@CacheNamespace(implementation = RedisMybatisCache.class)
@Mapper
public interface PayMapper {
    @Select("select * from pay ")
    List<Pay> payAll();

    @Select("select * from pay where pid=#{pid}")
    Pay getPayByPid(Integer pid);
}
