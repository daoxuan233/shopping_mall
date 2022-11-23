package com.example.shopping_mall.Service.Impl;

import com.example.shopping_mall.Service.PayService;
import com.example.shopping_mall.entity.Pay;
import com.example.shopping_mall.mapper.PayMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PayServiceImpl implements PayService {
    @Resource
    PayMapper payMapper;
    /**
     * 查询所有支付方式
     *
     * @return
     */
    @Override
    public List<Pay> payAll() {
        return payMapper.payAll();
    }

    /**
     * 通过id查询是否存在该支付方式
     *
     * @param pid
     * @return
     */
    @Override
    public Pay isPay(Integer pid) {
       return payMapper.getPayByPid(pid);
    }
}
