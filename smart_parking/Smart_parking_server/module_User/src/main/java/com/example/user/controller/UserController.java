package com.example.user.controller;

import com.example.user.serviceImpl.UserServiceImpl;

import com.feign.api.entity.order.Order_information;
import com.feign.api.entity.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @GetMapping(value = "/app_login/{user_name}/{password}/{UUID}", produces = "text/plain;charset=utf-8")
    public String app_login(@PathVariable String user_name,@PathVariable String password,@PathVariable String UUID){
        return userService.login_User(user_name,password,UUID);
    }



    @ApiOperation(value = "app用户注册1")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @GetMapping(value = "/app_register1", produces = "application/json;charset=utf-8")
    public boolean app_register1(String user_name, String password){
        return userService.find(user_name);
    }



    @ApiOperation(value = "app用户注册2")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user_id", value = "身份证号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "license_plate_number", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "vehicle_photos", value = "车辆照片", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "registration", value = "机动车登记证照片", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "driving_permit", value = "车辆行驶证照片", required = true, dataType = "MultipartFile"),
   })
    @PostMapping(value = "/app_register2", produces = "text/plain;charset=utf-8")
    public String app_register2(String user_name,
                                String password,
                                String user_id,
                                String license_plate_number,
                                MultipartFile vehicle_photos,
                                MultipartFile registration,
                                MultipartFile driving_permit){

        try {

            return userService.add_User(
                    user_name,
                    password,user_id,
                    license_plate_number,
                    vehicle_photos,
                    registration,
                    driving_permit);

        } catch (IOException e) {
            e.printStackTrace();
            return "错误";

        }
    }





    @ApiOperation(value = "删除绑定的车辆信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "license_plate_number", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")

    })
    @DeleteMapping(value = "/deleteVehicle", produces = "text/plain; charset=utf-8")
    public String deleteVehicle (String user_name, String license_plate_number, String UUID){
        return userService.deleteVehicle(user_name,license_plate_number);
    }



    @ApiOperation(value = "获取用户绑定的车辆信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")

    })
    @GetMapping(value = "/getUserVehicle", produces = "application/json; charset=utf-8")
    public List<String> getUserVehicle (String user_name,String UUID){
        return userService.getUserVehicle(user_name);
    }



    @ApiOperation(value = "添加绑定车辆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user_id", value = "身份证号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "license_plate_number", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "vehicle_photos", value = "车辆照片", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "registration", value = "机动车登记证照片", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "driving_permit", value = "车辆行驶证照片", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")
    })
    @PostMapping(value = "/vehicle_binding", produces = "text/plain;charset=utf-8")
    public String vehicle_binding (String user_name,
                                   String user_id,
                                   String license_plate_number,
                                   MultipartFile vehicle_photos,
                                   MultipartFile registration,
                                   MultipartFile driving_permit,
                                   String UUID){

            return userService.vehicle_binding(user_name,
                    user_id,
                    license_plate_number,
                    vehicle_photos,
                    registration,
                    driving_permit);
    }



    @ApiOperation(value = "获取用户身份证号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String")
    })
    @GetMapping(value = "/getUserId/{user_name}", produces = "text/plain;charset=utf-8")
    public String getUserId(@PathVariable String user_name){
        return userService.getUserId(user_name);
    }







    @ApiOperation(value = "查找停车场")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parking_lot_name", value = "停车场名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "city", value = "所在城市", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")
    })
    @GetMapping(value = "/getParkingLot", produces = "application/json;charset=utf-8")
    public Object getParkingLot (String parking_lot_name,String city,String user_name,String UUID){
        return userService.getParkingLot(parking_lot_name,city);
    }




    @ApiOperation(value = "查找所有用户")
    @GetMapping(value = "/getAllUsers", produces = "application/json; charset=utf-8")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }




    @ApiOperation(value = "删除某位用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")
    })
    @DeleteMapping(value = "/delete_User")
    public String delete_User(String user_name,String UUID){
        return userService.delete_User(user_name,UUID);
    }



}
