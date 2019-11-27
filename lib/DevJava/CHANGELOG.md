Change Log
==========

Version 1.0.6 *(2019-11-25)*
----------------------------

* `[refactor]` 重构整个项目, 优化代码逻辑判断、代码风格、合并工具类减少包大小等, 并修改 95% 返回值 void 的方法为 boolean 明确获取调用结果
 
* `[Add]` FileRecordUtils 文件记录结果回调

* `[Add]` MapUtils、CollectionUtils 获取泛型数组 toArrayT

* `[Update]` 移动 FileRecordUtils、HtmlUtils 到 Java 模块

Version 1.0.5 *(2019-11-05)*
----------------------------
 
* `[Add]` FileUtils#isImageFormats、isAudioFormats、isVideoFormats、isFileFormats

Version 1.0.4 *(2019-10-31)*
----------------------------
 
* `[Add]` ArrayUtils#getMinimum、getMaximum、getMinimumIndex、getMaximumIndex、sumarray

* `[Add]` CollectionUtils#getMinimum、getMaximum、getMinimumIndex、getMaximumIndex、sumlist

Version 1.0.3 *(2019-10-09)*
----------------------------
 
 * `[Add]` NumberUtils#getMultiple、getMultipleI、getMultipleD、getMultipleL、getMultipleF
 

Version 1.0.2 *(2019-09-19)*
----------------------------
 
 * `[Update]` 修改 FileBreadthFirstSearchUtils 部分方法返回值 ( 返回当前对象, 方便链式调用 )


Version 1.0.1 *(2019-09-12)*
----------------------------
 
 * `[Add]` ConvertUtils#convert


Version 1.0.0 *(2019-08-25)*
----------------------------

 Initial release
