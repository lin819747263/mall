package com.linmsen.product.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class CartItemInput {

    @ApiModelProperty("产品id")
    private Long productId;

    @ApiModelProperty("购买数量")
    private Integer buyNum;
}
