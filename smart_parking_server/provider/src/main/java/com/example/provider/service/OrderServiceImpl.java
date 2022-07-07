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
     * */
    public String generate_order(String user_name,String license_plate_number,String parking_lot_number) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String generation_time= formatter.format(date);

        //生成订单编号
        String order_number;
        Order_information order_information;
        do {
            order_number = user_name;
            order_number += String.valueOf((int) (Math.random() * 10000));
            order_information = orderDao.find_Order_number(order_number);
        } while (order_information != null);

        //判断停车场是否存在
        Parking_lot_information parkingLotInformation=parkingLotDao.find_Parking_num(parking_lot_number);
        if (parkingLotInformation==null){
            return "停车场不存在";
        }

        //检查车辆信息是否注册
        Vehicle_information vehicle_information=vehicleDao.check_license_plate_number(user_name,license_plate_number);
        if (vehicle_information==null){
            return "未注册车辆信息";
        }

        int i = orderDao.add_Order(order_number, generation_time, user_name, (String) null, (String) null, parkingLotInformation.getParking_lot_name(), parking_lot_number, license_plate_number, 0, false, "等待进入");
        if (i<=0){
            return "添加车辆信息失败";
        }
        else {
            return "添加车辆信息成功";
        }
    }


    /**
     * 获取所有用户列表
     * */
    public List<Order_information> getAllOrders() {
        return orderDao.getAllOrders();
    }


}
