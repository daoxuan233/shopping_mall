package com.example.shopping_mall.mapper;

import com.example.shopping_mall.cache.RedisMybatisCache;
import com.example.shopping_mall.entity.Address;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@CacheNamespace(implementation = RedisMybatisCache.class)
@Mapper
public interface AddressMapper {
    /**
     * 查看当前用户是否有使用过的地址
     */
    @Select("select * from address where uid = #{uid}")
    List<Address> getAddressByUid(Integer uid);

    /**
     * 添加地址
     * @param address
     * @return
     */
    @Insert("insert into address(name, address, namephone, uid) value(#{name},#{address},#{namephone},#{uid})")
    Integer addAddress(Address address);

    /**
     * 通过uid 返回给前端地址
     * @param uid
     * @return
     */
    @Select("select address from address where uid=#{uid}")
    Map<String , String> getAddressByUId(Integer uid);

}
