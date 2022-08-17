package com.saltfish.example.aseptcut;

import com.saltfish.example.annotation.DFSUpdate;
import com.saltfish.example.service.FastDFSClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Aspect
public class UpdateAsept {
    @Pointcut(value = "@annotation(com.saltfish.example.annotation.DFSUpdate)")
    public void UpdateApi(){}



    @Around(value = "UpdateApi()")
    public Object Update(ProceedingJoinPoint point) throws Throwable {
        //获取调用注解的方法
        MethodSignature ms = (MethodSignature)point.getSignature();
        Method method = point.getTarget().getClass().getDeclaredMethod(ms.getName(), ms.getParameterTypes());
        DFSUpdate mi = method.getAnnotation(DFSUpdate.class);//获取自定义注解对象
        String IPPath = mi.Ipaddr();//获取注解成员信息
        String LocalPath = mi.LocalPath();
        //获取参数和函数参数名的对应map
        Map<String,Object> ParamMap = GetParamData(point);
        String Ipaddr = (String) ParamMap.get(IPPath);
        String Local  = (String) ParamMap.get(LocalPath);
        int ins = Ipaddr.indexOf("/",2);
        String groupname = Ipaddr.substring(1,ins);
        String MetaPath = Ipaddr.substring(ins+1);
        String[] strs = Local.split("/");
        String Filename = strs[strs.length-1];

        String res = (String) point.proceed();
        String[] info = FastDFSClient.modifyFile(groupname,MetaPath,new File(Local),Filename);
        res = "";
        for (String s : info) {
            res+="/"+s;
        }

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
