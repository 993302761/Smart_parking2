package com.example.vehicle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TestContoller {
    //跳到网页
    @GetMapping("/load")
    public String index(){
        return  "/upload.html";
    }
}
