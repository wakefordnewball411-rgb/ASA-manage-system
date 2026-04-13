package com.example.employeesystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@SpringBootApplication
@MapperScan("com.example.employeesystem.mapper")


public class EmployeeSystemSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeSystemSpringBootApplication.class, args);
    }
}
