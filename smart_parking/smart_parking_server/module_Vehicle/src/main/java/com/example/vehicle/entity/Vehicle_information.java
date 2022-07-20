package com.example.vehicle.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data                   //生成get set equals toString等方法  来自lombok包
@NoArgsConstructor      //生成无参构造方法
public class Vehicle_information {

    private String user_name;                   //用户名
    private String user_id;                    //身份证号
    private String license_plate_number;        //车牌号
    private img picture_index;               //车辆照片
    private img registration;                //机动车登记证照片
    private img vehicle_license;             //车辆行驶证照片

}
