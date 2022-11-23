package com.example.shopping_mall.Service;

import com.example.shopping_mall.entity.Orders;

import java.util.List;
import java.util.Map;

public interface OrdersService {
    /**
     * 下单后，将其放入清单表中
     * @param orders
     * @return
     */
    Integer addOrders(Orders orders);

    /**
     * 更新订单信息
     * @param orders
     * @return
     */
    Integer updateOrders(Orders orders);

    /**
     * 根据oid，查询记录是否存在
     * @param oid
     * @return
     */
    Orders isOrders(Integer oid);

    /**
     * 动态通过state查询订单信息
     * @param state
     * @return
     */
    List<Orders> showPay(Map<String,Object> state);
}
