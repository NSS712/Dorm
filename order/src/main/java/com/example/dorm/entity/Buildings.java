package com.example.dorm.entity;


import com.baomidou.mybatisplus.annotation.TableField;

public class Buildings {

  private long id;
  private String name;

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


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public long getOrderNum() {
    return orderNum;
  }

  public void setOrderNum(long orderNum) {
    this.orderNum = orderNum;
  }


  public long getIsValid() {
    return isValid;
  }

  public void setIsValid(long isValid) {
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


  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }


  public long getIsDel() {
    return isDel;
  }

  public void setIsDel(long isDel) {
    this.isDel = isDel;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }

}
