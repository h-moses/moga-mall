package com.ms.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ms.common.api.Response;
import com.ms.common.to.SeckillOrderTo;
import com.ms.seckill.entity.SmsSeckillSession;
import com.ms.seckill.entity.SmsSeckillSkuRelation;
import com.ms.seckill.feign.ProductFeign;
import com.ms.seckill.interceptor.SeckillInterceptor;
import com.ms.seckill.service.ISeckillService;
import com.ms.seckill.service.ISmsSeckillSessionService;
import com.ms.seckill.service.ISmsSeckillSkuRelationService;
import com.ms.seckill.vo.SeckillProductVo;
import com.ms.seckill.vo.SeckillSkuInfoVo;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SeckillServiceImpl implements ISeckillService {

    private static final String SECKILL_SESSION_PREFIX = "seckill_session:";

    private static final String SECKILL_PROD_PREFIX = "seckill_prod";

    private static final String SECKILL_STOCK_PREFIX = "seckill_stock:";

    @Resource
    ISmsSeckillSessionService seckillSessionService;

    @Resource
    ISmsSeckillSkuRelationService seckillSkuRelationService;

    @Resource
    ProductFeign productFeign;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    RabbitTemplate rabbitTemplate;

    @Resource
    RedissonClient redissonClient;

    @Override
    public void onShelfLast3d() {
        List<SmsSeckillSession> sessionList = seckillSessionService.list(new LambdaQueryWrapper<SmsSeckillSession>().between(SmsSeckillSession::getStartTime, startTime(), endTime()));

        if (null != sessionList && !sessionList.isEmpty()) {
            sessionList = sessionList.stream().peek(session -> {

                // 得到当前秒杀活动的ID
                Long id = session.getId();
                List<SmsSeckillSkuRelation> skuRelationList = seckillSkuRelationService.list(new LambdaQueryWrapper<SmsSeckillSkuRelation>().eq(SmsSeckillSkuRelation::getPromotionSessionId, id));

                assert null != skuRelationList && !skuRelationList.isEmpty();

                session.setSkuRelationList(skuRelationList);

            }).collect(Collectors.toList());

            // 缓存活动信息
            sessionList.forEach(session -> {
                LocalDateTime startTime = session.getStartTime();
                LocalDateTime endTime = session.getEndTime();

                String key = SECKILL_SESSION_PREFIX + startTime +"_" + endTime;

                // 检查key是否存在，如果存在，则无需重复保存
                if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
                    List<String> skuIds = session.getSkuRelationList().stream().map(item -> item.getPromotionSessionId() + "_" + item.getSkuId().toString()).collect(Collectors.toList());
                    redisTemplate.opsForList().leftPushAll(key, skuIds);
                }
            });

            // 缓存活动的商品信息
            sessionList.forEach(session -> {
                BoundHashOperations hashOps = redisTemplate.boundHashOps(SECKILL_PROD_PREFIX);
                session.getSkuRelationList().forEach(skuRelation -> {

                    // 设置随机码
                    String code = UUID.randomUUID().toString().replace("-", "");

                    // 用于区分不同场次包含同一商品的秒杀活动
                    String ssid = skuRelation.getPromotionSessionId() + "_" + skuRelation.getSkuId();

                    // 检查是否该商品信息，幂等性处理
                    if (Boolean.FALSE.equals(hashOps.hasKey(ssid))) {
                        SeckillProductVo productVo = new SeckillProductVo();
                        BeanUtils.copyProperties(skuRelation, productVo);

                        // 设置秒杀时间
                        productVo.setStartTime(session.getStartTime());
                        productVo.setEndTime(session.getEndTime());

                        productVo.setRandomCode(code);

                        // 获取商品的详细信息
                        Response<SeckillSkuInfoVo> response = productFeign.info(skuRelation.getSkuId());
                        if (response.getCode() == 200) {
                            SeckillSkuInfoVo skuInfoVo = response.getData();
                            productVo.setSkuInfoVo(skuInfoVo);

                            // 保存到redis中
                            hashOps.put(ssid, JSON.toJSONString(productVo));
                        }

                        // 使用库存作为分布式的信号量，进行限流
                        RSemaphore semaphore = redissonClient.getSemaphore(SECKILL_STOCK_PREFIX + code);
                        semaphore.trySetPermits(skuRelation.getSeckillCount());
                    }
                });
            });
        }
    }

    private String startTime() {
        LocalDate now = LocalDate.now();
        LocalTime min = LocalTime.MIN;
        LocalDateTime start = LocalDateTime.of(now, min);
        return start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private String endTime() {
        LocalDate now = LocalDate.now();
        now = now.plusDays(2);
        return LocalDateTime.of(now, LocalTime.MAX).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public List<SeckillProductVo> queryPromotionInfo() {
        // 确定当前时间是哪个场次
        LocalDateTime now = LocalDateTime.now();
        // 查询所有秒杀场次
        Set<String> keys = redisTemplate.keys(SECKILL_SESSION_PREFIX + "*");
        assert keys != null;
        for (String key : keys) {
            String replace = key.replace(SECKILL_SESSION_PREFIX, "");
            String[] s = replace.split("_");
            LocalDateTime start = LocalDateTime.parse(s[0]);
            LocalDateTime end = LocalDateTime.parse(s[1]);
            if (now.isAfter(start) && now.isBefore(end)) {
                // 获取这个场次的所有商品信息
                List<String> productIds = redisTemplate.opsForList().range(key, -100, 100);
                assert productIds != null;
                BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(SECKILL_PROD_PREFIX);
                List<String> prodInfo = ops.multiGet(productIds);
                if (null != prodInfo && !prodInfo.isEmpty()) {
                    List<SeckillProductVo> productVoList = prodInfo.stream().map(item -> {
                        SeckillProductVo productVo = JSON.parseObject(item, SeckillProductVo.class);
                        return productVo;
                    }).collect(Collectors.toList());
                    return productVoList;
                }
            }
        }
        return null;
    }

    /**
     *
     * @param killId 场次id + 商品id
     * @param key 随机码
     * @param num 数量
     * @return
     */
    @Override
    public String kill(String killId, String key, Integer num) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(SECKILL_PROD_PREFIX);

        // 查询商品是否存在
        String s = ops.get(killId);
        if (StringUtils.hasText(s)) {
            SeckillProductVo productVo = JSON.parseObject(s, SeckillProductVo.class);

            // 校验合法性
            LocalDateTime startTime = productVo.getStartTime();
            LocalDateTime endTime = productVo.getEndTime();

            LocalDateTime now = LocalDateTime.now();
            // 校验时间合法性
            if (now.isAfter(startTime) && now.isBefore(endTime)) {
                // 校验随机码和商品ID
                String randomCode = productVo.getRandomCode();
                String ssid = productVo.getPromotionSessionId() + "_" + productVo.getSkuId();

                if (randomCode.equals(key) && killId.equals(ssid)) {
                    // 验证购买数量是否合理
                    if (num <= productVo.getSeckillLimit()) {
                        // 验证该用户是否已购买过，限流的一种措施，幂等性处理
                        // 秒杀成功，就在redis保存记录，userId_sessionId_skuId
                        String userId = SeckillInterceptor.THREAD_LOCAL_SECKILL.get().split("_")[1];
                        String redisKey = userId + "_" + ssid;
                        // 自动过期时间：活动结束时间 - 当前时间
                        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(redisKey, num.toString(), Duration.between(now, endTime));
                        if (ifAbsent) {
                            // 说明从来没购买过
                            RSemaphore semaphore = redissonClient.getSemaphore(SECKILL_STOCK_PREFIX + randomCode);
                            try {
                                // 秒杀成功，快速下单
                                semaphore.tryAcquire(num, 100, TimeUnit.MILLISECONDS);
                                // 发送消息给MQ
                                String orderSn = IdWorker.getTimeId();

                                SeckillOrderTo seckillOrderTo = new SeckillOrderTo();
                                seckillOrderTo.setOrderSn(orderSn);
                                seckillOrderTo.setSkuId(productVo.getSkuId());
                                seckillOrderTo.setUserId(userId);
                                seckillOrderTo.setPromotionSessionId(productVo.getPromotionSessionId());
                                seckillOrderTo.setCount(num);
                                seckillOrderTo.setSeckillPrice(productVo.getSeckillPrice());
                                rabbitTemplate.convertAndSend("order-event-exchange", "order.event.seckill", seckillOrderTo);

                                return orderSn;
                            } catch (InterruptedException e) {
                                return null;
                            }
                        } else {
                            // 说明已经购买过
                            return null;
                        }
                    }
                }
            }
        }
        return null;
    }


}
