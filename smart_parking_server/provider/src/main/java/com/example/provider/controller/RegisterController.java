package com.example.provider.controller;

import com.example.provider.service.ParkingLotServiceImpl;
import com.example.provider.service.UserServiceImpl;
import com.example.provider.service.VehicleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "注册模块")
@RequestMapping("/Register")
public class RegisterController {

    @Autowired(required = false)
    private ParkingLotServiceImpl parkingLotService;


    @Autowired(required = false)
    private UserServiceImpl userService;

    @Autowired(required = false)
    private VehicleServiceImpl vehicleService;

    @ApiOperation(value = "app注册1")
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



    @ApiOperation(value = "app注册2")
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
        String s="用户:";
        s+=userService.add_User(user_name,password,user_id);
        if (s.equals("用户:注册成功"))
        {
            s+="--车辆信息：";
            s+=vehicleService.add_Vehicle(user_name,user_id,license_plate_number,picture_index,registration,vehicle_license);
        }
        return s;
    }


    @ApiOperation(value = "停车场管理员注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "latitude", value = "纬度", required = true, dataType = "String"),
            @ApiImplicitParam(name = "longitude", value = "经度", required = true, dataType = "String"),
            @ApiImplicitParam(name = "billing_rules", value = "计费规则  (元/小时)", required = true, dataType = "float",example = "0"),
            @ApiImplicitParam(name = "parking_spaces_num", value = "车位数量", required = true, dataType = "int",example = "0"),
            @ApiImplicitParam(name = "parking_in_the_city", value = "停车场所在地", required = true, dataType = "String"),
            @ApiImplicitParam(name = "parking_lot_name", value = "停车场名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pctr_password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pctr_id", value = "停车场管理员账号", required = true, dataType = "String")
    })
    @PostMapping(value = "/parking_register", produces = "text/plain;charset=utf-8")
    public String parking_register(String pctr_id,
                                 String pctr_password,
                                 String parking_lot_name,
                                 String parking_in_the_city,
                                 Integer parking_spaces_num,
                                 float billing_rules,
                                 String longitude,
                                 String latitude){
        return parkingLotService.add_Parking(pctr_id,pctr_password,parking_lot_name,parking_in_the_city,parking_spaces_num,billing_rules,longitude,latitude);
    }


}
