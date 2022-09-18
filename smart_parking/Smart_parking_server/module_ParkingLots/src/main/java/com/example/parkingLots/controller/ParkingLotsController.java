package com.example.parkingLots.controller;

import com.example.parkingLots.serviceImpl.ParkingLotServiceImpl;

import com.feign.api.entity.parkingLots.Parking;
import com.feign.api.entity.parkingLots.Parking_for_user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "停车场管理员模块")
@RequestMapping("/ParkingLots")
public class ParkingLotsController {

    @Resource
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



    @ApiOperation(value = "获取周边所有停车场")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "latitude", value = "纬度", required = true, dataType = "String"),
            @ApiImplicitParam(name = "longitude", value = "经度", required = true, dataType = "String"),
            @ApiImplicitParam(name = "city", value = "城市", required = true, dataType = "String")
    })
    @GetMapping(value = "/parking_register/{latitude}/{longitude}/{city}", produces = "application/json;charset=utf-8")
    public HashMap<String, Point> peripheralParking(@PathVariable String latitude,
                                                    @PathVariable String longitude,
                                                    @PathVariable String city){
        return parkingLotService.peripheralParking(latitude,longitude,city);
    }



    @ApiOperation(value = "停车场管理员注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "latitude", value = "纬度", required = true, dataType = "String"),
            @ApiImplicitParam(name = "longitude", value = "经度", required = true, dataType = "String"),
            @ApiImplicitParam(name = "billing_rules", value = "计费规则  (元/小时)", required = true, dataType = "float",example = "0"),
            @ApiImplicitParam(name = "parking_spaces_num", value = "车位数量", required = true, dataType = "int",example = "0"),
            @ApiImplicitParam(name = "parking_in_the_city", value = "停车场所在地", required = true, dataType = "String"),
            @ApiImplicitParam(name = "parking_lot_name", value = "停车场名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pctr_password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pctr_id", value = "停车场管理员账号", required = true, dataType = "String")
    })
    @PostMapping(value = "/parking_register", produces = "text/plain;charset=utf-8")
    public String parking_register(String pctr_id,
                                   String pctr_password,
                                   String parking_lot_name,
                                   String parking_in_the_city,
                                   Integer parking_spaces_num,
                                   float billing_rules,
                                   String longitude,
                                   String latitude){
        return parkingLotService.add_Parking(pctr_id,pctr_password,parking_lot_name,parking_in_the_city,parking_spaces_num,billing_rules,longitude,latitude);
    }




    @ApiOperation(value = "停车场信息更改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "billing_rules", value = "计费规则  (元/小时)", required = true, dataType = "float",example = "0"),
            @ApiImplicitParam(name = "parking_spaces_num", value = "车位数量", required = true, dataType = "int",example = "0"),
            @ApiImplicitParam(name = "parking_in_the_city", value = "停车场所在地", required = true, dataType = "String"),
            @ApiImplicitParam(name = "parking_lot_number", value = "停车场编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pctr_id", value = "停车场管理员账号", required = true, dataType = "String")
    })
    @PutMapping(value = "/updateParking/{pctr_id}/{parking_lot_name}/{parking_in_the_city}/{parking_lot_number}/{billing_rules}", produces = "text/plain;charset=utf-8")
    public String updateParking(@PathVariable String pctr_id,
                                @PathVariable   String parking_lot_number,
                                @PathVariable    String parking_in_the_city,
                                @PathVariable   Integer parking_spaces_num,
                                @PathVariable    float billing_rules){
        return parkingLotService.updateParking(pctr_id,parking_lot_number,parking_in_the_city,parking_spaces_num,billing_rules);
    }



    @ApiOperation(value = "车位情况变化")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parking_lot_number", value = "停车场编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "Available_place_num", value = "可用车位数量", required = true, dataType = "int")
    })
    @PutMapping(value = "/change_parking_space", produces = "text/plain; charset=utf-8")
    public String change_parking_space (String parking_lot_number ,int Available_place_num){
        return parkingLotService.change_parking_space(parking_lot_number,Available_place_num);
    }


    @ApiOperation(value = "判断停车场是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parking_lot_number", value = "停车场编号", required = true, dataType = "String")
    })
    @GetMapping(value = "/change_parking_space", produces = "text/plain; charset=utf-8")
    public int  findParkingLot  (String parking_lot_number ){
        return parkingLotService.findParkingLot(parking_lot_number);
    }



    @ApiOperation(value = "根据停车场编号查找停车场名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parking_lot_number", value = "停车场编号", required = true, dataType = "String"),
    })
    @GetMapping(value = "/getParkingName/{parking_lot_number}", produces = "text/plain; charset=utf-8")
    public String getParkingName (@PathVariable String parking_lot_number ){
        return parkingLotService.getParkingName(parking_lot_number);
    }




    @ApiOperation(value = "根据停车场编号获取停车场收费标准")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parking_lot_number", value = "停车场编号", required = true, dataType = "String")
    })
    @GetMapping(value = "/getParkingBilling_rules/{parking_lot_number}", produces = "text/plain; charset=utf-8")
    public String getParkingBilling_rules (@PathVariable String parking_lot_number ){
        return parkingLotService.getParkingBilling_rules(parking_lot_number);
    }



    @ApiOperation(value = "停车场订单取消")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parking_lot_number", value = "停车场编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order_number", value = "订单编号", required = true, dataType = "String")
    })
    @PutMapping(value = "/parking_cancellation_Order/{parking_lot_number}/{order_number}", produces = "text/plain;charset=utf-8")
    public String parking_cancellation_Order (@PathVariable String parking_lot_number,@PathVariable  String order_number){
        return parkingLotService.parking_cancellation_Order(parking_lot_number,order_number);
    }



    @ApiOperation(value = "查找停车场")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parking_lot_name", value = "停车场名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "city", value = "所在城市", required = true, dataType = "String")
    })
    @GetMapping(value = "/getParkingLot/{parking_lot_name}/{city}", produces = "application/json;charset=utf-8")
    public List<Parking_for_user> getParkingLot (@PathVariable String parking_lot_name, @PathVariable String city){
        return parkingLotService.getParkingLot(parking_lot_name,city);
    }



    @ApiOperation(value = "获取某一区域停车场情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "city", value = "所在城市", required = true, dataType = "String"),
    })
    @GetMapping(value = "/get_parking_lot/{city}", produces = "application/json; charset=utf-8")
    public List<Parking_for_user> get_parking_lot (@PathVariable String city){
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
