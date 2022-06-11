package com.example.provider.service.base;

import com.example.provider.entiry.User;

import java.util.List;

public interface UserService {

    /**
     * 增加一个用户
     *
     * @return*/
    int add_User(String user_name, String password, String user_id);


    /**
     * 查找用户
     * */
    User find_User(String user_name);


    /**
     * 获取用户总量
     */
    Integer getAllUsersNumber();

    /**
     * 获取用户列表
     * @return
     */
    List<User> getAllUsers();


    /**
     * 删除所有用户
     */
    void deleteAllUsers();
}
