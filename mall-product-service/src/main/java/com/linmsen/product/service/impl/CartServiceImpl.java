package com.linmsen.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.linmsen.BizException;
import com.linmsen.LoginUser;
import com.linmsen.constant.CacheKey;
import com.linmsen.content.UserContent;
import com.linmsen.enums.BizCodeEnum;
import com.linmsen.product.controller.vo.CartItemInput;
import com.linmsen.product.controller.vo.CartItemVO;
import com.linmsen.product.controller.vo.CartVO;
import com.linmsen.product.controller.vo.ProductVO;
import com.linmsen.product.service.CartService;
import com.linmsen.product.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

        Object cacheObj = myCart.get(productId.toString());
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
            myCart.put(productId.toString(), JSON.toJSONString(cartItem));
        } else {
            //存在则新增数量
            CartItemVO cartItem = JSON.parseObject(result, CartItemVO.class);
            cartItem.setBuyNum(cartItem.getBuyNum() + buyNum);
            myCart.put(productId.toString(), JSON.toJSONString(cartItem));
        }
    }

    @Override
    public void changeItemNum(CartItemInput input) {
        BoundHashOperations<String,Object,Object> mycart =  getMyCartOps();

        Object cacheObj = mycart.get(input.getProductId().toString());

        if(cacheObj==null){throw new BizException(BizCodeEnum.CART_FAIL);}

        String obj = (String)cacheObj;

        CartItemVO cartItemVO =  JSON.parseObject(obj,CartItemVO.class);
        cartItemVO.setBuyNum(input.getBuyNum());
        mycart.put(input.getProductId().toString(),JSON.toJSONString(cartItemVO));
    }

    @Override
    public void clear() {
        String cartKey = getCartKey();
        redisTemplate.delete(cartKey);
    }

    @Override
    public CartVO getMyCart() {
        //获取全部购物项
        List<CartItemVO> cartItemVOList = buildCartItem(false);

        //封装成cartvo
        CartVO cartVO = new CartVO();
        cartVO.setItemVOS(cartItemVOList);

        return cartVO;
    }

    @Override
    public void deleteItem(long productId) {
        BoundHashOperations<String,Object,Object> mycart =  getMyCartOps();

        mycart.delete(String.valueOf(productId));
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

    /**
     * 获取最新的购物项，
     * @param latestPrice 是否获取最新价格
     * @return
     */
    private List<CartItemVO> buildCartItem(boolean latestPrice) {

        BoundHashOperations<String,Object,Object> myCart = getMyCartOps();

        List<Object> itemList = myCart.values();

        List<CartItemVO> cartItemVOList = new ArrayList<>();

        //拼接id列表查询最新价格
        List<Long> productIdList = new ArrayList<>();

        for(Object item: itemList){
            CartItemVO cartItemVO = JSON.parseObject((String)item,CartItemVO.class);
            cartItemVOList.add(cartItemVO);

            productIdList.add(cartItemVO.getProductId());
        }

        //查询最新的商品价格
        if(latestPrice){

            setProductLatestPrice(cartItemVOList,productIdList);
        }

        return cartItemVOList;

    }

    /**
     * 设置商品最新价格
     * @param cartItemVOList
     * @param productIdList
     */
    private void setProductLatestPrice(List<CartItemVO> cartItemVOList, List<Long> productIdList) {

        //批量查询
        List<ProductVO> productVOList = productService.findProductsByIdBatch(productIdList);

        //分组
        Map<Long,ProductVO> maps = productVOList.stream().collect(Collectors.toMap(ProductVO::getId, Function.identity()));


        cartItemVOList.stream().forEach(item->{

            ProductVO productVO = maps.get(item.getProductId());
            item.setTitle(productVO.getTitle());
            item.setProduvtImg(productVO.getCoverImg());
            item.setAmount(productVO.getPrice());

        });


    }
}
