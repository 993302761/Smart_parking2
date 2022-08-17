package com.saltfish.example.aseptcut;

import com.saltfish.example.annotation.DFSDelete;
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
public class DeleteAsept {
    @Pointcut(value = "@annotation(com.saltfish.example.annotation.DFSDelete)")
    public void DelApi(){}

    @Around("DelApi()")
    public boolean DeleteAsp(ProceedingJoinPoint point) throws Throwable {

        MethodSignature ms = (MethodSignature)point.getSignature();
        Method method = point.getTarget().getClass().getDeclaredMethod(ms.getName(), ms.getParameterTypes());
        DFSDelete mi = method.getAnnotation(DFSDelete.class);//获取自定义注解对象
        String IpPath = mi.IpAddr();
        //获取参数和函数参数名的对应map
        Map<String,Object> ParamMap = GetParamData(point);
        String PathParam = (String) ParamMap.get(IpPath);
        int ins = PathParam.indexOf("/",2);
        String groupname = PathParam.substring(1,ins);
        String MetaPath = PathParam.substring(ins+1);
        boolean res = (boolean) point.proceed();
        if (FastDFSClient.deleteFile(groupname,MetaPath)==0)
            res = true;
        else
            res = false;
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
