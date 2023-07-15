package com.ms.product.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.common.api.Response;
import com.ms.common.enums.BizStatusCode;
import com.ms.product.entity.PmsAttr;
import com.ms.product.entity.PmsProductAttrValue;
import com.ms.product.service.impl.PmsAttrServiceImpl;
import com.ms.product.service.impl.PmsProductAttrValueServiceImpl;
import com.ms.product.vo.AttrGroupRelationParamVo;
import com.ms.product.vo.AttrParamVo;
import com.ms.product.vo.AttrResponseVo;
import io.swagger.annotations.ApiOperation;
import org.simpleframework.xml.Path;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@RestController
@RequestMapping("/pms-attr")
public class PmsAttrController {

    @Resource
    PmsAttrServiceImpl attrService;

    @Resource
    PmsProductAttrValueServiceImpl productAttrValueService;

    @PostMapping("/attr/add")
    public Response addAttr(@RequestBody AttrParamVo attrParamVo) {
        attrService.saveAttr(attrParamVo);
        return Response.SUCCESS(BizStatusCode.SUCCESS);
    }

    @GetMapping("/attr/page")
    public Response baseAttrPage(@RequestParam("categoryId") Long categoryId,
                                 @RequestParam("attrType") Integer attrType,
                                 @RequestParam("pageNum") Integer pageNum,
                                 @RequestParam("pageSize") Integer pageSize){
        Page<AttrResponseVo> attrResponseVoPage = attrService.attrPage(categoryId, attrType, pageNum, pageSize);
        return Response.SUCCESS(attrResponseVoPage);
    }

    @GetMapping("/attr/info/{attrId}")
    public Response attrInfo(@PathVariable("attrId") Long attrId) {
        AttrResponseVo attrInfo = attrService.getAttrInfo(attrId);
        return Response.SUCCESS(attrInfo);
    }

    @PostMapping("/baseAttr/update")
    public Response updateAttr(@RequestBody AttrParamVo attrParamVo) {
        attrService.updateAttr(attrParamVo);
        return Response.SUCCESS("属性更新成功");
    }

    @ApiOperation(value = "获取spu规格参数")
    @GetMapping("/baseAttr/{spuId}")
    public Response baseAttrBySpuId(@PathVariable("spuId") Long spuId) {
        List<PmsProductAttrValue> res = productAttrValueService.queryBySpuId(spuId);
        return Response.SUCCESS(res);
    }

    @PostMapping("/updatebaseattr")
    public Response updateAttr(@RequestParam("spuId") Long spuId,
                    @RequestBody List<PmsProductAttrValue> productAttrValues) {
        productAttrValueService.updateAttr(spuId, productAttrValues);
        return Response.SUCCESS(BizStatusCode.SUCCESS);
    }
}
