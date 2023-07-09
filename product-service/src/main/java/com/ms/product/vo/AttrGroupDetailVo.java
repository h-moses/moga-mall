package com.ms.product.vo;

import com.ms.product.entity.PmsAttr;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AttrGroupDetailVo {

    @ApiModelProperty("分组id")
    private Long attrGroupId;

    @ApiModelProperty("组名")
    private String attrGroupName;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("组图标")
    private String icon;

    @ApiModelProperty("所属分类id")
    private Long categoryId;

    private List<PmsAttr> attrs;
}
