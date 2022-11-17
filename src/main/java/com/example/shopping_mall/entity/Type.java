package com.example.shopping_mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * (Type)实体类
 *
 * @author makejava
 * @since 2022-11-12 12:16:00
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Type implements Serializable {

    private Integer id;
    
    private String name;
    
    private Integer level;
    /**
     * 自对应
     */
    private Integer prentid;
    
    private Date creattime;

}

