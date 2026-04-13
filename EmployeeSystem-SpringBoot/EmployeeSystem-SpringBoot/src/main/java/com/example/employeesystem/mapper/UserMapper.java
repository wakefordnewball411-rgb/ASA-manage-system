package com.example.employeesystem.mapper;

import com.example.employeesystem.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import org.apache.ibatis.annotations.*;
import java.util.List;

@Repository
@Mapper

public interface UserMapper {
    //管理员登录
    @Select("SELECT * FROM user WHERE username= #{username} AND password= #{password}")
    User login(String username,String password);

    @Select("select * from user")
    List<User> findAll();

    @Select("select * from user where id=#{id}")
    User findById(Integer id);

    @Insert("insert into user(username,password) values(#{username},#{password})")
    void addUser(User user);

    @Update("update user set username=#{username},password=#{password} where id=#{id}")
    void updateUser(User user);

    @Delete("delete from user where id=#{id}")
    void deleteUser(Integer id);

    @Select("select * from user where username = #{username}")
    User findByUsername(String username);



}
