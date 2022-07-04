package com.example.provider.service;

import com.example.provider.dao.UserDao;
import com.example.provider.entiry.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl  {

    @Resource
    private UserDao userDao;


    @Resource
    private RedisTemplate<String, Integer> redisTemplate;


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
     * UUID校验
     * */
    public boolean UUID_check(String UUID,String user_name){
        redisTemplate.opsForValue().set("123",5);
        System.out.println(redisTemplate.opsForValue().get("123"));
        return false;
    }



    /**
     * 获取用户总量
     * */
    public Integer getAllUsersNumber() {
        return userDao.getAllUsersNumber();
    }


    /**
     * 获取所有用户列表
     * */
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

}
