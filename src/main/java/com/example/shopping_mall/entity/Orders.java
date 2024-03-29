package com.example.shopping_mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单表(Orders)实体类
 *
 * @author makejava
 * @since 2022-11-20 15:51:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Orders implements Serializable {
    /**
     * 订单表id
     */
    private Integer oid;
    /**
     * 订单详情
     */
    private String oinfo;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 商品id
     */
    private String sid;
    /**
     * 总价
     */
    private Double sum;
    /**
     * 用户地址
     */
    private Integer addressid;
    /**
     * 支付方式
     */
    private Integer pay;
    /**
     * 用户状态，0表示待支付，1表示已支付，2表示已发货，3表示已签收
     */
    private Integer state;

}

