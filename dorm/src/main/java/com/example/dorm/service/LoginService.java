package com.example.dorm.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dorm.entity.Auth;
import com.example.dorm.entity.LoginInfo;
import com.example.dorm.mapper.AuthMapper;
import com.example.dorm.utils.ErrorCode;
import com.example.dorm.utils.Result;
import com.example.dorm.mapper.UsersMapper;
import com.example.dorm.utils.TokenUtil;
import com.example.dorm.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    AuthMapper authMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    TokenUtil tokenUtil;

    private static final String salt="459*dk";//加密盐

    //更新token与redis
    public LoginInfo update(int uid,HttpServletResponse response,boolean is_refresh){
        //生成token和refresh_token
        String token= UUID.randomUUID().toString().replaceAll("-", "");
        String refresh_token=UUID.randomUUID().toString().replaceAll("-", "");
        //redis操作,存储id-token和token-id
        //如果存在旧的信息，则删除
        List<String> old_info=redisTemplate.opsForList().range("uid:"+String.valueOf(uid),0,-1);
        long remain_second=0;
        if(old_info!=null&&old_info.size()>0){
            remain_second=redisTemplate.opsForValue().getOperations().getExpire("refresh_token:"+old_info.get(1));//获取refresh_token剩余时间
            redisTemplate.delete("uid:"+String.valueOf(uid));
            redisTemplate.delete("token:"+old_info.get(0));
            redisTemplate.delete("refresh_token:"+old_info.get(1));

        }

        System.out.println(old_info);
        //更新新的token
        redisTemplate.opsForList().rightPush("uid:"+String.valueOf(uid),token);
        redisTemplate.opsForList().rightPush("uid:"+String.valueOf(uid),refresh_token);
        redisTemplate.opsForValue().set("token:"+token,String.valueOf(uid),TokenUtil.EXPIRE_TIME_TOKEN , TimeUnit.SECONDS);
        if(is_refresh)
            redisTemplate.opsForValue().set("refresh_token:"+refresh_token,String.valueOf(uid),remain_second,TimeUnit.SECONDS);
        else
            redisTemplate.opsForValue().set("refresh_token:"+refresh_token,String.valueOf(uid),TokenUtil.EXPIRE_TIME_REFRESH,TimeUnit.SECONDS);
        System.out.println("refresh_token剩余时间："+redisTemplate.opsForValue().getOperations().getExpire("refresh_token:"+refresh_token));

        //设置cookie
        Cookie cookie1=new Cookie("token",token);
        Cookie cookie2=new Cookie("refresh_token",refresh_token);
        cookie1.setPath("/");//访问所有路径携带token
        cookie2.setPath("/auth/refresh");//刷新时携带refresh_token
        response.addCookie(cookie1);
        response.addCookie(cookie2);

        LoginInfo loginInfo=new LoginInfo();
        loginInfo.setAccess_token(token);
        loginInfo.setRefresh_token(refresh_token);
        if(is_refresh)
            loginInfo.setexpires_in(remain_second);
        else
            loginInfo.setexpires_in(TokenUtil.EXPIRE_TIME_TOKEN);
        return loginInfo;
    }
    //用户登录并检查是否正确
    public Result check(String username, String password, HttpServletResponse response){
        Result result=new Result();

        //查找Auth中username=username的记录
        QueryWrapper<Auth> wp = new QueryWrapper<>();
        wp.eq("username",username);
        List<Auth> auths = authMapper.selectList(wp);
        System.out.println(auths);
        Auth auth=auths.get(0);
        if(auth==null||!auth.getPassword().equals(DigestUtils.md5DigestAsHex((password+salt).getBytes()))){
            result.setmessage(ErrorCode.LOGIN_ERROR.getMessage());
            result.setCode(ErrorCode.LOGIN_ERROR.getCode());
        }
        else{
            //合法则进行更新

            int uid= (int) auth.getUid();
            LoginInfo loginInfo=update(uid,response,false);
            result.setData(loginInfo);
            result.setCode(ErrorCode.SUCCESS.getCode());
            result.setmessage(ErrorCode.SUCCESS.getMessage());
        }
        return result;
    }

    public Result refresh(String refresh_token,HttpServletResponse response){
        Result result=new Result();
        String temp=(String)redisTemplate.opsForValue().get("refresh_token:"+refresh_token);
        if(temp==null){
            result.setCode(ErrorCode.REFRESH_ERROR.getCode());
            result.setmessage(ErrorCode.REFRESH_ERROR.getMessage());
        }
        else if(!tokenUtil.verify(refresh_token,1)){
            result.setCode(ErrorCode.REFRESH_ERROR.getCode());
            result.setmessage(ErrorCode.REFRESH_ERROR.getMessage());
        }
        else {
            int uid = Integer.parseInt(temp);
            LoginInfo loginInfo = update(uid, response,true);
            result.setData(loginInfo);
            result.setCode(ErrorCode.SUCCESS.getCode());
            result.setmessage(ErrorCode.SUCCESS.getMessage());
        }
        return result;
    }

    public Result logout(String token, HttpServletRequest request,HttpServletResponse response){
        Result result=new Result();

        String temp=(String)redisTemplate.opsForValue().get("token:"+token);
        int uid = Integer.parseInt(temp);
        List<String> old_info=redisTemplate.opsForList().range("uid:"+String.valueOf(uid),0,-1);
        if(old_info!=null&&old_info.size()>0){
            redisTemplate.delete("uid:"+String.valueOf(uid));
            redisTemplate.delete("token:"+old_info.get(0));
            redisTemplate.delete("refresh_token:"+old_info.get(1));
        }
        result.setCode(ErrorCode.SUCCESS.getCode());
        result.setmessage(ErrorCode.SUCCESS.getMessage());
        return result;
    }
}
