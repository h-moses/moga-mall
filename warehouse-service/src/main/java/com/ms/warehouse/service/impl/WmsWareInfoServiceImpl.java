package com.ms.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.warehouse.domain.entity.WmsWareInfo;
import com.ms.warehouse.mapper.WmsWareInfoMapper;
import com.ms.warehouse.service.IWmsWareInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 仓库信息 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-09
 */
@Service
public class WmsWareInfoServiceImpl extends ServiceImpl<WmsWareInfoMapper, WmsWareInfo> implements IWmsWareInfoService {

    @Override
    public Page<WmsWareInfo> queryPage(String key, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<WmsWareInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(key)) {
            queryWrapper.and(w -> {
                w.eq(WmsWareInfo::getId, key)
                        .or()
                        .like(WmsWareInfo::getName, key)
                        .or()
                        .like(WmsWareInfo::getAddress, key)
                        .or()
                        .like(WmsWareInfo::getAreacode, key);
            });
        }
        Page<WmsWareInfo> page = new Page<>(pageNum, pageSize);
        return page(page, queryWrapper);
    }
}
