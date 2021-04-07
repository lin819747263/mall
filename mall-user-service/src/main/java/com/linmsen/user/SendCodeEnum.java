package com.linmsen.user;

import lombok.Data;

public enum  SendCodeEnum {



    USER_REGISTER("USER_REGISTER");



    private String name;

    public String getName() {
        return name;
    }

    SendCodeEnum(String name) {
        this.name = name;
    }
}
