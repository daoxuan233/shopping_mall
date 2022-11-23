package com.example.shopping_mall.controller.shopping;

import com.example.shopping_mall.Service.shop.CardService;
import com.example.shopping_mall.Service.shop.ShopService;
import com.example.shopping_mall.entity.Seller;
import com.example.shopping_mall.entity.Shopping;
import com.example.shopping_mall.entity.ShoppingCard;
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
@RequestMapping("/card")
@Api(description = "购物车相关")
public class ShoppingCartController {

    @Resource
    CardService cardService;
    @Resource
    ShopService shopService;

    @ApiOperation("点击添加事件，购物车接口")
    @RequestMapping("/addCard")
    public RestBean<List<ShoppingCard>> addCard(@ApiParam("商品sid") @RequestParam("sid") String sid,
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
        List<ShoppingCard> byIDAll = cardService.getCardByIDAll(Integer.valueOf(uid));
        return new RestBean<>(200,"请求成功",byIDAll);
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
            sum += shoppingCard.gettotalAmount();
        }
        shop.put("当前商品总金额：",sum);
        return new RestBean<>(200, "数据获取正常", shop);
    }

    @ApiOperation("删除购物车中的部分商品")
    @RequestMapping("/delete-Part")
    public RestBean<List<Map<String, Object>>> deletePart(
            @ApiParam("购物车中商品的id") @RequestParam("cid") List<Integer> cid,
            @ApiParam("用户uid") @RequestParam("uid") Integer uid
    ){
        Integer integer1 = null;
        for (Integer integer : cid) {
            integer1 = cardService.deletePart(uid, integer);
        }
        if (integer1 == null) {
            return new RestBean<>(402, "请求错误");
        }
        if (integer1 <= 0){
            return new RestBean<>(500,"错误");
        }
        List<Map<String, Object>> cardAllSum = cardService.getCardAllSum(uid);
        if (cardAllSum == null){
            return new RestBean<>(402,"程序进程错误，请重新请求");
        }
        return new RestBean<>(200,"请求成功",cardAllSum);
    }

    @ApiOperation("删除购物车中的全部商品")
    @RequestMapping("/delete-all")
    public RestBean<Void> deleteAll(
            @ApiParam("用户uid") @RequestParam("uid") String uid
    ){
        List<Map<String, Object>> cardAllSum = cardService.getCardAllSum(Integer.valueOf(uid)); //判断购物车是否存在该用户的信息
        if (cardAllSum == null){
            return new RestBean<>(402,"没有该用户购物车的信息");
        }
        Integer integer = cardService.deleteAll(Integer.valueOf(uid));
        if (integer == 0){
            return new RestBean<>(402,"请求错误");
        }
        return new RestBean<>(200,"删除成功");
    }
}
