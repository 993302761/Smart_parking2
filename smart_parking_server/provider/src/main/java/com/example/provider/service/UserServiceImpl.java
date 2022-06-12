package com.example.provider.service;

import com.example.provider.entiry.User;
import com.example.provider.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.springframework.boot.Banner.Mode.LOG;

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @Override
    public String add_User(String user_name, String password, String user_id) {
        if (user_name.equals("")||password.equals("")||user_id.equals("")){
            return "所填信息不完整";
        }
        User user = find_User(user_name);
        if (user!=null){
            return "用户已注册";
        }
        int i= jdbcTemplate.update("insert into User values(?,?,?)",user_name,password,user_id);
        if (i<=0){
            return "注册失败";
        }
        else {
            return "注册成功";
        }
    }

    @Override
    public String login_User(String user_name, String password) {
        if (user_name.equals("")||password.equals("")){
            return "用户名或密码为空";
        }
        User user=find_User(user_name);
        if (user==null){
            return "用户未注册";
        }
        if (user.getPassword().equals(password)){
            return "登录成功";
        }else {
            return "密码错误";
        }
    }

    @Override
    public User find_User(String user_name) {
        try {
            User user=jdbcTemplate.queryForObject("select * from User where user_name= ?",new BeanPropertyRowMapper<>(User.class),user_name);
            return user;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Integer getAllUsersNumber() {
        return jdbcTemplate.queryForObject("select count(1) from User", Integer.class);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("select * from User",new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void deleteAllUsers() {
        jdbcTemplate.update("delete from User ");
    }
}
