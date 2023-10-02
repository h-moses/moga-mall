package com.ms.order.vo;

import com.ms.order.entity.OrderEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderSubmitResVo {


    @ApiModelProperty(value = "业务状态码")
    Integer code;

    OrderEntity order;

}
