package com.example.provider.dao;

import com.example.provider.entiry.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {

    /**
     * 查找用户
     * */
    @Select("SELECT * FROM User WHERE user_name =#{user_name}")
    User find_User(@Param("user_name") String user_name);


    /**
     * 获取用户总量
     */
    @Select("select count(1) from User")
    int getAllUsersNumber();


    /**
     * 增加一个用户
     *
     * */
    @Insert("INSERT INTO User VALUES(#{user_name}, #{password}, #{user_id})")
    int add_User(@Param("user_name") String user_name, @Param("password") String password,@Param("user_id")  String user_id);


    /**
     * 获取用户列表
     *
     */
    @Select("select * from User")
    List<User> getAllUsers();



    /**
     * 删除所有用户
     */
    @Delete("DELETE FROM User")
    void delete_User();
}
