package com.ms.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.common.api.Response;
import com.ms.common.enums.ProductStatus;
import com.ms.common.to.SkuEsModel;
import com.ms.product.domain.entity.*;
import com.ms.product.domain.vo.*;
import com.ms.product.feign.SearchFeignService;
import com.ms.product.feign.WareFeignService;
import com.ms.product.mapper.PmsSpuInfoMapper;
import com.ms.product.service.IPmsSpuInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    @Resource
    WareFeignService wareFeignService;

    @Resource
    SearchFeignService searchFeignService;

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
        List<PmsSkuInfo> infoList = skuInfoService.getSkuBySpuId(spuId);

        // 查询当前sku的所有可以用来被检索的规格属性
        List<PmsProductAttrValue> productAttrValues = productAttrValueService.queryBySpuId(spuId);
        List<Long> attrIdList = productAttrValues.stream().map(PmsProductAttrValue::getAttrId).collect(Collectors.toList());

        // 找出可以快速检索的id
        List<Long> searchAttr = attrService.searchAttr(attrIdList);

        // 根据id找出快速检索的属性
        List<SkuEsModel.Attrs> attrs = productAttrValues.stream().filter(item -> searchAttr.contains(item.getAttrId())).map(it -> {
            SkuEsModel.Attrs attrs1 = new SkuEsModel.Attrs();
            BeanUtils.copyProperties(it, attrs1);
            return attrs1;
        }).collect(Collectors.toList());

        List<Long> skuIds = infoList.stream().map(PmsSkuInfo::getSkuId).collect(Collectors.toList());
        Response<List<StockVo>> response = wareFeignService.HasStockBySkuId(skuIds);
        List<StockVo> responseData = response.getData();
        Map<Long, Boolean> stockMap = responseData.stream().collect(Collectors.toMap(StockVo::getSkuId, StockVo::getIsStocked));

        List<SkuEsModel> esModelList = infoList.stream().map(pmsSkuInfo -> {
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(pmsSkuInfo, skuEsModel);
            skuEsModel.setSkuPrice(pmsSkuInfo.getPrice());
            skuEsModel.setSkuImage(pmsSkuInfo.getSkuDefaultImg());

            // 查询库存系统是否有库存
            skuEsModel.setHasStock(stockMap.get(pmsSkuInfo.getSkuId()));
            // 热度评分
            skuEsModel.setHotScore(0L);

            // 查询品牌
            PmsBrand brand = brandService.getById(skuEsModel.getBrandId());
            skuEsModel.setBrandName(brand.getName());
            skuEsModel.setBrandImage(brand.getLogo());

            PmsCategory category = categoryService.getById(skuEsModel.getCategoryId());
            skuEsModel.setCategoryName(category.getName());

            // 查询sku的所有可以被检索的规格属性
            skuEsModel.setAttrs(attrs);
//            productAttrValueService
            return skuEsModel;
        }).collect(Collectors.toList());

        Response upProductStatus = searchFeignService.upProductStatus(esModelList);
        if (upProductStatus.getCode() == 0) {
            // 上架成功，修改当前spu的状态
            baseMapper.updateStatus(spuId, ProductStatus.UP.ordinal());
        } else {
            // 重复调用，幂等性？
        }
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

    @Override
    public PmsSpuInfo querySpuInfoBySkuId(Long skuId) {
        PmsSkuInfo skuInfo = skuInfoService.getById(skuId);
        return getById(skuInfo.getSpuId());
    }
}
