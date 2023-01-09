package com.example.dorm.service;

import com.example.dorm.entity.Users;
import com.example.dorm.utils.RePasswd;
import com.example.dorm.utils.ReRoomInfo.ReRoomInfo;
import com.example.dorm.utils.ReUserInfo;
import com.example.dorm.utils.Result;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UsersService {

    /**
     * myinfo
     *
     */
    Result<ReUserInfo> getUserInfo(String token);

    /**
     *setpasswd
     */
    Result setpasswd(String token, @RequestBody RePasswd rePasswd);
    /**
     * myroom
     */
    Result<ReRoomInfo> getMyRoom(String token);

}
