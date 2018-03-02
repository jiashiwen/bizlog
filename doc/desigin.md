# bizlog设计文档

标签（空格分隔）： 设计文档

## 目的
解决业务日志归集、存储、及可视化

## 组件
* logreciver
  负责接受应用体统推送的日志
* mq
  转发由logreciver收集到的日志到logpersistencor，在业务高峰期兼做缓存层。
* logersistencer
  日志持久化组件，主要负责日志入库


---

在此输入正文
