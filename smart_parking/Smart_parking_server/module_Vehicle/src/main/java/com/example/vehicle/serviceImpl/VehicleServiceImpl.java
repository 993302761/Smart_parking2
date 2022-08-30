package com.example.vehicle.serviceImpl;


import com.example.vehicle.dao.VehicleDao;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.hibernate.Hibernate;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Service
public class VehicleServiceImpl {

    @Resource
    private VehicleDao vehicleDao;






    /**
     * TODO：添加一条车辆信息
     * @param user_name 用户名
     * @param user_id 身份证
     * @param license_plate_number 车牌号
     * @param vehicle_photos 车辆照片
     * @param registration 机动车登记证照片
     * @param driving_permit 车辆行驶证照片
     * @return 是否成功
     */
    public String add_Vehicle(String user_name, String user_id,String license_plate_number, String vehicle_photos,String registration,String driving_permit) {
        if (user_name==null||license_plate_number==null||user_id==null||vehicle_photos==null||registration==null||driving_permit==null){
            return "所填信息不完整";
        }
        int vehicleNumber = check_license_plate_number(user_name, license_plate_number);
        if (vehicleNumber==1){
            return "该车辆已注册，请勿重复注册";
        }else if (vehicleNumber>1){
            return "数据错误";
        }else if (vehicleNumber<0){
            return "错误：601";
        }

        int i=vehicleDao.add_Vehicle(
                user_name,
                user_id,
                license_plate_number,
                vehicle_photos,
                registration,
                driving_permit);
        if (i<=0){
            return "添加车辆信息失败";
        }
        else {
            return "添加车辆信息成功";
        }
    }


    /**
     * TODO：将用户车辆信息存于本地文件夹
     * @param user_name 车辆照片
     * @param license_plate_number 机动车登记证照片
     * @param file 车辆文件
     * @param suffix 后缀名
     * @return 是否保存成功
     */
    private boolean savePhone(String user_name,String license_plate_number,byte[] file,String suffix,String name)  {
        String filepath="";//文件路径
        String filePath=filepath+user_name+"/"+license_plate_number;
        Path path = Paths.get(filePath);
        OutputStream out = null;
        InputStream in=null;
        try {
            Files.createDirectories(path);
            out = new FileOutputStream(filePath+"/"+name+suffix);
            in = new ByteArrayInputStream(file);
            byte[] buff = new byte[1024];
            int len;
            while ((len = in.read(buff)) != -1) {
                out.write(buff, 0, len);
            }
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

  }






    /**
     * TODO：删除车辆信息
     * @param user_name 用户名
     * @param license_plate_number 车牌号
     * @return 是否成功
     */
    public String delete_User_Vehicle(String user_name,String license_plate_number) {
        int i = vehicleDao.deleteUserVehicle(user_name, license_plate_number);
        if (i<=0){
            return "删除车辆信息失败";
        }else {
            return "删除成功";
        }
    }





    /**
     * TODO：删除车辆文件
     * @param file 文件
     * @return 是否成功
     */
    public static Boolean deleteFile(File file) {
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()) {
            System.out.println("文件删除失败,请检查文件是否存在以及文件路径是否正确");
            return false;
        }
        //获取目录下子文件
        File[] files = file.listFiles();
        //遍历该目录下的文件对象
        for (File f : files) {
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()) {
                //递归删除目录下的文件
                deleteFile(f);
            } else {
                //文件删除
                f.delete();
                //打印文件名
                System.out.println("文件名：" + f.getName());
            }
        }
        //文件夹删除
        file.delete();
        System.out.println("目录名：" + file.getName());
        return true;
    }



    /**
     * TODO：获取用户绑定的车辆列表
     * @param user_name 用户名
     * @return 用户绑定的车辆列表
     */
    public List<String> getUserVehicle(String user_name) {
        return vehicleDao.find_Vehicle(user_name);
    }









    /**
     * TODO：检测车牌号与用户名是否匹配
     * @param user_name 用户名
     * @param license_plate_number 车牌号
     * @return 车辆信息
     */
    public int check_license_plate_number(String user_name, String license_plate_number) {
        return vehicleDao.check_license_plate_number(user_name,license_plate_number);
    }





    //文件－>Blob
    public static Blob FileToBlob(MultipartFile file){
        Blob b = null;
        try
        {
            byte[] imgBytes = new byte[0];
            imgBytes = file.getBytes();
            b = Hibernate.createBlob(imgBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }


    //将Blob->文件
    public static InputStream BlobToFile(Blob blob){
        InputStream inputStream = null;
        try {
            inputStream =blob.getBinaryStream();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return inputStream;
    }

}
