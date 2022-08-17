package com.saltfish.example.aseptcut;

import com.saltfish.example.annotation.DFSDownLoad;
import com.saltfish.example.service.FastDFSClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Aspect
public class DownLoadAsept {
    @Pointcut(value = "@annotation(com.saltfish.example.annotation.DFSDownLoad)")
    public void DownApi(){}
    @Around("DownApi()")
    public boolean Down(ProceedingJoinPoint point) throws Throwable {
        //获取调用注解的方法
        MethodSignature ms = (MethodSignature)point.getSignature();
        Method method = point.getTarget().getClass().getDeclaredMethod(ms.getName(), ms.getParameterTypes());
        DFSDownLoad mi = method.getAnnotation(DFSDownLoad.class);//获取自定义注解对象

        String DFSPath = mi.DFSPath();//获取注解成员信息
        String DownPath = mi.DownPath();
        //获取参数和函数参数名的对应map
        Map<String,Object> ParamMap = GetParamData(point);
        String Ipaddr = (String) ParamMap.get(DFSPath);
        String Down = (String) ParamMap.get(DownPath);
        boolean res = FastDFSClient.DownLoad(Ipaddr,Down);
        return res;
    }

    private Map<String,Object> GetParamData(ProceedingJoinPoint point){
        Map<String,Object> res = new ConcurrentHashMap<>();

        Object[] sres = point.getArgs();
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] names = methodSignature.getParameterNames();
        for (int i = 0;i < names.length;i++){
            if (sres[i] == null)sres[i] = "";
            res.put(names[i],sres[i]);
        }
        return res;
    }



}
