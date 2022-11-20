package com.example.shopping_mall.Service;

import com.example.shopping_mall.entity.Address;

import java.util.List;
import java.util.Map;

public interface AddressService {
    /**
     * 查询该用户是否有地址记录
     * @param uid
     * @return
     */
    List<Address> getAddressByUidInfo(Integer uid);

    /**
     * 添加地址
     * @param address
     * @return
     */
    Integer addAddress(Address address);

    /**
     * 通过uid 向前端发送地址
     * @param uid
     * @return
     */
    Map<String , String> getAddressByUId(Integer uid);

}
