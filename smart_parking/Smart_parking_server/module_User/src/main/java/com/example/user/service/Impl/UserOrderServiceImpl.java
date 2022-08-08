package com.example.user.service.Impl;

import com.example.user.service.OrderFeignService;
import com.example.user.service.ParkingLotFeignService;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class UserOrderServiceImpl {


    @Resource
    private OrderFeignService orderFeignService;

    @Resource
    private ParkingLotFeignService parkingLotFeignService;




    /**
     * TODO：app开始订单
     * @param user_name 用户名
     * @param license_plate_number 车牌号
     * @param parking_lot_number 停车场编号
     * @return 是否成功
     */
    public String  generate_order (String user_name,String license_plate_number,String parking_lot_number){
        String order=orderFeignService.generate_order(user_name,license_plate_number,parking_lot_number);
        return order;
    }



    /**
     * TODO：搜索订单
     * @param user_name 用户名
     * @param order_number 订单号
     * @return 是否成功
     */
    public Object findOrder (String user_name,String order_number){
        Object order=orderFeignService.userGetParkingOrder(user_name,order_number);
        return order;
    }


    /**
     * TODO：订单支付完成
     * @param user_name 用户名
     * @param order_number 订单号
     * @return 是否成功
     */
    public String complete_Order (String user_name, String order_number){
        String exchange = orderFeignService.complete_Order(user_name,order_number);
        return exchange;
    }



    /**
     * TODO：app订单取消
     * @param user_name 用户名
     * @param order_number 订单号
     * @return 是否成功
     */
    public String app_cancellation_Order (String user_name,String order_number){
        String exchange = orderFeignService.app_cancellation_Order(user_name,order_number);
        return exchange;
    }



    /**
     * TODO：获取当前所在地的停车场情况
     * @param city 所在城市
     * @return 是否成功
     */
    public Object get_parking_lot ( String city){
        Object parkingLot=parkingLotFeignService.get_parking_lot(city);
        return parkingLot;
    }

}
