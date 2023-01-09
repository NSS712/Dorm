package com.example.dorm.controller;
import com.example.dorm.entity.Auth;
import com.example.dorm.entity.Users;
import com.example.dorm.service.UsersService;
import com.example.dorm.utils.RePasswd;
import com.example.dorm.utils.ReRoomInfo.ReRoomInfo;
import com.example.dorm.utils.ReUserInfo;
import com.example.dorm.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UsersController {
    @Autowired
    public UsersService usersService;

    @GetMapping("/myinfo")
    public Result<ReUserInfo> getUserInfo(@RequestHeader("Authorization") String token){
        String[] splited = token.split("\\s+");
        token=splited[1];

        return usersService.getUserInfo(token);
    }

    @PostMapping("/passwd")
    @ResponseBody
    public Result setpasswd(@RequestHeader("Authorization") String token, @RequestBody RePasswd rePasswd){
        String[] splited = token.split("\\s+");
        token=splited[1];
        return usersService.setpasswd(token, rePasswd);
    }

    @GetMapping("/myroom")
    public Result<ReRoomInfo> getMyRoom(@RequestHeader("Authorization") String token){
        String[] splited = token.split("\\s+");
        token=splited[1];
        return usersService.getMyRoom(token);
    }

}
