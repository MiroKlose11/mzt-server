# 环境变量配置指南

## 📋 配置步骤

### 1. 复制环境变量模板

```bash
# 在项目根目录执行
cp .env.example .env
```

### 2. 编辑 `.env` 文件，填入真实配置

```bash
# 使用你喜欢的编辑器打开
notepad .env
# 或
code .env
```

### 3. 必填配置项

#### JWT 配置
```properties
JWT_SECRET=你的32位以上随机字符串
JWT_ACCESS_EXPIRATION=86400      # 访问令牌有效期(秒)
JWT_REFRESH_EXPIRATION=604800    # 刷新令牌有效期(秒)
```

> **安全提示**: JWT密钥建议使用32位以上的随机字符串，可以用以下命令生成：
> ```bash
> # 使用 PowerShell 生成随机密钥
> -join ((48..57) + (65..90) + (97..122) | Get-Random -Count 32 | % {[char]$_})
> ```

#### 微信小程序配置
```properties
WECHAT_MINIAPP_APPID=wx1234567890abcdef    # 微信小程序AppID
WECHAT_MINIAPP_SECRET=abcdef1234567890      # 微信小程序AppSecret
```

> 获取方式：登录[微信公众平台](https://mp.weixin.qq.com/) → 开发 → 开发管理 → 开发设置

#### 腾讯云COS配置
```properties
TENCENT_COS_SECRET_ID=AKIDxxxxxxxxxx
TENCENT_COS_SECRET_KEY=xxxxxxxxxxxxxxxxxx
TENCENT_COS_REGION=ap-beijing
TENCENT_COS_BUCKET_NAME=your-bucket-1234567890
```

> 获取方式：登录[腾讯云控制台](https://console.cloud.tencent.com/) → 访问管理 → 访问密钥

#### 腾讯云短信配置
```properties
TENCENT_SMS_SECRET_ID=AKIDxxxxxxxxxx
TENCENT_SMS_SECRET_KEY=xxxxxxxxxxxxxxxxxx
TENCENT_SMS_REGION=ap-beijing
TENCENT_SMS_APP_ID=1400123456
TENCENT_SMS_TEMPLATE_ID=123456
TENCENT_SMS_SIGN_NAME=您的签名
```

> 获取方式：登录[腾讯云控制台](https://console.cloud.tencent.com/smsv2) → 短信 → 应用管理

#### 短信频率限制（可选）
```properties
SMS_RATE_PER_MINUTE=1    # 每分钟最多发送次数
SMS_RATE_PER_HOUR=5      # 每小时最多发送次数
SMS_RATE_PER_DAY=10      # 每天最多发送次数
```

## 🔒 安全注意事项

1. **永远不要提交 `.env` 文件到 Git**
   - `.env` 文件已经在 `.gitignore` 中被排除
   - 只提交 `.env.example` 模板文件

2. **生产环境配置**
   - 生产环境建议使用系统环境变量或容器的 Secret 管理
   - 不要在代码中硬编码任何敏感信息

3. **密钥管理**
   - 定期轮换密钥
   - 不同环境使用不同的密钥
   - 使用强随机密钥

## 🚀 启动项目

配置完成后，启动项目：

```bash
# 清理并编译
mvn clean install

# 运行项目
mvn spring-boot:run
```

## ❓ 常见问题

### Q: 启动时报错 "base64-encoded secret key cannot be null or empty"
A: 检查 `.env` 文件是否存在，且 `JWT_SECRET` 是否配置

### Q: 微信登录失败
A: 检查 `WECHAT_MINIAPP_APPID` 和 `WECHAT_MINIAPP_SECRET` 是否正确

### Q: 短信发送失败
A: 
1. 检查腾讯云短信配置是否正确
2. 确认短信模板已审核通过
3. 确认短信签名已审核通过
4. 检查账户余额是否充足

## 📝 配置文件说明

- `.env` - 本地开发环境配置（不提交到Git）
- `.env.example` - 配置模板（提交到Git）
- `application.yml` - Spring Boot 应用配置（从 `.env` 读取环境变量）



