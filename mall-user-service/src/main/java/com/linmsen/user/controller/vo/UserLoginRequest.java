package com.linmsen.user.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class UserLoginRequest {

    @ApiModelProperty("邮箱")
    private String mail;

    @ApiModelProperty("密码")
    private String pwd;
}
