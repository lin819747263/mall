package com.linmsen;

import com.linmsen.enums.BizCodeEnum;
import lombok.Data;

@Data
public class BizException extends RuntimeException {

    private Integer code;
    private String msg;

    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
    public BizException(BizCodeEnum bizCodeEnum){
        this(bizCodeEnum.getCode(), bizCodeEnum.getMessage());
    }
}
