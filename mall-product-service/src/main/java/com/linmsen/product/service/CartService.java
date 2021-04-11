package com.linmsen.product.service;

import com.linmsen.product.controller.vo.CartItemInput;
import com.linmsen.product.controller.vo.CartVO;

public interface CartService {

    void addToCart(CartItemInput input);

    void changeItemNum(CartItemInput input);

    void clear();

    CartVO getMyCart();

    void deleteItem(long productId);
}
