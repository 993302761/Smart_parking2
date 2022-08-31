package com.example.vehicle.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data                   //生成get set equals toString等方法  来自lombok包
@NoArgsConstructor      //生成无参构造方法
public class Vehicle {

    private String license_plate_number;          //车牌号
    private String vehicle_photos;                 //车辆照片
    private String registration;                  //机动车登记证照片
    private String driving_permit;               //车辆行驶证照片

}
