package com.linmsen.user.service.impl;

import com.linmsen.CheckUtil;
import com.linmsen.CommonUtil;
import com.linmsen.JsonData;
import com.linmsen.constant.CacheKey;
import com.linmsen.enums.BizCodeEnum;
import com.linmsen.user.SendCodeEnum;
import com.linmsen.user.service.MailService;
import com.linmsen.user.service.NotifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class NotifyServiceImpl implements NotifyService {

    private static final String SUBJECT = "林木森";
    private static final String CONTENT = "您的验证码为%s,有效期为60秒";
    private static final Integer CODE_EXPIRED = 1000*60*10;
    @Autowired
    private MailService mailService;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public JsonData sendCode(SendCodeEnum sendCodeType, String to) {
        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY,sendCodeType.name(),to);

        String cacheValue = redisTemplate.opsForValue().get(cacheKey);

        //如果不为空，则判断是否60秒内重复发送
        if(StringUtils.isNotBlank(cacheValue)){
            long ttl = Long.parseLong(cacheValue.split("_")[1]);
            //当前时间戳-验证码发送时间戳，如果小于60秒，则不给重复发送
            if(System.currentTimeMillis() - ttl < 1000*60){
                log.info("重复发送验证码,时间间隔:{} 秒",(System.currentTimeMillis()-ttl)/1000);
                return JsonData.buildResult(BizCodeEnum.CODE_LIMITED);
            }
        }

        //拼接验证码 2322_324243232424324
        String code = CommonUtil.getRandomCode(6);

        String value = code+"_"+System.currentTimeMillis();

        redisTemplate.opsForValue().set(cacheKey,value,CODE_EXPIRED, TimeUnit.MILLISECONDS);

        if(CheckUtil.isEmail(to)){
            //邮箱验证码
            mailService.sendMail(to,SUBJECT,String.format(CONTENT,code));

            return JsonData.buildSuccess();

        } else if(CheckUtil.isPhone(to)){
            //短信验证码

        }

        return JsonData.buildResult(BizCodeEnum.CODE_TO_ERROR);
    }
}
