# 参考：URL 模板与示例

## Maven Central Search（Solr）

```
https://search.maven.org/solrsearch/select?q=g:"com.squareup.okhttp3"+AND+a:"okhttp"&core=gav&rows=50&wt=json
```

响应中 `response.docs[].v` 为版本字符串列表，需按语义版本排序后结合「稳定版策略」选取。

## maven-metadata.xml

```
https://repo1.maven.org/maven2/com/squareup/okhttp3/okhttp/maven-metadata.xml
```

## JitPack（示例，以实际坐标为准）

- 构建列表：`https://jitpack.io/#User/Repo` 页面与 API 文档：https://docs.jitpack.io/  
- 常见依赖串：`com.github.USER:Repo:Tag`

## GitHub Releases

```
https://api.github.com/repos/square/okhttp/releases/latest
https://github.com/square/okhttp/releases
```

## Gradle Plugin Portal

```
https://plugins.gradle.org/plugin/com.android.application
```

## AndroidX

```
https://developer.android.com/jetpack/androidx/releases/room
```

（将 `room` 换为对应 artifact 模块名。）

## 本仓库注释风格示例

与 `config_libs.gradle` 邻居一致即可，例如：

```groovy
// https://mvnrepository.com/artifact/com.google.code.gson/gson
// Gson https://github.com/google/gson
gson : "com.google.code.gson:gson:2.13.2",
```

升级后保留 mvnrepository 行（若已有）；**若无** GitHub 行且已确认仓库，补第二行简述 + GitHub URL。
