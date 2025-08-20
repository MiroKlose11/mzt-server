# 🔐 环境变量配置指南

本项目使用环境变量来管理敏感配置信息，提高安全性。

## 📋 配置方式

### 方式1: 使用 .env.local 文件（推荐）

1. **复制模板文件**：
   ```bash
   cp .env.example .env.local
   ```

2. **编辑 .env.local 文件**，填入实际的配置值：
   ```bash
   vim .env.local  # 或用其他编辑器
   ```

3. **Spring Boot 会自动加载 .env.local 文件**（如果使用了 spring-boot-dotenv 依赖）

### 方式2: 系统环境变量

#### Windows (CMD)
```cmd
set DATABASE_PASSWORD=your_actual_password
set JWT_SECRET=your_actual_jwt_secret
```

#### Windows (PowerShell)
```powershell
$env:DATABASE_PASSWORD="your_actual_password"
$env:JWT_SECRET="your_actual_jwt_secret"
```

#### Linux/macOS
```bash
export DATABASE_PASSWORD=your_actual_password
export JWT_SECRET=your_actual_jwt_secret
```

### 方式3: IDE配置

#### IntelliJ IDEA
1. Run/Debug Configurations
2. Environment variables
3. 添加需要的环境变量

#### Eclipse
1. Run Configurations
2. Environment tab
3. 添加环境变量

## 🔑 必需的环境变量

| 变量名 | 说明 | 示例值 |
|--------|------|--------|
| `DATABASE_PASSWORD` | 数据库密码 | `your_db_password` |
| `JWT_SECRET` | JWT密钥(至少32位) | `your_super_secret_jwt_key_32chars` |
| `TENCENT_SMS_SECRET_ID` | 腾讯云SecretId | `AKIDxxxxxxxx` |
| `TENCENT_SMS_SECRET_KEY` | 腾讯云SecretKey | `xxxxxxxx` |
| `TENCENT_SMS_APP_ID` | 短信应用ID | `1400000000` |
| `TENCENT_SMS_TEMPLATE_ID` | 短信模板ID | `123456` |

## ⚠️ 安全注意事项

1. **永远不要提交 .env.local 文件到版本控制**
2. **不要在代码中硬编码敏感信息**
3. **定期更换密钥和密码**
4. **生产环境使用强密码**
5. **限制环境变量的访问权限**

## 🚀 部署环境配置

### Docker 部署
```dockerfile
# 在 docker-compose.yml 中
environment:
  - DATABASE_PASSWORD=${DATABASE_PASSWORD}
  - JWT_SECRET=${JWT_SECRET}
```

### 云服务器部署
在服务器的 `/etc/environment` 或用户的 `.bashrc` 中配置环境变量。

### Kubernetes 部署
使用 Secret 和 ConfigMap 管理环境变量。

## 🔍 验证配置

启动应用后，可以通过以下方式验证配置是否正确：

1. **查看启动日志** - 确认没有配置相关错误
2. **测试数据库连接** - 访问应用确认数据库连接正常
3. **测试短信功能** - 调用短信发送接口验证

如果遇到配置问题，检查：
- 环境变量名是否正确
- 环境变量值是否有效
- 是否重启了应用
- IDE 是否正确加载了环境变量