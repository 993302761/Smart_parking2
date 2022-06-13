package com.example.provider.service;

import com.example.provider.entiry.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;


@Mapper
public interface UserService {

    @Select("SELECT * FROM User WHERE user_name =#{user_name}")
    User findUser(@Param("user_name") String user_name);

}
