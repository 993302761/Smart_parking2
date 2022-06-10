package com.example.provider.service;

import com.example.provider.entiry.User;
import com.example.provider.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcAccessor;

public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add_User(String user_name, String password, String user_id) {
        jdbcTemplate.update()
    }

    @Override
    public User find_User(String user_name) {
        return null;
    }
}
