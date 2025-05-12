package com.example.cms_mzt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 配置HttpSecurity
     *
     * @param http HttpSecurity对象
     * @return SecurityFilterChain
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 配置HttpSecurity
        http
            // 关闭CSRF
            .csrf(csrf -> csrf.disable())
            // 基于Token认证，不需要Session
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 配置请求授权
            .authorizeHttpRequests(auth -> auth
                // 允许Druid监控页面请求
                .requestMatchers("/druid/**").permitAll()
                // 允许首页相关接口
                .requestMatchers("/index/**").permitAll()
                // 允许Swagger/Knife4j接口文档
                .requestMatchers("/doc.html", "/webjars/**", "/v3/api-docs/**").permitAll()
                // 其他请求需要认证
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
} 