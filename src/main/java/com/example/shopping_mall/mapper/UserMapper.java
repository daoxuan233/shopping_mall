package com.example.shopping_mall.mapper;

import com.example.shopping_mall.cache.RedisMybatisCache;
import com.example.shopping_mall.entity.ShoppingUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 只需要修改缓存实现类implementation为我们的RedisMybatisCache即可
 */
@CacheNamespace(implementation = RedisMybatisCache.class)
@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<ShoppingUser> getUserAll();

    @Select("select * from user where uname = #{username}")
    ShoppingUser getPasswordByUsername(@Param("username") String username);

    @Insert("insert into user(uname,umail,password,phone,idcard,role,sex,activation,ttime,img) value(#{uname},#{umail},#{password},#{phone},#{idcard},#{role},#{sex},#{activation},#{ttime},#{img})")
    Integer registerUser( ShoppingUser user);

    @Select("select * from user where uname=#{uname} and password=#{password}")
    ShoppingUser loginVerify(@Param("uname") String username , @Param("password") String password);

    @Select("select * from user where uname=#{uname}")
    ShoppingUser findUser(@Param("uname") String username);

    @Update("UPDATE user SET uid=#{uid},password=#{password},ttime=#{ttime},uname=#{uname},role=#{role},activation=#{activation},sex=#{sex},img=#{img} where uname=#{uname}")
    Integer changePasswordByUsername(ShoppingUser userEntity);

    @Select("select uname from user where uname=#{uname}")
    String getUnameAll(String uname);

    @Select("select * from user where uid=#{uid}")
    ShoppingUser getAllByUid(@Param("uid") Integer uid);

    @Update("UPDATE user SET uid=#{uid} , uname=#{uname},sex=#{sex},umail=#{umail} where uid=#{uid}")
    Integer edit(ShoppingUser user);

    @Update("UPDATE user SET uid=#{uid} , phone=#{phone} where uid=#{uid}")
    Integer editPhone(ShoppingUser user);
}
