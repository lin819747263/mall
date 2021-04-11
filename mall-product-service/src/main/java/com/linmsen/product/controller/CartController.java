package com.linmsen.product.controller;


import com.linmsen.product.controller.vo.CartItemInput;
import com.linmsen.product.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart/v1")
public class CartController {


    @Autowired
    CartService cartService;


    @ApiOperation("添加购物车")
    @PostMapping("addToCart")
    public void addToCart(CartItemInput input){
        cartService.addToCart(input);
    }
}
