package com.example.user.serviceImpl;

import com.feign.api.entity.order.Order;
import com.feign.api.service.OrderFeignService;
import com.feign.api.service.ParkingLotFeignService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
    private StringRedisTemplate stringRedisTemplate;


    /**
     * TODO：app开始订单
     * @param user_name 用户名
     * @param license_plate_number 车牌号
     * @param parking_lot_number 停车场编号
     * @return 是否成功
     */
    public String  generate_order (String user_name,String license_plate_number,String parking_lot_number,String UUID){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String generation_time= formatter.format(date);
            String key=UserServiceImpl.md5(user_name+UUID);
            boolean hasKey = stringRedisTemplate.hasKey(key);
            if(!hasKey){
                return "数据错误-3";
            }
            int s1 = Integer.parseInt(stringRedisTemplate.opsForValue().get(key));
            if (s1==1){
                return "您还有未完成的订单，请完成后再预约";
            }else if (s1>1){
                return "数据错误-4";
            }
            String s = orderFeignService.generate_order(user_name, license_plate_number, parking_lot_number,generation_time);
            HashMap<String,String> map=new HashMap();
            map.put("user_name",user_name);
            map.put("order_number",s);
            if (s.equals(user_name + '-' + parking_lot_number + '-' + generation_time)){
                rabbitTemplate.convertAndSend("OrderExchange","Timeout",map,setConfirmCallback());
                stringRedisTemplate.opsForValue().increment(key, 1);
                return "订单 "+s+" 已开始";
            }else {
                return s;
            }
        }catch (Exception e){
            e.printStackTrace();
            return "服务异常";
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
        boolean hasKey = stringRedisTemplate.hasKey(key);
        if(!hasKey){
            return "数据错误-1";
        }
        if (!stringRedisTemplate.opsForValue().get(key).equals("1")){
            return "数据错误-2";
        }
        stringRedisTemplate.opsForValue().increment(key, -1);

        rabbitTemplate.convertAndSend("IntegralExchange","addIntegral",user_name,setConfirmCallback());
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
