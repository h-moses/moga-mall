package com.ms.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author ms
 * @since 2023-09-07
 */
@Getter
@Setter
@TableName("mq_message")
@ApiModel( description = "RabbitMQ的消息内容")
public class MqMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String messageId;

    private String content;

    private String toExchane;

    private String routingKey;

    private String classType;

    @ApiModelProperty("0-新建 1-已发送 2-错误抵达 3-已抵达")
    private Integer messageStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
