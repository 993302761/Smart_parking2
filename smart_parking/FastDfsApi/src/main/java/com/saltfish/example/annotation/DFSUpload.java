package com.saltfish.example.annotation;

import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DFSUpload {
    String MultFile();
}
