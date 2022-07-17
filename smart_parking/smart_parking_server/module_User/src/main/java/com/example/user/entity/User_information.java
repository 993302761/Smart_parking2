package com.example.user.entity;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data                   //生成get set equals toString等方法
@NoArgsConstructor      //生成无参构造方法
public class User_information {


    private String user_name;   //用户名
    private String password;   //密码
    private String user_id;    //身份证号

}
