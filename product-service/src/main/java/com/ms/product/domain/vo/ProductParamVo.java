package com.ms.product.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(value = "ProductParamVo", description = "修改商品信息接收参数")
public class ProductParamVo implements Serializable {

    @ApiModelProperty("商品名称")
    @NotBlank(message = "商品名称不能为空！")
    private String name;

    @ApiModelProperty("商品分类ID")
    private Integer categoryId;

    @ApiModelProperty("商品品牌ID")
    private Integer brandId;

    @ApiModelProperty("商品价格")
    private BigDecimal price;

    @ApiModelProperty("商品库存")
    private Integer stock;

    @ApiModelProperty("商品销量")
    private Integer sales;

    @ApiModelProperty("商品图片")
    @NotNull(message = "商品图片未上传！")
    private MultipartFile image;

    @ApiModelProperty("商品状态（0：下架，1：上架）")
    private Integer status;
}
