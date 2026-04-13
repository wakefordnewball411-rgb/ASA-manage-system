package com.example.employeesystem.service;

import com.example.employeesystem.entity.User;
import com.example.employeesystem.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;;

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    public void add(User user) {
        userMapper.addUser(user);
    }

    public void update(User user) {
        userMapper.updateUser(user);
    }

    public void delete(Integer id) {
        userMapper.deleteUser(id);
    }

    public User login(String username, String password) {
        return userMapper.login(username, password);
    }

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }


}
