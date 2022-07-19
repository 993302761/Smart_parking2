package com.example.administrators.service;

import com.example.administrators.dao.AdministratorsDao;
import com.example.administrators.entity.Administrators;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

@Service
public class AdministratorsServiceImpl {

    @Resource
    private AdministratorsDao administratorsDao;

    @Resource
    private RestTemplate restTemplate;

    private final String userURl="http://www.localhost:9004/User";

    private final String orderURl="http://www.localhost:9001/Order";

    private final String parkingLotURl="http://www.localhost:9002/ParkingLots";

    private final String vehicleURl="http://www.localhost:9005/Vehicle";


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
        String classUrl=userURl+"/getAllUsers";
        Object user=restTemplate.getForObject(classUrl,Object.class);
        return user;
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
        String classUrl=orderURl+"/getAllOrder";
        Object order=restTemplate.getForObject(classUrl,Object.class);
        return order;
    }


}
