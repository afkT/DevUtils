
## Gradle

```gradle
implementation 'io.github.afkt:DevAssist:1.3.5'
```

## 目录结构

```
- dev                   | 根目录
   - adapter            | 适配器相关
   - assist             | 快捷功能辅助类
   - base               | 实体类基类相关
      - data            | 数据操作
      - entry           | KeyValue 实体类
      - multiselect     | 多选编辑操作
      - number          | 数值操作
      - state           | 状态相关
   - callback           | 接口回调相关
   - engine             | 兼容 Engine
      - analytics       | Analytics Engine 数据统计 ( 埋点 )
      - barcode         | BarCode Engine 条形码、二维码处理
         - listener     | 条形码、二维码操作回调事件
      - cache           | Cache Engine 有效期键值对缓存
      - compress        | Image Compress Engine 图片压缩
         - listener     | 图片压缩回调事件
      - image           | Image Engine 图片加载、下载、转格式等
         - listener     | 图片加载监听事件
      - json            | JSON Engine 映射
      - keyvalue        | KeyValue Engine 键值对存储
      - log             | Log Engine 日志打印
      - media           | Media Selector Engine 多媒体资源选择
      - permission      | Permission Engine 权限申请
      - push            | Push Engine 推送平台处理
      - share           | Share Engine 分享平台处理
         - listener     | 分享回调事件
      - storage         | Storage Engine 外部、内部文件存储
         - listener     | Storage 存储结果事件
   - function           | 快捷方法执行相关
```


## 事项

- 部分 API 更新不及时或有遗漏等，`具体以对应的工具类为准`

- [检测代码规范、注释内容排版，API 文档生成](https://github.com/afkT/JavaDoc)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/CHANGELOG.md)

## API


- dev                                                     | 根目录
   - [adapter](#devadapter)                               | 适配器相关
   - [assist](#devassist)                                 | 快捷功能辅助类
   - [base](#devbase)                                     | 实体类基类相关
      - [data](#devbasedata)                              | 数据操作
      - [entry](#devbaseentry)                            | KeyValue 实体类
      - [multiselect](#devbasemultiselect)                | 多选编辑操作
      - [number](#devbasenumber)                          | 数值操作
      - [state](#devbasestate)                            | 状态相关
   - [callback](#devcallback)                             | 接口回调相关
   - [engine](#devengine)                                 | 兼容 Engine
      - [analytics](#devengineanalytics)                  | Analytics Engine 数据统计 ( 埋点 )
      - [barcode](#devenginebarcode)                      | BarCode Engine 条形码、二维码处理
         - [listener](#devenginebarcodelistener)          | 条形码、二维码操作回调事件
      - [cache](#devenginecache)                          | Cache Engine 有效期键值对缓存
      - [compress](#devenginecompress)                    | Image Compress Engine 图片压缩
         - [listener](#devenginecompresslistener)         | 图片压缩回调事件
      - [image](#devengineimage)                          | Image Engine 图片加载、下载、转格式等
         - [listener](#devengineimagelistener)            | 图片加载监听事件
      - [json](#devenginejson)                            | JSON Engine 映射
      - [keyvalue](#devenginekeyvalue)                    | KeyValue Engine 键值对存储
      - [log](#devenginelog)                              | Log Engine 日志打印
      - [media](#devenginemedia)                          | Media Selector Engine 多媒体资源选择
      - [permission](#devenginepermission)                | Permission Engine 权限申请
      - [push](#devenginepush)                            | Push Engine 推送平台处理
      - [share](#devengineshare)                          | Share Engine 分享平台处理
         - [listener](#devenginesharelistener)            | 分享回调事件
      - [storage](#devenginestorage)                      | Storage Engine 外部、内部文件存储
         - [listener](#devenginestoragelistener)          | Storage 存储结果事件
   - [function](#devfunction)                             | 快捷方法执行相关


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


* **EditText 搜索辅助类 ->** [EditTextSearchAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/EditTextSearchAssist.java)

| 方法 | 注释 |
| :- | :- |
| remove | 移除消息 |
| post | 发送消息 ( 功能由该方法实现 ) |
| setDelayMillis | 设置搜索延迟时间 |
| setCallback | 设置搜索回调接口 |
| bindEditText | 绑定 EditText 输入事件 |
| callback | 搜索回调 |


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
| callback | 回调方法 |
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
| pause | pause |
| resume | resume |
| preload | preload |
| clear | clear |
| clearDiskCache | clearDiskCache |
| clearMemoryCache | clearMemoryCache |
| clearAllCache | clearAllCache |
| lowMemory | lowMemory |
| display | display |
| loadImage | loadImage |
| loadImageThrows | loadImageThrows |
| loadBitmap | loadBitmap |
| loadBitmapThrows | loadBitmapThrows |
| loadDrawable | loadDrawable |
| loadDrawableThrows | loadDrawableThrows |
| convertImageFormat | convertImageFormat |


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
| isGranted | 判断是否授予了权限 |
| shouldShowRequestPermissionRationale | 获取拒绝权限询问勾选状态 |
| getDeniedPermissionStatus | 获取拒绝权限询问状态集合 |
| againRequest | 再次请求处理操作 |
| request | 请求权限 |
| onGranted | 授权通过权限回调 |
| onDenied | 授权未通过权限回调 |


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