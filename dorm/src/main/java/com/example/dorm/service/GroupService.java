package com.example.dorm.service;

import com.example.dorm.utils.Result;

public interface GroupService {
    Result createGroup(String token,String name,String describe);
    Result deleteGroup(String token);
    Result joinGroup(String token,String inviteCode);
    Result quitGroup(String token);
    Result getMyGroup(String token);
    Result transferGroupCreator(String token,String studentId);
}
