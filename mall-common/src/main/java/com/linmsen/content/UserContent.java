package com.linmsen.content;

import com.linmsen.LoginUser;

public class UserContent {

    private static ThreadLocal<LoginUser> userThreadLocal = new ThreadLocal<>();


    public static void set(LoginUser user){
        userThreadLocal.set(user);
    }

    public static void get(){
        userThreadLocal.get();
    }

    public static void remove(){
        userThreadLocal.remove();
    }
}
