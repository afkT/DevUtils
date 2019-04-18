# SharedPreferences 工具类

#### 使用演示类 [ShareUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/com/dev/utils/share/ShareUse.java) 介绍了配置参数及使用

> 1. apply没有返回值而 commit返回boolean表明修改是否提交成功
> 2. apply是将修改数据原子提交到内存, 而后异步真正提交到硬件磁盘, 而commit是同步的提交到硬件磁盘
> 3. apply方法不会提示任何失败的提示 apply的效率高一些，如果没有必要确认是否提交成功建议使用apply。

#### 项目类结构 - [包目录](https://github.com/afkT/DevUtils/tree/master/DevLibUtils/src/main/java/dev/utils/app/share)

* SharedPreferences 工具类（[SPUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/share/SPUtils.java)）：SP 操作工具类, 实现 IPreferenceHolder 初始化方法

* IPreference 持有类（[IPreferenceHolder.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/share/IPreferenceHolder.java)）：IPreference 持有类，内部返回实现类 IPreference

* IPreference 接口类（[IPreference.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/share/IPreference.java)）：主要是正常操作方法接口类

* PreferenceImpl 接口实现类（[PreferenceImpl.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/share/PreferenceImpl.java)）：实现 IPreference 接口, SharedPreferences 操作接口具体实现类

* SharedPreferences 快捷使用工具类（[SharedUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/share/SharedUtils.java)）：内部实现 SPUtils, 直接进行使用 put/get 等

## API 文档

| 方法 | 注释 |
| :- | :- |
| init | 初始化操作 |
| put | 保存一个数据 |
| putAll | 保存一个 Map 集合(只能是 Integer, Long, Boolean, Float, String, Set) |
| get | 根据 key 取出一个数据 |
| getAll | 取出全部数据 |
| remove | 移除一个数据 |
| removeAll | 移除一个集合的数据 |
| contains | 是否存在 key |
| clear | 清除全部数据 |
| getInt | 获取 int 类型的数据 |
| getFloat | 获取 float 类型的数据 |
| getLong | 获取 long 类型的数据 |
| getBoolean | 获取 boolean 类型的数据 |
| getString | 获取 String 类型的数据 |
| getSet | 获取 Set 类型的数据 |

#### 使用方法
```java
// 具体实现方法 基于 PreferenceImpl 实现

// 存在可调用的方法 IPreference

// SharedUtils 二次分装 SPUtils, 直接调用

// 在DevUtils.init 中初始化了, 实际可以不调用
SharedUtils.init(DevUtils.getContext());

SharedUtils.put("aa", "aa");
SharedUtils.put("ac", 123);

// ===========
// = SPUtils =
// ===========

// 想要自定义 模式，名字等
SPUtils.getPreference(DevUtils.getContext()).put("aa", 1);
SPUtils.getPreference(DevUtils.getContext(), "xxx").put("aa", 1);
SPUtils.getPreference(DevUtils.getContext(), "xxxxx", Context.MODE_PRIVATE).put("aa", 1);


// 默认值如下
switch (type) {
    case INTEGER:
        return preferences.getInt(key, -1);
    case FLOAT:
        return preferences.getFloat(key, -1f);
    case BOOLEAN:
        return preferences.getBoolean(key, false);
    case LONG:
        return preferences.getLong(key, -1L);
    case STRING:
        return preferences.getString(key, null);
    case STRING_SET:
        return preferences.getStringSet(key, null);
    default: // 默认取出String类型的数据
        return null;
}
```