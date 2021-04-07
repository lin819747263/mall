package com.linmsen.controller;


import com.linmsen.model.AddressDO;
import com.linmsen.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 电商-公司收发货地址表 前端控制器
 * </p>
 *
 * @author linmsne
 * @since 2021-04-06
 */
@RestController
@RequestMapping("/api/address/v1")
public class AddressController {

    @Autowired
    private AddressService addressService;


    @GetMapping("details/{id}")
    public AddressDO details(@PathVariable int id){
        return addressService.details(id);
    }

}

