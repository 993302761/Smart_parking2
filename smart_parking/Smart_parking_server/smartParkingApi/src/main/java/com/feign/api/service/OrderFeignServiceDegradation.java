package com.feign.api.service;

import com.feign.api.entity.order.Order_information;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderFeignServiceDegradation implements OrderFeignService{
    @Override
    public List<Order_information> getAllOrders() {
        List<Order_information> order =new ArrayList<>();
        Order_information s=new Order_information();
        s.setOrder_number("系统繁忙，获取所有订单失败，请稍后再试");
        order.add(s);
        return order;
    }

    @Override
    public String parking_cancellation_Order(String parking_lot_number, String order_number) {
        return "系统繁忙，停车场取消订单失败，请稍后再试";
    }

    @Override
    public String generate_order(String user_name, String license_plate_number, String parking_lot_number) {
        return "系统繁忙，APP新增用户失败,请稍后再试";
    }

    @Override
    public Order_information userGetParkingOrder(String user_name, String parking_lot_number) {
        Order_information s=new Order_information();
        s.setOrder_number("系统繁忙，app用户查找订单失败，请稍后再试");
        return s;
    }

    @Override
    public String complete_Order(String user_name, String order_number) {
        return "系统繁忙，订单支付失败，请稍后再试";
    }

    @Override
    public String app_cancellation_Order(String user_name, String order_number) {
        return "系统繁忙，app订单取消失败，请稍后再试";
    }

    @Override
    public List<Order_information> getOrderByUsername(String user_name) {
        List<Order_information> order =new ArrayList<>();
        Order_information s=new Order_information();
        s.setOrder_number("系统繁忙，用户 "+user_name+" 获取订单失败，请稍后再试");
        order.add(s);
        return order;
    }
}
