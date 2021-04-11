package com.linmsen.order.controller.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ConfirmOrderInput {

    private Long couponRecordId;

    private List<Long> productIds;

    private String payType;

    private String clientType;

    private Long addressId;

    private BigDecimal totalAmount;

    private BigDecimal realAmount;

}
