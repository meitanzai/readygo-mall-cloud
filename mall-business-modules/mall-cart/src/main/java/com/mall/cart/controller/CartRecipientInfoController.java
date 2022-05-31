package com.mall.cart.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mall.cart.entity.RecipientInfoSelectedEntity;
import com.mall.cart.service.RecipientInfoSelectedService;
import com.mall.cart.vo.RecipientInfoVO;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.member.api.FeignRecipientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/5/30 15:13
 * @Version 1.0
 */
@RestController
@RequestMapping( "cartRecipientInfo")
public class CartRecipientInfoController {

    @Autowired
    private FeignRecipientInfoService feignRecipientInfoService;

    @Autowired
    private RecipientInfoSelectedService recipientInfoSelectedService;

    /**
     * 收货人信息
     * @param params
     * @return
     */
    @GetMapping("/payInfo")
    public CommonResult recipientInfo(@RequestParam Map<String, Object> params) {
        CommonResult recipientInfoResult = feignRecipientInfoService.getRecipientInfoList(params,
                JSON.toJSONString(CurrentUserContextUtil.getCurrentUserInfo()));
        List<RecipientInfoVO> recipientInfoList = new ArrayList<>();
        if (recipientInfoResult != null || "200".equals(recipientInfoResult.getCode())) {
            recipientInfoList = (List)recipientInfoResult.getData();
            String jsonStr = JSON.toJSONString(recipientInfoList);//依旧使用alibaba提供的json转换工具。
            recipientInfoList = JSONObject.parseArray(jsonStr, RecipientInfoVO.class);
        }
        RecipientInfoSelectedEntity recipientInfoSelectedEntity =
                recipientInfoSelectedService.getRecipientInfoSelectedByMemberId(CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        boolean selected = false;
        if (!CollectionUtils.isEmpty(recipientInfoList)) {
            if(recipientInfoSelectedEntity != null) {
                for (int i=0; i<recipientInfoList.size(); i++) {
                    if (recipientInfoList.get(i).getId().equals(recipientInfoSelectedEntity.getRecipientInfoId())) {
                        recipientInfoList.get(i).setSelected(true);
                        selected = true;
                    }
                }
            }
            //没有选中的，就是要默认的
            if (!selected) {
                for (int i=0; i<recipientInfoList.size(); i++) {
                    if (recipientInfoList.get(i).getIsDefault()) {
                        recipientInfoList.get(i).setSelected(true);
                    }
                }
            }
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), recipientInfoList);
    }

    /**
     * 选择收货人信息
     * @param recipientInfoId
     * @return
     */
    @PostMapping("selectAddress")
    public CommonResult selectAddress(@RequestParam("recipientInfoId") Long recipientInfoId) {
        Long memberId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        RecipientInfoSelectedEntity recipientInfoSelected =
                recipientInfoSelectedService.getRecipientInfoSelectedByMemberId(memberId);
        if (recipientInfoSelected == null) {
            RecipientInfoSelectedEntity newRecipientInfoSelected = new RecipientInfoSelectedEntity();
            newRecipientInfoSelected.setRecipientInfoId(recipientInfoId);
            newRecipientInfoSelected.setMemberId(memberId);
            recipientInfoSelectedService.saveRecipientInfoSelected(newRecipientInfoSelected);
        } else {
            recipientInfoSelected.setRecipientInfoId(recipientInfoId);
            recipientInfoSelectedService.updateRecipientInfoSelected(recipientInfoSelected);
        }
        return CommonResult.success();
    }
}
