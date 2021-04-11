package com.linmsen.coupon.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class NewUserCouponAddInput {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名称")
    private String name;
}
