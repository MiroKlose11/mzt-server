package com.example.mzt_server.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * JWT工具类
 */
@Component
public class JwtUtils {
    
    /**
     * 密钥
     */
    @Value("${jwt.secret:mztServerSecretKey}")
    private String secret;
    
    /**
     * 访问令牌过期时间(秒)
     */
    @Value("${jwt.access-token-expiration:1800}")
    private Long accessTokenExpiration;
    
    /**
     * 刷新令牌过期时间(秒)
     */
    @Value("${jwt.refresh-token-expiration:604800}")
    private Long refreshTokenExpiration;
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    /**
     * 生成访问令牌
     *
     * @param username 用户名
     * @param roles 角色列表
     * @param userId 用户ID
     * @return 访问令牌
     */
    public String generateAccessToken(String username, List<String> roles, Long userId) {
        return generateToken(username, roles, userId, accessTokenExpiration);
    }
    
    /**
     * 生成刷新令牌
     *
     * @param username 用户名
     * @param roles 角色列表
     * @param userId 用户ID
     * @return 刷新令牌
     */
    public String generateRefreshToken(String username, List<String> roles, Long userId) {
        return generateToken(username, roles, userId, refreshTokenExpiration);
    }
    
    /**
     * 生成令牌
     *
     * @param username 用户名
     * @param roles 角色列表
     * @param userId 用户ID
     * @param expiration 过期时间(秒)
     * @return 令牌
     */
    private String generateToken(String username, List<String> roles, Long userId, long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration * 1000);
        
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .claim("roles", roles)
                .setIssuedAt(now)
                .setId(UUID.randomUUID().toString())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    
    /**
     * 解析令牌
     *
     * @param token 令牌
     * @return 声明
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
    
    /**
     * 验证令牌
     *
     * @param token 令牌
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            // 解析令牌
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            
            // 检查是否在黑名单中
            String tokenId = claims.getId();
            Boolean isBlacklisted = redisTemplate.hasKey("jwt:blacklist:" + tokenId);
            if (Boolean.TRUE.equals(isBlacklisted)) {
                return false;
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 获取访问令牌过期时间(秒)
     *
     * @return 过期时间
     */
    public Long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }
} 