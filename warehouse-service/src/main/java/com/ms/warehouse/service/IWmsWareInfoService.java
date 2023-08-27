package com.ms.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.warehouse.domain.entity.WmsWareInfo;

/**
 * <p>
 * 仓库信息 服务类
 * </p>
 *
 * @author ms
 * @since 2023-07-09
 */
public interface IWmsWareInfoService extends IService<WmsWareInfo> {

    Page<WmsWareInfo> queryPage(String key, Integer pageNum, Integer pageSize);
}
