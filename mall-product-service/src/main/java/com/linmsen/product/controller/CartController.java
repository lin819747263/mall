package com.linmsen.product.controller;


import com.linmsen.JsonData;
import com.linmsen.product.controller.vo.CartItemInput;
import com.linmsen.product.controller.vo.CartVO;
import com.linmsen.product.service.CartService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("修改购物车数量")
    @PostMapping("change")
    public JsonData changeItemNum(CartItemInput input){

        cartService.changeItemNum(input);

        return JsonData.buildSuccess();
    }




    @ApiOperation("清空购物车")
    @DeleteMapping("/clear")
    public JsonData cleanMyCart(){

        cartService.clear();
        return JsonData.buildSuccess();
    }



    @ApiOperation("查看我的购物车")
    @GetMapping("/mycart")
    public JsonData findMyCart(){

        CartVO cartVO = cartService.getMyCart();

        return JsonData.buildSuccess(cartVO);
    }



    @ApiOperation("删除购物项")
    @DeleteMapping("/delete/{product_id}")
    public JsonData deleteItem( @ApiParam(value = "商品id",required = true)@PathVariable("product_id")long productId ){

        cartService.deleteItem(productId);
        return JsonData.buildSuccess();
    }
}
