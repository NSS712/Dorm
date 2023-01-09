package com.example.dorm.handle;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dorm.config.RabbitmqConfig;
import com.example.dorm.entity.*;
import com.example.dorm.mapper.*;
import com.example.dorm.utils.Result;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.Types.INTEGER;
import static java.sql.Types.NULL;

@Component
public class ReceiveHandler {
    //监听email队列

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


    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_EMAIL})
    public void receive_email(String msg, Message message, Channel channel){
        System.out.println("QUEUE_INFORM_EMAIL msg"+msg);
        String[] splited = msg.split("\\s+");
        int uid=Integer.parseInt(splited[0]);
        int group_id=Integer.parseInt(splited[1]);
        int building_id=Integer.parseInt(splited[2]);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String time=timestamp.toString();

        Orders order=new Orders();
        order.setCreateTime(time);
        order.setFinishTime(time);
        order.setSubmitTime(time);
        order.setId(NULL);
        order.setUid(uid);
        order.setGroupId(group_id);
        order.setBuildingId(building_id);
        order.setRoomId((long)0);
        order.setRemarks("Successfully submitted");
        order.setIsDel(0);
        order.setStatus(0);

        ordersMapper.insert(order);


        QueryWrapper<Orders> so = new QueryWrapper<>();
        so.eq("group_id",group_id);
        so.eq("building_id",building_id);
        List<Orders> ordersList=ordersMapper.selectList(so);
        long order_id=0;
        for (Orders order0:ordersList){
            order_id= Math.max(order0.getId(), order_id);
        }


        Groups group=groupMapper.selectById(group_id);
        System.out.println("group_name: "+group.getName());
        Map<String,Object> d = new HashMap<>();
        d.put("order_id",order_id);
        System.out.println("orderID:"+order_id);
        order.setId(order_id);
        //查看小组分配情况

        if(group.getStatus()==1){
            order.setStatus(2);
            order.setRemarks("Fail,the team has been allocated room");
            ordersMapper.updateById(order);
            return ;
        }

        System.out.println("uid"+uid);
        QueryWrapper<Users> wp3 = new QueryWrapper<>();
        wp3.eq("uid",uid);
        Users user= usersMapper.selectOne(wp3);
        long gender=user.getGender();

        //获取组内人数和组员列表
        QueryWrapper<Groupers> wp1 = new QueryWrapper<>();
        wp1.eq("group_id",group_id);
        wp1.eq("is_del",0);
        List<Groupers> groupersList=groupersMapper.selectList(wp1);
        long num=groupersList.size();
        System.out.println("组内人数："+num);

        //查询符合性别和楼号要求的房间
        QueryWrapper<Rooms> wp = new QueryWrapper<>();
        wp.eq("gender",gender);
        wp.eq("building_id",building_id);
        List<Rooms> roomList=roomMapper.selectList(wp);
        //System.out.println("room列表："+roomList.get(0).getName());

        //查询符合要求的床位
        for (Rooms rooms : roomList) {
            long roomid = rooms.getId();
            QueryWrapper<Beds> wp2 = new QueryWrapper<>();
            wp2.eq("status", 0);
            wp2.eq("is_valid", 1);
            wp2.eq("room_id", roomid);
            List<Beds> bedsList = bedMapper.selectList(wp2);
            System.out.println("bedlist:"+bedsList.size());
            if (bedsList.size() >=num){
                for(int i=0;i<num;i++){
                    bedsList.get(i).setUid(groupersList.get(i).getUid());
                    bedsList.get(i).setStatus(1);
                    bedMapper.updateById(bedsList.get(i));
                }
                group.setStatus(1);
                groupMapper.updateById(group);
                order.setStatus(1);
                order.setRoomId(roomid);
                order.setRemarks("Successfully allocated");
                ordersMapper.updateById(order);
                return ;
            }
            else {
                continue;
            }

        }
        order.setRemarks("Fail,no spare room");
        order.setStatus(2);
        ordersMapper.updateById(order);
    }
}
