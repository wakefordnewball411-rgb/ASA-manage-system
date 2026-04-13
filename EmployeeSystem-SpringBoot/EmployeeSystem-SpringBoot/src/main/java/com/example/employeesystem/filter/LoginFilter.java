package com.example.employeesystem.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.employeesystem.common.Result;
import com.example.employeesystem.utils.JwtUtil;

import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFilter implements Filter {

    private final JwtUtil jwtUtil;

    public LoginFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        System.out.println("=== 过滤器收到请求：" + req.getRequestURI());

        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            System.out.println("=== 放行OPTIONS预检请求：" + req.getRequestURI());
            chain.doFilter(request, response);
            return;
        }

        String url = req.getRequestURI();
        System.out.println("=== LoginFilter 处理请求：" + url);

        if (url.contains("/login")) {
            chain.doFilter(request, response);
            return;
        }

        if (url.startsWith("/admin/") || url.startsWith("/employee/")) {
            // 打印所有 header
            System.out.println("=== 所有请求头：");
            java.util.Enumeration<String> headerNames = req.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = req.getHeader(headerName);
                System.out.println(headerName + ": " + headerValue);
            }

            String token = req.getHeader("Authorization");
            System.out.println("=== 收到的Authorization：" + token);

            if (token == null || token.trim().isEmpty()) {
                System.out.println("=== token为空，返回未登录");
                resp.setContentType("application/json;charset=utf-8");
                resp.getWriter().write(JSONObject.toJSONString(Result.fail("请先登录")));
                return;
            }

            try {
                jwtUtil.parseToken(token);
                System.out.println("=== token校验通过");
            } catch (Exception e) {
                System.err.println("=== token校验失败：" + e.getMessage());
                resp.setContentType("application/json;charset=utf-8");
                resp.getWriter().write(JSONObject.toJSONString(Result.fail("登录已过期")));
                return;
            }
        }

        System.out.println("=== 放行请求：" + url);
        chain.doFilter(request, response);
    }
}
