package com.example.shopping_mall;

import com.example.shopping_mall.Service.OrdersService;
import com.example.shopping_mall.Service.TypeService;
import com.example.shopping_mall.Service.shop.CardService;
import com.example.shopping_mall.Service.shop.ShopService;
import com.example.shopping_mall.entity.*;
import com.example.shopping_mall.mapper.CommentMapper;
import com.example.shopping_mall.mapper.OrdersMapper;
import com.example.shopping_mall.mapper.ShopMapper;
import com.example.shopping_mall.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ShoppingMallApplicationTests {

    @Resource
    UserMapper userMapper;

    @Resource
    ShopMapper shopMapper;

    @Resource
    ShopService shopService;

    @Resource
    TypeService typeService;

    @Resource
    CardService cardService;

    @Resource
    CommentMapper commentMapper;

    @Resource
    OrdersMapper ordersMapper;

    @Resource
    OrdersService ordersService;

    @Test
    void contextLoads() {
    }
    @Test
    void find(){
       BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }

    @Test
    void find01(){
        System.out.println(userMapper.findUser("admin"));
    }
    @Test
    void find02(){
        ShoppingUser admin = userMapper.findUser("admin");
        userMapper.changePasswordByUsername(new ShoppingUser()
                .setUid(admin.getUid())
                .setUname("admin")
                .setPassword(new BCryptPasswordEncoder().encode("123456"))
                .setActivation(1)
                .setRole(admin.getRole())
                .setTtime(admin.getTtime())
                .setUmail(admin.getUname())
                .setIdcard(admin.getIdcard())
                .setPhone(admin.getPhone())
        );
        System.out.println(userMapper.findUser("admin"));
    }
    @Test
    void Ci(){
        System.out.println(shopMapper.getShopType("数码"));
    }
    @Test
    void S1(){
        Shopping shopping = shopService.productDetails("1001");
        System.out.println(shopping);
    }

    @Test
    public void ss(){
        System.out.println(typeService.findAllType());
    }
    @Test
    public void cq(){
        Shopping shopping = shopService.productDetails(String.valueOf(1001));
        System.out.println("shopping:  "+shopping);
        String seller = shopping.getSeller();
        Seller sellers = shopService.getSeller(seller);
        Map<String , Object> map = new HashMap<>();
        map.put("shopping",shopping);
        map.put("sellers",sellers);
        System.out.println("\n");
        System.out.println(map);
    }

    @Test
    void C0(){
        List<Map<String, Object>> allType = typeService.findAllType();
        System.out.println(allType);
    }

    @Test
    void c1(){
        List<ShoppingCard> byIDAll = cardService.getCardByIDAll(1001);
        System.out.println(byIDAll);
    }

    @Test
    void c2(){
        Orders orders = ordersService.isOrders(1001);
        String sid = orders.getSid();
        System.out.println(sid);
    }

    @Test
    void c3(){
        List<Map<String, Object>> byIdSum = cardService.getCardAllSum(1001);
    }

    @Test
    void c4(){
        List<Shopping> shopTypeAll = shopService.getShopTypeAll(85);
        System.out.println(shopTypeAll);
    }

    @Test
    void C5(){
        Map<String,Object> map = new HashMap<>();
        map.put("state",1);
        List<Orders> orders = ordersMapper.selectOrdersInfoWithParam(map);
        System.out.println(orders);
    }

    @Test
    void C6(){
        String string = "张三,李四,王五,马六,小气,";
        String substring = string.substring(0, string.length() - 1);
        System.out.println(substring);
        String[] split = substring.split(",");//以逗号分割
        for (String string2 : split) {
            System.out.println("数据-->>>" + string2);
        }
    }

    @Test
    void c01(){
        System.out.println("其实我也不知道做什么，要不写一个加权计算程序");
    }
}
