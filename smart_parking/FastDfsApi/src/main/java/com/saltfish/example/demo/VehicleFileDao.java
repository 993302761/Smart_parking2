package com.saltfish.example.demo;

import com.saltfish.example.annotation.*;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.FileInfo;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class VehicleFileDao {

    //获取文件信息
    @GetFileInfo(GroupParam = "aaa",RemoteParam = "bbb")
    public FileInfo GetInfo(String aaa,String bbb){
        return null;
    }
    //获取文件元数据
    @GetFileMetaArr(GroupParam = "aaa",RemoteParam = "bbb")
    public NameValuePair[] GetMetaArr(String aaa,String bbb){return null;}
    //本地上传文件
    @UploadFileAddr(FilePath = "path")
    public String uploadFile(String path){return null;}
    //网站上传文件
    @DFSUpload(MultFile = "xxx")
    public String addVehicleFile(MultipartFile xxx){return null;}
    //下载文件
    @DFSDownLoad(DFSPath = "aaa",DownPath = "bbb")
    public boolean downxxx(String aaa,String bbb){return false;}
    //删除文件
    @DFSDelete(IpAddr = "aaa")
    public boolean deleteVehicleFile(String aaa){return false;}
    //更换文件
    @DFSUpdate(Ipaddr = "aaa",LocalPath = "bbb")
    public String Updataexax(String aaa,String bbb){return "";}
}
