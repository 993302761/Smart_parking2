package com.example.user.serviceImpl;


import com.example.user.dao.UserDao;
import com.example.user.entity.User_information;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feign.api.entity.user.User;
import com.feign.api.service.ParkingLotFeignService;
import com.feign.api.service.VehicleFeignService;
import com.saltfish.example.demo.VehicleFileDao;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
@Import({
        com.feign.api.service.ParkingLotFeignServiceDegradation.class,
        com.feign.api.service.VehicleFeignServiceDegradation.class,
        com.saltfish.example.demo.VehicleFileDao.class,
        com.saltfish.example.aseptcut.UploadAsept.class
})
public class UserServiceImpl  {

    @Resource
    private UserDao userDao;


    @Resource
    private VehicleFileDao vehicleFileDao;


    @Resource
    private VehicleFeignService vehicleFeignService;


    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ParkingLotFeignService parkingLotFeignService;


    //json序列化工具
    private final static ObjectMapper mapper=new ObjectMapper();





    /**
     * TODO：添加一名用户
     * @param user_name 用户名
     * @param password 密码
     * @param user_id 身份证号码
     * @param license_plate_number 车牌号
     * @param vehicle_photos 车辆照片
     * @param registration 机动车登记证照片
     * @param driving_permit 车辆行驶证照片
     * @return 是否成功
     */
    public String add_User(String user_name,
                           String password,
                           String user_id,
                           String license_plate_number,
                           MultipartFile vehicle_photos,
                           MultipartFile registration,
                           MultipartFile driving_permit) throws IOException {

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
            s.append("注册成功  ");
        }
        s.append("车辆信息：");


        try {
            String vehicle=vehicle_binding(user_name,user_id,license_plate_number,vehicle_photos,registration,driving_permit);
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
                                   MultipartFile vehicle_photos,
                                   MultipartFile registration,
                                   MultipartFile driving_permit){

        int vehicleNumber = vehicleFeignService.check_license_plate_number(user_name, license_plate_number);
        if (vehicleNumber==1){
            return "该车辆已注册，请勿重复注册";
        }else if (vehicleNumber>1){
            return "数据错误";
        }else if (vehicleNumber<0){
            return "访问错误";
        }

        String vehicle_photos_address = vehicleFileDao.addVehicleFile(vehicle_photos);
        String registration_address = vehicleFileDao.addVehicleFile(registration);
        String driving_permit_address = vehicleFileDao.addVehicleFile(driving_permit);
        if (vehicle_photos_address==null||registration_address==null||driving_permit_address==null){
            return "照片保存错误";
        }
        try {

            String s = vehicleFeignService.vehicle_binding(user_name, user_id, license_plate_number, vehicle_photos_address.replace('/', '&'), registration_address.replace('/', '&'), driving_permit_address.replace('/', '&'));
            return s;
        }catch (Exception e){
            vehicleFileDao.deleteVehicleFile(vehicle_photos_address.replace('/','&'));
            vehicleFileDao.deleteVehicleFile(registration_address.replace('/','&'));
            vehicleFileDao.deleteVehicleFile(driving_permit_address.replace('/','&'));
            e.printStackTrace();
            return "绑定信息失败";
        }
    }





    /**
     * TODO：删除绑定的车辆信息
     * @param user_name 用户名
     * @param license_plate_number 车牌号
     * @return 是否成功
     */
    public String deleteVehicle (String user_name, String license_plate_number){
        return vehicleFeignService.deleteVehicle(user_name,license_plate_number);
    }





    /**
     * TODO：获取用户绑定的车辆信息
     * @param user_name 用户名
     * @return 是否成功
     */
    public List<String> getUserVehicle (String user_name){
        return vehicleFeignService.getUserVehicle(user_name);
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
        int user = userDao.check_User(user_name);
        if (user==0){
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
        return parkingLotFeignService.getParkingLot(parking_lot_name,city);
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
        String key=md5(user_name+UUID);
        boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            return true;
        } else {
            return false;
        }
    }


    /**
     * TODO：Md5加密（加盐）
     * @param data 字符串
     * @return 加密后的编码
     */
    public static String md5(String data){
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest messageDigest=MessageDigest.getInstance("md5");
            byte[] digest = messageDigest.digest(data.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b:digest){
                int num=b&0xff;
                String s=Integer.toHexString(num);
                if (s.length()==1){
                    stringBuffer.append("0");
                }
                stringBuffer.append(s);
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
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
        String key=md5(user_name+UUID);
        redisTemplate.opsForValue().set(key, 0);
        Boolean expire = redisTemplate.expire(key, second, TimeUnit.SECONDS);
        return expire;
    }






    /**
     * TODO：获取用户列表
     * @return 用户列表
     */
    public List<User> getAllUsers() {
        List<User> users = userDao.getAllUsers();
        List<User> newUsers=new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            List<String> vehicle=vehicleFeignService.getUserVehicle(users.get(i).getUser_name());
            User t=users.get(i);
            t.setVehicle(vehicle);
            newUsers.add(t);
        }
        return newUsers;
    }


    /**
     * TODO：从redis中获取对象
     * @param key
     */
    public Object getRedisValue (String key,Class clazz) throws JsonProcessingException {
        String value= (String) redisTemplate.opsForValue().get(key);
        //反序列化
        Object userInformation=mapper.readValue(value,clazz);
        return userInformation;
    }



    /**
     * TODO：删除用户
     */
    public String delete_User (String user_name,String UUID){
        userDao.delete_User(user_name);
        String key=md5(user_name+UUID);
        redisTemplate.delete(key);
        return vehicleFeignService.deleteAllVehicle(user_name);
    }




}
