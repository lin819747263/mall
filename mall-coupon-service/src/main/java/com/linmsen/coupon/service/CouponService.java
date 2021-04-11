package com.linmsen.coupon.service;

import com.linmsen.JsonData;
import com.linmsen.coupon.controller.vo.NewUserCouponAddInput;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linmsen
 * @since 2021-04-10
 */
public interface CouponService {

    Map<String, Object> pageCouponActivity(int page, int size);

    JsonData addCoupon(long couponId, String couponCategory);

    JsonData initnewUserCoupon(NewUserCouponAddInput input);

}
