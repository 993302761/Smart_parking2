package com.example.administrators.controller;

import com.example.administrators.service.Impl.AdministratorsServiceImpl;
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
@Api(tags = "超级管理员模块")
@RequestMapping("/Administrators")
public class AdministratorsController {



    @Autowired(required = false)
    private AdministratorsServiceImpl administratorsService;




    @ApiOperation(value = "超级管理员登录", notes = "输入超级管理员账号和密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctr_password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ctr_id", value = "超级管理员登录", required = true, dataType = "String")
    })
    @GetMapping(value = "/controller_login/{ctr_id}/{ctr_password}", produces = "text/plain;charset=utf-8")
    public String controller_login(@PathVariable String ctr_id,@PathVariable  String ctr_password){
        return administratorsService.login_Ctl(ctr_id,ctr_password);
    }


    @ApiOperation(value = "查找所有用户")
    @GetMapping(value = "/getAllUsers", produces = "application/json; charset=utf-8")
    public Object getAllUsers()  {
        return administratorsService.getAllUsers();
    }


    @ApiOperation(value = "查找所有停车场")
    @GetMapping(value = "/getAllParking", produces = "application/json; charset=utf-8")
    public Object getAllParking()  {
        return administratorsService.getAllParking();
    }


    @ApiOperation(value = "查找所有订单")
    @GetMapping(value = "/getAllOrder", produces = "application/json; charset=utf-8")
    public Object getAllOrder()  {
        return administratorsService.getAllOrder();
    }


}
