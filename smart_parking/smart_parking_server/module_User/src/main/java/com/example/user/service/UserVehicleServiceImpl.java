package com.example.user.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
public class UserVehicleServiceImpl {

    @Resource
    private RestTemplate restTemplate;




    private final String vehicleURl="http://ClientVehicle/Vehicle";



    /**
     * TODO：删除绑定的车辆信息
     * @param user_name 用户名
     * @param license_plate_number 车牌号
     * @return 是否成功
     */
    public ResponseEntity<String> deleteVehicle (String user_name, String license_plate_number){
        String url=vehicleURl+"/deleteVehicle/"+user_name+"/"+license_plate_number;
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        return exchange;
    }



    /**
     * TODO：获取用户绑定的车辆信息
     * @param user_name 用户名
     * @return 是否成功
     */
    public Object getUserVehicle (String user_name){
        String url=vehicleURl+"/getUserVehicle/"+user_name;
        Object s=restTemplate.getForObject(url,Object.class);
        return s;
    }




    /**
     * TODO：添加绑定车辆
     * @param user_name 用户名
     * @param user_id 身份证号码
     * @param license_plate_number 车牌号
     * @param vehicle_photos 车辆照片
     * @param registration 机动车登记证照片
     * @param driving_permit 车辆行驶证照片
     * @return 是否成功
     */
    public ResponseEntity<String> vehicle_binding (String user_name, String user_id, String license_plate_number, MultipartFile vehicle_photos, MultipartFile registration, MultipartFile driving_permit){
        String url=vehicleURl+"/vehicle_binding/"+user_name+"/"+user_id+"/"+license_plate_number+"/"+vehicle_photos+"/"+registration+"/"+driving_permit;
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, null, String.class);
        return exchange;
    }
}
