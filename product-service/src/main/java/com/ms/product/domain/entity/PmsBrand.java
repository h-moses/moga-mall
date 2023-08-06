package com.ms.product.domain.entity;

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
 * 品牌
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Getter
@Setter
@TableName("pms_brand")
@ApiModel(value = "PmsBrand对象", description = "品牌")
public class PmsBrand implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("品牌id")
    @TableId(value = "brand_id", type = IdType.AUTO)
    private Long brandId;

    @ApiModelProperty("品牌名")
    private String name;

    @ApiModelProperty("品牌logo地址")
    private String logo;

    @ApiModelProperty("介绍")
    private String description;

    @ApiModelProperty("显示状态[0-不显示；1-显示]")
    private Integer status;

    @ApiModelProperty("检索首字母")
    private String firstLetter;

    @ApiModelProperty("排序")
    private Integer sort;


}
