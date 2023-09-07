package com.ms.product.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.product.domain.entity.PmsSkuImages;
import com.ms.product.mapper.PmsSkuImagesMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * sku图片 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Service
public class PmsSkuImagesServiceImpl extends ServiceImpl<PmsSkuImagesMapper, PmsSkuImages> implements IService<PmsSkuImages> {

}
