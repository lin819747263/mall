package com.linmsen.user.fegin;

import com.linmsen.JsonData;
import com.linmsen.user.controller.vo.NewUserCouponAddInput;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "coupon-service")
public interface CouponFeignService {

    @PostMapping("/api/coupon/v1/addNewUserCoupon")
    public JsonData addNewUserCoupon(@RequestBody NewUserCouponAddInput input);

}
