package com.example.user.service;


import com.example.user.dao.UserDao;
import com.example.user.entity.User_information;
import com.example.user.entity.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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



    private final String vehicleURl="http://ClientVehicle/Vehicle";



    private final String parkingLotURl="http://Client_ParkingLots/ParkingLots";

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
    public String add_User(String user_name, String password, String user_id, String license_plate_number, MultipartFile vehicle_photos, MultipartFile registration, MultipartFile driving_permit) {
        String s="用户:";

        if (user_name==null||password==null||user_id==null){
            s+= "所填信息不完整";
        }
        User_information user = userDao.find_User(user_name);
        if (user!=null){
            s+= "用户已注册";
        }
        int i= userDao.add_User(user_name,password,user_id);
        if (i<=0){
            s+="注册失败";
        }
        else {
            s+="注册成功";
        }
        s+=" 车辆信息：";
        String url=vehicleURl+"/vehicle_binding/"+user_name+"/"+user_id+"/"+license_plate_number+"/"+vehicle_photos+"/"+registration+"/"+driving_permit;
        String vehicle=restTemplate.getForObject(url,String.class);
        s+=vehicle;
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
        String url=parkingLotURl+"/getParkingLot/"+parking_lot_name+"/"+city;
        Object ParkingLot =restTemplate.getForObject(url,Object.class);
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
                    System.out.println("Redis设置失败");
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
        String url=vehicleURl+"/getUserVehicle";
        for (int i = 0; i < users.size(); i++) {
            String s=url+"/"+users.get(i).getUser_name();
            Object vehicle=restTemplate.getForObject(s,Object.class);
            User t=users.get(i);
            t.setVehicle(vehicle);
            newUsers.add(t);
        }
        return newUsers;
    }




    /**
     * TODO：删除所有用户
     */
    public void delete_User (){
        userDao.delete_User();
    }



}
