package com.linmsen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linmsen.mapper.AddressMapper;
import com.linmsen.model.AddressDO;
import com.linmsen.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 电商-公司收发货地址表 服务实现类
 * </p>
 *
 * @author linmsne
 * @since 2021-04-06
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressMapper addressMapper;

    @Override
    public AddressDO details(int id) {
        return addressMapper.selectOne(new QueryWrapper<AddressDO>().eq("id", id));
    }
}
