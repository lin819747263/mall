package com.linmsen.product.controller.vo;

import lombok.Data;

@Data
public class CartItemInput {

    private Long productId;

    private Integer buyNum;
}
