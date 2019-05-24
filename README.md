# spring-security-auth-seed
spring security 技术栈 

要求：

1. 企业级认证和授权
2. 包含 QQ 登录，微信登录常见第三方登录
3. 移动端认证授权
4. 浏览器端认证授权
5. RBAC
6. OAuth2
7. 项目示例
8. spring boot 1.x 和 spring boot 2.x
9. SSO

- seed-security-core:认证与授权的核心模块
- seed-security-browser:浏览器作为客户端的认证与授权模块，依赖 seed-security-core 模块
- seed-security-app:移动端作为客户端的认证与授权模块，依赖 seed-security-core 模块
- seed-security-examples:案例模块，依赖 seed-security-browser 和 seed-security-app 模块

## 依赖管理

