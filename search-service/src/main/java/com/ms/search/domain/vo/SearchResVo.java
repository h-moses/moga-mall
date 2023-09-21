package com.ms.search.domain.vo;

import com.ms.common.to.SkuEsModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class SearchResVo {

    @ApiModelProperty(value = "商品信息")
    List<SkuEsModel> products;

    @ApiModelProperty(value = "品牌信息")
    List<Brand> brands;

    @ApiModelProperty(value = "分类信息")
    List<Category> categories;

    @ApiModelProperty(value = "属性信息")
    List<Attribute> attributes;

    @ApiModelProperty(value = "当前页码")
    Integer pageNum;

    @ApiModelProperty(value = "总页数")
    Integer totalPage;

    @ApiModelProperty(value = "总记录数")
    Long total;


    @Data
    @Builder
    public static class Brand {
        Long id;

        String brandName;

        String brandPic;
    }

    @Data
    @Builder
    public static class Category {
        Long id;

        String categoryName;
    }

    @Data
    @Builder
    public static class Attribute {
        Long id;

        String attrName;

        List<String> attrValues;
    }
}
