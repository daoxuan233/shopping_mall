package com.example.shopping_mall.controller;

import com.example.shopping_mall.Service.BankCardService;
import com.example.shopping_mall.Service.UserService;
import com.example.shopping_mall.entity.Bankcard;
import com.example.shopping_mall.entity.ShoppingUser;
import com.example.shopping_mall.entity.resp.RestBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cart-pay")
@Api(description = "应用内支付相关")
public class BankCardController {
    @Resource
    UserService userService;

    @Resource
    BankCardService bankCardService;

    @ApiOperation("用户申请应用内支付的账号")
    @RequestMapping("request-card")
    public RestBean<Map<String,Object>> RequestCard(
            @ApiParam("用户id") @RequestParam("uid") String uid
    ){
        ShoppingUser userByUid = userService.getUserByUid(uid);
        if (userByUid == null){
            return new RestBean<>(404,"用户未找到");
        }
        Bankcard bankcard = bankCardService.ByUidQueryBanCard(Integer.valueOf(uid));
        if (bankcard != null){
            return new RestBean<>(302,"用户已经存在,请直接使用");

        }
        Bankcard bankcard1 = new Bankcard()
                .setType("金卡")
                .setAccount((double) 0)
                .setUid(Integer.valueOf(uid));
        Integer integer = bankCardService.addBankCard(bankcard1);
        if (integer <= 0){
            return new RestBean<>(402,"信息错误，请重试");
        }
        Integer cid = bankcard1.getCid();
        Map<String,Object> map = new HashMap<>();
        map.put("账号：",cid);
        return new RestBean<>(200,"请求成功，请用户记住唯一标识id",map);
    }















}
