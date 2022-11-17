package com.example.shopping_mall.mapper;

import com.example.shopping_mall.cache.RedisMybatisCache;
import com.example.shopping_mall.entity.ShoppingUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 只需要修改缓存实现类implementation为我们的RedisMybatisCache即可
 */
@CacheNamespace(implementation = RedisMybatisCache.class)
@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<ShoppingUser> getUserAll();

    @Insert("insert into user(uname,umail,password,phone,idcard,role,sex,activation,ttime) value(#{uname},#{umail},#{password},#{phone},#{idcard},#{role},#{sex},#{activation},#{ttime})")
    Integer registerUser( ShoppingUser user);

    @Select("select * from user where uname=#{uname} and password=#{password}")
    ShoppingUser loginVerify(@Param("uname") String username , @Param("password") String password);

    @Select("select * from user where uname=#{uname}")
    ShoppingUser findUser(@Param("uname") String username);

    @Update("UPDATE user SET uid=#{uid},password=#{password},ttime=#{ttime},uname=#{uname},role=#{role},activation=#{activation},sex=#{sex} where uname=#{uname}")
    Integer changePasswordByUsername(ShoppingUser userEntity);
}
