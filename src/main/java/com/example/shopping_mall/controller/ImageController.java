package com.example.shopping_mall.controller;

import com.example.shopping_mall.entity.resp.RestBean;
import com.example.shopping_mall.util.UploadFileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Api(description = "文件上传类")
public class ImageController {
    @Autowired
    UploadFileUtil uploadFile;

    @RequestMapping("/api/shop/shop-file")
    public RestBean<String> fileShop(
            @ApiParam("multipartFile 文件对象") @RequestParam("multipartFile") MultipartFile multipartFile,
            @ApiParam("dir 上传目录") @RequestParam("dir") String dir){
        RestBean<String> restBean = uploadFile.uploadFile(multipartFile, dir);
        if (restBean == null){
            return new RestBean<>(401,"请求失败！");
        }
        return restBean;
    }
}
