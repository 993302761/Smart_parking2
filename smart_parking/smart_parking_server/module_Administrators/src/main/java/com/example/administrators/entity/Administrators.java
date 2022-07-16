package com.example.administrators.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data                   //生成get set equals toString等方法
@NoArgsConstructor      //生成无参构造方法
public class Administrators {

    private String ctr_id;             //管理员账号
    private String ctr_password;       //管理员密码



}
