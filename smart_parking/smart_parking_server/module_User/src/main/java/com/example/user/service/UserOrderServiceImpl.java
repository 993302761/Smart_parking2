package com.example.user.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class UserOrderServiceImpl {

    @Resource
    private RestTemplate restTemplate;




    private final String orderURl="http://ClientOrder/Order";

    private final String parkingLotURl="http://ClientParkingLots/ParkingLots";



    /**
     * TODO：app开始订单
     * @param user_name 用户名
     * @param license_plate_number 车牌号
     * @param parking_lot_number 停车场编号
     * @return 是否成功
     */
    public String  generate_order (String user_name,String license_plate_number,String parking_lot_number){
        String url=orderURl+"/generate_order/"+user_name+"/"+license_plate_number+"/"+parking_lot_number;
        String order=restTemplate.getForObject(url,String.class);
        return order;
    }



    /**
     * TODO：搜索订单
     * @param user_name 用户名
     * @param order_number 订单号
     * @return 是否成功
     */
    public Object findOrder (String user_name,String order_number){
        String url=orderURl+"/userGetParkingOrder/"+user_name+"/"+order_number;
        Object order=restTemplate.getForObject(url,Object.class);
        return order;
    }


    /**
     * TODO：订单支付完成
     * @param user_name 用户名
     * @param order_number 订单号
     * @return 是否成功
     */
    public ResponseEntity<String> complete_Order (String user_name, String order_number){
        String url=orderURl+"/complete_Order/"+user_name+"/"+order_number;
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
        return exchange;
    }



    /**
     * TODO：app订单取消
     * @param user_name 用户名
     * @param order_number 订单号
     * @return 是否成功
     */
    public ResponseEntity<String> app_cancellation_Order (String user_name,String order_number){
        String url=orderURl+"/app_cancellation_Order/"+user_name+"/"+order_number;
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
        return exchange;
    }



    /**
     * TODO：获取当前所在地的停车场情况
     * @param city 所在城市
     * @return 是否成功
     */
    public Object get_parking_lot ( String city){
        String url=parkingLotURl+"/get_parking_lot/"+city;
        Object parkingLot=restTemplate.getForObject(url,Object.class);
        return parkingLot;
    }

}
