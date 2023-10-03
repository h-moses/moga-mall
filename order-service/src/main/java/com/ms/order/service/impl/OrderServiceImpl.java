package com.ms.order.service.impl;

import com.alipay.easysdk.factory.Factory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.ms.common.api.Response;
import com.ms.common.constant.RedisKey;
import com.ms.common.enums.BizStatusCode;
import com.ms.common.enums.OrderStatus;
import com.ms.common.to.OrderTo;
import com.ms.common.utils.BeanUtils;
import com.ms.order.configuration.RabbitConfiguration;
import com.ms.order.entity.OrderEntity;
import com.ms.order.entity.OrderItemEntity;
import com.ms.order.feign.CartServiceFeign;
import com.ms.order.feign.ProductServiceFeign;
import com.ms.order.feign.UserServiceFeign;
import com.ms.order.feign.WarehouseServiceFeign;
import com.ms.order.interceptor.OrderInterceptor;
import com.ms.order.mapper.OmsOrderMapper;
import com.ms.order.service.IOmsOrderItemService;
import com.ms.order.service.IOrderService;
import com.ms.order.to.OrderCreationTo;
import com.ms.order.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-09-07
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OmsOrderMapper, OrderEntity> implements IOrderService {

    ThreadLocal<OrderSubmitVo> submitVoThreadLocal = new ThreadLocal<>();

    @Resource
    UserServiceFeign userServiceFeign;

    @Resource
    ProductServiceFeign productServiceFeign;

    @Resource
    CartServiceFeign cartServiceFeign;

    @Resource
    WarehouseServiceFeign warehouseServiceFeign;

    @Resource
    ThreadPoolExecutor executor;

    @Resource
    StringRedisTemplate redisTemplate;

    @Resource
    RabbitTemplate rabbitTemplate;

    @Resource
    IOmsOrderItemService orderItemService;

    @Override
    public OrderEntity queryOrderStatus(String orderSn) {
        return getOne(new LambdaQueryWrapper<OrderEntity>().eq(OrderEntity::getOrderSn, orderSn));
    }

    /**
     * 定时关闭订单
     * @param order
     */
    @Override
    public void closeOrder(OrderEntity order) {
        OrderEntity orderEntity = getById(order.getId());
        if (OrderStatus.CREATED.getCode() == orderEntity.getStatus()) {
            OrderEntity newOrder = new OrderEntity();
            newOrder.setId(orderEntity.getId());
            newOrder.setStatus(OrderStatus.CANCELLED.getCode());
            updateById(newOrder);

            // 如果因网络等原因，导致库存先于订单解锁，会出现问题，直接发送一条消息给库存队列
            OrderTo orderTo = new OrderTo();
            org.springframework.beans.BeanUtils.copyProperties(order, orderTo);
            try {
                // 创建一张消息记录表
                rabbitTemplate.convertAndSend(RabbitConfiguration.ORDER_EVENT_EXCHANGE, RabbitConfiguration.CANCEL_ROUTING_KEY, orderTo);
            } catch (Exception e) {
                // 保证可靠消息

            }
        }
    }

    @Override
    public PayVo queryPayInfoByOrderSn(String orderSn) {
        PayVo payVo = new PayVo();
        OrderEntity orderEntity = queryOrderStatus(orderSn);

        BigDecimal payAmount = orderEntity.getTotalAmount().setScale(2, RoundingMode.UP);
        payVo.setTotalAmount(String.valueOf(payAmount));
        payVo.setOutTradeNo(orderEntity.getOrderSn());

        List<OrderItemEntity> itemEntityList = orderItemService.list(new LambdaQueryWrapper<OrderItemEntity>().eq(OrderItemEntity::getOrderSn, orderSn));
        payVo.setSubject(itemEntityList.get(0).getSkuName());

        return payVo;
    }

    @Override
    public String handlePayResult(Map<String, String> parameters) {
        log.info("parameters: " + parameters.toString());

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        try {
            //商户订单号
            String out_trade_no = new String(parameters.get("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //交易状态
            String trade_status = new String(parameters.get("trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            //计算得出通知验证结果
            //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
            Boolean verify_result = Factory.Payment.Common().verifyNotify(parameters);

            if(verify_result){//验证成功
                //////////////////////////////////////////////////////////////////////////////////////////
                //请在这里加上商户的业务逻辑程序代码

                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

                if(("TRADE_FINISHED").equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)){
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    //如果有做过处理，不执行商户的业务程序

                    // 修改订单状态
                    log.info("编号为{}的订单支付成功，正在修改订单状态", out_trade_no);
                    OrderEntity orderEntity = queryOrderStatus(out_trade_no);
                    orderEntity.setStatus(OrderStatus.PAID.getCode());
                    updateById(orderEntity);
                    // 扣减商品库存
                    log.info("编号为{}的订单状态修改成功，正在扣减商品库存", out_trade_no);
                    warehouseServiceFeign.deduction(out_trade_no);
                    //注意：
                    //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                    //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。

                    log.info("编号为{}的订单处理完成", out_trade_no);
                }
                return "success";

                //////////////////////////////////////////////////////////////////////////////////////////
            }else{//验证失败
                return "fail";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OrderDetailVo queryOrderDetail() throws ExecutionException, InterruptedException {
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        String claim = OrderInterceptor.THREAD_LOCAL_ORDER.get();
        Long userId = Long.valueOf(claim.split("_")[0]);

        // 获取主线程的请求信息
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        // 远程查询收货地址
        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
            Response<List<UserAddressVo>> userAddressVoResponse = userServiceFeign.queryAddress(userId);
            orderDetailVo.setAddressVoList(userAddressVoResponse.getData());
        });
        // 远程查询订单的商品信息
        CompletableFuture<Void> cartFuture = CompletableFuture.runAsync(() -> {
            // 在异步线程中设置新的请求信息，内部使用ThreadLocal进行维护
            RequestContextHolder.setRequestAttributes(requestAttributes);
            Response<CartVo> cartVoResponse = cartServiceFeign.queryCheckedItem();
            List<CartItemVo> cartItemList = cartVoResponse.getData().getCartItemList();
            List<OrderItemVo> orderItemVoList = BeanUtils.copyList(cartItemList, OrderItemVo.class);
            orderDetailVo.setOrderItemVoList(orderItemVoList);
            // 设置订单总价
            orderDetailVo.setTotalPrice(cartVoResponse.getData().getTotalAmount());
        }, executor).thenRunAsync(() -> {
            // 在异步线程中设置新的请求信息，内部使用ThreadLocal进行维护
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<OrderItemVo> orderItemVoList = orderDetailVo.getOrderItemVoList();
            List<Long> skuIdList = orderItemVoList.stream().map(OrderItemVo::getSkuId).collect(Collectors.toList());
            Response<List<StockVo>> stockResponse = warehouseServiceFeign.HasStockBySkuId(skuIdList);
            List<StockVo> stockVoList = stockResponse.getData();
            Map<Long, Boolean> stockMap = stockVoList.stream().collect(Collectors.toMap(StockVo::getSkuId, StockVo::getHasStock));
            orderDetailVo.setStockMap(stockMap);
        }, executor);

        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(RedisKey.USER_ORDER_TOKEN_PREFiX + claim, token, 10, TimeUnit.MINUTES);
        orderDetailVo.setOrderToken(token);

        CompletableFuture.allOf(addressFuture, cartFuture).get();

        return orderDetailVo;
    }

//    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderSubmitResVo submitOrder(OrderSubmitVo orderVo) {
        submitVoThreadLocal.set(orderVo);
        OrderSubmitResVo orderSubmitResVo = new OrderSubmitResVo();
        String claim = OrderInterceptor.THREAD_LOCAL_ORDER.get();
        long userId = Long.parseLong(claim.split("_")[0]);
        String orderToken = orderVo.getOrderToken();
        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                "    return redis.call(\"del\",KEYS[1])\n" +
                "else\n" +
                "    return 0\n" +
                "end";
        // 0 - failure, 1 - success，原子判断
        Long executed = redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Collections.singletonList(RedisKey.USER_ORDER_TOKEN_PREFiX + claim), orderToken);
        if (null != executed && executed == 1L) {
            // 创建订单
            OrderCreationTo order = createOrder();

            // TODO 后端计算与前端页面的价格验证

            // 订单保存到数据库
            saveOrder(order);

            // 库存锁定
            StockLockVo stockLockVo = new StockLockVo();
            stockLockVo.setOrderSn(order.getOrder().getOrderSn());
            List<OrderItemVo> orderItemVoList = order.getOrderItemList().stream().map(item -> {
                OrderItemVo orderItemVo = new OrderItemVo();
                orderItemVo.setSkuId(item.getSkuId());
                orderItemVo.setCount(item.getSkuQuantity());
                orderItemVo.setTitle(item.getSkuName());
                orderItemVo.setSkuAttr(new ArrayList<>());
                orderItemVo.setImage("");
                orderItemVo.setPrice(BigDecimal.valueOf(0));
                orderItemVo.setTotalPrice(BigDecimal.valueOf(0));
                return orderItemVo;
            }).collect(Collectors.toList());
            stockLockVo.setOrderItemVoList(orderItemVoList);
            Response<List<StockLockResVo>> response = warehouseServiceFeign.lockStock(stockLockVo);
            if (0 == response.getCode()) {
                orderSubmitResVo.setCode(200);
                orderSubmitResVo.setOrder(order.getOrder());
            } else {
                // 锁定失败
                orderSubmitResVo.setCode(response.getCode());
            }
//            int i = 10 / 0;

            // TODO 订单创建成功，发送定时取消订单消息
            rabbitTemplate.convertAndSend(RabbitConfiguration.ORDER_EVENT_EXCHANGE, RabbitConfiguration.CREATE_ROUTING_KEY, order.getOrder());
        } else {
            orderSubmitResVo.setCode(BizStatusCode.ORDER_NOT_EXIST.getCode());
        }
        return orderSubmitResVo;
    }

    private void saveOrder(OrderCreationTo order) {
        OrderEntity orderEntity = order.getOrder();
        orderEntity.setModifyTime(LocalDateTime.now());
        save(orderEntity);

        List<OrderItemEntity> orderItemList = order.getOrderItemList();
        orderItemService.saveBatch(orderItemList);
    }

    private OrderCreationTo createOrder() {
        OrderCreationTo orderCreationTo = new OrderCreationTo();
        OrderSubmitVo orderSubmitVo = submitVoThreadLocal.get();

        // 订单号
        String orderSn = IdWorker.getTimeId();
        OrderEntity entity = new OrderEntity();
        entity.setMemberId(Long.valueOf(OrderInterceptor.THREAD_LOCAL_ORDER.get().split("_")[0]));
        entity.setOrderSn(orderSn);

        // 获取收货地址信息
        Response<UserAddressVo> userAddressVoResponse = userServiceFeign.queryAddressById(orderSubmitVo.getAddressId());
        UserAddressVo addressVo = userAddressVoResponse.getData();
        entity.setReceiverCity(addressVo.getCity());
        entity.setReceiverDetailAddress(addressVo.getDetail());
        entity.setReceiverName(addressVo.getName());
        entity.setReceiverPhone(addressVo.getPhone());
        entity.setReceiverPostCode(addressVo.getPostCode());
        entity.setReceiverProvince(addressVo.getProvince());
        entity.setReceiverRegion(addressVo.getDistrict());

        // 获取所有订单项信息
        List<OrderItemEntity> orderItemList = buildOrderItems(orderSn);

        // 订单价格
        assert orderItemList != null;
        computePrice(entity, orderItemList);

        // 订单状态
        entity.setStatus(OrderStatus.CREATED.getCode());
        entity.setDeleteStatus(0);
        entity.setAutoConfirmDay(7);

        orderCreationTo.setOrder(entity);
        orderCreationTo.setOrderItemList(orderItemList);

        return orderCreationTo;
    }

    private void computePrice(OrderEntity order, List<OrderItemEntity> orderItemList) {
        BigDecimal actual = new BigDecimal("0.0");
        BigDecimal coupon = new BigDecimal("0.0");
        BigDecimal promotion = new BigDecimal("0.0");
        BigDecimal integral = new BigDecimal("0.0");

        for (OrderItemEntity orderItem: orderItemList) {
            actual = actual.add(orderItem.getRealAmount());
            coupon = coupon.add(orderItem.getCouponAmount());
            promotion = promotion.add(orderItem.getPromotionAmount());
            integral = integral.add(orderItem.getIntegrationAmount());
        }

        order.setTotalAmount(actual);
        order.setPromotionAmount(promotion);
        order.setIntegrationAmount(integral);
        order.setCouponAmount(coupon);
    }

    /**
     * 构建订单项数据
     */
    private List<OrderItemEntity> buildOrderItems(String orderSn) {
        Response<CartVo> cartVoResponse = cartServiceFeign.queryCheckedItem();
        if (null != cartVoResponse && !cartVoResponse.getData().getCartItemList().isEmpty()) {
            List<CartItemVo> cartItemList = cartVoResponse.getData().getCartItemList();
            return cartItemList.stream().map(cartItemVo -> {
                OrderItemEntity orderItem = new OrderItemEntity();

                // spu信息
                Response<SpuInfoVo> spuInfoVoResponse = productServiceFeign.querySpuInfoBySkuId(cartItemVo.getSkuId());
                if (spuInfoVoResponse.getCode() == 200) {
                    SpuInfoVo spuInfoVo = spuInfoVoResponse.getData();
                    orderItem.setSpuId(spuInfoVo.getId());
                    orderItem.setSpuName(spuInfoVo.getSpuName());
                    orderItem.setSpuBrand(spuInfoVo.getBrandId().toString());
                    orderItem.setCategoryId(spuInfoVo.getCategoryId());
                }
                // sku信息
                orderItem.setSkuId(cartItemVo.getSkuId());
                orderItem.setSkuName(cartItemVo.getTitle());
                orderItem.setSkuPic(cartItemVo.getImage());
                orderItem.setSkuPrice(cartItemVo.getPrice());
                String skuAttr = StringUtils.collectionToDelimitedString(cartItemVo.getSkuAttr(), ";");
                orderItem.setSkuAttrsVals(skuAttr);
                orderItem.setSkuQuantity(cartItemVo.getCount());

                // TODO 优惠信息，积分信息

                // 价格信息
                orderItem.setPromotionAmount(new BigDecimal("0.0"));
                orderItem.setCouponAmount(new BigDecimal("0.0"));
                orderItem.setIntegrationAmount(new BigDecimal("0.0"));
                BigDecimal actualAmount = orderItem.getSkuPrice()
                        .multiply(new BigDecimal(orderItem.getSkuQuantity().toString()))
                        .subtract(orderItem.getCouponAmount())
                        .subtract(orderItem.getPromotionAmount())
                        .subtract(orderItem.getIntegrationAmount());
                orderItem.setRealAmount(actualAmount);

                orderItem.setOrderSn(orderSn);
                return orderItem;
            }).collect(Collectors.toList());
        }

        return null;
    }
}
