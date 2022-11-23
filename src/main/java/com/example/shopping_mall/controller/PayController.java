package com.example.shopping_mall.controller;

import com.example.shopping_mall.Service.BankCardService;
import com.example.shopping_mall.Service.OrdersService;
import com.example.shopping_mall.Service.PayService;
import com.example.shopping_mall.entity.Bankcard;
import com.example.shopping_mall.entity.Orders;
import com.example.shopping_mall.entity.Pay;
import com.example.shopping_mall.entity.resp.RestBean;
import com.example.shopping_mall.util.PayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pay")
@Api(description = "支付相关")
public class PayController {

    @Resource
    PayService payService;

    @Resource
    OrdersService ordersService;

    @Autowired
    PayUtil payUtil;

    @Resource
    BankCardService bankCardService;


    @ApiOperation("下单之后跳转到-选择支付方式")
    @RequestMapping("choose-pay")
    public RestBean<List<Pay>> choosePay(){
        List<Pay> pays = payService.payAll();
        if (pays.isEmpty()){
            return new RestBean<>(500,"发生错误");
        }
        return new RestBean<>(200,"请求成功",pays);
    }

    @ApiOperation("确定支付方式")
    @RequestMapping("determine-pay")
    public RestBean<Map<String,Object>> determinePay(
            @ApiParam("支付id") @RequestParam("pid") String pid,
            @ApiParam("用户uid") @RequestParam("uid") String uid,
            @ApiParam("订单oid") @RequestParam("oid") String oid
    ){
        Map<String,Object> map = new HashMap<>();

        /*
          - cid1 转账人账户 [执行取款的逻辑接口]
          - cid2 接受人账户 [执行存款的逻辑接口]
         */
        //根据uid查询银行号信息 -> 用户卡号
        Bankcard bankcard = bankCardService.ByUidQueryBanCard(Integer.valueOf(uid));
        Integer cid = bankcard.getCid();
        Orders orders = ordersService.isOrders(Integer.valueOf(oid));
        if (orders == null){
            return new RestBean<>(402,"没有该订单的信息");
        }

        Integer cid2 = 2112010001; //公转账户

        Pay pay = payService.isPay(Integer.valueOf(pid));
        if (pay == null){
            return new RestBean<>(402,"没有该类型的支付方式");
        }
        map.put("确定的支付信息：",pay);
        if (pid.equals("4")){
            payUtil.application(cid,cid2,orders.getSum());//cid1,cid2,money
        }

        Integer integer = ordersService.updateOrders(
                new Orders()
                        .setOid(Integer.valueOf(oid))
                        .setUid(Integer.valueOf(uid))
                        .setPay(Integer.valueOf(pid))
                        .setState(1)
        );
        if (integer == 0){
            return new RestBean<>(402,"数据请求错误，请重试！");
        }
        Orders orders1 = ordersService.isOrders(Integer.valueOf(oid));
        if (orders1 == null){
            return new RestBean<>(500,"更新错误");
        }
        map.put("订单信息更新：",orders1);
        return new RestBean<>(200,"请求成功",map);
    }

    @ApiOperation("显示付款状态")
    @RequestMapping("/show-payment")
    public RestBean<List<Orders>> showPay(
            @ApiParam("查看的类型") @RequestParam(value = "state",required = false) String state
    ){
        List<Orders> orders = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        if (state == null){
            orders = ordersService.showPay(map);
            if (orders.isEmpty()){
                return new RestBean<>(402,"请求错误");
            }
        }else {
            map.put("state",state);
            orders = ordersService.showPay(map);
            if (orders.isEmpty()){
                return new RestBean<>(402,"请求错误");
            }
        }
        return new RestBean<>(200,"请求成功",orders);
    }
}
