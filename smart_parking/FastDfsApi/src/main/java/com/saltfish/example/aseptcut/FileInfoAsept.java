package com.saltfish.example.aseptcut;

import com.saltfish.example.annotation.GetFileInfo;
import com.saltfish.example.annotation.GetFileMeta;
import com.saltfish.example.annotation.GetFileMetaArr;
import com.saltfish.example.service.FastDFSClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.FileInfo;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Aspect
public class FileInfoAsept {
    /**
     * 获取文件信息
     */
    @Pointcut(value = "@annotation(com.saltfish.example.annotation.GetFileInfo)")
    public void InfoPointcut(){
    }

    /**
     * 获取一组文件元数据
     */
    @Pointcut(value = "@annotation(com.saltfish.example.annotation.GetFileMetaArr)")
    public void MetaArrPointcut(){
    }

    /**
     * 获取单个文件元数据
     */
    @Pointcut(value = "@annotation(com.saltfish.example.annotation.GetFileMeta)")
    public void MetaPointcut(){

    }

    /**
     * 获取文件信息切面实现方法
     * @param point
     * @return
     * @throws Throwable
     */
    @Around(value = "InfoPointcut()")
    public Object doInfo(ProceedingJoinPoint point) throws Throwable {
        //获取调用注解的方法
        MethodSignature ms = (MethodSignature)point.getSignature();
        Method method = point.getTarget().getClass().getDeclaredMethod(ms.getName(), ms.getParameterTypes());
        GetFileInfo mi = method.getAnnotation(GetFileInfo.class);//获取自定义注解对象

        String GroupName = mi.GroupParam();//获取注解成员信息
        String RemoteName = mi.RemoteParam();
        //获取参数和函数参数名的对应map
        Map<String,Object> ParamMap = GetParamData(point);

        Object res = point.proceed();
        FileInfo info = FastDFSClient.getFileInfo((String)ParamMap.get(GroupName),(String)ParamMap.get(RemoteName));
        System.out.println(info.toString());

        res = info;
        return res;
    }


    /**
     * 获取成组的文件元数据  长度&原文件名称
     * @param point
     * @return
     * @throws Throwable
     */
    @Around(value = "MetaArrPointcut()")
    public Object doMetaArr(ProceedingJoinPoint point) throws Throwable {
        //获取调用注解的方法
        MethodSignature ms = (MethodSignature)point.getSignature();
        Method method = point.getTarget().getClass().getDeclaredMethod(ms.getName(), ms.getParameterTypes());
        GetFileMetaArr mi = method.getAnnotation(GetFileMetaArr.class);//获取自定义注解对象

        String GroupName = mi.GroupParam();//获取注解成员信息
        String RemoteName = mi.RemoteParam();
        //获取参数和函数参数名的对应map
        Map<String,Object> ParamMap = GetParamData(point);

        Object res = point.proceed();
        NameValuePair[] info = FastDFSClient.getMetaData((String)ParamMap.get(GroupName),(String)ParamMap.get(RemoteName));
        System.out.println(info.toString());
        res = info;
        return res;
    }


    /**
     * 获取单个文件元数据  长度&原文件名称
     * @param point
     * @return
     * @throws Throwable
     */
    @Around(value = "MetaPointcut()")
    public Object doMeta(ProceedingJoinPoint point) throws Throwable {
        //获取调用注解的方法
        MethodSignature ms = (MethodSignature)point.getSignature();
        Method method = point.getTarget().getClass().getDeclaredMethod(ms.getName(), ms.getParameterTypes());
        GetFileMeta mi = method.getAnnotation(GetFileMeta.class);//获取自定义注解对象

        String GroupName = mi.GroupParam();//获取注解成员信息
        String RemoteName = mi.RemoteParam();
        //获取参数和函数参数名的对应map
        Map<String,Object> ParamMap = GetParamData(point);

        Object res = point.proceed();
        NameValuePair[] info = FastDFSClient.getMetaData((String)ParamMap.get(GroupName),(String)ParamMap.get(RemoteName));
        res = info[0];
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
