package com.example.provider.service;

import com.example.provider.entiry.Administrators;
import com.example.provider.service.base.ControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdministratorsServiceImpl implements ControllerService {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;


    @Override
    public String login_Ctl(String ctr_id, String ctr_password) {
        if (ctr_id.equals("")||ctr_password.equals("")){
            return "用户名或密码为空";
        }
        Administrators controller=find_Ctl(ctr_id);
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
    public Administrators find_Ctl(String ctr_id) {
        try {
            Administrators controller=jdbcTemplate.queryForObject("select * from Administrators where ctr_id= ?",new BeanPropertyRowMapper<>(Administrators.class),ctr_id);
            return controller;
        }catch (Exception e){
            return null;
        }    }


}
