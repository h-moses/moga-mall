package com.ms.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 退货原因
 * </p>
 *
 * @author ms
 * @since 2023-09-07
 */
@Getter
@Setter
@TableName("oms_order_return_reason")
@ApiModel(value = "OmsOrderReturnReason对象", description = "退货原因")
public class OmsOrderReturnReason implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("退货原因名")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("启用状态")
    private Boolean status;

    @ApiModelProperty("create_time")
    private LocalDateTime createTime;


}
