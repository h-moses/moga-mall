package com.ms.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.product.domain.entity.PmsSpuImages;
import com.ms.product.mapper.PmsSpuImagesMapper;
import com.ms.product.service.IPmsSpuImagesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * spu图片 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Service
public class PmsSpuImagesServiceImpl extends ServiceImpl<PmsSpuImagesMapper, PmsSpuImages> implements IPmsSpuImagesService {

    @Override
    public void saveImage(Long id, List<String> images) {
        if (null == images || images.isEmpty()) {
            return;
        }
        List<PmsSpuImages> imagesList = images.stream().map(image -> {
            PmsSpuImages spuImages = new PmsSpuImages();
            spuImages.setSpuId(id);
            spuImages.setImgUrl(image);
            return spuImages;
        }).collect(Collectors.toList());

        saveBatch(imagesList);
    }
}
