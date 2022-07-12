package com.example.provider.controller;

import com.example.provider.service.ParkingLotServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@Api(tags = "车位情况模块")
@RequestMapping("/Parking")
public class ParkingController {

    @Autowired(required = false)
    private ParkingLotServiceImpl parkingLotService;

    @ApiOperation(value = "车位情况变化")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parking_lot_number", value = "停车场编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "Available_place_num", value = "可用车位数量", required = true, dataType = "String")
    })
    @PostMapping(value = "/change_parking_space", produces = "text/json; charset=utf-8")
    public void change_parking_space (String parking_lot_number ,String Available_place_num){
        parkingLotService.change_parking_space(parking_lot_number,Available_place_num);
    }



    @ApiOperation(value = "获取当前地区停车场")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "latitude", value = "纬度", required = true, dataType = "String"),
            @ApiImplicitParam(name = "longitude", value = "经度", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")
    })
    @PostMapping(value = "/change_parking_space", produces = "text/json; charset=utf-8")
    public void get_parking_lot (String user_name ,String longitude, String latitude,String UUID){

    }
}
