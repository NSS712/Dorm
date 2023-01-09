package com.example.dorm.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dorm.entity.Groupers;
import com.example.dorm.entity.Groups;
import com.example.dorm.entity.StudentInfo;
import com.example.dorm.entity.Users;
import com.example.dorm.mapper.GroupMapper;
import com.example.dorm.mapper.GroupersMapper;
import com.example.dorm.mapper.StudentInfoMapper;
import com.example.dorm.mapper.UsersMapper;
import com.example.dorm.service.GroupService;
import com.example.dorm.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private GroupersMapper groupersMapper;

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Autowired
    public UsersMapper usersMapper;
    @Override
    public Result createGroup(String token,String name,String describe) {
        // 获取uid？
        //String token=request.getHeader("Authorization");
        String temp= (String) redisTemplate.opsForValue().get("token:"+token);
        long userId=Integer.parseInt(temp);

        // 登录接口有bug，手动添加测试数据
        //long userId1=8;

        if(name==null || describe==null || name.length()==0 || describe.length()==0 || name.isEmpty() || describe.isEmpty()){
            return Result.error(514020,"参数不能为空！");
        }


        // 查询用户本身是否已加入队伍，若是，则返回错误
        Groupers groupers=groupMapper.getGrouperByUid(userId); //userId1测试完记得改成userId
        if(groupers!=null){
            return Result.error(514001,"用户已加入队伍！");
        }

        // 随机生成队伍码
        String code=String.valueOf((int)((Math.random()*9+1)*100000));
        // 查队伍码是否重复
        QueryWrapper<Groups> wp = new QueryWrapper<>();
        wp.eq("invite_code",code);
        while(groupMapper.selectOne(wp)!=null){
            code=String.valueOf((int)((Math.random()*9+1)*100000));
        }



        // 创建队伍，在group中新增一条记录
        Groups group=new Groups();
        group.setName(name);
        group.setInviteCode(code);
        group.setDescription(describe);
        groupMapper.insert(group);
        long testId=group.getId();

        groupMapper.AddOneGroup(name,code,describe);

        long groupId=groupMapper.getLastInsertGroupId();

        // 在grouper中新增一条记录
        groupMapper.AddOneMember(userId,1,groupId); //userId1测试完记得改成userId

        // 返回team_id和invite_code
        Map<String,Object> groupMap=new LinkedHashMap<>();
        groupMap.put("team_id",groupId);
        groupMap.put("invite_code",code);

        return Result.success(groupMap);

    }

    @Override
    public Result deleteGroup(String token) {
        //  获取uid
        String temp= (String) redisTemplate.opsForValue().get("token:"+token);
        long userId=Integer.parseInt(temp);
        // 登录接口有bug，手动添加测试数据
        //long userId1=2;

        // 先判断是否为该队伍成员，再判断用户是否为creator，只有creator才有删除队伍的权限
        Groupers groupers=groupMapper.getGrouperByUid(userId);
        if(groupers==null){
            return Result.error(514015,"您未加入队伍！");
        }
        long groupId=groupers.getGroupId();
//        if(groupId!=teamId){
//            return Result.error(514015,"您不属于该队伍！");
//        }

        if(groupers.getIsCreator()!=1){
            return Result.error(514010,"您无删除队伍权限！");
        }


        // 看队伍中是否还有其他人，只有所有人都退出了队伍，才能删除
        List<Groupers> groupersList=groupMapper.getAllMembers(groupId);
        if(groupersList.size()!=1){
            return Result.error(514006,"队伍中还有其他队员，无法删除队伍！");
        }

        // 退出并删除
        groupMapper.quitGroup(userId,groupId);
        groupMapper.deleteGroup(groupId);

        return Result.success();

    }

    @Override
    public Result joinGroup(String token,String inviteCode) {
        //  获取uid
        String temp= (String) redisTemplate.opsForValue().get("token:"+token);
        long userId=Integer.parseInt(temp);

        // 登录接口有bug，手动添加测试数据
        //long userId1=7;

        // 判断用户是否已加入队伍，若是，返回错误
        Groupers groupers=groupMapper.getGrouperByUid(userId); //userId1测试完记得改成userId
        if(groupers!=null){
            return Result.error(514001,"用户已加入队伍！");
        }

        // 根据邀请码查询队伍id
        Groups groups=groupMapper.getGroupByCode(inviteCode);
        if(groups==null){
            return Result.error(514002,"校验码错误！");
        }
        long groupId=groups.getId();

        Groupers groupers1=groupMapper.getCreator(groupId);
        long teamUserId=groupers1.getUid();





        // 获取队伍性别与用户性别
//        QueryWrapper<Groupers> wp1 = new QueryWrapper<>();
//        wp1.eq("group_id",groupId);
//        Groupers groupers1=groupersMapper.selectOne(wp1);
//        long teamUserId=groupers1.getUid();
        QueryWrapper<Users> wp2 = new QueryWrapper<>();
        wp2.eq("uid",teamUserId);

        QueryWrapper<Users> wp3 = new QueryWrapper<>();
        wp3.eq("uid",userId);
        Users teamUser= usersMapper.selectOne(wp2);
        Users myUser=usersMapper.selectOne(wp3);
        long teamGender=teamUser.getGender();
        long myGender=myUser.getGender();
        if(teamGender!=myGender){
            return Result.error(514017,"您的性别与该队伍不符");
        }





        // 并判断队伍人数是否已满，若满，返回错误
        List<Groupers> groupersList=groupMapper.getAllMembers(groupId);
        if(groupersList.size()==4){
            return Result.error(514003,"队伍已满！");
        }


        // 根据队伍id和uid在grouper中插入一条数据
        groupMapper.AddOneMember(userId,0,groupId);

        // 返回team_id
        Map<String,Object> groupMap=new HashMap<>();
        groupMap.put("team_id",groupId);
        return Result.success(groupMap);
    }

    @Override
    public Result quitGroup(String token) {
        // 获取uid
        String temp= (String) redisTemplate.opsForValue().get("token:"+token);
        long userId=Integer.parseInt(temp);

        // 登录接口有bug，手动添加测试数据
        //long userId1=3;

        //获取groupId（新）

        // 查询用户是否属于该队伍,不是则无法退出；查询用户是否为队长，若是，需要先转让再退出，若否则直接退出
        Groupers groupers=groupMapper.getGrouperByUid(userId);
        if(groupers==null){
            return Result.error(514016,"您还未加入任何队伍！");
        }
        long groupId=groupers.getGroupId();
//        if(groupId!=teamId){
//            return Result.error(514015,"您不属于该队伍！");
//        } else if (groupers.getIsCreator()==1) {
//            return Result.error(514011,"您需先转让队长权限再退出队伍！");
//        }

        if(groupers.getIsCreator()==1){
            return Result.error(514011,"您需先转让队长权限再退出队伍！");
        }


        // 退出队伍
        groupMapper.quitGroup(userId,groupId);

        return Result.success();


    }

    @Override
    public Result getMyGroup(String token) {
        // 获取uid
        String temp= (String) redisTemplate.opsForValue().get("token:"+token);
        long userId=Integer.parseInt(temp);
        // 登录接口有bug，手动添加测试数据
        //long userId1=3;

        // 先查询有无所属队伍
        Groupers groupers=groupMapper.getGrouperByUid(userId);
        if(groupers==null){
            return Result.error(514001,"用户未加入队伍！");
        }

        // 获取队伍id
        long groupId=groupers.getGroupId();
        // 根据groupId从group表中获取id,name,code
        Groups groups=groupMapper.getGroupById(groupId);

        // 从grouper表中获取队员uid
        List<Groupers> groupersList=groupMapper.getAllMembers(groupId);

        // 根据uid去student表中查找学生id和姓名
        // StudentInfo studentInfo=studentInfoMapper.getStudentInfoByUid();

        List<Map> members=new ArrayList<>();

        Iterator<Groupers> iterator=groupersList.iterator();
        while(iterator.hasNext()){
            Groupers tmpGrouper=iterator.next();
            long tmpUserId=tmpGrouper.getUid();

            QueryWrapper<StudentInfo> wp = new QueryWrapper<>();
            wp.eq("uid",tmpUserId);
            StudentInfo tmpStudentInfo=studentInfoMapper.selectOne(wp);

            QueryWrapper<Users> wp1 = new QueryWrapper<>();
            wp1.eq("uid",tmpUserId);

            Users user=usersMapper.selectOne(wp1);

            Map<String,Object> memberInfo=new LinkedHashMap<>();

            memberInfo.put("student_id",tmpStudentInfo.getStudentid());
            memberInfo.put("student_name",user.getName());

            members.add(memberInfo);

        }


        // 打包信息返回json
        Map<String,Object> groupMap=new LinkedHashMap<>();
        groupMap.put("group_id",groupId);
        groupMap.put("group_name",groups.getName());
        groupMap.put("invite_code",groups.getInviteCode());
        groupMap.put("members",members);


        return Result.success(groupMap);
    }

    @Override
    public Result transferGroupCreator(String token,String studentId) {
        //
        if(studentId.length()==0 || studentId==null || studentId.isEmpty()){
            return Result.error(514020,"参数不能为空！");
        }
        long student=Long.parseLong(studentId);

        // 获取uid
        String temp= (String) redisTemplate.opsForValue().get("token:"+token);
        long userId=Integer.parseInt(temp);

        // 登录接口有bug，手动添加测试数据
       // long userId1=3;

        // 根据studentId查表得uid
        QueryWrapper<StudentInfo> wp = new QueryWrapper<>();
        wp.eq("studentid",student);
        StudentInfo studentInfo=studentInfoMapper.selectOne(wp);

        if(studentInfo==null){
            return Result.error(514013,"学号错误！");
        }
        long userId2=studentInfo.getUid();



        // 查grouper表看该uid是否属于同一队伍，否则返回错误
        Groupers groupers1=groupMapper.getGrouperByUid(userId);

        if(groupers1==null){
            return Result.error(514001,"用户未加入队伍！");
        } else if (groupers1.getIsCreator()!=1) {
            return Result.error(514010,"您无队长权限！无法转让！");
        }

        Groupers groupers2=groupMapper.getGrouperByUid(userId2);

        if(groupers2==null){
            return Result.error(514012,"您与该用户不属于同一队伍，无法转让！");
        } else if (groupers1.getGroupId()!=groupers2.getGroupId()) {
            return Result.error(514012,"您与该用户不属于同一队伍，无法转让！");
        }


        // 更新grouper表中两条记录is_creator的值
        groupMapper.updateCreator(userId,groupers1.getGroupId(),0);
        groupMapper.updateCreator(userId2,groupers2.getGroupId(),1);


        return Result.success();
    }
}
