package com.ms.product.service.impl;

import com.ms.common.enums.BizStatusCode;
import com.ms.common.exception.BizException;
import com.ms.product.entity.Product;
import com.ms.product.mapper.ProductMapper;
import com.ms.product.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Product searchProduct(String keyword, int categoryId, String orderBy, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Product productInfo(int productId) {
        Product product = getById(productId);
        if (null == product) {
            throw new BizException(BizStatusCode.GOODS_NOT_EXIST);
        }
        return product;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Product updateStock(int productId, int stock) {
        Product product = getById(productId);
        if (null == product) {
            throw new BizException(BizStatusCode.GOODS_NOT_EXIST);
        }
        product.setStock(product.getStock() - stock);
        return product;
    }

    @Override
    public Product secKillProduct(String username, int productId) {
        return updateStock(productId, 1);
    }
}
