package com.example.parkingLots.serviceImpl;

import com.example.parkingLots.dao.ParkingLotDao;
import com.example.parkingLots.entity.Parking_lot_information;


import com.feign.api.entity.parkingLots.Parking;
import com.feign.api.entity.parkingLots.Parking_for_user;
import com.feign.api.service.OrderFeignService;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.context.annotation.Import;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Import({
        com.feign.api.service.ParkingLotFeignServiceDegradation.class,
        com.feign.api.service.OrderFeignServiceDegradation.class
})
public class ParkingLotServiceImpl {

    @Resource
    private ParkingLotDao parkingLotDao;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private OrderFeignService orderFeignService;



    /**
     * TODO：新增一个停车场
     * @param pctr_id 停车场管理员账号
     * @param pctr_password 停车场管理员密码
     * @param parking_lot_name 停车场名
     * @param parking_in_the_city 停车场所在城市
     * @param parking_spaces_num 停车场总车位数
     * @param billing_rules 定价
     * @param longitude 经度
     * @param latitude 维度
     * @return 是否成功
     */
    public String add_Parking(String pctr_id,
                              String pctr_password,
                              String parking_lot_name,
                              String parking_in_the_city,
                              Integer parking_spaces_num,
                              float billing_rules,
                              String longitude,
                              String latitude) {
        if (pctr_id==null || pctr_password==null || parking_lot_name==null || parking_in_the_city==null || parking_spaces_num==null || longitude==null || latitude==null) {
            return "请输入完整信息";
        }
        Parking_lot_information parkingLotInformation = parkingLotDao.getParkingByPid(pctr_id);
        if (parkingLotInformation != null) {
            return "该停车场已注册";
        }

        //生成停车场编号
        String parking_lot_number=pctr_id+System.currentTimeMillis();


        int update = parkingLotDao.add_Parking(pctr_id, pctr_password, parking_lot_name, parking_in_the_city, parking_lot_number, parking_spaces_num, billing_rules, longitude, latitude);
        if (update > 0) {
            redisTemplate.opsForHash().put(parking_lot_number,"longitude",longitude);
            redisTemplate.opsForHash().put(parking_lot_number,"latitude",latitude);
            redisTemplate.opsForHash().put(parking_lot_number,"billing_rules",billing_rules);
            setParkingMassage();
            return "注册成功";
        } else {
            return "注册失败";
        }
    }





    /**
     * TODO：更新停车场信息
     * @param pctr_id 停车场管理员账号
     * @param parking_lot_number 停车场编号
     * @param parking_in_the_city 停车场所在城市
     * @param parking_spaces_num 停车场总车位数
     * @param billing_rules 定价
     * @return 是否成功
     */
    public String updateParking(String pctr_id,
                              String parking_lot_number,
                              String parking_in_the_city,
                              Integer parking_spaces_num,
                              float billing_rules) {
        if (pctr_id==null  || parking_lot_number==null || parking_in_the_city==null || parking_spaces_num==null) {
            return "请输入完整信息";
        }
        try {

            Boolean key = redisTemplate.hasKey(parking_lot_number);
            if (BooleanUtils.isFalse(key)) {
                return "该停车场未注册";
            }

            //redis互斥锁   解决缓存击穿
            Boolean absent = redisTemplate.opsForValue().setIfAbsent("updateParking", "1", 10, TimeUnit.SECONDS);
            if (BooleanUtils.isFalse(absent)) {
                Thread.sleep(50);
                return updateParking(pctr_id, parking_lot_number, parking_in_the_city, parking_spaces_num, billing_rules);
            }
            int i = parkingLotDao.updateParking(parking_in_the_city, parking_spaces_num, billing_rules, pctr_id);
            if (i == 1) {
                redisTemplate.opsForHash().delete(parking_lot_number, "billing_rules");
                return "更新成功";
            }
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }finally {
            //redis释放互斥锁
            redisTemplate.delete("updateParking");
        }
        return "更新失败";

    }





    /**
     * TODO：停车场管理员登录
     * @param pctr_id 停车场管理员账号
     * @param pctr_password 停车场管理员密码
     * @return 是否成功
     */
    public String login_Parking(String pctr_id, String pctr_password) {
        if (pctr_id==null||pctr_password==null){
            return "用户名或密码为空";
        }
        Parking_lot_information parkingLotInformation=parkingLotDao.getParkingByPid(pctr_id);
        if (parkingLotInformation==null){
            return "用户未注册";
        }
        if (parkingLotInformation.getPctr_password().equals(pctr_password)){
            return "登录成功";
        }else {
            return "密码错误";
        }
    }




    /**
     * TODO：查找停车场
     * @param parking_lot_name 停车场名
     * @param city 当前所在城市
     */
    public List<Parking_for_user> getParkingLot (String parking_lot_name, String city){
        List<Parking_for_user> parking_lot = get_parking_lot(city);
        for (int i = 0; i < parking_lot.size(); i++) {
            if (!parking_lot.get(i).getParking_lot_name().contains(parking_lot_name)){
                parking_lot.remove(i);
            }
        }
        return parking_lot;
    }


    /**
     * TODO：判断停车场是否存在
     * @param parking_lot_number 停车场编号
     */
    public int  findParkingLot  (String parking_lot_number ){
        return parkingLotDao.findParkingLot(parking_lot_number);
    }




    /**
     * TODO：车位情况变化
     * @param parking_lot_number 停车场编号
     * @param Available_place_num 当前可用停车位
     */
    public String change_parking_space(String parking_lot_number ,int Available_place_num){
        Parking parking_num = parkingLotDao.getParkingByPNumber(parking_lot_number);
        if (parking_num==null){
            return "无此停车场";
        }
        if (parking_num.getParking_spaces_num()<Available_place_num){
            return "数据错误";
        }
        redisTemplate.opsForHash().put(parking_lot_number,"Available_place_num",Available_place_num);
        return "可用车位数量变更";
    }





    /**
     * TODO：停车场取消订单
     * @param order_number 订单编号
     * @return 是否成功
     */
    public String parking_cancellation_Order (String parking_lot_number, String order_number){
        return orderFeignService.parking_cancellation_Order(parking_lot_number,order_number);
    }






    /**
     * TODO：获取某一区域停车场情况
     * @param city 所在城市
     * @return 某一区域停车场情况
     */
    public List<Parking_for_user> get_parking_lot(String city){
        if (city==null){
            return null;
        }

        List<Parking_for_user> parking_lot = parkingLotDao.get_parking_lot(city);
        if (parking_lot.isEmpty()){
            return null;
        }
        List<Parking_for_user> new_parking_lot=new ArrayList<>();
        for (int i = 0; i < parking_lot.size(); i++) {
            Parking_for_user p=parking_lot.get(i);
            Boolean hasKey = redisTemplate.hasKey(p.getParking_lot_number());
            if(BooleanUtils.isTrue(hasKey) ){
                Object  s =  redisTemplate.opsForHash().get(parking_lot.get(i).getParking_lot_number(),"Available_place_num");
                if (s==null){
                    s=0;
                }
                p.setAvailable_parking_spaces_num((int)s);
            }
            new_parking_lot.add(p);
        }
        return new_parking_lot;
    }








    /**
     * TODO：获取周边所有停车场
     * @param latitude 维度
     * @param longitude 经度
     * @return 停车场列表
     */
    public List<Parking_for_user> peripheralParking(String latitude, String longitude, String city) {
        if (latitude==null||longitude==null||city==null){
            return null;
        }else if (Double.parseDouble(latitude)<0||Double.parseDouble(latitude)>90.0){
            return null;
        }else if (Double.parseDouble(longitude)<0||Double.parseDouble(longitude)>180.0){
            return null;
        }
        Boolean key = redisTemplate.hasKey(city);
        if (BooleanUtils.isFalse(key)){
            setParkingMassage();
        }
        GeoResults<RedisGeoCommands.GeoLocation<Object>> radius = redisTemplate.opsForGeo().radius(
                city,
                new Circle(Double.parseDouble(longitude), Double.parseDouble(latitude),Metrics.KILOMETERS.getMultiplier()),
                RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(10)
        );
        List<GeoResult<RedisGeoCommands.GeoLocation<Object>>> content = radius.getContent();

        List<Parking_for_user> new_parking_lot=new ArrayList<>();
        content.forEach(result -> {
            //获取停车场编号
            String number = (String) result.getContent().getName();
            //获取停车场位置
            Boolean hasKey = redisTemplate.hasKey(number);
            if(BooleanUtils.isTrue(hasKey) ){
                Object  s =  redisTemplate.opsForHash().get(number,"Available_place_num");
                if (s!=null&&(int)s>0){
                    Parking_for_user parking1=parkingLotDao.getParkingForNearby(number);
                    parking1.setAvailable_parking_spaces_num((int)s);
                    new_parking_lot.add(parking1);
                }
            }
        });
        return new_parking_lot;
    }





    /**
     * TODO:更新Redis中的停车场位置信息
     */
    public void setParkingMassage(){
        //将停车场信息存入redis
        List<Parking> parkingList = getAllParking();
        //list关于城市分组
        Map<String, List<Parking>> map = parkingList.stream().collect(Collectors.groupingBy(Parking::getParking_in_the_city));
        for (Map.Entry<String, List<Parking>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<Parking> value = entry.getValue();
            List<RedisGeoCommands.GeoLocation<Object>> locations=new ArrayList<>(value.size());
            //写入redis
            for (Parking parking : value) {
                locations.add(new RedisGeoCommands.GeoLocation<>(
                        parking.getParking_lot_number(),
                        new Point(Double.parseDouble(parking.getLongitude()),Double.parseDouble(parking.getLatitude()))
                ));
            }
            redisTemplate.opsForGeo().add(key, locations);
        }
    }






    /**
     * TODO：根据停车场编号查找停车场名
     * @return 根据停车场编号查找停车场名
     */
    public String getParkingName(String parking_lot_number) {
        Boolean key = redisTemplate.hasKey(parking_lot_number);
        String name;
        if(BooleanUtils.isTrue(key)) {
            name = (String) redisTemplate.opsForHash().get(parking_lot_number, "parking_lot_name");
        }else {
            name = parkingLotDao.getParkingName(parking_lot_number);
            if (name == null) {
                redisTemplate.opsForValue().set(parking_lot_number, null, 5, TimeUnit.MINUTES);
                return null;
            }
            redisTemplate.opsForHash().put(parking_lot_number, "parking_lot_name", name);
        }
        return name;
    }





    /**
     * TODO：根据停车场编号获取停车场收费标准
     * @return 停车场收费标准
     */
    public String getParkingBilling_rules(String parking_lot_number) {
        float s= parkingLotDao.getParkingBilling_rules(parking_lot_number);
        return Float.toString(s);
    }


    /**
     * TODO：获取所有停车场列表
     * @return 所有停车场列表
     */
    public List<Parking> getAllParking() {
        return parkingLotDao.getAllParking();
    }




    /**
     * TODO：删除所有已注册停车场
     */
    public void delete_Parking (){
        parkingLotDao.delete_Parking();
    }



}
