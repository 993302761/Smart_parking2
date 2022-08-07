package com.example.administrators.service;

import com.example.administrators.dao.AdministratorsDao;
import com.example.administrators.entity.Administrators;
import com.feign.api.service.OrderFeignService;
import com.feign.api.service.ParkingLotFeignService;
import com.feign.api.service.UserFeignService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdministratorsServiceImpl {

    @Resource
    private AdministratorsDao administratorsDao;

    @Resource
    private UserFeignService userFeignService;

    @Resource
    private ParkingLotFeignService parkingLotFeignService;

    @Resource
    private OrderFeignService orderFeignService;




    /**
     * TODO：超级管理员登录
     * @param ctr_id 超级管理员账号
     * @param ctr_password 超级管理员密码
     * @return 是否成功
     */
    public String login_Ctl(String ctr_id, String ctr_password) {
        if (ctr_id==null||ctr_password==null){
            return "用户名或密码为空";
        }
        Administrators controller=administratorsDao.find_Adm(ctr_id);
        if (controller==null){
            return "用户未注册";
        }
        if (controller.getCtr_password().equals(ctr_password)){
            return "登录成功";
        }else {
            return "密码错误";
        }
    }



    /**
     * TODO：获取用户列表
     * @return 用户列表
     */
    public Object getAllUsers() {
        Object users=userFeignService.getAllUsers();
        return users;
    }


    /**
     * TODO：获取停车场列表
     * @return 获取停车场列表
     */
    public Object getAllParking() {
        Object parking=parkingLotFeignService.getAllParking();
        return parking;
    }


    /**
     * TODO：获取订单列表
     * @return 获取订单列表
     */
    public Object getAllOrder() {
        Object order=orderFeignService.getAllOrders();
        return order;
    }


}
