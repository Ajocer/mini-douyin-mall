# mini-douyin-mall
随着移动互联网的普及和消费者购物习惯的变化，社交电商呈现出蓬勃发展的趋势。
    抖音作为一款拥有庞大用户群体的短视频社交平台，具有巨大的电商潜力。
        通过搭建电商平台，抖音可以为用户提供更加丰富的购物体验，
            同时为商家提供新的销售渠道，实现用户、商家和平台的多赢局面。

一句话做一个“简易版”抖音商城。为用户提供便捷、优质的购物环境.
满足用户多样化的购物需求，打造一个具有影响力的社交电商平台，提升抖音在电商领域的市场竞争力。

# 技术需求
## （一）注册中心集成

### 1. 服务注册与发现

- 该服务能够与注册中心（如 Consul、Nacos 、etcd 等）进行集成，自动注册服务数据。

## （二）身份认证

### 1. 登录认证

- 可以使用第三方现成的登录验证框架（CasBin、Satoken等），对请求进行身份验证
- 可配置的认证白名单，对于某些不需要认证的接口或路径，允许直接访问
- 可配置的黑名单，对于某些异常的用户，直接进行封禁处理（可选）

### 2. 权限认证（高级）

- 根据用户的角色和权限，对请求进行授权检查，确保只有具有相应权限的用户能够访问特定的服务或接口。
- 支持正则表达模式的权限匹配（加分项）
- 支持动态更新用户权限信息，当用户权限发生变化时，权限校验能够实时生效。

##   （三）可观测要求

### 1. 日志记录与监控

- 对服务的运行状态和请求处理过程进行详细的日志记录，方便故障排查和性能分析。
- 提供实时监控功能，能够及时发现和解决系统中的问题。

##   （四）可靠性要求（高级）

### 1. 容错机制

- 该服务应具备一定的容错能力，当出现部分下游服务不可用或网络故障时，能够自动切换到备用服务或进行降级处理。
- 保证下游在异常情况下，系统的整体可用性不会受太大影响，且核心服务可用。
- 服务应该具有一定的流量兜底措施，在服务流量激增时，应该给予一定的限流措施。



# 功能需求

# 认证中心
- 分发身份令牌
- 续期身份令牌（高级）
- 校验身份令牌

# 用户服务
- 创建用户
- 登录
- 用户登出（可选）
- 删除用户（可选）
- 更新用户（可选）
- 获取用户身份信息


#   商品服务
- 创建商品（可选）
- 修改商品信息（可选）
- 删除商品（可选）
- 查询商品信息（单个商品、批量商品）


#   购物车服务
- 创建购物车
- 清空购物车
- 获取购物车信息


#   订单服务
- 创建订单
- 修改订单信息（可选）
- 订单定时取消（高级）

#   结算

- 订单结算
  
#   支付

- 取消支付（高级）
- 定时取消支付（高级）
- 支付

#   AI大模型

- 订单查询
- 模拟自动下单