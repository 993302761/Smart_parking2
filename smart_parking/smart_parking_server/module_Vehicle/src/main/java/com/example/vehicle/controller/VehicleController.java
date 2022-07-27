package com.example.vehicle.controller;



import com.example.vehicle.entity.Vehicle_Blob_information;
import com.example.vehicle.service.VehicleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @ApiImplicitParam(name = "user_id", value = "身份证号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "license_plate_number", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "vehicle_photos", value = "车辆照片", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "registration", value = "机动车登记证照片", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "driving_permit", value = "车辆行驶证照片", required = true, dataType = "MultipartFile"),
    })
    @PostMapping(value = "/vehicle_binding", produces = "text/plain;charset=utf-8")

//    @PostMapping(value = "/vehicle_binding/{user_name}/{user_id}/{license_plate_number}/{vehicle_photos}/{registration}/{driving_permit}", produces = "text/plain;charset=utf-8")
    public String  vehicle_binding ( String user_name,
                                     String user_id,
                                     String license_plate_number,
                                     MultipartFile vehicle_photos,
                                     MultipartFile registration,
                                     MultipartFile driving_permit){
        System.out.println("in");
        return vehicleService.add_Vehicle(user_name,user_id,license_plate_number,vehicle_photos,registration,driving_permit);
    }



    @ApiOperation(value = "获取用户绑定的车辆列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
    })
    @GetMapping(value = "/getUserVehicle/{user_name}", produces = "application/json; charset=utf-8")
    public List<String> getUserVehicle (@PathVariable String user_name){
        return vehicleService.getUserVehicle(user_name);
    }



    @ApiOperation(value = "删除绑定的车辆信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "license_plate_number", value = "车牌号", required = true, dataType = "String"),

    })
    @DeleteMapping(value = "/deleteVehicle/{user_name}/{license_plate_number}", produces = "text/plain; charset=utf-8")
    public String deleteVehicle (@PathVariable String user_name, @PathVariable String license_plate_number){
        return vehicleService.delete_User_Vehicle(user_name, license_plate_number);
    }





    @ApiOperation(value = "检测车牌号与用户名是否匹配")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "license_plate_number", value = "车牌号", required = true, dataType = "String"),
    })
    @GetMapping(value = "/check_license_plate_number/{user_name}/{license_plate_number}", produces = "application/json; charset=utf-8")
    public int check_license_plate_number (@PathVariable String user_name,@PathVariable String license_plate_number){
        return vehicleService.check_license_plate_number(user_name,license_plate_number);
    }




    //TODO:更新的接口展示，只是展示可以将值获取
    @PostMapping(value = "/Upload3")
    @ResponseBody
    public String Upload3(
            String user_name,
            String user_id,
            String license,
            MultipartFile vehicle,
            MultipartFile regist,
            MultipartFile driving){
        System.out.println(user_name);
        System.out.println(user_id);
        System.out.println(license);
        System.out.println(vehicle.getOriginalFilename());
        System.out.println(regist.getOriginalFilename());
        System.out.println(driving.getOriginalFilename());
        return "Y";
    }

}
