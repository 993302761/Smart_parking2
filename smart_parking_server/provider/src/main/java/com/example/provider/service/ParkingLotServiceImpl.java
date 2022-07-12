package com.example.provider.service;

import com.example.provider.dao.ParkingLotDao;
import com.example.provider.entiry.Parking_lot_information;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ParkingLotServiceImpl  {

    @Resource
    private ParkingLotDao parkingLotDao;


    @Resource
    private RedisTemplate<String, String> redisTemplate;




    /**
     * TODO：新增一个停车场
     * @param pctr_id 停车场管理员账号
     * @param pctr_password 停车场管理员密码
     * @param parking_lot_name 停车场名
     * @param parking_in_the_city 停车场所在城市
     * @param parking_spaces_num 停车场总车位数
     * @param billing_rules 定价
     * @param longitude 经度
     * @param latitude 维度
     * @return 是否成功
     */
    public String add_Parking(String pctr_id,
                              String pctr_password,
                              String parking_lot_name,
                              String parking_in_the_city,
                              Integer parking_spaces_num,
                              float billing_rules,
                              String longitude,
                              String latitude) {
        if (pctr_id==null || pctr_password==null || parking_lot_name==null || parking_in_the_city==null || parking_spaces_num==null || longitude==null || latitude==null) {
            return "请输入完整信息";
        }
        Parking_lot_information parkingLotInformation = parkingLotDao.find_Parking(pctr_id);
        if (parkingLotInformation != null) {
            return "该用户已注册";
        }
        String parking_lot_number;
        do {
            parking_lot_number = String.valueOf((int)(Math.random() * 10000));
            parkingLotInformation = parkingLotDao.find_Parking_num(parking_lot_number);
        } while (parkingLotInformation != null);
        int update = parkingLotDao.add_Parking(pctr_id, pctr_password, parking_lot_name, parking_in_the_city, parking_lot_number, parking_spaces_num, billing_rules, longitude, latitude);
        if (update > 0) {
            return "注册成功";
        } else {
            return "注册失败";
        }
    }




    /**
     * TODO：停车场管理员登录
     * @param pctr_id 停车场管理员账号
     * @param pctr_password 停车场管理员密码
     * @return 是否成功
     */
    public String login_Parking(String pctr_id, String pctr_password) {
        if (pctr_id==null||pctr_password==null){
            return "用户名或密码为空";
        }
        Parking_lot_information parkingLotInformation=parkingLotDao.find_Parking(pctr_id);
        if (parkingLotInformation==null){
            return "用户未注册";
        }
        if (parkingLotInformation.getPctr_password().equals(pctr_password)){
            return "登录成功";
        }else {
            return "密码错误";
        }
    }




    /**
     * TODO：获取所有停车场列表
     * @return 所有停车场列表
     */
    public List<Parking_lot_information> getAllParking() {
        return parkingLotDao.getAllParking();
    }




    /**
     * TODO：车位情况变化
     * @param parking_lot_number 停车场编号
     * @param Available_place_num 当前可用停车位
     * @return 是否成功
     */
    public void change_parking_space(String parking_lot_number ,String Available_place_num){
        redisTemplate.opsForValue().set(parking_lot_number, Available_place_num);
    }

}
