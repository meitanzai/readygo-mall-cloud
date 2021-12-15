package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.util.PageBuilder;
import com.mall.common.util.PageUtil;
import com.mall.goods.entity.GoodsEntity;
import com.mall.goods.mapper.GoodsMapper;
import com.mall.goods.service.GoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/30 16:54
 * @Version 1.0
 */
@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, GoodsEntity> implements GoodsService {

    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        Page<GoodsEntity> page = (Page<GoodsEntity>)new PageBuilder<GoodsEntity>().getPage(params);
        QueryWrapper<GoodsEntity> wrapper = new QueryWrapper<>();
        String name = String.valueOf(params.get("name"));//商品名称
        Long adminUserId = params.get("adminUserId") == null ? null: Long.valueOf((params.get("adminUserId").toString()));
        wrapper
                .like(StringUtils.isNotBlank(name), "name", name)
                .eq(adminUserId != null, "admin_user_id", adminUserId);
        IPage<GoodsEntity> iPage = baseMapper.queryPage(page, wrapper, adminUserId);
        return new PageUtil(iPage);
    }

    @Override
    public GoodsEntity getGoodsAndSku(Long id) {
        return baseMapper.getGoodsAndSku(id);
    }

    /**
     * 上架 / 下架
     * @param onSale
     */
    @Override
    public int updateOnSale(Long goodsId, Boolean onSale) {
        GoodsEntity goodsEntity = baseMapper.selectById(goodsId);
        goodsEntity.setOnSale(onSale);
        return baseMapper.updateById(goodsEntity);
    }

    @Override
    public List<GoodsEntity> getAllGoodsList(Map<String, Object> params) {
        Long adminUserId = params.get("adminUserId") == null ? null: Long.valueOf((params.get("adminUserId").toString()));
        QueryWrapper<GoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(adminUserId != null, "admin_user_id", adminUserId);
        return baseMapper.selectList(queryWrapper);
    }
}
