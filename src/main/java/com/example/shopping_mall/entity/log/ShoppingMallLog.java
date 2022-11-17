package com.example.shopping_mall.entity.log;

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
@ApiModel(description = "日志表")
public class ShoppingMallLog implements Serializable {

    private Integer lid;

    /**
     * 执行者
     */
    @ApiModelProperty("执行者")
    private String lname;

    /**
     * 执行类型
     */
    @ApiModelProperty("执行类型")
    private String type;

    /**
     * 执行描述
     */
    @ApiModelProperty("执行描述")
    private String info;

    /**
     * 受影响的内容
     */
    @ApiModelProperty("受影响的内容")
    private String affectedinfo;

    /**
     * 产生时间
     */
    @ApiModelProperty("执行时间")
    private Date ltime;
}
