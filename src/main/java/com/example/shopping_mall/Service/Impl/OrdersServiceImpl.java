package com.example.shopping_mall.Service.Impl;

import com.example.shopping_mall.Service.OrdersService;
import com.example.shopping_mall.entity.log.ShoppingMallLog;
import com.example.shopping_mall.mapper.LogMapper;
import com.example.shopping_mall.mapper.OrdersMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
@Service
public class OrdersServiceImpl implements OrdersService {
    @Resource
    OrdersMapper ordersMapper;

    @Resource
    LogMapper logMapper;
    /**
     * 下单后，将其放入清单表中
     *
     * @param orders
     * @return
     */
    @Override
    public Integer addOrders(Orders orders) {
        Integer integer = ordersMapper.addOrders(orders);
        if (integer != 0){
            logMapper.addLog(new ShoppingMallLog()
                    .setLname("前端用户")
                    .setType("添加")
                    .setInfo("前端用户 下单,订单内容如下："+orders)
                    .setAffectedinfo("1")
                    .setLtime(new Date())
            );
        }
        return integer;
    }
}
