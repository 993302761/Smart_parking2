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

import java.io.IOException;
import java.util.List;

@RestController
@Api(tags = "用户模块")
@RequestMapping("/User")
public class UserController {



    @Autowired(required = false)
    private UserServiceImpl userService;


    //跳到网页
    @RequestMapping("/load")
    public String index(){
        return  "/upload.html";
    }


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
            System.out.println(vehicle_photos.getBytes().length);
            System.out.println(registration.getBytes().length);
            System.out.println(driving_permit.getBytes().length);

            return userService.add_User(
                    user_name,
                    password,user_id,
                    license_plate_number,
                    vehicle_photos.getBytes(),
                    registration.getBytes(),
                    driving_permit.getBytes(),
                    vehicle_photos.getOriginalFilename(),
                    registration.getOriginalFilename(),
                    driving_permit.getOriginalFilename());

        } catch (IOException e) {
            e.printStackTrace();
            return "错误";

        }
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
            @ApiImplicitParam(name = "city", value = "所在城市", required = true, dataType = "String")
    })
    @GetMapping(value = "/getParkingLot", produces = "application/json;charset=utf-8")
    public Object getParkingLot (String parking_lot_name,String city){
        return userService.getParkingLot(parking_lot_name,city);
    }




    @ApiOperation(value = "查找所有用户")
    @GetMapping(value = "/getAllUsers", produces = "application/json; charset=utf-8")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }




    @ApiOperation(value = "删除某位用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String")
    })
    @DeleteMapping(value = "/delete_User")
    public void delete_User(String user_name){
        userService.delete_User(user_name);
    }



}
