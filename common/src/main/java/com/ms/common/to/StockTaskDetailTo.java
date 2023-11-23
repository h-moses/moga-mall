package com.ms.common.to;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockTaskDetailTo implements Serializable {

    private static final long serialVersionUID = 5880903413378285128L;

    private Long id;

    private Long skuId;

    private String skuName;

    private Integer skuNum;

    private Long taskId;

    private Long wareId;

    private Integer lockStatus;
}
