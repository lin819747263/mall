package com.linmsen.product.controller.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartVO {

    private List<CartItemVO> itemVOS;

    private Integer totalNum;

    private BigDecimal totalPrice;

    private BigDecimal realPayPrice;
}
