package com.linmsen.config;

import com.linmsen.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Bean
    LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/api/product/*/**")
                .addPathPatterns("/api/cart/*/**")
                .addPathPatterns("/api/banner/*/**");

        //不拦截的路径
//                .excludePathPatterns("/api/coupon/*/");

        WebMvcConfigurer.super.addInterceptors(registry);
    }


}

