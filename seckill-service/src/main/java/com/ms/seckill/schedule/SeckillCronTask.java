package com.ms.seckill.schedule;

import com.ms.seckill.service.ISeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SeckillCronTask {

    @Resource
    ISeckillService seckillService;

    @Resource
    RedissonClient redissonClient;

    private static final String RED_LOCK = "seckill:upload:lock";

    /**
     * 凌晨3点，提前上架最近3天需要秒杀的商品
     */
    @Scheduled(cron = "0 * * * * ?")
    public void onShelfLast3d() {
        log.info("上架秒杀的商品信息……");
        // TODO 避免重复上架，幂等性处理
        RLock lock = redissonClient.getLock(RED_LOCK);
        lock.lock(10, TimeUnit.SECONDS);
        try {
            seckillService.onShelfLast3d();
        } finally {
            log.info("秒杀商品上架完成……");
            lock.unlock();
        }
    }
}
