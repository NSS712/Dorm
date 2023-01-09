package com.example.dorm.utils;

public class ListOrder {
    public Integer order_id;
    public String Group_name;
    public String building_name;
    public long Submit_time;
    public long result_content;
    public String status;

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public void setGroup_name(String group_name) {
        Group_name = group_name;
    }

    public void setSubmit_time(long submit_time) {
        Submit_time = submit_time;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public void setResult_content(long result_content) {
        this.result_content = result_content;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
