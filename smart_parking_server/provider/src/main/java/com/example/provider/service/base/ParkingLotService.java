package com.example.provider.service.base;

import com.example.provider.entiry.User;

import java.util.List;

public interface ParkingLotService {

    /**
     * 增加一个停车场管理员
     *
     * @return*/
    String add_Parking(String user_name, String password, String user_id);

    /**
     * 停车场管理员登录
     * */
    String login_Parking(String user_name, String password);


    /**
     * 查找停车场管理员
     * */
    User find_Parking(String user_name);


    /**
     * 获取停车场管理员总量
     */
    Integer getAllParkingNumber();

    /**
     * 获取停车场管理员列表
     * @return
     */
    List<User> getAllParking();


    /**
     * 删除所有停车场管理员
     */
    void deleteAllParking();

}
