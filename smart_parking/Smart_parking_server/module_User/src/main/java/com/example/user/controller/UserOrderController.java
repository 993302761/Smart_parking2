package com.example.user.controller;

import com.example.user.serviceImpl.UserOrderServiceImpl;
import com.feign.api.entity.order.Order_information;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "用户订单模块")
@RequestMapping("/UserOrder")
@DefaultProperties(defaultFallback ="err")
public class UserOrderController {

    @Resource
    private UserOrderServiceImpl userOrderService;


    @ApiOperation(value = "app开始订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "parking_lot_number", value = "停车场编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "license_plate_number", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")

    })
    @PostMapping(value = "/generate_order", produces = "text/plain;charset=utf-8")
    @HystrixCommand
    public String  generate_order (String user_name,String license_plate_number,String parking_lot_number,String UUID){
        return userOrderService.generate_order(user_name,license_plate_number,parking_lot_number);
    }



    @ApiOperation(value = "搜索订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order_number", value = "订单编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")
    })
    @GetMapping(value = "/findOrder", produces = "application/json; charset=utf-8")
    @HystrixCommand
    public Object findOrder (String user_name,String order_number,String UUID){
        return userOrderService.findOrder(user_name,order_number);
    }



    @ApiOperation(value = "订单支付完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order_number", value = "订单编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")
    })
    @PutMapping(value = "/complete_Order", produces = "text/plain;charset=utf-8")
    @HystrixCommand
    public String complete_Order (String user_name, String order_number, String UUID){
        return userOrderService.complete_Order(user_name,order_number);
    }







    @ApiOperation(value = "app订单取消")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order_number", value = "订单编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")
    })
    @PutMapping(value = "/app_cancellation_Order", produces = "text/plain;charset=utf-8")
    @HystrixCommand
    public String app_cancellation_Order (String user_name, String order_number, String UUID){
          return userOrderService.app_cancellation_Order(user_name,order_number);
    }


    @ApiOperation(value = "app获取用户订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")
    })
    @GetMapping(value = "/getUserOrder/{user_name}/{UUID}",produces = "application/json; charset=utf-8")
    @HystrixCommand
    public List<Order_information> getUserOrder (@PathVariable String user_name,@PathVariable String UUID){
        return userOrderService.getOrderByUsername(user_name);
    }


    @ApiOperation(value = "获取某一区域停车场情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "city", value = "所在城市", required = true, dataType = "String"),
            @ApiImplicitParam(name = "UUID", value = "通用唯一识别码", required = true, dataType = "String")
    })
    @GetMapping(value = "/get_parking_lot", produces = "application/json; charset=utf-8")
    @HystrixCommand
    public Object get_parking_lot (String user_name , String city, String UUID){
        return userOrderService.get_parking_lot(city);
    }


    private String err(){
        return "用户订单访问错误，请稍后再试";
    }



}
