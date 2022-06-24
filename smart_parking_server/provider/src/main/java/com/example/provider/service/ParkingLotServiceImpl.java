package com.example.provider.service;

import com.example.provider.entiry.Parking_lot_information;
import com.example.provider.entiry.User;
import com.example.provider.service.base.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;


    @Override
    public String add_Parking(String pctr_id, String pctr_password, String parking_lot_name, String parking_lot_number, Integer parking_spaces_num, float billing_rules, String longitude, String latitude) {
        return null;
    }

    @Override
    public String login_Parking(String pctr_id, String pctr_password) {
        if (pctr_id.equals("")||pctr_password.equals("")){
            return "用户名或密码为空";
        }
        Parking_lot_information parkingLotInformation=find_Parking(pctr_id);
        if (parkingLotInformation==null){
            return "用户未注册";
        }
        if (parkingLotInformation.getPctr_password().equals(pctr_password)){
            return "登录成功";
        }else {
            return "密码错误";
        }
    }

    @Override
    public Parking_lot_information find_Parking(String pctr_id) {
        try {
            Parking_lot_information parkingLotInformation=jdbcTemplate.queryForObject("select * from Parking_lot_information where pctr_id= ?",new BeanPropertyRowMapper<>(Parking_lot_information.class),pctr_id);
            return parkingLotInformation;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Integer getAllParkingNumber() {
        return jdbcTemplate.queryForObject("select count(1) from Parking_lot_information", Integer.class);
    }

    @Override
    public List<Parking_lot_information> getAllParking() {
        return jdbcTemplate.query("select * from Parking_lot_information",new BeanPropertyRowMapper<>(Parking_lot_information.class));
    }

    @Override
    public void deleteAllParking() {
        jdbcTemplate.update("delete from Parking_lot_information ");
    }
}
