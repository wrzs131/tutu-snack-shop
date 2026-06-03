# 🍬 图图零食店 (Tutu Snack Shop)

> 一款基于 AI 智能体的移动端电商零食店应用

---

## 📖 项目简介

**图图零食店** 是一款基于 AI 智能体的移动端电商零食店应用，其智能体 **"构想"** 通过自然交互、个性化决策和全流程协同能力，为用户提供智能化的零食店下单服务，让零食店下单体验更高效、更个性化、更愉悦。

---

## ✨ 核心特色

- 🤖 **AI 智能体"构想"** - 集成百度千帆 ERNIE-3.5-8K 大模型，提供智能对话和推荐
- 🛒 **完整购物流程** - 商品浏览 → 加入购物车 → 订单创建 → 状态管理
- 📱 **多端适配** - Vue3 + TypeScript 前端，UniApp 转换为微信小程序
- 🔐 **安全认证** - JWT Token 认证机制，保障用户数据安全
- 🎨 **优雅的代码结构** - 采用分层架构，代码规范，易于维护

---

## 🛠️ 技术栈

### 前端技术栈
- **Vue 3** - 渐进式 JavaScript 框架
- **TypeScript** - 类型安全的 JavaScript 超集
- **UniApp** - 一套代码，多端运行（微信小程序、H5、App 等）

### 后端技术栈
- **Java 17** - 最新 LTS 版本
- **Spring Boot 3.5.11** - 企业级 Java 开发框架
- **MyBatis 3.0.5** - 持久层框架
- **MySQL** - 关系型数据库
- **百度千帆 ERNIE-3.5-8K** - 大语言模型 API
- **JWT (jjwt 0.9.1)** - 无状态身份认证
- **PageHelper 1.4.7** - 分页插件
- **Knife4j 4.1.0** - API 文档生成

### 构建工具
- **Maven** - Java 项目构建和依赖管理

---

## 🏗️ 系统架构

### 模块分层架构

```
┌─────────────────────────────────────────┐
│         前端 (Vue3 + TS + UniApp)        │
│  ┌──────────────┐ ┌─────────────────┐  │
│  │  商品浏览页面 │ │  购物车页面      │  │
│  └──────────────┘ └─────────────────┘  │
│  ┌──────────────┐ ┌─────────────────┐  │
│  │  订单管理页面 │ │  AI 对话页面     │  │
│  └──────────────┘ └─────────────────┘  │
└────────────────┬────────────────────────┘
                 │ HTTP/REST API
                 │
┌────────────────▼────────────────────────┐
│           后端 (Java + Spring Boot)      │
│                                          │
│  ┌───────────┐   ┌───────────────┐      │
│  │  Controller │ ↔   Service 层  │      │
│  └───────────┘   └───────┬───────┘      │
│                          │                │
│  ┌───────────┐   ┌──────▼───────┐      │
│  │   Mapper  │ ↔   Entity/VO/DTO │      │
│  └───────────┘   └───────────────┘      │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│           数据库 (MySQL)                 │
│                                          │
│  user, product, category, cart,          │
│  order, order_item, shop                 │
└──────────────────────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│         百度千帆平台 (Qianfan)           │
│                                          │
│  ERNIE-3.5-8K 大语言模型                  │
│  "构想" 智能体对话服务                    │
└──────────────────────────────────────────┘
```

---

## 📂 项目结构

### 后端项目结构

```
lspevent/
├── src/
│   ├── main/
│   │   ├── java/com/itheima/
│   │   │   ├── config/              # 配置类
│   │   │   │   ├── WebMvcConfig.java       # Web MVC 配置（拦截器注册）
│   │   │   │   └── CorsConfig.java          # 跨域配置
│   │   │   ├── context/             # 上下文
│   │   │   │   └── BaseContext.java       # 线程上下文（存储当前用户ID）
│   │   │   ├── controller/          # 控制器层（API 接口）
│   │   │   │   ├── UserController.java     # 用户管理接口
│   │   │   │   ├── ProductController.java  # 商品管理接口
│   │   │   │   ├── CategoryController.java # 分类管理接口
│   │   │   │   ├── CartController.java     # 购物车管理接口
│   │   │   │   ├── OrderController.java    # 订单管理接口
│   │   │   │   ├── OrderItemController.java # 订单项管理接口
│   │   │   │   ├── QianfanController.java  # 百度千帆 AI 接口
│   │   │   │   ├── ShopController.java     # 店铺管理接口
│   │   │   │   └── CommonController.java   # 通用接口
│   │   │   ├── service/             # 服务层
│   │   │   │   ├── UserService.java        # 用户服务接口
│   │   │   │   ├── ProductService.java     # 商品服务接口
│   │   │   │   ├── CartService.java        # 购物车服务接口
│   │   │   │   ├── OrderService.java       # 订单服务接口
│   │   │   │   ├── QianfanService.java     # 千帆 AI 服务接口
│   │   │   │   └── impl/                   # 服务实现
│   │   │   ├── mapper/              # 数据访问层
│   │   │   │   ├── UserMapper.java         # 用户数据访问
│   │   │   │   ├── ProductMapper.java      # 商品数据访问
│   │   │   │   ├── CartMapper.java         # 购物车数据访问
│   │   │   │   ├── OrderMapper.java        # 订单数据访问
│   │   │   │   └── OrderItemMapper.java    # 订单项数据访问
│   │   │   ├── pojo/                # 实体类
│   │   │   │   ├── User.java               # 用户实体
│   │   │   │   ├── Product.java            # 商品实体
│   │   │   │   ├── Cart.java               # 购物车实体
│   │   │   │   ├── Order.java              # 订单实体
│   │   │   │   ├── OrderItem.java          # 订单项实体
│   │   │   │   └── ...
│   │   │   ├── vo/                  # 视图对象
│   │   │   │   ├── CartVO.java             # 购物车视图
│   │   │   │   ├── CartListVO.java         # 购物车列表视图
│   │   │   │   ├── UserLoginVO.java        # 用户登录视图
│   │   │   │   ├── ChatResponseVO.java     # AI 响应视图
│   │   │   │   └── OrderItemVO.java        # 订单项视图
│   │   │   ├── dto/                 # 数据传输对象
│   │   │   │   ├── CartDTO.java            # 购物车请求数据
│   │   │   │   ├── ChatRequestDTO.java     # AI 对话请求
│   │   │   │   ├── UserLoginDTO.java       # 用户登录请求
│   │   │   │   └── Message.java            # 消息对象
│   │   │   ├── properties/          # 配置属性类
│   │   │   │   ├── JwtProperties.java      # JWT 配置
│   │   │   │   ├── QianfanProperties.java  # 千帆配置
│   │   │   │   └── WeChatProperties.java   # 微信配置
│   │   │   ├── interceptor/         # 拦截器
│   │   │   │   └── JwtTokenUserInterceptor.java # JWT 令牌拦截器
│   │   │   ├── utils/               # 工具类
│   │   │   │   ├── JwtUtils.java           # JWT 工具
│   │   │   │   └── HttpClientUtil.java     # HTTP 客户端工具
│   │   │   └── exception/           # 异常处理
│   │   ├── resources/
│   │   │   ├── application.yml               # 主配置文件
│   │   │   ├── application-dev.yml           # 开发环境配置
│   │   │   ├── com.itheima.mapper/           # MyBatis XML 映射
│   │   │   │   ├── UserMapper.xml
│   │   │   │   ├── ProductMapper.xml
│   │   │   │   ├── CartMapper.xml
│   │   │   │   ├── OrderMapper.xml
│   │   │   │   └── OrderItemMapper.xml
│   │   │   ├── static/                       # 静态资源
│   │   │   └── upload/                       # 上传资源
│   │   └── LspeventApplication.java           # 应用启动类
├── pom.xml                                     # Maven 配置
└── README.md
```

---

## 🔌 API 接口文档

### 用户管理接口 (`/user`)

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/user/login` | 用户登录（微信授权） |
| GET | `/user/list` | 查询用户列表 |
| GET | `/user/getById/{id}` | 根据ID查询用户 |
| GET | `/user/getByPhone/{phone}` | 根据手机号查询用户 |
| POST | `/user/insert` | 新增用户 |
| PUT | `/user/update` | 更新用户信息 |
| DELETE | `/user/delete/{id}` | 删除用户 |

### 商品管理接口 (`/product`)

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/product/list` | 查询商品列表 |
| GET | `/product/listByCategoryId/{categoryId}` | 根据分类ID查询商品 |
| GET | `/product/getById/{id}` | 根据ID查询商品详情 |
| POST | `/product/insert` | 新增商品 |
| PUT | `/product/update` | 更新商品信息 |
| DELETE | `/product/delete/{id}` | 删除商品 |
| PUT | `/product/updateStock` | 更新商品库存 |

### 购物车管理接口 (`/cart`)

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/cart/list` | 查询当前用户购物车列表 |
| POST | `/cart/add` | 添加商品到购物车 |
| PUT | `/cart/update` | 更新购物车商品数量 |
| DELETE | `/cart/delete/{id}` | 删除购物车中的商品 |
| DELETE | `/cart/clear` | 清空购物车 |

### 订单管理接口 (`/order`)

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/order/list` | 订单分页查询 |
| GET | `/order/getById/{id}` | 根据ID查询订单 |
| POST | `/order/insert` | 新增订单 |
| PUT | `/order/update` | 更新订单 |
| DELETE | `/order/delete/{id}` | 删除订单 |
| GET | `/order/listByUserId/{userId}` | 根据用户ID查询订单 |
| POST | `/order/create` | 从购物车创建订单 |
| PUT | `/order/updateStatus` | 更新订单状态 |

### 订单项管理接口 (`/orderItem`)

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/orderItem/list` | 查询订单项列表 |
| GET | `/orderItem/getById/{id}` | 根据ID查询订单项 |
| POST | `/orderItem/insert` | 新增订单项 |
| PUT | `/orderItem/update` | 更新订单项 |
| DELETE | `/orderItem/delete/{id}` | 删除订单项 |

### 分类管理接口 (`/category`)

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/category/list` | 查询分类列表 |
| GET | `/category/getById/{id}` | 根据ID查询分类 |
| POST | `/category/insert` | 新增分类 |
| PUT | `/category/update` | 更新分类 |
| DELETE | `/category/delete/{id}` | 删除分类 |

### 百度千帆 AI 接口 (`/qianfan`)

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/qianfan/chat` | 与"构想"智能体对话 |

#### AI 对话请求示例
```json
POST /qianfan/chat
Content-Type: application/json

{
  "messages": [
    {
      "role": "user",
      "content": "什么零食比较好吃？限20字回答"
    }
  ]
}
```

#### AI 对话响应示例
```json
{
  "code": 1,
  "message": "成功",
  "data": {
    "content": "薯片酥脆、巧克力醇厚、果冻Q弹都好吃"
  }
}
```

---

## 🗄️ 数据库设计

### 核心数据表

#### user (用户表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 用户ID（主键） |
| openid | VARCHAR | 微信用户标识 |
| nickname | VARCHAR | 用户昵称 |
| phone | VARCHAR | 手机号 |
| avatar | VARCHAR | 头像URL |
| created_at | DATETIME | 创建时间 |
| updated_at | DATETIME | 更新时间 |

#### product (商品表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 商品ID（主键） |
| name | VARCHAR | 商品名称 |
| description | TEXT | 商品描述 |
| price | DECIMAL | 商品价格 |
| stock | INT | 库存数量 |
| category_id | INT | 分类ID |
| image | VARCHAR | 商品图片URL |
| created_at | DATETIME | 创建时间 |
| updated_at | DATETIME | 更新时间 |

#### category (分类表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 分类ID（主键） |
| name | VARCHAR | 分类名称 |
| description | VARCHAR | 分类描述 |
| created_at | DATETIME | 创建时间 |
| updated_at | DATETIME | 更新时间 |

#### cart (购物车表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 购物车ID（主键） |
| user_id | INT | 用户ID（索引） |
| product_id | INT | 商品ID（索引） |
| quantity | INT | 商品数量 |
| price | DECIMAL | 商品单价 |
| created_at | DATETIME | 创建时间 |
| updated_at | DATETIME | 更新时间 |

> **注意**: `user_id + product_id` 为唯一索引，确保同一用户同一商品只存在一条记录

#### order (订单表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 订单ID（主键） |
| user_id | INT | 用户ID |
| total_amount | DECIMAL | 订单总金额 |
| status | VARCHAR | 订单状态（待确认、已确认、已取消等） |
| created_at | DATETIME | 创建时间 |
| updated_at | DATETIME | 更新时间 |

#### order_item (订单项表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 订单项ID（主键） |
| order_id | INT | 订单ID（索引） |
| product_id | INT | 商品ID |
| quantity | INT | 商品数量 |
| price | DECIMAL | 商品单价 |
| created_at | DATETIME | 创建时间 |

#### shop (店铺表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 店铺ID（主键） |
| name | VARCHAR | 店铺名称 |
| description | TEXT | 店铺描述 |
| ... | ... | ... |

---

## 🚀 快速开始

### 环境要求

| 工具 | 版本要求 |
|------|----------|
| JDK | 17 或更高 |
| Maven | 3.6 或更高 |
| MySQL | 8.0 或更高 |
| Node.js | 16 或更高（前端开发） |

### 后端启动步骤

1. **克隆项目到本地**
   ```bash
   git clone <repository-url>
   cd lspevent
   ```

2. **配置数据库**

   创建数据库并导入初始数据：
   ```sql
   CREATE DATABASE heima_shop DEFAULT CHARACTER SET utf8mb4;
   USE heima_shop;
   -- 导入项目中的 SQL 脚本（tutu-snack-shop.sql）
   ```

3. **修改配置文件**

   在 [application-dev.yml](file:///d:/Java/LSPxcxEvent/lspevent/src/main/resources/application-dev.yml) 中修改数据库连接信息：
   ```yaml
   sky:
     datasource:
       driver-class-name: com.mysql.cj.jdbc.Driver
       host: localhost
       port: 3306
       database: heima_shop
       username: your_username
       password: your_password
   ```

4. **配置百度千帆 API**

   在配置文件中配置您的百度千帆 API 密钥：
   ```yaml
   sky:
     qianfan:
       access-key: your_access_key
       secret-key: your_secret_key
   ```

   > 如何获取 API 密钥？请访问 [百度智能云千帆平台](https://cloud.baidu.com/doc/qianfan-api/s/3m7of64lb) 注册并申请。

5. **编译并运行项目**
   ```bash
   mvn clean compile
   mvn spring-boot:run
   ```

   或者直接运行启动类：
   ```bash
   mvn package
   java -jar target/lspevent-0.0.1-SNAPSHOT.jar
   ```

6. **访问应用**

   - 后端服务: `http://localhost:8080`
   - API 文档 (Knife4j): `http://localhost:8080/doc.html`

### 前端启动步骤

1. **进入前端项目目录**
   ```bash
   cd tutu-snack-shop-fronted
   ```

2. **安装依赖**
   ```bash
   npm install
   ```

3. **启动开发服务器**
   ```bash
   npm run dev
   ```

4. **编译为微信小程序**
   ```bash
   npm run build:mp-weixin
   ```

---

## 🔧 核心功能实现

### 1. 购物车模块

- **智能添加**: 同一商品重复添加时自动累加数量，不创建重复记录
- **实时计算**: 自动计算每个商品的小计和购物车总金额
- **关联查询**: 查询时自动关联商品名称、图片等信息
- **用户隔离**: 通过 JWT 识别用户，数据严格隔离

### 2. 订单模块

- **从购物车创建**: 一键将购物车商品转为订单
- **自动生成 ID**: 订单 ID 自动递增生成（无需依赖数据库自增）
- **事务管理**: 订单创建、订单项插入、购物车清空在同一事务中执行
- **状态管理**: 支持更新订单状态（待确认 → 已确认 → 已取消 等）

### 3. AI 智能体"构想"

- **百度千帆集成**: 使用官方 Java SDK 调用 ERNIE-3.5-8K 模型
- **多轮对话**: 支持消息列表传递，实现上下文理解
- **自然交互**: 可以用自然语言推荐零食、回答问题
- **个性化服务**: 结合用户历史数据提供个性化建议

### 4. 用户认证

- **微信登录**: 支持通过微信 code 授权登录
- **JWT Token**: 登录后生成 JWT Token 返回前端
- **自动解析**: 请求拦截器自动从 Token 解析用户 ID
- **线程安全**: 使用 ThreadLocal 存储当前用户上下文
- **兜底策略**: 未登录时默认使用用户 ID=1，便于测试和体验

---

## 📝 代码规范

### 命名规范
- **Controller 层**: 使用 RESTful 风格，方法名清晰表达业务含义
- **Service 层**: 接口定义规范，实现类放在 `impl` 子包下
- **Mapper 层**: 方法名与 XML 中的 SQL ID 严格对应
- **POJO/VO/DTO**:
  - `POJO`: 数据库表对应的实体类
  - `VO`: 返回给前端的视图对象（View Object）
  - `DTO`: 前端传入的请求数据（Data Transfer Object）

### 分层设计原则
1. **Controller**: 接收请求，参数校验，调用 Service，返回响应
2. **Service**: 业务逻辑处理，事务管理，调用 Mapper
3. **Mapper**: 纯数据访问，只做 CRUD 操作
4. **Pojo/VO/DTO**: 数据承载，不包含业务逻辑

---

## 🧪 测试建议

### API 测试
启动服务后访问 Knife4j 文档：`http://localhost:8080/doc.html`

可以依次测试以下流程：

1. **用户登录** → `POST /user/login`
2. **浏览商品** → `GET /product/list`
3. **加入购物车** → `POST /cart/add`
4. **查看购物车** → `GET /cart/list`
5. **创建订单** → `POST /order/create`
6. **更新订单状态** → `PUT /order/updateStatus?id=1&status=已确认`
7. **体验 AI** → `POST /qianfan/chat`

### Postman 测试
所有接口均为标准 RESTful API，支持 Postman 或 Apifox 进行测试。

---

## 📚 相关文档

- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [MyBatis 官方文档](https://mybatis.org/mybatis-3/)
- [百度千帆 API 文档](https://cloud.baidu.com/doc/qianfan-api/s/3m7of64lb)
- [Knife4j 文档](https://doc.xiaominfo.com/docs/quick-start)
- [UniApp 官方文档](https://uniapp.dcloud.net.cn/)
- [Vue 3 官方文档](https://cn.vuejs.org/)

---

## 🌟 项目亮点

1. **完整的电商闭环** - 从商品浏览到订单创建的全流程实现
2. **AI 深度融合** - 将大语言模型与电商场景结合，提升用户体验
3. **规范的代码结构** - 清晰的分层设计，易于维护和扩展
4. **完善的 API 文档** - 集成 Knife4j，接口文档自动生成
5. **灵活的认证机制** - JWT + 拦截器，既安全又便于调试
6. **事务完整性** - 订单创建等关键操作使用事务管理
7. **多端适配能力** - 一套前端代码可编译为 Web 和小程序

---

## 📄 License

MIT License

---

## 🙏 致谢

感谢以下开源项目和平台的支持：

- 🌱 **Spring Boot** - 强大的后端框架
- 🔥 **MyBatis** - 灵活的持久层框架
- 🤖 **百度千帆** - 优秀的中文大语言模型
- 📱 **UniApp** - 便捷的跨端开发方案
- ✨ **Knife4j** - 美观的 API 文档工具

---

## 📮 联系方式

如有问题或建议，欢迎交流讨论！

---

> **图图零食店** - 让零食购物体验更智能、更愉悦！🍬
