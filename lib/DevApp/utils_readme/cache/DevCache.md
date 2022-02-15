# Cache 工具类

#### 使用演示类 [CacheUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/utils_use/cache/CacheUse.java) 介绍了配置参数及使用

#### 项目类结构 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/cache)

* 缓存工具类（[DevCache.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/cache/DevCache.java)）：缓存工具类, 提供各种保存数据方法

* 缓存管理类（[DevCacheManager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/cache/DevCacheManager.java)）：内部缓存管理类

## API 文档

* **缓存类 ->** [DevCache.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/cache/DevCache.java)

| 方法 | 注释 |
| :- | :- |
| newCache | 获取 DevCache |
| getCachePath | 获取缓存地址 |
| remove | 移除数据 |
| removeForKeys | 删除 Key[] 配置、数据文件 |
| contains | 是否存在 key |
| isDue | 判断某个 key 是否过期 |
| clear | 清除全部数据 |
| clearDue | 清除过期数据 |
| clearType | 清除某个类型的全部数据 |
| getItemByKey | 通过 Key 获取 Item |
| getKeys | 获取有效 Key 集合 |
| getPermanentKeys | 获取永久有效 Key 集合 |
| getCount | 获取有效 Key 数量 |
| getSize | 获取有效 Key 占用总大小 |
| put | 保存 int 类型的数据 |
| getInt | 获取 int 类型的数据 |
| getLong | 获取 long 类型的数据 |
| getFloat | 获取 float 类型的数据 |
| getDouble | 获取 double 类型的数据 |
| getBoolean | 获取 boolean 类型的数据 |
| getString | 获取 String 类型的数据 |
| getBytes | 获取 byte[] 类型的数据 |
| getBitmap | 获取 Bitmap 类型的数据 |
| getDrawable | 获取 Drawable 类型的数据 |
| getSerializable | 获取 Serializable 类型的数据 |
| getParcelable | 获取 Parcelable 类型的数据 |
| getJSONObject | 获取 JSONObject 类型的数据 |
| getJSONArray | 获取 JSONArray 类型的数据 |
| getKey | 获取存储 Key |
| isPermanent | 是否永久有效 |
| getType | 获取数据存储类型 |
| getSaveTime | 获取保存时间 ( 毫秒 ) |
| getValidTime | 获取有效期 ( 毫秒 ) |
| setType | setType |
| setSaveTime | setSaveTime |
| setValidTime | setValidTime |
| isInt | isInt |
| isLong | isLong |
| isFloat | isFloat |
| isDouble | isDouble |
| isBoolean | isBoolean |
| isString | isString |
| isBytes | isBytes |
| isBitmap | isBitmap |
| isDrawable | isDrawable |
| isSerializable | isSerializable |
| isParcelable | isParcelable |
| isJSONObject | isJSONObject |
| isJSONArray | isJSONArray |

#### 使用示例
```java
// 初始化
CacheVo cacheVo = new CacheVo("测试持久化");
// 打印信息
DevEngine.getLog().dTag(TAG, "保存前: %s", cacheVo.toString());
// 保存数据
DevCache.newCache().put("ctv", cacheVo, -1);
// 重新获取
CacheVo ctv = (CacheVo) DevCache.newCache().getSerializable("ctv");
// 打印获取后的数据
DevEngine.getLog().dTag(TAG, "保存后: %s", ctv.toString());
// 设置保存有效时间 5秒
DevCache.newCache().put("ctva", new CacheVo("测试有效时间"), 1);

// 保存到指定文件夹下
DevCache.newCache(
        new File(PathUtils.getSDCard().getSDCardPath(), "Cache").getAbsolutePath()
).put("key", "保存数据", -1);

// 延迟后
new Thread(new Runnable() {
    @Override
    public void run() {
        try {
            // 延迟 1.5 已经过期再去获取
            Thread.sleep(1500);
            // 获取数据
            CacheVo ctva = (CacheVo) DevCache.newCache().getSerializable("ctva");
            // 判断是否过期
            DevEngine.getLog().dTag(TAG, "是否过期: %s", (ctva == null));
        } catch (Exception ignored) {
        }
    }
}).start();
```