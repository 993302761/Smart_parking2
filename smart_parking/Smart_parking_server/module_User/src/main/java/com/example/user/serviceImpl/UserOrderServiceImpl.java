package com.example.user.serviceImpl;

import com.feign.api.entity.order.Order_information;
import com.feign.api.service.OrderFeignService;
import com.feign.api.service.ParkingLotFeignService;
import io.swagger.models.auth.In;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserOrderServiceImpl {


    @Resource
    private OrderFeignService orderFeignService;

    @Resource
    private ParkingLotFeignService parkingLotFeignService;



    @Resource
    private RedisTemplate<String, Integer> redisTemplate;


    /**
     * TODO：app开始订单
     * @param user_name 用户名
     * @param license_plate_number 车牌号
     * @param parking_lot_number 停车场编号
     * @return 是否成功
     */
    public String  generate_order (String user_name,String license_plate_number,String parking_lot_number){
        boolean hasKey = redisTemplate.hasKey(parking_lot_number);
        if(hasKey) {
            Integer s = redisTemplate.opsForValue().get(parking_lot_number);
            System.out.println(s);
            if (s <= 0) {
                return "车位已满";
            }
            redisTemplate.opsForValue().increment(parking_lot_number, -1);   //车位自减
            try {
                String generateOrder = orderFeignService.generate_order(user_name, license_plate_number, parking_lot_number);
                return generateOrder;
            } catch (Exception e) {
                redisTemplate.opsForValue().increment(parking_lot_number, 1);        //车位自增
                e.printStackTrace();
                return "预约失败";
            }
        }else {
            return "停车场车位异常，请移步其他停车场";
        }
    }



    /**
     * TODO：搜索订单
     * @param user_name 用户名
     * @param order_number 订单号
     * @return 是否成功
     */
    public Object findOrder (String user_name,String order_number){
        return orderFeignService.userGetParkingOrder(user_name,order_number);
    }



    /**
     * TODO：订单支付完成
     * @param user_name 用户名
     * @param order_number 订单号
     * @return 是否成功
     */
    public String complete_Order (String user_name, String order_number){
        Order_information order_information = orderFeignService.userGetParkingOrder(user_name, order_number);
        if (order_information==null){
            return "订单不存在";
        }
        boolean hasKey = redisTemplate.hasKey(order_information.getParking_lot_number());
        if(hasKey){
            redisTemplate.opsForValue().increment(order_information.getParking_lot_number(),1);        //车位自增
        }
        return orderFeignService.complete_Order(user_name,order_number);
    }



    /**
     * TODO：app订单取消
     * @param user_name 用户名
     * @param order_number 订单号
     * @return 是否成功
     */
    public String app_cancellation_Order (String user_name,String order_number){
        return orderFeignService.app_cancellation_Order(user_name,order_number);
    }



    /**
     * TODO：获取当前所在地的停车场情况
     * @param city 所在城市
     * @return 是否成功
     */
    public Object get_parking_lot ( String city){
        return parkingLotFeignService.get_parking_lot(city);
    }

}
