package com.example.shopping_mall.controller.shopping;

import com.example.shopping_mall.Service.AddressService;
import com.example.shopping_mall.Service.OrdersService;
import com.example.shopping_mall.Service.UserService;
import com.example.shopping_mall.Service.shop.CardService;
import com.example.shopping_mall.Service.shop.ShopService;
import com.example.shopping_mall.entity.*;
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

import static java.lang.Double.parseDouble;

@RestController
@RequestMapping("/api/card")
@Api(description = "购物车相关")
public class ShoppingCartController {

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

    @ApiOperation("点击添加事件，购物车接口")
    @RequestMapping("/addCard")
    public RestBean<Void> addCard(@ApiParam("商品sid") @RequestParam("sid") String sid,
                                  @ApiParam("商品数量") @RequestParam("number") String number,
                                  @ApiParam("当前登录的用户") @RequestParam("uid") String uid
    ){
        Shopping shopping = shopService.productDetails(sid); //查询商品
        if (shopping == null){
            return new RestBean<>(402,"请求失败，请检查商品id");
        }
        String seller = shopping.getSeller();
        Seller sellers = shopService.getSeller(seller);
        if (sellers == null){
            return new RestBean<>(402,"请求错误，请检查商品id");
        }
        Double sprice = shopping.getSprice();
        Double sum = sprice* parseDouble(number);
        Integer card = cardService.addCard(
                new ShoppingCard()
                        .setUid(Integer.valueOf(uid))
                        .setSid(Integer.valueOf(sid))
                        .setNumber(Integer.valueOf(number))
                        .setTotalamount(sum)
        );
        if (card == 0){
            return new RestBean<>(402,"数据添加失败");
        }
        return new RestBean<>(200,"请求成功");
    }

    @ApiOperation("查看购物车")
    @RequestMapping("/showCard")
    public RestBean<List<ShoppingCard>> showCard(
            @ApiParam("当前用户的Id") @RequestParam("uid") String uid
    ){
        List<ShoppingCard> byIDAll = cardService.getCardByIDAll(Integer.valueOf(uid));
        if (byIDAll == null){
            return new RestBean<>(402,"请求错误，请检查");
        }
        return new RestBean<>(200,"请求成功",byIDAll);
    }

    @ApiOperation("【Ajax】显示该用户的购物车金额/全部and部分")
    @RequestMapping("/sumCard")
    public RestBean<Map<String,Object>> sumCard(
            @ApiParam("购物车中商品的id") @RequestParam("cid") List<Integer> cid
    ){
        Map<String,Object> shop = new HashMap<>();
        List<ShoppingCard> shoppingCards = new ArrayList<>();
        for (Integer integer : cid){
            ShoppingCard shoppingCard = cardService.getCardByIdCid(integer);

            if (shoppingCard != null){
                shoppingCards.add(shoppingCard);
            }
        }

        if (!shoppingCards.isEmpty()){
            shop.put("carList",shoppingCards);
        }else {
            return new RestBean<>(402,"没有数据");
        }

        double sum = 0.0;
        for (ShoppingCard shoppingCard : shoppingCards){
            sum += shoppingCard.getTotalamount();
        }
        shop.put("当前商品总金额：",sum);
        return new RestBean<>(200, "数据获取正常", shop);
    }

    @ApiOperation("下单接口")
    @RequestMapping("place-the-orders")
    public RestBean<Orders> placeOrders(
            @ApiParam("用户id") @RequestParam("uid") String uid,
            @ApiParam("购物车中商品的id") @RequestParam("cid") List<Integer> cid
    ){
        //查询该用户是否有地址记录,true则返回该用户所有信息
        List<Address> addressByUidInfo = addressService.getAddressByUidInfo(Integer.valueOf(uid)); // 通过uid获取地址表中的所有数据
        if (addressByUidInfo == null){
            return new RestBean<>(402,"请求错误，请检查用户id是否正确");
        }
        Map<String,Object> shop = new HashMap<>();
        Map<String,Integer> sid = new HashMap<>();
        List<ShoppingCard> shoppingCards = new ArrayList<>();
        for (Integer integer : cid){
            ShoppingCard shoppingCard = cardService.getCardByIdCid(integer);

            if (shoppingCard != null){
                shoppingCards.add(shoppingCard);
                sid.put("sid",shoppingCard.getSid());
            }
        }

        if (!shoppingCards.isEmpty()){
            shop.put("carList",shoppingCards);
        }else {
            return new RestBean<>(402,"没有数据");
        }

        double sum = 0.0;
        for (ShoppingCard shoppingCard : shoppingCards){
            sum += shoppingCard.getTotalamount();
        }
        shop.put("当前商品总金额：",sum);



        return new RestBean<>(402,"请求错误");
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
