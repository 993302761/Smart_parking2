package com.saltfish.example.demo;

import com.saltfish.example.service.FastDFSClient;
import org.apache.commons.fileupload.FileItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;

@SpringBootTest
class testDaoTest {
    @Autowired
    VehicleFileDao t;
    @Test
    void getInfo() {
    }

    @Test
    void getMetaArr() {
    }

    @Test
    void uploadFile() {
        File f = new File("/home/saltfish/图片/145.png");
        FileItem fileItem = FastDFSClient.getMultipartFile(f, "145.png");
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

        System.out.println(t.uploadMult(multipartFile));
    }

    @Test
    void downxxx() {
    }

    @Test
    void delxxx() {
    }

    @Test
    void updataexax() {
    }
}