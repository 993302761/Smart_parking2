package com.example.provider.service.aop;


import com.example.provider.service.UserServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;

@Component    //把切面类加入到IOC容器中
@Aspect        //使之成为切面类
@Order(1)	//如果有多个 可以定义来控制顺序 数字越小执行顺序靠前
public class ServiceAop {

    @Autowired(required = false)
    private UserServiceImpl userService;

    @Before(value = "execution(* com.example.provider.controller.OrderController.*(..))")
    public String beforeAdvice(JoinPoint joinPoint) {
        String UUID = null;
        String user_name = null;
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames(); // 参数名
        for (int i = 0; i < argNames.length; i++) {
            if (argNames[i].equals("UUID")){
                UUID=argNames[i];
            }else if (argNames[i].equals("user_name")){
                user_name=argNames[i];
            }
        }
        if (UUID==null||user_name==null){
            return "参数错误";
        }
        boolean b = userService.check_UUID(UUID, user_name);
        if (b) {
            return "ok";
        }else {
            return "重新登录";
        }
    }



}
