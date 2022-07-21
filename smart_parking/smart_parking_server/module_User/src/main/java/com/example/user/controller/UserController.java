package com.example.user.controller;

import com.example.user.entity.User;
import com.example.user.service.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @ApiImplicitParam(name = "vehicle_photos", value = "车辆照片", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "registration", value = "机动车登记证照片", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "driving_permit", value = "车辆行驶证照片", required = true, dataType = "MultipartFile"),
   })
    @PostMapping(value = "/app_register2", produces = "text/plain;charset=utf-8")
    public String app_register2(String user_name, String password, String user_id, String license_plate_number, MultipartFile vehicle_photos, MultipartFile registration, MultipartFile driving_permit){
        return userService.add_User(user_name,password,user_id,license_plate_number,vehicle_photos,registration,driving_permit);
    }



    @ApiOperation(value = "获取用户身份证号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
    })
    @GetMapping(value = "/getUserId/{user_name}", produces = "text/plain;charset=utf-8")
    public String getUserId(@PathVariable String user_name){
        return userService.getUserId(user_name);
    }





    @ApiOperation(value = "删除绑定的车辆信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "license_plate_number", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")

    })
    @DeleteMapping(value = "/deleteVehicle", produces = "text/json; charset=utf-8")
    public String deleteVehicle (String user_name, String license_plate_number, String UUID){

    }




    @ApiOperation(value = "搜索订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order_number", value = "订单编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")
    })
    @GetMapping(value = "/findOrder", produces = "application/json; charset=utf-8")
    public Object findOrder (String user_name,String order_number,String UUID){

    }



    @ApiOperation(value = "订单支付完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order_number", value = "订单编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")
    })
    @PutMapping(value = "/complete_Order", produces = "text/plain;charset=utf-8")
    public String complete_Order (String user_name,String order_number,String UUID){

    }




    @ApiOperation(value = "获取用户绑定的车辆信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")

    })
    @GetMapping(value = "/getUserVehicle", produces = "application/json; charset=utf-8")
    public Object getUserVehicle (String user_name,String UUID){

    }




    @ApiOperation(value = "app订单取消")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order_number", value = "订单编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")
    })
    @PutMapping(value = "/app_cancellation_Order", produces = "text/plain;charset=utf-8")
    public String app_cancellation_Order (String user_name,String order_number,String UUID){

    }





    @ApiOperation(value = "获取某一区域停车场情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "city", value = "所在城市", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")
    })
    @GetMapping(value = "/get_parking_lot", produces = "application/json; charset=utf-8")
    public Object get_parking_lot (String user_name , String city, String UUID){

    }




    @ApiOperation(value = "查找停车场")
    @ApiImplicitParams({

    })
    @PutMapping(value = "/parking_cancellation_Order", produces = "text/plain;charset=utf-8")
    public String parking_cancellation_Order (String parking_lot_number,String order_number){

    }



    @ApiOperation(value = "添加绑定车辆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user_id", value = "身份证号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "license_plate_number", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "vehicle_photos", value = "车辆照片", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "registration", value = "机动车登记证照片", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "driving_permit", value = "车辆行驶证照片", required = true, dataType = "MultipartFile"),
    })
    @PostMapping(value = "/vehicle_binding", produces = "text/plain;charset=utf-8")
    public String  vehicle_binding (String user_name, String user_id, String license_plate_number, MultipartFile vehicle_photos, MultipartFile registration, MultipartFile driving_permit){

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
