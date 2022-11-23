package com.example.shopping_mall.provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class OrdersDynaSqlProviderBuilder {

    //查支付状态
    public String buildSelectOrdersWithParam(Map<String,Object> param){
        return new SQL(){
            {
                SELECT("*");
                //条件
                if (param.isEmpty()){
                    FROM("orders");
                }
                if (param.get("state") != null){
                    FROM("orders");
                    WHERE("state=#{state}");
                }
            }
        }.toString();
    }
}
