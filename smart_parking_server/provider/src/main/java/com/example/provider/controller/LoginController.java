package com.example.provider.controller;

import com.example.provider.service.ControllerServiceImpl;
import com.example.provider.service.ParkingLotServiceImpl;
import com.example.provider.service.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录模块")
@RequestMapping("/Login")
public class LoginController {

    @Autowired(required = false)
    private ParkingLotServiceImpl parkingLotService;


    @Autowired(required = false)
    private UserServiceImpl userService;


    @Autowired(required = false)
    private ControllerServiceImpl controllerService;


    @ApiOperation(value = "停车场管理员登录", notes = "输入停车场管理员账号和密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pctr_password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pctr_id", value = "停车场管理员账号", required = true, dataType = "String")
    })
    @GetMapping(value = "/parking_login/pctr_id/{pctr_id}/pctr_password/{pctr_password}", produces = "text/plain;charset=utf-8")
    public String parking_login(@PathVariable String pctr_id,@PathVariable String pctr_password){
        System.out.printf(pctr_id+' '+pctr_password);
        return parkingLotService.login_Parking(pctr_id,pctr_password);
    }


    @ApiOperation(value = "app用户登录", notes = "输入账号和密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "用户账号", required = true, dataType = "String")
    })
    @GetMapping(value = "/app_login/user_name/{user_name}/password/{password}", produces = "text/plain;charset=utf-8")
    public String app_login(@PathVariable String user_name,@PathVariable String password){
        return userService.login_User(user_name,password);
    }



    @ApiOperation(value = "超级管理员登录", notes = "输入超级管理员账号和密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctr_password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ctr_id", value = "超级管理员登录", required = true, dataType = "String")
    })
    @GetMapping(value = "/controller_login/ctr_id/{ctr_id}/ctr_password/{ctr_password}", produces = "text/plain;charset=utf-8")
    public String controller_login(@PathVariable String ctr_id,@PathVariable  String ctr_password){
        return controllerService.login_Ctl(ctr_id,ctr_password);
    }



}
