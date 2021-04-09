package com.linmsen.user.service;

import com.linmsen.user.controller.vo.AddressGetInput;
import com.linmsen.user.controller.vo.AddressVO;
import com.linmsen.user.model.AddressDO;

import java.util.List;

/**
 * <p>
 * 电商-公司收发货地址表 服务类
 * </p>
 *
 * @author linmsne
 * @since 2021-04-06
 */
public interface AddressService {

    AddressDO details(int id);

    int add(AddressGetInput input);

    AddressVO findById(long addressId);

    int del(long addressId);


    List<AddressVO> findAll();
}
