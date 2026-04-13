package com.example.employeesystem.controller;

import com.example.employeesystem.entity.Employee;
import com.example.employeesystem.common.Result;
import com.example.employeesystem.service.EmployeeService;
import com.example.employeesystem.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final JwtUtil jwtUtil;

    public EmployeeController(EmployeeService employeeService, JwtUtil jwtUtil) {
        this.employeeService = employeeService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/home")
    public Result home(HttpServletRequest request) {
        System.out.println("=== /employee/home 接口进入 ===");

        String token = request.getHeader("Authorization");
        System.out.println("=== 收到的token：" + token);

        if (token == null || token.trim().isEmpty()) {
            System.out.println("=== token为空，返回未登录");
            return Result.fail("请先登录");
        }

        try {
            String name = jwtUtil.parseToken(token).getSubject();
            System.out.println("=== 解析出的员工名：" + name);

            Employee employee = employeeService.findByName(name);
            System.out.println("=== 查询到的员工：" + employee);

            if (employee == null) {
                System.out.println("=== 员工不存在");
                return Result.fail("用户信息不存在");
            }

            return Result.success(employee);
        } catch (Exception e) {
            System.err.println("=== 异常栈：");
            e.printStackTrace();
            return Result.fail("登录已过期");
        }
    }




    // 查询所有：GET http://localhost:8080/employee/findAll
    @GetMapping("/findAll")
    public Result findAll(){
        List<Employee> employees = employeeService.findAll();
        return Result.success(employees);
    }

    // 根据ID查询：GET http://localhost:8080/employee/findById/1
    @GetMapping("/findById")
    public Result findById(Integer id){
        if(id==null||id<=0){
            throw new IllegalArgumentException("ID格式不正确");
        }
        Employee employee = employeeService.findById(id);
        if(employee==null){
            throw new RuntimeException("该员工不存在");
        }
        return Result.success(employee);
    }

    // 新增：POST http://localhost:8080/employee/add
    @PostMapping("/add")
    public Result add(@RequestBody Employee employee){
        if(employee==null||employee.getName()==null||employee.getName().trim().isEmpty()){
            throw new IllegalArgumentException("员工姓名不能为空");
        }
        Employee exist=employeeService.findById(employee.getId());

        employeeService.addEmployee(employee);
        return Result.success("添加成功");
    }

    // 修改：PUT http://localhost:8080/employee/update
    @PutMapping("/update")
    public Result update(@RequestBody Employee employee){
        if (employee.getId() == null) {
            throw new IllegalArgumentException("员工ID不能为空");
        }

        Employee exist = employeeService.findById(employee.getId());
        if (exist == null) {
            throw new RuntimeException("要修改的员工不存在");
        }

        employeeService.updateEmployee(employee);
        return Result.success("修改成功");
    }

    // 删除：DELETE http://localhost:8080/employee/delete/1
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("员工ID不合法");
        }

        Employee exist = employeeService.findById(id);
        if (exist == null) {
            throw new RuntimeException("要删除的员工不存在");
        }

        employeeService.deleteEmployee(id);
        return Result.success("删除成功");
    }

    @GetMapping("/info")
    public Result info(HttpServletRequest request) {
        System.out.println("=== /employee/info 接口进入 ===");

        String token = request.getHeader("Authorization");
        System.out.println("=== 收到的token：" + token);

        if (token == null || token.trim().isEmpty()) {
            System.out.println("=== token为空，返回未登录");
            return Result.fail("请先登录");
        }

        try {
            String username = jwtUtil.parseToken(token).getSubject();
            System.out.println("=== 解析出的用户名：" + username);

            Employee employee = employeeService.findByName(username);
            System.out.println("=== 查询到的员工：" + employee);

            if (employee == null) {
                System.out.println("=== 员工不存在");
                return Result.fail("用户信息不存在");
            }

            return Result.success(employee);
        } catch (Exception e) {
            System.err.println("=== 异常栈：");
            e.printStackTrace();
            return Result.fail("登录已过期");
        }
    }


}
