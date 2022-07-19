package com.example.user.controller;

import com.example.user.entity.User;
import com.example.user.service.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "用户模块")
@RequestMapping("/User")
public class UserController {



    @Autowired(required = false)
    private UserServiceImpl userService;


    @ApiOperation(value = "app用户登录", notes = "输入账号和密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")
    })
    @GetMapping(value = "/app_login", produces = "text/plain;charset=utf-8")
    public String app_login( String user_name, String password,String UUID){
        return userService.login_User(user_name,password,UUID);
    }



    @ApiOperation(value = "app用户注册1")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @GetMapping(value = "/app_register1", produces = "text/plain;charset=utf-8")
    public String app_register1(String user_name,String password){
        boolean i= userService.find(user_name);
        if (i==true){
            return "ok";
        }else {
            return "该手机号已注册";
        }
    }



    @ApiOperation(value = "app用户注册2")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user_id", value = "身份证号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "license_plate_number", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "picture_index", value = "车辆照片", required = true, dataType = "String"),
            @ApiImplicitParam(name = "registration", value = "机动车登记证照片", required = true, dataType = "String"),
            @ApiImplicitParam(name = "vehicle_license", value = "车辆行驶证照片", required = true, dataType = "String"),
    })
    @PostMapping(value = "/app_register2", produces = "text/plain;charset=utf-8")
    public String app_register2(String user_name,String password,String user_id,String license_plate_number,String picture_index,String registration,String vehicle_license){
        return userService.add_User(user_name,password,user_id,license_plate_number,picture_index,registration,vehicle_license);
    }


    @ApiOperation(value = "获取用户身份证号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
    })
    @GetMapping(value = "/getUserId/{user_name}", produces = "text/plain;charset=utf-8")
    public String getUserId(@PathVariable String user_name){
        return userService.getUserId(user_name);
    }


    @ApiOperation(value = "查找所有用户")
    @GetMapping(value = "/getAllUsers", produces = "application/json; charset=utf-8")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }



    @ApiOperation(value = "删除所有用户")
    @DeleteMapping(value = "/delete_User")
    public void delete_User(){
        userService.delete_User();
    }



}
