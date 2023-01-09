package com.example.dorm.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.dorm.entity.Buildings;
import com.example.dorm.entity.Sys;
import com.example.dorm.entity.Users;
import com.example.dorm.mapper.SysMapper;
import com.example.dorm.mapper.UsersMapper;
import com.example.dorm.service.SysService;
import com.example.dorm.utils.OpenTime;
import com.example.dorm.utils.ReClassLimit;
import com.example.dorm.utils.ReGroupNum;
import com.example.dorm.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class SysServiceImpl implements SysService {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    public SysMapper sysMapper;

    @Autowired
    public UsersMapper usersMapper;

    @Override
    public Result<OpenTime> getOpenTime(String token) {
        //System.out.println(token);
        //查询KeyName="start_time"  的记录
        QueryWrapper<Sys> wp1 = new QueryWrapper<>();
        wp1.eq("key_name","start_time");
        List<Sys> sys1 = sysMapper.selectList(wp1);
        System.out.println(sys1);

        //查找"end_time"的记录
        QueryWrapper<Sys> wp2 = new QueryWrapper<>();
        wp2.eq("key_name","end_time");
        List<Sys> sys2 = sysMapper.selectList(wp2);
        System.out.println(sys2);

        OpenTime openTime=new OpenTime();
        openTime.setStart_time(sys1.get(0).getKeyValue());
        openTime.setEnd_time(sys2.get(0).getKeyValue());
        return Result.success(openTime);
    }

    @Override
    public Result<ReClassLimit> getClassLimit( String token) {
        //查找KeyName="class_limit"的记录
        QueryWrapper<Sys> wp = new QueryWrapper<>();
        wp.eq("key_name","class_limit");
        List<Sys> sys = sysMapper.selectList(wp);
        System.out.println(sys);
        Integer classLimit= Integer.valueOf(sys.get(0).getKeyValue());
        ReClassLimit reClassLimit=new ReClassLimit();
        reClassLimit.setClass_limit(classLimit);
        return Result.success(reClassLimit);
    }

    @Override
    public Result<ReGroupNum> getGroupNum(String token) {
        //查找KeyName="group_num"的记录
        QueryWrapper<Sys> wp1 = new QueryWrapper<>();
        wp1.eq("key_name","group_num");
        List<Sys> sys1 = sysMapper.selectList(wp1);
        System.out.println(sys1);

        //查找KeyName="group_limit"的记录
        QueryWrapper<Sys> wp2 = new QueryWrapper<>();
        wp2.eq("key_name","group_limit");
        List<Sys> sys2 = sysMapper.selectList(wp2);
        System.out.println(sys2);

        ReGroupNum reGroupNum=new ReGroupNum();
        reGroupNum.setGroup_num(Long.parseLong(sys1.get(0).getKeyValue()));
        reGroupNum.setGroup_limit(Long.parseLong(sys2.get(0).getKeyValue()));
        return Result.success(reGroupNum);
    }
}
