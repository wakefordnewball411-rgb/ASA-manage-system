package com.example.employeesystem.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerErrorException;

// 全局异常捕获（自动生效，不用调用）
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 【重点修改】捕获所有 Exception，不要只捕获 RuntimeException
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        // 1. 强制打印完整红色异常栈！！！
        e.printStackTrace();

        // 2. 把异常信息打印到控制台，让你看见
        System.err.println("=== 全局异常处理器捕获到错误：===");
        System.err.println(e.getMessage());

        // 3. 返回固定错误，不要返回 e.getMessage()（防止暴露敏感信息）
        return Result.fail("服务器内部错误，请查看控制台日志");
    }

    // 捕获参数异常
    @ExceptionHandler(IllegalArgumentException.class)
    public Result handleIllegalArgumentException(IllegalArgumentException e) {
        e.printStackTrace();
        System.err.println("=== 参数错误：" + e.getMessage());
        return Result.fail("参数错误：" + e.getMessage());
    }
}
