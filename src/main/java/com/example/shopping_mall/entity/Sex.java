package com.example.shopping_mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (Sex)实体类
 *
 * @author makejava
 * @since 2022-11-20 20:13:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Sex implements Serializable {
    /**
     * 性别id
     */
    private Integer xid;
    /**
     * 性别描述信息
     */
    private String xinfo;


    public Integer getXid() {
        return xid;
    }

    public void setXid(Integer xid) {
        this.xid = xid;
    }

    public String getXinfo() {
        return xinfo;
    }

    public void setXinfo(String xinfo) {
        this.xinfo = xinfo;
    }

}

