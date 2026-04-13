package com.example.employeesystem.controller;

import com.example.employeesystem.common.Result;
import com.example.employeesystem.entity.Employee;
import com.example.employeesystem.entity.User;
import com.example.employeesystem.service.EmployeeService;
import com.example.employeesystem.service.UserService;
import com.example.employeesystem.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping
public class LoginController {

    private final UserService userService;
    private final EmployeeService employeeService;
    private final JwtUtil jwtUtil;

    public LoginController(UserService userService,
                           EmployeeService employeeService,
                           JwtUtil jwtUtil) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Result login(@RequestBody Map<String, Object> params) {
        String username = (String) params.get("username");
        String password = (String) params.get("password");
        String name = (String) params.get("name");
        String department = (String) params.get("department");

        // 管理员登录
        if (username != null && password != null) {

            System.out.println("========================================");
            System.out.println("【登录接口被调用了！】");
            System.out.println("用户名：" +  username);
            System.out.println("密码：" + password);
            System.out.println("========================================");

            User admin = userService.login(username, password);
            if (admin == null) return Result.fail("管理员账号错误");
            String token = jwtUtil.generateToken(admin.getUsername());
            Map<String, Object> data = new HashMap<>();
            data.put("admin", admin);
            data.put("token", token);
            return Result.success(data);
        }

        // 员工登录
        else if (name != null && department != null) {

            System.out.println("========================================");
            System.out.println("【登录接口被调用了！】");
            System.out.println("用户名：" +  name);
            System.out.println("密码：" + department);
            System.out.println("========================================");

            Employee emp = employeeService.login(name, department);
            if (emp == null) return Result.fail("员工账号错误");
            String token = jwtUtil.generateToken(emp.getName());
            Map<String, Object> data = new HashMap<>();
            data.put("employee", emp);
            data.put("token", token);
            return Result.success(data);
        }

        return Result.fail("参数错误");
    }
}

