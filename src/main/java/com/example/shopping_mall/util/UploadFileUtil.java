package com.example.shopping_mall.util;

import com.example.shopping_mall.entity.resp.RestBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.InetAddress;
import java.util.UUID;

@Component
public class UploadFileUtil {

/**
     * 项目端口
*/

    @Value("${server.port}")
    public String port;

/**
     * 项目路径
*/

    public String contextPath = "/static/**";

/**
     * 上传文件
     *
     * @param multipartFile 文件对象
     * @param dir 上传目录
     * @return
*/

    public RestBean<String> uploadFile(MultipartFile multipartFile, String dir) {
        try {
            if (multipartFile.isEmpty()) {
                return new RestBean<>(302,"请选择文件");
            }
            // 获取文件的名称
            String originalFilename = multipartFile.getOriginalFilename();
            // 文件后缀 例如：.png
            String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            // uuid 生成文件名
            String uuid = String.valueOf(UUID.randomUUID());
            // 根路径，在 resources/static/upload
            String basePath = ResourceUtils.getURL("classpath:").getPath() + "static/image/" + (StringUtils.isNotBlank(dir) ? (dir + "/") : "");
            // 新的文件名，使用uuid生成文件名
            String fileName = uuid + fileSuffix;
            // 创建新的文件
            File fileExist = new File(basePath);
            // 文件夹不存在，则新建
            if (!fileExist.exists()) {
                fileExist.mkdirs();
            }
            // 获取文件对象
            File file = new File(basePath, fileName);
            // 完成文件的上传
            multipartFile.transferTo(file);
            // 返回绝对路径
            return new RestBean<String>(200,"上传成功", "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + contextPath + "/upload/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RestBean<>(404,"上传失败",null);
    }
}


