package com.example.shopping_mall.mapper;

import com.example.shopping_mall.cache.RedisMybatisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 只需要修改缓存实现类implementation为我们的RedisMybatisCache即可
 */
@CacheNamespace(implementation = RedisMybatisCache.class)
@Mapper
public interface OrdersMapper {
    /**
     * 添加订单
     * @param orders
     * @return
     */
    @Insert("insert into orders(oinfo,uid,sid,addressid,pay,state) value(#{oinfo},#{uid},#{sid},#{addressid},#{pay},#{state})")
    Integer addOrders(Orders orders);
}
