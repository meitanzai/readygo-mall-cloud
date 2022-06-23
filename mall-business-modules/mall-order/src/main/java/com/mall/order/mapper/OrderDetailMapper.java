package com.mall.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.order.entity.OrderDetailEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author DongJunTao
 * @Description 订单详情service
 * @Date 2022/6/16 19:41
 * @Version 1.0
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetailEntity> {
}
