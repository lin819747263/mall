package com.linmsen.user.controller;


import com.linmsen.JsonData;
import com.linmsen.user.controller.vo.AddressGetInput;
import com.linmsen.user.controller.vo.AddressVO;
import com.linmsen.user.model.AddressDO;
import com.linmsen.user.service.AddressService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 电商-公司收发货地址表 前端控制器
 * </p>
 *
 * @author linmsne
 * @since 2021-04-06
 */
@Api(tags = "地址")
@RestController
@RequestMapping("/api/address/v1")
public class AddressController {

    @Autowired
    private AddressService addressService;


    @GetMapping("details/{id}")
    public JsonData details(@PathVariable int id){
        AddressDO addressDO = addressService.details(id);
        return JsonData.buildSuccess(addressDO);
    }


    @PostMapping("add")
    public JsonData add(AddressGetInput input){
        int row = addressService.add(input);
        return JsonData.buildSuccess(row);
    }


    @GetMapping("findById/{addressId}")
    public AddressVO findById(@PathVariable Long addressId){
        return addressService.findById(addressId);
    }

    @GetMapping("findAll")
    public List<AddressVO> findAll(){
        return addressService.findAll();
    }

    @PostMapping("del/{addressId}")
    public int del(@PathVariable long addressId){
        return addressService.del(addressId);
    }

}

