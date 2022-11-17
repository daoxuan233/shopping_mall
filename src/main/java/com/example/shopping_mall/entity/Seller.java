package com.example.shopping_mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true) //生成链式调用
@AllArgsConstructor //生成有参构造器
@NoArgsConstructor //生成无参构造器
@ApiModel(description = "卖家表")
public class Seller implements Serializable {
    private Integer mid;
    /**
     * 账号
     */
    @ApiModelProperty("账号")
    private String mname;
    /**
     * 卖家真实姓名
     */
    @ApiModelProperty("卖家真实姓名")
    private String nick;
    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String shopname;
    /**
     * 商品描述信息
     */
    @ApiModelProperty("商品描述信息")
    private String shopinfo;
    /**
     * 商铺地址
     */
    @ApiModelProperty("商铺地址")
    private String address;
    /**
     * 商铺图片
     */
    @ApiModelProperty("商铺图片")
    private String image;

}
