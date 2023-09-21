package com.ms.common.to;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StockLockTo implements Serializable {

    private static final long serialVersionUID = 4896283349124003898L;

    Long id;

    StockTaskDetailTo detailTo;
}
