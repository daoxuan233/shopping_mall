package com.example.shopping_mall.util;

import com.example.shopping_mall.Service.BankCardService;
import com.example.shopping_mall.entity.resp.RestBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PayUtil {

    @Resource
    BankCardService bankCardService;

    /**
     * 应用内支付
     * @param money
     * @return
     */
    public RestBean<Double> application(Integer cid1 , Integer cid2 , Double money){
        return bankCardService.judgeCid(cid1, cid2, money);
    }
}
