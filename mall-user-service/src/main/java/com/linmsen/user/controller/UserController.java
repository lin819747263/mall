package com.linmsen.user.controller;


import com.linmsen.JsonData;
import com.linmsen.enums.BizCodeEnum;
import com.linmsen.user.controller.vo.RegisterVO;
import com.linmsen.user.controller.vo.UserLoginRequest;
import com.linmsen.user.service.FileService;
import com.linmsen.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linmsne
 * @since 2021-04-06
 */
@Api(tags = "用户")
@RestController
@RequestMapping("/api/user/v1")
public class UserController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @ApiOperation("用户头像上传")
    @PostMapping(value = "upload")
    public JsonData uploadHeaderImg(@ApiParam(value = "文件上传",required = true) @RequestPart("file") MultipartFile file){

        String result = fileService.uploadUserHeadImg(file);

        return result != null?JsonData.buildSuccess(result):JsonData.buildResult(BizCodeEnum.FILE_UPLOAD_USER_IMG_FAIL);

    }

    @ApiOperation("用户注册")
    @PostMapping(value = "register")
    public JsonData register(RegisterVO register){
         return userService.register(register);
    }

    /**
     * 登录
     * @param loginRequest
     * @return
     */
    @PostMapping("login")
    public JsonData login(UserLoginRequest loginRequest){
        JsonData jsonData = userService.login(loginRequest);
        return jsonData;
    }

    /**
     * 登录
     * @param loginRequest
     * @return
     */
    @GetMapping("getUser")
    public JsonData getUser(){
        JsonData jsonData = userService.getUser();
        return jsonData;
    }
}

