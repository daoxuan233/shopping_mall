package com.example.shopping_mall.Service;

import com.example.shopping_mall.entity.Pay;

import java.util.List;

public interface PayService {
    /**
     * 查询所有支付方式
     * @return
     */
    List<Pay> payAll();

    /**
     * 通过id查询是否存在该支付方式
     * @param pid
     * @return
     */
    Pay isPay(Integer pid);
}
