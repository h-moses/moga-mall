package com.ms.product.mapper;

import com.ms.product.domain.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ms
 * @since 2023-06-14
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}
