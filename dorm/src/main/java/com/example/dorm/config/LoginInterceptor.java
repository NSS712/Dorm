package com.example.dorm.config;


import com.example.dorm.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    TokenUtil tokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=tokenUtil.getToken(request,0);
        if(token!=null&&tokenUtil.verify(token,0)){
            System.out.println("通过");
            return true;
        }
        System.out.println("请登录");
        return false;
    }
}
