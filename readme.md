Spring Boot 项目 README
项目介绍
这是一个基于 Spring Boot 2.7.12 的后端项目，集成了多种实用的功能和依赖，旨在提供高效、稳定的后端服务。
核心功能
数据库操作：集成 MyBatis-Plus 和 PageHelper，支持高效的数据库操作和分页功能
文件上传：支持文件上传功能，集成 Commons FileUpload
缓存支持：集成 Redis，提供数据缓存和会话管理
消息队列：集成 RabbitMQ，支持异步消息处理
图片处理：集成 JavaCV，支持图片处理和视频处理
安全认证：集成 JWT，支持用户认证和权限控制
日志管理：使用 Log4j 进行日志管理
技术栈
主要依赖
依赖项	版本	说明
Spring Boot Starter JDBC	2.7.12	数据库连接和操作
Spring Boot Starter Web	2.7.12	Web 应用开发
MySQL Connector-J	8.0.33	MySQL 数据库驱动
Lombok	1.18.26	减少样板代码
Spring Boot Starter Test	2.7.12	测试支持
Pagehelper-Spring-Boot-Starter	1.4.2	分页功能
Aliyun SDK OSS	3.15.1	阿里云 OSS 文件存储
JAXB API	2.3.1	Java XML 绑定
SnakeYAML	2.0	YAML 配置解析
Java Activation	1.1.1	Java 激活框架
JAXB Runtime	2.3.3	JAXB 运行时支持
JWT	0.9.1	JSON Web Token 认证
FastJSON	1.2.76	JSON 处理
MyBatis-Plus Boot Starter	3.5.3.1	MyBatis-Plus 集成
Hutool	5.8.11	工具类库
Spring Boot Configuration Processor	2.7.12	配置元数据支持
Pinyin4j	2.5.1	汉字转拼音
Commons FileUpload	1.4	文件上传处理
Spring Boot Starter Data Redis	2.7.12	Redis 缓存支持
JavaCV	1.5.5	图像和视频处理
JAVE Core	2.4.5	视频处理
JAVE Native Win64	2.4.5	Windows 平台视频处理原生库
Jedis	2.8.0	Redis 客户端
Log4j	1.2.17	日志管理
Alibaba Druid	1.1.21	数据库连接池
Spring Boot Starter AMQP	2.3.9.RELEASE	RabbitMQ 消息队列
Jackson Databind	2.11.4	JSON 数据绑定
项目运行
环境准备
JDK 8 或更高版本
Maven 3.6 或更高版本
MySQL 5.7 或更高版本
Redis 服务器
RabbitMQ 服务器
运行项目
bash
复制
mvn spring-boot:run
构建项目
bash
复制
mvn clean package
构建后的 JAR 文件位于 target 目录下。
测试说明
项目使用 JUnit 和 Spring Boot Test 进行单元测试和集成测试，确保代码质量和功能正确性。
部署说明
将构建好的 JAR 文件部署到服务器上，确保服务器环境满足项目运行要求。
许可证
本项目采用 MIT 许可证，欢迎自由使用和修改。
