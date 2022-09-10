package com.feign.api.entity.user;

import java.util.List;



public class User {

    private String user_name;   //用户名
    private String user_id;    //身份证号
    private List<String> Vehicle;    //所绑定的车辆信息


    public User() {
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<String> getVehicle() {
        return Vehicle;
    }

    public void setVehicle(List<String> vehicle) {
        Vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_name='" + user_name + '\'' +
                ", user_id='" + user_id + '\'' +
                ", Vehicle=" + Vehicle +
                '}';
    }
}
