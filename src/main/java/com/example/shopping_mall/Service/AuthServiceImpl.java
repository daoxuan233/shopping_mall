package com.example.shopping_mall.Service;

import com.example.shopping_mall.entity.ShoppingUser;
import com.example.shopping_mall.entity.log.ShoppingMallLog;
import com.example.shopping_mall.mapper.LogMapper;
import com.example.shopping_mall.mapper.UserMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AuthServiceImpl implements UserDetailsService {

    @Resource
    UserMapper userMapper;

    @Resource
    LogMapper logMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("s:"+s);
        ShoppingUser user = userMapper.findUser(s);
        System.out.println("user:"+user);
        if (user == null) throw new UsernameNotFoundException("用户"+s+"登录失败");
        else {
            logMapper.addLog(new ShoppingMallLog()
                    .setLname("用户"+user.getUid())
                    .setType("登录")
                    .setInfo("用户"+s+"[编号：+"+user.getUid()+"]"+"登录网站")
                    .setAffectedinfo("0")
                    .setLtime(new Date())
            );
            return User
                    .withUsername(user.getUname())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
        }
    }
}
