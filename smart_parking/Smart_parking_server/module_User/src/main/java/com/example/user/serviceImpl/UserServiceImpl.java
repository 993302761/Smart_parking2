package com.example.user.serviceImpl;


import com.example.user.dao.UserDao;
import com.example.user.entity.User_information;
import com.feign.api.entity.user.User;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class UserServiceImpl  {

    @Resource
    private UserDao userDao;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private ParkingLotFeignService parkingLotFeignService;

    private final String vehicleURl="http://ClientVehicle/Vehicle";







    /**
     * TODO：添加一名用户
     * @param user_name 用户名
     * @param password 密码
     * @param user_id 身份证号码
     * @param license_plate_number 车牌号
     * @param vehicle_photos 车辆照片
     * @param registration 机动车登记证照片
     * @param driving_permit 车辆行驶证照片
     * @param vehicle_photos_suffix 车辆照片后缀
     * @param registration_suffix 机动车登记证照片后缀
     * @param driving_permit_suffix 车辆行驶证照片后缀
     * @return 是否成功
     */
    public String add_User(String user_name,
                           String password,
                           String user_id,
                           String license_plate_number,
                           byte[] vehicle_photos,
                           byte[] registration,
                           byte[] driving_permit,
                           String vehicle_photos_suffix,
                           String registration_suffix,
                           String driving_permit_suffix) throws IOException {

        StringBuilder s=new StringBuilder("用户:");

        if (user_name==null||password==null||user_id==null){
            return "所填信息不完整";
        }
        int user = userDao.check_User(user_name);
        if (user==1){
            return "用户已注册";
        }else if (user!=0){
            return "数据错误";
        }
        int i= userDao.add_User(user_name,password,user_id);
        if (i<=0){
            return "注册失败";
        }
        else {
            s.append("注册成功");
        }
        s.append("车辆信息：");


        try {
            String vehicle=vehicle_binding(user_name,user_id,license_plate_number,vehicle_photos,registration,driving_permit,vehicle_photos_suffix,registration_suffix,driving_permit_suffix);
            s.append(vehicle);
            return s.toString();
        }catch (Exception e){
            userDao.delete_User(user_name);
            e.printStackTrace();
            return "错误";
        }



    }






    /**
     * TODO：添加绑定车辆
     * @param user_name 用户名
     * @param user_id 身份证号码
     * @param license_plate_number 车牌号
     * @param vehicle_photos 车辆照片
     * @param registration 机动车登记证照片
     * @param driving_permit 车辆行驶证照片
     * @return 是否成功
     */
    public String vehicle_binding (String user_name,
                                   String user_id,
                                   String license_plate_number,
                                   byte[] vehicle_photos,
                                   byte[] registration,
                                   byte[] driving_permit,
                                   String vehicle_photos_suffix,
                                   String registration_suffix,
                                   String driving_permit_suffix){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(9);
        params.add("user_name"  , user_name);
        params.add("user_id" ,user_id);
        params.add("license_plate_number" ,license_plate_number);
        params.add("vehicle_photos" , Base64.encodeBase64String(vehicle_photos));
        params.add("registration" ,Base64.encodeBase64String(registration));
        params.add("driving_permit" ,Base64.encodeBase64String(driving_permit));
        params.add("vehicle_photos_suffix" ,vehicle_photos_suffix.substring(vehicle_photos_suffix.lastIndexOf(".")));
        params.add("registration_suffix" ,registration_suffix.substring(registration_suffix.lastIndexOf(".")));
        params.add("driving_permit_suffix" ,driving_permit_suffix.substring(driving_permit_suffix.lastIndexOf(".")));


        String url=vehicleURl+"/vehicle_binding";
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<>(params, headers);


        try {
            //服务间传输数据
            String vehicle=restTemplate.postForObject(url,requestEntity,String.class);
            return vehicle;
        }catch (Exception e){
            e.printStackTrace();
            return "错误";
        }
    }





    /**
     * TODO：删除绑定的车辆信息
     * @param user_name 用户名
     * @param license_plate_number 车牌号
     * @return 是否成功
     */
    public ResponseEntity<String> deleteVehicle (String user_name, String license_plate_number){
        String url=vehicleURl+"/deleteVehicle/"+user_name+"/"+license_plate_number;
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        return exchange;
    }





    /**
     * TODO：获取用户绑定的车辆信息
     * @param user_name 用户名
     * @return 是否成功
     */
    public Object getUserVehicle (String user_name){
        String url=vehicleURl+"/getUserVehicle/"+user_name;
        Object s=restTemplate.getForObject(url,Object.class);
        return s;
    }







    /**
     * TODO：用户登录
     * @param user_name 用户名
     * @param password 密码
     * @param UUID 通用唯一识别码
     * @return 是否成功
     */
    public String login_User(String user_name, String password,String UUID) {
        if (user_name==null||password==null){
            return "用户名或密码为空";
        }
        User_information user=userDao.find_User(user_name);
        if (user==null){
            return "用户未注册";
        }
        if (user.getPassword().equals(password)){
            set_UUID(UUID,user_name);
            boolean b = set_UUID(UUID, user_name);
            if (!b){
                System.out.println(user_name+"：Redis设置失败");
            }
            return "登录成功";
        }else {
            return "密码错误";
        }
    }





    /**
     * TODO：查找用户
     * @param user_name 用户名
     * @return 是否成功
     */
    public boolean find(String user_name){
        User_information user=userDao.find_User(user_name);
        if (user==null){
            return true;
        }
        else return false;
    }




    /**
     * TODO：查找停车场
     * @param parking_lot_name 停车场名
     * @param city 当前所在城市
     */
    public Object getParkingLot (String parking_lot_name,String city){
        Object ParkingLot =parkingLotFeignService.getParkingLot(parking_lot_name,city);
        return ParkingLot;
    }





    /**
     * TODO：获取用户身份证
     * @param user_name 用户名
     * @return 用户身份证
     */
    public String getUserId(String user_name){
        return userDao.getUserId(user_name);
    }






    /**
     * TODO：UUID校验
     * @param user_name 用户名
     * @param UUID 通用唯一识别码
     * @return 是否成功
     */
    public boolean check_UUID(String UUID,String user_name){
        boolean hasKey = redisTemplate.hasKey(user_name);
        if(hasKey){
            String s = redisTemplate.opsForValue().get(user_name);
            if (s.equals(UUID)){
                boolean b = set_UUID(UUID, user_name);
                if (!b){
                    System.err.println("Redis设置失败");
                }
                return true;
            }else {
                redisTemplate.delete(user_name);
                return false;
            }
        } else {
            return false;
        }
    }





    /**
     * TODO：UUID设置
     * @param user_name 用户名
     * @param UUID 通用唯一识别码
     * @return 是否成功
     */
    public boolean set_UUID(String UUID,String user_name){
        //设置过期时间为一个月
        Calendar curDate = Calendar.getInstance();
        Calendar nextDate = new GregorianCalendar(curDate.get(Calendar.YEAR),
                curDate.get(Calendar.MONTH) + 1,
                curDate.get(Calendar.DAY_OF_MONTH),
                curDate.get(Calendar.HOUR_OF_DAY),
                curDate.get(Calendar.MINUTE),
                curDate.get(Calendar.SECOND));
        long second = (nextDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;
        redisTemplate.opsForValue().set(user_name, UUID);
        Boolean expire = redisTemplate.expire(user_name, second, TimeUnit.SECONDS);
        return expire;
    }






    /**
     * TODO：获取用户列表
     * @return 用户列表
     */
    public List<User> getAllUsers() {
        List<User> users = userDao.getAllUsers();
        List<User> newUsers=new ArrayList<>();
        StringBuilder s=new StringBuilder();
        for (int i = 0; i < users.size(); i++) {
            s.append(vehicleURl+"/getUserVehicle/"+users.get(i).getUser_name());
            Object vehicle=restTemplate.getForObject(s.toString(),Object.class);
            User t=users.get(i);
            t.setVehicle(vehicle);
            newUsers.add(t);
            s.delete(0,s.length());
        }
        return newUsers;
    }


    /**
     * TODO：获取用户列表
     * @return 用户列表
     */
    public List<User> getAllUsers2() {
        List<User> users = userDao.getAllUsers();
        return users;
    }



    /**
     * TODO：删除所有用户
     */
    public void delete_User (String user_name){
        userDao.delete_User(user_name);
    }




}
