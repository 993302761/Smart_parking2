package com.saltfish.example.aseptcut;

import com.saltfish.example.annotation.DFSUpload;
import com.saltfish.example.annotation.UploadFileAddr;
import com.saltfish.example.service.FastDFSClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Aspect
public class UploadAsept {
    @Pointcut(value = "@annotation(com.saltfish.example.annotation.UploadFileAddr)")
    public void LoadPoint(){ }


    @Pointcut(value = "@annotation(com.saltfish.example.annotation.DFSUpload)")
    public void UploadMultPoint(){}



    @Around(value = "UploadMultPoint()")
    public Object UploadMult(ProceedingJoinPoint point) throws Throwable {
        MethodSignature ms = (MethodSignature)point.getSignature();
        Method method = point.getTarget().getClass().getDeclaredMethod(ms.getName(), ms.getParameterTypes());
        DFSUpload mi = method.getAnnotation(DFSUpload.class);//获取自定义注解对象

        String fileData = mi.MultFile();//获取注解成员信息
        //获取参数和函数参数名的对应map
        Map<String,Object> ParamMap = GetParamData(point);
        MultipartFile file = (MultipartFile) ParamMap.get(fileData);
        String res ;
        res = FastDFSClient.uploadFile_Mult(file);
        return res;
    }





    @Around(value = "LoadPoint()")
    public Object UploadAdrr(ProceedingJoinPoint point) throws Throwable {
        //获取调用注解的方法
        MethodSignature ms = (MethodSignature)point.getSignature();
        Method method = point.getTarget().getClass().getDeclaredMethod(ms.getName(), ms.getParameterTypes());
        UploadFileAddr mi = method.getAnnotation(UploadFileAddr.class);//获取自定义注解对象

        String GroupName = mi.FilePath();//获取注解成员信息
        //获取参数和函数参数名的对应map
        Map<String,Object> ParamMap = GetParamData(point);
        String Params = (String)ParamMap.get(GroupName);
        String[] strs = Params.split("/");
        String Filename = strs[strs.length-1];
        Object res = point.proceed();
        File f = new File(Params);
        String info = FastDFSClient.uploadFile_P(f,Filename);
        System.out.println(info);
        res = info;
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
