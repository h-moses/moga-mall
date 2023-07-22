package com.ms.product.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CategoryTreeDto implements Serializable {

    private static final long serialVersionUID = 6829122104387673185L;

    @ApiModelProperty("分类ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("父分类ID")
    private Integer parentId;

    @ApiModelProperty("分类级别")
    private Integer level;

    @ApiModelProperty("分类状态（0：禁用，1：启用）")
    private Integer status;

    @ApiModelProperty("子分类")
    private List<CategoryTreeDto> children;
}
