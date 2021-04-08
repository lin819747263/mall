package com.linmsen;

import lombok.Data;

@Data
public class LoginUser {
    private Long id;

    private String mail;

    private String name;

    private String pwd;

    private String headImg;

    private Integer sex;

    private Integer code;
}
