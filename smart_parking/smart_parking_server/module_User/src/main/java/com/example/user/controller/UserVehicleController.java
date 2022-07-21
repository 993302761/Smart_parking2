package com.example.user.controller;

import com.example.user.service.UserVehicleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "用户车辆信息模块")
@RequestMapping("/UserOrder")
public class UserVehicleController {


    @Autowired(required = false)
    private UserVehicleServiceImpl userVehicleService;


    @ApiOperation(value = "删除绑定的车辆信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "license_plate_number", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")

    })
    @DeleteMapping(value = "/deleteVehicle", produces = "text/json; charset=utf-8")
    public ResponseEntity<String> deleteVehicle (String user_name, String license_plate_number, String UUID){
        return userVehicleService.deleteVehicle(user_name,license_plate_number);
    }



    @ApiOperation(value = "获取用户绑定的车辆信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")

    })
    @GetMapping(value = "/getUserVehicle", produces = "application/json; charset=utf-8")
    public Object getUserVehicle (String user_name,String UUID){
        return userVehicleService.getUserVehicle(user_name);
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
    public ResponseEntity<String> vehicle_binding (String user_name, String user_id, String license_plate_number, MultipartFile vehicle_photos, MultipartFile registration, MultipartFile driving_permit, String UUID){
        return userVehicleService.vehicle_binding(user_name,user_id,license_plate_number,vehicle_photos,registration,driving_permit);
    }


}
