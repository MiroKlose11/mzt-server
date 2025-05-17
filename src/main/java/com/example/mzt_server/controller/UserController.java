package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.common.exception.BusinessException;
import com.example.mzt_server.common.exception.ErrorEnum;
    import com.example.mzt_server.common.vo.UserProfileForm;
import com.example.mzt_server.service.ISysUserService;
import com.example.mzt_server.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户控制器
 */
@Tag(name = "用户管理", description = "用户相关接口")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ISysUserService userService;

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
     * 获取用户个人信息
     */
    @Operation(
        summary = "获取个人中心用户信息", 
        description = "根据用户ID获取用户个人信息",
        security = @SecurityRequirement(name = "Bearer")
    )
    @GetMapping("/{userId}/profile")
    public Result<UserProfileForm> getUserProfile(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            HttpServletRequest request) {
        
        // 获取Token并验证
        String token = getTokenFromRequest(request);
        if (!StringUtils.hasText(token) || !jwtUtils.validateToken(token)) {
            throw new BusinessException(ErrorEnum.UNAUTHORIZED);
        }
        
        try {
            // 解析Token获取当前用户ID
            Claims claims = jwtUtils.parseToken(token);
            Long currentUserId = claims.get("userId", Long.class);
            
            // 只允许查看自己的信息或管理员可查看所有用户信息
            // 这里可以根据实际需求调整权限控制
            boolean isAdmin = userService.getUserRoles(currentUserId).contains("ROLE_ADMIN");
            if (!currentUserId.equals(userId) && !isAdmin) {
                throw new BusinessException(ErrorEnum.FORBIDDEN);
            }
            
            // 获取用户个人信息
            UserProfileForm profileForm = userService.getUserProfile(userId);
            return Result.success(profileForm);
        } catch (BusinessException e) {
            throw e;
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