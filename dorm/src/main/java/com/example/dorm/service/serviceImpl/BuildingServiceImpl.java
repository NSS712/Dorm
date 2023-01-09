package com.example.dorm.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dorm.entity.*;
import com.example.dorm.mapper.BedsMapper;
import com.example.dorm.mapper.BuildingMapper;
import com.example.dorm.mapper.RoomsMapper;
import com.example.dorm.mapper.UsersMapper;

import com.example.dorm.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    public BuildingMapper buildingMapper;
    @Autowired
    public UsersMapper usersMapper;
    @Autowired
    public RoomsMapper roomMapper;
    @Autowired
    public BedsMapper bedMapper;

    public Map<String,Object> getBuildingList(String token){
        //下面的部分支持只显示该用户性别可以选的楼号
//        String temp= (String) redisTemplate.opsForValue().get("token:"+token);
//        int uid=Integer.parseInt(temp);
//        Users user=usersMapper.selectById(uid);
//        long gender=user.getGender();
//        //查找属于该性别或者男女都可选的楼号
//        QueryWrapper<Buildings> wrapper = new QueryWrapper<>();
//        //通过wrapper设置条件。
//        //ge gt  le lt
//        //>= >  <= <
//        //查询GENDER= 0 的记录
//        wrapper.eq("gender",gender).or().eq("gender",2) ;
//        List<Buildings> buildings = buildingMapper.selectList(wrapper);

        List<Buildings> buildings = buildingMapper.selectList(null);
        System.out.println(buildings);
        List<Map<String,Object>> rows=new LinkedList<>();

        for (Buildings building:buildings){
            Map<String,Object> buildingListInfo=new HashMap<>();
            buildingListInfo.put("building_id",building.getId());
            buildingListInfo.put("building_name",building.getName());
            rows.add(buildingListInfo);
            //rows.add(Arrays.asList(Long.toString(building.getId()),building.getName()));
        }
        Map<String,Object> buildingListRes=new HashMap<>();
        buildingListRes.put("rows",rows);
        return buildingListRes;
    }

    public Map<String,String> getBuildingInfo(Long building_id,String token){
        //可能楼号不合法
//        List<Buildings> buildingList= buildingMapper.selectList(null);
//        List<Long> building_ids=new ArrayList<>();
//        for (Buildings building:buildingList){
//            building_ids.add(building.getId());
//        }
//        if  (!building_ids.contains(building_id)){
//            return null;
//        }
//        name      describe image_url
        Buildings building=buildingMapper.selectById(building_id);
        if (building==null){
            return null;
        }
        Map<String,String> buildingInfo=new HashMap<>();
        buildingInfo.put("name",building.getName());
        buildingInfo.put("describe",building.getDescription());
        buildingInfo.put("image_url",building.getImageUrl());
        return buildingInfo;
    }

    public Map<String,Object> getRoomInfo(String token, Long roomId){
        System.out.println(roomId);

        Rooms room=roomMapper.selectById(roomId);
        if (room==null){
            System.out.println("no result");
            return null;
        }
        Map<String,Object> roomInfo=new HashMap<>();
        roomInfo.put("name",room.getName());
        roomInfo.put("gender",room.getGender());
        roomInfo.put("describe",room.getDescription());
        roomInfo.put("image_url",room.getImage_url());
        roomInfo.put("building_id",room.getBuilding_id());
        return roomInfo;
    }

//    public Map<String,Object> getBuildingCapacity(String token, Long gender) {
//        List<Beds> beds = bedMapper.selectList(null);
//        Map<Long, Long> buildingCapacity = new HashMap<>();
//        for (Beds bed : beds) {
//            if (bed.getStatus() == 0) {
//                Long roomId = bed.getRoomId();
//                Rooms room = roomMapper.selectById(roomId);
//                if(room.getGender()==gender){
//                    Buildings building = buildingMapper.selectById(room.getBuilding_id());
//                    Long building_id = building.getId();
//                    Long count = buildingCapacity.getOrDefault(building_id, 0L);
//                    buildingCapacity.put(building_id, count + 1);
//                }
//
//            }
//        }
//        List<Buildings> buildings = buildingMapper.selectList(null);
//        List<Long> building_ids = new ArrayList<>();
//        for (Buildings building : buildings) {
//            building_ids.add(building.getId());
//        }
//        List<Map<String,Object>> rows = new LinkedList<>();
//        for (Long id : building_ids) {
//            Map<String,Object> buildingCapacityInfo=new HashMap<>();
//            buildingCapacityInfo.put("building_id",id);
//            buildingCapacityInfo.put("gender",gender);
//            buildingCapacityInfo.put("cnt",buildingCapacity.getOrDefault(id, 0L));
//            rows.add(buildingCapacityInfo);
//            //rows.add(Arrays.asList(id, gender, buildingCapacity.getOrDefault(id, 0L)));
//        }
//
//        Map<String,Object> buildingCntRes=new HashMap<>();
//        buildingCntRes.put("row",rows);
//        return buildingCntRes;
//    }
//}

    public Map<String,Object> getBuildingCapacity(String token) {

        String temp= (String) redisTemplate.opsForValue().get("token:"+token);
        long uid=Integer.parseInt(temp);
        System.out.println("!!!"+uid);

        QueryWrapper<Users> so = new QueryWrapper<>();
        so.eq("uid",uid);
        Users u=usersMapper.selectOne(so);

        long gender=u.getGender();

        List<Beds> beds = bedMapper.selectList(null);
        List<Buildings> allBuildings = buildingMapper.selectList(null);
        Map<Long, Long> buildingCapacity = new HashMap<>();
        for (Buildings each:allBuildings){
            buildingCapacity.put(each.getId(),0L);
        }
        System.out.println(buildingCapacity);
        int flag=0;
        for (Beds bed : beds) {
            if (bed.getStatus()== 0) {
                Long roomId = bed.getRoomId();
                Rooms room = roomMapper.selectById(roomId);
                System.out.println(roomId);
                if(room.getGender()==gender){
                    flag=1;
                    System.out.println(room.getBuilding_id());
                    // Buildings building = buildingMapper.selectById(room.getBuilding_id());
                    Long building_id = room.getBuilding_id();
                    Long count = buildingCapacity.getOrDefault(building_id, 0L);
                    buildingCapacity.put(building_id, count + 1);
                }

            }
        }

        List<Map<String,Object>> rows = new LinkedList<>();

        for(Long id : buildingCapacity.keySet()){
            Map<String,Object> buildingCapacityInfo=new HashMap<>();
            buildingCapacityInfo.put("building_id",id);
            buildingCapacityInfo.put("gender",gender);
            buildingCapacityInfo.put("cnt",buildingCapacity.getOrDefault(id, 0L));
            rows.add(buildingCapacityInfo);
        }
        Map<String,Object> buildingCntRes=new HashMap<>();
        buildingCntRes.put("row",rows);
        return buildingCntRes;
    }
}
