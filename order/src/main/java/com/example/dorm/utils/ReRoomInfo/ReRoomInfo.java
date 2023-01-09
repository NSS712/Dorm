package com.example.dorm.utils.ReRoomInfo;


public class ReRoomInfo {
    /**
     *  宿舍成员
     */
    private Member member;
    /**
     * 房间号
     */
    private String roomName;
    private String roomId;

    public Member getMember() { return member; }
    public void setMember(Member value) { this.member = value; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String value) { this.roomName = value; }

    public void setRoomId(Long r){this.roomId=r.toString();}
}

