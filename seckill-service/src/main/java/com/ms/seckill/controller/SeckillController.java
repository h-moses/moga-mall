package com.ms.seckill.controller;

import com.ms.common.api.Response;
import com.ms.seckill.service.ISeckillService;
import com.ms.seckill.vo.SeckillProductVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Resource
    ISeckillService seckillService;

    @GetMapping("/all")
    public Response<List<SeckillProductVo>> queryPromotionInfo() {
        List<SeckillProductVo> seckillProductVos = seckillService.queryPromotionInfo();

        return Response.SUCCESS(seckillProductVos);
    }

    @PostMapping("/kill")
    public Response kill(@RequestParam("killId") String killId,
                         @RequestParam("key") String key,
                         @RequestParam("num") Integer num) {

        String orderSn = seckillService.kill(killId, key, num);
        return Response.SUCCESS(orderSn);
    }

}
