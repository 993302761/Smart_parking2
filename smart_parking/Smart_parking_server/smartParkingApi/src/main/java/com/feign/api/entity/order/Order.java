package com.feign.api.entity.order;


import java.sql.Timestamp;

public class Order {

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
        return "{" +
                "generation_time=" + generation_time +
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

    public Order(String order_number, Timestamp generation_time, String user_name, Timestamp inTime, Timestamp outTime, String parking_lot_name, String parking_lot_number, String license_plate_number, float payment_amount, String order_status) {
        this.order_number = order_number;
        this.generation_time = generation_time;
        this.user_name = user_name;
        this.inTime = inTime;
        this.outTime = outTime;
        this.parking_lot_name = parking_lot_name;
        this.parking_lot_number = parking_lot_number;
        this.license_plate_number = license_plate_number;
        this.payment_amount = payment_amount;
        this.order_status = order_status;
    }

    public Order() {
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public Timestamp getGeneration_time() {
        return generation_time;
    }

    public void setGeneration_time(Timestamp generation_time) {
        this.generation_time = generation_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Timestamp getInTime() {
        return inTime;
    }

    public void setInTime(Timestamp inTime) {
        this.inTime = inTime;
    }

    public Timestamp getOutTime() {
        return outTime;
    }

    public void setOutTime(Timestamp outTime) {
        this.outTime = outTime;
    }

    public String getParking_lot_name() {
        return parking_lot_name;
    }

    public void setParking_lot_name(String parking_lot_name) {
        this.parking_lot_name = parking_lot_name;
    }

    public String getParking_lot_number() {
        return parking_lot_number;
    }

    public void setParking_lot_number(String parking_lot_number) {
        this.parking_lot_number = parking_lot_number;
    }

    public String getLicense_plate_number() {
        return license_plate_number;
    }

    public void setLicense_plate_number(String license_plate_number) {
        this.license_plate_number = license_plate_number;
    }

    public float getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(float payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
}
