package com.linmsen.coupon.controller;


import com.linmsen.product.controller.vo.BannerVO;
import com.linmsen.product.service.BannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linmsen
 * @since 2021-04-10
 */
@RestController
@RequestMapping("/api/banner/v1")
public class BannerController {

    @Autowired
    private BannerService bannerService;


    @ApiOperation("查询轮播图")
    @GetMapping("list")
    public List<BannerVO> list() {
        return bannerService.list();
    }

}

