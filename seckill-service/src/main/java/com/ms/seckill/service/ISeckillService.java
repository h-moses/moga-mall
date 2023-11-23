package com.ms.seckill.service;

import com.ms.seckill.vo.SeckillProductVo;

import java.util.List;

public interface ISeckillService {

    void onShelfLast3d();

    List<SeckillProductVo> queryPromotionInfo();

    String kill(String killId, String key, Integer num);
}
