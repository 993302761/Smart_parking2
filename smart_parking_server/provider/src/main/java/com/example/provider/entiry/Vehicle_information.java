package com.example.provider.entiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                   //生成get set equals toString等方法  来自lombok包
@AllArgsConstructor     //生成一个全属性构造方法
@NoArgsConstructor      //生成无参构造方法
public class Vehicle_information {

    private String user_name;                   //用户名
    private String user_id;;                    //身份证号
    private String license_plate_number;        //车牌号
    private String picture_index;               //车辆照片
    private String registration;                //机动车登记证照片
    private String vehicle_license;             //车辆行驶证照片

}
