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
@ApiModel(description = "用户表")
public class ShoppingUser implements Serializable {
    /**
     * 用户id（账号）
     */
    @ApiModelProperty("uid")
    private Integer uid;
    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    private String uname;
    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String umail;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;
    /**
     * 电话号码
     */
    @ApiModelProperty("电话号码")
    private String phone;
    /**
     * 类型：admin-管理员，user-普通成员
     */
    @ApiModelProperty("类型：admin-管理员，user-普通成员 ，shop-卖家")
    private String role;
    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private Integer sex;
    /**
     * 账号激活状态
     */
    @ApiModelProperty("账号激活状态-主要用于初次登录的密码校验")
    private Integer activation;
    /**
     * 身份证号
     */
    @ApiModelProperty("身份证号")
    private String idcard;
    /**
     * 开户时间
     */
    @ApiModelProperty("开户时间")
    private Date ttime;
    /**
     * 用户头像
     */
    private String img;
}
