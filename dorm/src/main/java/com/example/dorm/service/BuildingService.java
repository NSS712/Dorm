package com.example.dorm.service;

import org.springframework.stereotype.Service;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface BuildingService{
    Map<String,Object> getBuildingList(String token);
    Map<String,String> getBuildingInfo(Long buildingId,String token);
    Map<String,Object> getRoomInfo(String token, Long roomId);
    Map<String,Object> getBuildingCapacity(String token);

}
