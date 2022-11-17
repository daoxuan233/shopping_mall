package com.example.shopping_mall.mapper;

import com.example.shopping_mall.cache.RedisMybatisCache;
import com.example.shopping_mall.entity.Shopping;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 只需要修改缓存实现类implementation为我们的RedisMybatisCache即可
 */
@CacheNamespace(implementation = RedisMybatisCache.class)
@Mapper
public interface ShopMapper {
    @Select("select * from shopping")
    List<Shopping> getShopAll();

    /**
     * 商品分类
     */
    @Select("select * from shopping where shopping.stype like concat('%',#{type},'%')")
    List<Shopping> getShopType(@Param("type") String type);

    /**
     * 商品热度排名
     */
    @Select("SELECT  * FROM shopping ORDER BY heat ASC")
    List<Shopping> heatShop();

    /**
     * 商品修改
     */
    @Update("UPDATE shopping SET sid=#{sid},sname=#{sname},snameinfo=#{snameinfo},sprice=#{sprice},simage=#{simage},seller=#{seller},stime=#{stime},stype=#{stype} where sid=#{sid}")
    Integer ModifyShop(Shopping shopping);

    /**
     * 商品添加
     */
    @Insert("insert into shopping (sname,snameinfo,sprice,simage,seller,stime,stype) value(#{sname},#{snameinfo},#{sprice},#{simage},#{seller},#{stime},#{stype})")
    Integer addShop(Shopping shopping);

    /**
     * 商品详情
     */
    @Select("select * from shopping where sid=#{sid}")
    Shopping productDetails(@Param("sid") String sid);

    /**
     * 商品搜索
     */
    @Select("select * from shopping where snameinfo like concat('%',#{snameinfo},'%')")
    List<Shopping> getShopSNameInfo(@Param("snameinfo") String snameinfo);

}
