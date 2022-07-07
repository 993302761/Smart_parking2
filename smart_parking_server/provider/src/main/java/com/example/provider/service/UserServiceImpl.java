package com.example.provider.service;

import com.example.provider.dao.UserDao;
import com.example.provider.entiry.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
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


    /**
     * 添加一名用户
     * */
    public String add_User(String user_name, String password, String user_id) {
        if (user_name==null||password==null||user_id==null){
            return "所填信息不完整";
        }
        User user = userDao.find_User(user_name);
        if (user!=null){
            return "用户已注册";
        }
        int i= userDao.add_User(user_name,password,user_id);
        if (i<=0){
            return "注册失败";
        }
        else {
            return "注册成功";
        }
    }


    /**
     * 用户登录
     * */
    public String login_User(String user_name, String password,String UUID) {
        if (user_name==null||password==null){
            return "用户名或密码为空";
        }
        User user=userDao.find_User(user_name);
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
     * 查找用户
     * */
    public boolean find(String user_name){
        User user=userDao.find_User(user_name);
        if (user==null){
            return true;
        }
        else return false;
    }



    /**
     * UUID校验
     * */
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
     * 设置UUID
     * */
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
     * 获取用户总量
     * */
    public Integer getAllUsersNumber() {
        return userDao.getAllUsersNumber();
    }


    /**
     * 获取所有用户列表
     * */
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

}
