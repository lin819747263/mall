package com.linmsen.order.controller;


import com.linmsen.JsonData;
import com.linmsen.enums.ProductOrderPayTypeEnum;
import com.linmsen.order.controller.vo.ConfirmOrderInput;
import com.linmsen.order.service.ProductOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linmsen
 * @since 2021-04-10
 */
@RestController
@Slf4j
@RequestMapping("/api/order/v1")
public class ProductOrderController {

    @Autowired
    private ProductOrderService productOrderService;

    @ApiOperation("提交订单")
    @PostMapping("confirm")
    public JsonData confirm(ConfirmOrderInput input, HttpServletResponse response){
        JsonData jsonData =  productOrderService.confirmOrder(input, response);
        if(jsonData.getCode() == 0){
            String client = input.getClientType();
            String payType = input.getPayType();
            if(payType.equalsIgnoreCase(ProductOrderPayTypeEnum.ALIPAY.name())){
                log.info("创建订单成功");
            }

            writeData(response, jsonData);

        }else {
            log.error("创建订单失败");
        }
        return jsonData;
    }

    private void writeData(HttpServletResponse response, JsonData jsonData) {

        try {
            response.setContentType("text/html;charset=UTF8");
            response.getWriter().write(jsonData.getData().toString());
            response.getWriter().flush();
            response.getWriter().close();
        }catch (IOException e){
            log.error("写出Html异常：{}",e);
        }

    }

}

