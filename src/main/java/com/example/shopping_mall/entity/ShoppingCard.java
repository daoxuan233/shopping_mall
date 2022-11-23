package com.example.shopping_mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (Shoppingcard)实体类
 *
 * @author makejava
 * @since 2022-11-21 11:46:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ShoppingCard implements Serializable {
    /**
     * 购物车中的id
     */
    private Integer cid;
    /**
     * 商品表中id
     */
    private Integer sid;
    /**
     * 该商品的数量
     */
    private Integer number;
    /**
     * 买家id
     */
    private Integer uid;
    /**
     * 总金额
     */
    private Double totalamount;

    public Double gettotalAmount() {
        return totalamount;
    }
}

