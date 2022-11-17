package com.example.shopping_mall.Service.Impl;

import com.example.shopping_mall.Service.TypeService;
import com.example.shopping_mall.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    /**
     * 查询所有类型方法
     * @return
     */
    @Override
    public List<Map<String, Object>> findAllType() {
        List<Map<String, Object>> tops = typeMapper.findAll(0, 0);
        if (!tops.isEmpty()){
            //循环顶层查询对应的中层
            for (Map<String,Object> map : tops){
                List<Map<String, Object>> centers = typeMapper.findAll(1, Integer.parseInt(String.valueOf(map.get("id"))));
                if (!centers.isEmpty()){
                    //把中层的值放到tops中
                    map.put("centers",centers);
                    //循环中层查询对应的底层
                    for (Map<String,Object> map1 : centers){
                        List<Map<String, Object>> lefts = typeMapper.findAll(2, Integer.parseInt(String.valueOf(map1.get("id"))));
                        if (!lefts.isEmpty()){
                            //把底层的值放到centers中
                            map1.put("lefts",lefts);
                        }
                    }
                }
            }
        }
        return tops;
    }
}
