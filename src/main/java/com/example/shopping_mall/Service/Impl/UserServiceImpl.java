package com.example.shopping_mall.Service.Impl;

import com.example.shopping_mall.Service.UserService;
import com.example.shopping_mall.entity.ShoppingUser;
import com.example.shopping_mall.entity.log.ShoppingMallLog;
import com.example.shopping_mall.entity.resp.RestBean;
import com.example.shopping_mall.mapper.LogMapper;
import com.example.shopping_mall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Resource
    LogMapper logMapper;

    /**
     * 注册，在第一次登录时，默认要修改密码的
     * @param username
     * @param password
     */
    @Override
    public void createUser(String username, String password , String amil) {
        ShoppingUser user = new ShoppingUser();
        user.setUname(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setSex(3);
        user.setActivation(0);
        user.setUmail(amil);
        Integer addUser = userMapper.registerUser(user);
        if (addUser != null){
            logMapper.addLog(new ShoppingMallLog()
                    .setLname(username)
                    .setType("添加数据")
                    .setInfo("用户"+username+"注册账户："+"注册邮箱："+amil+"密码[明文]："+password)
                    .setAffectedinfo("1")
                    .setLtime(new Date())
            );
        }
    }

    @Override
    public RestBean<ShoppingUser> loginVerify(String username, String password) {
        ShoppingUser user = userMapper.loginVerify(username, password);
        if (user == null){
            return new RestBean<>(404,"登录失败，账户或密码错误！",null);
        }else {
            return new RestBean<>(200,"登录成功！",user);
        }
    }

    /**
     * 修改密码 前置
     * 01
     *
     * @param username
     */
    @Override
    public RestBean<ShoppingUser> ModifyPassword(String username) {
        ShoppingUser user = userMapper.findUser(username);
        if (user.getActivation() == 0){
            return new RestBean<>(304,"请前往密码修改界面，激活身份",null);
        }else {
            return new RestBean<>(200,"验证正确，进入首页！",user);
        }
    }

    @Override
    public String UserAll(String uname) {
        return userMapper.getUnameAll(uname);
    }

    /**
     * 根据id查询用户信息
     *
     * @param uid
     * @return
     */
    @Override
    public ShoppingUser getUserByUid(String uid) {
        return userMapper.getAllByUid(Integer.valueOf(uid));
    }

    /**
     * 个人中心
     *
     * @param uname
     * @return
     */
    @Override
    public ShoppingUser user(String uname) {
        return userMapper.findUser(uname);
    }

    /**
     * 修改个人信息
     *
     * @param user
     * @return
     */
    @Override
    public Integer edit(ShoppingUser user) {
        Integer edit = userMapper.edit(user);
        if (edit != null){
            logMapper.addLog(new ShoppingMallLog()
                    .setLname("用户："+user.getUid())
                    .setType("修改")
                    .setInfo("用户更新个人信息，更改如下："+user)
                    .setAffectedinfo("0")
                    .setLtime(new Date())
            );
        }
        return edit;
    }
}
