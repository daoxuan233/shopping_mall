package com.example.shopping_mall.controller;

import com.example.shopping_mall.Service.UserService;
import com.example.shopping_mall.Service.VerifyService;
import com.example.shopping_mall.entity.ShoppingUser;
import com.example.shopping_mall.entity.resp.RestBean;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth")
@Api(description = "登录、注册、验证码请求、退出系统")
public class RegisterApiController {
    @Resource
    VerifyService verifyService;

    @Resource
    UserService userService;


    @ApiOperation("请求邮箱验证码")
    @RequestMapping("verify-code")
    @ApiResponses({
            @ApiResponse(code = 200 , message = "邮件发送成功"),
            @ApiResponse(code = 500 , message = "邮件发送失败")
    })
    public RestBean<Void> verifyCode(@ApiParam("邮箱地址") @RequestParam("email")String email){
        try {
            verifyService.sendVerifyCode(email);
            return new RestBean<>(200,"邮件发送成功");
        }catch (Exception e){
            e.printStackTrace();
            return new RestBean<>(500,"邮件发送失败");
        }
    }

    @ApiResponses({
            @ApiResponse(code = 200 , message = "注册成功"),
            @ApiResponse(code = 403 , message = "注册失败，验证码填写错误")
    })
    @ApiOperation("验证验证码")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RestBean<Void> register(@ApiParam("用户名") @RequestParam("username") String username,
                                   @ApiParam("用户密码") @RequestParam("password") String password,
                                   @ApiParam("邮箱地址") @RequestParam("email") String email,
                                   @ApiParam("验证码") @RequestParam("verify") String verify
                                   ){
        if ( userService.UserAll(username) != null){
            return new RestBean<>(302,"用户名已被使用，请重新选择");
        }
        Boolean doVerify = verifyService.doVerify(email, verify);
        if (doVerify) {
            userService.createUser(username,password,email);
            return new RestBean<>(200,"注册成功");
        }else {
            return new RestBean<>(403,"注册失败，验证码填写错误");
        }
    }

    @ApiOperation("登录验证")
    @GetMapping("/access-deny")
    public RestBean<Void> accessDeny() {
        return new RestBean<>(401, "未验证,请先进行登录");
    }

    @ApiOperation("登录失败处理")
    @PostMapping("/login-failure")
    public RestBean<Void> loginFailure() {
        return new RestBean<>(403, "登录失败,用户名或密码错误!");
    }

    @PostMapping("/login-success")
    @ApiOperation("登录成功,关于新用户需要修改密码的验证")
    public RestBean<ShoppingUser> loginSuccess(@ApiParam("用户名") @RequestParam("username") String username){
        return  userService.ModifyPassword(username);
    }

    @GetMapping("/logout-success")
    public RestBean<Void> logoutSuccess() {
        return new RestBean<>(200, "退出成功");
    }
}
