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

    /**
     * 通过aid，查询唯一
     * @param aid
     * @return
     */
    Address getAddressByAid(Integer aid);

    /**
     * 地址管理-查看
     * @param uid
     * @param uanem
     * @return
     */
    Map<String,Address> addressManagement(Integer uid , String uanem);

    /**
     * 修改地址
     * @param address
     * @return
     */
    Integer editAddress(Address address);
}
