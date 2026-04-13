package com.example.employeesystem.controller;

import com.example.employeesystem.common.Result;
import com.example.employeesystem.entity.User;
import com.example.employeesystem.service.UserService;
import com.example.employeesystem.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/home")
    public Result home(HttpServletRequest request) {
        System.out.println("=== /admin/home 接口进入 ===");

        String token = request.getHeader("Authorization");
        System.out.println("=== 收到的token：" + token);

        if (token == null || token.trim().isEmpty()) {
            System.out.println("=== token为空，返回未登录");
            return Result.fail("请先登录");
        }

        try {
            String username = jwtUtil.parseToken(token).getSubject();
            System.out.println("=== 解析出的用户名：" + username);

            User user = userService.findByUsername(username);
            System.out.println("=== 查询到的管理员：" + user);

            if (user == null) {
                System.out.println("=== 管理员不存在");
                return Result.fail("用户信息不存在");
            }

            return Result.success(user);
        } catch (Exception e) {
            System.err.println("=== 异常栈：");
            e.printStackTrace();
            return Result.fail("登录已过期");
        }
    }

    // 查询所有用户
    @GetMapping("/findAll")
    public Result findAll() {
        List<User> users = userService.findAll();
        return Result.success(users);
    }

    // 根据ID查询用户
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("用户ID不合法");
        }
        User user = userService.findById(id);
        if (user == null) {
            throw new RuntimeException("该用户不存在");
        }
        return Result.success(user);
    }

    // 新增用户
    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        userService.add(user);
        return Result.success("新增成功");
    }

    // 修改用户
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        User exist = userService.findById(user.getId());
        if (exist == null) {
            throw new RuntimeException("要修改的用户不存在");
        }
        userService.update(user);
        return Result.success("修改成功");
    }

    // 删除用户
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("用户ID不合法");
        }
        User exist = userService.findById(id);
        if (exist == null) {
            throw new RuntimeException("要删除的用户不存在");
        }
        userService.delete(id);
        return Result.success("删除成功");
    }
}
