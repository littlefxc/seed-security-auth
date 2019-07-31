# spring-security-auth-seed
spring security 技术栈 

期望要求：可重用的 企业级的 认证和授权模块

1. 企业级认证和授权
2. 包含 QQ 登录，微信登录常见第三方登录
3. 移动端认证授权
4. 浏览器端认证授权
5. RBAC
6. OAuth2
7. 项目示例
8. spring boot 1.x 和 spring boot 2.x
9. SSO
10. 希望能够封装起来重用，能给别人用
11. 能够支持集群环境，跨应用工作，SESSION攻击，控制用户权限，防护与用户认证相关的攻击
12. 支持多种前端渠道
13. 支持多种认证

## 企业级的认证和授权

![企业级的认证和授权.png](md/企业级的认证和授权.png)

## 代码结构介绍

- seed-security-core : 核心业务逻辑
- seed-security-browser : 浏览器安全特定代码
- seed-security-app : app 相关特定代码
- seed-security-examples : 样例程序

## 使用 Spring MVC 开发 Restful API

![Restful第一印象.png](md/Restful第一印象.png)

![restful成熟度模型.png](md/restful成熟度模型.png)

- 使用 Spring MVC 编写 Restful API
- 使用 Spring MVC 处理其它 web 应用常用的需求和场景
- Restful API 开发常用辅助框架
  - swagger：生成服务文档
  - wiremock：伪造服务
- [RESTful API 设计参考文献列表](https://github.com/aisuhua/restful-api-design-references)
- 还不错的[编程规范](https://xwjie.github.io/rule/)

## Hibernate Validator

![HibernateValidator1.png](md/HibernateValidator1.png)

![HibernateValidator2.png](md/HibernateValidator2.png)

## Restful API 错误处理 

### Spring Boot中默认的错误处理机制

错误处理类：`org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController`

![SpringBoot默认错误处理机制.png](md/SpringBoot默认错误处理机制.png)

#### 如何添加自定义的 404.html, 500.html

![自定义错误页面的位置.png](md/自定义错误页面的位置.png)

### 自定义异常处理




