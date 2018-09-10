## Use

> ##### 只需要在 Application 中调用 `DevUtils.init()` 进行初始化就行

## 目录结构

```
- dev.utils			| 根目录
	- app 			| app相关工具类
		- anim		| 动画相关
		- assist	| 辅助类, 如 Camera，ScreenSensor
		- cache		| 缓存工具类
		- image		| 图片相关处理
		- info		| App信息, PackageInfo 等
		- logger	| 日志库 DevLogger
		- share		| SharedPreferences 封装
		- toast		| Toast、Toasty
		- wifi		| wifi、热点
	- common		| java工具类, 不依赖android api
		- assist	| 各种快捷辅助类
		- cipher	| 编/解码工具类
		- encrypt	| 加密工具类
		- thread	| 线程相关
		- validator	| 数据校验工具类
```

## 注意事项

- 内部存在两个日志工具类
```
// dev.utils.app - App 打印日志工具类
LogPrintUtils
// dev.utils.common - Java Common 日志打印工具类
JCLogUtils
```

- 需要开启日志, 单独调用
```
// 打开 lib 内部日志
DevUtils.openLog();
// 标示 debug 模式
DevUtils.openDebug();
```

## API