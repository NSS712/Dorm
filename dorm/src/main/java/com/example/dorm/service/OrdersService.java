package com.example.dorm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dorm.config.RabbitmqConfig;
import com.example.dorm.entity.*;
import com.example.dorm.mapper.*;
import com.example.dorm.utils.OrderCreate;
import com.example.dorm.utils.ReOrderInfo;
import com.example.dorm.utils.ReOrderList;
import com.example.dorm.utils.Result;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.collections.RedisList;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

import static java.sql.Types.NULL;

@Service
@Component
public class OrdersService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    RoomsMapper roomMapper;

    @Autowired
    BedsMapper bedMapper;

    @Autowired
    GroupersMapper groupersMapper;

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    public Result<Map<String,Object>> createOrder(String token,Long group_id,Long building_id){
        String temp= (String) redisTemplate.opsForValue().get("token:"+token);
        int uid=Integer.parseInt(temp);
        String message = temp+" "+group_id.toString()+" "+building_id.toString();
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM, "inform.email", message);
        System.out.println("send");
        Map<String,Object> d = new HashMap<>();
        d.put("order_id",0);
        return Result.success(d);

//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        String time=timestamp.toString();
//
//
//
//        Orders order=new Orders();
//        order.setCreateTime(time);
//        order.setFinishTime(time);
//        order.setSubmitTime(time);
//        order.setId(NULL);
//        order.setUid(uid);
//        order.setGroupId(group_id);
//        order.setBuildingId(building_id);
//        order.setRoomId((long)0);
//        order.setRemarks("Successfully submitted");
//        order.setIsDel(0);
//        order.setStatus(0);
//
//        ordersMapper.insert(order);
//
//
//        QueryWrapper<Orders> so = new QueryWrapper<>();
//        so.eq("group_id",group_id);
//        so.eq("building_id",building_id);
//        List<Orders> ordersList=ordersMapper.selectList(so);
//        long order_id=0;
//        for (Orders order0:ordersList){
//            order_id= Math.max(order0.getId(), order_id);
//        }
//
//
//        Groups group=groupMapper.selectById(group_id);
//        System.out.println("group_name: "+group.getName());
//        Map<String,Object> d = new HashMap<>();
//        d.put("order_id",order_id);
//        System.out.println("orderID:"+order_id);
//        order.setId(order_id);
//        //查看小组分配情况
//
//        if(group.getStatus()==1){
//            order.setStatus(2);
//            order.setRemarks("Fail,the team has been allocated room");
//            ordersMapper.updateById(order);
//            return Result.success(d);
//        }
//
//        System.out.println("uid"+uid);
//        QueryWrapper<Users> wp3 = new QueryWrapper<>();
//        wp3.eq("uid",uid);
//        Users user= usersMapper.selectOne(wp3);
//        long gender=user.getGender();
//
//        //获取组内人数和组员列表
//        QueryWrapper<Groupers> wp1 = new QueryWrapper<>();
//        wp1.eq("group_id",group_id);
//        wp1.eq("is_del",0);
//        List<Groupers> groupersList=groupersMapper.selectList(wp1);
//        long num=groupersList.size();
//        System.out.println("组内人数："+num);
//
//        //查询符合性别和楼号要求的房间
//        QueryWrapper<Rooms> wp = new QueryWrapper<>();
//        wp.eq("gender",gender);
//        wp.eq("building_id",building_id);
//        List<Rooms> roomList=roomMapper.selectList(wp);
//       //System.out.println("room列表："+roomList.get(0).getName());
//
//        //查询符合要求的床位
//        for (Rooms rooms : roomList) {
//            long roomid = rooms.getId();
//            QueryWrapper<Beds> wp2 = new QueryWrapper<>();
//            wp2.eq("status", 0);
//            wp2.eq("is_valid", 1);
//            wp2.eq("room_id", roomid);
//            List<Beds> bedsList = bedMapper.selectList(wp2);
//            System.out.println("bedlist:"+bedsList.size());
//            if (bedsList.size() >=num){
//                for(int i=0;i<num;i++){
//                    bedsList.get(i).setUid(groupersList.get(i).getUid());
//                    bedsList.get(i).setStatus(1);
//                    bedMapper.updateById(bedsList.get(i));
//                }
//                group.setStatus(1);
//                groupMapper.updateById(group);
//                order.setStatus(1);
//                order.setRoomId(roomid);
//                order.setRemarks("Successfully allocated");
//                ordersMapper.updateById(order);
//                return Result.success(d);
//            }
//            else {
//                continue;
//            }
//
//        }
//        order.setRemarks("Fail,no spare room");
//        order.setStatus(2);
//        ordersMapper.updateById(order);
//        return Result.success(d);
    }

    public Result<Object> getOrderList(String token){
        String temp= (String) redisTemplate.opsForValue().get("token:"+token);
        int uid=Integer.parseInt(temp);
        QueryWrapper<Orders> wp = new QueryWrapper<>();
        wp.eq("uid",uid);
        Orders order=new Orders();
        List<ReOrderList> reOrderList =new ArrayList<ReOrderList>();;
        List<Orders> orderlist = ordersMapper.selectList(wp);
        for(Orders each : orderlist){
            ReOrderList reOder=new ReOrderList();
            reOder.building_name=""+each.getBuildingId();
            reOder.order_id=each.getId();
            reOder.status=each.getStatus();
            reOder.group_name=""+each.getGroupId();
            reOder.result_content=each.getRemarks();
            reOder.submit_time=""+each.getSubmitTime();
            reOrderList.add(reOder);
        }
        Map<String,Object> Res=new HashMap<>();
        Res.put("rows",reOrderList);

        return Result.success(Res);
    }
    public Result<ReOrderInfo> getOrderInfo(String token, Long order_id){
        String temp= (String) redisTemplate.opsForValue().get("token:"+token);
        System.out.println("temp:"+temp);
        int uid=Integer.parseInt(temp);
        System.out.println("uid:"+uid);
        QueryWrapper<Orders> wp = new QueryWrapper<>();
        wp.eq("id",order_id);
        List<Orders> orderlist = ordersMapper.selectList(wp);
        Orders order=orderlist.get(0);
        ReOrderInfo reOrderInfo = new ReOrderInfo();
        reOrderInfo.setStatus(order.getStatus());
        reOrderInfo.setRoom_id(order.getRoomId());
        return Result.success(reOrderInfo);
    }
}
