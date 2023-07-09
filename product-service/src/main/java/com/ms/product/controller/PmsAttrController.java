package com.ms.product.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.common.api.Response;
import com.ms.common.enums.BizStatusCode;
import com.ms.product.entity.PmsAttr;
import com.ms.product.service.impl.PmsAttrServiceImpl;
import com.ms.product.vo.AttrGroupRelationParamVo;
import com.ms.product.vo.AttrParamVo;
import com.ms.product.vo.AttrResponseVo;
import org.simpleframework.xml.Path;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

}
