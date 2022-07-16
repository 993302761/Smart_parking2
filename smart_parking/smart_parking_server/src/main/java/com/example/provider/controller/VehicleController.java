package com.example.provider.controller;


import com.example.provider.entity.Vehicle_information;
import com.example.provider.service.VehicleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "车辆信息绑定模块")
@RequestMapping("/Vehicle")
public class VehicleController {

    @Autowired(required = false)
    private VehicleServiceImpl vehicleService;



    @ApiOperation(value = "绑定车辆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "license_plate_number", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "picture_index", value = "车辆照片", required = true, dataType = "String"),
            @ApiImplicitParam(name = "registration", value = "机动车登记证照片", required = true, dataType = "String"),
            @ApiImplicitParam(name = "vehicle_license", value = "车辆行驶证照片", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")
    })
    @PostMapping(value = "/vehicle_binding", produces = "text/plain;charset=utf-8")
    public String  vehicle_binding (String user_name,String license_plate_number,String picture_index,String registration,String vehicle_license,String UUID){
        return vehicleService.add_Vehicle(user_name,license_plate_number,picture_index,registration,vehicle_license);
    }



    @ApiOperation(value = "获取用户绑定的车辆信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")

    })
    @GetMapping(value = "/getUserVehicle", produces = "application/json; charset=utf-8")
    public List<Vehicle_information> getUserVehicle (String user_name,String UUID){
        return vehicleService.getUserVehicle(user_name);
    }



    @ApiOperation(value = "删除绑定的车辆信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "license_plate_number", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")

    })
    @DeleteMapping(value = "/deleteVehicle", produces = "text/json; charset=utf-8")
    public String deleteVehicle (String user_name, String license_plate_number, String UUID){
        return vehicleService.delete_User_Vehicle(user_name, license_plate_number);
    }



    @ApiOperation(value = "查找车牌号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "license_plate_number", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")
    })
    @GetMapping(value = "/getVehicleNumber", produces = "application/json; charset=utf-8")
    public Vehicle_information getVehicleNumber (String user_name,String license_plate_number,String UUID){
        return vehicleService.getVehicleNumber(user_name,license_plate_number);
    }

}
