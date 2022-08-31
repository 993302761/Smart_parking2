package com.example.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/a")

public class TestContoller {
    //跳到网页
    @RequestMapping("/a")
    public String index(){
        return  "/upload.html";
    }

    //跳到网页
    @RequestMapping("/b")
    public String index2(){
        return  "/upload2.html";
    }


}
