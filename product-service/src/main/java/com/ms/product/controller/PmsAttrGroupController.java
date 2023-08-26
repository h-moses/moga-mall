package com.ms.product.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.common.api.Response;
import com.ms.product.domain.entity.PmsAttr;
import com.ms.product.domain.entity.PmsAttrGroup;
import com.ms.product.domain.vo.AttrGroupDetailVo;
import com.ms.product.domain.vo.AttrGroupRelationParamVo;
import com.ms.product.service.IPmsAttrAttrgroupRelationService;
import com.ms.product.service.IPmsAttrGroupService;
import com.ms.product.service.IPmsAttrService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 属性分组 前端控制器
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@RestController
@RequestMapping("/product/attr_group")
public class PmsAttrGroupController {

    @Resource
    private IPmsAttrGroupService attrGroupService;

    @Resource
    private IPmsAttrAttrgroupRelationService relationService;

    @Resource
    private IPmsAttrService attrService;

    @ApiOperation(value = "分页模糊查询分类对应的属性分组")
    @GetMapping("/listAttGroup")
    public Response listAttGroup(@RequestParam("categoryId") Long categoryId,
                                 @RequestParam("keyword") String keyword,
                                 @RequestParam("pageNum") Integer pageNum,
                                 @RequestParam("pageSize") Integer pageSize) {
        Page<PmsAttrGroup> page = attrGroupService.pageAttrGroup(categoryId, keyword, pageNum, pageSize);
        return Response.SUCCESS(page);
    }

    @ApiOperation(value = "查询特定分组的属性信息")
    @GetMapping("/relation/{groupId}")
    public Response getRelation(@PathVariable("groupId") Long attrGroupId) {
        List<PmsAttr> relatedAttr = attrService.getRelatedAttr(attrGroupId);
        return Response.SUCCESS(relatedAttr);
    }

    @ApiOperation(value = "分页查询特定分组未关联的属性")
    @GetMapping("/unrelated/{groupId}")
    public Response unRelatedAttr(@PathVariable("groupId") Long attrGroupId,
                                  @RequestParam("pageNum") Integer pageNum,
                                  @RequestParam("pageSize") Integer pageSize) {
        Page<PmsAttr> unrelatedAttr = attrService.getUnRelatedAttr(attrGroupId, pageNum, pageSize);
        return Response.SUCCESS(unrelatedAttr);
    }

    @ApiOperation(value = "删除分组和属性的关联")
    @PostMapping("/relation/delete")
    public Response deleteRelation(@RequestBody AttrGroupRelationParamVo[] attrGroupRelationParamVos) {
        attrService.deleteRelation(attrGroupRelationParamVos);
        return Response.SUCCESS("关联删除成功");
    }

    @ApiOperation(value = "添加分组和属性的关联")
    @PostMapping("/relation/add")
    public Response addRelation(@RequestBody AttrGroupRelationParamVo[] attrGroupRelationParamVos) {
        relationService.addRelation(attrGroupRelationParamVos);
        return Response.SUCCESS("关联添加成功");
    }

    @ApiOperation(value = "获取分组及相关属性")
    @GetMapping("/groupWithAttr/{categoryId}")
    public Response getGroupWithAttr(@PathVariable("categoryId") Long categoryId) {
        List<AttrGroupDetailVo> groupDetail = attrGroupService.getGroupDetail(categoryId);
        return Response.SUCCESS(groupDetail);
    }
}
