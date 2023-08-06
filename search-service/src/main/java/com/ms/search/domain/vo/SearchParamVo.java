package com.ms.search.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@ApiModel(value = "SearchParamVo", description = "商城搜索传参")
@Data
public class SearchParamVo {

    @ApiModelProperty(value = "全文匹配的关键字")
    String keyword;

    @ApiModelProperty(value = "三级分类id")
    Long thirdCategoryId;

    @ApiModelProperty(value = "排序条件")
    String sort;

    @ApiModelProperty(value = "页码")
    Integer pageNum;

    @ApiModelProperty(value = "过滤条件【价格区间、品牌、库存、SKU属性等】")
    Map<String, Object> filter;

}
