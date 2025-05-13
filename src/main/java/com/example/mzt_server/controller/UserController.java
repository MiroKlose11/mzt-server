package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.common.exception.BusinessException;
import com.example.mzt_server.common.exception.ErrorEnum;
import com.example.mzt_server.service.SysUserService;
import com.example.mzt_server.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户控制器
 */
@Tag(name = "用户管理", description = "用户相关接口")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 获取当前用户信息
     */
    @Operation(
        summary = "获取当前用户信息", 
        description = "获取当前登录用户信息",
        security = @SecurityRequirement(name = "Bearer")
    )
    @GetMapping("/me")
    public Result<Map<String, Object>> getCurrentUserInfo(HttpServletRequest request) {
        // 获取Token
        String token = getTokenFromRequest(request);
        
        // 验证Token
        if (!StringUtils.hasText(token) || !jwtUtils.validateToken(token)) {
            throw new BusinessException(ErrorEnum.UNAUTHORIZED);
        }
        
        try {
            // 解析Token获取用户ID
            Claims claims = jwtUtils.parseToken(token);
            Long userId = claims.get("userId", Long.class);
            
            // 获取用户信息
            Map<String, Object> userInfo = userService.getUserInfo(userId);
            return Result.success(userInfo);
        } catch (Exception e) {
            throw new BusinessException(ErrorEnum.UNAUTHORIZED);
        }
    }
    
    /**
     * 从请求中获取Token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
} 