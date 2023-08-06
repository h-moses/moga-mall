package com.ms.product.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CategoryTreeDto implements Serializable {

    private static final long serialVersionUID = 6829122104387673185L;

    @ApiModelProperty("分类ID")
    private Long id;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("父分类ID")
    private Long parentId;

    @ApiModelProperty("分类级别")
    private Integer level;

    @ApiModelProperty("子分类")
    private List<CategoryTreeDto> children;
}
