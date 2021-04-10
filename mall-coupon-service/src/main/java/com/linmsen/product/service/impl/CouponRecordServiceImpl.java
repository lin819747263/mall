package com.linmsen.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linmsen.JsonData;
import com.linmsen.LoginUser;
import com.linmsen.content.UserContent;
import com.linmsen.product.controller.vo.CouponRecordVO;
import com.linmsen.product.controller.vo.NewUserCouponAddInput;
import com.linmsen.product.mapper.CouponRecordMapper;
import com.linmsen.product.model.CouponRecordDO;
import com.linmsen.product.service.CouponRecordService;
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
public class CouponRecordServiceImpl implements CouponRecordService {

    @Autowired
    private CouponRecordMapper couponRecordMapper;

    @Override
    public Map<String, Object> page(int page, int size) {
        LoginUser loginUser = UserContent.get();

        //第1页，每页2条
        Page<CouponRecordDO> pageInfo = new Page<>(page, size);
        IPage<CouponRecordDO> recordDOPage = couponRecordMapper.selectPage(pageInfo, new QueryWrapper<CouponRecordDO>().eq("user_id",loginUser.getId()).orderByDesc("create_time"));
        Map<String, Object> pageMap = new HashMap<>(3);

        pageMap.put("total_record", recordDOPage.getTotal());
        pageMap.put("total_page", recordDOPage.getPages());
        pageMap.put("current_data", recordDOPage.getRecords().stream().map(obj -> beanProcess(obj)).collect(Collectors.toList()));

        return pageMap;
    }

    @Override
    public CouponRecordVO findById(long recordId) {
        LoginUser loginUser = UserContent.get();
        CouponRecordDO recordDO = couponRecordMapper.selectOne(new QueryWrapper<CouponRecordDO>().eq("id", recordId).eq("user_id", loginUser.getId()));
        if(recordDO == null){return null;}
        CouponRecordVO couponRecordVO = beanProcess(recordDO);
        return couponRecordVO;
    }

    @Override
    public JsonData initnewUserCoupon(NewUserCouponAddInput input) {

        return null;
    }

    private CouponRecordVO beanProcess(CouponRecordDO obj) {
        return new CouponRecordVO();
    }
}
