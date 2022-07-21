package com.example.vehicle.service;


import com.example.vehicle.dao.VehicleDao;
import com.example.vehicle.entity.Vehicle_Blob_information;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.hibernate.Hibernate;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    @Resource
    private RestTemplate restTemplate;

    private final String userURl="http://www.localhost:9004/User";

    private final String orderURl="http://www.localhost:9001/Order";

    private final String parkingLotURl="http://www.localhost:9002/ParkingLots";

    private final String filepath = "/home/lyq/UserVehicle/";
    //存放图片的文件夹


    /**
     * TODO：添加一条车辆信息
     * @param user_name 用户名
     * @param license_plate_number 车牌号
     * @param vehicle_photos 车辆照片
     * @param registration 机动车登记证照片
     * @param driving_permit 车辆行驶证照片
     * @return 是否成功
     */
    public String add_Vehicle(String user_name, String user_id,String license_plate_number, MultipartFile vehicle_photos,MultipartFile registration,MultipartFile driving_permit) {
        if (user_name==null||license_plate_number==null||user_id==null||vehicle_photos==null||registration==null||driving_permit==null){
            return "所填信息不完整";
        }
        int vehicleNumber = check_license_plate_number(user_name, license_plate_number);
        if (vehicleNumber>0){
            return "该车辆已注册，请勿重复注册";
        }else if (vehicleNumber<0){
            return "错误：601";
        }

        boolean b = savePhone(user_name, license_plate_number, vehicle_photos, registration, driving_permit);
        if (!b){
            return "错误：602";
        }

        Blob vehicle_photos0=FileToBlob(vehicle_photos);
        //车辆照片
        String vehicle_photos_suffix=getSuffix(vehicle_photos);
        //车辆照片后缀名


        Blob registration0=FileToBlob(registration);
        //机动车登记证照片
        String registration_suffix=getSuffix(registration);
        //机动车登记证照片后缀名


        Blob driving_permit0=FileToBlob(driving_permit);
        //车辆行驶证照片
        String driving_permit_suffix=getSuffix(driving_permit);
        //车辆行驶证照片后缀名


        int i=vehicleDao.add_Vehicle(user_name,user_id,license_plate_number,vehicle_photos0,vehicle_photos_suffix,registration0,registration_suffix,driving_permit0,driving_permit_suffix);
        if (i<=0){
            return "添加车辆信息失败";
        }
        else {
            return "添加车辆信息成功";
        }
    }


    /**
     * TODO：将用户车辆信息存于本地文件夹
     * @param vehicle_photos 车辆照片
     * @param registration 机动车登记证照片
     * @param driving_permit 车辆行驶证照片
     * @return 是否保存成功
     */
    private boolean savePhone(String user_name,String license_plate_number,MultipartFile vehicle_photos,MultipartFile registration,MultipartFile driving_permit)  {
        String filePath=filepath+user_name+"/"+license_plate_number;
        Path path = Paths.get(filePath);
        try {
            Files.createDirectories(path);
            vehicle_photos.transferTo(new File(filePath+"/vehicle_photos"+getSuffix(vehicle_photos)));
            registration.transferTo(new File(filePath+"/registration"+getSuffix(registration)));
            driving_permit.transferTo(new File(filePath+"/driving_permit"+getSuffix(driving_permit)));
            return true;
        }catch (IOException e){
            return false;
        }
  }



    /**
     * TODO：获取文件后缀名
     * @param file 文件
     * @return 是否保存成功
     */
    private String getSuffix(MultipartFile file) {
        String realFileName = file.getOriginalFilename();
        return realFileName.substring(realFileName.lastIndexOf("."));
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
