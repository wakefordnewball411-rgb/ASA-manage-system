package com.example.employeesystem.entity;

public class Employee {
    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private String department;
    private String role;


    // 无参
    public Employee() {}

    // 全参
    public Employee(Integer id, String name, Integer age, String gender, String department, String role) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.department = department;
        this.role = role;
    }

    // Getter&&Setter
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;

    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public Integer getAge(){
        return age;
    }
    public void setAge(Integer age){
        this.age = age;

    }
    public String getGender(){
        return gender;
    }
    public void setGender(String gender){
        this.gender = gender;
    }

    public String getDepartment(){
        return department;
    }
    public void setDepartment(String department){
        this.department = department;
    }

    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", department='" + department + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
