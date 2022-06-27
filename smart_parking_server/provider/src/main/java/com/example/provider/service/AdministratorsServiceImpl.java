package com.example.provider.service;

import com.example.provider.dao.AdministratorsDao;
import com.example.provider.entiry.Administrators;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdministratorsServiceImpl  {

    @Resource
    private AdministratorsDao administratorsDao;


    public String login_Ctl(String ctr_id, String ctr_password) {
        if (ctr_id.equals("")||ctr_password.equals("")){
            return "用户名或密码为空";
        }
        Administrators controller=administratorsDao.find_Adm(ctr_id);
        if (controller==null){
            return "用户未注册";
        }
        if (controller.getCtr_password().equals(ctr_password)){
            return "登录成功";
        }else {
            return "密码错误";
        }
    }


}
