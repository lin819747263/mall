package com.linmsen.user.config;

import com.linmsen.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor())
                //拦截的路径
                .addPathPatterns("/api/user/*/**","/api/address/*/**")

        //排查不拦截的路径
                .excludePathPatterns("/api/user/*/send_code","/api/user/*/getCaptcha",
                "/api/user/*/register","/api/user/*/login","/api/user/*/upload");

    }

}
