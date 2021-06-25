package com.mall.goods.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author DongJunTao
 * @Description 商品规格表
 * @Date 2021/6/22 11:10
 * @Version 1.0
 */
@Data
@TableName("goods_specifications")
public class GoodsSpecificationsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 规格名称
     */
    private String name;
    /**
     * 规格值
     */
    private String value;
    /**
     * 创建时间
     */
    private Date createTime;
}
