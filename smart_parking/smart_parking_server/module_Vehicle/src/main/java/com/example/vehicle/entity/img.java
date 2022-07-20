package com.example.vehicle.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data                   //生成get set equals toString等方法  来自lombok包
@NoArgsConstructor      //生成无参构造方法
public class img {
    Blob img;
    String suffix;
}
