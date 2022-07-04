package com.example.provider.controller;


import com.example.provider.entiry.Parking_lot_information;
import com.example.provider.entiry.User;
import com.example.provider.service.ParkingLotServiceImpl;
import com.example.provider.service.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "查找模块")
@RequestMapping("/LookUp")
public class LookUpController {
    @Autowired(required = false)
    private ParkingLotServiceImpl parkingLotService;


    @Autowired(required = false)
    private UserServiceImpl userService;


    @ApiOperation(value = "查找所有停车场管理员")
    @GetMapping(value = "/getAllParking")
    public List<Parking_lot_information> getAllParking(){
        return parkingLotService.getAllParking();
    }



    @ApiOperation(value = "查找所有用户")
    @GetMapping(value = "/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
