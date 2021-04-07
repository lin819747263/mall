package com.linmsen.service;

import com.linmsen.model.AddressDO;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
