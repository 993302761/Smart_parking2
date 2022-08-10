package com.feign.api.entity.parkingLots;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data                   //生成get set equals toString等方法
@NoArgsConstructor      //生成无参构造方法
@EqualsAndHashCode(callSuper = true)
public class Parking_for_user extends Parking {

    private int available_parking_spaces_num;     //可用车位数量

}
