package com.ms.warehouse.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 库存工作单
 * </p>
 *
 * @author ms
 * @since 2023-07-09
 */
@Getter
@Setter
@Builder
@TableName("wms_ware_order_task_detail")
@ApiModel(description = "库存工作单")
public class WareOrderTaskDetailEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("sku_id")
    private Long skuId;

    @ApiModelProperty("sku_name")
    private String skuName;

    @ApiModelProperty("购买个数")
    private Integer skuNum;

    @ApiModelProperty("工作单id")
    private Long taskId;

    @ApiModelProperty("仓库id")
    private Long wareId;

    @ApiModelProperty("1-已锁定  2-已解锁  3-扣减")
    private Integer lockStatus;


}
