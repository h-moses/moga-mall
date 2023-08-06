package com.ms.product.service;

import com.ms.product.domain.dto.ProductDto;
import com.ms.product.domain.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ms
 * @since 2023-06-14
 */
public interface IProductService extends IService<Product> {

    boolean saveProduct(ProductDto productDto);

}
