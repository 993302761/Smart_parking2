package com.saltfish.example.demo;

import org.csource.common.NameValuePair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class testDaoTest {
    @Autowired
    testDao t;
    @Test
    void getInfo() {
//        t.GetInfo("group1","M00/00/00/wKgKbGL48U6AakAUAAx_0JjZdK468.jpeg");
//        NameValuePair[] pair = t.GetMetaArr("group1","M00/00/00/wKgKbGL48U6AakAUAAx_0JjZdK468.jpeg");
//        NameValuePair p = t.GetMeta("group1","M00/00/00/wKgKbGL48U6AakAUAAx_0JjZdK468.jpeg");
//        System.out.println(p.getName()+"-------"+p.getValue());
//        String res =t.uploadFile("/home/saltfish/图片/bg3.jpeg");
//        System.out.println(res);
//       if (t.downxxx("/group1/M00/00/00/wKgKbGL5_aiAZplAAAuzTPPOXv840.jpeg","/home/saltfish/test"))
//           System.out.println("下载完成");

//        if (t.Delxxx("/group1/M00/00/00/wKgKbGLz1GKADw7RAAx_0JjZdK402.jpeg"))
//            System.out.println("删除成功");
        System.out.println(t.uploadFile("/home/lyq/图片/123.png"));





    }
}