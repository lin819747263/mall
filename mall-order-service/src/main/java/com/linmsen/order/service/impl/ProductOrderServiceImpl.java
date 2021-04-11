package com.linmsen.order.service.impl;

import com.linmsen.JsonData;
import com.linmsen.order.controller.vo.ConfirmOrderInput;
import com.linmsen.order.model.ProductOrderDO;
import com.linmsen.order.mapper.ProductOrderMapper;
import com.linmsen.order.service.ProductOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linmsen
 * @since 2021-04-10
 */
@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    @Override
    public JsonData confirmOrder(ConfirmOrderInput input, HttpServletResponse response) {
        return null;
    }
}
