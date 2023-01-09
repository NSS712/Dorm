package com.example.dorm.utils;

//返回类
public class ReUserInfo {
    /**
     * 班级
     */
    private String class_name;
    private String email;
    /**
     * 性别（0女，1男）
     */
    private long gender;
    private String last_login_time;
    /**
     * 姓名
     */
    private String name;
    /**
     * 学号
     */
    private String studentid;
    private String tel;
    /**
     * 用户ID
     */
    private long uid;
    /**
     * 校验码
     */
    private String verification_code;

    public String getclass_name() { return class_name; }
    public void setclass_name(String value) { this.class_name = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public long getGender() { return gender; }
    public void setGender(long value) { this.gender = value; }

    public String getlast_login_time() { return last_login_time; }
    public void setlast_login_time(String value) { this.last_login_time = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getStudentid() { return studentid; }
    public void setStudentid(String value) { this.studentid = value; }

    public String getTel() { return tel; }
    public void setTel(String value) { this.tel = value; }

    public long getUid() { return uid; }
    public void setUid(long value) { this.uid = value; }

    public String getverification_code() { return verification_code; }
    public void setverification_code(String value) { this.verification_code = value; }
}
