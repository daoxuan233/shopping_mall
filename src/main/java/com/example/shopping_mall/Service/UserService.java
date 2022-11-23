package com.example.shopping_mall.Service;

import com.example.shopping_mall.entity.ShoppingUser;
import com.example.shopping_mall.entity.resp.RestBean;

public interface UserService {
    void createUser(String username, String password,String mail);

    /**
     * 登录验证
     */
    RestBean<ShoppingUser> loginVerify(String username , String password);
    /**
     * 修改密码 前置
     *              01
     */
    RestBean<ShoppingUser> ModifyPassword(String username);

    String UserAll(String uname);

    /**
     * 根据id查询用户信息
     * @param uid
     * @return
     */
    ShoppingUser getUserByUid(String uid);

    /**
     * 个人中心
     * @param uname
     * @return
     */
    ShoppingUser user(String uname);

    /**
     * 修改个人信息
     * @param user
     * @return
     */
    Integer edit(ShoppingUser user);
}
