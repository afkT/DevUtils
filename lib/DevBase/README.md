
## Gradle

```java
implementation 'com.afkt:DevBase:1.0.0'
```

## 目录结构

```
- dev.base            | 根目录
   - able             | 基类接口
   - activity         | 核心 Base Activity 代码
   - expand           | 基于 Base Activity、Fragment 扩展包
      - content       | 内置 XML Layout 为 Content View 基类封装
      - mvp           | MVP 基础架构封装
      - viewbinding   | ViewBinding 基类封装
   - fragment         | 核心 Base Fragment 代码
   - utils            | 基类依赖工具包
      - assist        | 功能辅助类 ( 抽取通用代码 )
```


