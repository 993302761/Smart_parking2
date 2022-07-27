package com.example.vehicle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/Loaf")

public class TestContoller {
    //跳到网页
    @RequestMapping("/load")
    public String index(){
        return  "/upload.html";
    }
}
