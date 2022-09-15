package com.example.user.serviceImpl;

import com.feign.api.entity.order.Order;
import com.feign.api.service.OrderFeignService;
import com.feign.api.service.ParkingLotFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
@Import({
        com.feign.api.service.ParkingLotFeignServiceDegradation.class,
        com.feign.api.service.OrderFeignServiceDegradation.class
})
public class UserOrderServiceImpl {


    @Resource
    private OrderFeignService orderFeignService;

    @Resource
    private ParkingLotFeignService parkingLotFeignService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RedisTemplate redisTemplate;


    /**
     * TODO：app开始订单
     * @param user_name 用户名
     * @param license_plate_number 车牌号
     * @param parking_lot_number 停车场编号
     * @return 是否成功
     */
    public String  generate_order (String user_name,String license_plate_number,String parking_lot_number,String UUID){

        String key=UserServiceImpl.md5(user_name+UUID);
        Boolean hasKey = redisTemplate.hasKey(key);
        if (!hasKey){
            return "找不到此用户";
        }
        int num = (int) redisTemplate.opsForValue().get(key);
        if(num==1){
            return "您还有订单未完成，请完成后再预约";
        }
        long l = System.currentTimeMillis();
        String s = orderFeignService.generate_order(user_name, license_plate_number, parking_lot_number,l);
        if (s==null){
            return "错误";
        }
        String s1=user_name + '-' + parking_lot_number + '-' + license_plate_number+'&'+l;
        if (s.equals(s1)){
            rabbitTemplate.convertAndSend("OrderExchange","Timeout",s,setConfirmCallback());
            Long aLong = redisTemplate.opsForValue().increment(key);
            if (aLong==-1){
                return "数据错误-3";
            }
            return "订单 "+s+" 已开始";
        }else {
            return s+" 失败";
        }
    }



    /**
     * TODO：搜索订单
     * @param user_name 用户名
     * @param order_number 订单号
     * @return 是否成功
     */
    public Object findOrder (String user_name,String order_number){
        return orderFeignService.userGetParkingOrder(order_number);
    }




    /**
     * TODO：设置RabbitMQ的ConfirmCallback
     */
    public CorrelationData setConfirmCallback (){
        CorrelationData correlationData=new CorrelationData(UUID.randomUUID().toString());
        correlationData.getFuture().addCallback(result -> {
                    if (result.isAck()){
                        //ACK
                        log.info("消息发送成功，id{}",correlationData.getId());
                    }else {
                        //NACK
                        log.error("消息发送失败，id{}，原因{}",correlationData.getId(),result.getReason());
                    }
                 },
                ex -> {
                    log.error("消息发送异常，id{}，原因{}",correlationData.getId(),ex.getMessage());
                });
        return correlationData;
    }



    /**
     * TODO：获取某位用户的订单列表
     * @param user_name 所查找的用户
     * @return 用户订单
     */
    public List<Order> getOrderByUsername(String user_name) {
        return orderFeignService.getOrderByUsername(user_name);
    }


    /**
     * TODO：订单支付完成
     * @param user_name 用户名
     * @param order_number 订单号
     * @return 是否成功
     */
    public String complete_Order (String user_name, String order_number, String UUID){
        String key=UserServiceImpl.md5(user_name+UUID);
        boolean hasKey = redisTemplate.hasKey(key);
        if(!hasKey){
            return "数据错误-1";
        }
        int o = (int) redisTemplate.opsForValue().get(key);
        if (!(o==1)){
            return "数据错误-2";
        }

        rabbitTemplate.convertAndSend("IntegralExchange","addIntegral",user_name,setConfirmCallback());
        String s = orderFeignService.complete_Order(user_name, order_number);
        if (s.equals("支付完成")){
            redisTemplate.opsForValue().decrement(key);
        }
        return s;
    }



    /**
     * TODO：app订单取消
     * @param user_name 用户名
     * @param order_number 订单号
     * @return 是否成功
     */
    public String app_cancellation_Order (String user_name,String order_number,String UUID){
        String s = orderFeignService.app_cancellation_Order(user_name, order_number);
        if (s.equals("订单已取消")){
            String key=UserServiceImpl.md5(user_name+UUID);
            redisTemplate.opsForValue().decrement(key);
        }
        return s;
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
