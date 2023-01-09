package com.example.dorm.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

public class Rooms {
  //  @TableId(value = "id",type = IdType.AUTO)
  private long id;
  @TableField("building_id")
  private long buildingId;
  private String name;
  private long gender;
  @TableField("order_num")
  private long orderNum;
  @TableField("is_valid")
  private long isValid;
  private String remarks;
  private String description;
  @TableField("image_url")
  private String imageUrl;
  @TableField("is_del")
  private long isDel;
  private long status;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getBuilding_id() {
    return buildingId;
  }

  public void setBuilding_id(long building_id) {
    this.buildingId = buildingId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getGender() {
    return gender;
  }

  public void setGender(long gender) {
    this.gender = gender;
  }

  public long getOrder_num() {
    return orderNum;
  }

  public void setOrder_num(long order_num) {
    this.orderNum = orderNum;
  }

  public long getIs_valid() {
    return isValid;
  }

  public void setIs_valid(long is_valid) {
    this.isValid = isValid;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }

  public String getImage_url() {
    return imageUrl;
  }

  public void setImage_url(String image_url) {
    this.imageUrl = imageUrl;
  }

  public long getIs_del() {
    return isDel;
  }

  public void setIs_del(long is_del) {
    this.isDel = isDel;
  }
}
