package com.example.shopping_mall.Service;

public interface OrdersService {
    /**
     * 下单后，将其放入清单表中
     * @param orders
     * @return
     */
    Integer addOrders(Orders orders);
}
