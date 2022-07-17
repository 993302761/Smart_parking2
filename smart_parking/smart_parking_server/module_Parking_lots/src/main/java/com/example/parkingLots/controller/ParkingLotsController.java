package com.example.parkingLots.controller;

import com.example.parkingLots.entity.Parking;
import com.example.parkingLots.service.ParkingLotServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "停车场管理员模块")
@RequestMapping("/ParkingLots")
public class ParkingLotsController {

    @Autowired(required = false)
    private ParkingLotServiceImpl parkingLotService;




    @ApiOperation(value = "停车场管理员登录", notes = "输入停车场管理员账号和密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pctr_password", value = "密码",required = true,  dataType = "String"),
            @ApiImplicitParam(name = "pctr_id", value = "停车场管理员账号", required = true, dataType = "String")
    })
    @GetMapping(value = "/parking_login", produces = "text/plain;charset=utf-8")
    public String parking_login( String pctr_id, String pctr_password){
        return parkingLotService.login_Parking(pctr_id,pctr_password);
    }


    @ApiOperation(value = "车位情况变化")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parking_lot_number", value = "停车场编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "Available_place_num", value = "可用车位数量", required = true, dataType = "String")
    })
    @PutMapping(value = "/change_parking_space", produces = "text/json; charset=utf-8")
    public void change_parking_space (String parking_lot_number ,String Available_place_num){
        parkingLotService.change_parking_space(parking_lot_number,Available_place_num);
    }



    @ApiOperation(value = "获取某一区域停车场情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "city", value = "所在城市", required = true, dataType = "String"),
    })
    @GetMapping(value = "/get_parking_lot", produces = "application/json; charset=utf-8")
    public Object get_parking_lot (String city){
        return parkingLotService.get_parking_lot(city);
    }


    @ApiOperation(value = "查找所有停车场管理员")
    @GetMapping(value = "/getAllParking", produces = "application/json; charset=utf-8")
    public List<Parking> getAllParking(){
        return parkingLotService.getAllParking();
    }


    @ApiOperation(value = "删除所有停车场")
    @DeleteMapping(value = "/delete_Parking")
    public void delete_Parking(){
        parkingLotService.delete_Parking();
    }
}
