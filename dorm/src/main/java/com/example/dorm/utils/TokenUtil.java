package com.example.dorm.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class TokenUtil {
    @Autowired
    RedisTemplate redisTemplate;
    public static final long EXPIRE_TIME_TOKEN= 100000000;//token到期时间
    public static final long EXPIRE_TIME_REFRESH= 100000000;//refresh_token到期时间

    //获取tokne,0为token,1为refresh_token
    public String getToken(HttpServletRequest request, int type) {
        String name="token";
        if(type!=0)
            name="refresh_token";
        Cookie[] cookies = request.getCookies();
        if(cookies==null)
            return null;
        for (Cookie c : cookies) {
            //System.out.println(6);
            if (c.getName().equals(name)) {
                return c.getValue();
            }
        }
        return null;
    }

    public boolean verify(String token,int type){
        String name="token:";
        if(type!=0)
            name="refresh_token:";
        if(redisTemplate.hasKey(name+token))
            System.out.println("token合法");
        else
            System.out.println("token不合法");
        return redisTemplate.hasKey(name+token);

    }
}

