package com.example.shopping_mall.controller;

import com.example.shopping_mall.Service.TypeService;
import com.example.shopping_mall.entity.log.ShoppingMallLog;
import com.example.shopping_mall.entity.resp.RestBean;
import com.example.shopping_mall.mapper.LogMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/type")
public class TypeController {

    @Resource
    private TypeService typeService;

    @Resource
    LogMapper logMapper;

    @RequestMapping("/findAllType")
    public RestBean<List<Map<String,Object>>> findAllType(){

        List<Map<String, Object>> allType = typeService.findAllType();
        if (!allType.isEmpty()){
            logMapper.addLog(new ShoppingMallLog()
                    .setLname("前端用户")
                    .setType("查询")
                    .setInfo("前端用户查询了商品的所有类型")
                    .setAffectedinfo("0")
                    .setLtime(new Date())
            );
            return new RestBean<>(200,"查询成功",allType);

        }else {

            return new RestBean<>(500,"没有该类型的数据");

        }
    }
}
