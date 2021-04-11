package com.linmsen.user.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("注册对象")
@Data
public class RegisterVO {

    @ApiModelProperty("邮箱")
    private String mail;

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("密码")
    private String pwd;

    @ApiModelProperty("头像")
    private String headImg;

    @ApiModelProperty("性别")
    private Integer sex;

    @ApiModelProperty("验证码")
    private Integer code;

}
