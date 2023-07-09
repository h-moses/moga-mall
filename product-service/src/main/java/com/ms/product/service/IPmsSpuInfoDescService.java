package com.ms.product.service;

import com.ms.product.entity.PmsSpuInfoDesc;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * spu信息介绍 服务类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
public interface IPmsSpuInfoDescService extends IService<PmsSpuInfoDesc> {

    void saveSpuInfoDesc(PmsSpuInfoDesc pmsSpuInfoDesc);

}