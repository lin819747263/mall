package com.linmsen.product.service;

import com.linmsen.product.controller.vo.CartItemInput;

public interface CartService {

    void addToCart(CartItemInput input);
}
