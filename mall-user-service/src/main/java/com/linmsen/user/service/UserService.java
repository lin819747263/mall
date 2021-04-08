package com.linmsen.user.service;

import com.linmsen.JsonData;
import com.linmsen.user.controller.vo.RegisterVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linmsne
 * @since 2021-04-06
 */
public interface UserService {

    JsonData register(RegisterVO register);

}
