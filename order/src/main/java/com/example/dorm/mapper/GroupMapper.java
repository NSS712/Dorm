package com.example.dorm.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dorm.entity.Groupers;
import com.example.dorm.entity.Groups;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMapper extends BaseMapper<Groups> {
    @Select("select * from groupers where group_id=#{groupId} and is_del=0 and is_creator=1")
    // 根据groupid查找队长
    Groupers getCreator(@Param("groupId") long groupId);
    // 根据uid获取groupid
    @Select("select * from groupers where uid=#{userId} and is_del=0")
    Groupers getGrouperByUid(long userId);

    // 根据groupId查询队伍信息
    @Select("select * from `groups` where id=#{groupId} and is_del=0;")
    Groups getGroupById(@Param("groupId") long groupId);

    // 获取grouper数据
    @Select("select * from groupers where uid=#{userId} and is_del=0;")
    Groupers getGrouperById(long userId);

    // 根据队伍邀请码获取队伍id
    @Select("select * from groups where invite_code=#{code} and is_del=0")
    Groups getGroupByCode(String code);

    // 获取所有队伍成员
    @Select("select * from groupers where group_id=#{groupId} and is_del=0")
    List<Groupers> getAllMembers(long groupId);

    // 添加一个队伍，返回队伍id
    @Update("insert into `groups`(name,invite_code,description) VALUES (#{name},#{code},#{description})")
    void AddOneGroup(@Param("name") String name, @Param("code") String code, @Param("description") String description );

    // 获取最近插入的队伍id
    @Select("SELECT LAST_INSERT_ID()")
    long getLastInsertGroupId();

    // 插入一条队员数据
    @Update("insert into groupers(uid,is_creator,group_id)values (#{userId},#{isCreator},#{groupId})")
    void AddOneMember(@Param("userId") long userId,@Param("isCreator") int isCreator,@Param("groupId")long groupId);

    // 删除队伍
    @Update("update `groups` set is_del=1 where id=#{groupId}")
    void deleteGroup(long groupId);

    // 退出队伍
    @Update("update groupers set is_del=1 where group_id=#{groupId} and uid=#{userId} and is_del=0")
    void quitGroup(@Param("userId") long userId,@Param("groupId") long groupId);

    // 更新is_creator字段
    @Update("update groupers set is_creator=#{isCreator} where uid=#{userId} and group_id=#{groupId} and is_del=0")
    void updateCreator(@Param("userId") long userId,@Param("groupId") long groupId,@Param("isCreator") int isCreator);

    Groupers selectOne(QueryWrapper<Groupers> wp1);
}
