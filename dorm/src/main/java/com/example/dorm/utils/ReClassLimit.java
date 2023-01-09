package com.example.dorm.utils;

public class ReClassLimit {
    /**
     * 1为限制以班级为单位组队及分配宿舍，0为不限制
     */
    private long class_limit;


    public long getClass_limit() {
        return class_limit;
    }

    public void setClass_limit(long class_limit) {
        this.class_limit = class_limit;
    }
}