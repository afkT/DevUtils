
## Gradle

```java
implementation 'com.afkt:DevAssist:1.1.0'
```

## 目录结构

```
- dev                                                 | 根目录
   - adapter                                          | 适配器相关
   - assist                                           | 快捷功能辅助类
   - base                                             | 实体类基类相关
      - data                                          | 数据操作
      - entry                                         | KeyValue 实体类
      - expand                                        | 实体类拓展
      - multiselect                                   | 多选编辑操作
      - number                                        | 数值操作
   - callback                                         | 接口回调相关
   - engine                                           | 兼容 Engine
      - http                                          | Http Engine
      - image                                         | Image Engine
         - listener                                   | 图片加载监听事件
      - json                                          | JSON Engine
      - log                                           | Log Engine
   - function                                         | 快捷方法执行相关
```


## 事项

- 部分 API 更新不及时或有遗漏等，`具体以对应的工具类为准`

- [检测代码规范、注释内容排版，API 文档生成](https://github.com/afkT/JavaDoc)

## API


- dev                                                 | 根目录
   - [adapter](#devadapter)                           | 适配器相关
   - [assist](#devassist)                             | 快捷功能辅助类
   - [base](#devbase)                                 | 实体类基类相关
      - [data](#devbasedata)                          | 数据操作
      - [entry](#devbaseentry)                        | KeyValue 实体类
      - [expand](#devbaseexpand)                      | 实体类拓展
      - [multiselect](#devbasemultiselect)            | 多选编辑操作
      - [number](#devbasenumber)                      | 数值操作
   - [callback](#devcallback)                         | 接口回调相关
   - [engine](#devengine)                             | 兼容 Engine
      - [http](#devenginehttp)                        | Http Engine
      - [image](#devengineimage)                      | Image Engine
         - [listener](#devengineimagelistener)        | 图片加载监听事件
      - [json](#devenginejson)                        | JSON Engine
      - [log](#devenginelog)                          | Log Engine
   - [function](#devfunction)                         | 快捷方法执行相关




## <span id="dev">**`dev`**</span>


* **开发辅助类 ->** [DevAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/DevAssist.java)

| 方法 | 注释 |
| :- | :- |
| getDevAssistVersionCode | 获取 DevAssist 版本号 |
| getDevAssistVersion | 获取 DevAssist 版本 |
| getDevJavaVersionCode | 获取 DevJava 版本号 |
| getDevJavaVersion | 获取 DevJava 版本 |


## <span id="devadapter">**`dev.adapter`**</span>


* **DataManager RecyclerView Adapter ->** [DevDataAdapter.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/adapter/DevDataAdapter.java)

| 方法 | 注释 |
| :- | :- |
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
| equalsFirstData | equalsFirstData |
| equalsLastData | equalsLastData |
| equalsPositionData | equalsPositionData |
| addData | addData |
| addDataAt | addDataAt |
| addDatas | addDatas |
| addDatasAt | addDatasAt |
| addDatasChecked | addDatasChecked |
| addDatasCheckedAt | addDatasCheckedAt |
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
| equalsFirstData | 判断 First Value 是否一致 |
| equalsLastData | 判断 Last Value 是否一致 |
| equalsPositionData | 判断 Position Value 是否一致 |
| addData | 添加数据 |
| addDataAt | 添加数据 |
| addDatas | 添加数据集 |
| addDatasAt | 添加数据集 |
| addDatasChecked | 添加数据集 ( 进行校验 ) |
| addDatasCheckedAt | 添加数据集 ( 进行校验 ) |
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
| equalsFirstData | 判断 First Value 是否一致 |
| equalsLastData | 判断 Last Value 是否一致 |
| equalsPositionData | 判断 Position Value 是否一致 |
| addData | 添加数据 |
| addDataAt | 添加数据 |
| addDatas | 添加数据集 |
| addDatasAt | 添加数据集 |
| addDatasChecked | 添加数据集 ( 进行校验 ) |
| addDatasCheckedAt | 添加数据集 ( 进行校验 ) |
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
| getTag | 获取标记 Tag |
| convertTag | 转换标记 Tag |
| setTag | 设置标记 Tag |
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
| equalsTag | 判断 Tag 是否一致 |
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


* **资源路径通用类 ->** [DevSource.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/DevSource.java)

| 方法 | 注释 |
| :- | :- |
| create | create |
| createWithPath | createWithPath |
| isUrl | isUrl |
| isUri | isUri |
| isBytes | isBytes |
| isResource | isResource |
| isFile | isFile |
| isSource | isSource |


* **变量基类 ( 方便判断处理 ) ->** [DevVariable.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/DevVariable.java)

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
| getVariableValuesToReverse | 获取变量数据 value list( 倒序 ) |
| getVariableKey | 通过 value 获取 key |
| getVariableKeys | 获取变量数据 key list |
| getVariableKeysToReverse | 获取变量数据 key list( 倒序 ) |


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
| equalsFirstData | 判断 First Value 是否一致 |
| equalsLastData | 判断 Last Value 是否一致 |
| equalsPositionData | 判断 Position Value 是否一致 |
| addData | 添加数据 |
| addDataAt | 添加数据 |
| addDatas | 添加数据集 |
| addDatasAt | 添加数据集 |
| addDatasChecked | 添加数据集 ( 进行校验 ) |
| addDatasCheckedAt | 添加数据集 ( 进行校验 ) |
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


## <span id="devbaseexpand">**`dev.base.expand`**</span>


* **请求状态类 ->** [RequestState.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/expand/RequestState.java)

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
| isRequestNormal | 判断是否默认状态 |
| isRequestNever | 判断是否未请求过 |
| isRequestIng | 判断是否请求中 |
| isRequestSuccess | 判断是否请求成功 |
| isRequestFail | 判断是否请求失败 |
| isRequestError | 判断是否请求异常 |
| setRequestNormal | 设置状态为默认状态 |
| setRequestNever | 设置状态为未请求过 |
| setRequestIng | 设置状态为请求中 |
| setRequestSuccess | 设置状态为请求成功 |
| setRequestFail | 设置状态为请求失败 |
| setRequestError | 设置状态为请求异常 |


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


## <span id="devenginehttp">**`dev.engine.http`**</span>


* **Http Engine ->** [DevHttpEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/http/DevHttpEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 HttpEngine |
| setEngine | 设置 HttpEngine |


* **Http Engine 接口 ->** [IHttpEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/http/IHttpEngine.java)

| 方法 | 注释 |
| :- | :- |
| newCall | 获取 Request Call Object |
| cancelAll | 取消请求 ( 全部 ) |
| cancelCall | 取消请求 |
| cancelUrl | 取消请求 |
| cancelTag | 取消请求 |
| getRequest | 获取 Request Object |
| getSentRequestAtMillis | 获取发送请求时间 |
| getReceivedResponseAtMillis | 获取请求响应时间 |
| isCanceled | 是否取消请求 |
| isExecuted | 是否执行过请求 |
| isEnd | 是否请求结束 |
| cancel | 取消请求 |
| start | 开始请求方法 ( 同步 ) |
| startAsync | 开始请求方法 ( 异步 ) |
| onStart | 开始请求 |
| onCancel | 请求取消 |
| onResponse | 请求响应 |
| onFailure | 请求失败 |


## <span id="devengineimage">**`dev.engine.image`**</span>


* **Image Engine ->** [DevImageEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/DevImageEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 ImageEngine |
| setEngine | 设置 ImageEngine |


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
| loadBitmap | loadBitmap |
| loadDrawable | loadDrawable |


* **图片加载事件 ->** [LoadListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/LoadListener.java)

| 方法 | 注释 |
| :- | :- |
| getTranscodeType | 获取转码类型 |
| onStart | 开始加载 |
| onResponse | 响应回调 |
| onFailure | 失败回调 |


## <span id="devengineimagelistener">**`dev.engine.image.listener`**</span>


* **Bitmap 加载事件 ->** [BitmapListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/listener/BitmapListener.java)

| 方法 | 注释 |
| :- | :- |
| getTranscodeType | getTranscodeType |


* **Drawable 加载事件 ->** [DrawableListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/listener/DrawableListener.java)

| 方法 | 注释 |
| :- | :- |
| getTranscodeType | getTranscodeType |


## <span id="devenginejson">**`dev.engine.json`**</span>


* **JSON Engine ->** [DevJSONEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/json/DevJSONEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 JSONEngine |
| setEngine | 设置 JSONEngine |


* **JSON Engine 接口 ->** [IJSONEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/json/IJSONEngine.java)

| 方法 | 注释 |
| :- | :- |
| toJson | 将对象转换为 JSON String |
| fromJson | 将 JSON String 映射为指定类型对象 |
| isJSON | 判断字符串是否 JSON 格式 |
| isJSONObject | 判断字符串是否 JSON Object 格式 |
| isJSONArray | 判断字符串是否 JSON Array 格式 |
| toJsonIndent | JSON String 缩进处理 |


## <span id="devenginelog">**`dev.engine.log`**</span>


* **Log Engine ->** [DevLogEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/log/DevLogEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 LogEngine |
| setEngine | 设置 LogEngine |


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


## <span id="devfunction">**`dev.function`**</span>


* **执行方法类 ->** [Function.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/function/Function.java)

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