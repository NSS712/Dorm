package com.example.dorm.utils;


public class ReGroupNum {
    /**
     * 1为不允许组队，0为允许组队
     */
    private long group_limit;
    /**
     * 通常为2-4
     */
    private long group_num;

    public long getGroup_limit() {
        return group_limit;
    }

    public void setGroup_limit(long group_limit) {
        this.group_limit = group_limit;
    }

    public long getGroup_num() {
        return group_num;
    }

    public void setGroup_num(long group_num) {
        this.group_num = group_num;
    }
}
