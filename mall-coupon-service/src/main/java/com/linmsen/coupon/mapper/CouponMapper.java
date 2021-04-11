package com.linmsen.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linmsen.coupon.model.CouponDO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linmsen
 * @since 2021-04-10
 */
public interface CouponMapper extends BaseMapper<CouponDO> {

    int reduceStock(long couponId, Integer stock);
}
