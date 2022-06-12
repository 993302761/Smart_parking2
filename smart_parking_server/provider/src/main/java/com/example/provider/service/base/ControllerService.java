package com.example.provider.service.base;

import com.example.provider.entiry.User;

import java.util.List;

public interface ControllerService {

    /**
     * 增加一个超级管理员
     *
     * @return*/
    String add_User(String user_name, String password, String user_id);

    /**
     * 超级管理员登录
     * */
    String login_User(String user_name, String password);


    /**
     * 查找超级管理员
     * */
    User find_User(String user_name);



}
