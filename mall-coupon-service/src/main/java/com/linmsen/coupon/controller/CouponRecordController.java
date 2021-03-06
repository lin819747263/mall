package com.linmsen.coupon.controller;


import com.linmsen.JsonData;
import com.linmsen.coupon.controller.vo.CouponRecordVO;
import com.linmsen.coupon.controller.vo.NewUserCouponAddInput;
import com.linmsen.coupon.service.CouponRecordService;
import com.linmsen.enums.BizCodeEnum;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/api/record/v1")
public class CouponRecordController {

    @Autowired
    private CouponRecordService couponRecordService;

    @ApiOperation("分页查询我的优惠券列表")
    @GetMapping("page")
    public JsonData listRecordPage(@RequestParam(value = "page",defaultValue = "1")int page,
                                   @RequestParam(value = "size",defaultValue = "20")int size){

        Map<String,Object> pageInfo = couponRecordService.page(page,size);

        return JsonData.buildSuccess(pageInfo);
    }


    @ApiOperation("查询优惠券记录信息")
    @GetMapping("/detail/{record_id}")
    public JsonData findUserCouponRecordById(@PathVariable("record_id")long recordId ){

        CouponRecordVO couponRecordVO = couponRecordService.findById(recordId);
        return  couponRecordVO == null? JsonData.buildResult(BizCodeEnum.COUPON_NO_EXITS):JsonData.buildSuccess(couponRecordVO);
    }

}

