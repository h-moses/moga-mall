package com.ms.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.product.domain.dto.ProductDto;
import com.ms.product.domain.entity.Product;

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
