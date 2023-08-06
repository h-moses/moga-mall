package com.ms.product.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * spu信息
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Getter
@Setter
@TableName("pms_spu_info")
@ApiModel(value = "PmsSpuInfo对象", description = "spu信息")
public class PmsSpuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商品名称")
    private String spuName;

    @ApiModelProperty("商品描述")
    private String spuDescription;

    @ApiModelProperty("所属分类id")
    private Long categoryId;

    @ApiModelProperty("品牌id")
    private Long brandId;

    private BigDecimal weight;

    @ApiModelProperty("上架状态[0 - 下架，1 - 上架]")
    private Integer status;

    private Date createTime;

    private Date updateTime;


}
