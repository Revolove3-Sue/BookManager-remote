# 基于Springboot+Vue的图书管理系统Librasphere

[![Contributors][contributors-shield]][contributors-url][![Forks][forks-shield]][forks-url][![Stargazers][stars-shield]][stars-url][![Issues][issues-shield]][issues-url][![MIT License][license-shield]][license-url][![LinkedIn][linkedin-shield]][linkedin-url]

<br />
<div align="center">
  <a href="https://github.com/Revolover3-Sue/Librasphere">
    <img src="images/Librasphere.png" alt="Logo" width="300" height="300">
  </a>

  <h3 align="center"><img src="https://user-images.githubusercontent.com/74038190/213844263-a8897a51-32f4-4b3b-b5c2-e1528b89f6f3.png" width="50px" /> &nbsp;Libraspere   <img src="https://user-images.githubusercontent.com/74038190/213844263-a8897a51-32f4-4b3b-b5c2-e1528b89f6f3.png" width="50px" /> &nbsp;</h3>

  <p align="center">
    基于Springboot+Vue的图书管理系统Librasphere
    <br />
    <a href="https://github.com/Revolove3-Sue/LibraSphere/blob/main/README.md"><strong>浏览文档 »</strong></a>
    <br />
    <br />
    <a href="https://github.com/Revolover3-Sue/Librasphere/issues"> 🐞反馈 Bug</a>
    ·
    <a href="https://github.com/Revolover3-Sue/Librasphere/issues"> ✨请求新功能</a>
  </p>
</div>

## 📚项目简介

Librasphere是一套在线图书管理系统，技术选型为前后端分离，前端基于Vue.js，后端基于Java语言开发，使用了SpringBoot和MyBatis框架提高开发效率和质量。主要面向图书馆管理员和读者在图书管理、图书借阅等需求。  
![image](images/homepage.png)

## 🛠️技术栈

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
- **框架:** Vue.js 2.x
- **UI 组件:** Element UI
- **HTTP 客户端:** Axios
- **路由:** Vue Router
- **状态管理:** Vuex

## 🏛️项目结构
图书管理系统向下划分为图书管理、图书类型管理、用户信息管理和借阅管理四个模块。图书管理主要负责对图书的信息进行管理，以及管理员进行图书的查询；图书类型管理主要关联图书信息进行分类，可以对类型进行管理，以及对类型进行条件查询；借阅管理主要负责读者进行借书、还书操作，以及查询读者历史借阅信息；用户信息管理可以针对图书管理员和读者的信息进行管理。  
![image](/images/softwarestructure.png)

项目的数据结构由软件结构决定。  
![image](/images/datastructure.png)

## ✨功能特性

Librasphere 图书管理系统分为以下几个主要模块：

### 📚 图书管理
- **功能:** 图书信息的增删改查、图书分类管理、图书库存管理、图书搜索功能
- **描述:** 负责对图书的信息进行管理，以及管理员进行图书的查询

### 📖 借阅管理
- **功能:** 图书借阅、图书归还、借阅历史查询、借阅状态追踪
- **描述:** 负责读者进行借书、还书操作，以及查询读者历史借阅信息

### 👥 用户管理
- **功能:** 用户注册与登录、角色权限控制、用户信息管理、借阅权限管理
- **描述:** 针对图书管理员和读者的信息进行管理

### 📑 图书类型管理
- **功能:** 图书类型的增删改查、图书类型条件查询
- **描述:** 关联图书信息进行分类，可以对类型进行管理，以及对类型进行条件查询

## 🌐 环境要求

### 后端环境
- **JDK:** 1.8+
- **构建工具:** Maven 3.6+
- **数据库:** MySQL 8.0+
- **缓存:** Redis

### 前端环境
- **Node.js:** 12.x 或更高版本
- **包管理工具:** npm 6.x 或更高版本 

## 🚀项目搭建
### 后端搭建
  1. 克隆项目到本地
``` bash
git clone https://github.com/Revolove3-Sue/Librasphere.git
```
  2.使用IDE打开项目（推荐IntelliJ IDEA）
  3. 配置数据库连接
  4. 运行 `BookManagerApplication.java`
### 前端搭建 
  1.启动node.js
  
  2.运行
  * Node.js
  ```sh
  npm run dev
  ```


## 版本历史

- v1.0.0 
  - 初始版本发布
  - 完成核心功能开发
  - 项目架构搭建完成
  
- v1.0.1 
  - [x] 添加缓存支持
  - [x] 优化日志管理
  - [x] 优化查询性能
  - [x] 添加批量操作功能
  - [ ] 添加对使用docker部署程序的初步支持
  
  v1.0.2(进行中)
  - [ ] 为登陆界面添加验证码（已在一个分支上构建好了前端）。
  - [ ] 使用的框架过于老旧,或许可以尝试使用springboot3.0+Vue3.0对项目进行重构。
  - [ ] 首页过于空白，可以添加公告通知栏。
  - [ ] 添加清除借阅记录的功能。
  - [ ] 优化对移动端的适配。

## 贡献

贡献让开源社区成为了一个非常适合学习、启发和创新的地方。你所做出的任何贡献都是**受人尊敬**的。

如果你有好的建议，请复刻（fork）本仓库并且创建一个拉取请求（pull request）。你也可以简单地创建一个议题（issue），并且添加标签「enhancement」。不要忘记给项目点一个 star！再次感谢！

1. 复刻（Fork）本项目
2. 创建你的 Feature 分支 (`git checkout -b feature/AmazingFeature`)
3. 提交你的变更 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到该分支 (`git push origin feature/AmazingFeature`)
5. 创建一个拉取请求（Pull Request）

## 许可证

根据 MIT 许可证分发。打开 [LICENSE.txt](LICENSE.txt) 查看更多内容。


[contributors-shield]: https://img.shields.io/github/contributors/Revolove3-Sue/Librasphere.svg?style=for-the-badge
[contributors-url]: https://github.com/admin1025/Libraspher/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/Revolove3-Sue/Librasphere.svg?style=for-the-badge
[forks-url]: https://github.com/Revolove3-Sue/Librasphere/network/members
[stars-shield]: https://img.shields.io/github/stars/Revolove3-Sue/Librasphere.svg?style=for-the-badge
[stars-url]: https://github.com/Revolove3-Sue/Librasphere/stargazers
[issues-shield]: https://img.shields.io/github/issues/Revolove3-Sue/Librasphere.svg?style=for-the-badge
[issues-url]: https://github.com/Revolove3-Sue/Librasphere/issues
[license-shield]: https://img.shields.io/github/license/Revolove3-Sue/Librasphere.svg?style=for-the-badge
[license-url]: https://github.com/Revolove3-Sue/Librasphere/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/othneildrew
[product-screenshot]: images/screenshot.png
