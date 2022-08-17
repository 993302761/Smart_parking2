package com.example.user.dao;

import com.saltfish.example.annotation.UploadFileAddr;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;

@Repository
public class test {
    //上传文件
    @UploadFileAddr(FilePath = "path")
    public String uploadFile(String path){return null;}


}
