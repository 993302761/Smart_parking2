package com.example.provider.service;

import com.example.provider.dao.UserDao;
import com.example.provider.entiry.User;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl  {

    @Resource
    private UserDao userDao;


    /**
     * 添加一名用户
     * */
    public String add_User(String user_name, String password, String user_id) {
        if (user_name==null||password==null||user_id==null){
            return "所填信息不完整";
        }
        User user = userDao.find_User(user_name);
        if (user!=null){
            return "用户已注册";
        }
        int i= userDao.add_User(user_name,password,user_id);
        if (i<=0){
            return "注册失败";
        }
        else {
            return "注册成功";
        }
    }


    /**
     * 用户登录
     * */
    public String login_User(String user_name, String password) {
        if (user_name==null||password==null){
            return "用户名或密码为空";
        }
        User user=userDao.find_User(user_name);
        if (user==null){
            return "用户未注册";
        }
        if (user.getPassword().equals(password)){
            return "登录成功";
        }else {
            return "密码错误";
        }
    }


    /**
     * 查找用户
     * */
    public boolean find(String user_name){
        User user=userDao.find_User(user_name);
        if (user==null){
            return true;
        }
        else return false;
    }



    /**
     * 查找用户
     * */
    public String User_Register(String user_name,String password,String user_id,String license_plate_number,String picture_index,String registration,String vehicle_license){
        if (user_name==null||password==null||user_id==null||license_plate_number==null||picture_index==null||registration==null||vehicle_license==null){
            return "所填信息不完整";
        }
        return "注册失败";
    }


    public Integer getAllUsersNumber() {
        return userDao.getAllUsersNumber();
    }


    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

}
