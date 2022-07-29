package com.example.vehicle.controller;



import com.example.vehicle.entity.Vehicle_Blob_information;
import com.example.vehicle.service.VehicleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "车辆信息绑定模块")
@RequestMapping("/Vehicle")
public class VehicleController {



    @Autowired(required = false)
    private VehicleServiceImpl vehicleService;



    @ApiOperation(value = "绑定车辆")
    @PostMapping(value = "/vehicle_binding", produces = "text/plain;charset=utf-8",consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PostMapping(value = "/vehicle_binding/{user_name}/{user_id}/{license_plate_number}/{vehicle_photos_suffix}/{registration_suffix}/{driving_permit_suffix}", produces = "text/plain;charset=utf-8")
    public String  vehicle_binding ( @RequestParam("user_name")String user_name,
                                     @RequestParam("user_id")String user_id,
                                     @RequestParam("license_plate_number")String license_plate_number,
                                     @RequestParam("vehicle_photos")byte[] vehicle_photos,
                                     @RequestParam("registration")byte[] registration,
                                     @RequestParam("driving_permit")byte[] driving_permit,
                                     @RequestParam("vehicle_photos_suffix")String vehicle_photos_suffix,
                                     @RequestParam("registration_suffix")String registration_suffix,
                                     @RequestParam("driving_permit_suffix")String driving_permit_suffix)
    {

        System.out.println(vehicle_photos.length);
        System.out.println(registration.length);
        System.out.println(driving_permit.length);

        return vehicleService.add_Vehicle(user_name,user_id,license_plate_number,vehicle_photos,registration,driving_permit,vehicle_photos_suffix,registration_suffix,driving_permit_suffix);

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
