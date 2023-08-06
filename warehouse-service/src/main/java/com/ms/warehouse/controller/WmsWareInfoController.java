package com.ms.warehouse.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.common.api.Response;
import com.ms.warehouse.domain.entity.WmsWareInfo;
import com.ms.warehouse.service.impl.WmsWareInfoServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 仓库信息 前端控制器
 * </p>
 *
 * @author ms
 * @since 2023-07-09
 */
@RestController
@RequestMapping("/wms-ware-info")
public class WmsWareInfoController {

    @Resource
    WmsWareInfoServiceImpl wareInfoService;

    @GetMapping("/ware/page")
    public Response pageQuery(@RequestParam("key") String key,
                              @RequestParam("pageNum") Integer pageNum,
                              @RequestParam("pageSize") Integer pageSize) {
        Page<WmsWareInfo> page = wareInfoService.queryPage(key, pageNum, pageSize);
        return Response.SUCCESS(page);
    }
}
