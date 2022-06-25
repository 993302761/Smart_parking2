package com.example.provider.service.base;

import com.example.provider.entiry.Administrators;

public interface ControllerService {


    /**
     * 超级管理员登录
     * */
    String login_Ctl(String ctr_id, String ctr_password);


    /**
     * 查找超级管理员
     * */
    Administrators find_Ctl(String ctr_id);



}
