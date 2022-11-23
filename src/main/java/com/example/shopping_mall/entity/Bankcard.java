package com.example.shopping_mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 银行卡表(Bankcard)实体类
 *
 * @author makejava
 * @since 2022-11-23 11:20:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Bankcard implements Serializable {
    /**
     * 卡号
     */
    private Integer cid;
    /**
     * 卡类型：金卡、银卡、钻石卡...
     */
    private String type;
    /**
     * 余额
     */
    private Double account;
    /**
     * 用户id
     */
    private Integer uid;

}

