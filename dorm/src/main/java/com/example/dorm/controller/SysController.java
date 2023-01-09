package com.example.dorm.controller;


import com.example.dorm.entity.Sys;
import com.example.dorm.entity.Users;
import com.example.dorm.service.SysService;
import com.example.dorm.service.UsersService;
import com.example.dorm.utils.OpenTime;
import com.example.dorm.utils.ReClassLimit;
import com.example.dorm.utils.ReGroupNum;
import com.example.dorm.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/sys")
public class SysController {

    @Autowired
    public SysService sysService;

    @GetMapping("/opentime")
    public Result<OpenTime> getOpenTime(@RequestHeader("Authorization") String token){
        String[] splited = token.split("\\s+");
        token=splited[1];
        return sysService.getOpenTime(token);
    }

    @GetMapping("/classlimit")
    public Result<ReClassLimit> getClassLimit(@RequestHeader("Authorization") String token){
        String[] splited = token.split("\\s+");
        token=splited[1];
        return sysService.getClassLimit(token);
    }

    @GetMapping("/groupnum")
    public Result<ReGroupNum> getGroupNum(@RequestHeader("Authorization") String token){
        String[] splited = token.split("\\s+");
        token=splited[1];
        return sysService.getGroupNum(token);
    }

}
