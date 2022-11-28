package com.example.shopping_mall.controller;

import com.example.shopping_mall.Service.AddressService;
import com.example.shopping_mall.Service.OrdersService;
import com.example.shopping_mall.Service.UserService;
import com.example.shopping_mall.Service.shop.CardService;
import com.example.shopping_mall.Service.shop.ShopService;
import com.example.shopping_mall.entity.Address;
import com.example.shopping_mall.entity.Orders;
import com.example.shopping_mall.entity.ShoppingCard;
import com.example.shopping_mall.entity.ShoppingUser;
import com.example.shopping_mall.entity.resp.RestBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@Api(description = "订单相关")
public class OrdersController {
    @Resource
    CardService cardService;
    @Resource
    ShopService shopService;
    @Resource
    OrdersService ordersService;
    @Resource
    AddressService addressService;
    @Resource
    UserService userService;

    @ApiOperation("下单接口")
    @RequestMapping("place-the-orders")
    public RestBean<Map<String,Object>> placeOrders(
            @ApiParam("用户id") @RequestParam("uid") String uid,
            @ApiParam("购物车中商品的id") @RequestParam("cid") List<Integer> cid,
            @ApiParam("备注信息") @RequestParam(value = "oinfo",required = false) String oinfo,
            @ApiParam("地址id") @RequestParam("addressid") Integer addressid
    ){
        Map<String,Object> shop = new HashMap<>();
        String SQLSid = "";
        List<String> sid = new ArrayList<>();
        List<ShoppingCard> shoppingCards = new ArrayList<>();
        for (Integer integer : cid){
            ShoppingCard shoppingCard = cardService.getCardByIdCid(integer);
            if (shoppingCard != null){
                shoppingCards.add(shoppingCard);
                sid.add(String.valueOf(shoppingCard.getSid()));
            }
        }
        // -----------------------------------
        // 只要不为空，就在后面添加逗号，最后再用subString（）函数截取，去掉最后一位的逗号
        for (String object : sid) {
            if (object != null) {
                SQLSid += object + ",";
            }
        }
        /* if (SQLSid.length() > 0) {
            SQLSid = SQLSid.substring(0, SQLSid.length() - 1);
        }*/
        // -----------------------------------
        if (!shoppingCards.isEmpty()){
            shop.put("carList",shoppingCards);
        }else {
            return new RestBean<>(402,"没有数据");
        }
        if (!sid.isEmpty()){
            shop.put("sid",SQLSid);
        }else {
            return new RestBean<>(402,"没有该商品数据");
        }

        double sum = 0.0;
        for (ShoppingCard shoppingCard : shoppingCards){
            sum += shoppingCard.gettotalAmount();
        }
        shop.put("当前商品总金额：",sum);
        Integer integerOrders = ordersService.addOrders(
                new Orders()
                        .setOinfo(oinfo)
                        .setUid(Integer.valueOf(uid))
                        .setSid(SQLSid)
                        .setSum(sum)
                        .setAddressid(addressid)
                        .setState(0)
        );
        if (integerOrders == null){
            return new RestBean<>(402,"请求失败，数据添加错误");
        }
        Address addressByAid = addressService.getAddressByAid(addressid);
        shop.put("用户地址：",addressByAid.getAddress());
        Integer integer1 = null;
        for (Integer integer : cid) {
            integer1 = cardService.deletePart(integer, Integer.valueOf(uid));
        }
        if (integer1 == null){
            return new RestBean<>(403,"删除购物车数据错误");
        }
        return new RestBean<>(200, "数据获取正常",shop);
    }


    @ApiOperation("下单接口前置-请求用户是否有地址")
    @RequestMapping("place-is-address")
    public RestBean<List<Address>> placeAddress(@ApiParam("用户id") @RequestParam("uid") String uid){
        List<Address> addressByUidInfo = addressService.getAddressByUidInfo(Integer.valueOf(uid));
        if (addressByUidInfo == null){
            return new RestBean<>(402,"用户没有在本平台使用的地址，请提示用户添加地址");
        }
        return new RestBean<>(200,"查询成功，地址返回中……",addressByUidInfo);
    }

    @ApiOperation("下单接口前置-没有地址，添加地址")
    @RequestMapping("place-address")
    public RestBean<Void> addAddress(
            @ApiParam("用户id") @RequestParam("uid") String uid,
            @ApiParam("地址") @RequestParam("address") String address
    ){
        ShoppingUser userByUid = userService.getUserByUid(uid); // 获取uid信息
        if (userByUid == null){
            return new RestBean<>(402,"没有该用户id");
        }
        Integer integer = addressService.addAddress(
                new Address()
                        .setUid(Integer.valueOf(uid))
                        .setName(userByUid.getUname())
                        .setAddress(address)
                        .setNamephone(userByUid.getPhone())
        );
        if (integer == 0){
            return new RestBean<>(402,"信息错误");
        }
        return new RestBean<>(200,"请求成功,信息已添加");
    }

    @ApiOperation("下单接口前置-获取地址")
    @RequestMapping("place-get-address")
    public RestBean<Map<String,String>> placeOrder(
            @ApiParam("用户id") @RequestParam("uid") String uid
    ){
        Map<String, String> addressByUId = addressService.getAddressByUId(Integer.valueOf(uid));
        if (addressByUId == null) {
            return new RestBean<>(402, "请求错误");
        }
        return new RestBean<>(200,"请求成功",addressByUId);
    }
}
