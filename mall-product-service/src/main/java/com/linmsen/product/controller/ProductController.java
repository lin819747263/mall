package com.linmsen.product.controller;


import com.linmsen.JsonData;
import com.linmsen.product.controller.vo.BannerVO;
import com.linmsen.product.controller.vo.ProductVO;
import com.linmsen.product.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linmsen
 * @since 2021-04-10
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @ApiOperation("产品列表")
    @GetMapping("page")
    public Map<String, Object> page(@RequestParam(value = "page",defaultValue = "1")int page,
                                    @RequestParam(value = "size",defaultValue = "20")int size) {
        return productService.page(page, size);
    }


    @ApiOperation("产品详情")
    @GetMapping("detail/{productId}")
    public JsonData detail(@PathVariable Long productId) {
        return productService.detail(productId);
    }

}

