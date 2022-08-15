package com.saltfish.example.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface GetFileInfo {
    String GroupParam();
    String RemoteParam();
}
