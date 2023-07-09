package com.ms.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 属性&属性分组关联
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Getter
@Setter
@TableName("pms_attr_attrgroup_relation")
@ApiModel(value = "PmsAttrAttrgroupRelation对象", description = "属性&属性分组关联")
public class PmsAttrAttrgroupRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("属性id")
    private Long attrId;

    @ApiModelProperty("属性分组id")
    private Long attrGroupId;

    @ApiModelProperty("属性组内排序")
    private Integer attrSort;


}
