package com.ms.order.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayVo implements Serializable {

    private static final long serialVersionUID = -4044773654752040415L;

    String subject;

    String outTradeNo;

    String totalAmount;
}
