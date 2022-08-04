package com.example.administrators.service.Impl;

import com.example.administrators.dao.AdministratorsDao;
import com.example.administrators.entity.Administrators;

import com.example.administrators.service.UserFeignService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class AdministratorsServiceImpl {

    @Resource
    private AdministratorsDao administratorsDao;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private UserFeignService userFeignService;

    private final String userURl="http://ClientUser/User";

    private final String orderURl="http://ClientOrder/Order";

    private final String parkingLotURl="http://ClientParkingLots/ParkingLots";


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
        String classUrl=parkingLotURl+"/getAllParking";
        Object parking=restTemplate.getForObject(classUrl,Object.class);
        return parking;
    }


    /**
     * TODO：获取订单列表
     * @return 获取订单列表
     */
    public Object getAllOrder() {
        String classUrl=orderURl+"/getAllOrders";
        Object order=restTemplate.getForObject(classUrl,Object.class);
        return order;
    }


}
