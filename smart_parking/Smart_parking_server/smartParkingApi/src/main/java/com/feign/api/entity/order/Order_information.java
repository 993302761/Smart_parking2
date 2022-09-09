package com.feign.api.entity.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data                   //生成get set equals toString等方法
@NoArgsConstructor      //生成无参构造方法
@AllArgsConstructor
public class Order_information {

    private String order_number;              //订单编号
    private Timestamp generation_time;        //订单生成时间
    private String user_name;                 //用户名
    private Timestamp inTime;                 //进入时间
    private Timestamp outTime;                //离开时间
    private String parking_lot_name;          //停车场名
    private String parking_lot_number ;       //停车场编号
    private String license_plate_number ;     //车牌号
    private float payment_amount ;            //支付金额
    private String order_status ;             //订单状态


    public String toMessage() {
        return "Order_information{" +
                "order_number='" + order_number + '\'' +
                ", generation_time=" + generation_time +
                ", user_name='" + user_name + '\'' +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                ", parking_lot_name='" + parking_lot_name + '\'' +
                ", parking_lot_number='" + parking_lot_number + '\'' +
                ", license_plate_number='" + license_plate_number + '\'' +
                ", payment_amount=" + payment_amount +
                ", order_status='" + order_status + '\'' +
                '}';
    }
}
