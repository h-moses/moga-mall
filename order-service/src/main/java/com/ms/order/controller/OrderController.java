package com.ms.order.controller;


import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kms.aliyun.credentials.http.HttpRequest;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import com.ms.common.api.Response;
import com.ms.order.entity.OrderEntity;
import com.ms.order.service.IOrderService;
import com.ms.order.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author ms
 * @since 2023-09-07
 */
@Slf4j
@Api(tags = "订单接口")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    IOrderService orderService;

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
    @PostMapping("/create")
    public RedirectView createOrder(OrderSubmitVo orderVo) {
        OrderSubmitResVo orderSubmitResVo = orderService.submitOrder(orderVo);
        if (orderSubmitResVo.getCode() == 200) {
            return new RedirectView("/payment");
        }
        return new RedirectView("/login");
    }

    @ApiOperation(value = "查询订单信息")
    @GetMapping("/info/{orderSn}")
    public Response<OrderEntity> queryOrderInfo(@PathVariable("orderSn") String orderSn) {
       return Response.SUCCESS(orderService.queryOrderStatus(orderSn));
    }

    /**
     * TODO 跳转到支付结果页（订单列表页）
     * @param orderSn
     * @return
     */
    @ApiOperation(value = "支付订单")
    @GetMapping(value = "/pay", produces = "text/html")
    public String payOrder(@RequestParam("orderSn") String orderSn) {
        PayVo payVo = orderService.queryPayInfoByOrderSn(orderSn);

        AlipayTradeWapPayResponse response;
        try {
            response = Factory.Payment.Wap().pay(payVo.getSubject(), payVo.getOutTradeNo(), payVo.getTotalAmount(), "","");
        } catch (Exception e) {
            log.error("支付宝付款调用失败，原因：" + e.getMessage());
            throw new RuntimeException("网络异常,请刷新后重试");
        }
        return response.getBody();
    }

    @ApiOperation(value = "支付结果异步通知")
    @PostMapping(value = "/pay/notify")
    public String notifyPayResult(@RequestParam Map<String, String> parameters) throws IllegalAccessException {
        return orderService.handlePayResult(parameters);
    }
}