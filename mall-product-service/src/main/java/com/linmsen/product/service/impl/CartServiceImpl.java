package com.linmsen.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.linmsen.LoginUser;
import com.linmsen.constant.CacheKey;
import com.linmsen.content.UserContent;
import com.linmsen.product.controller.vo.CartItemInput;
import com.linmsen.product.controller.vo.CartItemVO;
import com.linmsen.product.controller.vo.ProductVO;
import com.linmsen.product.service.CartService;
import com.linmsen.product.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    ProductService productService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public void addToCart(CartItemInput input) {
        Long productId = input.getProductId();

        int buyNum = input.getBuyNum();
        //获取购物车
        BoundHashOperations<String, Object, Object> myCart = getMyCartOps();

        Object cacheObj = myCart.get(productId);
        String result = "";

        if (cacheObj != null) {
            result = (String) cacheObj;
        }
        if (StringUtils.isBlank(result)) {
            //不存在则新建一个购物项
            CartItemVO cartItem = new CartItemVO();
            ProductVO productVO = productService.findDetailById(productId);
            cartItem.setAmount(productVO.getPrice());
            cartItem.setBuyNum(buyNum);
            cartItem.setProductId(productId);
            cartItem.setProduvtImg(productVO.getCoverImg());
            cartItem.setTitle(productVO.getTitle());
            myCart.put(productId, JSON.toJSONString(cartItem));
        } else {
            //存在则新增数量
            CartItemVO cartItem = JSON.parseObject(result, CartItemVO.class);
            cartItem.setBuyNum(cartItem.getBuyNum() + buyNum);
            myCart.put(productId, JSON.toJSONString(cartItem));
        }
    }

    /**
     * 抽取我的购物车通用方法
     *
     * @return
     */
    private BoundHashOperations<String, Object, Object> getMyCartOps() {
        String cartKey = getCartKey();
        return redisTemplate.boundHashOps(cartKey);
    }


    /**
     * 获取购物车的key
     *
     * @return
     */
    private String getCartKey() {
        LoginUser loginUser = UserContent.get();
        String cartKey = String.format(CacheKey.CART_KEY, loginUser.getId());
        return cartKey;
    }
}
