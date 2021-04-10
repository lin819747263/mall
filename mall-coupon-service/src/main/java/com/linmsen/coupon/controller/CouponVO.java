package com.linmsen.coupon.controller;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CouponVO {

    /**
     * 优惠卷类型[NEW_USER注册赠券，TASK任务卷，PROMOTION促销劵]
     */
    private String category;

    /**
     * 发布状态, PUBLISH发布，DRAFT草稿，OFFLINE下线
     */
    private String publish;

    /**
     * 优惠券图片
     */
    private String couponImg;

    /**
     * 优惠券标题
     */
    private String couponTitle;

    /**
     * 抵扣价格
     */
    private BigDecimal price;

    /**
     * 每人限制张数
     */
    private Integer userLimit;

    /**
     * 优惠券开始有效时间
     */
    private Date startTime;

    /**
     * 优惠券失效时间
     */
    private Date endTime;

    /**
     * 优惠券总量
     */
    private Integer publishCount;

    /**
     * 库存
     */
    private Integer stock;

    private Date createTime;

    /**
     * 满多少才可以使用
     */
    private BigDecimal conditionPrice;
}
