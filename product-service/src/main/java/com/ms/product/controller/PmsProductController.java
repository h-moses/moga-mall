package com.ms.product.controller;


import com.ms.common.api.Response;
import com.ms.common.enums.BizStatusCode;
import com.ms.common.enums.SysExceptionCode;
import com.ms.common.exception.SysException;
import com.ms.product.dto.ProductDto;
import com.ms.product.service.IMinioService;
import com.ms.product.service.IProductService;
import com.ms.product.vo.ProductParamVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ms
 * @since 2023-06-14
 */
@Api(tags = "商品服务")
@RestController
@RequestMapping("/product")
public class PmsProductController {


    @Resource
    private IMinioService minioService;

    @Resource
    private IProductService productService;

    @ApiOperation(value = "添加商品")
    @PostMapping("/add_product")
    public Response addProduct(@Valid ProductParamVo productParamVo) {
        MultipartFile image = productParamVo.getImage();
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(productParamVo, productDto);
        try {
            minioService.uploadObject(image.getOriginalFilename(), image.getInputStream(), image.getSize(), image.getContentType());
            String fileUrl = minioService.getFileUrl(image.getOriginalFilename());
            productDto.setImage(fileUrl);
        } catch (Exception e) {
            throw new SysException(SysExceptionCode.UPLOAD_OSS_ERROR.getCode(), "商品图片上传失败");
        }
        boolean res = productService.saveProduct(productDto);
        if (res) {
            return Response.SUCCESS(BizStatusCode.SUCCESS);
        } else {
            throw new SysException(SysExceptionCode.SAVE_DB_ERROR.getCode(), "商品添加失败");
        }
    }
}
