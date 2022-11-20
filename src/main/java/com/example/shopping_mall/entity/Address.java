package com.example.shopping_mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 地址表(Address)实体类
 *
 * @author makejava
 * @since 2022-11-20 14:08:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Address implements Serializable {
    /**
     * 用户地址管理的id
     */
    private Integer aid;
    /**
     * 收货人姓名
     */
    private String name;
    /**
     * 收货地址
     */
    private String address;
    /**
     * 收货人联系方式
     */
    private String namephone;
    /**
     * 当前使用用户的id
     */
    private Integer uid;
    /**
     * 卖家id
     */
    private Integer mid;

}

