package com.example.shopping_mall.Service.Impl.shopImpl;

import com.example.shopping_mall.Service.shop.ShopService;
import com.example.shopping_mall.entity.Seller;
import com.example.shopping_mall.entity.Shopping;
import com.example.shopping_mall.entity.log.ShoppingMallLog;
import com.example.shopping_mall.mapper.LogMapper;
import com.example.shopping_mall.mapper.SellerMapper;
import com.example.shopping_mall.mapper.ShopMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Resource
    ShopMapper shopMapper;

    @Resource
    SellerMapper sellerMapper;

    @Resource
    LogMapper logMapper;

    /**
     * 所有商品展示
     */
    @Override
    public List<Shopping> shopAll() {
        return shopMapper.getShopAll();
    }

    /**
     * 商品分类
     *
     * @param type
     */
    @Override
    public List<Shopping> shoppingType(String type) {
        return shopMapper.getShopType(type);
    }

    /**
     * 热度商品
     */
    @Override
    public List<Shopping> heatShop() {
        return shopMapper.heatShop();
    }

    /**
     * 商品详情
     */
    @Override
    public Shopping productDetails(String sid) {
        return shopMapper.productDetails(sid);
    }

    /**
     * 商品查询
     *
     * @param nameInfo
     */
    @Override
    public List<Shopping> getSNameInfo(String nameInfo) {
        return shopMapper.getShopSNameInfo(nameInfo);
    }

    /**
     * 卖家查询
     *
     * @param mid
     */
    @Override
    public Seller getSeller(String mid) {
        Seller sellerById = sellerMapper.getSellerById(mid);
        if (sellerById != null){
            logMapper.addLog(new ShoppingMallLog()
                    .setLname("前端用户")
                    .setType("查询")
                    .setInfo("前端用户查询了商品的卖家"+mid+"[卖家姓名：+"+sellerById.getMname()+"]")
                    .setAffectedinfo("0")
                    .setLtime(new Date())
            );
        }
        return sellerById;
    }

    /**
     * 三级列表的分类查询商品
     *
     * @param type
     * @return
     */
    @Override
    public List<Shopping> getShopTypeAll(Integer type) {
        List<Shopping> shopType = shopMapper.getShopTypeAll(type);
        if (!shopType.isEmpty()){
            logMapper.addLog(new ShoppingMallLog()
                    .setLname("前端用户")
                    .setType("查询")
                    .setInfo("前端用户查询了商品的三级列表的分类,类型id为："+type)
                    .setAffectedinfo("0")
                    .setLtime(new Date())
            );
        }
        return shopType;
    }

    /**
     * 热度增加
     *
     * @param sid
     * @return
     */
    @Override
    public Integer increaseHeat(Integer sid , Integer heat) {
        return shopMapper.increaseHeat(sid,heat);
    }

}
