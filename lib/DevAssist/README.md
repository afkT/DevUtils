
## Gradle

```java
implementation 'com.afkt:DevAssist:1.0.5'
```

## 目录结构

```
- dev                                                 | 根目录
   - assist                                           | 快捷功能辅助类
      - adapter                                       | Adapter 相关
      - multiselect                                   | 多选模块
         - edit                                       | 多选编辑方法
   - base                                             | 基类相关
   - callback                                         | 回调相关
      - click                                         | 点击回调
      - common                                        | 通用回调
      - result                                        | 操作结果回调
   - engine                                           | 兼容 Engine
      - http                                          | Http Engine
      - image                                         | Image Engine
      - json                                          | JSON Engine
      - log                                           | Log Engine
```


## 事项

- 部分 API 更新不及时或有遗漏等，`具体以对应的工具类为准`

- [检测代码规范、注释内容排版，API 文档生成](https://github.com/afkT/JavaDoc)

## API


- dev                                                 | 根目录
   - [assist](#devassist)                             | 快捷功能辅助类
      - [adapter](#devassistadapter)                  | Adapter 相关
      - [multiselect](#devassistmultiselect)          | 多选模块
         - [edit](#devassistmultiselectedit)          | 多选编辑方法
   - [base](#devbase)                                 | 基类相关
   - [callback](#devcallback)                         | 回调相关
      - [click](#devcallbackclick)                    | 点击回调
      - [common](#devcallbackcommon)                  | 通用回调
      - [result](#devcallbackresult)                  | 操作结果回调
   - [engine](#devengine)                             | 兼容 Engine
      - [http](#devenginehttp)                        | Http Engine
      - [image](#devengineimage)                      | Image Engine
      - [json](#devenginejson)                        | JSON Engine
      - [log](#devenginelog)                          | Log Engine




## <span id="dev">**`dev`**</span>


* **开发辅助类 ->** [DevAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/DevAssist.java)

| 方法 | 注释 |
| :- | :- |
| getDevAssistVersionCode | 获取 DevAssist 版本号 |
| getDevAssistVersion | 获取 DevAssist 版本 |
| getDevJavaVersionCode | 获取 DevJava 版本号 |
| getDevJavaVersion | 获取 DevJava 版本 |


## <span id="devassist">**`dev.assist`**</span>


* **解决 Adapter 多个 Item 存在 EditText 监听输入问题 ->** [EditTextWatcherAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/EditTextWatcherAssist.java)

| 方法 | 注释 |
| :- | :- |
| bindListener | 绑定事件 |


* **数量控制辅助类 ->** [NumberControlAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/NumberControlAssist.java)

| 方法 | 注释 |
| :- | :- |
| isMinNumber | 判断当前数量, 是否等于最小值 |
| isLessThanMinNumber | 判断数量, 是否小于最小值 |
| isGreaterThanMinNumber | 判断数量, 是否大于最小值 |
| isMaxNumber | 判断当前数量, 是否等于最大值 |
| isLessThanMaxNumber | 判断数量, 是否小于最大值 |
| isGreaterThanMaxNumber | 判断数量, 是否大于最大值 |
| getObject | 获取 Object |
| setObject | 设置 Object |
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


* **Page 页数辅助类 ->** [PageAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/PageAssist.java)

| 方法 | 注释 |
| :- | :- |
| initPageConfig | 初始化全局分页配置 |
| getPageNum | 获取当前请求页数 |
| setPageNum | 设置当前请求页数 |
| getPageSize | 获取每页请求条数 |
| setPageSize | 设置每页请求条数 |
| getTotalRow | 获取数据总条数 |
| setTotalRow | 设置数据总条数 |
| isLastPage | 判断是否最后一页 |
| setLastPage | 设置是否最后一页 |
| isFirstPage | 判断是否第一页 |
| isAllowNextPage | 判断是否允许请求下一页 |
| getNextPage | 获取下一页页数 |
| nextPage | 设置下一页页数 |
| isLessThanPageSize | 判断是否小于每页请求条数 |
| reset | 重置操作 |


* **请求状态辅助类 ->** [RequestStateAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/RequestStateAssist.java)

| 方法 | 注释 |
| :- | :- |
| getRequestType | 获取请求类型 |
| setRequestType | 设置请求类型 |
| equalsRequestType | 判断请求类型是否一致 |
| getRequestHashCode | 获取请求 HashCode |
| getRequestHashCodeRandom | 获取请求 HashCode ( 随机生成并赋值 ) |
| equalsHashCode | 判断 HashCode 是否一致 |
| getRequestState | 获取请求状态 |
| setRequestState | 设置请求状态 |
| isRequestNormal | 判断是否默认状态 ( 暂未进行操作 ) |
| isRequestNever | 判断是否未请求过 |
| isRequestIng | 判断是否请求中 |
| isRequestSuccess | 判断是否请求成功 |
| isRequestFail | 判断是否请求失败 |
| isRequestError | 判断是否请求异常 |
| setRequestNormal | 设置状态为默认状态 ( 暂未进行操作 ) |
| setRequestNever | 设置状态为未请求过 |
| setRequestIng | 设置状态为请求中 |
| setRequestSuccess | 设置状态为请求成功 |
| setRequestFail | 设置状态为请求失败 |
| setRequestError | 设置状态为请求异常 |


* **变量辅助类 ->** [VariableAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/VariableAssist.java)

| 方法 | 注释 |
| :- | :- |
| getBooleanVariable | 获取 Boolean 变量存储对象 |
| getObjectVariable | 获取 Object 变量存储对象 |


## <span id="devassistadapter">**`dev.assist.adapter`**</span>


* **Adapter 数据辅助类 ->** [AdapterDataAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/adapter/AdapterDataAssist.java)

| 方法 | 注释 |
| :- | :- |
| setAdapterNotify | 设置 Adapter Notify |
| getDataList | 获取 List Data |
| getDataArrayList | 获取 ArrayList Data |
| getDataCount | 获取 List Count |
| getDataItem | 获取 List Position Item |
| getDataItemPosition | 获取 Value Position |
| getDataFirstItem | 获取 First Item Data |
| getDataLastItem | 获取 Last Item Data |
| isFirstItem | 判断是否 First Item Data |
| isLastItem | 判断是否 Last Item Data |
| clearDataList | 清空全部数据 |
| addData | 添加数据 |
| addAllData | 添加数据 |
| removeData | 移除数据 |
| setDataList | 设置 List Data |
| adapterNotifyDataSetChanged | 通知 Adapter 数据改变 |


* **Adapter 数据操作接口 ->** [IAdapterData.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/adapter/IAdapterData.java)

| 方法 | 注释 |
| :- | :- |
| getDataList | 获取 List Data |
| getDataArrayList | 获取 ArrayList Data |
| getDataCount | 获取 List Count |
| getDataItem | 获取 List Position Item |
| getDataItemPosition | 获取 Value Position |
| getDataFirstItem | 获取 First Item Data |
| getDataLastItem | 获取 Last Item Data |
| isFirstItem | 判断是否 First Item Data |
| isLastItem | 判断是否 Last Item Data |
| clearDataList | 清空全部数据 |
| addData | 添加数据 |
| addAllData | 添加数据 |
| removeData | 移除数据 |
| setDataList | 设置 List Data |


* **Adapter 通知接口 ->** [IAdapterNotify.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/adapter/IAdapterNotify.java)

| 方法 | 注释 |
| :- | :- |
| adapterNotifyDataSetChanged | 通知 Adapter 数据改变 |


## <span id="devassistmultiselect">**`dev.assist.multiselect`**</span>


* **多选操作接口 ( 基类 ) ->** [IBaseMultiSelect.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/multiselect/IBaseMultiSelect.java)

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


* **多选操作接口 ( List ) ->** [IMultiSelectToList.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/multiselect/IMultiSelectToList.java)

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


* **多选操作接口 ( Map ) ->** [IMultiSelectToMap.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/multiselect/IMultiSelectToMap.java)

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


* **List 多选辅助类 ->** [MultiSelectListAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/multiselect/MultiSelectListAssist.java)

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


* **Map 多选辅助类 ->** [MultiSelectMapAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/multiselect/MultiSelectMapAssist.java)

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


## <span id="devassistmultiselectedit">**`dev.assist.multiselect.edit`**</span>


* **多选编辑接口 ->** [IMultiSelectEdit.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/assist/multiselect/edit/IMultiSelectEdit.java)

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


## <span id="devbase">**`dev.base`**</span>


* **Key-Value Entry ->** [DevBaseEntry.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/DevBaseEntry.java)

| 方法 | 注释 |
| :- | :- |
| getEntryKey | 获取 Entry Key |
| setEntryKey | 设置 Entry Key |
| getEntryValue | 获取 Entry Value |
| setEntryValue | 设置 Entry Value |
| isCorrect | 校验数据正确性 |


* **Event 基类 ->** [DevBaseEvent.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/DevBaseEvent.java)

| 方法 | 注释 |
| :- | :- |
| getCode | 获取 Code |
| setCode | 设置 Code |
| getCodeStr | 获取 Code String |
| setCodeStr | 设置 Code String |
| getValue | 获取 Value |
| setValue | 设置 Value |
| getObject | 获取 Object |
| setObject | 设置 Object |
| equalsCode | 判断 Code 是否一致 |
| equalsValue | 判断 Value 是否一致 |
| equalsObject | 判断 Object 是否一致 |


* **Model 基类 ->** [DevBaseModel.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/DevBaseModel.java)

| 方法 | 注释 |
| :- | :- |
| getUuid | 获取 UUID |
| getModelId | 获取 Model id |
| setModelId | 设置 Model id |
| isCorrect | 校验数据正确性 |


* **变量基类 ( 方便判断处理 ) ->** [DevBaseVariable.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/base/DevBaseVariable.java)

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


## <span id="devcallback">**`dev.callback`**</span>


* **抽象回调 ( 基类 ) ->** [AbstractCallBack.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/callback/AbstractCallBack.java)

| 方法 | 注释 |
| :- | :- |
| getUUID | 获取 UUID |
| getTag | 获取标记 Tag |
| setTag | 设置标记 Tag |
| getValue | 获取 Value |
| setValue | 设置 Value |
| getObject | 获取 Object |
| setObject | 设置 Object |


* **通用抽象回调 ( 基类 ) ->** [AbstractCommonCallBack.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/callback/AbstractCommonCallBack.java)

| 方法 | 注释 |
| :- | :- |
| onResult | 结果回调通知 |
| onError | 异常回调通知 |
| onFailure | 失败回调通知 |


## <span id="devcallbackclick">**`dev.callback.click`**</span>


* **通用 Click 回调 ->** [DevClickCallBack.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/callback/click/DevClickCallBack.java)

| 方法 | 注释 |
| :- | :- |
| onClick | 点击回调 |
| onLongClick | 长按回调 |


* **通用 Dialog 回调 ->** [DevDialogCallBack.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/callback/click/DevDialogCallBack.java)

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


* **通用 Item Click 回调 ->** [DevItemClickCallBack.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/callback/click/DevItemClickCallBack.java)

| 方法 | 注释 |
| :- | :- |
| onItemClick | 点击 Item 回调 |
| onItemLongClick | 长按 Item 回调 |


## <span id="devcallbackcommon">**`dev.callback.common`**</span>


* **Dev 回调基类 ->** [DevCallBack.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/callback/common/DevCallBack.java)

| 方法 | 注释 |
| :- | :- |
| callback | 回调方法 |


* **过滤处理回调类 ->** [DevFilterCallback.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/callback/common/DevFilterCallback.java)

| 方法 | 注释 |
| :- | :- |
| filter | 过滤处理 |
| isFilter | 判断是否过滤 |
| compare | 对比过滤处理 |


## <span id="devcallbackresult">**`dev.callback.result`**</span>


* **通用结果回调类 ( 针对 DevResultCallback 进行扩展 ) ->** [DevExResultCallback.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/callback/result/DevExResultCallback.java)

| 方法 | 注释 |
| :- | :- |
| onResult | 结果回调通知 |
| onError | 异常回调通知 |
| onFailure | 失败回调通知 |
| getExpandResult | 获取实体类 |
| setExpandResult | 设置实体类 |


* **通用结果回调类 ->** [DevResultCallback.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/callback/result/DevResultCallback.java)

| 方法 | 注释 |
| :- | :- |
| onResult | 结果回调通知 |


## <span id="devengine">**`dev.engine`**</span>


## <span id="devenginehttp">**`dev.engine.http`**</span>


* **Http Engine ->** [DevHttpEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/http/DevHttpEngine.java)

| 方法 | 注释 |
| :- | :- |
| initEngine | 初始化 Engine |
| newCall | 获取 Request Call Object |
| cancelAll | 取消请求 ( 全部 ) |
| cancelCall | 取消请求 |
| cancelUrl | 取消请求 |
| cancelTag | 取消请求 |


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
| initEngine | 初始化 Engine |
| displayImage | 图片显示 |
| loadImage | 图片加载 |
| clearDiskCache | 清除磁盘缓存 |
| clearMemoryCache | 清除内存缓存 |


* **Image Engine 接口 ->** [IImageEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/IImageEngine.java)

| 方法 | 注释 |
| :- | :- |
| displayImage | 图片显示 |
| loadImage | 图片加载 |
| clearDiskCache | 清除磁盘缓存 |
| clearMemoryCache | 清除内存缓存 |
| getTranscodeType | 获取转码类型 |
| onResponse | 响应回调 |
| onFailure | 失败回调 |


## <span id="devenginejson">**`dev.engine.json`**</span>


* **JSON Engine ->** [DevJSONEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/json/DevJSONEngine.java)

| 方法 | 注释 |
| :- | :- |
| initEngine | 初始化 Engine |
| toJson | 将对象转换为 JSON String |
| fromJson | 将 JSON String 映射为指定类型对象 |
| isJSON | 判断字符串是否 JSON 格式 |
| isJSONObject | 判断字符串是否 JSON Object 格式 |
| isJSONArray | 判断字符串是否 JSON Array 格式 |
| toJsonIndent | JSON String 缩进处理 |


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
| initEngine | 初始化 Engine |
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