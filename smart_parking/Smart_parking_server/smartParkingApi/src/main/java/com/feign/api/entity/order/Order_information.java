package com.feign.api.entity.order;





public class Order_information {




    private String order_number;              //订单编号
    private String order_message;              //订单信息


    public Order_information(String order_number, String order_message) {
        this.order_number = order_number;
        this.order_message = order_message;
    }

    public Order_information() {
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getOrder_message() {
        return order_message;
    }

    public void setOrder_message(String order_message) {
        this.order_message = order_message;
    }

    @Override
    public String toString() {
        return "Order_information{" +
                "order_number='" + order_number + '\'' +
                ", order_message='" + order_message + '\'' +
                '}';
    }
}
