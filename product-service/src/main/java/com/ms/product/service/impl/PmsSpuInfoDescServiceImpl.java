package com.ms.product.service.impl;

import com.ms.product.domain.entity.PmsSpuInfoDesc;
import com.ms.product.mapper.PmsSpuInfoDescMapper;
import com.ms.product.service.IPmsSpuInfoDescService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * spu信息介绍 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Service
public class PmsSpuInfoDescServiceImpl extends ServiceImpl<PmsSpuInfoDescMapper, PmsSpuInfoDesc> implements IPmsSpuInfoDescService {

    @Override
    public void saveSpuInfoDesc(PmsSpuInfoDesc pmsSpuInfoDesc) {
        save(pmsSpuInfoDesc);
    }
}
