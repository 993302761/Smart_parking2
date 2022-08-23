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

@Component  //把普通pojo实例化到spring容器中
@Aspect         //作用是把当前类标识为一个切面供容器读取
public class DeleteAsept {

    //是指那些方法需要被执行"AOP"
    //    execution()：用于匹配方法执行的连接点
    //    args(): 用于匹配当前执行的方法传入的参数为指定类型的执行方法
    //    this(): 用于匹配当前AOP代理对象类型的执行方法；注意是AOP代理对象的类型匹配，这样就可能包括引入接口也类型匹配；
    //    target(): 用于匹配当前目标对象类型的执行方法；注意是目标对象的类型匹配，这样就不包括引入接口也类型匹配；
    //    within(): 用于匹配指定类型内的方法执行；
    //    @args():于匹配当前执行的方法传入的参数持有指定注解的执行；
    //    @target():用于匹配当前目标对象类型的执行方法，其中目标对象持有指定的注解；
    //    @within():用于匹配所以持有指定注解类型内的方法；
    //    @annotation:用于匹配当前执行方法持有指定注解的方法；
    @Pointcut(value = "@annotation(com.saltfish.example.annotation.DFSDelete)")
    public void DelApi(){}



    //既可以在目标方法之前织入增强动作，也可以在执行目标方法之后织入增强动作；
    //可以决定目标方法在什么时候执行，如何执行，甚至可以完全阻止目标目标方法的执行；
    //可以改变执行目标方法的参数值，也可以改变执行目标方法之后的返回值； 当需要改变目标方法的返回值时，只能使用Around方法；
    //
    //    虽然Around功能强大，但通常需要在线程安全的环境下使用。
    //    因此，如果使用普通的Before、AfterReturing增强方法就可以解决的事情，就没有必要使用Around增强处理了。
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
