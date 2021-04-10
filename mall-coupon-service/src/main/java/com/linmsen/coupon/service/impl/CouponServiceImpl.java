package com.linmsen.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linmsen.BizException;
import com.linmsen.JsonData;
import com.linmsen.LoginUser;
import com.linmsen.content.UserContent;
import com.linmsen.coupon.controller.vo.CouponVO;
import com.linmsen.coupon.mapper.CouponMapper;
import com.linmsen.coupon.mapper.CouponRecordMapper;
import com.linmsen.coupon.model.CouponDO;
import com.linmsen.coupon.model.CouponRecordDO;
import com.linmsen.coupon.service.CouponService;
import com.linmsen.enums.BizCodeEnum;
import com.linmsen.enums.CouponCategoryEnum;
import com.linmsen.enums.CouponPublishEnum;
import com.linmsen.enums.CouponStateEnum;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
@Slf4j
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CouponRecordMapper couponRecordMapper;

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

    @Override
    public JsonData addCoupon(long couponId, String couponCategory) {

        LoginUser loginUser = UserContent.get();
        CouponDO couponDO = couponMapper.selectOne(new QueryWrapper<CouponDO>().eq("id", couponId)
                .eq("category", couponCategory)
                .eq("publish", CouponPublishEnum.PUBLISH));

        this.couponCheck(couponDO,loginUser.getId());

        RLock lock = redissonClient.getLock("lock:coupon:"+couponId);
        lock.lock();
        try {
            CouponRecordDO couponRecordDO = new CouponRecordDO();
            BeanUtils.copyProperties(couponDO,couponRecordDO);
            couponRecordDO.setCreateTime(new Date());
            couponRecordDO.setUseState(CouponStateEnum.NEW.name());
            couponRecordDO.setUserId(loginUser.getId());
            couponRecordDO.setUserName(loginUser.getName());
            couponRecordDO.setCouponId(couponId);
            couponRecordDO.setId(null);

            //高并发下扣减劵库存，采用乐观锁,当前stock做版本号,延伸多种防止超卖的问题,一次只能领取1张，TODO
            int rows = couponMapper.reduceStock(couponId,couponDO.getStock());
            if(rows == 1){
                //库存扣减成功才保存
                couponRecordMapper.insert(couponRecordDO);
            }else {
                log.warn("发放优惠券失败:{},用户:{}",couponDO,loginUser);
                throw new BizException(BizCodeEnum.COUPON_NO_STOCK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }



        return JsonData.buildSuccess();
    }

    /**
     * 优惠券检查
     * @param couponDO
     * @param userId
     */
    private void couponCheck(CouponDO couponDO,long userId){

        //优惠券不存在
        if(couponDO == null){
            throw  new BizException(BizCodeEnum.COUPON_NO_EXITS);
        }
        //库存不足
        if(couponDO.getStock()<=0){
            throw  new BizException(BizCodeEnum.COUPON_NO_STOCK);
        }
        //是否在领取时间范围
        long time = System.currentTimeMillis();
        long start = couponDO.getStartTime().getTime();
        long end = couponDO.getEndTime().getTime();
        if(time < start || time > end){
            throw  new BizException(BizCodeEnum.COUPON_OUT_OF_TIME);
        }
        //用户是否超过限制
        int recordNum = couponRecordMapper.selectCount(new QueryWrapper<CouponRecordDO>()
                .eq("coupon_id",couponDO.getId())
                .eq("user_id",userId));

        if(recordNum>=couponDO.getUserLimit()){
            throw  new BizException(BizCodeEnum.COUPON_OUT_OF_LIMIT);
        }
    }

    private Object beanProcess(CouponDO obj) {
        CouponVO vo = new CouponVO();
        BeanUtils.copyProperties(obj, vo);
        return vo;
    }

}
