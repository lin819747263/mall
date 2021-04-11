package com.linmsen.order.service;

import com.linmsen.JsonData;
import com.linmsen.order.controller.vo.ConfirmOrderInput;
import com.linmsen.order.model.ProductOrderDO;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linmsen
 * @since 2021-04-10
 */
public interface ProductOrderService {

    JsonData confirmOrder(ConfirmOrderInput input, HttpServletResponse response);
}
