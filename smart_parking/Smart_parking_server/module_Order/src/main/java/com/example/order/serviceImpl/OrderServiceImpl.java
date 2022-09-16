package com.example.order.serviceImpl;

import com.example.order.dao.OrderDao;

import com.feign.api.entity.order.Order;
import com.feign.api.service.ParkingLotFeignService;
import com.feign.api.service.VehicleFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
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
@Slf4j
public class OrderServiceImpl {


    @Resource
    private OrderDao orderDao;

    @Resource
    private ParkingLotFeignService parkingLotFeignService;

    @Resource
    private VehicleFeignService vehicleFeignService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;







    /**
     * TODO：生成订单
     * @param user_name 生成订单的用户
     * @param license_plate_number 车牌号
     * @param parking_lot_number 停车场编号
     * @return 是否成功
     */
    public String generate_order(String user_name,String license_plate_number,String parking_lot_number,long generation_time) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //生成订单编号
        StringBuilder order_number = new StringBuilder(user_name + '-' + parking_lot_number + '-' + license_plate_number+'&'+generation_time);

        //获取停车场收费标准
        Object billingRules =redisTemplate.opsForHash().get(parking_lot_number, "billing_rules");
        if (billingRules==null){
            String billing_rules=parkingLotFeignService.getParkingBilling_rules(parking_lot_number);
            if (billing_rules==null){
                return "停车场不存在";
            }else if (billing_rules.equals("系统繁忙，查找停车场名失败，请稍后再试")){
                return "停车场服务异常";
            }
            //订单信息存入redis
            redisTemplate.opsForHash().put(parking_lot_number,"billing_rules",billing_rules);
        }


        //检查车辆信息是否注册
        Boolean key = redisTemplate.hasKey(user_name + license_plate_number);
        if (!key) {
            int check=vehicleFeignService.check_license_plate_number(user_name,license_plate_number);
            if (check==0){
                return "未注册车辆信息";
            }else if (check==-1){
                return "车辆信息服务异常";
            }
            redisTemplate.opsForValue().set(user_name+license_plate_number,null);
        }



        Boolean hasKey = redisTemplate.hasKey(parking_lot_number);
        if (hasKey==null){
            return "错误";
        }
        //检测是否还有空车位
        if(hasKey) {
            int s = (int) redisTemplate.opsForHash().get(parking_lot_number,"Available_place_num");
            if (s <= 0) {
                return "车位已满";
            }
            try {
                redisTemplate.opsForHash().increment(parking_lot_number,"Available_place_num", -1);   //车位自减
            } catch (Exception e) {
                redisTemplate.opsForHash().increment(parking_lot_number,"Available_place_num", 1);        //车位自增
                e.printStackTrace();
                return "预约失败";
            }
        }else {
            return "停车场车位异常，请移步其他停车场";
        }

        //记录订单
        int i = orderDao.addOrder(order_number.toString(), formatter.format(generation_time), user_name, parking_lot_number, license_plate_number);
        if (i==0){
            return "信息错误";
        }
        redisTemplate.opsForHash().put(order_number.toString(),"inTime",null);
        return order_number.toString();
    }




    /**
     * TODO：获取所有订单列表
     * @return 所有订单
     */
    public List<Order> getAllOrders() {
        List<Order> orderList = orderDao.getAllOrders();
        return getMessageList(orderList);
    }



    /**
     * TODO：获取某位用户的订单列表
     * @param user_name 所查找的用户
     * @return 用户订单
     */
    public List<Order> getOrderByUsername(String user_name) {
        List<Order> orderList = orderDao.getOrderByUsername(user_name);
        return getMessageList(orderList);
    }



    /**
     * TODO：获取停车场订单列表
     * @param parking_lot_number 停车场编号
     * @return 停车场订单
     */
    public List<Order> getParkingOrders(String parking_lot_number) {
        List<Order> orderList = orderDao.getOrderByParking(parking_lot_number);
        return getMessageList(orderList);
    }




    /**
     * TODO：批量获取redis中的信息
     * @param orderList 订单列表
     */
    public List<Order> getMessageList(List<Order> orderList){
        for (int i = 0; i < orderList.size(); i++) {
            Order o=orderList.get(i);
            if (o.getOrder_status().equals("进行中")){
                o=getMessage(o,o.getOrder_number());
                orderList.set(i,o);
            }
        }
        return orderList;
    }


    /**
     * TODO：判断订单是否超时
     * @return 判断结果
     */
    public String orderTimeout(String order_number) {
        Boolean key = redisTemplate.hasKey(order_number);
        if (key==null){
            return "错误";
        }
        if (!key){
            Order order = orderDao.getOrderByNumber(order_number);
            if (order==null){
                return "无此订单";
            }else {
                return "订单 "+order.getOrder_status();
            }
        }
        Object inTime = redisTemplate.opsForHash().get(order_number, "inTime");
        if (inTime==null){
            redisTemplate.delete(order_number);
            return "订单："+order_number+" 已超时 ";
        }
        return null;
    }










    /**
     * TODO：根据订单号查找订单
     * @param order_number 订单号
     * @return 查找订单
     */
    public Order getOrderByNumber(String order_number) {
        Boolean key = redisTemplate.hasKey(order_number);
        if (key==null){
            return null;
        }
        if (key) {
            Order order = orderDao.getOrderByNumber(order_number);
            if (order == null) {
                return null;
            }
            order = getMessage(order, order_number);
            return order;
        }else {
            return null;
        }
    }




    /**
     * TODO：获取Redis中的订单信息
     * @param order 订单
     * @param order_number  订单号
     */
    public Order getMessage(Order order,String order_number){
        order.setOutTime((Timestamp) redisTemplate.opsForHash().get(order_number,"outTime"));
        order.setInTime((Timestamp) redisTemplate.opsForHash().get(order_number,"inTime"));
        String payment_amount = (String) redisTemplate.opsForHash().get(order_number, "payment_amount");
        if (payment_amount!=null) {
            order.setPayment_amount(Float.parseFloat(payment_amount));
        }else {
            order.setPayment_amount(0);
        }
        return order;
    }





    /**
     * TODO：车辆进入
     * @param license_plate_number 车牌号
     * @param parking_lot_number 停车场编号
     * @return 是否成功
     */
    public String setStatus_in (String license_plate_number ,String parking_lot_number) {
        String generation_time= getTime();
        String s=getOrderByLP(license_plate_number,parking_lot_number);
        if (s==null){
            return null;
        }
        return setMessage(s,"inTime",generation_time);
    }





    /**
     * TODO：车辆离开
     * @param license_plate_number 车牌号
     * @param parking_lot_number 停车场编号
     * @return 是否成功
     */
    public String setStatus_out (String license_plate_number ,String parking_lot_number) {

        String generation_time= getTime();
        String s=getOrderByLP(license_plate_number,parking_lot_number);
        if (s==null){
            return null;
        }
        String inTime = (String) redisTemplate.opsForHash().get(s, "inTime");
        if (inTime==null){
            return "车辆未进入";
        }

        String s0=add_available_parking_spaces_num(s);
        if (s0!=null){
            return s0;
        }

        String billing_rules = (String) redisTemplate.opsForHash().get(parking_lot_number, "billing_rules");
        setMoney(Timestamp.valueOf(inTime),Timestamp.valueOf(generation_time),billing_rules,s);
        return setMessage(s,"outTime",generation_time);
    }




    /**
     * TODO：获取当前时间
     * @return 当前时间
     */
    public String getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }





    /**
     * TODO：通过停车场编号和车牌号获取订单号
     * @param license_plate_number   车牌号
     * @param parking_lot_number  停车场编号
     * @return 订单号
     */
    public String getOrderByLP(String license_plate_number ,String parking_lot_number){
        Set<String> keys = redisTemplate.keys("*-" + parking_lot_number + "-" + license_plate_number+"&*");
        if (keys==null){
            return null;
        }
        if (keys.isEmpty()){
            return null;
        }else if (keys.size()>1){
            return null;
        }
        return keys.iterator().next();
    }







    /**
     * TODO：收费情况
     * @param billing_rules 价格
     */
    public void setMoney (Timestamp inTime,Timestamp outTime,String billing_rules,String order_number){
        int hours = (int) ((outTime.getTime() - inTime.getTime()) / (1000 * 60* 60));
        int minutes=(int) (((outTime.getTime()  - inTime.getTime())/1000-hours*(60*60))/60);
        hours+=minutes/60.0+0.7;
        float money=hours*Float.parseFloat(billing_rules);
        setMessage(order_number,"payment_amount", String.valueOf(money));
    }







    /**
     * TODO：订单支付完成
     * @param order_number 订单编号
     * @return 是否成功
     */
    public String complete_Order (String order_number){

        Boolean key = redisTemplate.hasKey(order_number);
        if (key==null){
            return "错误";
        }
        if (!key){
            Order order = orderDao.getOrderByNumber(order_number);
            if (order==null){
                return "未找到订单";
            }else if (order.getOrder_status().equals("已完成")||order.getOrder_status().equals("已取消")){
                return "订单已完成，请勿重复支付";
            }
        }
        String inTime = (String) redisTemplate.opsForHash().get(order_number, "inTime");
        if (inTime==null){
            orderDao.updateOrder(null,null,null,"已取消",order_number);
            return "订单未完成，请在完成后支付";
        }

        String outTime = (String) redisTemplate.opsForHash().get(order_number, "outTime");
        if (outTime==null){
            return "订单未完成，请在完成后支付";
        }
        Map<Object, Object> map = redisTemplate.opsForHash().entries(order_number);
        Iterator<Map.Entry<Object, Object>> iterator = map.entrySet().iterator();
        String payment_amount=null;
        while (iterator.hasNext()){
            Map.Entry<Object, Object> entry=iterator.next();
            String k= (String) entry.getKey();
            if (k.equals("inTime")){
                inTime= (String) entry.getValue();
            }else if (k.equals("payment_amount")){
                payment_amount=(String) entry.getValue();
            }
        }
        orderDao.updateOrder(inTime,outTime,payment_amount,"已完成",order_number);
        redisTemplate.delete(order_number);
        return "支付完成";
    }



    /**
     * TODO：车位自增
     * @param order_number 订单编号
     */
    public String add_available_parking_spaces_num (String order_number){
        Boolean key = redisTemplate.hasKey(order_number);
        if (key==null){
            return "错误";
        }
        if (!key){
            return "订单不存在";
        }
        String parking_lot_number=order_number.substring(order_number.indexOf("-")+1,order_number.lastIndexOf("-"));
        Boolean hasKey = redisTemplate.hasKey(parking_lot_number);
        if (hasKey==null){
            return "错误";
        }
        if(hasKey){
            redisTemplate.opsForHash().increment(parking_lot_number,"Available_place_num",1);        //车位自增
            return null;
        }else {
            return "停车场信息异常";
        }
    }



    /**
     * TODO：app取消订单
     * @param order_number 订单编号
     * @return 是否成功
     */
    public String app_cancellation_Order (String order_number){
        String s=add_available_parking_spaces_num(order_number);
        if (s!=null){
            return s;
        }
        Boolean key = redisTemplate.hasKey(order_number);
        if (key==null){
            return "错误-1";
        }
        if (key){
            String inTime = (String) redisTemplate.opsForHash().get(order_number, "inTime");
            if (inTime==null){
                orderDao.setStatus("已取消",order_number);
                redisTemplate.delete(order_number);
                return "订单已取消";
            }
            return "订单进行中，不可取消";
        }else {
            Order order = orderDao.getOrderByNumber(order_number);
            if (order==null){
                return "无此订单";
            }else if (order.getOrder_status().equals("已取消")){
                return "请勿重复取消订单";
            }
            else if (order.getOrder_status().equals("已完成")){
                return "订单已完成";
            }else {
                log.error(order.getOrder_number()+" "+order.getOrder_status());
                return "数据缓存错误";
            }
        }
    }



    /**
     * TODO：超级管理员/停车场管理员 取消订单
     * @param order_number 订单编号
     * @return 是否成功
     */
    public String cancelOrder (String order_number){
        String s=add_available_parking_spaces_num(order_number);
        if (s!=null){
            return s;
        }

        Boolean key = redisTemplate.hasKey(order_number);
        if (key==null){
            return "错误";
        }
        if (!key){
            Order order = orderDao.getOrderByNumber(order_number);
            if (order==null){
                return "无此订单";
            }else {
                return "订单已结束,请勿重复取消订单";
            }
        }else {
            Map<Object, Object> map = redisTemplate.opsForHash().entries(order_number);
            Iterator<Map.Entry<Object, Object>> iterator = map.entrySet().iterator();
            String outTime=null;
            String inTime=null;
            String payment_amount=null;
            while (iterator.hasNext()){
                Map.Entry<Object, Object> entry=iterator.next();
                String k= (String) entry.getKey();
                switch (k){
                    case "inTime":inTime= (String) entry.getValue();break;
                    case "payment_amount":payment_amount= (String) entry.getValue();break;
                    case "outTime":outTime= (String) entry.getValue();break;
                }
            }
            orderDao.updateOrder(inTime,outTime,payment_amount,"已取消",order_number);
            redisTemplate.delete(order_number);
            return "订单 "+order_number+" 已取消";
        }
    }






    /**
     * TODO：添加订单状态信息
     * @param name 订单信息名
     * @param order_number 订单编号
     * @param message 订单信息
     * @return 是否成功
     * */
    public String setMessage(String order_number, String name, String  message){
        Boolean hasKey = redisTemplate.hasKey(order_number);
        if (hasKey==null){
            return "错误";
        }
        if (!hasKey){
            Order order = orderDao.getOrderByNumber(order_number);
            if (order==null){
                return "订单不存在";
            }
            return "订单已结束";
        }
        Object o = redisTemplate.opsForHash().get(order_number, name);
        if (o!=null){
            return "请勿重复设置 "+name;
        }
        redisTemplate.opsForHash().put(order_number,name,message);

        return "订单:"+order_number+" 添加订单状态信息："+name+" 成功";
    }


    /**
     * TODO：查找用户是否有未完成订单
     * @param user_name
     * @param order_number
     * @return
     */
    public int checkOpenOrder(String user_name, String order_number) {
        return orderDao.checkOpenOrder(user_name,order_number);
    }
}
