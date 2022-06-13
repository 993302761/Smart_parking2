package com.example.provider.service;

import com.example.provider.entiry.Controller;
import com.example.provider.entiry.User;
import com.example.provider.service.base.ControllerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class ControllerServiceImpl implements ControllerService {

    @Autowired(required = false)
    private Controller controller;

    @Override
    public String add_User(String user_name, String password, String user_id) {
        return null;
    }

    @Override
    public String login_User(String user_name, String password) {
        return null;
    }

    @Override
    public User find_User(String user_name) {
        return null;
    }
}
