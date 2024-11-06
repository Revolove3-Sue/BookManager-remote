# 图书管理系统 API

## 项目简介

图书管理系统（Book Manager System）是一个基于 Spring Boot 开发的图书管理后端服务系统。本系统提供完整的图书管理功能，包括图书信息管理、用户管理、借阅管理等功能。系统采用前后端分离架构，本仓库为后端 API 服务。

## 技术栈

### 后端技术
- **核心框架：** Spring Boot 2.x
- **安全框架：** Spring Security + JWT
- **数据库：** MySQL 8.0
- **数据库连接池：** HikariCP
- **API文档：** Swagger/SpringDoc
- **日志框架：** SLF4J + Logback
- **工具库：** 
  - Lombok - 简化代码
  - MapStruct - 对象映射
  - Hutool - 工具集
- **构建工具：** Maven
- **版本控制：** Git

### 前端技术
- Vue.js 2.x
- Element UI
- Axios
- Vue Router
- Vuex

## 项目结构
plaintext
BookManagerApi/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/rabbiter/bms/
│ │ │ ├── config/ # 配置类
│ │ │ │ ├── CorsConfig.java # 跨域配置
│ │ │ │ └── SecurityConfig.java # 安全配置
│ │ │ ├── controller/ # 控制器层
│ │ │ │ ├── BookController.java
│ │ │ │ ├── UserController.java
│ │ │ │ └── BorrowController.java
│ │ │ ├── service/ # 服务层
│ │ │ │ ├── impl/ # 服务实现
│ │ │ │ └── interfaces/ # 服务接口
│ │ │ ├── repository/ # 数据访问层
│ │ │ ├── model/ # 数据模型
│ │ │ │ ├── entity/ # 实体类
│ │ │ │ ├── dto/ # 数据传输对象
│ │ │ │ └── vo/ # 视图对象
│ │ │ ├── utils/ # 工具类
│ │ │ └── exception/ # 自定义异常
│ │ └── resources/
│ │ ├── static/ # 静态资源
│ │ ├── application.yml # 主配置文件
│ │ └── application-dev.yml # 开发环境配置
│ └── test/ # 测试代码
├── pom.xml # 项目依赖
└── README.md # 项目说明
## 核心功能

### 1. 用户管理
- 用户注册与登录
- 角色权限管理（管理员、普通用户）
- 用户信息管理
- JWT 令牌认证

### 2. 图书管理
- 图书信息的 CRUD 操作
- 图书分类管理
- 图书搜索（支持多条件）
- 图书库存管理

### 3. 借阅管理
- 图书借阅
- 图书归还
- 借阅历史查询
- 借阅状态追踪
- 超期提醒

## 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+
- Redis (可选，用于缓存)

## 版本历史

- v1.0.0 
  - 基础功能实现
  - 用户认证授权
  - 图书管理基础功能
  
- v1.1.0 (计划中)
  - 添加缓存支持
  - 优化查询性能
  - 添加批量操作功能

## 许可证

本项目采用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件