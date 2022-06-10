package com.example.provider.service.base;

import com.example.provider.entiry.User;

public interface UserService {

    /**
     * 增加一个用户
     * */
    void add_User(String user_name,String password,String user_id);


    /**
     * 查找用户
     * */
    User find_User(String user_name);


}
