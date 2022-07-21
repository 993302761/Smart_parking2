package com.example.vehicle.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data                   //生成get set equals toString等方法  来自lombok包
@NoArgsConstructor      //生成无参构造方法
public class Vehicle_Blob {

    private String license_plate_number;        //车牌号

    private Blob vehicle_photos;                 //车辆照片
    private String vehicle_photos_suffix;        //车辆照片后缀名
    private Blob registration;                  //机动车登记证照片
    private String registration_suffix;         //机动车登记证照片后缀名
    private Blob driving_permit;               //车辆行驶证照片
    private String driving_permit_suffix;       //车辆行驶证照片后缀名

}
