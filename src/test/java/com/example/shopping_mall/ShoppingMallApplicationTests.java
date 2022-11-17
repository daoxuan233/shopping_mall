package com.example.shopping_mall;

import com.example.shopping_mall.Service.TypeService;
import com.example.shopping_mall.Service.shop.CardService;
import com.example.shopping_mall.Service.shop.ShopService;
import com.example.shopping_mall.entity.Seller;
import com.example.shopping_mall.entity.Shopping;
import com.example.shopping_mall.entity.ShoppingCard;
import com.example.shopping_mall.entity.ShoppingUser;
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

    }

    @Test
    void c3(){
        List<Map<String, Object>> byIdSum = cardService.getCardAllSum(1001);
    }

    @Test
    void c4(){
    }
}
