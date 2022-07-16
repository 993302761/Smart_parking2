package com.example.provider.service;

import com.example.provider.dao.AdministratorsDao;
import com.example.provider.entity.Administrators;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdministratorsServiceImpl  {

    @Resource
    private AdministratorsDao administratorsDao;



    /**
     * TODO：超级管理员登录
     * @param ctr_id 超级管理员账号
     * @param ctr_password 超级管理员密码
     * @return 是否成功
     */
    public String login_Ctl(String ctr_id, String ctr_password) {
        if (ctr_id==null||ctr_password==null){
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
