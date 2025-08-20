package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.common.vo.LoginResult;
import com.example.mzt_server.entity.SysUser;
import com.example.mzt_server.service.SysUserService;
import com.example.mzt_server.util.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试Token控制器
 */
@Tag(name = "测试工具", description = "用于测试的工具接口")
@RestController
@RequestMapping("/test")
public class TestTokenController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private JwtUtils jwtUtils;
    
    @Value("${test.token.enabled:false}")
    private boolean testTokenEnabled;

    /**
     * 获取长期有效的测试Token
     */
    @Operation(summary = "获取测试Token", description = "获取长期有效的测试Token，仅用于开发测试")
    @GetMapping("/token")
    public Result<LoginResult> getTestToken(@RequestParam(defaultValue = "admin") String username) {
        // 安全检查，只在开发环境启用
        if (!testTokenEnabled) {
            return Result.error("测试Token功能未启用，请在配置文件中设置test.token.enabled=true");
        }
        
        // 查询用户
        SysUser user = userService.getByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }
        
        // 获取用户角色
        List<String> roles = userService.getUserRoles(user.getId());
        
        // 生成超长有效期的Token（30天）
        long longExpiration = 30 * 24 * 60 * 60; // 30天
        String accessToken = generateLongTermToken(user.getUsername(), roles, user.getId(), longExpiration);
        
        // 构建返回结果
        LoginResult result = new LoginResult();
        result.setAccessToken(accessToken);
        result.setTokenType("Bearer");
        result.setExpiresIn(longExpiration);
        
        return Result.success(result);
    }
    
    /**
     * 生成长期有效的Token
     */
    private String generateLongTermToken(String username, List<String> roles, Long userId, long expiration) {
        return jwtUtils.generateToken(username, roles, userId, expiration);
    }
}