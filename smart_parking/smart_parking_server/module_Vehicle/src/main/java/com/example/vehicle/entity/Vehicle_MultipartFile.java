package com.example.vehicle.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data                   //生成get set equals toString等方法  来自lombok包
@NoArgsConstructor      //生成无参构造方法
public class Vehicle_MultipartFile {

    private String license_plate_number;        //车牌号
    private MultipartFile vehicle_photos;        //车辆照片
    private MultipartFile registration;         //机动车登记证照片
    private MultipartFile driving_permit;      //车辆行驶证照片
}
