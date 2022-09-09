package com.example.order.controller;

import com.example.order.serviceImpl.OrderServiceImpl;

import com.feign.api.entity.order.Order_information;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Component
@RestController
@Api(tags = "订单模块")
@RequestMapping("/Order")
@Slf4j
public class    OrderController {


    @Resource
    private OrderServiceImpl orderService;


    //死信队列
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "dlOrderTimeout", autoDelete = "false",durable = "true"),
            exchange = @Exchange(name = "dlOrderExchange", autoDelete = "false"),  //交换机
            key = {"orderTimeout"}
    ))
    //@RabbitListener 标注在类上面表示当有收到消息的时候，就交给 @RabbitHandler 的方法处理，根据接受的参数类型进入具体的方法中。
    public void   orderTimeout (HashMap<String,String> map){
        log.info(orderService.orderTimeout( map.get("user_name"),  map.get("order_number")));
    }


    @ApiOperation(value = "app用户新增订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "license_plate_number", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "parking_lot_number", value = "停车场编号", required = true, dataType = "String")

    })
    @PostMapping(value = "/generate_order/{user_name}/{license_plate_number}/{parking_lot_number}", produces = "application/json; charset=utf-8")
    public String  generate_order (@PathVariable String user_name,@PathVariable String license_plate_number,@PathVariable String parking_lot_number){
        return orderService.generate_order(user_name,license_plate_number,parking_lot_number);
    }



    @ApiOperation(value = "app获取用户订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
    })
    @GetMapping(value = "/getOrderByUsername/{user_name}",produces = "application/json; charset=utf-8")
    public List<Order_information> getOrderByUsername (@PathVariable String user_name){
        return orderService.getOrderByUsername(user_name);
    }


    @ApiOperation(value = "停车场管理员获取用户订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parking_lot_number", value = "停车场编号", required = true, dataType = "String")
    })
    @GetMapping(value = "/getParkingOrder", produces = "application/json; charset=utf-8")
    public List<Order_information> getParkingOrder (String parking_lot_number){
        return orderService.getParkingOrders(parking_lot_number);
    }



    @ApiOperation(value = "app用户查找订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order_number", value = "订单编号", required = true, dataType = "String")
    })
    @GetMapping(value = "/userGetParkingOrder/{user_name}/{order_number}", produces = "application/json; charset=utf-8")
    public Order_information userGetParkingOrder (@PathVariable String user_name, @PathVariable String order_number){
        return orderService.userGetParkingOrder(user_name,order_number);
    }



    @ApiOperation(value = "根据订单编号搜索订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_number", value = "订单编号", required = true, dataType = "String"),
    })
    @GetMapping(value = "/getOrderByNumber", produces = "application/json; charset=utf-8")
    public Order_information getOrderByNumber (String order_number){
        return orderService.getOrderByNumber(order_number);
    }



    @ApiOperation(value = "车辆进入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "license_plate_number", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "parking_lot_number", value = "停车场编号", required = true, dataType = "String")
    })
    @PutMapping(value = "/setStatus_in", produces = "text/plain;charset=utf-8")
    public String setStatus_in (String license_plate_number ,String parking_lot_number){
        return orderService.setStatus_in(license_plate_number,parking_lot_number);
    }



    @ApiOperation(value = "车辆离开")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "license_plate_number", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "parking_lot_number", value = "停车场编号", required = true, dataType = "String")
    })
    @PutMapping(value = "/setStatus_out", produces = "text/plain;charset=utf-8")
    public String setStatus_out (String license_plate_number ,String parking_lot_number){
        return orderService.setStatus_out(license_plate_number,parking_lot_number);
    }



    @ApiOperation(value = "订单支付完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order_number", value = "订单编号", required = true, dataType = "String")
    })
    @PutMapping(value = "/complete_Order/{user_name}/{order_number}", produces = "text/plain;charset=utf-8")
    public String complete_Order (@PathVariable String user_name,@PathVariable String order_number){
        return orderService.complete_Order(user_name,order_number);
    }



    @ApiOperation(value = "app订单取消")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order_number", value = "订单编号", required = true, dataType = "String"),
    })
    @PutMapping(value = "/app_cancellation_Order/{user_name}/{order_number}", produces = "text/plain;charset=utf-8")
    public String app_cancellation_Order (@PathVariable String user_name,@PathVariable String order_number){
        return orderService.app_cancellation_Order(user_name,order_number);
    }



    @ApiOperation(value = "超级管理员订单取消")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_number", value = "订单编号", required = true, dataType = "String")
    })
    @PutMapping(value = "/cancelOrder/{order_number}", produces = "text/plain;charset=utf-8")
    public String cancelOrder (@PathVariable String order_number){
        return orderService.cancelOrder(order_number);
    }


    @ApiOperation(value = "停车场订单取消")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parking_lot_number", value = "停车场编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order_number", value = "订单编号", required = true, dataType = "String")
    })
    @PutMapping(value = "/parking_cancellation_Order/{parking_lot_number}/{order_number}", produces = "text/plain;charset=utf-8")
    public String parking_cancellation_Order (@PathVariable String parking_lot_number,@PathVariable String order_number){
        return orderService.parking_cancellation_Order(parking_lot_number,order_number);
    }





    @ApiOperation(value = "获取所有订单")
    @GetMapping(value = "/getAllOrders")
    public List<Order_information> getAllOrders (){
        return orderService.getAllOrders();
    }


}
