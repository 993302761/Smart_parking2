package com.example.vehicle.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data                   //生成get set equals toString等方法  来自lombok包
@NoArgsConstructor      //生成无参构造方法
@EqualsAndHashCode(callSuper = true)
public class Vehicle_information extends Vehicle {

    private String user_name;                   //用户名
    private String user_id;                    //身份证号


}
