package com.example.parkingLots.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data                   //生成get set equals toString等方法
@NoArgsConstructor      //生成无参构造方法
public class Parking_lot_information extends Parking{

    private String pctr_id;        //停车场管理员账号
    private String pctr_password;      //停车场管理员密码


}
