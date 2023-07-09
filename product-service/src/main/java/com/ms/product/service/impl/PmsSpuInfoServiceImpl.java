package com.ms.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.common.to.es.SkuEsModel;
import com.ms.product.entity.*;
import com.ms.product.mapper.PmsSpuInfoMapper;
import com.ms.product.service.IPmsSpuInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.product.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * spu信息 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Service
public class PmsSpuInfoServiceImpl extends ServiceImpl<PmsSpuInfoMapper, PmsSpuInfo> implements IPmsSpuInfoService {

    @Resource
    PmsSkuInfoServiceImpl skuInfoService;

    @Resource
    PmsBrandServiceImpl brandService;

    @Resource
    PmsCategoryServiceImpl categoryService;

    @Resource
    PmsProductAttrValueServiceImpl productAttrValueService;

    @Resource
    PmsSpuInfoDescServiceImpl spuInfoDescService;

    @Resource
    PmsSpuImagesServiceImpl spuImagesService;

    @Resource
    PmsAttrServiceImpl attrService;

    @Resource
    PmsSkuImagesServiceImpl skuImagesService;

    @Resource
    PmsSkuSaleAttrValueServiceImpl skuSaleAttrValueService;

    @Transactional
    @Override
    public void saveSpuInfo(SaveSpuVo saveSpuVo) {
        // 保存spu的基本信息
        PmsSpuInfo spuInfo = new PmsSpuInfo();
        BeanUtils.copyProperties(saveSpuVo, spuInfo);
        spuInfo.setCreateTime(new Date());
        spuInfo.setUpdateTime(new Date());
        saveBaseSpuInfo(spuInfo);

        // 保存spu的描述图片
        List<String> decript = saveSpuVo.getDecript();
        PmsSpuInfoDesc pmsSpuInfoDesc = new PmsSpuInfoDesc();
        pmsSpuInfoDesc.setSpuId(spuInfo.getId());
        pmsSpuInfoDesc.setDecript(String.join(",", decript));
        spuInfoDescService.saveSpuInfoDesc(pmsSpuInfoDesc);

        // 保存spu的图片集
        List<String> images = saveSpuVo.getImages();
        spuImagesService.saveImage(spuInfo.getId(), images);

        // 保存spu的规格参数
        List<BaseAttrVo> baseAttrs = saveSpuVo.getBaseAttrs();
        List<PmsProductAttrValue> objectList = baseAttrs.stream().map(baseAttrVo -> {
            PmsProductAttrValue attrValue = new PmsProductAttrValue();
            attrValue.setAttrId(baseAttrVo.getAttrId());
            PmsAttr attr = attrService.getById(baseAttrVo.getAttrId());
            attrValue.setAttrName(attr.getAttrName());
            attrValue.setAttrValue(baseAttrVo.getAttrValues());
            attrValue.setQuickShow(baseAttrVo.getShowDesc());
            attrValue.setSpuId(spuInfo.getId());
            return attrValue;
        }).collect(Collectors.toList());
        productAttrValueService.saveProductAttr(objectList);

        // 保留积分信息

        // 保存所有sku信息
        List<SkuVo> skus = saveSpuVo.getSkus();
        // 保存sku的基本信息
        if (null != skus && !skus.isEmpty()) {
            skus.forEach(skuVo -> {

                String defaultImage = skuVo.getImages().stream().filter(image -> image.getDefaultImg() == 1).findFirst().map(Image::getImgUrl).get();
                PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
                BeanUtils.copyProperties(skuVo, pmsSkuInfo);
                pmsSkuInfo.setBrandId(spuInfo.getBrandId());
                pmsSkuInfo.setCategoryId(spuInfo.getCategoryId());
                pmsSkuInfo.setSaleCount(0L);
                pmsSkuInfo.setSpuId(spuInfo.getId());
                pmsSkuInfo.setSkuDefaultImg(defaultImage);

                skuInfoService.saveSkuInfo(pmsSkuInfo);

                Long skuId = pmsSkuInfo.getSkuId();

                List<PmsSkuImages> skuImagesList = skuVo.getImages().stream().map(image -> {
                    PmsSkuImages skuImages = new PmsSkuImages();
                    skuImages.setSkuId(skuId);
                    skuImages.setImgUrl(image.getImgUrl());
                    skuImages.setDefaultImg(image.getDefaultImg());
                    return skuImages;
                }).filter(image -> StringUtils.hasText(image.getImgUrl())).collect(Collectors.toList());

                // sku的图片信息
                skuImagesService.saveBatch(skuImagesList);

                // sku的销售属性
                List<AttrVo> attrVoList = skuVo.getAttr();
                List<PmsSkuSaleAttrValue> attrValueList = attrVoList.stream().map(attrVo -> {
                    PmsSkuSaleAttrValue attrValue = new PmsSkuSaleAttrValue();
                    BeanUtils.copyProperties(attrVo, attrValue);
                    attrValue.setSkuId(skuId);

                    return attrValue;
                }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(attrValueList);
            });


        }
    }

    private void saveSpuInfoDesc(PmsSpuInfoDesc pmsSpuInfoDesc) {

    }

    private void saveBaseSpuInfo(PmsSpuInfo spuInfo) {

    }

    @Override
    public void upShelf(Long spuId) {
        List<SkuEsModel> models = new ArrayList<>();
        List<PmsSkuInfo> infoList = skuInfoService.getSkuBySpuId(spuId);
        List<SkuEsModel> esModelList = infoList.stream().map(pmsSkuInfo -> {
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(pmsSkuInfo, skuEsModel);
            skuEsModel.setSkuPrice(pmsSkuInfo.getPrice());
            skuEsModel.setSkuImage(pmsSkuInfo.getSkuDefaultImg());

            // 查询库存系统是否有库存

            // 热度评分

            // 查询品牌
            PmsBrand brand = brandService.getById(skuEsModel.getBrandId());
            skuEsModel.setBrandName(brand.getName());
            skuEsModel.setBrandImage(brand.getLogo());
            PmsCategory category = categoryService.getById(skuEsModel.getCategoryId());
            skuEsModel.setCategoryName(category.getName());

            // 查询sku的所有可以被检索的规格属性
//            productAttrValueService
            return skuEsModel;
        }).collect(Collectors.toList());
    }

    @Override
    public Page<PmsSpuInfo> searchSpu(String key, Long categoryId, Long brandId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<PmsSpuInfo> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(key)) {
            queryWrapper.and(w -> {w.eq(PmsSpuInfo::getId, key).or().like(PmsSpuInfo::getSpuName, key);});
        }
        if (StringUtils.hasText(String.valueOf(categoryId))) {
            queryWrapper.eq(PmsSpuInfo::getCategoryId, categoryId);
        }
        if (StringUtils.hasText(String.valueOf(brandId))) {
            queryWrapper.eq(PmsSpuInfo::getBrandId, brandId);
        }
        Page<PmsSpuInfo> infoPage = new Page<>(pageNum, pageSize);
        return page(infoPage, queryWrapper);
    }
}
