package com.example.dorm.service;

import com.example.dorm.entity.Sys;
import com.example.dorm.entity.Users;
import com.example.dorm.utils.OpenTime;
import com.example.dorm.utils.ReClassLimit;
import com.example.dorm.utils.ReGroupNum;
import com.example.dorm.utils.Result;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface SysService {
    /**
     *
     */
    Result<OpenTime> getOpenTime(String token);

    Result<ReClassLimit> getClassLimit(String token);

    Result<ReGroupNum> getGroupNum(String token);
}
