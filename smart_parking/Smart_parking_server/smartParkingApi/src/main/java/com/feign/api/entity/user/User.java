package com.feign.api.entity.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data                   //生成get set equals toString等方法
@NoArgsConstructor      //生成无参构造方法
public class User {

    private String user_name;   //用户名
    private String user_id;    //身份证号
    private List<String> Vehicle;    //所绑定的车辆信息

}
