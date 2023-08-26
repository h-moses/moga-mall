package com.ms.product.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * spu信息介绍
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Getter
@Setter
@TableName("pms_spu_info_desc")
@ApiModel(value = "PmsSpuInfoDesc对象", description = "spu信息介绍")
public class PmsSpuInfoDesc implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品id")
    @TableId(type = IdType.INPUT)
    private Long spuId;

    @ApiModelProperty("商品介绍")
    private String decript;


}
