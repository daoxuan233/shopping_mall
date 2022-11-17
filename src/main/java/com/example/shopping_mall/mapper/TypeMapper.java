package com.example.shopping_mall.mapper;

import com.example.shopping_mall.cache.RedisMybatisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@CacheNamespace(implementation = RedisMybatisCache.class)
@Mapper
public interface TypeMapper {

    @Select("select * from type where level = #{level} and prentId = #{id}")
    public List<Map<String,Object>> findAll(Integer level, Integer id);

}
