package com.ms.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.product.domain.entity.PmsSpuImages;

import java.util.List;

/**
 * <p>
 * spu图片 服务类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
public interface IPmsSpuImagesService extends IService<PmsSpuImages> {

    void saveImage(Long id, List<String> images);
}
