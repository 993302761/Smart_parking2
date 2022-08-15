package com.saltfish.example.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface GetFileMeta {
    String GroupParam();
    String RemoteParam();
}
