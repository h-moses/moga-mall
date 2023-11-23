## 项目简介

本项目是一个基于微服务架构的电商平台，提供用户注册、登录、商品查询、购物车、订单等功能。

## 项目架构

本项目采用微服务架构，使用Spring Boot和Spring Cloud Alibaba构建微服务应用。具体架构如下：

```
├── api-gateway  # 网关服务
├── common  # 公共模块
├── order-service  # 订单服务
├── payment-service  # 支付服务
├── product-service  # 商品服务
├── user-service  # 用户服务
└── cart-service  # 购物车服务
```

## 技术选型

本项目采用以下技术：

- Spring Boot：快速构建微服务应用。
- Spring Cloud Alibaba：提供微服务框架和组件。
- MySQL：存储数据。
- Redis：缓存数据。
- Elasticsearch：搜索和分析数据。
- RabbitMQ：实现消息队列。
- Alipay Pay：实现支付功能。
- JWT：实现用户认证和授权。
- Spring Security：提供安全框架。
- HTTPS：提供安全传输协议。
- Docker：实现容器化部署和管理。

## 数据库设计

本项目采用以下数据库设计：

### 用户表（user）

| 字段名      | 类型     | 描述                          |
| ----------- | -------- | ----------------------------- |
| id          | bigint   | 用户ID                        |
| username    | varchar  | 用户名                        |
| password    | varchar  | 密码                          |
| nickname    | varchar  | 昵称                          |
| gender      | tinyint  | 性别（0：未知，1：男，2：女） |
| birthday    | date     | 生日                          |
| email       | varchar  | 邮箱                          |
| phone       | varchar  | 手机号码                      |
| avatar      | varchar  | 头像                          |
| status      | tinyint  | 状态（0：禁用，1：启用）      |
| create_time | datetime | 创建时间                      |
| update_time | datetime | 更新时间                      |

### 商品表（product）

| 字段名      | 类型     | 描述                         |
| ----------- | -------- | ---------------------------- |
| id          | bigint   | 商品ID                       |
| name        | varchar  | 商品名称                     |
| category_id | bigint   | 商品分类ID                   |
| brand_id    | bigint   | 商品品牌ID                   |
| price       | decimal  | 商品价格                     |
| stock       | int      | 商品库存                     |
| sales       | int      | 商品销量                     |
| image       | varchar  | 商品图片                     |
| status      | tinyint  | 商品状态（0：下架，1：上架） |
| create_time | datetime | 创建时间                     |
| update_time | datetime | 更新时间                     |

### 商品分类表（category）

| 字段名      | 类型     | 描述                         |
| ----------- | -------- | ---------------------------- |
| id          | bigint   | 分类ID                       |
| name        | varchar  | 分类名称                     |
| parent_id   | bigint   | 父分类ID                     |
| level       | int      | 分类级别                     |
| status      | tinyint  | 分类状态（0：禁用，1：启用） |
| create_time | datetime | 创建时间                     |
| update_time | datetime | 更新时间                     |

### 订单表（order）

| 字段名      | 类型     | 描述                                                         |
| ----------- | -------- | ------------------------------------------------------------ |
| id          | bigint   | 订单ID                                                       |
| user_id     | bigint   | 用户ID                                                       |
| product_id  | bigint   | 商品ID                                                       |
| quantity    | int      | 商品数量                                                     |
| price       | decimal  | 商品价格                                                     |
| status      | tinyint  | 订单状态（0：待支付，1：已支付，2：已取消，3：已发货，4：已收货，5：已评价，6：已退款） |
| create_time | datetime | 创建时间                                                     |
| update_time | datetime | 更新时间                                                     |

### 购物车表（cart）

| 字段名      | 类型     | 描述     |
| ----------- | -------- | -------- |
| id          | bigint   | 购物车ID |
| user_id     | bigint   | 用户ID   |
| product_id  | bigint   | 商品ID   |
| quantity    | int      | 商品数量 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

### 物流信息表（logistics）

| 字段名      | 类型     | 描述                                        |
| ----------- | -------- | ------------------------------------------- |
| id          | bigint   | 物流信息ID                                  |
| order_id    | bigint   | 订单ID                                      |
| company     | varchar  | 快递公司                                    |
| number      | varchar  | 快递单号                                    |
| status      | tinyint  | 物流状态（0：未发货，1：已发货，2：已签收） |
| create_time | datetime | 创建时间                                    |
| update_time | datetime | 更新时间                                    |

### 评论表（comment）

| 字段名      | 类型     | 描述                             |
| ----------- | -------- | -------------------------------- |
| id          | bigint   | 评论ID                           |
| user_id     | bigint   | 用户ID                           |
| product_id  | bigint   | 商品ID                           |
| content     | varchar  | 评论内容                         |
| score       | int      | 评分                             |
| status      | tinyint  | 评论状态（0：待审核，1：已审核） |
| create_time | datetime | 创建时间                         |
| update_time | datetime | 更新时间                         |

### 支付记录表（payment）

| 字段名      | 类型     | 描述                                        |
| ----------- | -------- | ------------------------------------------- |
| id          | bigint   | 支付记录ID                                  |
| order_id    | bigint   | 订单ID                                      |
| amount      | decimal  | 支付金额                                    |
| channel     | varchar  | 支付渠道                                    |
| status      | tinyint  | 支付状态（0：未支付，1：已支付，2：已退款） |
| create_time | datetime | 创建时间                                    |
| update_time | datetime | 更新时间                                    |

### 用户地址表（address）

| 字段名      | 类型     | 描述           |
| ----------- | -------- | -------------- |
| id          | bigint   | 地址ID         |
| user_id     | bigint   | 用户ID         |
| name        | varchar  | 收货人姓名     |
| phone       | varchar  | 收货人手机号码 |
| province    | varchar  | 省份           |
| city        | varchar  | 城市           |
| district    | varchar  | 区县           |
| detail      | varchar  | 详细地址       |
| create_time | datetime | 创建时间       |
| update_time | datetime | 更新时间       |

### 商品品牌表（brand）

| 字段名      | 类型     | 描述                         |
| ----------- | -------- | ---------------------------- |
| id          | bigint   | 品牌ID                       |
| name        | varchar  | 品牌名称                     |
| logo        | varchar  | 品牌Logo                     |
| description | varchar  | 品牌描述                     |
| status      | tinyint  | 品牌状态（0：禁用，1：启用） |
| create_time | datetime | 创建时间                     |
| update_time | datetime | 更新时间                     |

### 商品属性表（product_attribute）

| 字段名      | 类型     | 描述     |
| ----------- | -------- | -------- |
| id          | bigint   | 属性ID   |
| product_id  | bigint   | 商品ID   |
| name        | varchar  | 属性名称 |
| value       | varchar  | 属性值   |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

### 商品规格表（product_specification）

| 字段名      | 类型     | 描述     |
| ----------- | -------- | -------- |
| id          | bigint   | 规格ID   |
| product_id  | bigint   | 商品ID   |
| name        | varchar  | 规格名称 |
| value       | varchar  | 规格值   |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

### 商品评价表（product_review）

| 字段名      | 类型     | 描述                             |
| ----------- | -------- | -------------------------------- |
| id          | bigint   | 评价ID                           |
| product_id  | bigint   | 商品ID                           |
| user_id     | bigint   | 用户ID                           |
| content     | varchar  | 评价内容                         |
| score       | int      | 评分                             |
| status      | tinyint  | 评价状态（0：待审核，1：已审核） |
| create_time | datetime | 创建时间                         |
| update_time | datetime | 更新时间                         |

### 商品分类属性表（category_attribute）

| 字段名      | 类型     | 描述                                 |
| ----------- | -------- | ------------------------------------ |
| id          | bigint   | 分类属性ID                           |
| category_id | bigint   | 分类ID                               |
| name        | varchar  | 属性名称                             |
| input_type  | tinyint  | 输入类型（0：手动输入，1：选择输入） |
| value_list  | varchar  | 可选值列表                           |
| create_time | datetime | 创建时间                             |
| update_time | datetime | 更新时间                             |

### 商品分类规格表（category_specification）

| 字段名      | 类型     | 描述       |
| ----------- | -------- | ---------- |
| id          | bigint   | 分类规格ID |
| category_id | bigint   | 分类ID     |
| name        | varchar  | 规格名称   |
| value_list  | varchar  | 可选值列表 |
| create_time | datetime | 创建时间   |
| update_time | datetime | 更新时间   |

### 订单商品表（order_item）

| 字段名      | 类型     | 描述       |
| ----------- | -------- | ---------- |
| id          | bigint   | 订单商品ID |
| order_id    | bigint   | 订单ID     |
| product_id  | bigint   | 商品ID     |
| quantity    | int      | 商品数量   |
| price       | decimal  | 商品价格   |
| create_time | datetime | 创建时间   |
| update_time | datetime | 更新时间   |

### 退款记录表（refund）

| 字段名      | 类型     | 描述                             |
| ----------- | -------- | -------------------------------- |
| id          | bigint   | 退款记录ID                       |
| order_id    | bigint   | 订单ID                           |
| amount      | decimal  | 退款金额                         |
| reason      | varchar  | 退款原因                         |
| status      | tinyint  | 退款状态（0：未处理，1：已处理） |
| create_time | datetime | 创建时间                         |
| update_time | datetime | 更新时间                         |

### 优惠券表（coupon）

| 字段名      | 类型     | 描述                                          |
| ----------- | -------- | --------------------------------------------- |
| id          | bigint   | 优惠券ID                                      |
| name        | varchar  | 优惠券名称                                    |
| description | varchar  | 优惠券描述                                    |
| type        | tinyint  | 优惠券类型（0：满减券，1：折扣券）            |
| value       | decimal  | 优惠券面值                                    |
| min_amount  | decimal  | 最低使用金额                                  |
| start_time  | datetime | 开始时间                                      |
| end_time    | datetime | 结束时间                                      |
| status      | tinyint  | 优惠券状态（0：未开始，1：进行中，2：已结束） |
| create_time | datetime | 创建时间                                      |
| update_time | datetime | 更新时间                                      |

### 用户优惠券表（user_coupon）

| 字段名      | 类型     | 描述                                          |
| ----------- | -------- | --------------------------------------------- |
| id          | bigint   | 用户优惠券ID                                  |
| user_id     | bigint   | 用户ID                                        |
| coupon_id   | bigint   | 优惠券ID                                      |
| status      | tinyint  | 优惠券状态（0：未使用，1：已使用，2：已过期） |
| used_time   | datetime | 使用时间                                      |
| create_time | datetime | 创建时间                                      |
| update_time | datetime | 更新时间                                      |

### 收货地址表（address）

| 字段名      | 类型     | 描述                         |
| ----------- | -------- | ---------------------------- |
| id          | bigint   | 地址ID                       |
| user_id     | bigint   | 用户ID                       |
| name        | varchar  | 收货人姓名                   |
| phone       | varchar  | 收货人电话                   |
| province    | varchar  | 省份                         |
| city        | varchar  | 城市                         |
| district    | varchar  | 区县                         |
| detail      | varchar  | 详细地址                     |
| is_default  | tinyint  | 是否默认地址（0：否，1：是） |
| create_time | datetime | 创建时间                     |
| update_time | datetime | 更新时间                     |

### 购物车表（cart）

| 字段名      | 类型     | 描述     |
| ----------- | -------- | -------- |
| id          | bigint   | 购物车ID |
| user_id     | bigint   | 用户ID   |
| product_id  | bigint   | 商品ID   |
| quantity    | int      | 商品数量 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

### 支付记录表（payment）

| 字段名      | 类型     | 描述                             |
| ----------- | -------- | -------------------------------- |
| id          | bigint   | 支付记录ID                       |
| order_id    | bigint   | 订单ID                           |
| amount      | decimal  | 支付金额                         |
| status      | tinyint  | 支付状态（0：未支付，1：已支付） |
| create_time | datetime | 创建时间                         |
| update_time | datetime | 更新时间                         |

### 物流记录表（shipment）

| 字段名      | 类型     | 描述                                        |
| ----------- | -------- | ------------------------------------------- |
| id          | bigint   | 物流记录ID                                  |
| order_id    | bigint   | 订单ID                                      |
| status      | tinyint  | 物流状态（0：未发货，1：已发货，2：已签收） |
| create_time | datetime | 创建时间                                    |
| update_time | datetime | 更新时间                                    |

## 功能和接口

本项目提供以下功能和接口：

1. 用户服务
   - 用户注册接口：/api/user/register
   - 用户登录接口：/api/user/login
   - 个人信息查询接口：/api/user/info
   - 个人信息修改接口：/api/user/update
   - 第三方登录接口：/api/user/third-party-login
   - 密码找回接口：/api/user/password-retrieve
   - 注销接口：/api/user/logout
   - 权限管理接口：/api/user/permission
   - 头像上传接口：/api/user/avatar
   - 地址管理接口：/api/user/address
   - 用户积分管理接口：/api/user/point
   - 用户等级管理接口：/api/user/level
   - 用户签到接口：/api/user/check-in
2. 商品服务
   - 商品查询接口：/api/product/search
   - 商品详情查询接口：/api/product/detail
   - 商品分类查询接口：/api/product/category
   - 商品搜索接口：/api/product/search
   - 商品秒杀接口：/api/product/seckill
   - 商品促销接口：/api/product/promotion
   - 商品库存管理接口：/api/product/stock
3. 订单服务
   - 订单创建接口：/api/order/create
   - 订单支付接口：/api/order/pay
   - 订单取消接口：/api/order/cancel
   - 订单查询接口：/api/order/query
   - 订单退款接口：/api/order/refund
   - 订单确认接口：/api/order/confirm
   - 物流信息查询接口：/api/order/logistics
   - 订单退货接口：/api/order/return
   - 订单提醒发货接口：/api/order/remind-ship
   - 订单拆分接口：/api/order/split
   - 订单合并接口：/api/order/merge
   - 订单批量查询接口：/api/order/batch-query
4. 购物车服务
   - 购物车添加接口：/api/cart/add
   - 购物车删除接口：/api/cart/delete
   - 购物车修改接口：/api/cart/update
   - 购物车查询接口：/api/cart/query
   - 购物车清空接口：/api/cart/clear
   - 购物车结算接口：/api/cart/settle
   - 购物车商品数量查询接口：/api/cart/quantity
   - 购物车商品优惠接口：/api/cart/discount
5. 支付服务
   - 支付宝支付接口：/api/pay/alipay
   - 支付状态查询接口：/api/pay/query
   - 支付宝支付退款接口：/api/pay/alipay-refund
   - 支付宝支付异步通知接口：/api/pay/alipay-notify
6. 物流服务
   - 物流信息查询接口：/api/logistics/query
   - 订单发货接口：/api/logistics/ship
   - 订单收货接口：/api/logistics/receive
   - 物流信息更新接口：/api/logistics/update
   - 物流信息删除接口：/api/logistics/delete
   - 物流信息批量查询接口：/api/logistics/batch-query
   - 物流信息推送接口：/api/logistics/push

以上是本项目提供的所有功能和接口。

## 接口参数

### 用户服务

1. 用户注册接口（/api/user/register）

- username：用户名，字符串类型，必填
- password：密码，字符串类型，必填
- nickname：昵称，字符串类型，选填
- gender：性别，整型，选填
- birthday：生日，日期类型，选填
- phone：手机号码，字符串类型，必填
- email：邮箱，字符串类型，选填

2. 用户登录接口（/api/user/login）

- username：用户名，字符串类型，必填
- password：密码，字符串类型，必填

3. 个人信息查询接口（/api/user/info）

无需参数

4. 个人信息修改接口（/api/user/update）

- nickname：昵称，字符串类型，选填
- gender：性别，整型，选填
- birthday：生日，日期类型，选填
- phone：手机号码，字符串类型，选填
- email：邮箱，字符串类型，选填

5. 第三方登录接口（/api/user/third-party-login）

- provider：第三方登录提供商，字符串类型，必填
- accessToken：第三方登录授权凭证，字符串类型，必填

6. 密码找回接口（/api/user/password-retrieve）

- username：用户名，字符串类型，必填
- email：邮箱，字符串类型，必填

7. 注销接口（/api/user/logout）

无需参数

8. 权限管理接口（/api/user/permission）

- userId：用户ID，整型，必填

9. 头像上传接口（/api/user/avatar）

- file：头像文件，文件类型，必填

10. 地址管理接口（/api/user/address）

- name：收货人姓名，字符串类型，必填
- phone：收货人手机号码，字符串类型，必填
- province：省份，字符串类型，必填
- city：城市，字符串类型，必填
- district：区县，字符串类型，必填
- detail：详细地址，字符串类型，必填
- isDefault：是否默认地址，整型，选填

11. 用户积分管理接口（/api/user/point）

- userId：用户ID，整型，必填
- type：积分类型，字符串类型，必填
- amount：积分数量，整型，必填

12. 用户等级管理接口（/api/user/level）

- userId：用户ID，整型，必填
- level：等级，整型，必填

13. 用户签到接口（/api/user/check-in）

- userId：用户ID，整型，必填

### 商品服务

1. 商品查询接口（/api/product/search）

- keyword：关键字，字符串类型，选填
- categoryId：分类ID，整型，选填
- orderBy：排序方式，字符串类型，选填
- pageNum：页码，整型，选填
- pageSize：每页数量，整型，选填

2. 商品详情查询接口（/api/product/detail）

- productId：商品ID，整型，必填

3. 商品分类查询接口（/api/product/category）

- parentId：父级分类ID，整型，选填

4. 商品秒杀接口（/api/product/seckill）

- productId：商品ID，整型，必填
- userId：用户ID，整型，必填

5. 商品促销接口（/api/product/promotion）

- productId：商品ID，整型，必填

6. 商品库存管理接口（/api/product/stock）

- productId：商品ID，整型，必填
- stock：库存数量，整型，必填

### 订单服务

1. 订单创建接口（/api/order/create）

- userId：用户ID，整型，必填
- productId：商品ID，整型，必填
- quantity：数量，整型，必填
- addressId：收货地址ID，整型，必填

2. 订单支付接口（/api/order/pay）

- orderId：订单ID，整型，必填
- paymentMethod：支付方式，字符串类型，必填

3. 订单取消接口（/api/order/cancel）

- orderId：订单ID，整型，必填

4. 订单查询接口（/api/order/query）

- orderId：订单ID，整型，选填
- userId：用户ID，整型，选填
- pageNum：页码，整型，选填
- pageSize：每页数量，整型，选填

5. 订单退款接口（/api/order/refund）

- orderId：订单ID，整型，必填
- refundAmount：退款金额，浮点型，必填

6. 订单确认接口（/api/order/confirm）

- orderId：订单ID，整型，必填

8. 物流信息查询接口（/api/order/logistics）

- orderId：订单ID，整型，必填

9. 订单退货接口（/api/order/return）

- orderId：订单ID，整型，必填
- reason：退货原因，字符串类型，必填
- images：退货图片，文件类型，选填

10. 订单提醒发货接口（/api/order/remind-ship）

- orderId：订单ID，整型，必填

11. 订单拆分接口（/api/order/split）

- orderId：订单ID，整型，必填
- subOrderIds：子订单ID列表，数组类型，必填

12. 订单合并接口（/api/order/merge）

- orderId：订单ID，整型，必填
- mergeOrderIds：合并的订单ID列表，数组类型，必填

13. 订单批量查询接口（/api/order/batch-query）

- orderIds：订单ID列表，数组类型，必填

### 购物车服务

1. 购物车添加接口（/api/cart/add）

- userId：用户ID，整型，必填
- productId：商品ID，整型，必填
- quantity：数量，整型，必填

2. 购物车删除接口（/api/cart/delete）

- userId：用户ID，整型，必填
- productId：商品ID，整型，必填

3. 购物车修改接口（/api/cart/update）

- userId：用户ID，整型，必填
- productId：商品ID，整型，必填
- quantity：数量，整型，必填

4. 购物车查询接口（/api/cart/query）

- userId：用户ID，整型，必填

5. 购物车清空接口（/api/cart/clear）

- userId：用户ID，整型，必填

6. 购物车结算接口（/api/cart/settle）

- userId：用户ID，整型，必填
- productIds：商品ID列表，数组类型，必填

7. 购物车商品数量查询接口（/api/cart/quantity）

- userId：用户ID，整型，必填

8. 购物车商品优惠接口（/api/cart/discount）

- userId：用户ID，整型，必填
- productId：商品ID，整型，必填

### 支付服务

1. 支付宝支付接口（/api/pay/alipay）

- orderId：订单ID，整型，必填
- paymentAmount：支付金额，浮点型，必填

3. 支付状态查询接口（/api/pay/query）

- orderId：订单ID，整型，必填

4. 支付宝支付退款接口（/api/pay/alipay-refund）

- orderId：订单ID，整型，必填
- refundAmount：退款金额，浮点型，必填

6. 支付宝支付异步通知接口（/api/pay/alipay-notify）

- 无需参数，支付宝会将异步通知发送到该接口

### 物流服务

1. 物流信息查询接口（/api/logistics/query）

- logisticsId：物流ID，整型，必填

2. 订单发货接口（/api/logistics/ship）

- orderId：订单ID，整型，必填
- logisticsId：物流ID，整型，必填
- logisticsCompany：物流公司，字符串类型，必填
- logisticsNumber：物流单号，字符串类型，必填

3. 订单收货接口（/api/logistics/receive）

- orderId：订单ID，整型，必填

4. 物流信息更新接口（/api/logistics/update）

- logisticsId：物流ID，整型，必填
- logisticsCompany：物流公司，字符串类型，选填
- logisticsNumber：物流单号，字符串类型，选填
- status：物流状态，字符串类型，选填

5. 物流信息删除接口（/api/logistics/delete）

- logisticsId：物流ID，整型，必填

6. 物流信息批量查询接口（/api/logistics/batch-query）

- logisticsIds：物流ID列表，数组类型，必填

7. 物流信息推送接口（/api/logistics/push）

- logisticsId：物流ID，整型，必填
- pushContent：推送内容，字符串类型，必填
