package com.example.shopping_mall.controller.shopping;

import com.example.shopping_mall.Service.shop.ShopService;
import com.example.shopping_mall.entity.Seller;
import com.example.shopping_mall.entity.Shopping;
import com.example.shopping_mall.entity.log.ShoppingMallLog;
import com.example.shopping_mall.entity.resp.RestBean;
import com.example.shopping_mall.mapper.LogMapper;
import com.example.shopping_mall.mapper.ShopMapper;
import com.example.shopping_mall.util.UploadFileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/index")
@Api(description = "商品相关")
public class ShoppingApiController {
    @Resource
    ShopService shopService;

    @Resource
    ShopMapper shopMapper;

    @Autowired
    private UploadFileUtil uploadFileUtil;

    @Resource
    LogMapper logMapper;

    @ApiOperation("所有商品展示")
    @RequestMapping("/show-shop")
    public RestBean<List<Shopping>> showShop(){
        List<Shopping> all = shopService.shopAll();
        if (all==null){
            return new RestBean<>(404,"发生错误！",null);
        }
        return new RestBean<>(200,"正常显示！",all);
    }

    @ApiOperation("商品展示详情/通过id查询商品详情")
    @RequestMapping("/product-details")
    public RestBean<Map<String,Object>> product(@ApiParam("商品id") @RequestParam("sid") String sid){
        Shopping shopping = shopService.productDetails(sid);
        if (shopping == null){
            return new RestBean<>(402,"请求失败，请检查商品id");
        }
        String seller = shopping.getSeller();
        Seller sellers = shopService.getSeller(seller);
        Map<String , Object> map = new HashMap<>();
        map.put("shopping",shopping);
        map.put("sellers",sellers);
        Integer heat = shopping.getHeat();
        Integer integer = shopService.increaseHeat(Integer.valueOf(sid),heat+1);
        if (integer <= 0 ){
            return new RestBean<>(402,"商品热度增加错误");
        }
        return new RestBean<>(200,"请求成功",map);
    }

    @ApiOperation("商品分类[搜索]")
    @RequestMapping("/shop-type")
    public RestBean<List<Shopping>> typeShopping(@ApiParam("查询内容") @RequestParam("shopType") String type){
        List<Shopping> shoppingType = shopService.shoppingType(type);
        if (shoppingType == null) return new RestBean<>(302, "查询结果为空");
        return new RestBean<>(200, "查询成功", shoppingType);
    }

    @ApiOperation("热门商品排行")
    @RequestMapping("/show-shopping")
    public RestBean<List<Shopping>> shopping(){
        List<Shopping> heatShop = shopService.heatShop();
        if (heatShop == null) return new RestBean<>(302, "查询结果为空");
        return new RestBean<>(200, "查询成功", heatShop);
    }

    @ApiOperation("上传商品/新增商品")
    @RequestMapping("/shop-file")
    public RestBean<String> fileShop(
            @ApiParam("商品id") @RequestParam("sid") String sid,
            @ApiParam("商品关键词") @RequestParam("sname") String sname,
            @ApiParam("商品详细信息") @RequestParam("snameinfo") String snameinfo,
            @ApiParam("卖家账号") @RequestParam("seller") String seller,
            @ApiParam("发布日期") @RequestParam("stime") String stime,
            @ApiParam("商品类型") @RequestParam("stype") Integer stype,
            @ApiParam("multipartFile 文件对象") @RequestParam("multipartFile") MultipartFile multipartFile,
            @ApiParam("dir 上传目录") @RequestParam("dir") String dir){
        RestBean<String> restBean = uploadFileUtil.uploadFile(multipartFile, dir);
        if (restBean == null){
            return new RestBean<>(401,"请求失败！");
        }else {
            Integer addShop = shopMapper.addShop(new Shopping()
                    .setSname(sname)
                    .setSNameInfo(snameinfo)
                    .setSeller(seller)
                    .setStime(new Date())
                    .setStype(stype)
            );
            if (addShop != null){
                logMapper.addLog(new ShoppingMallLog()
                        .setLname("前端用户")
                        .setType("新增")
                        .setInfo("前端用户新增商品："+sid+"商品名称："+snameinfo+"商品类型："+stype+"商品发布时间："+stime)
                        .setAffectedinfo(String.valueOf(addShop))
                        .setLtime(new Date())
                );
            }
            return restBean;
        }
    }

    /**
     * 通过名称模糊查询商品
     * @param sNameInfo
     * @return RestBean<List<Shopping>>
     */
    @ApiOperation("搜索商品/通过名称搜索")
    @RequestMapping("/shop-query")
    public RestBean<List<Shopping>> shopQuery(@ApiParam("商品名称") @RequestParam("sname") String sNameInfo){
        List<Shopping> shoppings = shopService.getSNameInfo(sNameInfo);
        if (shoppings == null){
            return new RestBean<>(500,"没有该商品");
        }
        return new RestBean<>(200, "获取成功", shoppings);
    }

    @ApiOperation("商品分类[三级列表]")
    @RequestMapping("/type-Query")
    public RestBean<List<Shopping>> typeQuery(@ApiParam("类型id") @RequestParam("id") String id){
        List<Shopping> shopTypeAll = shopService.getShopTypeAll(Integer.valueOf(id));
        if (shopTypeAll != null){
            return new RestBean<>(200,"请求成功",shopTypeAll);
        }
        return new RestBean<>(402,"没有该类型的商品");
    }
}
