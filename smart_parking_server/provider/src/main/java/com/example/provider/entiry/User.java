package com.example.provider.entiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data                   //生成get set equals toString等方法
@AllArgsConstructor     //生成一个全属性构造方法
@NoArgsConstructor      //生成无参构造方法
public class User {


    @Id
    private String user_name;   //用户名
    private String password;   //密码
    private String user_id;    //身份证号

}
