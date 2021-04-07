package com.linmsen.user.service;

import com.linmsen.JsonData;
import com.linmsen.user.SendCodeEnum;

public interface NotifyService {

    JsonData sendCode(SendCodeEnum sendCodeType, String to);
}
