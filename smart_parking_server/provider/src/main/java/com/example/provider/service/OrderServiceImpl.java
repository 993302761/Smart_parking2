package com.example.provider.service;

import com.example.provider.dao.OrderDao;
import com.example.provider.dao.ParkingLotDao;
import com.example.provider.dao.VehicleDao;
import com.example.provider.entiry.Order_information;
import com.example.provider.entiry.Parking_lot_information;
import com.example.provider.entiry.User;
import com.example.provider.entiry.Vehicle_information;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl {
    @Resource
    private OrderDao orderDao;

    @Resource
    private ParkingLotDao parkingLotDao;


    @Resource
    private VehicleDao vehicleDao;


    /**
     * 生成订单
     *
     * */
    public String generate_order(String user_name,String license_plate_number,String parking_lot_number) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String generation_time= formatter.format(date);

        //生成订单编号
        String order_number;
        order_number = user_name;
        order_number += System.currentTimeMillis();

        //判断停车场是否存在
        Parking_lot_information parkingLotInformation=parkingLotDao.find_Parking_num(parking_lot_number);
        if (parkingLotInformation==null){
            return "停车场不存在";
        }

        //检查车辆信息是否注册
        int vehicle_information=vehicleDao.check_license_plate_number(user_name,license_plate_number);
        if (vehicle_information==0){
            return "未注册车辆信息";
        }

        //检查是否有未完成订单
        int incomplete_Order=orderDao.find_Incomplete_Order(order_number);
        if (incomplete_Order>0){
            return "您还有进行中或未支付的订单，请完成订单后再预约";
        }

        int i = orderDao.add_Order(order_number, generation_time, user_name,  null, null, parkingLotInformation.getParking_lot_name(), parking_lot_number, license_plate_number, 0, false, "等待进入");
        if (i<=0){
            return "订单开始";
        }
        else {
            return "订单生成失败";
        }


    }


    /**
     * 获取所有订单列表
     * */
    public List<Order_information> getAllOrders() {
        return orderDao.getAllOrders();
    }


    /**
     * 获取用户订单列表
     * */
    public List<Order_information> getUserOrders(String user_name) {
        return orderDao.find_Order_Username(user_name);
    }

    /**
     * 获取停车场订单列表
     * */
    public List<Order_information> getParkingOrders(String pctr_id) {
        return orderDao.find_Order_Parking(pctr_id);
    }

    /**
     * 根据订单号查找订单
     * */
    public Order_information getOrder(String order_number) {
        return orderDao.find_Order_number(order_number);
    }


}
