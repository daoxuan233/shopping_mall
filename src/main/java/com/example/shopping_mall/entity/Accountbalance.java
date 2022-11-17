package com.example.shopping_mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true) //生成链式调用
public class Accountbalance implements Serializable {

  private long aid;
  private String aname;
  private long acardid;
  private double accountbalance;
  private long uid;

}
