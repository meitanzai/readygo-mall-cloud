package com.mall.cart.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 购物车列表
 * @Date 2022/5/21 17:39
 * @Version 1.0
 */
@Data
public class CartVO {

    /**
     * 已选的商品总量
     */
    private Integer totalCount;
    /**
     * 已选的商品总价
     */
    private BigDecimal totalPrice;

    List<CartMerchantVO> cartMerchantList;
}
