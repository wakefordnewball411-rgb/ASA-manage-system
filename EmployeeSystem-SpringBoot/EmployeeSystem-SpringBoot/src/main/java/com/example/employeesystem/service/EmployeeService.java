package com.example.employeesystem.service;

import com.example.employeesystem.entity.Employee;
import com.example.employeesystem.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    public List<Employee> findAll() {
        return employeeMapper.findAll();
    }

    public Employee findById(Integer id) {
        return employeeMapper.findById(id);
    }

    public void addEmployee(Employee employee) {
        employeeMapper.addEmployee(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeMapper.updateEmployee(employee);
    }

    public void deleteEmployee(Integer id) {
        employeeMapper.deleteEmployee(id);
    }

    public Employee login(String name, String department) {
        return employeeMapper.login(name, department);
    }
    public Employee findByName(String name) {
        return employeeMapper.findByName(name);
    }


}