package com.example.shopping_mall.controller;

import com.example.shopping_mall.Service.AddressService;
import com.example.shopping_mall.Service.BankCardService;
import com.example.shopping_mall.Service.UserService;
import com.example.shopping_mall.entity.Address;
import com.example.shopping_mall.entity.Bankcard;
import com.example.shopping_mall.entity.ShoppingUser;
import com.example.shopping_mall.entity.log.ShoppingMallLog;
import com.example.shopping_mall.entity.resp.RestBean;
import com.example.shopping_mall.mapper.LogMapper;
import com.example.shopping_mall.mapper.SexMapper;
import com.example.shopping_mall.mapper.UserMapper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(description = "用户接口")
@RequestMapping("/api/user")
public class UserApiController {
    @Autowired
    UserMapper userMapper;

    @Resource
    UserService userService;

    @Resource
    SexMapper sexMapper;

    @Resource
    LogMapper logMapper;

    @Resource
    AddressService addressService;

    @Resource
    BankCardService bankCardService;

    @ApiResponses({
            @ApiResponse(code = 200 , message = "注册成功"),
            @ApiResponse(code = 402 , message = "请求错误，没有该用户")
    })
    @GetMapping("/change-password")
    @ApiOperation("忘记密码接口")
    public RestBean<ShoppingUser> change(
            @ApiParam("用户名") @RequestParam("username") String username,
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

    @ApiOperation("个人中心Ⅰ")
    @RequestMapping("/personal-center")
    public RestBean<Map<String,Object>> user(
            @ApiParam("登录用户名") @RequestParam("uname") String uname
    ){
        Map<String,Object> map = new HashMap<>();
        ShoppingUser user = userService.user(uname);
        if (user == null){
            return new RestBean<>(402,"用户错误");
        }
        String sex = sexMapper.sex(user.getSex());
        if (sex == null){
            return new RestBean<>(402,"性别请求错误");
        }
        boolean realNameAuthentication = user.getIdcard() != null;
        map.put("用户姓名：",user.getUname());
        map.put("用户邮箱：",user.getUmail());
        map.put("用户电话：",user.getPhone());
        map.put("用户性别：",sex);
        map.put("realNameAuthentication：",realNameAuthentication);
        map.put("用户注册时间：",user.getTtime());
        return new RestBean<>(200,"请求成功",map);
    }

    @ApiOperation("编辑资料/修改个人信息")
    @RequestMapping("/edit-home-page")
    public RestBean<ShoppingUser> edit(
            @ApiParam("用户id") @RequestParam("uid") String uid,
            @ApiParam("个人账号") @RequestParam("uname") String name,
            @ApiParam("邮箱换绑") @RequestParam("umail") String umail,
            @ApiParam("性别") @RequestParam("sex") Integer sex
    ){
        ShoppingUser user1 = userMapper.findUser(name);
        if (user1 != null){
            return new RestBean<>(403,"用户已经存在，请更换用户名");
        }
        Integer edit = userService.edit(
                new ShoppingUser()
                        .setUid(Integer.valueOf(uid))
                        .setUname(name)
                        .setUmail(umail)
                        .setSex(sex)
        );
        if (edit == null){
            return new RestBean<>(402,"请求失败");
        }
        ShoppingUser user = userService.user(name);
        return new RestBean<>(200,"请求成功",user);
    }

    @ApiOperation("编辑资料/更换手机号")
    @RequestMapping("/editPhone")
    public RestBean<ShoppingUser> editPhone(
            @ApiParam("用户id") @RequestParam("uid") String uid,
            @ApiParam("手机换绑") @RequestParam("phone") String phone
    ){
        Integer edit = userMapper.editPhone(
                new ShoppingUser()
                        .setUid(Integer.valueOf(uid))
                        .setPhone(phone)//18111198700
        );
        if (edit == 0){
            return new RestBean<>(402,"请求失败");
        }
        logMapper.addLog(new ShoppingMallLog()
                .setLname("用户："+uid)
                .setType("修改")
                .setInfo("用户进行手机换绑，更改如下：phone{"+phone+"}")
                .setAffectedinfo("0")
                .setLtime(new Date())
        );
        ShoppingUser user = userService.getUserByUid(uid);
        return new RestBean<>(200,"请求成功",user);
    }

    @ApiOperation("收货地址管理-查看")
    @RequestMapping("/address-management-toView")
    public RestBean<Map<String, Address>> addressManagement(
            @ApiParam("用户uid") @RequestParam("uid") String uid,
            @ApiParam("用户名") @RequestParam("uname") String uname
    ){
        Map<String, Address> stringAddressMap = addressService.addressManagement(Integer.valueOf(uid), uname);
        if (stringAddressMap.isEmpty()){
            return new RestBean<>(402,"请求错误，请检查传来的uid和用户名");
        }
        return new RestBean<>(200,"请求成功",stringAddressMap);
    }

    @ApiOperation("收货地址管理-修改")
    @RequestMapping("/address-management-edit")
    public RestBean<Map<String, Address>> addressEdit(
            @ApiParam("用户uid") @RequestParam("uid") String uid,
            @ApiParam("用户名") @RequestParam("uname") String uname,
            @ApiParam("联系方式") @RequestParam("namephone") String namephone,
            @ApiParam("地址") @RequestParam("address") String address
    ){
        Integer integer = addressService.editAddress(
                new Address()
                        .setUid(Integer.valueOf(uid))
                        .setName(uname)
                        .setNamephone(namephone)
                        .setAddress(address)
        );
        if (integer == 0){
            return new RestBean<>(402,"请求错误，请检查提交的数据");
        }
        Map<String, Address> stringAddressMap = addressService.addressManagement(Integer.valueOf(uid), uname);
        return new RestBean<>(200,"请求成功",stringAddressMap);
    }

    @ApiOperation("收货地址管理-新增")
    @RequestMapping("/address-management-add")
    public RestBean<Map<String, Address>> addressAdd(
            @ApiParam("用户uid") @RequestParam("uid") String uid,
            @ApiParam("地址") @RequestParam("address") String address
    ) {
        ShoppingUser user = userService.getUserByUid(uid); // 获取uid信息，检查用户是否存在
        if (user == null){
            return new RestBean<>(402,"没有该用户id");
        }
        Integer integer = addressService.addAddress(
                new Address()
                        .setUid(Integer.valueOf(uid))
                        .setName(user.getUname())
                        .setNamephone(user.getPhone())
                        .setAddress(address)
        );
        if (integer == 0){
            return new RestBean<>(402,"信息错误");
        }
        Map<String, Address> stringAddressMap = addressService.addressManagement(Integer.valueOf(uid), user.getUname());
        return new RestBean<>(200,"请求成功,信息已添加",stringAddressMap);
    }

    @ApiOperation("账户内余额")
    @RequestMapping("/balance-account")
    public RestBean<Bankcard> show(
        @ApiParam("当前用户id") @RequestParam("uid") String uid
    ){
        Bankcard bankcard = bankCardService.ByUidQueryBanCard(Integer.valueOf(uid));
        if (bankcard == null){
            return new RestBean<>(402,"该用户还没有注册过！");
        }
        return new RestBean<>(200,"请求成功",bankcard);
    }





    //Modifying the Profile Picture

}
