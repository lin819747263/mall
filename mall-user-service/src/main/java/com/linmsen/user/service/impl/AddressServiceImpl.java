package com.linmsen.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linmsen.LoginUser;
import com.linmsen.content.UserContent;
import com.linmsen.user.controller.vo.AddressGetInput;
import com.linmsen.user.controller.vo.AddressVO;
import com.linmsen.user.mapper.AddressMapper;
import com.linmsen.user.model.AddressDO;
import com.linmsen.user.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public int add(AddressGetInput input) {
        LoginUser loginUser = UserContent.get();
        AddressDO addressDO = new AddressDO();
        addressDO.setCreateTime(new Date());
        BeanUtils.copyProperties(input, addressDO);
        addressDO.setUserId(loginUser.getId());
        //是否有默认收货地址
        if (addressDO.getDefaultStatus() == 1) {
            AddressDO defaultAddressDO = addressMapper.selectOne(new QueryWrapper<AddressDO>().eq("user_id", loginUser.getId()).eq("default_status", 1));
            if (defaultAddressDO != null) {
                //修改为非默认地址
                defaultAddressDO.setDefaultStatus(0);
                addressMapper.update(defaultAddressDO, new QueryWrapper<AddressDO>().eq("id", defaultAddressDO.getId()));
            }
        }
        int rows = addressMapper.insert(addressDO);
        return rows;
    }

    @Override
    public AddressVO findById(long addressId) {

        AddressDO addressDO = addressMapper.selectOne(new QueryWrapper<AddressDO>().eq("id", addressId));
        if (addressDO == null) {
            return null;
        }
        AddressVO addressVO = new AddressVO();
        BeanUtils.copyProperties(addressDO, addressVO);
        return addressVO;
    }

    @Override
    public int del(long addressId) {
        LoginUser loginUser = UserContent.get();
        int rows = addressMapper.delete(new QueryWrapper<AddressDO>().eq("id", addressId).eq("user_id", loginUser.getId()));
        return rows;
    }

    @Override
    public List<AddressVO> findAll() {
        long id = UserContent.get().getId();
        List<AddressDO> list = addressMapper.selectList(new QueryWrapper<AddressDO>().eq("id", id));
        return list.stream().map(x -> {
            AddressVO vo = new AddressVO();
            BeanUtils.copyProperties(x,vo);
            return vo;
        }).collect(Collectors.toList());
    }
}
