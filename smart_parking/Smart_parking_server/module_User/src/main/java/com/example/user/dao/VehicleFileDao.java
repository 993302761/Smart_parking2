package com.example.user.dao;

import com.saltfish.example.annotation.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class VehicleFileDao {


    //网站上传文件
    @DFSUpload(MultFile = "xxx")
    public String uploadMult(MultipartFile xxx){return null;}


    //删除文件
    @DFSDelete(IpAddr = "aaa")
    public boolean Delxxx(String aaa){return false;}


}
