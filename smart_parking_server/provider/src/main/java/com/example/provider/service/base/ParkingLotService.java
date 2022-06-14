package com.example.provider.service.base;

import com.example.provider.entiry.Parking_lot_information;
import com.example.provider.entiry.User;

import java.util.List;

public interface ParkingLotService {

    /**
     * 增加一个停车场管理员
     *
     * @return*/
    String add_Parking(String pctr_id, String pctr_password, String parking_lot_name,String parking_lot_number,Integer parking_spaces_num,float billing_rules,String longitude,String latitude);

    /**
     * 停车场管理员登录
     * */
    String login_Parking(String pctr_id, String pctr_password);


    /**
     * 查找停车场管理员
     * */
    Parking_lot_information find_Parking(String pctr_id);


    /**
     * 获取停车场管理员总量
     */
    Integer getAllParkingNumber();

    /**
     * 获取停车场管理员列表
     * @return
     */
    List<Parking_lot_information> getAllParking();


    /**
     * 删除所有停车场管理员
     */
    void deleteAllParking();

}
