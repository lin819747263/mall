package com.linmsen.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linmsen.CommonUtil;
import com.linmsen.JsonData;
import com.linmsen.JwtUtil;
import com.linmsen.LoginUser;
import com.linmsen.content.UserContent;
import com.linmsen.enums.BizCodeEnum;
import com.linmsen.user.SendCodeEnum;
import com.linmsen.user.controller.vo.RegisterVO;
import com.linmsen.user.controller.vo.UserLoginRequest;
import com.linmsen.user.mapper.UserMapper;
import com.linmsen.user.model.UserDO;
import com.linmsen.user.service.NotifyService;
import com.linmsen.user.service.UserService;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        BeanUtils.copyProperties(register, userDO);
        userDO.setSecret("$1$" + CommonUtil.getStringRandomCode(8));
        String cryptPwd = Md5Crypt.md5Crypt(register.getPwd().getBytes(), userDO.getSecret());
        userDO.setPwd(cryptPwd);

        userMapper.insert(userDO);

        // 设置密码

        return JsonData.buildSuccess();
    }

    @Override
    public JsonData login(UserLoginRequest loginRequest) {
        List<UserDO> list = userMapper.selectList(
                new QueryWrapper<UserDO>().eq("mail", loginRequest.getMail()));

        if (list != null && list.size() == 1) {
            UserDO userDO = list.get(0);
            String cryptPwd = Md5Crypt.md5Crypt(loginRequest.getPwd().getBytes(), userDO.getSecret());
            if (cryptPwd.equals(userDO.getPwd())) {
                //生成token令牌
                LoginUser userDTO = new LoginUser();
                BeanUtils.copyProperties(userDO, userDTO);
                String token = JwtUtil.geneJsonWebToken(userDTO);
                return JsonData.buildSuccess(token);

            }
            //密码错误
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_PWD_ERROR);
        } else {
            //未注册
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_UNREGISTER);
        }
    }

    @Override
    public JsonData getUser() {
        long id = UserContent.get().getId();
        UserDO user = userMapper.selectById(id);
        return JsonData.buildSuccess(user);
    }


    private boolean checkUnique(String email){
        return false;
    }
}
