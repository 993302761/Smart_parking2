package com.example.administrators.serviceImpl;

import com.example.administrators.dao.AdministratorsDao;
import com.example.administrators.entity.Administrators;

import com.feign.api.service.OrderFeignService;
import com.feign.api.service.ParkingLotFeignService;
import com.feign.api.service.UserFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Import({
        com.feign.api.service.ParkingLotFeignServiceDegradation.class,
        com.feign.api.service.OrderFeignServiceDegradation.class,
        com.feign.api.service.UserFeignServiceDegradation.class
})
public class AdministratorsServiceImpl {

    @Resource
    private AdministratorsDao administratorsDao;

    @Resource
    private UserFeignService userFeignService;

    @Resource
    private ParkingLotFeignService parkingLotFeignService;

    @Resource
    private OrderFeignService orderFeignService;


    private static final Logger LOGGER= LoggerFactory.getLogger(AdministratorsServiceImpl.class);


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
        return userFeignService.getAllUsers();
    }


    /**
     * TODO：获取停车场列表
     * @return 获取停车场列表
     */
    public Object getAllParking() {
        return parkingLotFeignService.getAllParking();
    }


    /**
     * TODO：获取订单列表
     * @return 获取订单列表
     */
    public Object getAllOrder() {
        return orderFeignService.getAllOrders();
    }


    /**
     * TODO：更新停车场信息
     * @param pctr_id 停车场管理员账号
     * @param parking_lot_name 停车场名
     * @param parking_in_the_city 停车场所在城市
     * @param parking_spaces_num 停车场总车位数
     * @param billing_rules 定价
     * @return 是否成功
     */
    public String updateParking(String pctr_id, String parking_lot_name, String parking_in_the_city, Integer parking_spaces_num, float billing_rules) {
        return parkingLotFeignService.updateParking(pctr_id, parking_lot_name, parking_in_the_city, parking_spaces_num, billing_rules);
    }



    /**
     * TODO：超级管理员取消订单
     * @param order_number 订单编号
     * @return 是否成功
     */
    public String cancelOrder( String order_number) {
        return orderFeignService.cancelOrder(order_number);
    }
}
