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

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add_User(String user_name, String password, String user_id) {
        User user = find_User(user_name);
        if (user!=null){
            return 0;
        }
        return jdbcTemplate.update("insert into User values(?,?,?)",user_name,password,user_id);
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
