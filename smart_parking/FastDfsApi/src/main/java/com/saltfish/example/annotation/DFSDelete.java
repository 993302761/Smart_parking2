package com.saltfish.example.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)     //注解用于字段上
@Retention(RetentionPolicy.RUNTIME)     //保留到运行时，可通过注解获取
@Documented
@Inherited
public @interface DFSDelete {
    String IpAddr();
}
