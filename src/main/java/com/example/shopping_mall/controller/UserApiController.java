package com.example.shopping_mall.controller;

import com.example.shopping_mall.entity.ShoppingUser;
import com.example.shopping_mall.entity.resp.RestBean;
import com.example.shopping_mall.mapper.UserMapper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "user的请求")
@RequestMapping("/api/user")
public class UserApiController {
    @Autowired
    UserMapper userMapper;

    @ApiResponses({
            @ApiResponse(code = 200 , message = "注册成功"),
            @ApiResponse(code = 402 , message = "请求错误，没有该用户")
    })
    @GetMapping("/change-password")
    @ApiOperation("忘记密码接口")
    public RestBean<ShoppingUser> change(@ApiParam("用户名") @RequestParam("username") String username,
                                        @ApiParam("密码") @RequestParam("password") String password
                                     ) {
        ShoppingUser user = userMapper.findUser(username);
        if (user != null){
            userMapper.changePasswordByUsername(new ShoppingUser()
                    .setUid(user.getUid())
                    .setUname(username)
                    .setPassword(new BCryptPasswordEncoder().encode(password))
                    .setActivation(1)
                    .setRole(user.getRole())
                    .setTtime(user.getTtime())
                    .setUmail(user.getUname())
                    .setIdcard(user.getIdcard())
                    .setPhone(user.getPhone())
                    .setSex(user.getSex())
            );
            return new RestBean<>(200, "请求成功",user);
        }
        return new RestBean<>(402,"请求错误，没有该用户",null);
    }

}
