package com.ms.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.common.constant.RedisKey;
import com.ms.common.enums.BizStatusCode;
import com.ms.common.exception.BizException;
import com.ms.product.domain.dto.CategoryTreeDto;
import com.ms.product.domain.entity.PmsCategory;
import com.ms.product.mapper.PmsCategoryMapper;
import com.ms.product.service.IPmsCategoryService;
import com.ms.product.domain.vo.CategoryParamVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品三级分类 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Service
public class PmsCategoryServiceImpl extends ServiceImpl<PmsCategoryMapper, PmsCategory> implements IPmsCategoryService {

    @Resource
    PmsCategoryBrandRelationServiceImpl categoryBrandRelationService;

    @Resource
    RedisTemplate redisTemplate;

    @CacheEvict(value = RedisKey.category_cache, allEntries = true)
    @Transactional
    @Override
    public void updateCategory(CategoryParamVo categoryParamVo) {
        PmsCategory pmsCategory = new PmsCategory();
        BeanUtils.copyProperties(categoryParamVo, pmsCategory);
        updateById(pmsCategory);
        if (StringUtils.hasText(pmsCategory.getName())) {
            categoryBrandRelationService.updateCategory(pmsCategory.getId(), pmsCategory.getName());
        }
    }

    @Cacheable(value = RedisKey.category_cache, key = "#root.method.name")
    @Override
    public List<CategoryTreeDto> getTreeList() throws JsonProcessingException {
        /*
        * 空结果缓存：缓存穿透
        * 设置随机过期时间：缓存雪崩
        * 加锁：缓存击穿
        * */
        String cache = (String) redisTemplate.opsForValue().get(RedisKey.category_cache);
        if (StringUtils.hasText(cache)) {
            return new ObjectMapper().readValue(cache, new TypeReference<List<CategoryTreeDto>>() {});
        }
        String uuid = UUID.randomUUID().toString();
        boolean locked = redisTemplate.opsForValue().setIfAbsent(RedisKey.category_lock, uuid, 300, TimeUnit.SECONDS);
        List<CategoryTreeDto> primaryDto;
        if (Boolean.TRUE.equals(locked)) {
            // 查询所有分类
            List<PmsCategory> categoryList = list();

            // 找出一级分类
            List<PmsCategory> primary = categoryList.stream().filter(category -> category.getParentId() == 0).collect(Collectors.toList());

            primaryDto = com.ms.common.utils.BeanUtils.copyList(primary, CategoryTreeDto.class);
            primaryDto.forEach(categoryTreeDto -> categoryTreeDto.setChildren(getCategoryChildren(categoryTreeDto, categoryList)));

//            redisTemplate.opsForValue().set(RedisKey.category_cache, new ObjectMapper().writeValueAsString(primaryDto));

            String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
            redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Collections.singletonList(RedisKey.category_lock), uuid);

            return primaryDto;
        }

        return null;

//        synchronized (this) {
//            // 查询所有分类
//            List<PmsCategory> categoryList = list();
//
//            // 找出一级分类
//            List<PmsCategory> primary = categoryList.stream().filter(category -> category.getParentId() == 0).collect(Collectors.toList());
//
//            List<CategoryTreeDto> primaryDto = com.ms.common.utils.BeanUtils.copyList(primary, CategoryTreeDto.class);
//            primaryDto.forEach(categoryTreeDto -> categoryTreeDto.setChildren(getCategoryChildren(categoryTreeDto, categoryList)));
//
//            redisTemplate.opsForValue().set(ProductConstant.category_cache, new ObjectMapper().writeValueAsString(primaryDto));
//
//            return primaryDto;
//        }
    }

    @Override
    public boolean deleteByIds(Integer[] ids) {
        List<PmsCategory> categoryList = list();
        for (Integer id: ids) {
            boolean res = categoryList.stream().anyMatch(c -> c.getParentId() == id.intValue());
            if (res) {
                throw new BizException(BizStatusCode.CATEGORY_HAS_CHILDREN);
            }
        }
        int deleted = baseMapper.deleteBatchIds(Arrays.asList(ids));
        return deleted > 0;
    }

    private List<CategoryTreeDto> getCategoryChildren(CategoryTreeDto primaryDto, List<PmsCategory> source) {
        List<PmsCategory> children = source.stream().filter(category -> category.getParentId().equals(primaryDto.getId())).collect(Collectors.toList());
        List<CategoryTreeDto> categoryTreeDtos = com.ms.common.utils.BeanUtils.copyList(children, CategoryTreeDto.class);
        categoryTreeDtos = categoryTreeDtos.stream().peek(categoryTreeDto1 -> categoryTreeDto1.setChildren(getCategoryChildren(categoryTreeDto1,source))).collect(Collectors.toList());
        return categoryTreeDtos;
    }
}
