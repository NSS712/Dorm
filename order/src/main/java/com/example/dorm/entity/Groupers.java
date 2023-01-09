package com.example.dorm.entity;


public class Groupers {

  private long id;
  private long uid;
  private long isCreator;
  private long groupId;
  private long isDel;
  private long joinTime;
  private long leaveTime;
  private long status;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }


  public long getIsCreator() {
    return isCreator;
  }

  public void setIsCreator(long isCreator) {
    this.isCreator = isCreator;
  }


  public long getGroupId() {
    return groupId;
  }

  public void setGroupId(long groupId) {
    this.groupId = groupId;
  }


  public long getIsDel() {
    return isDel;
  }

  public void setIsDel(long isDel) {
    this.isDel = isDel;
  }


  public long getJoinTime() {
    return joinTime;
  }

  public void setJoinTime(long joinTime) {
    this.joinTime = joinTime;
  }


  public long getLeaveTime() {
    return leaveTime;
  }

  public void setLeaveTime(long leaveTime) {
    this.leaveTime = leaveTime;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }

}
