package com.example.shopping_mall.Service.shop;

import com.example.shopping_mall.entity.Seller;
import com.example.shopping_mall.entity.Shopping;

import java.util.List;

public interface ShopService {

    /**
     * 所有商品展示
     */
    public List<Shopping> shopAll();

    /**
     * 商品分类
     */
    public List<Shopping> shoppingType(String type);

    /**
     * 热度商品
     */
    public List<Shopping> heatShop();

    /**
     * 商品详情
     */
    Shopping productDetails(String sid);
    /**
     * 商品查询
     */
    List<Shopping> getSNameInfo(String nameInfo);
    /**
     * 卖家查询
     */
    Seller getSeller(String mid);

    /**
     * 三级列表的分类查询商品
     * @param type
     * @return
     */
    List<Shopping> getShopTypeAll(Integer type);
}
