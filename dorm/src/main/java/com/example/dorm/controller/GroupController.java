package com.example.dorm.controller;

import com.example.dorm.service.GroupService;
import com.example.dorm.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping("/team/create")
    public Result createGroup(@RequestHeader("Authorization") String token, @RequestBody Map<String,String> r){
        String[] splited = token.split("\\s+");
        token=splited[1];
        String name=r.get("name");
        String describe=r.get("describe");
        return groupService.createGroup(token,name,describe);
    }

    @PostMapping("/team/del")
    public Result deleteGroup(@RequestHeader("Authorization") String token){
        String[] splited = token.split("\\s+");
        token=splited[1];
        //long teamId=Long.parseLong(r.get("team_id"));
        return groupService.deleteGroup(token);
    }
    @PostMapping("/team/join")
    public Result joinGroup(@RequestHeader("Authorization") String token, @RequestBody Map<String,String> r){
        String[] splited = token.split("\\s+");
        token=splited[1];
        String inviteCode=r.get("invite_code");
        return groupService.joinGroup(token,inviteCode);
    }
    @PostMapping("/team/quit")
    public Result quitGroup(@RequestHeader("Authorization") String token){
        String[] splited = token.split("\\s+");
        token=splited[1];
        //long teamId=Long.parseLong(r.get("team_id"));
        return groupService.quitGroup(token);
    }
    @GetMapping("/team/my")
    public Result getMyGroup(@RequestHeader("Authorization") String token){
        String[] splited = token.split("\\s+");
        token=splited[1];
        return groupService.getMyGroup(token);
    }

    @PostMapping("/team/transfer")
    public Result transferGroupCreator(@RequestHeader("Authorization") String token, @RequestBody Map<String,String> r){
        String[] splited = token.split("\\s+");
        token=splited[1];
        String studentId=r.get("student_id");
        //long studentId=Long.parseLong(r.get("student_id"));
        return groupService.transferGroupCreator(token,studentId);
    }
}
