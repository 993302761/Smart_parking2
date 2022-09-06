package com.feign.api.service;

import com.feign.api.entity.order.Order_information;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class OrderFeignServiceDegradation implements FallbackFactory<OrderFeignService> {


    @Override
    public OrderFeignService create(Throwable cause) {
        return new OrderFeignService() {
            @Override
            public List<Order_information> getAllOrders() {
                List<Order_information> order =new ArrayList<>();
                Order_information s=new Order_information();
                s.setOrder_number("系统繁忙，获取所有订单失败，请稍后再试");
                order.add(s);
                log.error("getAllOrders 服务出现异常，异常信息：" + cause);
                return order;
            }

            @Override
            public String parking_cancellation_Order(String parking_lot_number, String order_number) {
                log.error("parking_cancellation_Order 服务出现异常，异常信息：" + cause);
                return "系统繁忙，停车场取消订单失败，请稍后再试";
            }

            @Override
            public String generate_order(String user_name, String license_plate_number, String parking_lot_number) {
                log.error("generate_order 服务出现异常，异常信息：" + cause);
                return "系统繁忙，APP新增用户失败,请稍后再试";
            }

            @Override
            public Order_information userGetParkingOrder(String user_name, String parking_lot_number) {
                Order_information s=new Order_information();
                s.setOrder_number("系统繁忙，app用户查找订单失败，请稍后再试");
                log.error("userGetParkingOrder 服务出现异常，异常信息：" + cause);
                return s;
            }

            @Override
            public String complete_Order(String user_name, String order_number) {
                log.error("complete_Order 服务出现异常，异常信息：" + cause);
                return "系统繁忙，订单支付失败，请稍后再试";
            }

            @Override
            public String app_cancellation_Order(String user_name, String order_number) {
                log.error("app_cancellation_Order 服务出现异常，异常信息：" + cause);
                return "系统繁忙，app订单取消失败，请稍后再试";
            }

            @Override
            public List<Order_information> getOrderByUsername(String user_name) {
                List<Order_information> order =new ArrayList<>();
                Order_information s=new Order_information();
                s.setOrder_number("系统繁忙，用户 "+user_name+" 获取订单失败，请稍后再试");
                order.add(s);
                log.error("getOrderByUsername 服务出现异常，异常信息：" + cause);
                return order;
            }
        };
    }
}
