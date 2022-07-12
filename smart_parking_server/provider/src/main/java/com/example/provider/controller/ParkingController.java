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
    public String change_parking_space (String parking_lot_number ,String Available_place_num){

    }
}
