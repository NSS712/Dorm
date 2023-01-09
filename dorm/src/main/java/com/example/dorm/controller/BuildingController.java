package com.example.dorm.controller;

import com.example.dorm.service.BuildingService;
import com.example.dorm.utils.ErrorCode;
import com.example.dorm.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/room")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;

    @GetMapping("/buildinglist")
    public Result buildingList(@RequestHeader("Authorization") String token){
        String[] splited = token.split("\\s+");
        token=splited[1];
        return Result.success(buildingService.getBuildingList(token));
    }

    @GetMapping("/building")
    public Result buildingInfo(@RequestHeader("Authorization") String token, @RequestParam("id") String buildingIdStr){
        String[] splited = token.split("\\s+");
        token=splited[1];
        Long buildingId=Long.parseLong(buildingIdStr);
        System.out.println(buildingId);
        if (buildingService.getBuildingInfo(buildingId,token)==null){
            return Result.error(ErrorCode.Param_ERROR.getCode(),ErrorCode.Param_ERROR.getMessage());
        }
        return Result.success(buildingService.getBuildingInfo(buildingId,token));
    }

    @GetMapping("/room")
    public Result roomInfo(@RequestHeader("Authorization") String token, @RequestParam("id") String roomIdStr){
        String[] splited = token.split("\\s+");
        token=splited[1];
        Long roomId=Long.parseLong(roomIdStr);
        System.out.println(roomId);
        return Result.success(buildingService.getRoomInfo(token,roomId));
    }
    @GetMapping("/empty")
    public Result EmptyRoom(@RequestHeader("Authorization") String token){
        String[] splited = token.split("\\s+");
        token=splited[1];
        return Result.success(buildingService.getBuildingCapacity(token));
    }
}
