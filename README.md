# 作业管理系统

一个基于 Spring Boot + Vue.js 的前后端分离管理系统，提供班级学员管理、员工管理、课程教务管理及数据统计等功能。

## 项目概述

本系统是一个综合性的教务管理平台，旨在帮助教育机构高效管理班级、学员、员工及课程信息。系统采用前后端分离架构，后端使用 Spring Boot 提供 RESTful API 服务，前端使用 Vue.js + Element UI 构建响应式管理界面。

### 技术栈

#### 后端技术
- **Java 17+**
- **Spring Boot** - 应用框架
- **Spring Data JPA** - 数据持久化
- **JWT** - 身份认证
- **Knife4j** - API 文档生成
- **阿里云 OSS** - 文件存储服务
- **MySQL** - 数据库

#### 前端技术
- **Vue.js 2.6** - 前端框架
- **Element UI** - UI 组件库
- **Vuex** - 状态管理
- **Vue Router** - 路由管理
- **Axios** - HTTP 请求库
- **ECharts** - 数据可视化图表
- **Sass** - CSS 预处理器

## 功能特性

### 班级学员管理
- 班级信息管理（增删改查）
- 学员信息管理（增删改查）
- 学员与班级关联管理

### 系统信息管理
- 部门信息管理（增删改查）
- 员工信息管理（增删改查）
- 员工与部门关联管理

### 课程教务管理
- 课程信息管理（增删改查）
- 课程安排与教师绑定
- 课程状态管理

### 数据统计管理
- 员工信息统计报表
- 学员信息统计报表
- 数据可视化展示

### 系统安全
- 基于 JWT 的用户认证
- 统一请求拦截与权限校验
- 全局异常处理

## 项目结构

```
manage system/
├── backend/                          # 后端项目
│   └── src/
│       └── main/
│           ├── java/com/hitweb/
│           │   ├── config/           # 配置类
│           │   ├── controller/       # 控制器层
│           │   ├── entity/           # 请求实体类
│           │   ├── global/           # 全局处理
│           │   ├── interceptor/      # 拦截器
│           │   ├── message/          # 消息处理
│           │   ├── pojo/             # 数据实体类
│           │   ├── repository/       # 数据访问层
│           │   ├── service/          # 服务层
│           │   │   └── impl/         # 服务实现
│           │   └── utils/            # 工具类
│           └── resources/
│               └── application.yaml  # 配置文件
│
└── frontend/                         # 前端项目
    └── vue-admin-template-master/
        ├── public/                   # 静态资源
        ├── src/
        │   ├── api/                  # API 接口
        │   ├── assets/               # 资源文件
        │   ├── components/           # 公共组件
        │   ├── icons/                # 图标
        │   ├── layout/               # 布局组件
        │   ├── router/               # 路由配置
        │   ├── store/                # Vuex 状态管理
        │   ├── styles/               # 样式文件
        │   ├── utils/                # 工具函数
        │   └── views/                # 页面组件
        ├── tests/                    # 测试文件
        └── package.json              # 依赖配置
```

## 安装指南

### 环境要求

- **JDK 17+**
- **Node.js 14+**
- **MySQL 8.0+**
- **Maven 3.6+**

### 后端安装

1. 克隆项目到本地
```bash
git clone <repository-url>
cd "manage system/backend"
```

2. 配置数据库连接，编辑 `src/main/resources/application.yaml`
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database?useSSL=false&serverTimezone=UTC
    username: your_username
    password: your_password
```

3. 配置阿里云 OSS（可选），取消注释并填写相关配置
```yaml
aliyun:
  oss:
    endpoint: your_endpoint
    accessKeyId: your_access_key_id
    accessKeySecret: your_access_key_secret
    bucketName: your_bucket_name
```

4. 构建并运行项目
```bash
mvn clean install
mvn spring-boot:run
```

后端服务将在 `http://localhost:8088` 启动。

### 前端安装

1. 进入前端目录
```bash
cd "../frontend/vue-admin-template-master"
```

2. 安装依赖
```bash
npm install
```

3. 启动开发服务器
```bash
npm run serve
```

前端服务将在 `http://localhost:9528` 启动。

## 使用说明

### 访问系统

1. 打开浏览器访问 `http://localhost:9528`
2. 使用管理员账号登录系统
3. 登录成功后即可使用各项功能

### API 文档

后端启动后，可访问 Knife4j 接口文档：
```
http://localhost:8088/doc.html
```

## 配置方法

### 后端配置

主要配置文件位于 `backend/src/main/resources/application.yaml`：

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| `server.port` | 服务端口 | 8088 |
| `spring.datasource.url` | 数据库连接地址 | - |
| `spring.datasource.username` | 数据库用户名 | - |
| `spring.datasource.password` | 数据库密码 | - |
| `hitweb.jwt.secret` | JWT 密钥 | asdfghjkl |
| `hitweb.jwt.expire` | Token 过期时间 | 7200000000ms |

### 前端配置

环境配置文件：
- `.env.development` - 开发环境配置
- `.env.production` - 生产环境配置
- `.env.staging` - 预发布环境配置

主要配置项：
```javascript
VUE_APP_BASE_API = 'http://localhost:8088'  // 后端 API 地址
```

## 开发指南

### 后端开发

1. 新增实体类：在 `pojo` 包下创建实体类
2. 创建数据访问层：在 `repository` 包下创建 Repository 接口
3. 创建服务层：在 `service` 包下创建接口和实现类
4. 创建控制器：在 `controller` 包下创建 Controller 类

### 前端开发

1. 新增页面：在 `views` 目录下创建 Vue 组件
2. 配置路由：在 `router/index.js` 中添加路由配置
3. 创建 API：在 `api` 目录下创建接口文件
4. 状态管理：在 `store/modules` 下创建 Vuex 模块

### 代码规范

- 后端遵循阿里巴巴 Java 开发规范
- 前端遵循 Vue 官方风格指南
- 提交代码前请确保通过 ESLint 检查

```bash
# 前端代码检查
npm run lint
```

## 构建部署

### 后端打包

```bash
cd backend
mvn clean package -DskipTests
```

打包后的 JAR 文件位于 `target/` 目录下。

### 前端打包

```bash
# 生产环境打包
npm run build:prod

# 预发布环境打包
npm run build:stage
```

打包后的静态文件位于 `dist/` 目录下。

### 部署说明

1. 后端部署：将 JAR 包上传至服务器，使用 `java -jar` 命令启动
2. 前端部署：将 `dist` 目录内容部署至 Nginx 或其他 Web 服务器

## 测试

### 后端测试

```bash
cd backend
mvn test
```

### 前端测试

```bash
cd frontend/vue-admin-template-master
npm run test:unit
```

## 贡献指南

欢迎对本项目进行贡献，请遵循以下步骤：

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

### 贡献规范

- 确保代码风格与项目一致
- 添加必要的测试用例
- 更新相关文档
- 保持提交信息清晰明了

## 许可证

本项目采用 MIT 许可证。详见 [LICENSE](LICENSE) 文件。

## 联系方式

如有问题或建议，请通过以下方式联系：

- 提交 Issue
- 发送邮件至项目维护者

---

**注意**：本项目仅供学习交流使用，请勿用于商业用途。
