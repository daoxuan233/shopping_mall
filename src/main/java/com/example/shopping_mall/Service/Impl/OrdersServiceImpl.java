package com.example.shopping_mall.Service.Impl;

import com.example.shopping_mall.Service.OrdersService;
import com.example.shopping_mall.entity.Orders;
import com.example.shopping_mall.entity.log.ShoppingMallLog;
import com.example.shopping_mall.mapper.LogMapper;
import com.example.shopping_mall.mapper.OrdersMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
                    .setLname("前端用户"+orders.getUid())
                    .setType("添加")
                    .setInfo("前端用户 下单,订单内容如下："+orders)
                    .setAffectedinfo("1")
                    .setLtime(new Date())
            );
        }
        return integer;
    }

    /**
     * 更新订单信息
     *
     * @param orders
     * @return
     */
    @Override
    public Integer updateOrders(Orders orders) {
        Integer integer = ordersMapper.updateOrders(orders);
        if (integer != 0){
            logMapper.addLog(new ShoppingMallLog()
                    .setLname("用户："+orders.getUid())
                    .setType("更新")
                    .setInfo("用户:"+orders.getUid()+"，更新了订单信息，"+orders+";\n订单编号："+orders.getOid())
                    .setAffectedinfo("0")
                    .setLtime(new Date())
            );
        }
        return integer;
    }

    /**
     * 根据oid，查询记录是否存在
     *
     * @param oid
     * @return
     */
    @Override
    public Orders isOrders(Integer oid) {
       return ordersMapper.getOrdersByOid(oid);
    }

    /**
     * 动态通过state查询订单信息
     *
     * @param state
     * @return
     */
    @Override
    public List<Orders> showPay(Map<String, Object> state) {
        List<Orders> orders = ordersMapper.selectOrdersInfoWithParam(state);
        if (orders != null){
            logMapper.addLog(new ShoppingMallLog()
                    .setLname("用户：")
                    .setType("动态查询")
                    .setInfo("用户动态查询了："+state)
                    .setAffectedinfo("0")
                    .setLtime(new Date())
            );
        }
        return orders;
    }
}
