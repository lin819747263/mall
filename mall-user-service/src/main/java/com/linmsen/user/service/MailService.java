package com.linmsen.user.service;

public interface MailService {

    void sendMail(String to, String sub, String content);
}
