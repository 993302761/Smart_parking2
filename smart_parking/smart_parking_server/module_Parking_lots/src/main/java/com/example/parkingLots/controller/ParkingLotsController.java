package com.example.parkingLots.controller;

import com.example.parkingLots.entity.Parking_lot_information;
import com.example.parkingLots.service.ParkingLotServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



    @ApiOperation(value = "查找所有停车场管理员")
    @GetMapping(value = "/getAllParking", produces = "application/json; charset=utf-8")
    public List<Parking_lot_information> getAllParking(){
        return parkingLotService.getAllParking();
    }


    @ApiOperation(value = "删除所有停车场")
    @DeleteMapping(value = "/delete_Parking")
    public void delete_Parking(){
        parkingLotService.delete_Parking();
    }
}
