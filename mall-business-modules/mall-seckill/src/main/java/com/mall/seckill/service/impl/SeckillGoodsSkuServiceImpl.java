package com.mall.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.seckill.entity.SeckillGoodsSkuEntity;
import com.mall.seckill.mapper.SeckillGoodsSkuMapper;
import com.mall.seckill.service.SeckillGoodsSkuService;
import com.mall.seckill.vo.GoodsSkuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 秒杀商品详细信息service实现
 * @Date 2022/1/6 10:06
 * @Version 1.0
 */
@Service("seckillGoodsDetailService")
public class SeckillGoodsSkuServiceImpl
        extends ServiceImpl<SeckillGoodsSkuMapper, SeckillGoodsSkuEntity> implements SeckillGoodsSkuService {

    @Autowired
    private SeckillGoodsSkuMapper seckillGoodsSkuMapper;

    @Override
    public List<SeckillGoodsSkuEntity> getSeckillGoodsSkuListByIds(List<Long> seckillGoodsSkuIds) {
        return seckillGoodsSkuMapper.getSeckillGoodsSkuListByIds(seckillGoodsSkuIds);
    }
}
