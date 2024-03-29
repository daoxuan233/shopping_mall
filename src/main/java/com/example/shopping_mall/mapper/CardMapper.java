package com.example.shopping_mall.mapper;

import com.example.shopping_mall.cache.RedisMybatisCache;
import com.example.shopping_mall.entity.ShoppingCard;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 只需要修改缓存实现类implementation为我们的RedisMybatisCache即可
 */
@CacheNamespace(implementation = RedisMybatisCache.class)
@Mapper
public interface CardMapper {
    /**
     * 添加到购物车
     * @param card
     * @return
     */
    @Insert("insert into shoppingcard (sid,number,uid,totalAmount) value(#{sid},#{number},#{uid},#{totalAmount})")
    Integer addCard(ShoppingCard card);

    /**
     * 更新购物车内容
     * @param card
     * @return
     */
    @Update("UPDATE shoppingcard SET cid=#{cid},sid=#{sid},number=#{number} where cid=#{cid}")
    Integer updateCard(ShoppingCard card);

    /**
     * 展示所有商品
     * @param uid
     * @return List
     */
    @Select("select * from shoppingcard where uid=#{uid}")
    List<ShoppingCard> getCardAll(Integer uid);

    /**
     * 展示所有商品
     * @param uid
     * @return Map
     */
    @Select("select * from shoppingcard where uid=#{uid}")
    List<Map<String,Object>> getCardAllSum(Integer uid);

    /**
     * 展示所有商品
     * @param cid
     * @return ShoppingCard
     */
    @Select("select * from shoppingcard where cid=#{cid}")
    ShoppingCard getCardAllById (Integer cid);

    /**
     * 根据cid进行指定删除
     * @param cid
     * @return
     */
    @Delete("delete from shoppingcard where cid=#{cid}")
    Integer deleteCardOid(@Param("cid") Integer cid);

    /**
     * 根据uid，删除所有
     * @param uid
     * @return
     */
    @Delete("delete  from shoppingcard where uid = #{uid}")
    Integer deleteCardAll(Integer uid);
}
