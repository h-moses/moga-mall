package com.ms.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ms.common.enums.BizStatusCode;
import com.ms.common.exception.BizException;
import com.ms.product.dto.ProductDto;
import com.ms.product.entity.Product;
import com.ms.product.mapper.ProductMapper;
import com.ms.product.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-06-14
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Override
    public boolean saveProduct(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        Long count = getByProductName(product.getName());
        if (null != count && count > 0) {
            throw new BizException(BizStatusCode.GOODS_ALREADY_EXIST);
        }
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
        return save(product);
    }

    Long getByProductName(String name) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getName, name);
        return count(queryWrapper);
    }
}