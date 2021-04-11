package com.linmsen.coupon.controller;


import com.linmsen.JsonData;
import com.linmsen.coupon.controller.vo.NewUserCouponAddInput;
import com.linmsen.coupon.service.CouponRecordService;
import com.linmsen.coupon.service.CouponService;
import com.linmsen.enums.CouponCategoryEnum;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linmsen
 * @since 2021-04-10
 */
@RestController
@RequestMapping("/api/coupon/v1")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRecordService couponRecordService;



    @ApiOperation("分页查询优惠券")
    @GetMapping("page")
    public JsonData pageCouponList(
            @ApiParam(value = "当前页")  @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam(value = "每页显示多少条") @RequestParam(value = "size", defaultValue = "10") int size
    ) {

        Map<String,Object> pageMap = couponService.pageCouponActivity(page,size);
        return JsonData.buildSuccess(pageMap);
    }


    /**
     * 领取优惠券
     * @param couponId
     * @return
     */
    @ApiOperation("领取优惠券")
    @GetMapping("/add/promotion/{couponId}")
    public JsonData addPromotionCoupon(@ApiParam(value = "优惠券id",required = true) @PathVariable("couponId")long couponId){


        JsonData jsonData = couponService.addCoupon(couponId, CouponCategoryEnum.PROMOTION.name());

        return jsonData;
    }



    @ApiOperation("添加新用户优惠券")
    @PostMapping("/addNewUserCoupon")
    public JsonData addNewUserCoupon(NewUserCouponAddInput input){

        return couponRecordService.initnewUserCoupon(input);
    }

}

