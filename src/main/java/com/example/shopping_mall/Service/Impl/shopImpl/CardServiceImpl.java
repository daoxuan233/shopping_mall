package com.example.shopping_mall.Service.Impl.shopImpl;

import com.example.shopping_mall.Service.shop.CardService;
import com.example.shopping_mall.entity.ShoppingCard;
import com.example.shopping_mall.entity.log.ShoppingMallLog;
import com.example.shopping_mall.mapper.CardMapper;
import com.example.shopping_mall.mapper.LogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CardServiceImpl implements CardService {

    @Resource
    CardMapper cardMapper;

    @Resource
    LogMapper logMapper;

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

    /**
     * 删除部分商品
     *
     * @param uid
     * @param cid
     * @return
     */
    @Override
    public Integer deletePart(Integer uid, Integer cid) {
        Integer integer = cardMapper.deleteCardOid(cid);
        if (integer != 0){
            logMapper.addLog(new ShoppingMallLog()
                    .setLname("用户："+uid)
                    .setType("删除")
                    .setInfo("用户："+uid+"，删除记录为："+cid+"的记录")
                    .setAffectedinfo("1")
                    .setLtime(new Date())
            );
        }
        return integer;
    }

    /**
     * 删除全部
     *
     * @param uid
     * @return
     */
    @Override
    public Integer deleteAll(Integer uid) {
        Integer integer = cardMapper.deleteCardAll(uid);
        if (integer != 0){
            logMapper.addLog(new ShoppingMallLog()
                    .setLname("用户："+uid)
                    .setType("删除")
                    .setInfo("用户："+uid+"，删除了自己购物车中的全部记录")
                    .setAffectedinfo("1")
                    .setLtime(new Date())
            );
        }
        return integer;
    }
}
