# bizlog设计文档

标签（空格分隔）： 设计文档

## 目的
解决业务日志归集、存储、及可视化

## 工程步骤

 1. 实现多个节点的日志推送和归集
 2. 解决日志格式化及入库问题
 3. 实现可视化及分析功能

## 组件
* logreciver
  负责接受应用体统推送的日志
* mq
  转发由logreciver收集到的日志到logpersistencor，在业务高峰期兼做缓存层。
* logpersistencer
  日志持久化组件，主要负责日志入库


---

### todo

* log4j SocketAppender 及 Socket server的具体实现
  - [x] Scoket Appender具体配置
  - [x] 运行 org.apache.logging.log4j.core.net.server.TcpSocketServer(log4j server示例)
  - [ ] log4j json格式日志配置
  - [x] 编写socket server的简单示例
  - [ ] socket server与springboot集成
* RocketMq相关程序编写
  - [x] 生产者、消费者例程编写
  - [x] 接受通过log4j rocketmq Appender 发送的日志消息
  - [ ] 文件落地方案，按找接收tag异步输出文件，落地文件定期进行归档，归档日志定期删除
  - [ ] rocketmq Appender与springboot集成
- [ ] logpersistencer设计工作
- [ ] elasticsearch 模板设计
- [ ] 数据可视化方案
- [ ] 系统多租户方案

---
### 注意事项
* socket appender 的PatternLayout标签必须与console配置一致否则服务器端输出结果错误
* springboot1.5目前不支持log4j 2.7以上版本导致http appender不可用

---
### Q&A

#### 如何启动log4j2自带的TcpSocketServer
* 下载jcommander并解压编译
```
wget https://github.com/cbeust/jcommander/archive/1.71.tar.gz
tar zxvf 1.71.tar.gz
cd jcommander-1.71
./kobaltw assemble
```
* 将jcommander-1.71.jar log4j-api-2.7.jar log4j-core-2.7.jar复制到统一路径下
```
mkdir /tmp/log4jsocketserver
cp /tmp/jcommander-1.71//kobaltBuild/libs/jcommander-1.71.jar /tmp/log4jsocketserver/
cp  ~/.m2/repository/org/apache/logging/log4j/log4j-api/2.7/log4j-api-2.7.jar /tmp/log4jsocketserver/
cp ~/.m2/repository/org/apache/logging/log4j/log4j-core/2.7/log4j-core-2.7.jar /tmp/log4jsocketserver/

```
* 查看帮助文档
```
cd /tmp/log4jsocketserver
java -classpath jcommander-1.71.jar:log4j-api-2.7.jar:log4j-core-2.7.r  org.apache.logging.log4j.core.net.server.TcpSocketServer -h
```
* 编写配置文件
```
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
  <!-- Appenders 输出目的地 -->
  <Appenders>
    <!-- 控制台输出 -->
    <Console name="STDOUT" target="SYSTEM_OUT">
    <!-- 输出格式  布局-->
      <PatternLayout pattern="%d %-5p [%t] %C{2} (%F:%L) - %m%n"/>
    </Console>
<File name="MyFile" fileName="D:/logs/log4j2test.log">  
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %C{2} (%F:%L) %L- %msg%n" />  
    </File> 
  </Appenders>
  <Loggers>
  <!-- debug级别 -->
    <Root level="debug">
      <AppenderRef ref="MyFile"/>
      <AppenderRef ref="STDOUT"/>
    </Root>
    <Logger name="apiall" level="ERROR" additivity="false">
<AppenderRef ref="MyFile" />
</Logger>
  </Loggers>
</Configuration>
```
* 启动socketserver
```
java -classpath jcommander-1.71.jar:log4j-api-2.7.jar:log4j-core-2.7.r  org.apache.logging.log4j.core.net.server.TcpSocketServer  -c log2jtext.xml
```
* 备注
2.7中的这部分程序可能有问题通过-p指定端口会报错

---
### springboot 集成log4j2并使用Rocketmq Appender
参考文档http://www.ifuun.com/a201801219263080/
---


### 遇到的问题与坑
---
#### springboot配置log4j2的Http Appender后启动报错
```
main ERROR Error processing element Http ([Appenders: null]): CLASS_NOT_FOUND
```
* 问题原因
springboot版本1.5.9，依赖spring-boot-starter-log4j2包含的log4j版本为2.7，不包含HTTP Appender，google一下，该版本对于2.7版本以上的log4j都不兼容，官方预计在2.0.0支持高版本log4j

---
### 使用rocketmq Appender 报错 org.apache.rocketmq.remoting.exception.RemotingConnectException: connect to <10.204.12.33:10909> failed

主要是因为插件访问vip，但通过rokcetmq console 开器vip改错误依旧存在







