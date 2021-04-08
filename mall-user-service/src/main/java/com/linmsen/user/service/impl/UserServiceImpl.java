package com.linmsen.user.service.impl;

import com.linmsen.CommonUtil;
import com.linmsen.JsonData;
import com.linmsen.enums.BizCodeEnum;
import com.linmsen.user.SendCodeEnum;
import com.linmsen.user.controller.vo.RegisterVO;
import com.linmsen.user.mapper.UserMapper;
import com.linmsen.user.model.UserDO;
import com.linmsen.user.service.NotifyService;
import com.linmsen.user.service.UserService;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linmsne
 * @since 2021-04-06
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotifyService notifyService;

    @Override
    public JsonData register(RegisterVO register) {
        boolean check = false;
        if(StringUtils.isNotBlank(register.getEmail())){
            check = notifyService.checkCode(SendCodeEnum.USER_REGISTER, register.getEmail(), register.getCode().toString());
        }
        if(!check){
            return JsonData.buildResult(BizCodeEnum.CODE_ERROR);
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDO, userDO);
        userDO.setSecret("$1$" + CommonUtil.getStringRandomCode(8));
        String cryptPwd = Md5Crypt.md5Crypt(register.getPwd().getBytes(), userDO.getSecret());
        userDO.setPwd(cryptPwd);

        userMapper.insert(userDO);

        // 设置密码

        return JsonData.buildSuccess();
    }


    private boolean checkUnique(String email){
        return false;
    }
}
