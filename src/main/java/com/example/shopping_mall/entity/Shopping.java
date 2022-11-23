package com.example.shopping_mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true) //生成链式调用
@AllArgsConstructor //生成有参构造器
@NoArgsConstructor //生成无参构造器
@ApiModel(description = "商品表")
public class Shopping implements Serializable {
    /**
     * 商品主键
     */
    @ApiModelProperty("sid")
    private Integer sid;
    /**
     * 商品关键词
     */
    @ApiModelProperty("商品关键词")
    private String sname;
    /**
     * 商品描述信息
     */
    @ApiModelProperty("商品描述信息")
    private String sNameInfo;
    /**
     * 商品价格
     */
    @ApiModelProperty("商品价格")
    private Double sprice;
    /**
     * 商品封面图
     */
    @ApiModelProperty("商品封面图")
    private String simage;
    /**
     * 卖家账号
     */
    @ApiModelProperty("卖家账号")
    private String seller;
    /**
     * 商品发布时间
     */
    @ApiModelProperty("商品发布时间")
    private Date stime;
    /**
     * 商品类型
     */
    @ApiModelProperty("商品类型")
    private Integer stype;

    /**
     * 商品热度
     */
    @ApiModelProperty("商品热度")
    private Integer heat;
}
