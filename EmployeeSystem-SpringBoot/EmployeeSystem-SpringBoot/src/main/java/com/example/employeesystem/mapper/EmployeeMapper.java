package com.example.employeesystem.mapper;

import com.example.employeesystem.entity.Employee;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

// @Repository：标记为数据访问层组件
// @Mapper：MyBatis自动生成接口实现类
@Repository
@Mapper
public interface EmployeeMapper {
    //员工登录
    @Select("select * from employee where name=#{name} and department=#{department}")
    Employee login(String name, String department);


    //查询所有员工
    @Select("SELECT id, name, age, gender, department FROM employee")
    List<Employee> findAll();

    //根据ID查询
    @Select("SELECT id, name, age, gender, department FROM employee WHERE id = #{id}")
    Employee findById(Integer id);

    //新增员工
    @Insert("INSERT INTO employee (name, age, gender, department) VALUES (#{name}, #{age}, #{gender}, #{department})")
    void addEmployee(Employee employee);

    //修改员工
    @Update("UPDATE employee SET name = #{name}, age = #{age}, gender = #{gender}, department = #{department} WHERE id = #{id}")
    void updateEmployee(Employee employee);

    //删除员工
    @Delete("DELETE FROM employee WHERE id = #{id}")
    void deleteEmployee(Integer id);

    @Select("SELECT * FROM employee WHERE name = #{name}")
    Employee findByName(String name);
}
