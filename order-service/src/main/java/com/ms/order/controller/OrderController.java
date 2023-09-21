package com.ms.order.controller;


import com.ms.common.api.Response;
import com.ms.order.service.IOmsOrderService;
import com.ms.order.vo.OrderDetailVo;
import com.ms.order.vo.OrderSubmitResVo;
import com.ms.order.vo.OrderSubmitVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author ms
 * @since 2023-09-07
 */
@Api(tags = "订单接口")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    IOmsOrderService orderService;

    @ApiOperation(value = "确认订单【订单详情】")
    @GetMapping("/confirm")
    public Response<OrderDetailVo> confirm() throws ExecutionException, InterruptedException {
        OrderDetailVo orderDetailVo = orderService.queryOrderDetail();
        return Response.SUCCESS(orderDetailVo);
    }

    /**
     * 下单流程：令牌验证、锁库存
     * 成功跳转到：支付
     * 失败则进行：兜底
     *
     * @param orderVo
     * @return
     */
    @ApiOperation(value = "提交订单")
    @PostMapping("/submit")
    public RedirectView submitOrder(OrderSubmitVo orderVo) {
        OrderSubmitResVo orderSubmitResVo = orderService.submitOrder(orderVo);
        if (orderSubmitResVo.getCode() == 200) {
            return new RedirectView("/payment");
        }
        return new RedirectView("/login");
    }
}
