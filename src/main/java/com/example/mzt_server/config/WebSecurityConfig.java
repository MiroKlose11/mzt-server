package com.example.mzt_server.config;

import com.example.mzt_server.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Web安全配置
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * 配置SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
            // 禁用CSRF
            .csrf(csrf -> csrf.disable())
            // 基于Token，不需要Session
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 请求授权
            .authorizeHttpRequests((authz) -> authz
                    // 白名单接口放行
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/doc.html",
                            "/auth/login", "/auth/captcha").permitAll()
                    // 需要认证的接口
                    .requestMatchers("/auth/info", "/user/info", "/menus/routes").authenticated()
                    // 其余接口默认放行
                    .anyRequest().permitAll())
            // 添加JWT过滤器
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    /**
     * 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 