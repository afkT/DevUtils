# Cache 工具类

#### 使用演示类 [CacheUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/utils_use/cache/CacheUse.java) 介绍了配置参数及使用

#### 项目类结构 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/cache)

* 缓存工具类（[DevCache.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/cache/DevCache.java)）：缓存工具类, 提供各种保存数据方法

* 缓存管理类（[DevCacheManager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/cache/DevCacheManager.java)）：内部缓存管理类

* 缓存处理工具类（[DevCacheUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/cache/DevCacheUtils.java)）：内部缓存处理工具类, 判断是否过期等各种方法

## API 文档

| 方法 | 注释 |
| :- | :- |
| newCache | 获取 DevCache - 默认缓存文件名 |
| put | 保存 String 数据到缓存中 |
| getAsString | 读取 String 数据 |
| getAsJSONObject | 读取 JSONObject 数据 |
| getAsJSONArray | 读取 JSONArray 数据 |
| get | 获取对应 key 的 File 输入流 |
| getAsBinary | 获取 byte[] 数据 |
| getAsObject | 读取 Serializable 数据 |
| getAsBitmap | 读取 Bitmap 数据 |
| getAsDrawable | 读取 Drawable 数据 |
| file | 获取缓存文件 |
| remove | 移除某个 key 的数据 |
| clear | 清除所有数据 |

#### 使用方法
```java
// 初始化
CacheVo cacheVo = new CacheVo("测试持久化");
// 打印信息
DevLogger.dTag(TAG, "保存前: %s", cacheVo.toString());
// 保存数据
DevCache.newCache().put("ctv", cacheVo);
// 重新获取
CacheVo ctv = (CacheVo) DevCache.newCache().getAsObject("ctv");
// 打印获取后的数据
DevLogger.dTag(TAG, "保存后: %s", ctv.toString());
// 设置保存有效时间 5秒
DevCache.newCache().put("ctva", new CacheVo("测试有效时间"), 1);

// 保存到指定文件夹下
DevCache.newCache(new File(PathUtils.getSDCard().getSDCardPath(), "Cache")).put("key", "保存数据");

// 延迟后
new Thread(new Runnable() {
    @Override
    public void run() {
        try {
            // 延迟 1.5 已经过期再去获取
            Thread.sleep(1500);
            // 获取数据
            CacheVo ctva = (CacheVo) DevCache.newCache().getAsObject("ctva");
            // 判断是否过期
            DevLogger.dTag(TAG, "是否过期: %s", (ctva == null));
        } catch (Exception e) {
        }
    }
}).start();
```