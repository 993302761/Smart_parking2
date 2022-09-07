package com.example.administrators.controller;

import com.example.administrators.serviceImpl.AdministratorsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "超级管理员模块")
@RequestMapping("/Administrators")
public class AdministratorsController {



    @Resource
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


    @ApiOperation(value = "停车场信息更改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "billing_rules", value = "计费规则  (元/小时)", required = true, dataType = "float",example = "0"),
            @ApiImplicitParam(name = "parking_spaces_num", value = "车位数量", required = true, dataType = "int",example = "0"),
            @ApiImplicitParam(name = "parking_in_the_city", value = "停车场所在地", required = true, dataType = "String"),
            @ApiImplicitParam(name = "parking_lot_name", value = "停车场名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pctr_id", value = "停车场管理员账号", required = true, dataType = "String")
    })
    @PutMapping(value = "/updateParking", produces = "text/plain;charset=utf-8")
    public String updateParking(String pctr_id,
                                String parking_lot_name,
                                String parking_in_the_city,
                                Integer parking_spaces_num,
                                float billing_rules){
        return administratorsService.updateParking(pctr_id,parking_lot_name,parking_in_the_city,parking_spaces_num,billing_rules);
    }



    @ApiOperation(value = "超级管理员取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_number", value = "订单编号", required = true, dataType = "String")
    })
    @PutMapping(value = "/cancelOrder/{order_number}", produces = "text/plain;charset=utf-8")
    public String cancelOrder (@PathVariable  String order_number){
        return administratorsService.cancelOrder(order_number);
    }


}
