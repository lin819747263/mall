package com.linmsen.user.service;

import com.linmsen.UserApplication;
import com.linmsen.user.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    void sendMail() {
        mailService.sendMail("819747263@qq.com", "测试", "你的验证码为：655455");
    }
}