package com.example.administrators.controller;

import com.example.administrators.service.AdministratorsServiceImpl;
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
public class AdministratorsController {



    @Autowired(required = false)
    private AdministratorsServiceImpl administratorsService;




    @ApiOperation(value = "超级管理员登录", notes = "输入超级管理员账号和密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctr_password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ctr_id", value = "超级管理员登录", required = true, dataType = "String")
    })
    @GetMapping(value = "/controller_login", produces = "text/plain;charset=utf-8")
    public String controller_login( String ctr_id,  String ctr_password){
        return administratorsService.login_Ctl(ctr_id,ctr_password);
    }



}
