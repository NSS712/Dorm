package com.example.dorm.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dorm.entity.*;
import com.example.dorm.entity.Class;
import com.example.dorm.mapper.*;
import com.example.dorm.service.UsersService;
import com.example.dorm.utils.RePasswd;
import com.example.dorm.utils.ReRoomInfo.Member;
import com.example.dorm.utils.ReRoomInfo.ReRoomInfo;
import com.example.dorm.utils.ReRoomInfo.Row;
import com.example.dorm.utils.ReUserInfo;
import com.example.dorm.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    public AuthMapper authMapper;

    @Autowired
    public UsersMapper usersMapper;

    @Autowired
    public StudentInfoMapper studentInfoMapper;

    @Autowired
    public ClassMapper classMapper;

    @Autowired
    public RoomsMapper roomsMapper;

    @Autowired
    public BedsMapper bedsMapper;

    private static final String salt="459*dk";//加密盐


    @Override
    public Result<ReUserInfo> getUserInfo(String token) {
        System.out.println(token);
        String temp= (String) redisTemplate.opsForValue().get("token:"+token);
        long uid=Integer.parseInt(temp);
        System.out.println("uid"+uid);
        //查找Users中uid="uid"的记录
        QueryWrapper<Users> wp0 = new QueryWrapper<>();
        wp0.eq("uid",uid);
        List<Users> users = usersMapper.selectList(wp0);
        Users user=users.get(0);
        System.out.println(user);

        //查找StudentInfo中uid="uid"的记录
        QueryWrapper<StudentInfo> wp = new QueryWrapper<>();
        wp.eq("uid",uid);
        System.out.println("sssss222222");
        List<StudentInfo> studentInfos = studentInfoMapper.selectList(wp);
        System.out.println(studentInfos);
        StudentInfo student=studentInfos.get(0);
        System.out.println(student.getClassId());
        System.out.println("sssss33333");
        ReUserInfo reUserInfo=new ReUserInfo();
        reUserInfo.setUid(uid);
        reUserInfo.setStudentid(student.getStudentid());
        System.out.println("Studentid:"+student.getStudentid());
        reUserInfo.setName(user.getName());
        reUserInfo.setGender(user.getGender());
        reUserInfo.setEmail(user.getEmail());
        reUserInfo.setTel(user.getTel());
        reUserInfo.setlast_login_time(String.valueOf(user.getLastLoginTime()));
        System.out.println("user.getlast_login_time()): "+user.getLastLoginTime());
        reUserInfo.setverification_code(student.getVerificationCode());
        System.out.println("student.getverification_code():"+student.getVerificationCode());


        //查找class中id="student.getClassId()"的记录
        QueryWrapper<Class> wp1 = new QueryWrapper<>();
        wp1.eq("id",student.getClassId());
        List<Class> classes = classMapper.selectList(wp1);

        reUserInfo.setclass_name(classes.get(0).getName());
        return Result.success(reUserInfo);
    }

    @Override
    public Result setpasswd(String token, RePasswd rePasswd) {
        //System.out.println(token);
        String temp= (String) redisTemplate.opsForValue().get("token:"+token);
        long uid=Integer.parseInt(temp);
        System.out.println("uid:    "+uid);

        //查找Auth中uid="uid"的记录
        QueryWrapper<Auth> wp = new QueryWrapper<>();
        wp.eq("uid",uid);
        List<Auth> auths = authMapper.selectList(wp);

        System.out.println(auths);
        Auth auth=auths.get(0);
        System.out.println("Auth:    "+auth);

        //旧密码不正确
        if(!auth.getPassword().equals(DigestUtils.md5DigestAsHex((rePasswd.getOldPasswd()+salt).getBytes()))){
            System.out.println("auth.getPassword():    "+auth.getPassword());
            System.out.println("加密"+DigestUtils.md5DigestAsHex((rePasswd.getOldPasswd()+salt).getBytes()));
            return Result.error(512002,"旧密码输入错误");
        }

        //auth.setPassword(rePasswd.getNewPasswd());
        auth.setPassword(DigestUtils.md5DigestAsHex((rePasswd.getNewPasswd()+salt).getBytes()));
        authMapper.updateById(auth);
        return Result.success();
    }




    @Override
    public Result<ReRoomInfo> getMyRoom(String token) {
        String temp= (String) redisTemplate.opsForValue().get("token:"+token);
        long uid=Integer.parseInt(temp);
        System.out.println("uid:  "+uid);
        ReRoomInfo reRoomInfo=new ReRoomInfo();

        //查找Beds中uid="uid"的记录
        QueryWrapper<Beds> wp = new QueryWrapper<>();
        wp.eq("uid",uid);
        System.out.println("查找我的宿舍："+uid);
        List<Beds> beds0 = bedsMapper.selectList(wp);

        //如果我的宿舍信息为空
        /*if(beds0.size()==0){
            return Result.error(512001,"我的宿舍信息为空");
        }*/
        //如果我的宿舍信息不为空
        System.out.println("beds0.size():  "+beds0.size());
        Beds bed0=beds0.get(0);
        System.out.println(bed0);
        System.out.println("bed0.getname():  "+bed0.getName());
        System.out.println("bed0.getId():  "+bed0.getId());
        //System.out.println("bed0.isdel:  "+bed0.getIs_del());
        //System.out.println("bed0.isvalid:  "+bed0.getIs_valid());
        System.out.println("bed0.getRoom_id()： "+bed0.getRoomId());

        //查找Rooms中id="bed.getRoomId()"的记录
        QueryWrapper<Rooms> wp5 = new QueryWrapper<>();
        wp5.eq("id",bed0.getRoomId());
        List<Rooms> rooms0 = roomsMapper.selectList(wp5);
        System.out.println("rooms.size:  "+rooms0.size());

        Rooms room = rooms0.get(0);
        System.out.println("room:  "+room);


        //查找Beds中roomId="bed.getRoomId()"的记录
        QueryWrapper<Beds> wp1 = new QueryWrapper<>();
        wp1.eq("room_id",bed0.getRoomId());
        List<Beds> beds = bedsMapper.selectList(wp1);
        System.out.println(beds);

        Row[] rows= new Row[beds.size()];
        for (int i=0;i<beds.size();i++){
            Row row =new Row();

            //查找Users中uid="uid"的记录
            QueryWrapper<Users> wp2 = new QueryWrapper<>();
            wp2.eq("uid",beds.get(i).getUid());
            List<Users> users = usersMapper.selectList(wp2);
            Users user=users.get(0);
            System.out.println("users***"+user);
            row.setName(user.getName());
            rows[i]=row;
        }
        Member member=new Member();
        member.setRows(rows);
        reRoomInfo.setRoomName(room.getName());
        reRoomInfo.setMember(member);
        reRoomInfo.setRoomId(room.getId());
        return Result.success(reRoomInfo);
    }


}
