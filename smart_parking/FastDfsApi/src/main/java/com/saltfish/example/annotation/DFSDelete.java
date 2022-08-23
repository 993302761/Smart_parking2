package com.saltfish.example.annotation;

import java.lang.annotation.*;



@Target(ElementType.METHOD)
//              说明了Annotation所修饰的对象范围,取值(ElementType)有：
//        　　　　1.CONSTRUCTOR:用于描述构造器
//        　　　　2.FIELD:用于描述域
//        　　　　3.LOCAL_VARIABLE:用于描述局部变量
//        　　　　4.METHOD:用于描述方法
//        　　　　5.PACKAGE:用于描述包
//        　　　　6.PARAMETER:用于描述参数
//        　　　　7.TYPE:用于描述类、接口(包括注解类型) 或enum声明
@Retention(RetentionPolicy.RUNTIME)
//          注解按生命周期来划分可分为3类：
//        1、RetentionPolicy.SOURCE：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；
//        2、RetentionPolicy.CLASS：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期；
//        3、RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在
@Documented
//      @Documented是元注解，可以修饰其他注解,可在生成文档后显示在被此注解修饰的类上（不完整）
@Inherited
//如果一个类用上了@Inherited修饰的注解，那么其子类也会继承这个注解
//  注意：
//    接口用上个@Inherited修饰的注解，其实现类不会继承这个注解
//    父类的方法用了@Inherited修饰的注解，子类也不会继承这个注解
public @interface DFSDelete {
    String IpAddr();
}
