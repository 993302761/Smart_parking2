package com.example.provider.service;

import com.example.provider.entiry.Controller;
import com.example.provider.entiry.Parking_lot_information;
import com.example.provider.entiry.User;
import com.example.provider.service.base.ControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ControllerServiceImpl implements ControllerService {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;


    @Override
    public String login_Ctl(String ctr_id, String ctr_password) {
        if (ctr_id.equals("")||ctr_password.equals("")){
            return "用户名或密码为空";
        }
        Controller controller=find_Ctl(ctr_id);
        if (controller==null){
            return "用户未注册";
        }
        if (controller.getCtr_password().equals(ctr_password)){
            return "登录成功";
        }else {
            return "密码错误";
        }
    }

    @Override
    public Controller find_Ctl(String ctr_id) {
        try {
            Controller controller=jdbcTemplate.queryForObject("select * from Controller where ctr_id= ?",new BeanPropertyRowMapper<>(Controller.class),ctr_id);
            return controller;
        }catch (Exception e){
            return null;
        }    }


}
