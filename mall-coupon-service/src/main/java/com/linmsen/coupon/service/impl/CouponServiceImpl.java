package com.linmsen.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linmsen.coupon.controller.CouponVO;
import com.linmsen.coupon.model.CouponDO;
import com.linmsen.coupon.mapper.CouponMapper;
import com.linmsen.coupon.service.CouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmsen.enums.CouponCategoryEnum;
import com.linmsen.enums.CouponPublishEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linmsen
 * @since 2021-04-10
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Override
    public Map<String, Object> pageCouponActivity(int page, int size) {
        //第1页，每页2条
        Page<CouponDO> pageInfo = new Page<>(page, size);
        IPage<CouponDO> couponDOPage = couponMapper.selectPage(pageInfo, new QueryWrapper<CouponDO>()
                .eq("publish", CouponPublishEnum.PUBLISH)
                .eq("category", CouponCategoryEnum.PROMOTION)
                .orderByDesc("create_time"));

        Map<String, Object> pageMap = new HashMap<>(3);

        pageMap.put("total_record", couponDOPage.getTotal());
        pageMap.put("total_page", couponDOPage.getPages());
        pageMap.put("current_data", couponDOPage.getRecords().stream().map(obj -> beanProcess(obj)).collect(Collectors.toList()));

        return pageMap;
    }

    private Object beanProcess(CouponDO obj) {
        CouponVO vo = new CouponVO();
        BeanUtils.copyProperties(obj, vo);
        return vo;
    }

}
