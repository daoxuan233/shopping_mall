package com.example.shopping_mall.mapper;

import com.example.shopping_mall.cache.RedisMybatisCache;
import com.example.shopping_mall.entity.Orders;
import com.example.shopping_mall.provider.OrdersDynaSqlProviderBuilder;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

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
    @Insert("insert into orders(oinfo,uid,sid,sum,addressid,pay,state) value(#{oinfo},#{uid},#{sid},#{sum},#{addressid},#{pay},#{state})")
    Integer addOrders(Orders orders);

    /**
     * 修改订单情况
     * @param orders
     * @return
     */
    @Update("UPDATE orders SET pay=#{pay} , state =#{state} where oid=#{oid} and oid=#{oid}")
    Integer updateOrders(Orders orders);

    /**
     * 根据oid，查询orders
     * @param oid
     * @return
     */
    @Select("select * from orders where oid=#{oid}")
    Orders getOrdersByOid(Integer oid);

    @SelectProvider(type = OrdersDynaSqlProviderBuilder.class , method = "buildSelectOrdersWithParam")
    public List<Orders> selectOrdersInfoWithParam(Map<String,Object> param);
}
