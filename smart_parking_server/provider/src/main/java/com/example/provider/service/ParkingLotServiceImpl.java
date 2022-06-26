package com.example.provider.service;

import com.example.provider.dao.ParkingLotDao;
import com.example.provider.entiry.Parking_lot_information;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class ParkingLotServiceImpl  {

    @Resource
    private ParkingLotDao parkingLotDao;


    public String add_Parking(String pctr_id,
                              String pctr_password,
                              String parking_lot_name,
                              String parking_in_the_city,
                              Integer parking_spaces_num,
                              float billing_rules,
                              String longitude,
                              String latitude) {
        if (pctr_id.equals("")||pctr_password.equals("")||parking_lot_name.equals("")||parking_in_the_city.equals("")||parking_spaces_num.equals("")||longitude.equals("")||latitude.equals("")){
            return "请输入完整信息";
        }
        Parking_lot_information parkingLotInformation=parkingLotDao.find_Parking(pctr_id);
        if (parkingLotInformation!=null){
            return "该用户已注册";
        }
        int update = parkingLotDao.add_Parking(pctr_id,pctr_password,parking_lot_name,parking_in_the_city,parking_spaces_num,billing_rules,longitude,latitude);
        if (update>0)
        {
            return "注册成功";
        }else {
            return "注册失败";
        }
    }


    public String login_Parking(String pctr_id, String pctr_password) {
        if (pctr_id.equals("")||pctr_password.equals("")){
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



    public List<Parking_lot_information> getAllParking() {
        return parkingLotDao.getAllParking();
    }


}
