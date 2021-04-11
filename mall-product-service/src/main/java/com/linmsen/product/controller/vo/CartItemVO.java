package com.linmsen.product.controller.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemVO {

    private long productId;

    private int buyNum;

    private String title;

    private String produvtImg;

    private BigDecimal amount;

    private BigDecimal totalAmount;
}
