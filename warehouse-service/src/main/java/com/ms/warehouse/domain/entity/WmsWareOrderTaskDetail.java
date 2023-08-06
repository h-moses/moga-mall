package com.ms.warehouse.domain.entity;

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
 * 库存工作单
 * </p>
 *
 * @author ms
 * @since 2023-07-09
 */
@Getter
@Setter
@TableName("wms_ware_order_task_detail")
@ApiModel(value = "WmsWareOrderTaskDetail对象", description = "库存工作单")
public class WmsWareOrderTaskDetail implements Serializable {

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
