package com.example.provider.controller;


import com.example.provider.entiry.Order_information;
import com.example.provider.entiry.Parking_lot_information;
import com.example.provider.entiry.User;
import com.example.provider.entiry.Vehicle_information;
import com.example.provider.service.OrderServiceImpl;
import com.example.provider.service.ParkingLotServiceImpl;
import com.example.provider.service.UserServiceImpl;
import com.example.provider.service.VehicleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "数据模块")
@RequestMapping("/Data")
public class DataController {

    @Autowired(required = false)
    private ParkingLotServiceImpl parkingLotService;


    @Autowired(required = false)
    private UserServiceImpl userService;


    @Autowired(required = false)
    private VehicleServiceImpl vehicleService;


    @Autowired(required = false)
    private OrderServiceImpl orderService;



    @ApiOperation(value = "查找所有停车场管理员")
    @GetMapping(value = "/getAllParking", produces = "application/json; charset=utf-8")
    public List<Parking_lot_information> getAllParking(){
        return parkingLotService.getAllParking();
    }



    @ApiOperation(value = "查找所有用户")
    @GetMapping(value = "/getAllUsers", produces = "application/json; charset=utf-8")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }


    @ApiOperation(value = "查找所有绑定的车辆信息")
    @GetMapping(value = "/getAllVehicle", produces = "application/json; charset=utf-8")
    public List<Vehicle_information> getAllVehicle(){
        return vehicleService.getAllVehicle();
    }


    @ApiOperation(value = "查找所有订单情况")
    @GetMapping(value = "/getAllOrder", produces = "application/json; charset=utf-8")
    public List<Order_information> getAllOrder(){
        return orderService.getAllOrders();
    }


    @ApiOperation(value = "删除所有用户")
    @PostMapping(value = "/delete_User")
    public void delete_User(){
         userService.delete_User();
    }


    @ApiOperation(value = "删除所有停车场")
    @PostMapping(value = "/delete_Parking")
    public void delete_Parking(){
        parkingLotService.delete_Parking();
    }


    @ApiOperation(value = "删除所有订单")
    @PostMapping(value = "/delete_Order")
    public void delete_Order(){
        orderService.delete_Order();
    }


    @ApiOperation(value = "删除所有绑定车辆")
    @PostMapping(value = "/delete_Vehicle")
    public void delete_Vehicle(){
        vehicleService.delete_Vehicle();
    }

    @ApiOperation(value = "初始化数据")
    @PostMapping(value = "/initialization")
    public void initialization(){
        delete_Order();
        delete_User();
        delete_Parking();
        delete_Vehicle();
        userService.add_User("18153301670","lyq2001124","435126587965435874");
        vehicleService.add_Vehicle("18153301670","湘A88888","xxxxx","xxxxx","xxxxx");
        parkingLotService.add_Parking("123","123","天津xx停车场","天津",10, 5.0F,"39.064976","117.302938");
        parkingLotService.add_Parking("456","456","北京市xx停车场","北京",20, 10.0F,"39.821263","117.442708");

    }


}
