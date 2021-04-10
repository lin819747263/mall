package com.linmsen.product.mapper;

import com.linmsen.product.model.CouponDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
