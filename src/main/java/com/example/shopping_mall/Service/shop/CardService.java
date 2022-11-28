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

    /**
     * 删除部分商品
     * @param uid
     * @param cid
     * @return
     */
    Integer deletePart(Integer uid , Integer cid);

    /**
     * 删除全部
     * @param uid
     * @return
     */
    Integer deleteAll(Integer uid);

    /**
     * 判断该用户是否拥有购物车
     * @param uid
     * @return
     */
    List<ShoppingCard> isCardUser(Integer uid);

}
