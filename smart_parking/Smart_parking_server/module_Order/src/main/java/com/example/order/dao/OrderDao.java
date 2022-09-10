package com.example.order.dao;

import com.feign.api.entity.order.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderDao {

    /**
     * 根据订单号查找订单
     * */
    @Select("SELECT order_number,order_message from Order_information WHERE order_number =#{order_number} ")
    Order getOrderByNumber(@Param("order_number") String order_number);


//    /**
//     * 用户查找订单
//     * */
//    @Select("SELECT order_number,order_message from Order_information WHERE user_name =#{user_name} and order_number =#{order_number} ")
//    Order_information userGetOrderByNumber(@Param("user_name") String user_name,@Param("order_number") String order_number);




//    /**
//     * 根据用户名查找订单
//     * */
//    @Select("SELECT order_number,order_message FROM Order_information WHERE user_name =#{user_name}")
//    List<Order_information> getOrderByUsername(@Param("user_name") String user_name);


//    /**
//     * 根据停车场查找订单
//     * */
//    @Select("SELECT order_number,order_message FROM Order_information WHERE parking_lot_number =#{parking_lot_number}")
//    List<Order_information> getOrderByParking(@Param("parking_lot_number") String parking_lot_number);



    //待定存在
//    /**
//     * 根据停车场编号和车牌号查找订单
//     * */
//    @Select("SELECT order_number,order_message FROM Order_information WHERE parking_lot_number =#{parking_lot_number} and license_plate_number =#{license_plate_number} and order_status !=\"已取消\" and order_status !=\"已完成\" ")
//    Order_information getOrderByParkingAndOrder(@Param("parking_lot_number") String parking_lot_number, @Param("license_plate_number") String license_plate_number);



    //待定存在
//    /**
//     * 修改订单状态
//     * */
//    @Update("UPDATE Order_information SET order_status=#{order_status} WHERE order_number=#{order_number}")
//    int change_Order_status(@Param("order_status") String order_status,@Param("order_number") String order_number);

    //待定存在
//    /**
//     * 设置车辆进入时间
//     * */
//    @Update("UPDATE Order_information SET inTime=#{inTime} WHERE order_number=#{order_number}")
//    int  vehicle_entry (@Param("inTime") String inTime,@Param("order_number") String order_number);


    //待定存在
//    /**
//     * 设置车辆离开时间
//     * */
//    @Update("UPDATE Order_information SET outTime=#{outTime} WHERE order_number=#{order_number}")
//    int vehicle_departure(@Param("outTime") String outTime,@Param("order_number") String order_number);

    //待定存在
//    /**
//     * 设置支付金额
//     * */
//    @Update("UPDATE Order_information SET payment_amount=#{payment_amount} WHERE order_number=#{order_number}")
//    int set_money(@Param("payment_amount") float payment_amount,@Param("order_number") String order_number);



    //待定存在
//    /**
//     * 查找未完成订单
//     * */
//    @Select("SELECT count(1) FROM Order_information WHERE (order_status =\"等待进入\" or order_status =\"进行中\" or order_status =\"未支付\") and user_name =#{user_name} ")
//    int find_Incomplete_Order(@Param("user_name") String user_name);




    /**
     * 获取订单列表
     *
     */
    @Select("select order_number,order_message from Order_information")
    List<Order> getAllOrders();



    /**
     * 新增一条订单
     *
     * */
    @Insert("INSERT INTO Order_information VALUES(#{order_number}, #{order_message})")
    int add_Order(@Param("order_number") String order_number,
                  @Param("order_message")  String order_message);


}
