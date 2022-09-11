package com.example.order.serviceImpl;

import com.example.order.dao.OrderDao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feign.api.entity.order.Order;
import com.feign.api.service.ParkingLotFeignService;
import com.feign.api.service.VehicleFeignService;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Import({
        com.feign.api.service.ParkingLotFeignServiceDegradation.class,
        com.feign.api.service.VehicleFeignServiceDegradation.class
})
public class OrderServiceImpl {


    @Resource
    private OrderDao orderDao;

    @Resource
    private ParkingLotFeignService parkingLotFeignService;

    @Resource
    private VehicleFeignService vehicleFeignService;

    @Resource
    private RedisTemplate<String,Object> RedisTemplate;







    /**
     * TODO：生成订单
     * @param user_name 生成订单的用户
     * @param license_plate_number 车牌号
     * @param parking_lot_number 停车场编号
     * @return 是否成功
     */
    public String generate_order(String user_name,String license_plate_number,String parking_lot_number,String generation_time) {


        //生成订单编号
        StringBuffer order_number = null;
        order_number.append(user_name + '-' + parking_lot_number + '-' + generation_time)  ;

        //获取停车场收费标准
        String billing_rules=parkingLotFeignService.getParkingBilling_rules(parking_lot_number);
        if (billing_rules==null){
            return "停车场不存在";
        }else if (billing_rules.equals("系统繁忙，查找停车场名失败，请稍后再试")){
            return "停车场服务异常";
        }

        //检查车辆信息是否注册
        int check=vehicleFeignService.check_license_plate_number(user_name,license_plate_number);
        if (check==0){
            return "未注册车辆信息";
        }else if (check==-1){
            return "车辆信息服务异常";
        }


        //检测是否还有空车位
        boolean hasKey = RedisTemplate.hasKey(parking_lot_number);
        if(hasKey) {
            String s = (String) RedisTemplate.opsForValue().get(parking_lot_number);
            if (Integer.parseInt(s) <= 0) {
                return "车位已满";
            }
            try {
                RedisTemplate.opsForValue().increment(parking_lot_number, -1);   //车位自减
            } catch (Exception e) {
                RedisTemplate.opsForValue().increment(parking_lot_number, 1);        //车位自增
                e.printStackTrace();
                return "预约失败";
            }
        }else {
            return "停车场车位异常，请移步其他停车场";
        }

        //记录订单
        orderDao.add_Order(order_number.toString(),generation_time,user_name,parking_lot_number,license_plate_number);

        //订单存入redis
        RedisTemplate.opsForHash().put(order_number.toString(),"billing_rules",billing_rules);

        return order_number.toString();
    }




    /**
     * TODO：获取所有订单列表
     * @return 所有订单
     */
    public List<Order> getAllOrders() {
        List<Order> orderList = orderDao.getAllOrders();
        Order userInformation= null;
        Set<String> order = RedisTemplate.keys("*Order");
        Iterator<String> iterator = order.iterator();
        while (iterator.hasNext()){
            Object key=iterator.next();
            String value= (String) order.get(key);
            try {
                //反序列化
                userInformation = mapper.readValue(value, Order.class);
                orderList.add(userInformation);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return orderList;
    }



    /**
     * TODO：判断订单是否超时
     * @return 判断结果
     */
    public String orderTimeout(String user_name,String order_number) {
        Order order = orderDao.getOrderByNumber(order_number);
        if (order.getOrder_status().equals("等待进入")){
            String s = app_cancellation_Order(user_name, order_number);
            return "订单："+order_number+" 已超时 "+s;
        }
        return null;
    }





    /**
     * TODO：获取某位用户的订单列表
     * @param user_name 所查找的用户
     * @return 用户订单
     */
    public List<Order> getOrderByUsername(String user_name) {
        有问题
        return orderDao.getOrderByUsername(user_name);
    }




    /**
     * TODO：获取停车场订单列表
     * @param parking_lot_number 停车场编号
     * @return 停车场订单
     */
    public List<Order> getParkingOrders(String parking_lot_number) {
        有问题
        return orderDao.getOrderByParking(parking_lot_number);
    }






    /**
     * TODO：根据订单号查找订单
     * @param order_number 订单号
     * @return 查找订单
     */
    public Order getOrderByNumber(String order_number) {
        有问题
        return orderDao.getOrderByNumber(order_number);
    }





    /**
     * TODO：车辆进入
     * @param license_plate_number 车牌号
     * @param parking_lot_number 停车场编号
     * @return 是否成功
     */
    public String setStatus_in (String license_plate_number ,String parking_lot_number) {
        Order order = (Order) getRedisValue(parking_lot_number,license_plate_number, Order.class);
        if (order==null){
            return "未找到此订单";
        }
        if (order.getInTime()!=null||order.getOutTime()!=null){
            return "订单错误";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String generation_time= formatter.format(date);
        orderDao.vehicle_entry(generation_time,order.getOrder_number());
        return setStatus("进行中",parking_lot_number,license_plate_number);
    }





    /**
     * TODO：车辆离开
     * @param license_plate_number 车牌号
     * @param parking_lot_number 停车场编号
     * @return 是否成功
     */
    public String setStatus_out (String license_plate_number ,String parking_lot_number) {

        Order order = (Order) getRedisValue(parking_lot_number,license_plate_number, Order.class);
        if (order==null){
            return "未找到此订单";
        }
        if (order.getOutTime()!=null){
            return "订单错误";
        }
        String s=add_available_parking_spaces_num(order.getUser_name(),order.getOrder_number());
        if (s!=null){
            return s;
        }
        //判断停车场是否存在
        String parkingName=parkingLotFeignService.getParkingName(parking_lot_number);
        if (parkingName==null||parkingName.equals("")){
            return "未找到此订单";
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date outTime = new Date(System.currentTimeMillis());
        String generation_time= formatter.format(outTime);
        orderDao.vehicle_departure(generation_time,order.getOrder_number());
        setMoney(order.getOrder_number());
        return setStatus("未支付",order.getOrder_number());
    }


    /**
     * TODO：收费情况
     * @param order_number 订单编号
     */
    public String setMoney (String order_number){

        Order order = (Order) getRedisValue(parking_lot_number,license_plate_number, Order.class);
        //获取停车场的收费标准
        Float billing_rules= Float.valueOf(parkingLotFeignService.getParkingBilling_rules(order.getParking_lot_number()));
        int hours = (int) ((order.getOutTime().getTime() - order.getInTime().getTime()) / (1000 * 60* 60));
        float money=hours*billing_rules;
        orderDao.set_money(money,order.getOrder_number());
        return "已完成";
    }


    /**
     * TODO：用户查找订单
     * @param user_name 用户名
     * @param order_number 订单编号
     * @return 是否成功
     */
    public Order userGetParkingOrder (String user_name, String order_number){
        有问题
       return orderDao.userGetOrderByNumber(user_name,order_number);
    }



    /**
     * TODO：订单支付完成
     * @param user_name 用户名
     * @param order_number 订单编号
     * @return 是否成功
     */
    public String complete_Order (String user_name,String order_number){


        Order order = (Order) getRedisValue(parking_lot_number,license_plate_number, Order.class);
        if (order==null){
            return "未找到订单";
        }
        if (order.getOutTime()==null||order.getInTime()==null){
            return "请在订单结束后支付";
        }
        if (order.getOrder_status().equals("已完成")){
            return "订单已完成";
        }
        return setStatus("已完成",order.getOrder_number());
    }



    /**
     * TODO：车位自增
     * @param license_plate_number 车牌号
     * @param parking_lot_number 停车场编号
     * @return 是否成功
     */
    public String add_available_parking_spaces_num (String license_plate_number ,String parking_lot_number){

        Order order = (Order) getRedisValue(parking_lot_number,license_plate_number, Order.class);
        if (order==null){
            return "订单不存在";
        }
        boolean hasKey = RedisTemplate.hasKey(order.getParking_lot_number());
        if(hasKey){
            RedisTemplate.opsForValue().increment(order.getParking_lot_number(),1);        //车位自增
            return null;
        }else {
            return "停车场信息异常";
        }
    }



    /**
     * TODO：app取消订单
     * @param user_name 用户名
     * @param order_number 订单编号
     * @return 是否成功
     */
    public String app_cancellation_Order (String user_name,String order_number){
        String s=add_available_parking_spaces_num(user_name,order_number);
        if (s!=null){
            return s;
        }
        Order order = (Order) getRedisValue(order_number, Order.class);
        if (order==null){
            return "未找到订单";
        }
        else if (order.getOrder_status().equals("已取消")){
            return "请勿重复取消订单";
        }
        else if (order.getOrder_status().equals("已完成")){
            return "订单已完成";
        }
        else if (order.getOutTime()!=null||order.getInTime()!=null){
            return "订单进行中不可取消，若要取消可联系停车场";
        }
        return setStatus("已取消",order.getOrder_number());
    }



    /**
     * TODO：超级管理员取消订单
     * @param order_number 订单编号
     * @return 是否成功
     */
    public String cancelOrder (String order_number){
        Order order = (Order) getRedisValue(order_number, Order.class);
        if (order==null){
            return "未找到订单";
        }
        else if (order.getOrder_status().equals("已取消")){
            return "请勿重复取消订单";
        }
        else if (order.getOrder_status().equals("已完成")){
            return "订单已完成";
        }
        boolean hasKey = RedisTemplate.hasKey(order.getParking_lot_number());
        if(hasKey){
            RedisTemplate.opsForValue().increment(order.getParking_lot_number(),1);        //车位自增
            return setStatus("已取消",order.getOrder_number());
        }else {
            return "停车场信息异常";
        }
    }


    /**
     * TODO：停车场取消订单
     * @param order_number 订单编号
     * @return 是否成功
     */
    public String parking_cancellation_Order (String parking_lot_number,String order_number){
        Order orderByNumber = getOrderByNumber(order_number);
        String s=add_available_parking_spaces_num(orderByNumber.getUser_name(),order_number);
        if (s!=null){
            return s;
        }
        Order order = (Order) getRedisValue(order_number, Order.class);
        if (order==null){
            return "未找到订单";
        }else if (!parking_lot_number.equals(order.getParking_lot_number())){
            return "未找到此订单";
        }
        else if (order.getOrder_status().equals("已取消")){
            return "请勿重复取消订单";
        }
        else if (order.getOrder_status().equals("已完成")){
            return "订单已完成";
        }
        return setStatus("已取消",order_number);
    }




    /**
     * TODO：修改订单状态
     * @param status 状态信息
     * @param parking_lot_number 停车场编号
     * @param license_plate_number 车牌号
     * @return 是否成功
     * */
    public String setStatus (String status,String parking_lot_number,String license_plate_number){
        Order order = (Order) getRedisValue(parking_lot_number,license_plate_number, Order.class);
        order.setOrder_status(status);
        if (status.equals("已取消")||status.equals("已完成")){
//            orderDao.add_Order(order.getOrder_number(),
//                    order.getGeneration_time(),
//                    order.getUser_name(),
//                    order.getInTime(),
//                    order.getOutTime(),
//                    order.getParking_lot_name(),
//                    order.getParking_lot_number(),
//                    order.getLicense_plate_number(),
//                    order.getPayment_amount(),
//                    order.getOrder_status());
            deleteRedisValue(parking_lot_number,license_plate_number);
        }else {
            setRedisValue(parking_lot_number,license_plate_number, order);
        }
        return "订单:"+order.getOrder_number()+" 状态修改为："+status;
    }


//    /**
//     * TODO：向redis中存储对象
//     * @param key
//     * @param hashKey
//     * @param value
//     */
//    public void setRedisValue (String key,String hashKey, Order value)  {
//        //手动序列化
//        String jsonValue= null;
//        try {
//            jsonValue = mapper.writeValueAsString(value);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        RedisTemplate.opsForHash().put(key+"Order",hashKey,jsonValue);
//    }



//    /**
//     * TODO：从redis中获取对象
//     * @param key
//     * @param hashKey
//     */
//
//    public Object getRedisValue (String key,String hashKey)   {
//        String value= (String) RedisTemplate.opsForHash().get(key,hashKey);
//        //反序列化
//        Object userInformation= null;
//        try {
//            userInformation = mapper.readValue(value,clazz);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return userInformation;
//    }



//    /**
//     * TODO：从redis中删除对象
//     * @param parking_lot_number    停车场编号
//     * @param license_plate_number  车牌号
//     */
//    public void deleteRedisValue (String parking_lot_number,String license_plate_number) {
//         RedisTemplate.opsForHash().delete(parking_lot_number+"Order", license_plate_number);
//    }

}
