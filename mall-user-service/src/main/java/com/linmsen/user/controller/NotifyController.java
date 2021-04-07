package com.linmsen.user.controller;

import com.google.code.kaptcha.Producer;
import com.linmsen.CommonUtil;
import com.linmsen.JsonData;
import com.linmsen.enums.BizCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

@Api(tags = "通知")
@RestController
@Slf4j
@RequestMapping("/api/user/v1")
public class NotifyController {

    private static final long CAPTCHA_CODE_EXPIRED = 60 * 1000 * 10;

    @Autowired
    @Qualifier("captchaProducer")
    private Producer captchaProducer;

//    @Autowired
//    NotifyServic notifyService

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("getCaptcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){
        String captcha = captchaProducer.createText();
        log.info(captcha);
        redisTemplate.opsForValue().set(getCaptchaKey(request), captcha, CAPTCHA_CODE_EXPIRED, TimeUnit.MINUTES);

        BufferedImage bufferedImage = captchaProducer.createImage(captcha);
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log.info("", e);
        }
    }

    /**
     * 支持手机号、邮箱发送验证码
     * @return
     */
    @ApiOperation("发送验证码")
    @GetMapping("send_code")
    public JsonData sendRegisterCode(@RequestParam(value = "to", required = true)String to,
                                     @RequestParam(value = "captcha", required = true)String  captcha,
                                     HttpServletRequest request){

        String key = getCaptchaKey(request);
        String cacheCaptcha = redisTemplate.opsForValue().get(key);

        if(captcha!=null && cacheCaptcha!=null && cacheCaptcha.equalsIgnoreCase(captcha)) {
            redisTemplate.delete(key);
//            JsonData jsonData = notifyService.sendCode(SendCodeEnum.USER_REGISTER,to);
            return null;
        }else {
            return JsonData.buildResult(BizCodeEnum.CODE_CAPTCHA);
        }

    }

    private String getCaptchaKey(HttpServletRequest request){
        String ip = CommonUtil.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");
        String key = "user:captcha:"+ CommonUtil.MD5(ip + userAgent);
        return key;
    }
}
