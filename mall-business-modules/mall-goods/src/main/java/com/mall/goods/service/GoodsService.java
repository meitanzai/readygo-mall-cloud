package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.base.utils.PageUtil;
import com.mall.goods.entity.GoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/30 16:53
 * @Version 1.0
 */
public interface GoodsService extends IService<GoodsEntity> {

    PageUtil queryPage(Map<String, Object> params);

    GoodsEntity getGoodsAndSku(Long id);

    int updateOnSale(Long goodsId, Boolean onSale);

    List<GoodsEntity> getAllGoodsList(Map<String, Object> params);

    /**
     * 根据id集合，查询所有商户信息
     * @param ids
     * @return
     */
    List<GoodsEntity> getByIds(Long[] ids);

    /**
     * 根据商品id集合，查询商品【包含sku信息】
     * @param goodsIds
     * @return
     */
    List<GoodsEntity> getByGoodsIds(Long[] goodsIds);

    List<GoodsEntity> getAllGoodsWithDetail();
}
