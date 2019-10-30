

## 目录结构

```
- dev                                                 | 根目录
   - other                                            | 第三方库封装工具类
   - receiver                                         | BroadcastReceiver 监听相关
   - service                                          | Service 相关
   - temp                                             | 临时快捷调用工具类
```


## Use

> 直接 copy 所需要的类到项目中使用


## API


- dev                                                 | 根目录
   - [other](#devother)                               | 第三方库封装工具类
   - [receiver](#devreceiver)                         | BroadcastReceiver 监听相关
   - [service](#devservice)                           | Service 相关
   - [temp](#devtemp)                                 | 临时快捷调用工具类




## <span id="dev">**`dev`**</span>


* **Dev 工具类链式调用 Helper 类 ->** [DevHelper.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/DevHelper.java)

| 方法 | 注释 |
| :- | :- |
| get | 获取单例 DevHelper |
| viewHelper | 获取 ViewHelper |
| devHelper | 获取 DevHelper |
| postRunnable | 在主线程 Handler 中执行任务 |
| removeRunnable | 在主线程 Handler 中清除任务 |
| startTimer | 运行定时器 |
| closeTimer | 关闭定时器 |
| recycle | Bitmap 通知回收 |
| saveBitmapToSDCardJPEG | 保存图片到 SDCard - JPEG |
| saveBitmapToSDCardPNG | 保存图片到 SDCard - PNG |
| saveBitmapToSDCardWEBP | 保存图片到 SDCard - WEBP |
| saveBitmapToSDCard | 保存图片到 SDCard |
| addTextChangedListener | 添加输入监听事件 |
| removeTextChangedListener | 移除输入监听事件 |
| setKeyListener | 设置 KeyListener |
| record | 日志记录 |
| cleanInternalCache | 清除内部缓存 - path /data/data/package/cache |
| cleanInternalFiles | 清除内部文件 - path /data/data/package/files |
| cleanInternalDbs | 清除内部数据库 - path /data/data/package/databases |
| cleanInternalDbByName | 根据名称清除数据库 - path /data/data/package/databases/dbName |
| cleanInternalSp | 清除内部 SP - path /data/data/package/shared_prefs |
| cleanExternalCache | 清除外部缓存 - path /storage/emulated/0/android/data/package/cache |
| cleanCustomDir | 清除自定义路径下的文件, 使用需小心请不要误删, 而且只支持目录下的文件删除 |
| cleanApplicationData | 清除本应用所有的数据 |
| copyText | 复制文本到剪贴板 |
| copyUri | 复制 URI 到剪贴板 |
| copyIntent | 复制意图到剪贴板 |
| notifyMediaStore | 通知刷新本地资源 |
| insertImageIntoMediaStore | 添加图片到系统相册 ( 包含原图、相册图, 会存在两张 ) - 想要一张, 直接调用 notifyMediaStore() |
| insertVideoIntoMediaStore | 添加视频到系统相册 |
| insertIntoMediaStore | 保存到系统相册 |
| showDialog | 显示 Dialog |
| closeDialog | 关闭 Dialog |
| closeDialogs | 关闭多个 Dialog |
| closePopupWindow | 关闭 PopupWindow |
| closePopupWindows | 关闭多个 PopupWindow |
| autoCloseDialog | 自动关闭 dialog |
| autoClosePopupWindow | 自动关闭 PopupWindow |
| openKeyboard | 打开软键盘 |
| closeKeyboard | 关闭软键盘 |
| closeKeyBoardSpecial | 关闭软键盘 - 特殊处理 |
| judgeView | 设置某个 View 内所有非 EditText 的子 View OnTouchListener 事件 |
| registerSoftInputChangedListener | 注册软键盘改变监听 |
| registerSoftInputChangedListener2 | 注册软键盘改变监听 |
| applyLanguage | 修改系统语言 (APP 多语言, 单独改变 APP 语言 ) |
| setOnClicks | 设置点击事件 |
| setOnLongClicks | 设置长按事件 |
| addTouchArea | 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域 |
| cancelAllNotification | 移除通知 - 移除所有通知 ( 只是针对当前 Context 下的 Notification) |
| cancelNotification | 移除通知 - 移除标记为 id 的通知 ( 只是针对当前 Context 下的所有 Notification) |
| notifyNotification | 进行通知 |
| saveAssetsFormFile | 获取 Assets 资源文件数据并保存到本地 |
| saveRawFormFile | 获取 Raw 资源文件数据并保存到本地 |
| setWindowSecure | 设置禁止截屏 |
| setFullScreen | 设置屏幕为全屏 |
| setLandscape | 设置屏幕为横屏 |
| setPortrait | 设置屏幕为竖屏 |
| toggleScreenOrientation | 切换屏幕方向 |
| forceGetViewSize | 在 onCreate 中获取视图的尺寸 - 需回调 onGetSizeListener 接口, 在 onGetSize 中获取 View 宽高 |
| vibrate | 震动 |
| cancel | 取消震动 |
| closeIO | 关闭 IO |
| closeIOQuietly | 安静关闭 IO |
| getNetTime | 获取网络时间 - 默认使用百度链接 |
| waitForEndAsyn | 设置等待一段时间后, 通知方法 ( 异步 ) |
| waitForEnd | 设置等待一段时间后, 通知方法 ( 同步 ) |


* **View 链式调用快捷设置 Helper 类 ->** [ViewHelper.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/ViewHelper.java)

| 方法 | 注释 |
| :- | :- |
| get | 获取单例 ViewHelper |
| viewHelper | 获取 ViewHelper |
| devHelper | 获取 DevHelper |
| postRunnable | 在主线程 Handler 中执行任务 |
| removeRunnable | 在主线程 Handler 中清除任务 |
| setHint | 设置 Hint 文本 |
| setText | 设置文本 |
| setTexts | 设置多个 TextView 文本 |
| setHtmlText | 设置 Html 内容 |
| setHtmlTexts | 设置多个 TextView Html 内容 |
| setHintTextColor | 设置 Hint 字体颜色 |
| setHintTextColors | 设置多个 TextView Hint 字体颜色 |
| setTextColor | 设置字体颜色 |
| setTextColors | 设置多个 TextView 字体颜色 |
| setTypeface | 设置字体 |
| setTextSizeByPx | 设置字体大小 - px 像素 |
| setTextSizeBySp | 设置字体大小 - sp 缩放像素 |
| setTextSizeByDp | 设置字体大小 - dp 与设备无关的像素 |
| setTextSizeByIn | 设置字体大小 - inches 英寸 |
| setTextSize | 设置字体大小 |
| setTextSizes | 设置多个 TextView 字体大小 |
| clearFlags | 清空 flags |
| setBold | 设置 TextView 是否加粗 |
| setUnderlineText | 设置下划线 |
| setStrikeThruText | 设置中划线 |
| setLetterSpacing | 设置文字水平间距 |
| setLineSpacing | 设置文字行间距 ( 行高 ) |
| setLineSpacingAndMultiplier | 设置文字行间距 ( 行高 )、行间距倍数 |
| setTextScaleX | 设置字体水平方向的缩放 |
| setIncludeFontPadding | 设置是否保留字体留白间隙区域 |
| setInputType | 设置输入类型 |
| setImeOptions | 设置软键盘右下角按钮类型 |
| setLines | 设置行数 |
| setMaxLines | 设置最大行数 |
| setMinLines | 设置最小行数 |
| setMaxEms | 设置最大字符宽度限制 |
| setMinEms | 设置最小字符宽度限制 |
| setEms | 设置指定字符宽度 |
| setEllipsize | 设置 Ellipsize 效果 |
| setAutoLinkMask | 设置自动识别文本链接 |
| setAllCaps | 设置文本全为大写 |
| setTextGravity | 设置 Text Gravity |
| insert | 追加内容 ( 当前光标位置追加 ) |
| setMaxLength | 设置长度限制 |
| setMaxLengthAndText | 设置长度限制, 并且设置内容 |
| setCursorVisible | 设置是否显示光标 |
| setSelectionToTop | 设置光标在第一位 |
| setSelectionToBottom | 设置光标在最后一位 |
| setSelection | 设置光标位置 |
| addTextChangedListener | 添加输入监听事件 |
| removeTextChangedListener | 移除输入监听事件 |
| setKeyListener | 设置 KeyListener |
| setAdjustViewBounds | 设置 ImageView 是否保持宽高比 |
| setMaxHeight | 设置 ImageView 最大高度 |
| setMaxWidth | 设置 ImageView 最大宽度 |
| setBackground | 设置背景图片 |
| setBackgroundColor | 设置背景颜色 |
| setBackgroundResource | 设置背景资源 |
| setBackgroundTintList | 设置背景着色颜色 |
| setBackgroundTintMode | 设置背景着色模式 |
| setForeground | 设置前景图片 |
| setForegroundGravity | 设置前景重心 |
| setForegroundTintList | 设置前景着色颜色 |
| setForegroundTintMode | 设置前景着色模式 |
| setImageBitmap | 设置 ImageView Bitmap |
| setImageDrawable | 设置 ImageView Drawable |
| setImageResource | 设置 ImageView 资源 |
| setImageMatrix | 设置 ImageView Matrix |
| setImageTintList | 设置 ImageView 着色颜色 |
| setImageTintMode | 设置 ImageView 着色模式 |
| setScaleType | 设置 ImageView 缩放类型 |
| setColorFilter | ImageView 着色处理 |
| setBackgroundResources | 设置 View 图片资源 |
| setImageResources | 设置 View 图片资源 |
| setImageBitmaps | 设置 View Bitmap |
| setImageDrawables | 设置 View Drawable |
| setScaleTypes | 设置 View 缩放模式 |
| setWidthHeight | 设置 View 宽度、高度 |
| setWidth | 设置 View 宽度 |
| setHeight | 设置 View 高度 |
| setMinimumHeight | 设置 View 最小高度 |
| setMinimumWidth | 设置 View 最小宽度 |
| setAlpha | 设置 View 透明度 |
| setTag | 设置 View Tag |
| setScrollContainer | 设置 View 滚动效应 |
| setNextFocusForwardId | 设置下一个获取焦点的 View id |
| setNextFocusDownId | 设置向下移动焦点时, 下一个获取焦点的 View id |
| setNextFocusLeftId | 设置向左移动焦点时, 下一个获取焦点的 View id |
| setNextFocusRightId | 设置向右移动焦点时, 下一个获取焦点的 View id |
| setNextFocusUpId | 设置向上移动焦点时, 下一个获取焦点的 View id |
| setRotation | 设置 View 旋转度数 |
| setRotationX | 设置 View 水平旋转度数 |
| setRotationY | 设置 View 竖直旋转度数 |
| setScaleX | 设置 View 水平方向缩放比例 |
| setScaleY | 设置 View 竖直方向缩放比例 |
| setTextAlignment | 设置文本的显示方式 |
| setTextDirection | 设置文本的显示方向 |
| setPivotX | 设置水平方向偏转量 |
| setPivotY | 设置竖直方向偏转量 |
| setTranslationX | 设置水平方向的移动距离 |
| setTranslationY | 设置竖直方向的移动距离 |
| setLayerType | 设置 View 硬件加速类型 |
| setLayoutParams | 设置 View LayoutParams |
| setFocusable | 设置 View 是否可以获取焦点 |
| setSelected | 设置 View 是否选中 |
| setEnabled | 设置 View 是否启用 |
| setClickable | 设置 View 是否可以点击 |
| setLongClickable | 设置 View 是否可以长按 |
| setVisibility | 设置 View 显示的状态 |
| setVisibilitys | 设置 View 显示的状态 |
| toggleVisibilitys | 切换 View 显示的状态 |
| reverseVisibilitys | 反转 View 显示的状态 |
| removeSelfFromParent | 把自身从父 View 中移除 |
| setLayoutGravity | 设置 View Layout Gravity |
| setMarginLeft | 设置 View Left Margin |
| setMarginTop | 设置 View Top Margin |
| setMarginRight | 设置 View Right Margin |
| setMarginBottom | 设置 View Bottom Margin |
| setMargin | 设置 Margin 边距 |
| setPaddingLeft | 设置 View Left Padding |
| setPaddingTop | 设置 View Top Padding |
| setPaddingRight | 设置 View Right Padding |
| setPaddingBottom | 设置 View Bottom Padding |
| setPadding | 设置 Padding 边距 |
| setCompoundDrawablesByLeft | 设置 Left CompoundDrawables |
| setCompoundDrawablesByTop | 设置 Top CompoundDrawables |
| setCompoundDrawablesByRight | 设置 Right CompoundDrawables |
| setCompoundDrawablesByBottom | 设置 Bottom CompoundDrawables |
| setCompoundDrawables | 设置 CompoundDrawables |
| setCompoundDrawablesWithIntrinsicBoundsByLeft | 设置 Left CompoundDrawables - 按照原有比例大小显示图片 |
| setCompoundDrawablesWithIntrinsicBoundsByTop | 设置 Top CompoundDrawables - 按照原有比例大小显示图片 |
| setCompoundDrawablesWithIntrinsicBoundsByRight | 设置 Right CompoundDrawables - 按照原有比例大小显示图片 |
| setCompoundDrawablesWithIntrinsicBoundsByBottom | 设置 Bottom CompoundDrawables - 按照原有比例大小显示图片 |
| setCompoundDrawablesWithIntrinsicBounds | 设置 CompoundDrawables - 按照原有比例大小显示图片 |
| addRule | 设置 RelativeLayout View 布局规则 |
| removeRule | 移除 RelativeLayout View 布局规则 |
| addRules | 设置多个 RelativeLayout View 布局规则 |
| removeRules | 移除多个 RelativeLayout View 布局规则 |
| setAnimation | 设置动画 |
| clearAnimation | 清空动画 |
| startAnimation | 启动动画 |
| setOnClicks | 设置点击事件 |
| setOnLongClicks | 设置长按事件 |
| addTouchArea | 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域 |


## <span id="devother">**`dev.other`**</span>


* **EventBus 工具类 ->** [EventBusUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/EventBusUtils.java)

| 方法 | 注释 |
| :- | :- |
| register | 注册 EventBus |
| unregister | 解绑 EventBus |
| post | 发送事件消息 |
| cancelEventDelivery | 取消事件传送 |
| postSticky | 发送粘性事件消息 |
| removeStickyEvent | 移除指定的粘性订阅事件 |
| removeAllStickyEvents | 移除所有的粘性订阅事件 |


* **Glide 图形处理工具类 ->** [GlideTransformUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/GlideTransformUtils.java)

| 方法 | 注释 |
| :- | :- |
| transform | transform |
| updateDiskCacheKey | updateDiskCacheKey |
| blurBitmap | 模糊图片处理 |


* **Glide 工具类 ->** [GlideUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/GlideUtils.java)

| 方法 | 注释 |
| :- | :- |
| with | with |
| init | 初始化方法 |
| cloneImageOptions | 克隆图片加载配置 |
| defaultOptions | 获取默认加载配置 |
| emptyOptions | 获取空白加载配置 |
| skipCacheOptions | 获取跳过缓存 ( 每次都从服务端获取最新 ) 加载配置 |
| getLoadResOptions | 获取自定义图片加载配置 |
| transformationOptions | 获取图片处理效果加载配置 |
| clearDiskCache | 清除磁盘缓存 |
| clearMemoryCache | 清除内存缓存 |
| onLowMemory | 低内存通知 |
| getDiskCache | 获取 SDCard 缓存空间 |
| preload | 预加载图片 |
| displayImage | 图片显示 |
| displayImageToGif | 图片显示 |
| loadImageBitmap | 图片加载 |
| loadImageDrawable | 图片加载 |
| loadImageFile | 图片加载 |
| loadImageGif | 图片加载 |
| cancelDisplayTask | 取消图片显示任务 |
| destroy | 销毁操作 |
| pause | 暂停图片加载 |
| resume | 恢复图片加载 |
| stop | 停止图片加载 |
| start | 开始图片加载 |


* **Gson 工具类 ->** [GsonUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/GsonUtils.java)

| 方法 | 注释 |
| :- | :- |
| toJson | 将对象转换为 JSON String |
| fromJson | 将 JSON String 映射为指定类型对象 |
| isJSON | 判断字符串是否 JSON 格式 |
| toJsonIndent | JSON String 缩进处理 |
| createGson | 创建 GsonBuilder |
| createGsonExcludeFields | 创建过滤指定修饰符字段 GsonBuilder |
| getArrayType | 获取 Array Type |
| getListType | 获取 List Type |
| getSetType | 获取 Set Type |
| getMapType | 获取 Map Type |
| getType | 获取 Type |


* **ImageLoader 工具类 ->** [ImageLoaderUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/ImageLoaderUtils.java)

| 方法 | 注释 |
| :- | :- |
| init | 初始化 ImageLoader 加载配置 |
| defaultOptions | 获取 DisplayImageOptions 图片加载配置 |
| getDefaultImageOptions | 获取图片默认加载配置 |
| getNoCacheImageOptions | 获取不使用缓存的图片加载配置 |
| getCacheImageOptions | 获取 ImageLoader 图片缓存加载配置 |
| cloneImageOptions | 克隆图片加载配置 |
| getFadeInBitmapDisplayer | 获取图片渐变动画加载配置 |
| getRoundedBitmapDisplayer | 获取圆角图片加载配置 |
| getBitmapDisplayerOptions | 获取图片效果加载配置 |
| displayImage | 图片显示 |
| loadImage | 图片加载 |
| loadImageSync | 图片同步加载 |
| clearDiskCache | 清除磁盘缓存 |
| clearMemoryCache | 清除内存缓存 |
| getDiskCache | 获取 SDCard 缓存空间 |
| getMemoryCache | 获取 Memory 缓存空间 |
| handleSlowNetwork | 设置是否处理弱网情况 |
| denyNetworkDownloads | 设置是否禁止网络下载 |
| cancelDisplayTask | 取消图片显示任务 |
| getLoadingUriForView | 通过 ImageView 获取图片加载地址 |
| setDefaultLoadingListener | 设置全局加载监听事件 |
| destroy | 销毁操作 |
| pause | 暂停图片加载 |
| resume | 恢复图片加载 |
| stop | 停止图片加载 |


## <span id="devreceiver">**`dev.receiver`**</span>


* **网络监听广播 ->** [NetWorkReceiver.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/receiver/NetWorkReceiver.java)

| 方法 | 注释 |
| :- | :- |
| onReceive | onReceive |
| isConnectNetWork | 是否连接网络 |
| getConnectType | 获取连接的网络类型 |
| registerReceiver | 注册网络广播监听 |
| unregisterReceiver | 取消注册网络广播监听 |
| setNetListener | 设置监听通知事件 |
| onNetworkState | 网络连接状态改变时通知 |


* **手机监听广播 ->** [PhoneReceiver.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/receiver/PhoneReceiver.java)

| 方法 | 注释 |
| :- | :- |
| onReceive | onReceive |
| registerReceiver | 注册电话监听广播 |
| unregisterReceiver | 取消注册电话监听广播 |
| setPhoneListener | 设置电话状态监听事件 |
| onPhoneStateChanged | 电话状态改变通知 |


* **屏幕监听广播 ( 锁屏 / 解锁 / 亮屏 ) ->** [ScreenReceiver.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/receiver/ScreenReceiver.java)

| 方法 | 注释 |
| :- | :- |
| onReceive | onReceive |
| registerReceiver | 注册屏幕监听广播 |
| unregisterReceiver | 取消注册屏幕监听广播 |
| setScreenListener | 设置屏幕监听事件 |
| screenOn | 用户打开屏幕 ( 屏幕变亮 ) |
| screenOff | 锁屏触发 |
| userPresent | 用户解锁触发 |


* **短信监听广播 ->** [SmsReceiver.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/receiver/SmsReceiver.java)

| 方法 | 注释 |
| :- | :- |
| onReceive | onReceive |
| getMessageData | 获取消息数据 |
| registerReceiver | 注册短信监听广播 |
| unregisterReceiver | 取消注册短信监听广播 |
| setSmsListener | 设置短信监听事件 |
| onMessage | 最终触发通知 ( 超过长度的短信消息, 最终合并成一条内容体 ) |


* **时间监听广播 ->** [TimeReceiver.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/receiver/TimeReceiver.java)

| 方法 | 注释 |
| :- | :- |
| onReceive | onReceive |
| registerReceiver | 注册时间监听广播 |
| unregisterReceiver | 取消注册时间监听广播 |
| setTimeListener | 设置时间监听事件 |
| onTimeZoneChanged | 时区改变 |
| onTimeChanged | 设置时间 |
| onTimeTick | 每分钟调用 |


* **Wifi 监听广播 ->** [WifiReceiver.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/receiver/WifiReceiver.java)

| 方法 | 注释 |
| :- | :- |
| onReceive | onReceive |
| registerReceiver | 注册 Wifi 监听广播 |
| unregisterReceiver | 取消注册 Wifi 监听广播 |
| setWifiListener | 设置 Wifi 监听事件 |
| onIntoTrigger | 触发回调通知 ( 每次进入都通知 ) |
| onTrigger | 触发回调通知 |
| onWifiSwitch | Wifi 开关状态 |


## <span id="devservice">**`dev.service`**</span>


* **无障碍功能监听服务 ->** [AccessibilityListenerService.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/service/AccessibilityListenerService.java)

| 方法 | 注释 |
| :- | :- |
| onAccessibilityEvent | 通过这个函数可以接收系统发送来的 AccessibilityEvent |
| onInterrupt | 系统想要中断 AccessibilityService 返给的响应时会调用 |
| onServiceConnected | 系统成功绑定该服务时被触发, 也就是当你在设置中开启相应的服务, 系统成功的绑定了该服务时会触发, 通常我们可以在这里做一些初始化操作 |
| onCreate | onCreate |
| onDestroy | onDestroy |
| getSelf | 获取当前服务所持对象 |
| startService | 启动服务 |
| stopService | 停止服务 |
| checkAccessibility | 检查是否开启无障碍功能 |
| isAccessibilitySettingsOn | 判断是否开启无障碍功能 |
| setAccessibilityListener | 设置监听事件 |
| onServiceCreated | 服务创建通知 |
| onServiceDestroy | 服务销毁通知 |


* **通知栏监听服务 ->** [NotificationService.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/service/NotificationService.java)

| 方法 | 注释 |
| :- | :- |
| onNotificationPosted | 当系统收到新的通知后触发回调 |
| onNotificationRemoved | 当系统通知被删掉后触发回调 |
| onCreate | onCreate |
| onDestroy | onDestroy |
| onStartCommand | onStartCommand |
| getSelf | 获取当前服务所持对象 |
| startService | 启动服务 |
| stopService | 停止服务 |
| checkAndIntentSetting | 检查是否有获取通知栏信息权限并跳转设置页面 |
| isNotificationListenerEnabled | 判断是否有获取通知栏信息权限 |
| startNotificationListenSettings | 跳转到设置页面, 开启获取通知栏信息权限 |
| cancelNotification | 取消通知方法 |
| setNotificationListener | 设置通知栏监听事件 |
| onServiceCreated | 服务创建通知 |
| onServiceDestroy | 服务销毁通知 |


## <span id="devtemp">**`dev.temp`**</span>


* **随机生成汉字工具类 ->** [ChineseUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/temp/ChineseUtils.java)

| 方法 | 注释 |
| :- | :- |
| getRandomWord | 获取随机汉字 |