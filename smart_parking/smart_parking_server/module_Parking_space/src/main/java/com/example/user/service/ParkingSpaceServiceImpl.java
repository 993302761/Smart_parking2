package com.example.user.service;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingSpaceServiceImpl {


    @Resource
    private RedisTemplate<String, String> redisTemplate;




    /**
     * TODO：车位情况变化
     * @param parking_lot_number 停车场编号
     * @param Available_place_num 当前可用停车位
     */
    public void change_parking_space(String parking_lot_number ,String Available_place_num){
        Parking_lot_information parking_num = parkingLotDao.find_Parking_num(parking_lot_number);
        if (parking_num.getParking_spaces_num()<Integer.parseInt(Available_place_num)){
            System.out.println("数据错误");
            return;
        }
        redisTemplate.opsForValue().set(parking_lot_number, Available_place_num);
    }


    /**
     * TODO：获取某一区域停车场情况
     * @param city 所在城市
     * @return 某一区域停车场情况
     */
    public List<Parking> get_parking_lot(String city){
        List<Parking> parking_lot = parkingLotDao.get_parking_lot(city);
        if (parking_lot.isEmpty()){
            return null;
        }
        List<Parking> new_parking_lot=new ArrayList<>();
        for (int i = 0; i < parking_lot.size(); i++) {
            Parking p=parking_lot.get(i);
            boolean hasKey = redisTemplate.hasKey(p.getParking_lot_number());
            if(hasKey ){
                String s = redisTemplate.opsForValue().get(parking_lot.get(i).getParking_lot_number());
                p.setAvailable_parking_spaces_num(Integer.parseInt(s));
            }
            new_parking_lot.add(p);
        }
        return new_parking_lot;
    }





}
