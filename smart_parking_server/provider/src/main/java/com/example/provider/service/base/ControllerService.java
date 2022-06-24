package com.example.provider.service.base;

import com.example.provider.entiry.Controller;
import com.example.provider.entiry.User;

import java.util.List;

public interface ControllerService {


    /**
     * 超级管理员登录
     * */
    String login_Ctl(String ctr_id, String ctr_password);


    /**
     * 查找超级管理员
     * */
    Controller find_Ctl(String ctr_id);



}
