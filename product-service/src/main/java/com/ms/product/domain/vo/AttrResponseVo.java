package com.ms.product.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AttrResponseVo extends AttrParamVo {

    String categoryName;

    String attrGroupName;
}
