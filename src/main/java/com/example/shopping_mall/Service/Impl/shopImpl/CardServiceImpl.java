package com.example.shopping_mall.Service.Impl.shopImpl;

import com.example.shopping_mall.Service.shop.CardService;
import com.example.shopping_mall.entity.ShoppingCard;
import com.example.shopping_mall.mapper.CardMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CardServiceImpl implements CardService {

    @Resource
    CardMapper cardMapper;

    /**
     * 添加购物车
     *
     * @return
     */
    @Override
    public Integer addCard(ShoppingCard card) {
        return cardMapper.addCard(card);
    }

    /**
     * 展示所有商品
     *
     * @param uid
     */
    @Override
    public List<ShoppingCard> getCardByIDAll(Integer uid) {
        return cardMapper.getCardAll(uid);
    }

    /**
     * 展示所有商品
     *
     * @param uid
     * @return Map
     */
    @Override
    public List<Map<String, Object>> getCardAllSum(Integer uid) {
        return cardMapper.getCardAllSum(uid);
    }

    /**
     * 展示所有商品
     *
     * @param cid
     * @return ShoppingCard
     */
    @Override
    public ShoppingCard getCardByIdCid(Integer cid) {
        return cardMapper.getCardAllById(cid);
    }
}
