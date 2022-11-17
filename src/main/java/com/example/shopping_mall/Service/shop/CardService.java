package com.example.shopping_mall.Service.shop;

import com.example.shopping_mall.entity.ShoppingCard;

import java.util.List;
import java.util.Map;

public interface CardService {
    /**
     * 添加购物车
     * @return
     */
    Integer addCard(ShoppingCard card);

    /**
     * 展示所有商品
     */
    List<ShoppingCard> getCardByIDAll(Integer uid);

    /**
     * 展示所有商品
     * @param uid
     * @return Map
     */
    List<Map<String,Object>> getCardAllSum(Integer uid);

    /**
     * 展示所有商品
     * @param cid
     * @return ShoppingCard
     */
    ShoppingCard getCardByIdCid(Integer cid);
}
