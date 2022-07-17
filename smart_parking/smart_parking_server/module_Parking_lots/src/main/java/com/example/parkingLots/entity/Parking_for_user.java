package com.example.parkingLots.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data                   //生成get set equals toString等方法
@NoArgsConstructor      //生成无参构造方法
public class Parking_for_user extends Parking{

    private int available_parking_spaces_num;     //可用车位数量

}
