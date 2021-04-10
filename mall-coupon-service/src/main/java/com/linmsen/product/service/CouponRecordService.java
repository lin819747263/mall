package com.linmsen.product.service;

import com.linmsen.JsonData;
import com.linmsen.product.controller.vo.CouponRecordVO;
import com.linmsen.product.controller.vo.NewUserCouponAddInput;
import com.linmsen.product.model.CouponRecordDO;

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

    JsonData initnewUserCoupon(com.linmsen.coupon.controller.vo.NewUserCouponAddInput input);
}
