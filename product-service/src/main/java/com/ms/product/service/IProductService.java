package com.ms.product.service;

import com.ms.product.entity.Product;
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

    Product searchProduct(String keyword, int categoryId, String orderBy, int pageNum, int pageSize);

    Product productInfo(int productId);

    Product updateStock(int productId, int stock);

    Product secKillProduct(String username, int productId);
}
