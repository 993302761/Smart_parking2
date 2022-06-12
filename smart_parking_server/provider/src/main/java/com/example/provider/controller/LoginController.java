package com.example.provider.controller;

import com.example.provider.service.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录模块")
@RequestMapping("/Login")
public class LoginController {

    @Autowired
    private UserServiceImpl userService;


    @ApiOperation(value = "停车场管理员登录", notes = "输入停车场管理员账号和密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pctr_password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pctr_id", value = "停车场管理员账号", required = true, dataType = "String")
    })
    @GetMapping(value = "/{parking_login}", produces = "text/plain;charset=utf-8")
    public boolean parking_login(String pctr_id,String pctr_password){
        return false;
    }



    @ApiOperation(value = "超级管理员登录", notes = "输入停车场管理员账号和密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pctr_password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pctr_id", value = "超级管理员登录", required = true, dataType = "String")
    })
    @GetMapping(value = "/controller_login", produces = "text/plain;charset=utf-8")
    public boolean controller_login(String ctr_id,String ctr_password){
        return false;
    }


    @ApiOperation(value = "app用户登录", notes = "输入账号和密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @GetMapping(value = "/app_login")
    public String app_login(String user_name,String password){
        System.out.println(user_name+"  "+password);
        return userService.login_User(user_name,password);
    }
}
