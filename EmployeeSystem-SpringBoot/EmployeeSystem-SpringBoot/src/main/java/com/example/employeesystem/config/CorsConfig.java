package com.example.employeesystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class CorsConfig {


    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        //允许所有域名跨域
        config.addAllowedOriginPattern("http://localhost:3030");
        //允许所有请求头
        config.addAllowedHeader("*");
        //允许所有请求方法（GET, POST, PUT, DELETE, OPTIONS）
        config.addAllowedMethod("*");
        //允许携带 Cookie（前后端带 token 时必须true）
        config.setAllowCredentials(true);
        //预检请求有效期（秒），避免频繁发送OPTIONS
        config.setMaxAge(3600L);

        //对所有接口生效
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
