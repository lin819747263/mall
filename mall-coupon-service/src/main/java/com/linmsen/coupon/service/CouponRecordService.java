package com.linmsen.coupon.service;

import com.linmsen.JsonData;
import com.linmsen.coupon.controller.vo.CouponRecordVO;
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
public interface CouponRecordService {

    Map<String, Object> page(int page, int size);

    CouponRecordVO findById(long recordId);
}
