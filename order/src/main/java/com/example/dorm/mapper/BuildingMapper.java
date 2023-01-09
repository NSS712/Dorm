package com.example.dorm.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dorm.entity.Buildings;
import com.example.dorm.entity.Users;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BuildingMapper extends BaseMapper<Buildings> {

}
