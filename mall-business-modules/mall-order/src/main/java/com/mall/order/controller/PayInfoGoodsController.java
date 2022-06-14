package com.mall.order.controller;

import com.alibaba.fastjson.JSON;
import com.mall.admin.api.feign.front.FeignFrontFreightTemplateService;
import com.mall.cart.api.feign.FeignCartService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.coupon.api.feign.front.FeignFrontCouponService;
import com.mall.goods.api.front.FeignGoodsService;
import com.mall.member.api.FeignCouponReceivedService;
import com.mall.order.entity.CouponSelectedEntity;
import com.mall.order.service.CouponSelectedService;
import com.mall.order.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author DongJunTao
 * @Description 订单结算页 商家及商品信息
 * @Date 2022/5/8 16:00
 * @Version 1.0
 */
@RestController
@RequestMapping( "payInfo")
public class PayInfoGoodsController {

    @Autowired
    private FeignGoodsService feignGoodsService;

    @Autowired
    private CouponSelectedService couponSelectedService;

    @Autowired
    private FeignCouponReceivedService feignCouponReceivedService;

    @Autowired
    private FeignFrontCouponService feignFrontCouponService;

    @Autowired
    private FeignFrontFreightTemplateService feignFrontFreightTemplateService;

    @Autowired
    private FeignCartService feignCartService;


    /**
     * 获取结算页信息 【商品信息】
     * @return
     */
    @GetMapping("goods")
    public CommonResult goods(@RequestParam Map<String, Object> params) {
        CommonResult cartListResult = feignCartService.list(new HashMap<>(), JSON.toJSONString(CurrentUserContextUtil.getCurrentUserInfo()));
        Map<String, Object> cartMap = (Map<String, Object>)cartListResult.getData();
        Integer totalCount = Integer.parseInt(String.valueOf(cartMap.get("checkedTotalCount"))); //订单结算页的商品数量是购物车中已选中的商品数量
        BigDecimal totalPrice = new BigDecimal(String.valueOf(cartMap.get("totalPrice"))); //总价
        List<PayMerchantVO> payMerchantVOList = new ArrayList<>(); //封装购物车信息
        List cartMerchantList = (List)cartMap.get("cartMerchantList");
        for (int i=0; i<cartMerchantList.size(); i++) {
            PayMerchantVO payMerchantVO = new PayMerchantVO();
            Map<String, Object> cartMerchantMap = (Map<String, Object>)cartMerchantList.get(i);
            payMerchantVO.setMerchantId(Long.valueOf(String.valueOf(cartMerchantMap.get("merchantId"))));
            payMerchantVO.setMerchantName(String.valueOf(cartMerchantMap.get("merchantName")));
            List<PayGoodsVO> payGoodsList = new ArrayList<>();
            List cartGoodsList = (List)cartMerchantMap.get("cartGoodsList");
            for (int j=0; j<cartGoodsList.size(); j++) {
                PayGoodsVO payGoodsVO = new PayGoodsVO();
                Map<String, Object> cartGoodsMap = (Map<String, Object>) cartGoodsList.get(j);
                //选择已经选中的商品
                if (Boolean.TRUE== Boolean.valueOf(String.valueOf(cartGoodsMap.get("checked")))) {
                    payGoodsVO.setId(Long.valueOf(String.valueOf(cartGoodsMap.get("id"))));
                    payGoodsVO.setGoodsId(Long.valueOf(String.valueOf(cartGoodsMap.get("goodsId"))));
                    payGoodsVO.setGoodsSkuId(Long.valueOf(String.valueOf(cartGoodsMap.get("goodsSkuId"))));
                    payGoodsVO.setCount(Integer.valueOf(String.valueOf(cartGoodsMap.get("count"))));
                    payGoodsVO.setImage(String.valueOf(cartGoodsMap.get("image")));
                    payGoodsVO.setName(String.valueOf(cartGoodsMap.get("name")));
                    payGoodsVO.setSellingPrice(new BigDecimal(String.valueOf(cartGoodsMap.get("sellingPrice"))));
                    payGoodsVO.setSubTotal(new BigDecimal(String.valueOf(cartGoodsMap.get("subTotal"))));
                    payGoodsList.add(payGoodsVO);
                }
            }
            payMerchantVO.setPayGoodsList(payGoodsList);
            payMerchantVOList.add(payMerchantVO);
        }

        PayVO payVO = new PayVO();
        payVO.setPayMerchantList(payMerchantVOList); //购物车列表
        payVO.setTotalCount(totalCount);
        payVO.setTotalPrice(totalPrice); //总价格
        payVO.setFinalPrice(new BigDecimal("0")); //最终价格，可能包含优惠券减去的价格，还有运费等
        payVO.setDiscountPrice(new BigDecimal("0")); //优惠掉的价格

        //查询是否使用优惠券
        List<CouponSelectedEntity> couponSelectedList
                = couponSelectedService.getSelected(CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        if (!CollectionUtils.isEmpty(couponSelectedList)) { //使用优惠券
            Long receivedCouponId = couponSelectedList.get(0).getReceivedCouponId(); //我的优惠券信息
            CommonResult receivedCouponResult = feignCouponReceivedService.getById(receivedCouponId);
            if (receivedCouponResult == null || !"200".equals(receivedCouponResult.getCode())) {
                return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), payVO);
            }
            Map<String, Object> receivedCouponMap = (Map<String, Object>) receivedCouponResult.getData();
            Long couponId = Long.valueOf(String.valueOf(receivedCouponMap.get("couponId")));
            CommonResult couponResult = feignFrontCouponService.getById(couponId);
            if (couponResult == null || !"200".equals(couponResult.getCode())) {
                return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), payVO);
            }
            Map<String, Object> couponMap = (Map<String, Object>) couponResult.getData();
            Integer type = Integer.valueOf(String.valueOf(couponMap.get("type")));
            if (type == 0) { //满减券
                payVO.setDiscountPrice(new BigDecimal(String.valueOf(couponMap.get("discountAmount"))));
            } else if (type == 1) { //满折券
                payVO.setDiscountPrice(totalPrice.subtract(new BigDecimal(String.valueOf(couponMap.get("discountAmount"))).multiply(totalPrice).divide(new BigDecimal("10"))));
            }
            payVO.setFinalPrice(totalPrice.subtract(payVO.getDiscountPrice()));
        }
        //查看是否需要运费（若来自不同商家，运费叠加计算，若来自同一商家，计算规则为单个商品运费的最大值）
        List<PayMerchantVO> payMerchantList = payVO.getPayMerchantList();//商品涉及的商家及商品相关信息
        BigDecimal everyMerchantFreight = new BigDecimal("0"); //每家的运费（取该家所有商品的最大值）
        for (int i=0; i<payMerchantList.size(); i++) {
            PayMerchantVO payMerchantVO = payMerchantList.get(i); //每个商家的结算信息
            List<PayGoodsVO> payGoodsList = payMerchantVO.getPayGoodsList(); //每个商家的商品信息

            for (int j=0; j<payGoodsList.size(); j++) { //开始循环调用运费模板信息，计算运费
                PayGoodsVO payGoodsVO = payGoodsList.get(j);//先查询商品
                CommonResult goodsResult = feignGoodsService.getGoodsById(payGoodsVO.getGoodsId());
                if ("200".equals(goodsResult.getCode())) {
                    //运费模板id，再远程调用获取模板信息
                    Integer freightSettingId = Integer.valueOf(((Map<String,Object>) goodsResult.getData()).get("freightSetting").toString());
                    CommonResult freightTemplateResult = feignFrontFreightTemplateService.getShippingInfoById(freightSettingId);
                    if ("200".equals(freightTemplateResult.getCode())) {
                        //是否商家包邮 0:买家承担运费 1:卖家承担运费
                        Integer type = Integer.valueOf(((Map<String,Object>) freightTemplateResult.getData()).get("type").toString());
                        //计费方式 0:按数量 1:按金额 2:按体积
                        Integer chargeType = Integer.valueOf(((Map<String,Object>) freightTemplateResult.getData()).get("chargeType").toString());
                        Boolean enableDefaultFreight = Boolean.valueOf(((Map<String,Object>) freightTemplateResult.getData()).get("enableDefaultFreight").toString());
                        Boolean enableConditionFree = Boolean.valueOf(((Map<String,Object>) freightTemplateResult.getData()).get("enableDefaultFreight").toString());
                        Map<String, Object> freightDefaultEntity = (Map<String, Object>) ((Map<String,Object>) freightTemplateResult.getData()).get("freightDefaultEntity");
                        List<Map<String,Object>> freightRuleEntityList = (List<Map<String,Object>>)((Map<String,Object>)freightTemplateResult.getData()).get("freightRuleEntityList");
                        List<Map<String,Object>> freightFreeRuleEntityList = (List<Map<String,Object>>)((Map<String,Object>)freightTemplateResult.getData()).get("freightFreeRuleEntityList");
                        System.out.println("aa==");
                    }
                }
            }

        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), payVO);
    }

}