package com.example.dorm.controller;


import com.example.dorm.entity.Auth;
import com.example.dorm.utils.Result;
import com.example.dorm.mapper.UsersMapper;
import com.example.dorm.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    LoginService loginService;

    @Autowired
    UsersMapper usersMapper;

    @RequestMapping("/login")
    public Result login(HttpServletResponse response, @RequestBody Map<String, String> r){
        String username=r.get("username");
        String password=r.get("password");
        return loginService.check(username,password,response);
    }

    @RequestMapping("/refresh")
    public Result refresh(@CookieValue("refresh_token")String refresh_token,HttpServletResponse response){
        return loginService.refresh(refresh_token,response);
    }

    @RequestMapping("/logout")
    public Result logout(@CookieValue("token")String token,HttpServletRequest request,HttpServletResponse response){
        return loginService.logout(token,request,response);
    }

}

