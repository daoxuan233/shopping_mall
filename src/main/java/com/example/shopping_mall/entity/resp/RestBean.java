package com.example.shopping_mall.entity.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(description = "响应实体类的封装")
@NoArgsConstructor
@AllArgsConstructor
public class RestBean<T> {
    @ApiModelProperty("状态码")
    private Integer code;

    @ApiModelProperty("描述信息")
    private String reason;

    @ApiModelProperty("携带数据内容")
    private T data;

    public RestBean(Integer code, String reason) {
        this.code = code;
        this.reason = reason;
    }
}
