package com.example.shopping_mall.Service.Impl;

import com.example.shopping_mall.Service.AddressService;
import com.example.shopping_mall.entity.Address;
import com.example.shopping_mall.entity.log.ShoppingMallLog;
import com.example.shopping_mall.mapper.AddressMapper;
import com.example.shopping_mall.mapper.LogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    AddressMapper addressMapper;

    @Resource
    LogMapper logMapper;

    /**
     * 查询该用户是否有地址记录
     * @param uid
     * @return addressByUid
     */
    @Override
    public List<Address> getAddressByUidInfo(Integer uid) {
        List<Address> addressByUid = addressMapper.getAddressByUid(uid);
        if (addressByUid == null){
            logMapper.addLog(new ShoppingMallLog()
                    .setLname("系统")
                    .setType("查询")
                    .setInfo("系统查询该用户是否在本平台有过地址记录，结果："+addressByUid)
                    .setAffectedinfo("0")
                    .setLtime(new Date())
            );
        }
        logMapper.addLog(new ShoppingMallLog()
                .setLname("系统")
                .setType("查询")
                .setInfo("系统查询该用户是否在本平台有过地址记录，结果："+addressByUid)
                .setAffectedinfo("0")
                .setLtime(new Date())
        );
        return addressByUid;
    }

    /**
     * 添加地址
     *
     * @param address
     * @return
     */
    @Override
    public Integer addAddress(Address address) {
        Integer integer = addressMapper.addAddress(address);
        if (integer != 0){
            logMapper.addLog(new ShoppingMallLog()
                    .setLname("用户："+address.getUid())
                    .setType("添加")
                    .setInfo("用户："+address.getUid()+"，添加地址信息："+address.getAddress())
                    .setAffectedinfo("1")
                    .setLtime(new Date())
            );
        }
        return integer;
    }

    /**
     * 通过uid 向前端发送地址
     *
     * @param uid
     * @return
     */
    @Override
    public Map<String, String> getAddressByUId(Integer uid) {
        Map<String, String> addressByUId = addressMapper.getAddressByUId(uid);
        if (addressByUId != null){
            logMapper.addLog(new ShoppingMallLog()
                    .setLname("系统")
                    .setType("查询")
                    .setInfo("系统查询用户："+uid+"所有地址："+addressByUId)
                    .setAffectedinfo("0")
                    .setLtime(new Date())
            );
        }
        return addressByUId;
    }
}
