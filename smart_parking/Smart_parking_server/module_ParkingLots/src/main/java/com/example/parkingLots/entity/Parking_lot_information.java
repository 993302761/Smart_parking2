package com.example.parkingLots.entity;

import com.feign.api.entity.parkingLots.Parking;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data                   //生成get set equals toString等方法
@NoArgsConstructor      //生成无参构造方法
@EqualsAndHashCode(callSuper = true)
public class Parking_lot_information extends Parking {

    private String pctr_id;        //停车场管理员账号
    private String pctr_password;      //停车场管理员密码


}
