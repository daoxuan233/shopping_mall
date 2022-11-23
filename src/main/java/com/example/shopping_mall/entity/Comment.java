package com.example.shopping_mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.io.Serializable;

/**
 * (Comment)实体类
 *
 * @author makejava
 * @since 2022-11-21 17:30:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Comment implements Serializable {
    /**
     * 一级评论id
     */
    private Long id;
    /**
     * 发布人信息
     */
    private Integer uid;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 发布时间
     */
    private Date createTime;
    /**
     * 父评论id，自对应
     */
    private Long parentCommentId;
    /**
     * 商品id
     */
    private Integer sid;
    /**
     * 用户角色
     */
    private Integer urole;
    /**
     * 商品评价等级
     */
    private String grade;

}

