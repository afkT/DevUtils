
## Gradle

```gradle
// DevAssist - 封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等
implementation 'io.github.afkt:DevAssist:1.4.7'
```

## 目录结构

```
- dev                           | 根目录
   - adapter                    | 适配器相关
   - assist                     | 快捷功能辅助类
   - base                       | 实体类基类相关
      - data                    | 数据操作
      - entry                   | KeyValue 实体类
      - multiselect             | 多选编辑操作
      - number                  | 数值操作
      - state                   | 状态相关
   - callback                   | 接口回调相关
   - engine                     | 兼容 Engine
      - analytics               | Analytics Engine 数据统计 ( 埋点 )
      - barcode                 | BarCode Engine 条形码、二维码处理
         - listener             | 条形码、二维码操作回调事件
      - cache                   | Cache Engine 有效期键值对缓存
      - compress                | Image Compress Engine 图片压缩
         - listener             | 图片压缩回调事件
      - debug                   | Debug 编译辅助开发 Engine
      - eventbus                | EventBus Engine 事件总线
      - image                   | Image Engine 图片加载、下载、转格式等
         - listener             | 图片加载监听事件
      - json                    | JSON Engine 映射
      - keyvalue                | KeyValue Engine 键值对存储
      - log                     | Log Engine 日志打印
      - media                   | Media Selector Engine 多媒体资源选择
      - permission              | Permission Engine 权限申请
      - popnotification         | PopNotification Engine 简单通知提示
      - poptip                  | PopTip Engine 非阻断式文本提示
      - push                    | Push Engine 推送平台处理
      - refresh                 | Refresh Engine 下拉刷新、上拉加载 View
      - router                  | Router Engine 页面路由
      - share                   | Share Engine 分享平台处理
         - listener             | 分享回调事件
      - storage                 | Storage Engine 外部、内部文件存储
         - listener             | Storage 存储结果事件
      - toast                   | Toast Engine 吐司提示
      - web                     | WebView Engine 网页加载、配置、JS 交互
   - function                   | 快捷方法执行相关
```


## 事项

- 部分 API 更新不及时或有遗漏等，`具体以对应的工具类为准`

- [检测代码规范、注释内容排版，API 文档生成](https://github.com/afkT/JavaDoc)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/CHANGELOG.md)

## API


- dev                                                                 | 根目录
   - [adapter](#devadapter)                                           | 适配器相关
   - [assist](#devassist)                                             | 快捷功能辅助类
   - [base](#devbase)                                                 | 实体类基类相关
      - [data](#devbasedata)                                          | 数据操作
      - [entry](#devbaseentry)                                        | KeyValue 实体类
      - [multiselect](#devbasemultiselect)                            | 多选编辑操作
      - [number](#devbasenumber)                                      | 数值操作
      - [state](#devbasestate)                                        | 状态相关
   - [callback](#devcallback)                                         | 接口回调相关
   - [engine](#devengine)                                             | 兼容 Engine
      - [analytics](#devengineanalytics)                              | Analytics Engine 数据统计 ( 埋点 )
      - [barcode](#devenginebarcode)                                  | BarCode Engine 条形码、二维码处理
         - [listener](#devenginebarcodelistener)                      | 条形码、二维码操作回调事件
      - [cache](#devenginecache)                                      | Cache Engine 有效期键值对缓存
      - [compress](#devenginecompress)                                | Image Compress Engine 图片压缩
         - [listener](#devenginecompresslistener)                     | 图片压缩回调事件
      - [debug](#devenginedebug)                                      | Debug 编译辅助开发 Engine
      - [eventbus](#devengineeventbus)                                | EventBus Engine 事件总线
      - [image](#devengineimage)                                      | Image Engine 图片加载、下载、转格式等
         - [listener](#devengineimagelistener)                        | 图片加载监听事件
      - [json](#devenginejson)                                        | JSON Engine 映射
      - [keyvalue](#devenginekeyvalue)                                | KeyValue Engine 键值对存储
      - [log](#devenginelog)                                          | Log Engine 日志打印
      - [media](#devenginemedia)                                      | Media Selector Engine 多媒体资源选择
      - [permission](#devenginepermission)                            | Permission Engine 权限申请
      - [popnotification](#devenginepopnotification)                  | PopNotification Engine 简单通知提示
      - [poptip](#devenginepoptip)                                    | PopTip Engine 非阻断式文本提示
      - [push](#devenginepush)                                        | Push Engine 推送平台处理
      - [refresh](#devenginerefresh)                                  | Refresh Engine 下拉刷新、上拉加载 View
      - [router](#devenginerouter)                                    | Router Engine 页面路由
      - [share](#devengineshare)                                      | Share Engine 分享平台处理
         - [listener](#devenginesharelistener)                        | 分享回调事件
      - [storage](#devenginestorage)                                  | Storage Engine 外部、内部文件存储
         - [listener](#devenginestoragelistener)                      | Storage 存储结果事件
      - [toast](#devenginetoast)                                      | Toast Engine 吐司提示
      - [web](#devengineweb)                                          | WebView Engine 网页加载、配置、JS 交互
   - [function](#devfunction)                                         | 快捷方法执行相关


## <span id="dev">**`dev`**</span>


* **开发辅助类 ->** [DevAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/DevAssist.java)

| 方法 | 注释 |
| :- | :- |
| getDevAssistVersionCode | 获取 DevAssist 版本号 |
| getDevAssistVersion | 获取 DevAssist 版本 |
| getDevAppVersionCode | 获取 DevApp 版本号 |
| getDevAppVersion | 获取 DevApp 版本 |


## <span id="devadapter">**`dev.adapter`**</span>


* **DataManager RecyclerView Adapter ->** [DevDataAdapter.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/adapter/DevDataAdapter.java)

| 方法 | 注释 |
| :- | :- |
| getContext | 获取 Context |
| setContext | 设置 Context |
| getActivity | 获取 Activity |
| setActivity | 设置 Activity |
| parentContext | 通过 ViewGroup 设置 Context |
| getItemCount | getItemCount |
| onAttachedToRecyclerView | onAttachedToRecyclerView |
| onDetachedFromRecyclerView | onDetachedFromRecyclerView |
| getRecyclerView | getRecyclerView |
| setRecyclerView | setRecyclerView |
| bindAdapter | bindAdapter |
| initialize | initialize |
| getDataList | getDataList |
| getDataArrayList | getDataArrayList |
| getDataSize | getDataSize |
| getDataItem | getDataItem |
| getDataItemPosition | getDataItemPosition |
| getFirstData | getFirstData |
| getLastData | getLastData |
| getLastPosition | getLastPosition |
| isDataEmpty | isDataEmpty |
| isDataNotEmpty | isDataNotEmpty |
| isFirstPosition | isFirstPosition |
| isLastPosition | isLastPosition |
| isLastPositionAndGreaterThanOrEqual | isLastPositionAndGreaterThanOrEqual |
| equalsFirstData | equalsFirstData |
| equalsLastData | equalsLastData |
| equalsPositionData | equalsPositionData |
| addData | addData |
| addDataAt | addDataAt |
| addDatas | addDatas |
| addDatasAt | addDatasAt |
| addDatasChecked | addDatasChecked |
| addDatasCheckedAt | addDatasCheckedAt |
| addLists | addLists |
| removeData | removeData |
| removeDataAt | removeDataAt |
| removeDatas | removeDatas |
| replaceData | replaceData |
| replaceDataAt | replaceDataAt |
| swipePosition | swipePosition |
| contains | contains |
| clearDataList | clearDataList |
| setDataList | setDataList |
| notifyDataChanged | notifyDataChanged |
| notifyElementChanged | notifyElementChanged |


* **DataManager RecyclerView Adapter Extend ->** [DevDataAdapterExt.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/adapter/DevDataAdapterExt.java)

| 方法 | 注释 |
| :- | :- |
| getCallback | 获取通用回调 |
| setCallback | 设置通用回调 |
| getItemCallback | 获取通用 Item Click 回调 |
| setItemCallback | 设置通用 Item Click 回调 |
| getObject | 获取通用 Object |
| setObject | 设置通用 Object |
| getPage | 获取 Page 实体类 |
| setPage | 设置 Page 实体类 |
| getFlags | 获取标记值计算存储 ( 位运算符 ) 实体类 |
| setFlags | 设置标记值计算存储 ( 位运算符 ) 实体类 |
| getState | 获取通用状态实体类 |
| setState | 设置通用状态实体类 |
| getRequestState | 获取请求状态实体类 |
| setRequestState | 设置请求状态实体类 |
| getTextWatcherAssist | 获取 EditText 输入监听辅助类 |
| setTextWatcherAssist | 设置 EditText 输入监听辅助类 |
| getMultiSelectMap | 获取多选辅助类 |
| setMultiSelectMap | 设置多选辅助类 |


* **DataManager RecyclerView Adapter Extend ->** [DevDataAdapterExt2.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/adapter/DevDataAdapterExt2.java)

| 方法 | 注释 |
| :- | :- |
| isNotifyAdapter | 是否通知适配器 ( 通用: 如多选操作后是否通知适配器 ) |
| setNotifyAdapter | 设置是否通知适配器 ( 通用: 如多选操作后是否通知适配器 ) |
| isEditState | isEditState |
| setEditState | setEditState |
| toggleEditState | toggleEditState |
| clearSelectAll | clearSelectAll |
| isSelectAll | isSelectAll |
| isSelect | isSelect |
| isNotSelect | isNotSelect |
| getSelectSize | getSelectSize |
| getDataCount | getDataCount |
| selectAll | selectAll |
| inverseSelect | inverseSelect |
| getMultiSelectKey | 获取多选标记 Key |


* **DataManager List ->** [DevDataList.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/adapter/DevDataList.java)

| 方法 | 注释 |
| :- | :- |
| getContext | 获取 Context |
| setContext | 设置 Context |
| getActivity | 获取 Activity |
| setActivity | 设置 Activity |
| parentContext | 通过 ViewGroup 设置 Context |
| getItemCount | getItemCount |
| getDataList | getDataList |
| getDataArrayList | getDataArrayList |
| getDataSize | getDataSize |
| getDataItem | getDataItem |
| getDataItemPosition | getDataItemPosition |
| getFirstData | getFirstData |
| getLastData | getLastData |
| getLastPosition | getLastPosition |
| isDataEmpty | isDataEmpty |
| isDataNotEmpty | isDataNotEmpty |
| isFirstPosition | isFirstPosition |
| isLastPosition | isLastPosition |
| isLastPositionAndGreaterThanOrEqual | isLastPositionAndGreaterThanOrEqual |
| equalsFirstData | equalsFirstData |
| equalsLastData | equalsLastData |
| equalsPositionData | equalsPositionData |
| addData | addData |
| addDataAt | addDataAt |
| addDatas | addDatas |
| addDatasAt | addDatasAt |
| addDatasChecked | addDatasChecked |
| addDatasCheckedAt | addDatasCheckedAt |
| addLists | addLists |
| removeData | removeData |
| removeDataAt | removeDataAt |
| removeDatas | removeDatas |
| replaceData | replaceData |
| replaceDataAt | replaceDataAt |
| swipePosition | swipePosition |
| contains | contains |
| clearDataList | clearDataList |
| setDataList | setDataList |
| getCallback | 获取通用回调 |
| setCallback | 设置通用回调 |
| getItemCallback | 获取通用 Item Click 回调 |
| setItemCallback | 设置通用 Item Click 回调 |
| getObject | 获取通用 Object |
| setObject | 设置通用 Object |
| getPage | 获取 Page 实体类 |
| setPage | 设置 Page 实体类 |
| getFlags | 获取标记值计算存储 ( 位运算符 ) 实体类 |
| setFlags | 设置标记值计算存储 ( 位运算符 ) 实体类 |
| getState | 获取通用状态实体类 |
| setState | 设置通用状态实体类 |
| getRequestState | 获取请求状态实体类 |
| setRequestState | 设置请求状态实体类 |
| getTextWatcherAssist | 获取 EditText 输入监听辅助类 |
| setTextWatcherAssist | 设置 EditText 输入监听辅助类 |
| getMultiSelectMap | 获取多选辅助类 |
| setMultiSelectMap | 设置多选辅助类 |


* **DataManager List Extend ->** [DevDataListExt.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/adapter/DevDataListExt.java)

| 方法 | 注释 |
| :- | :- |
| isNotifyAdapter | 是否通知适配器 ( 通用: 如多选操作后是否通知适配器 ) |
| setNotifyAdapter | 设置是否通知适配器 ( 通用: 如多选操作后是否通知适配器 ) |
| isEditState | isEditState |
| setEditState | setEditState |
| toggleEditState | toggleEditState |
| clearSelectAll | clearSelectAll |
| isSelectAll | isSelectAll |
| isSelect | isSelect |
| isNotSelect | isNotSelect |
| getSelectSize | getSelectSize |
| getDataCount | getDataCount |
| selectAll | selectAll |
| inverseSelect | inverseSelect |
| getMultiSelectKey | 获取多选标记 Key |


## <span id="devassist">**`dev.assist`**</span>


* **数据辅助类 ->** [DataAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/DataAssist.java)

| 方法 | 注释 |
| :- | :- |
| setDataChanged | 设置数据改变通知 |
| getDataSource | 获取 DataSource Object |
| getDataList | 获取 List Data |
| getDataArrayList | 获取 ArrayList Data |
| getDataSize | 获取 List Size |
| getDataItem | 获取 List Position Data |
| getDataItemPosition | 获取 Value Position |
| getFirstData | 获取 First Data |
| getLastData | 获取 Last Data |
| getLastPosition | 获取 Last Position |
| isDataEmpty | 判断 List Size 是否为 0 |
| isDataNotEmpty | 判断 List Size 是否大于 0 |
| isFirstPosition | 判断是否 First Position |
| isLastPosition | 判断是否 Last Position |
| isLastPositionAndGreaterThanOrEqual | 判断是否 Last Position 且大于等于指定 size |
| equalsFirstData | 判断 First Value 是否一致 |
| equalsLastData | 判断 Last Value 是否一致 |
| equalsPositionData | 判断 Position Value 是否一致 |
| addData | 添加数据 |
| addDataAt | 添加数据 |
| addDatas | 添加数据集 |
| addDatasAt | 添加数据集 |
| addDatasChecked | 添加数据集 ( 进行校验 ) |
| addDatasCheckedAt | 添加数据集 ( 进行校验 ) |
| addLists | 添加数据集 ( 判断是追加还是重置 ) |
| removeData | 移除数据 |
| removeDataAt | 移除数据 |
| removeDatas | 移除数据集 |
| replaceData | 替换数据 |
| replaceDataAt | 替换数据 |
| swipePosition | 数据中两个索引 Data 互换位置 |
| contains | 是否存在 Data |
| clearDataList | 清空全部数据 |
| setDataList | 设置 List Data |
| notifyDataChanged | 通知数据改变 |
| notifyElementChanged | 通知某个数据改变 |


* **定时器辅助类 ->** [DevTimerAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/DevTimerAssist.java)

| 方法 | 注释 |
| :- | :- |
| setTag | 设置 TAG |
| setHandler | 设置 UI Handler |
| setCallback | 设置回调事件 |
| getTimer | 获取定时器 |
| getDuration | 获取剩余总时长 ( 毫秒 ) |
| start | 运行定时器 |
| stop | 关闭定时器 |
| onTick | 定时触发通知（每次计时触发一次） |


* **EditText 搜索辅助类 ->** [EditTextSearchAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/EditTextSearchAssist.java)

| 方法 | 注释 |
| :- | :- |
| remove | 移除消息 |
| post | 发送消息 ( 功能由该方法实现 ) |
| setDelayMillis | 设置搜索延迟时间 |
| setCallback | 设置搜索回调接口 |
| bindEditText | 绑定 EditText 输入事件 |
| onDebouncedQuery | 防抖后的搜索内容回调（与 {@link TextWatcher} 高频触发解耦） |


* **解决 Adapter 多个 Item 存在 EditText 监听输入问题 ->** [EditTextWatcherAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/EditTextWatcherAssist.java)

| 方法 | 注释 |
| :- | :- |
| bindListener | 绑定事件 |
| onFocusChange | 焦点触发方法 |
| beforeTextChanged | 在文本变化前调用 |
| afterTextChanged | 在文本变化后调用 |


* **数量控制辅助类 ->** [NumberControlAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/NumberControlAssist.java)

| 方法 | 注释 |
| :- | :- |
| getNumber | 获取 DevNumber Object |
| isMinNumber | 判断当前数量, 是否等于最小值 |
| isLessThanMinNumber | 判断数量, 是否小于最小值 |
| isGreaterThanMinNumber | 判断数量, 是否大于最小值 |
| isMaxNumber | 判断当前数量, 是否等于最大值 |
| isLessThanMaxNumber | 判断数量, 是否小于最大值 |
| isGreaterThanMaxNumber | 判断数量, 是否大于最大值 |
| getMinNumber | 获取最小值 |
| setMinNumber | 设置最小值 |
| getMaxNumber | 获取最大值 |
| setMaxNumber | 设置最大值 |
| setMinMaxNumber | 设置最小值、最大值 |
| getCurrentNumber | 获取当前数量 |
| setCurrentNumber | 设置当前数量 |
| getResetNumber | 获取重置数量 |
| setResetNumber | 设置重置数量 |
| isAllowNegative | 获取是否允许设置为负数 |
| setAllowNegative | 设置是否允许设置为负数 |
| numberChange | 数量改变通知 |
| addNumber | 添加数量 ( 默认累加 1 ) |
| subtractionNumber | 减少数量 ( 默认累减 1 ) |
| getNumberListener | 获取数量监听事件接口 |
| setNumberListener | 设置数量监听事件接口 |


* **Page 辅助类 ->** [PageAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/PageAssist.java)

| 方法 | 注释 |
| :- | :- |
| initPageConfig | 初始化全局分页配置 |
| reset | 重置操作 |
| getPage | 获取当前页数 |
| setPage | 设置当前页数 |
| equalsPage | 判断当前页数是否一致 |
| getConfig | 获取页数配置信息 |
| getConfigPage | 获取配置初始页页数 |
| getConfigPageSize | 获取配置每页请求条数 |
| getPageSize | 获取每页请求条数 |
| equalsPageSize | 判断每页请求条数是否一致 |
| isLastPage | 判断是否最后一页 |
| setLastPage | 设置是否最后一页 |
| calculateLastPage | 计算是否最后一页 ( 并同步更新 ) |
| isFirstPage | 判断是否第一页 |
| canNextPage | 判断是否允许请求下一页 |
| getNextPage | 获取下一页页数 |
| nextPage | 累加当前页数 ( 下一页 ) |
| isLessThanPageSize | 判断是否小于每页请求条数 |
| response | 请求响应处理 |


## <span id="devbase">**`dev.base`**</span>


* **数据源操作实体类 ->** [DevDataSource.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/DevDataSource.java)

| 方法 | 注释 |
| :- | :- |
| getDataList | 获取 List Data |
| getDataArrayList | 获取 ArrayList Data |
| getDataSize | 获取 List Size |
| getDataItem | 获取 List Position Data |
| getDataItemPosition | 获取 Value Position |
| getFirstData | 获取 First Data |
| getLastData | 获取 Last Data |
| getLastPosition | 获取 Last Position |
| isDataEmpty | 判断 List Size 是否为 0 |
| isDataNotEmpty | 判断 List Size 是否大于 0 |
| isFirstPosition | 判断是否 First Position |
| isLastPosition | 判断是否 Last Position |
| isLastPositionAndGreaterThanOrEqual | 判断是否 Last Position 且大于等于指定 size |
| equalsFirstData | 判断 First Value 是否一致 |
| equalsLastData | 判断 Last Value 是否一致 |
| equalsPositionData | 判断 Position Value 是否一致 |
| addData | 添加数据 |
| addDataAt | 添加数据 |
| addDatas | 添加数据集 |
| addDatasAt | 添加数据集 |
| addDatasChecked | 添加数据集 ( 进行校验 ) |
| addDatasCheckedAt | 添加数据集 ( 进行校验 ) |
| addLists | 添加数据集 ( 判断是追加还是重置 ) |
| removeData | 移除数据 |
| removeDataAt | 移除数据 |
| removeDatas | 移除数据集 |
| replaceData | 替换数据 |
| replaceDataAt | 替换数据 |
| swipePosition | 数据中两个索引 Data 互换位置 |
| contains | 是否存在 Data |
| clearDataList | 清空全部数据 |
| setDataList | 设置 List Data |


* **Key-Value Entry ->** [DevEntry.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/DevEntry.java)

| 方法 | 注释 |
| :- | :- |
| getKey | 获取 Key |
| setKey | 设置 Key |
| getValue | 获取 Value |
| setValue | 设置 Value |
| equalsKey | 判断 Key 是否一致 |
| equalsValue | 判断 Value 是否一致 |


* **历史数据记录功类 ->** [DevHistory.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/DevHistory.java)

| 方法 | 注释 |
| :- | :- |
| getCurrent | 获取当前数据 |
| setCurrent | 设置当前数据 |
| getListener | 获取方法事件触发接口 |
| setListener | 设置方法事件触发接口 |
| cleanCurrent | 清空当前数据 |
| reset | 重置操作 |
| clearBack | 清空回退栈数据 |
| sizeBack | 获取回退栈数据条数 |
| isEmptyBack | 是否不存在回退栈数据 |
| canGoBack | 是否能够执行回退操作 |
| addBack | 添加到回退栈 |
| getBack | 获取上一条回退栈数据 |
| goBack | 前往上一条回退栈数据 |
| toStringBack | 进行回退栈数据顺序拼接字符串 |
| clearForward | 清空前进栈数据 |
| sizeForward | 获取前进栈数据条数 |
| isEmptyForward | 是否不存在前进栈数据 |
| canGoForward | 是否能够执行前进操作 |
| addForward | 添加到前进栈 |
| getForward | 获取下一条前进栈数据 |
| goForward | 前往下一条前进栈数据 |
| toStringForward | 进行前进栈数据顺序拼接字符串 |
| accept | 是否允许添加 |
| changeCurrent | 当前数据改变通知 |
| clear | 清空数据回调 |
| add | 添加数据到栈内 |
| acceptCurrentToList | 是否允许 Current 添加到列表中 |


* **Intent 传参读写辅助类 ->** [DevIntent.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/DevIntent.java)

| 方法 | 注释 |
| :- | :- |
| with | 创建 DevIntent |
| insert | 插入数据 |
| reader | 读取数据并存储 |
| getDataMaps | 获取存储数据 Map |
| containsKey | 是否存在 Key |
| containsValue | 是否存在 Value |
| isNullValue | 对应 Key 保存的 Value 是否为 null |
| put | 保存数据 |
| putAll | 保存集合数据 |
| remove | 移除数据 |
| removeAll | 移除集合数据 |
| get | 获取对应 Key 保存的 Value |
| clear | 清空数据 |
| clearNull | 清除 null 数据 |
| clearNullKey | 清除 null Key 数据 |
| clearNullValue | 清除 null Value 数据 |
| clearEmpty | 清除 empty 数据 |
| clearEmptyKey | 清除 empty Key 数据 |
| clearEmptyValue | 清除 empty Value 数据 |


* **数量实体类 ->** [DevNumber.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/DevNumber.java)

| 方法 | 注释 |
| :- | :- |
| isMinNumber | 判断当前数量, 是否等于最小值 |
| isLessThanMinNumber | 判断数量, 是否小于最小值 |
| isGreaterThanMinNumber | 判断数量, 是否大于最小值 |
| isMaxNumber | 判断当前数量, 是否等于最大值 |
| isLessThanMaxNumber | 判断数量, 是否小于最大值 |
| isGreaterThanMaxNumber | 判断数量, 是否大于最大值 |
| getMinNumber | 获取最小值 |
| setMinNumber | 设置最小值 |
| getMaxNumber | 获取最大值 |
| setMaxNumber | 设置最大值 |
| setMinMaxNumber | 设置最小值、最大值 |
| getCurrentNumber | 获取当前数量 |
| setCurrentNumber | 设置当前数量 |
| getResetNumber | 获取重置数量 |
| setResetNumber | 设置重置数量 |
| isAllowNegative | 获取是否允许设置为负数 |
| setAllowNegative | 设置是否允许设置为负数 |
| numberChange | 数量改变通知 |
| addNumber | 添加数量 ( 默认累加 1 ) |
| subtractionNumber | 减少数量 ( 默认累减 1 ) |
| getNumberListener | 获取数量监听事件接口 |
| setNumberListener | 设置数量监听事件接口 |


* **通用 Object ->** [DevObject.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/DevObject.java)

| 方法 | 注释 |
| :- | :- |
| getUUID | 获取 UUID |
| getObject | 获取 Object |
| setObject | 设置 Object |
| getTag | 获取标记 TAG |
| convertTag | 转换标记 TAG |
| setTag | 设置标记 TAG |
| getModelId | 获取 Model id |
| setModelId | 设置 Model id |
| getCode | 获取 Code |
| setCode | 设置 Code |
| getType | 获取 Type |
| setType | 设置 Type |
| getState | 获取 State |
| setState | 设置 State |
| getTokenUUID | 获取 Token UUID |
| setTokenUUID | 设置 Token UUID |
| randomTokenUUID | 重置随机 Token UUID |
| equalsObject | 判断 Object 是否一致 |
| equalsTag | 判断 TAG 是否一致 |
| equalsModelId | 判断 Model id 是否一致 |
| equalsCode | 判断 Code 是否一致 |
| equalsType | 判断 Type 是否一致 |
| equalsState | 判断 State 是否一致 |
| equalsTokenUUID | 判断 Token UUID 是否一致 |
| isCorrect | 校验数据正确性 |


* **Page 实体类 ->** [DevPage.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/DevPage.java)

| 方法 | 注释 |
| :- | :- |
| reset | 重置操作 |
| getPage | 获取当前页数 |
| setPage | 设置当前页数 |
| equalsPage | 判断当前页数是否一致 |
| getConfig | 获取页数配置信息 |
| getConfigPage | 获取配置初始页页数 |
| getConfigPageSize | 获取配置每页请求条数 |
| getPageSize | 获取每页请求条数 |
| equalsPageSize | 判断每页请求条数是否一致 |
| isLastPage | 判断是否最后一页 |
| setLastPage | 设置是否最后一页 |
| calculateLastPage | 计算是否最后一页 ( 并同步更新 ) |
| isFirstPage | 判断是否第一页 |
| canNextPage | 判断是否允许请求下一页 |
| getNextPage | 获取下一页页数 |
| nextPage | 累加当前页数 ( 下一页 ) |
| isLessThanPageSize | 判断是否小于每页请求条数 |
| response | 请求响应处理 |
| getDefault | 获取默认配置 Page 实体类 |


* **资源来源通用类 ->** [DevSource.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/DevSource.java)

| 方法 | 注释 |
| :- | :- |
| create | create |
| createWithPath | createWithPath |
| isUrl | isUrl |
| isUri | isUri |
| isBytes | isBytes |
| isResource | isResource |
| isFile | isFile |
| isInputStream | isInputStream |
| isDrawable | isDrawable |
| isBitmap | isBitmap |
| isSource | 是否有效资源 |


* **变量操作基类 ->** [DevVariable.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/DevVariable.java)

| 方法 | 注释 |
| :- | :- |
| getVariables | 获取全部变量数据 |
| clearVariables | 清空全部变量数据 |
| putVariables | 保存变量数据集合 |
| getVariablesSize | 获取变量总数 |
| isVariables | 判断是否存在变量数据 |
| isVariableValue | 判断是否存在变量 ( 通过 value 判断 ) |
| removeVariableValue | 删除指定变量数据 |
| removeVariableValueAll | 删除指定变量数据 ( 符合条件的全部 value ) |
| isVariable | 判断是否存在变量 ( 通过 key 判断 ) |
| putVariable | 保存变量数据 |
| removeVariable | 移除指定变量数据 ( 通过 key 判断 ) |
| toggle | 切换变量数据存储状态 |
| getVariableValue | 通过 key 获取 value |
| getVariableValueConvert | 通过 key 获取 value |
| getVariableValues | 获取变量数据 value list |
| getVariableValuesToReverse | 获取变量数据 value list ( 倒序 ) |
| getVariableKey | 通过 value 获取 key |
| getVariableKeys | 获取变量数据 key list |
| getVariableKeysToReverse | 获取变量数据 key list ( 倒序 ) |


* **变量操作基类扩展类 ->** [DevVariableExt.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/DevVariableExt.java)

| 方法 | 注释 |
| :- | :- |
| getCreator | 获取变量创建器 |
| setCreator | 设置变量创建器 |
| getVariable | 获取变量操作基类 |
| getVariableValue | 通过 key 获取 value |
| create | 创建存储值 |


## <span id="devbasedata">**`dev.base.data`**</span>


* **数据改变通知 ->** [DataChanged.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/data/DataChanged.java)

| 方法 | 注释 |
| :- | :- |
| notifyDataChanged | 通知数据改变 |
| notifyElementChanged | 通知某个数据改变 |


* **数据管理接口 ->** [DataManager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/data/DataManager.java)

| 方法 | 注释 |
| :- | :- |
| getDataList | 获取 List Data |
| getDataArrayList | 获取 ArrayList Data |
| getDataSize | 获取 List Size |
| getDataItem | 获取 List Position Data |
| getDataItemPosition | 获取 Value Position |
| getFirstData | 获取 First Data |
| getLastData | 获取 Last Data |
| getLastPosition | 获取 Last Position |
| isDataEmpty | 判断 List Size 是否为 0 |
| isDataNotEmpty | 判断 List Size 是否大于 0 |
| isFirstPosition | 判断是否 First Position |
| isLastPosition | 判断是否 Last Position |
| isLastPositionAndGreaterThanOrEqual | 判断是否 Last Position 且大于等于指定 size |
| equalsFirstData | 判断 First Value 是否一致 |
| equalsLastData | 判断 Last Value 是否一致 |
| equalsPositionData | 判断 Position Value 是否一致 |
| addData | 添加数据 |
| addDataAt | 添加数据 |
| addDatas | 添加数据集 |
| addDatasAt | 添加数据集 |
| addDatasChecked | 添加数据集 ( 进行校验 ) |
| addDatasCheckedAt | 添加数据集 ( 进行校验 ) |
| addLists | 添加数据集 ( 判断是追加还是重置 ) |
| removeData | 移除数据 |
| removeDataAt | 移除数据 |
| removeDatas | 移除数据集 |
| replaceData | 替换数据 |
| replaceDataAt | 替换数据 |
| swipePosition | 数据中两个索引 Data 互换位置 |
| contains | 是否存在 Data |
| clearDataList | 清空全部数据 |
| setDataList | 设置 List Data |


## <span id="devbaseentry">**`dev.base.entry`**</span>


## <span id="devbasemultiselect">**`dev.base.multiselect`**</span>


* **List 多选实体类 ->** [DevMultiSelectList.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/multiselect/DevMultiSelectList.java)

| 方法 | 注释 |
| :- | :- |
| clearSelects | 清空全部选中数据 |
| getSelectSize | 获取选中的数据条数 |
| getSelects | 获取选中的数据集合 |
| putSelects | 通过集合添加选中数据 |
| isSelect | 判断是否存在选中的数据 |
| isSelectValue | 判断是否选中 ( 通过 value 判断 ) |
| unselectValue | 设置非选中 |
| unselectValueAll | 设置非选中 ( 符合条件的全部 value ) |
| select | 设置选中操作 |
| unselect | 设置非选中操作 |
| toggle | 切换选中状态 |
| getSelectValues | 获取选中的数据集合 |
| getSelectValuesToReverse | 获取选中的数据集合 ( 倒序 ) |
| getSelectValue | 获取选中的数据 |
| getSelectValueToPosition | 获取选中的数据所在的索引 |


* **Map 多选实体类 ->** [DevMultiSelectMap.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/multiselect/DevMultiSelectMap.java)

| 方法 | 注释 |
| :- | :- |
| clearSelects | 清空全部选中数据 |
| getSelectSize | 获取选中的数据条数 |
| getSelects | 获取选中的数据集合 |
| putSelects | 通过集合添加选中数据 |
| isSelect | 判断是否存在选中的数据 |
| isSelectValue | 判断是否选中 ( 通过 value 判断 ) |
| unselectValue | 设置非选中 |
| unselectValueAll | 设置非选中 ( 符合条件的全部 value ) |
| isSelectKey | 判断是否选中 ( 通过 key 判断 ) |
| select | 设置选中操作 |
| unselect | 设置非选中操作 |
| toggle | 切换选中状态 |
| getSelectValue | 通过 key 获取 value |
| getSelectValues | 获取选中的数据集合 |
| getSelectValuesToReverse | 获取选中的数据集合 ( 倒序 ) |
| getSelectKey | 通过 value 获取 key |
| getSelectKeys | 获取选中的数据集合 |
| getSelectKeysToReverse | 获取选中的数据集合 ( 倒序 ) |


* **多选操作接口 ( 基类 ) ->** [IMultiSelect.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/multiselect/IMultiSelect.java)

| 方法 | 注释 |
| :- | :- |
| clearSelects | 清空全部选中数据 |
| getSelectSize | 获取选中的数据条数 |
| getSelects | 获取选中的数据集合 |
| putSelects | 通过集合添加选中数据 |
| isSelect | 判断是否存在选中的数据 |
| isSelectValue | 判断是否选中 ( 通过 value 判断 ) |
| unselectValue | 设置非选中 |
| unselectValueAll | 设置非选中 ( 符合条件的全部 value ) |


* **多选编辑接口 ->** [IMultiSelectEdit.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/multiselect/IMultiSelectEdit.java)

| 方法 | 注释 |
| :- | :- |
| isEditState | 是否编辑状态 |
| setEditState | 设置编辑状态 |
| toggleEditState | 切换编辑状态 |
| selectAll | 全选 |
| clearSelectAll | 清空全选 ( 非全选 ) |
| inverseSelect | 反选 |
| isSelectAll | 判断是否全选 |
| isSelect | 判断是否存在选中的数据 |
| isNotSelect | 判断是否不存在选中的数据 |
| getSelectSize | 获取选中的数据条数 |
| getDataCount | 获取数据总数 |


* **多选操作接口 ( List ) ->** [IMultiSelectToList.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/multiselect/IMultiSelectToList.java)

| 方法 | 注释 |
| :- | :- |
| isSelect | 判断是否选中 ( 通过 value 判断 ) |
| select | 设置选中操作 |
| unselect | 设置非选中操作 |
| toggle | 切换选中状态 |
| getSelectValues | 获取选中的数据集合 |
| getSelectValuesToReverse | 获取选中的数据集合 ( 倒序 ) |
| getSelectValue | 获取选中的数据 |
| getSelectValueToPosition | 获取选中的数据所在的索引 |


* **多选操作接口 ( Map ) ->** [IMultiSelectToMap.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/multiselect/IMultiSelectToMap.java)

| 方法 | 注释 |
| :- | :- |
| isSelect | 判断是否选中 ( 如果未选中, 则设置为选中 ) |
| isSelectKey | 判断是否选中 ( 通过 key 判断 ) |
| select | 设置选中操作 |
| unselect | 设置非选中操作 |
| toggle | 切换选中状态 |
| getSelectValue | 通过 key 获取 value |
| getSelectValues | 获取选中的数据集合 |
| getSelectValuesToReverse | 获取选中的数据集合 ( 倒序 ) |
| getSelectKey | 通过 value 获取 key |
| getSelectKeys | 获取选中的数据集合 |
| getSelectKeysToReverse | 获取选中的数据集合 ( 倒序 ) |


## <span id="devbasenumber">**`dev.base.number`**</span>


* **数量监听事件接口 ->** [INumberListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/number/INumberListener.java)

| 方法 | 注释 |
| :- | :- |
| onPrepareChanged | 数量准备变化通知 |
| onNumberChanged | 数量变化通知 |


* **数量操作接口 ->** [INumberOperate.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/number/INumberOperate.java)

| 方法 | 注释 |
| :- | :- |
| isMinNumber | 判断当前数量是否等于最小值 |
| isLessThanMinNumber | 判断数量是否小于最小值 |
| isGreaterThanMinNumber | 判断数量是否大于最小值 |
| isMaxNumber | 判断当前数量是否等于最大值 |
| isLessThanMaxNumber | 判断数量是否小于最大值 |
| isGreaterThanMaxNumber | 判断数量是否大于最大值 |
| getMinNumber | 获取最小值 |
| setMinNumber | 设置最小值 |
| getMaxNumber | 获取最大值 |
| setMaxNumber | 设置最大值 |
| setMinMaxNumber | 设置最小值、最大值 |
| getCurrentNumber | 获取当前数量 |
| setCurrentNumber | 设置当前数量 |
| getResetNumber | 获取重置数量 |
| setResetNumber | 设置重置数量 |
| isAllowNegative | 获取是否允许设置为负数 |
| setAllowNegative | 设置是否允许设置为负数 |
| numberChange | 数量改变通知 |
| addNumber | 添加数量 ( 默认累加 1 ) |
| subtractionNumber | 减少数量 ( 默认累减 1 ) |
| getNumberListener | 获取数量监听事件接口 |
| setNumberListener | 设置数量监听事件接口 |


## <span id="devbasestate">**`dev.base.state`**</span>


* **通用状态类 ->** [CommonState.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/state/CommonState.java)

| 方法 | 注释 |
| :- | :- |
| getType | 获取操作类型 |
| setType | 设置操作类型 |
| equalsType | 判断操作类型是否一致 |
| getUUID | 获取操作 UUID |
| randomUUID | 获取操作 UUID ( 随机生成并赋值 ) |
| equalsUUID | 判断 UUID 是否一致 |
| getState | 获取 State |
| setState | 设置 State |
| equalsState | 判断 State 是否一致 |
| isNormal | 判断是否默认状态 ( 暂未进行操作 ) |
| isIng | 判断是否操作中 |
| isSuccess | 判断是否操作成功 |
| isFail | 判断是否操作失败 |
| isError | 判断是否操作异常 |
| isStart | 判断是否开始操作 |
| isRestart | 判断是否重新开始操作 |
| isEnd | 判断是否操作结束 |
| isPause | 判断是否操作暂停 |
| isResume | 判断是否操作恢复 ( 继续 ) |
| isStop | 判断是否操作停止 |
| isCancel | 判断是否操作取消 |
| isCreate | 判断是否创建 |
| isDestroy | 判断是否销毁 |
| isRecycle | 判断是否回收 |
| isInit | 判断是否初始化 |
| isEnabled | 判断是否已打开 |
| isEnabling | 判断是否正在打开 |
| isDisabled | 判断是否已关闭 |
| isDisabling | 判断是否正在关闭 |
| isConnected | 判断是否连接成功 |
| isConnecting | 判断是否连接中 |
| isDisconnected | 判断是否连接失败、断开 |
| isSuspended | 判断是否暂停、延迟 |
| isUnknown | 判断是否未知 |
| isInsert | 判断是否新增 |
| isDelete | 判断是否删除 |
| isUpdate | 判断是否更新 |
| isSelect | 判断是否查询 |
| isEncrypt | 判断是否加密 |
| isDecrypt | 判断是否解密 |
| isReset | 判断是否重置 |
| isClose | 判断是否关闭 |
| isOpen | 判断是否打开 |
| isExit | 判断是否退出 |
| setNormal | 设置状态为默认状态 ( 暂未进行操作 ) |
| setIng | 设置状态为操作中 |
| setSuccess | 设置状态为操作成功 |
| setFail | 设置状态为操作失败 |
| setError | 设置状态为操作异常 |
| setStart | 设置状态为开始操作 |
| setRestart | 设置状态为重新开始操作 |
| setEnd | 设置状态为操作结束 |
| setPause | 设置状态为操作暂停 |
| setResume | 设置状态为操作恢复 ( 继续 ) |
| setStop | 设置状态为操作停止 |
| setCancel | 设置状态为操作取消 |
| setCreate | 设置状态为创建 |
| setDestroy | 设置状态为销毁 |
| setRecycle | 设置状态为回收 |
| setInit | 设置状态为初始化 |
| setEnabled | 设置状态为已打开 |
| setEnabling | 设置状态为正在打开 |
| setDisabled | 设置状态为已关闭 |
| setDisabling | 设置状态为正在关闭 |
| setConnected | 设置状态为连接成功 |
| setConnecting | 设置状态为连接中 |
| setDisconnected | 设置状态为连接失败、断开 |
| setSuspended | 设置状态为暂停、延迟 |
| setUnknown | 设置状态为未知 |
| setInsert | 设置状态为新增 |
| setDelete | 设置状态为删除 |
| setUpdate | 设置状态为更新 |
| setSelect | 设置状态为查询 |
| setEncrypt | 设置状态为加密 |
| setDecrypt | 设置状态为解密 |
| setReset | 设置状态为重置 |
| setClose | 设置状态为关闭 |
| setOpen | 设置状态为打开 |
| setExit | 设置状态为退出 |


* **请求状态类 ->** [RequestState.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/state/RequestState.java)

| 方法 | 注释 |
| :- | :- |
| getType | 获取请求类型 |
| setType | 设置请求类型 |
| equalsType | 判断请求类型是否一致 |
| getRequestUUID | 获取请求 UUID |
| randomRequestUUID | 获取请求 UUID ( 随机生成并赋值 ) |
| equalsRequestUUID | 判断 UUID 是否一致 |
| getState | 获取 State |
| setState | 设置 State |
| equalsState | 判断 State 是否一致 |
| isRequestNormal | 判断是否默认状态 ( 暂未进行操作 ) |
| isRequestIng | 判断是否请求中 |
| isRequestSuccess | 判断是否请求成功 |
| isRequestFail | 判断是否请求失败 |
| isRequestError | 判断是否请求异常 |
| isRequestStart | 判断是否请求开始 |
| isRequestRestart | 判断是否重新请求 |
| isRequestEnd | 判断是否请求结束 |
| isRequestPause | 判断是否请求暂停 |
| isRequestResume | 判断是否请求恢复 ( 继续 ) |
| isRequestStop | 判断是否请求停止 |
| isRequestCancel | 判断是否请求取消 |
| setRequestNormal | 设置状态为默认状态 ( 暂未进行操作 ) |
| setRequestIng | 设置状态为请求中 |
| setRequestSuccess | 设置状态为请求成功 |
| setRequestFail | 设置状态为请求失败 |
| setRequestError | 设置状态为请求异常 |
| setRequestStart | 设置状态为请求开始 |
| setRequestRestart | 设置状态为重新请求 |
| setRequestEnd | 设置状态为请求结束 |
| setRequestPause | 设置状态为请求暂停 |
| setRequestResume | 设置状态为请求恢复 ( 继续 ) |
| setRequestStop | 设置状态为请求停止 |
| setRequestCancel | 设置状态为请求取消 |


## <span id="devcallback">**`dev.callback`**</span>


* **Dev 通用回调 ->** [DevCallback.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/callback/DevCallback.java)

| 方法 | 注释 |
| :- | :- |
| deliver | 投递回调（无参） |
| filter | 过滤处理 |
| isFilter | 判断是否过滤 |
| compare | 对比判断 |


* **通用 Click 回调 ->** [DevClickCallback.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/callback/DevClickCallback.java)

| 方法 | 注释 |
| :- | :- |
| onClick | 点击回调 |
| onLongClick | 长按回调 |


* **通用 Dialog 回调 ->** [DevDialogCallback.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/callback/DevDialogCallback.java)

| 方法 | 注释 |
| :- | :- |
| onDialogNotify | 特殊通知 |
| onDialogShow | show 通知 |
| onDialogDismiss | dismiss 通知 |
| onDialogStart | start 通知 |
| onDialogResume | resume 通知 |
| onDialogPause | pause 通知 |
| onDialogStop | stop 通知 |
| onDialogDestroy | destroy 通知 |


* **通用 Item Click 回调 ->** [DevItemClickCallback.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/callback/DevItemClickCallback.java)

| 方法 | 注释 |
| :- | :- |
| onItemClick | 点击 Item 回调 |
| onItemLongClick | 长按 Item 回调 |


* **通用结果回调类 ->** [DevResultCallback.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/callback/DevResultCallback.java)

| 方法 | 注释 |
| :- | :- |
| onResult | 结果回调通知 |
| onError | 异常回调通知 |
| onFailure | 失败回调通知 |


## <span id="devengine">**`dev.engine`**</span>


* **DevEngine Generic Assist ->** [DevEngineAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/DevEngineAssist.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


## <span id="devengineanalytics">**`dev.engine.analytics`**</span>


* **Analytics Engine ->** [DevAnalyticsEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/analytics/DevAnalyticsEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Analytics Engine 接口 ->** [IAnalyticsEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/analytics/IAnalyticsEngine.java)

| 方法 | 注释 |
| :- | :- |
| initialize | 初始化方法 |
| register | 绑定 |
| unregister | 解绑 |
| track | 数据统计 ( 埋点 ) 方法 |


## <span id="devenginebarcode">**`dev.engine.barcode`**</span>


* **BarCode Engine ->** [DevBarCodeEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/barcode/DevBarCodeEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **BarCode Engine 接口 ->** [IBarCodeEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/barcode/IBarCodeEngine.java)

| 方法 | 注释 |
| :- | :- |
| initialize | 初始化方法 |
| getConfig | 获取 BarCode Engine Config |
| encodeBarCode | 编码 ( 生成 ) 条码图片 |
| encodeBarCodeSync | 编码 ( 生成 ) 条码图片 |
| decodeBarCode | 解码 ( 解析 ) 条码图片 |
| decodeBarCodeSync | 解码 ( 解析 ) 条码图片 |
| addIconToBarCode | 添加 Icon 到条码图片上 |


## <span id="devenginebarcodelistener">**`dev.engine.barcode.listener`**</span>


* **条码解码 ( 解析 ) 回调 ->** [BarCodeDecodeCallback.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/barcode/listener/BarCodeDecodeCallback.java)

| 方法 | 注释 |
| :- | :- |
| onResult | 条码解码 ( 解析 ) 回调 |


* **条码编码 ( 生成 ) 回调 ->** [BarCodeEncodeCallback.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/barcode/listener/BarCodeEncodeCallback.java)

| 方法 | 注释 |
| :- | :- |
| onResult | 条码编码 ( 生成 ) 回调 |


## <span id="devenginecache">**`dev.engine.cache`**</span>


* **Cache Engine ->** [DevCacheEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/cache/DevCacheEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Cache Engine 接口 ->** [ICacheEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/cache/ICacheEngine.java)

| 方法 | 注释 |
| :- | :- |
| getConfig | 获取 Cache Engine Config |
| remove | 移除数据 |
| removeForKeys | 移除数组的数据 |
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
| getEntity | 获取指定类型对象 |
| cipher | cipher |
| key | key |
| type | type |
| size | size |
| saveTime | saveTime |
| validTime | validTime |
| isPermanent | isPermanent |


## <span id="devenginecompress">**`dev.engine.compress`**</span>


* **Image Compress Engine ->** [DevCompressEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/compress/DevCompressEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Image Compress Engine 接口 ->** [ICompressEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/compress/ICompressEngine.java)

| 方法 | 注释 |
| :- | :- |
| compress | 压缩方法 |


## <span id="devenginecompresslistener">**`dev.engine.compress.listener`**</span>


* **压缩过滤接口 ->** [CompressFilter.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/compress/listener/CompressFilter.java)

| 方法 | 注释 |
| :- | :- |
| apply | 根据路径判断是否进行压缩 |


* **压缩回调接口 ->** [OnCompressListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/compress/listener/OnCompressListener.java)

| 方法 | 注释 |
| :- | :- |
| onStart | 开始压缩前调用 |
| onSuccess | 压缩成功后调用 |
| onError | 当压缩过程出现问题时触发 |
| onComplete | 压缩完成 ( 压缩结束 ) |


* **修改压缩图片文件名接口 ->** [OnRenameListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/compress/listener/OnRenameListener.java)

| 方法 | 注释 |
| :- | :- |
| rename | 压缩前调用该方法用于修改压缩后文件名 |


## <span id="devenginedebug">**`dev.engine.debug`**</span>


* **Debug 编译辅助开发 Engine ->** [DevDebugEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/debug/DevDebugEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Debug 编译辅助开发 Engine 接口 ->** [IDebugEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/debug/IDebugEngine.java)

| 方法 | 注释 |
| :- | :- |
| initialize | 初始化方法 |
| isDisplayDebugFunction | 是否显示 Debug 功能开关 |
| setDebugFunction | 设置 Debug 功能开关 |
| attachDebug | 连接 ( 显示 ) Debug 功能关联 |
| detachDebug | 分离 ( 隐藏 ) Debug 功能关联 |
| updateConfig | 更新 Debug Config |


## <span id="devengineeventbus">**`dev.engine.eventbus`**</span>


* **EventBus Engine ->** [DevEventBusEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/eventbus/DevEventBusEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **EventBus Engine 接口 ->** [IEventBusEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/eventbus/IEventBusEngine.java)

| 方法 | 注释 |
| :- | :- |
| initialize | 初始化方法 |
| config | 应用事件配置 |
| post | 发送事件 |
| postDelay | 发送延迟事件 |
| postOrderly | 顺序发送事件 |
| postAcrossProcess | 跨进程发送事件 |
| postAcrossApp | 跨 App 发送事件 |
| broadcast | 广播形式发送事件 |
| observe | 注册生命周期感知观察者 |
| observeSticky | 注册生命周期感知 Sticky 观察者 |
| observeForever | 注册永久观察者 |
| observeStickyForever | 注册永久 Sticky 观察者 |
| removeObserver | 移除永久观察者 |


## <span id="devengineimage">**`dev.engine.image`**</span>


* **Image Engine ->** [DevImageEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/DevImageEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Image Engine 接口 ->** [IImageEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/IImageEngine.java)

| 方法 | 注释 |
| :- | :- |
| pause | 暂停 Fragment 关联的图片加载请求 |
| resume | 恢复 Fragment 关联的图片加载请求 |
| preload | 预加载图片资源 |
| clear | 清除 View 关联的图片加载 |
| clearDiskCache | 清除磁盘缓存 |
| clearMemoryCache | 清除内存缓存 |
| clearAllCache | 清除全部缓存 |
| lowMemory | 低内存回调处理 |
| display | 加载图片并显示到 ImageView |
| loadImage | 加载图片 |
| loadImageThrows | 加载图片 |
| loadBitmap | 加载 Bitmap |
| loadBitmapThrows | 加载 Bitmap |
| loadDrawable | 加载 Drawable |
| loadDrawableThrows | 加载 Drawable |
| convertImageFormat | 转换图片格式 |


## <span id="devengineimagelistener">**`dev.engine.image.listener`**</span>


* **Bitmap 加载事件 ->** [BitmapListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/listener/BitmapListener.java)

| 方法 | 注释 |
| :- | :- |
| getTranscodeType | getTranscodeType |


* **转换图片格式存储接口 ->** [ConvertStorage.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/listener/ConvertStorage.java)

| 方法 | 注释 |
| :- | :- |
| convert | 转换图片格式并存储 |


* **Drawable 加载事件 ->** [DrawableListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/listener/DrawableListener.java)

| 方法 | 注释 |
| :- | :- |
| getTranscodeType | getTranscodeType |


* **图片加载事件 ->** [LoadListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/listener/LoadListener.java)

| 方法 | 注释 |
| :- | :- |
| getTranscodeType | 获取转码类型 |
| onStart | 开始加载 |
| onResponse | 响应回调 |
| onFailure | 失败回调 |


* **转换图片格式回调接口 ->** [OnConvertListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/listener/OnConvertListener.java)

| 方法 | 注释 |
| :- | :- |
| onStart | 开始转换前调用 |
| onSuccess | 转换成功后调用 |
| onError | 当转换过程出现问题时触发 |
| onComplete | 转换完成 ( 转换结束 ) |


## <span id="devenginejson">**`dev.engine.json`**</span>


* **JSON Engine ->** [DevJSONEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/json/DevJSONEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **JSON Engine 接口 ->** [IJSONEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/json/IJSONEngine.java)

| 方法 | 注释 |
| :- | :- |
| toJson | 将对象转换为 JSON String |
| fromJson | 将 JSON String 映射为指定类型对象 |
| isJSON | 判断字符串是否 JSON 格式 |
| isJSONObject | 判断字符串是否 JSON Object 格式 |
| isJSONArray | 判断字符串是否 JSON Array 格式 |
| toJsonIndent | JSON String 缩进处理 |


## <span id="devenginekeyvalue">**`dev.engine.keyvalue`**</span>


* **KeyValue Engine ->** [DevKeyValueEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/keyvalue/DevKeyValueEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Key-Value Engine 接口 ->** [IKeyValueEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/keyvalue/IKeyValueEngine.java)

| 方法 | 注释 |
| :- | :- |
| getConfig | 获取 Key-Value Engine Config |
| remove | 移除数据 |
| removeForKeys | 移除数组的数据 |
| contains | 是否存在 key |
| clear | 清除全部数据 |
| putInt | 保存 int 类型的数据 |
| putLong | 保存 long 类型的数据 |
| putFloat | 保存 float 类型的数据 |
| putDouble | 保存 double 类型的数据 |
| putBoolean | 保存 boolean 类型的数据 |
| putString | 保存 String 类型的数据 |
| putEntity | 保存指定类型对象 |
| getInt | 获取 int 类型的数据 |
| getLong | 获取 long 类型的数据 |
| getFloat | 获取 float 类型的数据 |
| getDouble | 获取 double 类型的数据 |
| getBoolean | 获取 boolean 类型的数据 |
| getString | 获取 String 类型的数据 |
| getEntity | 获取指定类型对象 |
| cipher | cipher |


## <span id="devenginelog">**`dev.engine.log`**</span>


* **Log Engine ->** [DevLogEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/log/DevLogEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Log Engine 接口 ->** [ILogEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/log/ILogEngine.java)

| 方法 | 注释 |
| :- | :- |
| isPrintLog | 判断是否打印日志 |
| d | 打印 Log.DEBUG |
| e | 打印 Log.ERROR |
| w | 打印 Log.WARN |
| i | 打印 Log.INFO |
| v | 打印 Log.VERBOSE |
| wtf | 打印 Log.ASSERT |
| json | 格式化 JSON 格式数据, 并打印 |
| xml | 格式化 XML 格式数据, 并打印 |
| dTag | 打印 Log.DEBUG |
| eTag | 打印 Log.ERROR |
| wTag | 打印 Log.WARN |
| iTag | 打印 Log.INFO |
| vTag | 打印 Log.VERBOSE |
| wtfTag | 打印 Log.ASSERT |
| jsonTag | 格式化 JSON 格式数据, 并打印 |
| xmlTag | 格式化 XML 格式数据, 并打印 |


## <span id="devenginemedia">**`dev.engine.media`**</span>


* **Media Selector Engine ->** [DevMediaEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/media/DevMediaEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Media Selector Engine 接口 ->** [IMediaEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/media/IMediaEngine.java)

| 方法 | 注释 |
| :- | :- |
| getConfig | 获取全局配置 |
| setConfig | 设置全局配置 |
| openCamera | 打开相册拍照 |
| openGallery | 打开相册选择 |
| openPreview | 打开相册预览 |
| deleteCacheDirFile | 删除缓存文件 |
| deleteAllCacheDirFile | 删除全部缓存文件 |
| isMediaSelectorResult | 是否图片选择 ( onActivityResult ) |
| getSelectors | 获取 Media Selector Data List |
| getSelectorUris | 获取 Media Selector Uri List |
| getSingleSelector | 获取 Single Media Selector Data |
| getSingleSelectorUri | 获取 Single Media Selector Uri |


## <span id="devenginepermission">**`dev.engine.permission`**</span>


* **Permission Engine ->** [DevPermissionEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/permission/DevPermissionEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Permission Engine 接口 ->** [IPermissionEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/permission/IPermissionEngine.java)

| 方法 | 注释 |
| :- | :- |
| request | 请求权限 |
| isGrantedPermission | 判断是否授予了权限 |
| isGrantedPermissions | 判断是否授予了权限 |
| getGrantedPermissions | 获取已经授予的权限 |
| getDeniedPermissions | 获取已经拒绝的权限 |
| isDoNotAskAgainPermission | 获取拒绝权限询问勾选状态 |
| isDoNotAskAgainPermissions | 获取拒绝权限询问勾选状态 |
| equalsPermission | 判断两个权限是否相等 |
| containsPermission | 判断权限列表中是否包含某个权限 |
| permissionName | 获取权限的名称 |
| onResult | 权限请求结果回调 |


## <span id="devenginepopnotification">**`dev.engine.popnotification`**</span>


* **PopNotification Engine ->** [DevPopNotificationEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/popnotification/DevPopNotificationEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **PopNotification Engine 接口 ->** [IPopNotificationEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/popnotification/IPopNotificationEngine.java)

| 方法 | 注释 |
| :- | :- |
| getConfig | 获取 PopNotification Engine Config |
| setConfig | 设置 PopNotification Engine Config |
| build | 构建 PopNotification ( 不显示 ) |
| buildView | 构建 PopNotification ( 不显示 ) |
| show | 显示 PopNotification |
| isSinglePopNotification | 是否使用单例 PopNotification |
| getSinglePopNotification | 获取单例 PopNotification |
| isShowSinglePopNotification | 单例 PopNotification 是否正在显示 |
| dismissSinglePopNotification | 关闭单例 PopNotification |
| hideSinglePopNotification | 关闭单例 PopNotification ( 动画 ) |
| isShow | 指定 PopNotification 是否正在显示 |
| dismiss | 关闭指定 PopNotification |
| hide | 关闭指定 PopNotification ( 动画 ) |
| refreshUI | 刷新指定 PopNotification 界面 |
| autoDismiss | 设置指定 PopNotification 自动消失时长 |
| resetAutoDismissTimer | 重置指定 PopNotification 自动消失计时器 |
| showShort | 指定 PopNotification 短时间显示 |
| showLong | 指定 PopNotification 长时间显示 |
| showAlways | 指定 PopNotification 常驻显示 ( 不自动消失 ) |
| noAutoDismiss | 指定 PopNotification 取消自动消失 |
| bringToFront | 指定 PopNotification 置顶显示 |
| setThisOrderIndex | 设置指定 PopNotification 显示层级 |
| setStyle | 设置指定 PopNotification 主题样式 |
| setTheme | 设置指定 PopNotification 明暗主题 |
| setCustomView | 设置指定 PopNotification 自定义布局 |
| getCustomView | 获取指定 PopNotification 自定义布局 View |
| removeCustomView | 移除指定 PopNotification 自定义布局 |
| getAlign | 获取指定 PopNotification 对齐方式 |
| setAlign | 设置指定 PopNotification 对齐方式 |
| getIconResId | 获取指定 PopNotification 图标资源 id |
| setIconResId | 设置指定 PopNotification 图标资源 id |
| getIconBitmap | 获取指定 PopNotification 图标 Bitmap |
| setIcon | 设置指定 PopNotification 图标 Bitmap |
| getIconDrawable | 获取指定 PopNotification 图标 Drawable |
| getIconSize | 获取指定 PopNotification 图标尺寸 ( px ) |
| setIconSize | 设置指定 PopNotification 图标尺寸 ( px ) |
| getTitle | 获取指定 PopNotification 标题文本 |
| setTitle | 设置指定 PopNotification 标题文本 |
| getTitleTextInfo | 获取指定 PopNotification 标题文本样式 |
| setTitleTextInfo | 设置指定 PopNotification 标题文本样式 |
| getMessage | 获取指定 PopNotification 提示文本 |
| setMessage | 设置指定 PopNotification 提示文本 |
| appendMessage | 追加指定 PopNotification 提示文本 |
| getButtonText | 获取指定 PopNotification 按钮文本 |
| setButton | 设置指定 PopNotification 按钮文本 |
| getMessageTextInfo | 获取指定 PopNotification 提示文本样式 |
| setMessageTextInfo | 设置指定 PopNotification 提示文本样式 |
| getButtonTextInfo | 获取指定 PopNotification 按钮文本样式 |
| setButtonTextInfo | 设置指定 PopNotification 按钮文本样式 |
| setOnButtonClickListener | 设置指定 PopNotification 按钮点击事件 |
| setOnPopNotificationClickListener | 设置指定 PopNotification 自身点击事件 |
| isAutoTintIconInLightOrDarkMode | 指定 PopNotification 图标是否随明暗模式自动染色 |
| setAutoTintIconInLightOrDarkMode | 设置指定 PopNotification 图标是否随明暗模式自动染色 |
| getTintIcon | 指定 PopNotification 图标是否染色 |
| setTintIcon | 设置指定 PopNotification 图标是否染色 |
| iconSuccess | 设置指定 PopNotification 成功状态图标 |
| iconWarning | 设置指定 PopNotification 警告状态图标 |
| iconError | 设置指定 PopNotification 错误状态图标 |
| getBackgroundColor | 获取指定 PopNotification 背景色 |
| setBackgroundColor | 设置指定 PopNotification 背景色 |
| setBackgroundColorRes | 设置指定 PopNotification 背景色 |
| getRadius | 获取指定 PopNotification 圆角 ( px ) |
| setRadius | 设置指定 PopNotification 圆角 ( px ) |
| getEnterAnimDuration | 获取指定 PopNotification 进入动画时长 ( ms ) |
| setEnterAnimDuration | 设置指定 PopNotification 进入动画时长 ( ms ) |
| getExitAnimDuration | 获取指定 PopNotification 退出动画时长 ( ms ) |
| setExitAnimDuration | 设置指定 PopNotification 退出动画时长 ( ms ) |
| setAnimResId | 设置指定 PopNotification 进出场动画资源 |
| setEnterAnimResId | 设置指定 PopNotification 进入动画资源 |
| setExitAnimResId | 设置指定 PopNotification 退出动画资源 |
| getDialogXAnimImpl | 获取指定 PopNotification 动画实现 |
| setDialogXAnimImpl | 设置指定 PopNotification 动画实现 |
| setHapticFeedbackEnabled | 设置指定 PopNotification 是否启用振动反馈 |
| isSlideToClose | 指定 PopNotification 是否支持滑动关闭 |
| setSlideToClose | 设置指定 PopNotification 是否支持滑动关闭 |
| setDialogImplMode | 设置指定 PopNotification 实现模式 |
| setMargin | 设置指定 PopNotification 外边距 |
| getMarginLeft | 获取指定 PopNotification 左外边距 |
| setMarginLeft | 设置指定 PopNotification 左外边距 |
| getMarginTop | 获取指定 PopNotification 上外边距 |
| setMarginTop | 设置指定 PopNotification 上外边距 |
| getMarginRight | 获取指定 PopNotification 右外边距 |
| setMarginRight | 设置指定 PopNotification 右外边距 |
| getMarginBottom | 获取指定 PopNotification 下外边距 |
| setMarginBottom | 设置指定 PopNotification 下外边距 |
| setRootPadding | 设置指定 PopNotification 根布局内边距 |
| setLifecycleListener | 设置指定 PopNotification 显示生命周期监听 |
| onShow | 设置指定 PopNotification 显示回调 |
| onDismiss | 设置指定 PopNotification 关闭回调 |
| setActionRunnable | 设置指定 PopNotification 快捷功能键动作 |
| cleanAction | 清除指定 PopNotification 快捷功能键动作 |
| cleanAllAction | 清除指定 PopNotification 全部快捷功能键动作 |
| setData | 设置指定 PopNotification 临时存储数据 |
| bindDismissWithLifecycleOwner | 绑定指定 PopNotification 随 LifecycleOwner 关闭 |
| setCustomDialogLayoutResId | 设置指定 PopNotification 自定义弹窗布局资源 id |
| onlyOne | onlyOne |
| autoDismissDelay | autoDismissDelay |
| align | align |
| dialogImplMode | dialogImplMode |
| radiusPx | radiusPx |
| style | style |
| theme | theme |
| titleTextInfo | titleTextInfo |
| messageTextInfo | messageTextInfo |
| buttonTextInfo | buttonTextInfo |
| useHaptic | useHaptic |
| enterAnimDuration | enterAnimDuration |
| exitAnimDuration | exitAnimDuration |
| backgroundColor | backgroundColor |
| maxShowCount | maxShowCount |
| overrideEnterDuration | overrideEnterDuration |
| overrideExitDuration | overrideExitDuration |
| overrideEnterAnimRes | overrideEnterAnimRes |
| overrideExitAnimRes | overrideExitAnimRes |
| moveDisplacementInterceptor | moveDisplacementInterceptor |
| title | title |
| titleResId | titleResId |
| message | message |
| messageResId | messageResId |
| iconResId | iconResId |
| iconBitmap | iconBitmap |
| iconDrawable | iconDrawable |
| iconSize | iconSize |
| buttonText | buttonText |
| buttonTextResId | buttonTextResId |
| onButtonClickListener | onButtonClickListener |
| radius | radius |
| customView | customView |
| lifecycleListener | lifecycleListener |
| iconState | iconState |
| onPopNotificationClickListener | onPopNotificationClickListener |
| backgroundColorRes | backgroundColorRes |
| autoTintIcon | autoTintIcon |
| tintIcon | tintIcon |
| slideToClose | slideToClose |
| enterAnimResId | enterAnimResId |
| exitAnimResId | exitAnimResId |
| dialogXAnimImpl | dialogXAnimImpl |
| hapticFeedbackEnabled | hapticFeedbackEnabled |
| marginLeft | marginLeft |
| marginTop | marginTop |
| marginRight | marginRight |
| marginBottom | marginBottom |
| rootPadding | rootPadding |
| data | data |
| thisOrderIndex | thisOrderIndex |
| lifecycleOwner | lifecycleOwner |
| customDialogLayoutResId | customDialogLayoutResId |
| customDialogLayoutLightTheme | customDialogLayoutLightTheme |
| onClick | 按钮点击回调 |
| run | 执行回调 |


## <span id="devenginepoptip">**`dev.engine.poptip`**</span>


* **PopTip Engine ->** [DevPopTipEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/poptip/DevPopTipEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **PopTip Engine 接口 ->** [IPopTipEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/poptip/IPopTipEngine.java)

| 方法 | 注释 |
| :- | :- |
| getConfig | 获取 PopTip Engine Config |
| setConfig | 设置 PopTip Engine Config |
| build | 构建 PopTip ( 不显示 ) |
| buildView | 构建 PopTip ( 不显示 ) |
| show | 显示 PopTip |
| isSinglePopTip | 是否使用单例 PopTip |
| getSinglePopTip | 获取单例 PopTip |
| isShowSinglePopTip | 单例 PopTip 是否正在显示 |
| dismissSinglePopTip | 关闭单例 PopTip |
| hideSinglePopTip | 关闭单例 PopTip ( 动画 ) |
| isShow | 指定 PopTip 是否正在显示 |
| dismiss | 关闭指定 PopTip |
| hide | 关闭指定 PopTip ( 动画 ) |
| refreshUI | 刷新指定 PopTip 界面 |
| autoDismiss | 设置指定 PopTip 自动消失时长 |
| resetAutoDismissTimer | 重置指定 PopTip 自动消失计时器 |
| showShort | 指定 PopTip 短时间显示 |
| showLong | 指定 PopTip 长时间显示 |
| showAlways | 指定 PopTip 常驻显示 ( 不自动消失 ) |
| noAutoDismiss | 指定 PopTip 取消自动消失 |
| bringToFront | 指定 PopTip 置顶显示 |
| setThisOrderIndex | 设置指定 PopTip 显示层级 |
| setStyle | 设置指定 PopTip 主题样式 |
| setTheme | 设置指定 PopTip 明暗主题 |
| setCustomView | 设置指定 PopTip 自定义布局 |
| getCustomView | 获取指定 PopTip 自定义布局 View |
| removeCustomView | 移除指定 PopTip 自定义布局 |
| getAlign | 获取指定 PopTip 对齐方式 |
| setAlign | 设置指定 PopTip 对齐方式 |
| getIconResId | 获取指定 PopTip 图标资源 id |
| setIconResId | 设置指定 PopTip 图标资源 id |
| getMessage | 获取指定 PopTip 提示文本 |
| setMessage | 设置指定 PopTip 提示文本 |
| appendMessage | 追加指定 PopTip 提示文本 |
| getButtonText | 获取指定 PopTip 按钮文本 |
| setButton | 设置指定 PopTip 按钮文本 |
| getMessageTextInfo | 获取指定 PopTip 提示文本样式 |
| setMessageTextInfo | 设置指定 PopTip 提示文本样式 |
| getButtonTextInfo | 获取指定 PopTip 按钮文本样式 |
| setButtonTextInfo | 设置指定 PopTip 按钮文本样式 |
| setOnButtonClickListener | 设置指定 PopTip 按钮点击事件 |
| setOnPopTipClickListener | 设置指定 PopTip 自身点击事件 |
| isAutoTintIconInLightOrDarkMode | 指定 PopTip 图标是否随明暗模式自动染色 |
| setAutoTintIconInLightOrDarkMode | 设置指定 PopTip 图标是否随明暗模式自动染色 |
| isTintIcon | 指定 PopTip 图标是否染色 |
| setTintIcon | 设置指定 PopTip 图标是否染色 |
| iconSuccess | 设置指定 PopTip 成功状态图标 |
| iconWarning | 设置指定 PopTip 警告状态图标 |
| iconError | 设置指定 PopTip 错误状态图标 |
| getBackgroundColor | 获取指定 PopTip 背景色 |
| setBackgroundColor | 设置指定 PopTip 背景色 |
| setBackgroundColorRes | 设置指定 PopTip 背景色 |
| getRadius | 获取指定 PopTip 圆角 ( px ) |
| setRadius | 设置指定 PopTip 圆角 ( px ) |
| getEnterAnimDuration | 获取指定 PopTip 进入动画时长 ( ms ) |
| setEnterAnimDuration | 设置指定 PopTip 进入动画时长 ( ms ) |
| getExitAnimDuration | 获取指定 PopTip 退出动画时长 ( ms ) |
| setExitAnimDuration | 设置指定 PopTip 退出动画时长 ( ms ) |
| setAnimResId | 设置指定 PopTip 进出场动画资源 |
| setEnterAnimResId | 设置指定 PopTip 进入动画资源 |
| setExitAnimResId | 设置指定 PopTip 退出动画资源 |
| getDialogXAnimImpl | 获取指定 PopTip 动画实现 |
| setDialogXAnimImpl | 设置指定 PopTip 动画实现 |
| setHapticFeedbackEnabled | 设置指定 PopTip 是否启用振动反馈 |
| setDialogImplMode | 设置指定 PopTip 实现模式 |
| setMargin | 设置指定 PopTip 外边距 |
| getMarginLeft | 获取指定 PopTip 左外边距 |
| setMarginLeft | 设置指定 PopTip 左外边距 |
| getMarginTop | 获取指定 PopTip 上外边距 |
| setMarginTop | 设置指定 PopTip 上外边距 |
| getMarginRight | 获取指定 PopTip 右外边距 |
| setMarginRight | 设置指定 PopTip 右外边距 |
| getMarginBottom | 获取指定 PopTip 下外边距 |
| setMarginBottom | 设置指定 PopTip 下外边距 |
| setRootPadding | 设置指定 PopTip 根布局内边距 |
| setLifecycleListener | 设置指定 PopTip 显示生命周期监听 |
| onShow | 设置指定 PopTip 显示回调 |
| onDismiss | 设置指定 PopTip 关闭回调 |
| setActionRunnable | 设置指定 PopTip 快捷功能键动作 |
| cleanAction | 清除指定 PopTip 快捷功能键动作 |
| cleanAllAction | 清除指定 PopTip 全部快捷功能键动作 |
| setData | 设置指定 PopTip 临时存储数据 |
| bindDismissWithLifecycleOwner | 绑定指定 PopTip 随 LifecycleOwner 关闭 |
| setCustomDialogLayoutResId | 设置指定 PopTip 自定义弹窗布局资源 id |
| onlyOne | onlyOne |
| autoDismissDelay | autoDismissDelay |
| align | align |
| dialogImplMode | dialogImplMode |
| radiusPx | radiusPx |
| style | style |
| theme | theme |
| messageTextInfo | messageTextInfo |
| buttonTextInfo | buttonTextInfo |
| useHaptic | useHaptic |
| enterAnimDuration | enterAnimDuration |
| exitAnimDuration | exitAnimDuration |
| backgroundColor | backgroundColor |
| maxShowCount | maxShowCount |
| overrideEnterDuration | overrideEnterDuration |
| overrideExitDuration | overrideExitDuration |
| overrideEnterAnimRes | overrideEnterAnimRes |
| overrideExitAnimRes | overrideExitAnimRes |
| moveDisplacementInterceptor | moveDisplacementInterceptor |
| message | message |
| messageResId | messageResId |
| iconResId | iconResId |
| buttonText | buttonText |
| buttonTextResId | buttonTextResId |
| onButtonClickListener | onButtonClickListener |
| radius | radius |
| customView | customView |
| lifecycleListener | lifecycleListener |
| iconState | iconState |
| onPopTipClickListener | onPopTipClickListener |
| backgroundColorRes | backgroundColorRes |
| autoTintIcon | autoTintIcon |
| tintIcon | tintIcon |
| enterAnimResId | enterAnimResId |
| exitAnimResId | exitAnimResId |
| dialogXAnimImpl | dialogXAnimImpl |
| hapticFeedbackEnabled | hapticFeedbackEnabled |
| marginLeft | marginLeft |
| marginTop | marginTop |
| marginRight | marginRight |
| marginBottom | marginBottom |
| rootPadding | rootPadding |
| data | data |
| thisOrderIndex | thisOrderIndex |
| lifecycleOwner | lifecycleOwner |
| customDialogLayoutResId | customDialogLayoutResId |
| customDialogLayoutLightTheme | customDialogLayoutLightTheme |
| onClick | 按钮点击回调 |
| run | 执行回调 |


## <span id="devenginepush">**`dev.engine.push`**</span>


* **Push Engine ->** [DevPushEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/push/DevPushEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Push Engine 接口 ->** [IPushEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/push/IPushEngine.java)

| 方法 | 注释 |
| :- | :- |
| initialize | 初始化方法 |
| register | 绑定 |
| unregister | 解绑 |
| onReceiveServicePid | 推送进程启动通知 |
| onReceiveClientId | 初始化 Client Id 成功通知 |
| onReceiveDeviceToken | 设备 ( 厂商 ) Token 通知 |
| onReceiveOnlineState | 在线状态变化通知 |
| onReceiveCommandResult | 命令回执通知 |
| onNotificationMessageArrived | 推送消息送达通知 |
| onNotificationMessageClicked | 推送消息点击通知 |
| onReceiveMessageData | 透传消息送达通知 |
| convertMessage | 传入 Object 转换 Engine Message |


## <span id="devenginerefresh">**`dev.engine.refresh`**</span>


* **Refresh View Engine ->** [DevRefreshEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/refresh/DevRefreshEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Refresh View Engine 接口 ->** [IRefreshEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/refresh/IRefreshEngine.java)

| 方法 | 注释 |
| :- | :- |
| getConfig | 获取 Refresh Engine Config |
| initialize | 初始化 Refresh View |
| applyConfig | 应用 Refresh Config |
| setHeaderHeight | 设置 Header 高度 |
| setHeaderHeightPx | 设置 Header 高度 |
| setFooterHeight | 设置 Footer 高度 |
| setFooterHeightPx | 设置 Footer 高度 |
| setHeaderInsetStart | 设置 Header 起始偏移量 |
| setHeaderInsetStartPx | 设置 Header 起始偏移量 |
| setFooterInsetStart | 设置 Footer 起始偏移量 |
| setFooterInsetStartPx | 设置 Footer 起始偏移量 |
| setDragRate | 设置拖拽阻尼比率 |
| setHeaderMaxDragRate | 设置 Header 最大拖拽高度比率 |
| setFooterMaxDragRate | 设置 Footer 最大拖拽高度比率 |
| setHeaderTriggerRate | 设置 Header 触发刷新比率 |
| setFooterTriggerRate | 设置 Footer 触发加载比率 |
| setReboundInterpolator | 设置回弹动画插值器 |
| setReboundDuration | 设置回弹动画时长 |
| setRefreshHeader | 设置刷新头 |
| setRefreshFooter | 设置加载尾 |
| setRefreshContent | 设置内容 View |
| setEnableRefresh | 是否启用下拉刷新 |
| setEnableLoadMore | 是否启用上拉加载更多 |
| setEnableAutoLoadMore | 是否启用滚动到底部自动加载 |
| setEnableHeaderTranslationContent | 是否启用 Header 移动内容 |
| setEnableFooterTranslationContent | 是否启用 Footer 移动内容 |
| setEnableOverScrollBounce | 是否启用越界回弹 |
| setEnablePureScrollMode | 是否启用纯滚动模式 |
| setEnableScrollContentWhenLoaded | 加载完成后是否滚动内容显示新数据 |
| setEnableScrollContentWhenRefreshed | 刷新完成后是否滚动内容显示新数据 |
| setEnableLoadMoreWhenContentNotFull | 内容不满一页时是否可以加载更多 |
| setEnableOverScrollDrag | 是否启用越界拖动 |
| setEnableFooterFollowWhenNoMoreData | 没有更多数据后 Footer 是否跟随内容 |
| setEnableClipHeaderWhenFixedBehind | Header FixedBehind 时是否裁剪 Header |
| setEnableClipFooterWhenFixedBehind | Footer FixedBehind 时是否裁剪 Footer |
| setEnableNestedScroll | 是否启用嵌套滚动 |
| setFixedHeaderViewId | 设置固定在 Header 下方的视图 id |
| setFixedFooterViewId | 设置固定在 Footer 上方的视图 id |
| setHeaderTranslationViewId | 设置 Header 滚动时跟随滚动的视图 id |
| setFooterTranslationViewId | 设置 Footer 滚动时跟随滚动的视图 id |
| setDisableContentWhenRefresh | 刷新时是否禁用内容操作 |
| setDisableContentWhenLoading | 加载时是否禁用内容操作 |
| setOnRefreshListener | 设置刷新监听器 |
| setOnLoadMoreListener | 设置加载监听器 |
| setOnRefreshLoadMoreListener | 设置刷新和加载监听器 |
| setOnMultiListener | 设置多功能监听器 |
| setScrollBoundaryDecider | 设置滚动边界判断器 |
| setPrimaryColors | 设置主题色 |
| setPrimaryColorsId | 设置主题色资源 id |
| autoRefresh | 自动触发刷新 |
| autoRefreshAnimationOnly | 自动触发刷新动画 |
| autoLoadMore | 自动触发加载 |
| autoLoadMoreAnimationOnly | 自动触发加载动画 |
| finishRefresh | 完成刷新 |
| finishRefreshWithNoMoreData | 完成刷新并标记没有更多数据 |
| finishLoadMore | 完成加载 |
| finishLoadMoreWithNoMoreData | 完成加载并标记没有更多数据 |
| closeHeaderOrFooter | 关闭 Header 或 Footer |
| setNoMoreData | 设置没有更多数据状态 |
| resetNoMoreData | 重置没有更多数据状态 |
| finishRefreshAndLoad | 完成刷新、加载 |
| finishRefreshOrLoad | 完成刷新或加载 |
| getRefreshHeader | 获取刷新头 |
| getRefreshFooter | 获取加载尾 |
| getState | 获取当前状态 |
| getLayout | 获取实体布局视图 |
| isRefreshing | 是否正在刷新 |
| isLoading | 是否正在加载 |
| headerHeight | headerHeight |
| headerHeightPx | headerHeightPx |
| footerHeight | footerHeight |
| footerHeightPx | footerHeightPx |
| headerInsetStart | headerInsetStart |
| headerInsetStartPx | headerInsetStartPx |
| footerInsetStart | footerInsetStart |
| footerInsetStartPx | footerInsetStartPx |
| dragRate | dragRate |
| headerMaxDragRate | headerMaxDragRate |
| footerMaxDragRate | footerMaxDragRate |
| headerTriggerRate | headerTriggerRate |
| footerTriggerRate | footerTriggerRate |
| reboundInterpolator | reboundInterpolator |
| reboundDuration | reboundDuration |
| enableRefresh | enableRefresh |
| enableLoadMore | enableLoadMore |
| enableAutoLoadMore | enableAutoLoadMore |
| enableHeaderTranslationContent | enableHeaderTranslationContent |
| enableFooterTranslationContent | enableFooterTranslationContent |
| enableOverScrollBounce | enableOverScrollBounce |
| enablePureScrollMode | enablePureScrollMode |
| enableScrollContentWhenLoaded | enableScrollContentWhenLoaded |
| enableScrollContentWhenRefreshed | enableScrollContentWhenRefreshed |
| enableLoadMoreWhenContentNotFull | enableLoadMoreWhenContentNotFull |
| enableOverScrollDrag | enableOverScrollDrag |
| enableFooterFollowWhenNoMoreData | enableFooterFollowWhenNoMoreData |
| enableClipHeaderWhenFixedBehind | enableClipHeaderWhenFixedBehind |
| enableClipFooterWhenFixedBehind | enableClipFooterWhenFixedBehind |
| enableNestedScroll | enableNestedScroll |
| disableContentWhenRefresh | disableContentWhenRefresh |
| disableContentWhenLoading | disableContentWhenLoading |
| primaryColors | primaryColors |
| primaryColorIds | primaryColorIds |
| view | view |
| config | config |
| content | content |
| contentWidth | contentWidth |
| contentHeight | contentHeight |
| header | header |
| headerWidth | headerWidth |
| footer | footer |
| footerWidth | footerWidth |
| fixedHeaderViewId | fixedHeaderViewId |
| fixedFooterViewId | fixedFooterViewId |
| headerTranslationViewId | headerTranslationViewId |
| footerTranslationViewId | footerTranslationViewId |
| scrollBoundaryDecider | scrollBoundaryDecider |
| onRefreshListener | onRefreshListener |
| onLoadMoreListener | onLoadMoreListener |
| onRefreshLoadMoreListener | onRefreshLoadMoreListener |
| multiListener | multiListener |
| onRefresh | onRefresh |
| onLoadMore | onLoadMore |


## <span id="devenginerouter">**`dev.engine.router`**</span>


* **Router Engine ->** [DevRouterEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/router/DevRouterEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Router Engine 接口 ->** [IRouterEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/router/IRouterEngine.java)

| 方法 | 注释 |
| :- | :- |
| getConfig | 获取 Router Engine Config |
| setConfig | 设置 Router Engine Config |
| initialize | 初始化 Router Engine |
| isInitialized | Router 是否已初始化 |
| setDebug | 设置是否为 Debug 环境 |
| isDebug | 是否为 Debug 环境 |
| setLogCallback | 设置日志输出回调 |
| inject | 为 Autowired 注解的变量赋值 |
| isRouterPath | 判断 url 是否为 TheRouter 的路由 Path |
| isRouterAction | 判断 url 是否为 TheRouter 的 Action |
| getService | 获取跨模块依赖的服务 |
| runTask | 执行业务自定义 FlowTask |
| setDefaultNavigationCallback | 设置全局默认路由跳转结果回调 |
| addNavigatorPathFixHandle | 新增 Path 修改器 |
| removeNavigatorPathFixHandle | 移除 Path 修改器 |
| addPathReplaceInterceptor | 新增 Path 替换拦截器 |
| removePathReplaceInterceptor | 移除 Path 替换拦截器 |
| addRouterReplaceInterceptor | 新增路由替换拦截器 |
| removeRouterReplaceInterceptor | 移除路由替换拦截器 |
| addActionInterceptor | 新增 Action 拦截器 |
| removeActionInterceptor | 移除 Action 拦截器 |
| removeAllInterceptorForKey | 移除指定 Action 的全部拦截器 |
| removeAllInterceptorForValue | 移除指定拦截器 ( 所有 Action 共用 ) |
| addAutowiredParser | 新增 Autowired 注解解析器 |
| init | 手动初始化 TheRouter |
| setRouterInterceptor | 设置路由 AOP 拦截器 |
| sendPendingNavigator | 恢复 pending 状态的 Navigator 跳转 |
| optGlobalObject | 获取 Navigator 全局 Object 参数 |
| build | 通过 Path 构建 Navigator ( 不跳转 ) |
| getUrlWithParams | 获取带参数的完整 url |
| getNavigatorUrl | 获取 Navigator 当前 url |
| getOriginalUrl | 获取 Navigator 原始 url |
| getPathFixOriginalUrl | 获取 Navigator PathFix 后的原始 url |
| getSimpleUrl | 获取 Navigator 简化 url ( 不含 query ) |
| getNavigatorExtras | 获取 Navigator 参数 Bundle |
| getNavigatorIntent | 获取 Navigator 关联 Intent |
| pending | 设置 pending 等待路由表初始化 |
| withInt | 设置 Int 参数 |
| withLong | 设置 Long 参数 |
| withDouble | 设置 Double 参数 |
| withFloat | 设置 Float 参数 |
| withChar | 设置 Char 参数 |
| withByte | 设置 Byte 参数 |
| withBoolean | 设置 Boolean 参数 |
| withString | 设置 String 参数 |
| withSerializable | 设置 Serializable 参数 |
| withParcelable | 设置 Parcelable 参数 |
| withObject | 设置 Object 参数 |
| withBundle | 设置 Bundle 参数 |
| with | 设置 Bundle 参数 ( 合并到 Navigator ) |
| fillParams | 批量填充 Navigator 参数 Bundle |
| setData | 设置 Intent Data |
| setIdentifier | 设置 Intent Identifier |
| setClipData | 设置 Intent ClipData |
| optObject | 获取 Navigator Object 参数 |
| addFlags | 追加 Intent Flags |
| withFlags | 设置 Intent Flags |
| withOptionsCompat | 设置 Activity Options Bundle |
| withInAnimation | 设置进入动画资源 id |
| withOutAnimation | 设置退出动画资源 id |
| createIntent | 创建 Intent |
| createIntentWithCallback | 异步创建 Intent |
| createFragment | 创建 Fragment |
| createFragmentWithCallback | 异步创建 Fragment |
| navigation | 执行路由跳转 |
| action | 执行 Action |
| debug | debug |
| autoInit | autoInit |
| asyncInitRouterInject | asyncInitRouterInject |
| path | path |
| requestCode | requestCode |
| flags | flags |
| animIn | animIn |
| animOut | animOut |
| context | context |
| fragment | fragment |
| optionsCompat | optionsCompat |
| intentData | intentData |
| intentIdentifier | intentIdentifier |
| intentClipData | intentClipData |
| navigationCallback | navigationCallback |
| extras | extras |
| objectParams | objectParams |
| onFound | 找到路由 |
| onLost | 未找到路由 |
| onArrival | 路由到达 |
| onActivityCreated | Activity 创建完成 |
| log | 输出日志 |
| fill | 填充路由参数 Bundle |
| onIntent | Intent 创建完成 |
| onFragment | Fragment 创建完成 |
| fix | 修正单个 key-value 拼接 |
| onContinue | 继续路由 |
| process | 路由 AOP 处理 |


## <span id="devengineshare">**`dev.engine.share`**</span>


* **Share Engine ->** [DevShareEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/share/DevShareEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Share Engine 接口 ->** [IShareEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/share/IShareEngine.java)

| 方法 | 注释 |
| :- | :- |
| initialize | 初始化方法 |
| openMinApp | 打开小程序 |
| shareMinApp | 分享小程序 |
| shareUrl | 分享链接 |
| shareImage | 分享图片 |
| shareImageList | 分享多张图片 |
| shareText | 分享文本 |
| shareVideo | 分享视频 |
| shareMusic | 分享音乐 |
| shareEmoji | 分享表情 |
| shareFile | 分享文件 |
| share | 分享操作 ( 通用扩展 ) |
| onActivityResult | 部分平台 Activity onActivityResult 额外调用处理 |


## <span id="devenginesharelistener">**`dev.engine.share.listener`**</span>


* **分享回调 ->** [ShareListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/share/listener/ShareListener.java)

| 方法 | 注释 |
| :- | :- |
| onStart | 开始分享 |
| onResult | 分享成功 |
| onError | 分享失败 |
| onCancel | 取消分享 |


## <span id="devenginestorage">**`dev.engine.storage`**</span>


* **Storage Engine ->** [DevStorageEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/storage/DevStorageEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Storage Engine 接口 ->** [IStorageEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/storage/IStorageEngine.java)

| 方法 | 注释 |
| :- | :- |
| insertImageToExternal | 插入一张图片到外部存储空间 ( SDCard ) |
| insertVideoToExternal | 插入一条视频到外部存储空间 ( SDCard ) |
| insertAudioToExternal | 插入一条音频到外部存储空间 ( SDCard ) |
| insertDownloadToExternal | 插入一条文件资源到外部存储空间 ( SDCard ) |
| insertMediaToExternal | 插入一条多媒体资源到外部存储空间 ( SDCard ) |
| insertImageToInternal | 插入一张图片到内部存储空间 |
| insertVideoToInternal | 插入一条视频到内部存储空间 |
| insertAudioToInternal | 插入一条音频到内部存储空间 |
| insertDownloadToInternal | 插入一条文件资源到内部存储空间 |
| insertMediaToInternal | 插入一条多媒体资源到内部存储空间 |


## <span id="devenginestoragelistener">**`dev.engine.storage.listener`**</span>


* **插入多媒体资源事件 ->** [OnInsertListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/storage/listener/OnInsertListener.java)

| 方法 | 注释 |
| :- | :- |
| onResult | 插入多媒体资源结果方法 |


## <span id="devenginetoast">**`dev.engine.toast`**</span>


* **Toast Engine ->** [DevToastEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/toast/DevToastEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Toast Engine 接口 ->** [IToastEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/toast/IToastEngine.java)

| 方法 | 注释 |
| :- | :- |
| initialize | 初始化方法 |
| isInit | 判断 Toast 框架是否已经初始化 |
| setDebugMode | 设置是否为调试模式 |
| setConfig | 设置 Toast Config |
| getConfig | 获取 Toast Config |
| cancel | 取消 Toast 的显示 |
| delayedShow | 延迟显示 Toast |
| debugShow | debug 模式下显示 Toast |
| showShort | 显示一个短 Toast |
| showLong | 显示一个长 Toast |
| show | 显示 Toast |
| setView | 给当前 Toast 设置新的布局 |
| setGravity | 设置 Toast 的位置 |


## <span id="devengineweb">**`dev.engine.web`**</span>


* **WebView Engine ->** [DevWebEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/web/DevWebEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **WebView Engine 接口 ->** [IWebEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/web/IWebEngine.java)

| 方法 | 注释 |
| :- | :- |
| getConfig | 获取 WebView Engine Config |
| initialize | 初始化 WebView ( 应用配置、设置 Client、监听、JS 交互对象 ) |
| applyConfig | 应用 WebView Config |
| getWebView | 获取 WebView |
| isWebViewNotEmpty | WebView 是否不为 null |
| loadUrl | 加载网页 |
| loadData | 加载 Html 代码 |
| loadDataWithBaseURL | 加载 Html 代码 |
| postUrl | 使用 POST 方法将带有 postData 的 url 加载到 WebView 中 |
| getSettings | 获取 WebView 配置 ( WebSettings ) |
| getUserAgentString | 获取浏览器标识 UA |
| setUserAgentString | 设置浏览器标识 UA |
| addJavascriptInterface | 添加 JS 交互注入对象 |
| removeJavascriptInterface | 移除 JS 交互注入对象 |
| evaluateJavascript | 执行 JS 方法 |
| setWebViewClient | 设置处理各种通知和请求事件对象 |
| getWebViewClient | 获取处理各种通知和请求事件对象 |
| setWebChromeClient | 设置辅助 WebView 处理 Javascript 对话框、标题等对象 |
| getWebChromeClient | 获取辅助 WebView 处理 Javascript 对话框、标题等对象 |
| setDownloadListener | 设置下载监听 |
| setFindListener | 设置查找结果监听 |
| canGoBack | WebView 是否可以后退 |
| goBack | WebView 后退 |
| canGoForward | WebView 是否可以前进 |
| goForward | WebView 前进 |
| canGoBackOrForward | WebView 是否可以跳转到当前起始点相距的历史记录 |
| goBackOrForward | WebView 跳转到当前起始点相距的历史记录 |
| copyBackForwardList | 获取 WebView 历史记录列表 ( WebBackForwardList ) |
| reload | 刷新页面 ( 当前页面的所有资源都会重新加载 ) |
| stopLoading | 停止加载 |
| clearCache | 清除资源缓存 |
| clearHistory | 清除当前 WebView 访问的历史记录 |
| clearFormData | 清除自动完成填充的表单数据 |
| clearMatches | 清除高亮显示的查找结果 |
| clearSslPreferences | 清除 SSL 偏好设置 |
| deleteAllWebStorage | 清除全部 Web 存储数据 ( localStorage、IndexedDB、WebSQL 等, 全局生效 ) |
| getScale | 获取缩放比例 |
| getScrollX | 获取当前内容横向滚动距离 |
| getScrollY | 获取当前可见区域的顶端距整个页面顶端的距离 ( 当前内容滚动的距离 ) |
| getContentHeight | 获取 HTML 的高度 ( 原始高度, 不包括缩放后的高度 ) |
| getScaleHeight | 获取缩放高度 |
| getHeight | 获取 WebView 控件高度 |
| getUrl | 获取当前 Url |
| getOriginalUrl | 获取最初请求 Url |
| getTitle | 获取当前页面标题 |
| getProgress | 获取当前页面加载进度 |
| getFavicon | 获取当前页面图标 ( Bitmap ) |
| getHitTestResult | 获取长按事件类型 ( HitTestResult ) |
| getCertificate | 获取当前页面 SSL 证书 ( SslCertificate ) |
| pageDown | 将视图内容向下滚动一半页面大小 |
| pageUp | 将视图内容向上滚动一半页面大小 |
| scrollTo | 滚动到指定坐标 |
| scrollBy | 滚动指定偏移量 |
| flingScroll | 以给定的速度开始滑动 |
| zoomBy | 按指定比例缩放 |
| zoomIn | 放大 |
| zoomOut | 缩小 |
| setInitialScale | 设置初始缩放比例 |
| findAllAsync | 异步查找页面内所有匹配项 |
| findNext | 跳转到下一个 ( 上一个 ) 查找匹配项 |
| onPause | 暂停 WebView ( Activity onPause ) |
| onResume | 恢复 WebView ( Activity onResume ) |
| pauseTimers | 暂停所有 WebView 的布局、解析、JavaScript 定时器 |
| resumeTimers | 恢复所有 WebView 的布局、解析、JavaScript 定时器 |
| freeMemory | 释放内存 |
| destroy | 销毁处理 ( 避免 WebView 引起的内存泄漏 ) |
| handlerKeyDown | 处理按键 ( 是否回退 ) |
| setLayerTypeSoftware | 关闭 WebView 硬件加速功能 ( 解决 WebView 闪烁问题 ) |
| setLayerType | 设置 WebView 硬件加速类型 |
| setNetworkAvailable | 设置 WebView 是否处于可联网状态 |
| saveWebArchive | 保存当前页面为 Web 归档文件 |
| requestFocusNodeHref | 请求获取长按聚焦节点的超链接信息 |
| requestImageRef | 请求获取长按聚焦图片的地址信息 |
| documentHasImages | 查询当前页面是否包含图片资源 |
| createPrintDocumentAdapter | 创建打印文档适配器 ( PrintDocumentAdapter ) |
| setRendererPriorityPolicy | 设置渲染进程优先级策略 ( Android 8.0 起支持 ) |
| postWebMessage | 向网页投递一条 Web 消息 ( HTML5 postMessage ) |
| createWebMessageChannel | 创建一对 Web 消息端口 ( HTML5 MessageChannel ) |
| setWebContentsDebuggingEnabled | 设置是否开启 WebView 内容调试 ( Chrome inspect ) |
| getCurrentWebViewPackage | 获取当前 WebView 内核包信息 ( PackageInfo, Android 8.0 起支持 ) |
| clearClientCertPreferences | 清除客户端证书选择偏好 |
| isWebViewFeatureSupported | 判断 AndroidX WebKit 特性是否支持 |
| addDocumentStartJavaScript | 在文档开始加载时注入 JS ( 需 DOCUMENT_START_SCRIPT 特性 ) |
| addWebMessageListener | 添加 Web 消息监听 ( 安全 JSBridge, 需 WEB_MESSAGE_LISTENER 特性 ) |
| removeWebMessageListener | 移除 Web 消息监听 |
| getWebViewClientCompat | 获取处理各种通知和请求事件对象 ( 兼容版, 需 GET_WEB_VIEW_CLIENT 特性 ) |
| getWebChromeClientCompat | 获取辅助处理 Javascript 对话框、标题等对象 ( 兼容版, 需 GET_WEB_CHROME_CLIENT 特性 ) |
| getCurrentWebViewPackageCompat | 获取当前 WebView 内核包信息 ( 兼容版 ) |
| getWebViewRenderProcess | 获取 WebView 渲染进程 ( 需 GET_WEB_VIEW_RENDERER 特性 ) |
| setWebViewRenderProcessClient | 设置 WebView 渲染进程客户端 ( 需 WEB_VIEW_RENDERER_CLIENT_BASIC_USAGE 特性 ) |
| getWebViewRenderProcessClient | 获取 WebView 渲染进程客户端 |
| isMultiProcessEnabled | 是否启用多进程 ( 需 MULTI_PROCESS 特性 ) |
| getVariationsHeader | 获取 Variations Header ( 需 GET_VARIATIONS_HEADER 特性 ) |
| startSafeBrowsing | 启动安全浏览 ( 需 START_SAFE_BROWSING 特性 ) |
| setSafeBrowsingAllowlist | 设置安全浏览白名单 ( 需 SAFE_BROWSING_ALLOWLIST 特性 ) |
| getSafeBrowsingPrivacyPolicyUrl | 获取安全浏览隐私政策地址 ( 需 SAFE_BROWSING_PRIVACY_POLICY_URL 特性 ) |
| setProxyOverride | 设置 WebView 代理 ( 需 PROXY_OVERRIDE 特性 ) |
| clearProxyOverride | 清除 WebView 代理 ( 需 PROXY_OVERRIDE 特性 ) |
| setAudioMuted | 静音或取消静音 WebView ( 需 MUTE_AUDIO 特性 ) |
| isAudioMuted | WebView 是否静音 ( 需 MUTE_AUDIO 特性 ) |
| postVisualStateCallback | 投递可视状态回调 ( 内容可绘制时触发, 用于规避白屏, 需 VISUAL_STATE_CALLBACK 特性 ) |
| setWebViewProfile | 设置 WebView 使用的 Profile ( 多 Profile 数据隔离, 需 MULTI_PROFILE 特性 ) |
| getWebViewProfile | 获取 WebView 使用的 Profile ( 需 MULTI_PROFILE 特性 ) |
| setUserAgentMetadata | 设置 WebView 用户代理元数据 ( 生成 UA Client Hints, 需 USER_AGENT_METADATA 特性 ) |
| setWebViewMediaIntegrityApiStatus | 设置 WebView Media Integrity API 权限 ( 需 WEBVIEW_MEDIA_INTEGRITY_API_STATUS 特性 ) |
| getOrCreateWebProfile | 根据名称获取或创建 Profile ( 需 MULTI_PROFILE 特性 ) |
| getWebProfile | 根据名称获取 Profile ( 需 MULTI_PROFILE 特性 ) |
| deleteWebProfile | 根据名称删除 Profile ( 需 MULTI_PROFILE 特性 ) |
| getAllWebProfileNames | 获取全部 Profile 名称 ( 需 MULTI_PROFILE 特性 ) |
| setServiceWorkerClient | 设置 ServiceWorker 客户端 ( 需 SERVICE_WORKER_BASIC_USAGE 特性 ) |
| setServiceWorkerAllowContentAccess | 设置 ServiceWorker 是否允许访问 content:// 资源 ( 需 SERVICE_WORKER_CONTENT_ACCESS 特性 ) |
| setServiceWorkerAllowFileAccess | 设置 ServiceWorker 是否允许访问文件 ( 需 SERVICE_WORKER_FILE_ACCESS 特性 ) |
| setServiceWorkerBlockNetworkLoads | 设置 ServiceWorker 是否不从网络加载资源 ( 需 SERVICE_WORKER_BLOCK_NETWORK_LOADS 特性 ) |
| setServiceWorkerCacheMode | 设置 ServiceWorker 缓存模式 ( 需 SERVICE_WORKER_CACHE_MODE 特性 ) |
| isWebViewTracing | 是否正在进行性能跟踪 ( 需 TRACING_CONTROLLER_BASIC_USAGE 特性 ) |
| startWebViewTracing | 开始性能跟踪 ( 需 TRACING_CONTROLLER_BASIC_USAGE 特性 ) |
| stopWebViewTracing | 停止性能跟踪并输出结果 ( 需 TRACING_CONTROLLER_BASIC_USAGE 特性 ) |
| getBackForwardCacheSettings | 获取 BackForwardCache 实时配置对象 ( 实验性, 需 BACK_FORWARD_CACHE_SETTINGS_EXPERIMENTAL_V3 特性 ) |
| setDefaultTrafficStatsTag | 设置 WebView 默认流量统计 TAG ( 需 DEFAULT_TRAFFICSTATS_TAGGING 特性 ) |
| saveState | 保存 WebView 状态 ( 支持限制大小、是否含前进历史, 需 SAVE_STATE 特性 ) |
| addNavigationListener | 添加导航监听 ( 回调在主线程, 需 NAVIGATION_LISTENER 特性 ) |
| removeNavigationListener | 移除导航监听 ( 需 NAVIGATION_LISTENER 特性 ) |
| restoreState | 恢复 WebView 历史与状态 ( 与 saveState 配对 ) |
| setActive | 设置 WebView 激活 ( 可见 ) 状态 |
| getCoreType | 获取内核类型标识 |
| getCoreVersion | 获取内核版本 |
| isCoreReady | 内核是否就绪可用 |
| setCookie | 将 Cookie 设置到 WebView |
| getCookie | 获取指定 Url 的 Cookie |
| setAcceptCookie | 设置是否接受 Cookie |
| acceptCookie | 是否接受 Cookie |
| setAcceptThirdPartyCookies | 设置指定 WebView 是否接受第三方 Cookie |
| acceptThirdPartyCookies | 指定 WebView 是否接受第三方 Cookie |
| hasCookies | 是否存在 Cookie |
| removeCookie | 移除 Cookie ( Session、All ) |
| removeSessionCookie | 移除 Session Cookie |
| removeAllCookie | 移除所有的 Cookie |
| flushCookie | 同步 ( 刷新 ) Cookie 到本地存储 |
| getCookieInfo | 获取指定 Url 全部 Cookie 的完整属性 ( 含 Domain、Path、Expires、Secure 等, 需 GET_COOKIE_INFO 特性 ) |
| javaScriptEnabled | javaScriptEnabled |
| renderPriority | renderPriority |
| useWideViewPort | useWideViewPort |
| loadWithOverviewMode | loadWithOverviewMode |
| layoutAlgorithm | layoutAlgorithm |
| supportZoom | supportZoom |
| builtInZoomControls | builtInZoomControls |
| displayZoomControls | displayZoomControls |
| textZoom | textZoom |
| standardFontFamily | standardFontFamily |
| defaultFontSize | defaultFontSize |
| minimumFontSize | minimumFontSize |
| defaultFixedFontSize | defaultFixedFontSize |
| minimumLogicalFontSize | minimumLogicalFontSize |
| fixedFontFamily | fixedFontFamily |
| sansSerifFontFamily | sansSerifFontFamily |
| serifFontFamily | serifFontFamily |
| cursiveFontFamily | cursiveFontFamily |
| fantasyFontFamily | fantasyFontFamily |
| mixedContentMode | mixedContentMode |
| loadsImagesAutomatically | loadsImagesAutomatically |
| javaScriptCanOpenWindowsAutomatically | javaScriptCanOpenWindowsAutomatically |
| defaultTextEncodingName | defaultTextEncodingName |
| geolocationEnabled | geolocationEnabled |
| userAgentString | userAgentString |
| allowFileAccess | allowFileAccess |
| allowFileAccessFromFileURLs | allowFileAccessFromFileURLs |
| allowUniversalAccessFromFileURLs | allowUniversalAccessFromFileURLs |
| blockNetworkLoads | blockNetworkLoads |
| blockNetworkImage | blockNetworkImage |
| mediaPlaybackRequiresUserGesture | mediaPlaybackRequiresUserGesture |
| cacheMode | cacheMode |
| domStorageEnabled | domStorageEnabled |
| appCacheEnabled | appCacheEnabled |
| appCachePath | appCachePath |
| appCacheMaxSize | appCacheMaxSize |
| databaseEnabled | databaseEnabled |
| databasePath | databasePath |
| allowContentAccess | allowContentAccess |
| supportMultipleWindows | supportMultipleWindows |
| needInitialFocus | needInitialFocus |
| safeBrowsingEnabled | safeBrowsingEnabled |
| offscreenPreRaster | offscreenPreRaster |
| disabledActionModeMenuItems | disabledActionModeMenuItems |
| algorithmicDarkeningAllowed | algorithmicDarkeningAllowed |
| paymentRequestEnabled | paymentRequestEnabled |
| enterpriseAuthenticationAppLinkPolicyEnabled | enterpriseAuthenticationAppLinkPolicyEnabled |
| attributionRegistrationBehavior | attributionRegistrationBehavior |
| backForwardCacheEnabled | backForwardCacheEnabled |
| speculativeLoadingStatus | speculativeLoadingStatus |
| hyperlinkContextMenuItems | hyperlinkContextMenuItems |
| hasEnrolledInstrumentEnabled | hasEnrolledInstrumentEnabled |
| cookiesIncludedInShouldInterceptRequest | cookiesIncludedInShouldInterceptRequest |
| webAuthenticationSupport | webAuthenticationSupport |
| forceUserScalable | forceUserScalable |
| view | view |
| config | config |
| webViewClient | webViewClient |
| webChromeClient | webChromeClient |
| downloadListener | downloadListener |
| findListener | findListener |
| javascriptInterfaces | javascriptInterfaces |
| onWebListener | onWebListener |
| onPageStarted | 页面开始加载 |
| onPageFinished | 页面加载完成 |
| onProgressChanged | 加载进度变化 |
| onReceivedTitle | 接收网页标题 |
| onReceivedError | 接收加载错误 |


## <span id="devfunction">**`dev.function`**</span>


* **执行方法类 ->** [DevFunction.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/function/DevFunction.java)

| 方法 | 注释 |
| :- | :- |
| operation | 获取 Operation |
| object | 设置 Object |
| tryCatch | 捕获异常处理 |
| thread | 后台线程执行 |
| threadPool | 后台线程池执行 |
| threadCatch | 后台线程执行 |
| threadPoolCatch | 后台线程池执行 |
| method | method |
| error | error |
| getObject | 获取 Object |
| setObject | 设置 Object |