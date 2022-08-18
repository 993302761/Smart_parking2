package com.saltfish.example.service;

import org.apache.commons.fileupload.FileItem;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.FileInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.saltfish.example.service.FastDFSClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FastDFSClientTest {
    //上传单个图片
    //group1
    //M00/00/00/wKgKbGL48U6AakAUAAx_0JjZdK468.jpeg
    ///group1/M00/00/00/wKgKbGL5_aiAZplAAAuzTPPOXv840.jpeg
    @Test
    void uploadFile() throws FileNotFoundException {
        File f = new File("/home/saltfish/图片/145.png");
        FileItem fileItem = FastDFSClient.getMultipartFile(f, "145.png");
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        System.out.println(FastDFSClient.uploadFile_Mult(multipartFile));





//       String strs = FastDFSClient.uploadFile_P(new File("/home/saltfish/图片/bg3.jpeg"),"bg3.jpeg");
//        System.out.println(strs);
//        for (String str : strs) {
//            System.out.println(str);
//        }
    }

    @Test
    void testUploadFile() {
    }
    //查看文件详情
    @Test
    void getFileInfo() {
      FileInfo info = FastDFSClient.getFileInfo("group1","M00/00/00/wKgKbGL48U6AakAUAAx_0JjZdK468.jpeg");
        System.out.println(info.getSourceIpAddr());
        
    }

    @Test
    void getMetaData() {
        NameValuePair[] pair = FastDFSClient.getMetaData("group1","M00/00/00/wKgKbGL48U6AakAUAAx_0JjZdK468.jpeg");
        for (NameValuePair nameValuePair : pair) {
            System.out.println(nameValuePair.getName() + "---" + nameValuePair.getValue());
        }
    }

    @Test
    void downloadFile() {

        if (FastDFSClient.DownLoad("/group1/M00/00/00/wKgKbGL5_aiAZplAAAuzTPPOXv840.jpeg","/home/saltfish/test"))
            System.out.println("下载成功");
    }

    @Test
    void deleteFile() {
        String s = "/group1/M00/00/00/wKgKbGLxFUSAb98UAA6--s4YVLM954.jpg";
        FastDFSClient.deleteFile("group1","M00/00/00/wKgKbGLxFUSAb98UAA6--s4YVLM954.jpg");
    }


    //fileid = group1
    //fileid = M00/00/00/wKgKbGL6WA2AR_3cAAuzTPPOXv871.jpeg
    @Test
    void modifyFile() {
        String[] fileids = FastDFSClient.modifyFile("group1", "M00/00/00/wKgKbGL6WA2AR_3cAAuzTPPOXv871.jpeg",
                new File("/home/saltfish/图片/bg2.jpeg"), "bg2.jpeg");
        for (String fileid : fileids) {
            System.out.println("fileid = " + fileid);
        }
    }
}