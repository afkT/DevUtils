
## Gradle

```gradle
implementation 'io.github.afkt:DevJava:1.4.5'
```

## 目录结构

```
- dev.utils            | 根目录
   - common            | Java 工具类, 不依赖 android api
      - assist         | 各种快捷辅助类
         - record      | 文件记录分析类
         - search      | 搜索相关 ( 文件搜索等 )
         - url         | Url 携带信息解析
      - cipher         | 编 / 解码工具类
      - comparator     | 排序比较器
         - sort        | 各种类型比较器排序实现
      - encrypt        | 加密工具类
      - file           | 文件分片相关
      - random         | 随机概率算法工具类
      - thread         | 线程相关
      - validator      | 数据校验工具类
```


## 事项

- 部分 API 更新不及时或有遗漏等，`具体以对应的工具类为准`

- [检测代码规范、注释内容排版，API 文档生成](https://github.com/afkT/JavaDoc)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/CHANGELOG.md)

- 该工具类不依赖 android api，属于 Java 工具类库

- 开启日志
```java
// 打开 lib 内部日志 - 线上 (release) 环境，不调用方法
JCLogUtils.setPrintLog(true);
// 控制台打印日志
JCLogUtils.setControlPrintLog(true);
// 设置 Java 模块日志信息监听
JCLogUtils.setPrint(new JCLogUtils.Print() {});
```

## API


- dev.utils                                                 | 根目录
   - [common](#devutilscommon)                              | Java 工具类, 不依赖 android api
      - [assist](#devutilscommonassist)                     | 各种快捷辅助类
         - [record](#devutilscommonassistrecord)            | 文件记录分析类
         - [search](#devutilscommonassistsearch)            | 搜索相关 ( 文件搜索等 )
         - [url](#devutilscommonassisturl)                  | Url 携带信息解析
      - [cipher](#devutilscommoncipher)                     | 编 / 解码工具类
      - [comparator](#devutilscommoncomparator)             | 排序比较器
         - [sort](#devutilscommoncomparatorsort)            | 各种类型比较器排序实现
      - [encrypt](#devutilscommonencrypt)                   | 加密工具类
      - [file](#devutilscommonfile)                         | 文件分片相关
      - [random](#devutilscommonrandom)                     | 随机概率算法工具类
      - [thread](#devutilscommonthread)                     | 线程相关
      - [validator](#devutilscommonvalidator)               | 数据校验工具类


## <span id="devutilscommon">**`dev.utils.common`**</span>


* **Array 数组工具类 ->** [ArrayUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/ArrayUtils.java)

| 方法 | 注释 |
| :- | :- |
| isEmpty | 判断数组是否为 null |
| isNotEmpty | 判断数组是否不为 null |
| length | 获取数组长度 |
| isLength | 判断数组长度是否等于期望长度 |
| getCount | 获取数组长度总和 |
| getByArray | 获取数组对应索引数据 |
| get | 获取数组对应索引数据 |
| getFirst | 获取数组第一条数据 |
| getLast | 获取数组最后一条数据 |
| getPosition | 根据指定值获取 value 所在位置 + 偏移量的索引 |
| getNotNull | 根据指定 value 获取 value 所在位置 + 偏移量的值, 不允许值为 null |
| getPositionNotNull | 根据指定 value 获取索引, 不允许值为 null |
| intsToIntegers | int[] 转换 Integer[] |
| bytesToBytes | byte[] 转换 Byte[] |
| charsToCharacters | char[] 转换 Character[] |
| shortsToShorts | short[] 转换 Short[] |
| longsToLongs | long[] 转换 Long[] |
| floatsToFloats | float[] 转换 Float[] |
| doublesToDoubles | double[] 转换 Double[] |
| booleansToBooleans | boolean[] 转换 Boolean[] |
| integersToInts | Integer[] 转换 int[] |
| charactersToChars | Character[] 转换 char[] |
| asList | 转换数组为集合 |
| asListArgs | 转换数组为集合 |
| asListArgsInt | 转换数组为集合 |
| asListArgsByte | 转换数组为集合 |
| asListArgsChar | 转换数组为集合 |
| asListArgsShort | 转换数组为集合 |
| asListArgsLong | 转换数组为集合 |
| asListArgsFloat | 转换数组为集合 |
| asListArgsDouble | 转换数组为集合 |
| asListArgsBoolean | 转换数组为集合 |
| equals | 判断两个值是否一样 |
| arrayCopy | 拼接数组 |
| newArray | 创建指定长度数组 |
| subArray | 从数组上截取一段 |
| appendToString | 追加数组内容字符串 |
| getMinimumIndex | 获取数组中最小值索引 |
| getMaximumIndex | 获取数组中最大值索引 |
| getMinimum | 获取数组中最小值 |
| getMaximum | 获取数组中最大值 |
| sumArray | 计算数组总和 |


* **资金运算工具类 ->** [BigDecimalUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/BigDecimalUtils.java)

| 方法 | 注释 |
| :- | :- |
| setScale | 设置全局小数点保留位数、舍入模式 |
| getBigDecimal | 获取 BigDecimal |
| operation | 获取 Operation |
| adjustDouble | 获取自己想要的数据格式 |
| compareTo | 比较大小 |
| compareToThrow | 比较大小 ( 抛出异常 ) |
| add | 提供精确的加法运算 |
| subtract | 提供精确的减法运算 |
| multiply | 提供精确的乘法运算 |
| divide | 提供精确的除法运算 |
| remainder | 提供精确的取余运算 |
| round | 提供精确的小数位四舍五入处理 |
| addThrow | 提供精确的加法运算 ( 抛出异常 ) |
| subtractThrow | 提供精确的减法运算 ( 抛出异常 ) |
| multiplyThrow | 提供精确的乘法运算 ( 抛出异常 ) |
| divideThrow | 提供精确的除法运算 ( 抛出异常 ) |
| remainderThrow | 提供精确的取余运算 ( 抛出异常 ) |
| roundThrow | 提供精确的小数位四舍五入处理 ( 抛出异常 ) |
| getScale | 获取小数点保留位数 |
| getRoundingMode | 获取舍入模式 |
| requireNonNull | 检查 Value 是否为 null, 为 null 则抛出异常 |
| setBigDecimal | 设置 Value |
| getConfig | 获取配置信息 |
| setConfig | 设置配置信息 |
| removeConfig | 移除配置信息 |
| setScaleByConfig | 设置小数点保留位数、舍入模式 |
| isThrowError | 是否抛出异常 |
| setThrowError | 设置是否抛出异常 |
| clone | 克隆对象 |
| toString | 获取此 BigDecimal 的字符串表示形式科学记数法 |
| toPlainString | 获取此 BigDecimal 的字符串表示形式不带指数字段 |
| toEngineeringString | 获取此 BigDecimal 的字符串表示形式工程计数法 |
| intValue | 获取指定类型值 |
| floatValue | 获取指定类型值 |
| longValue | 获取指定类型值 |
| doubleValue | 获取指定类型值 |
| formatMoney | 金额分割, 四舍五入金额 |


* **日历工具类 ->** [CalendarUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/CalendarUtils.java)

| 方法 | 注释 |
| :- | :- |
| isSupportLunar | 是否支持农历年份计算 |
| isSupportSolar | 是否支持公历年份计算 |
| solarToLunar | 公历转农历 |
| lunarToSolar | 农历转公历 |
| getLunarYearDays | 获取农历年份总天数 |
| getLunarLeapDays | 获取农历年份闰月天数 |
| getLunarLeapMonth | 获取农历年份哪个月是闰月 |
| getLunarMonthDays | 获取农历年份与月份总天数 |
| getLunarGanZhi | 获取干支历 |
| getLunarMonthChinese | 获取农历中文月份 |
| getLunarDayChinese | 获取农历中文天数 |
| getSolarTermsIndex | 获取二十四节气 ( 公历 ) 索引 |
| getSolarTerms | 获取二十四节气 ( 公历 ) |
| getSolarTermsDate | 获取二十四节气 ( 公历 ) 时间 |
| isFestival | 校验是否相同节日 |
| getFestival | 获取符合条件的节日信息 |
| getSolarFestival | 获取公历符合条件的节日信息 |
| getLunarFestival | 获取农历符合条件的节日信息 |
| getFestivalHook | 获取节日 Hook 接口 |
| setFestivalHook | 设置节日 Hook 接口 |


* **中文工具类 ->** [ChineseUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/ChineseUtils.java)

| 方法 | 注释 |
| :- | :- |
| randomWord | 随机生成汉字 |
| randomName | 随机生成名字 |
| numberToCHN | 数字转中文数值 |


* **类 ( Class ) 工具类 ->** [ClassUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/ClassUtils.java)

| 方法 | 注释 |
| :- | :- |
| newInstance | 根据类获取对象, 不再必须一个无参构造 |
| getDefaultPrimitiveValue | 获取 Class 原始类型值 |
| getClass | 获取 Object Class |
| isPrimitive | 判断 Class 是否为原始类型 |
| isCollection | 判断是否 Collection 类型 |
| isMap | 判断是否 Map 类型 |
| isArray | 判断是否 Array 类型 |
| isGenericParamType | 判断是否参数类型 |
| getGenericParamType | 获取参数类型 |
| getGenericSuperclass | 获取父类泛型类型 |
| getGenericInterfaces | 获取接口泛型类型 |


* **克隆工具类 ->** [CloneUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/CloneUtils.java)

| 方法 | 注释 |
| :- | :- |
| deepClone | 进行克隆 |
| serializableToBytes | 通过序列化实体类, 获取对应的 byte[] 数据 |


* **关闭 ( IO 流 ) 工具类 ->** [CloseUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/CloseUtils.java)

| 方法 | 注释 |
| :- | :- |
| closeIO | 关闭 IO |
| closeIOQuietly | 安静关闭 IO |
| flush | 将缓冲区数据输出 |
| flushQuietly | 安静将缓冲区数据输出 |
| flushCloseIO | 将缓冲区数据输出并关闭流 |
| flushCloseIOQuietly | 安静将缓冲区数据输出并关闭流 |


* **集合工具类 ( Collection - List、Set、Queue ) 等 ->** [CollectionUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/CollectionUtils.java)

| 方法 | 注释 |
| :- | :- |
| isEmpty | 判断 Collection 是否为 null |
| isNotEmpty | 判断 Collection 是否不为 null |
| length | 获取 Collection 长度 |
| isLength | 获取长度 Collection 是否等于期望长度 |
| greaterThan | 判断 Collection 长度是否大于指定长度 |
| greaterThanOrEqual | 判断 Collection 长度是否大于等于指定长度 |
| lessThan | 判断 Collection 长度是否小于指定长度 |
| lessThanOrEqual | 判断 Collection 长度是否小于等于指定长度 |
| getCount | 获取 Collection 数组长度总和 |
| get | 获取数据 |
| getFirst | 获取第一条数据 |
| getLast | 获取最后一条数据 |
| getPosition | 根据指定 value 获取 value 所在位置 + 偏移量的索引 |
| getPositionNotNull | 根据指定 value 获取索引, 不允许值为 null |
| getNext | 根据指定 value 获取 value 所在位置的下一个值 |
| getNextNotNull | 根据指定 value 获取 value 所在位置的下一个值, 不允许值为 null |
| getPrevious | 根据指定 value 获取 value 所在位置的上一个值 |
| getPreviousNotNull | 根据指定 value 获取 value 所在位置的上一个值, 不允许值为 null |
| add | 添加一条数据 |
| addNotNull | 添加一条数据 ( value 不允许为 null ) |
| addAll | 添加集合数据 |
| addAllNotNull | 添加集合数据 ( values 内的值不允许为 null ) |
| clearAndAddAll | 移除全部数据并添加集合数据 |
| clearAndAddAllNotNull | 移除全部数据并添加集合数据 ( values 内的值不允许为 null ) |
| remove | 移除一条数据 |
| removeAll | 移除集合数据 |
| clear | 清空集合中符合指定 value 的全部数据 |
| clearNotBelong | 保留集合中符合指定 value 的全部数据 |
| clearAll | 清空集合全部数据 |
| clearNull | 清空集合中为 null 的值 |
| isEqualCollection | 判断两个集合是否相同 |
| isEqualCollections | 判断多个集合是否相同 |
| union | 两个集合并集处理 |
| unions | 多个集合并集处理 |
| intersection | 两个集合交集处理 |
| disjunction | 两个集合交集的补集处理 |
| subtract | 两个集合差集 ( 扣除 ) 处理 |
| equals | 判断两个值是否一样 |
| toArray | 转换数组 to Object |
| toArrayT | 转换数组 to T |
| reverse | 集合翻转处理 |
| getMinimumIndexI | 获取集合中最小值索引 |
| getMinimumIndexL | 获取集合中最小值索引 |
| getMinimumIndexF | 获取集合中最小值索引 |
| getMinimumIndexD | 获取集合中最小值索引 |
| getMaximumIndexI | 获取集合中最大值索引 |
| getMaximumIndexL | 获取集合中最大值索引 |
| getMaximumIndexF | 获取集合中最大值索引 |
| getMaximumIndexD | 获取集合中最大值索引 |
| getMinimumI | 获取集合中最小值 |
| getMinimumL | 获取集合中最小值 |
| getMinimumF | 获取集合中最小值 |
| getMinimumD | 获取集合中最小值 |
| getMaximumI | 获取集合中最大值 |
| getMaximumL | 获取集合中最大值 |
| getMaximumF | 获取集合中最大值 |
| getMaximumD | 获取集合中最大值 |
| sumlistI | 计算集合总和 |
| sumlistL | 计算集合总和 |
| sumlistF | 计算集合总和 |
| sumlistD | 计算集合总和 |


* **颜色工具类 ( 包括常用的色值 ) ->** [ColorUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/ColorUtils.java)

| 方法 | 注释 |
| :- | :- |
| hexAlpha | 获取十六进制透明度字符串 |
| getARGB | 返回一个颜色 ARGB 色值数组 ( 返回十进制 ) |
| alpha | 返回一个颜色中的透明度值 ( 返回十进制 ) |
| alphaPercent | 返回一个颜色中的透明度百分比值 |
| red | 返回一个颜色中红色的色值 ( 返回十进制 ) |
| redPercent | 返回一个颜色中红色的百分比值 |
| green | 返回一个颜色中绿色的色值 ( 返回十进制 ) |
| greenPercent | 返回一个颜色中绿色的百分比值 |
| blue | 返回一个颜色中蓝色的色值 ( 返回十进制 ) |
| bluePercent | 返回一个颜色中蓝色的百分比值 |
| rgb | 根据对应的 red、green、blue 生成一个颜色值 |
| argb | 根据对应的 alpha、red、green、blue 生成一个颜色值 ( 含透明度 ) |
| isRGB | 判断颜色 RGB 是否有效 |
| isARGB | 判断颜色 ARGB 是否有效 |
| setAlpha | 设置透明度 |
| setRed | 改变颜色值中的红色色值 |
| setGreen | 改变颜色值中的绿色色值 |
| setBlue | 改变颜色值中的蓝色色值 |
| parseColor | 解析颜色字符串, 返回对应的颜色值 |
| intToRgbString | 颜色值 转换 RGB 颜色字符串 |
| intToArgbString | 颜色值 转换 ARGB 颜色字符串 |
| getRandomColor | 获取随机颜色值 |
| getRandomColorString | 获取随机颜色值字符串 |
| judgeColorString | 判断是否为 ARGB 格式的十六进制颜色, 例如: FF990587 |
| setDark | 颜色加深 ( 单独修改 RGB 值, 不变动透明度 ) |
| setLight | 颜色变浅, 变亮 ( 单独修改 RGB 值, 不变动透明度 ) |
| setAlphaDark | 设置透明度加深 |
| setAlphaLight | 设置透明度变浅 |
| grayLevel | 获取灰度值 |
| setParser | 设置 Color 解析器 |
| sortGray | 灰度值排序 |
| sortHUE | HSB ( HSV ) HUE 色相排序 |
| sortSaturation | HSB ( HSV ) Saturation 饱和度排序 |
| sortBrightness | HSB ( HSV ) Brightness 亮度排序 |
| blendColor | 使用给定的比例在两种 ARGB 颜色之间进行混合 |
| transitionColor | 计算从 startColor 过渡到 endColor 过程中百分比为 ratio 时的颜色值 |
| getKey | 获取 Key |
| getValue | 获取 Value |
| getValueParser | 获取 Value 解析后的值 ( 如: #000 => #000000 ) |
| getValueColor | 获取 ARGB/RGB color |
| getAlpha | 返回颜色中的透明度值 ( 返回十进制 ) |
| getRed | 返回颜色中红色的色值 ( 返回十进制 ) |
| getGreen | 返回颜色中绿色的色值 ( 返回十进制 ) |
| getBlue | 返回颜色中蓝色的色值 ( 返回十进制 ) |
| getGrayLevel | 获取灰度值 |
| getHue | 获取颜色色调 |
| getSaturation | 获取颜色饱和度 |
| getBrightness | 获取颜色亮度 |
| handleColor | 处理 color |


* **转换工具类 ( Byte、Hex 等 ) ->** [ConvertUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/ConvertUtils.java)

| 方法 | 注释 |
| :- | :- |
| convert | Object 转换所需类型对象 |
| newString | Object 转 String |
| newStringNotArrayDecode | Object 转 String ( 不进行 Array 解码转 String ) |
| toString | Object 转 String |
| toInt | Object 转 Integer |
| toBoolean | Object 转 Boolean |
| toFloat | Object 转 Float |
| toDouble | Object 转 Double |
| toLong | Object 转 Long |
| toShort | Object 转 Short |
| toChar | Object 转 Character |
| toByte | Object 转 Byte |
| toBigDecimal | Object 转 BigDecimal |
| toBigInteger | Object 转 BigInteger |
| toChars | Object 获取 char[] |
| toBytes | Object 获取 byte[] |
| toCharInt | char 转换 unicode 编码 |
| charAt | Object 获取 char ( 默认第一位 ) |
| parseInt | 字符串转换对应的进制 |
| parseLong | 字符串转换对应的进制 |
| bytesToObject | byte[] 转为 Object |
| objectToBytes | Object 转为 byte[] |
| bytesToChars | byte[] 转换 char[], 并且进行补码 |
| charsToBytes | char[] 转换 byte[] |
| intsToStrings | int[] 转换 string[] |
| doublesToStrings | double[] 转换 string[] |
| longsToStrings | long[] 转换 string[] |
| floatsToStrings | float[] 转换 string[] |
| intsToDoubles | int[] 转换 double[] |
| intsToLongs | int[] 转换 long[] |
| intsToFloats | int[] 转换 float[] |
| stringsToInts | string[] 转换 int[] |
| stringsToDoubles | string[] 转换 double[] |
| stringsToLongs | string[] 转换 long[] |
| stringsToFloats | string[] 转换 float[] |
| doublesToInts | double[] 转换 int[] |
| longsToInts | long[] 转换 int[] |
| floatsToInts | float[] 转换 int[] |
| toBinaryString | 将 字节转换 为 二进制字符串 |
| decodeBinary | 二进制字符串 转换 byte[] 解码 |
| isHex | 判断是否十六进制数据 |
| decodeHex | 将十六进制字节数组解码 |
| hexToInt | 十六进制 char 转换 int |
| toHexString | int 转换十六进制 |
| toHexChars | 将 string 转换为 十六进制 char[] |
| bytesBitwiseAND | 按位求补 byte[] 位移编解码 ( 共用同一个方法 ) |


* **坐标 ( GPS 纠偏 ) 相关工具类 ->** [CoordinateUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/CoordinateUtils.java)

| 方法 | 注释 |
| :- | :- |
| bd09ToGcj02 | BD09 坐标转 GCJ02 坐标 |
| gcj02ToBd09 | GCJ02 坐标转 BD09 坐标 |
| gcj02ToWGS84 | GCJ02 坐标转 WGS84 坐标 |
| wgs84ToGcj02 | WGS84 坐标转 GCJ02 坐标 |
| bd09ToWGS84 | BD09 坐标转 WGS84 坐标 |
| wgs84ToBd09 | WGS84 坐标转 BD09 坐标 |
| outOfChina | 判断是否中国境外 |
| getDistance | 计算两个坐标相距距离 ( 单位: 米 ) |
| getAngle | 计算两个坐标的方向角度 |
| getDirection | 计算两个坐标的方向 |
| getValue | 获取中文方向值 |


* **日期工具类 ->** [DateUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/DateUtils.java)

| 方法 | 注释 |
| :- | :- |
| getDefaultFormat | 获取默认 SimpleDateFormat ( yyyy-MM-dd HH:mm:ss ) |
| getSafeDateFormat | 获取对应时间格式线程安全 SimpleDateFormat |
| getCalendar | 获取 Calendar |
| getCurrentTime | 获取当前时间 Date |
| getCurrentTimeMillis | 获取当前时间毫秒 |
| getDateTime | 获取 Date Time |
| getDateNow | 获取当前时间的字符串 |
| formatDate | 将 Date 转换日期字符串 |
| formatTime | 将时间毫秒转换日期字符串 |
| parseDate | 将时间毫秒转换成 Date |
| parseLong | 解析时间字符串转换为 long 毫秒 |
| parseStringDefault | 解析时间字符串转换为指定格式字符串 |
| parseString | 解析时间字符串转换为指定格式字符串 |
| getYear | 获取年份 |
| getMonth | 获取月份 ( 0 - 11 ) + 1 |
| getDay | 获取天数 |
| getWeek | 获取星期数 ( 1 - 7、日 - 六 ) |
| get24Hour | 获取小时 ( 24 ) |
| get12Hour | 获取小时 ( 12 ) |
| getMinute | 获取分钟 |
| getSecond | 获取秒数 |
| isAM | 是否上午 |
| isPM | 是否下午 |
| isYear | 是否对应年份 |
| isMonth | 是否对应月份 |
| isDay | 是否对应天数 |
| isWeek | 是否对应星期 |
| isHour | 是否对应小时 |
| isMinute | 是否对应分钟 |
| isSecond | 是否对应秒数 |
| getSecondMultiple | 获取秒数倍数 |
| getMinuteMultiple | 获取分钟倍数 |
| getHourMultiple | 获取小时倍数 |
| getDayMultiple | 获取天数倍数 |
| getWeekMultiple | 获取周数倍数 |
| getMillisMultiple | 获取对应单位倍数 |
| getTimeDiffByCurrent | 获取时间差 ( 传入时间 - 当前时间 ) |
| getTimeDiff | 获取时间差 |
| isLeapYear | 判断是否闰年 |
| getMonthDayNumberAll | 根据年份、月份, 获取对应的天数 ( 完整天数, 无判断是否属于未来日期 ) |
| getYearMonthNumber | 根据年份, 获取对应的月份 |
| getMonthDayNumber | 根据年份、月份, 获取对应的天数 |
| timeAddZero | 时间补 0 处理 ( 小于 10, 则自动补充 0x ) |
| getArrayToHH | 生成 HH 按时间排序数组 |
| getListToHH | 生成 HH 按时间排序集合 |
| getArrayToMM | 生成 MM 按时间排序数组 |
| getListToMM | 生成 MM 按时间排序集合 |
| getArrayToHHMM | 生成 HH:mm 按间隔时间排序数组 |
| getListToHHMM | 生成 HH:mm 按间隔时间排序集合 |
| getListToHHMMPosition | 获取 HH:mm 按间隔时间排序的集合中, 指定时间所在索引 |
| millisToFitTimeSpan | 转换时间 |
| millisToTimeArrays | 转换时间为数组 |
| timeConvertByMillis | 传入时间毫秒, 获取 00:00:00 格式 ( 不处理大于一天 ) |
| timeConvertBySecond | 传入时间秒, 获取 00:00:00 格式 ( 不处理大于一天 ) |
| isInTime | 判断时间是否在 [startTime, endTime] 区间 |
| isInTimeFormat | 判断时间是否在 [startTime, endTime] 区间 ( 自定义格式 ) |
| isInTimeHHmm | 判断时间是否在 [startTime, endTime] 区间 ( HHmm 格式 ) |
| isInTimeHHmmss | 判断时间是否在 [startTime, endTime] 区间 ( HHmmss 格式 ) |
| getEndTimeDiffHHmm | 获取指定时间距离该时间第二天的指定时段的时间 ( 判断凌晨情况 ) |
| getEndTimeDiff | 获取指定时间距离该时间第二天的指定时段的时间差 ( 判断凌晨情况 ) |
| getZodiac | 获取生肖 |
| getConstellation | 获取星座 |
| getConstellationDate | 获取星座日期 |


* **开发常用方法工具类 ->** [DevCommonUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/DevCommonUtils.java)

| 方法 | 注释 |
| :- | :- |
| timeRecord | 耗时时间记录 |
| getOperateTime | 获取操作时间 |
| sleepOperate | 堵塞操作 |
| isHttpRes | 判断是否网络资源 |
| whileMD5 | 循环 MD5 加密处理 |
| randomUUID | 获取随机唯一数 |
| randomUUIDToHashCode | 获取随机唯一数 HashCode |
| getRandomUUID | 获取随机规则生成 UUID |
| getRandomUUIDToString | 获取随机规则生成 UUID 字符串 |


* **编码工具类 ->** [EncodeUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/EncodeUtils.java)

| 方法 | 注释 |
| :- | :- |
| base64Encode | Base64 编码 |
| base64EncodeToString | Base64 编码 |
| base64Decode | Base64 解码 |
| base64DecodeToString | Base64 解码 |
| htmlEncode | Html 字符串编码 |


* **变量字段工具类 ->** [FieldUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/FieldUtils.java)

| 方法 | 注释 |
| :- | :- |
| getField | 获取变量对象 |
| getDeclaredField | 获取变量对象 |
| getFields | 获取变量对象数组 |
| getDeclaredFields | 获取变量对象数组 |
| set | 设置字段的值 |
| get | 获取字段的值 |
| isLong | 是否 long/Long 类型 |
| isFloat | 是否 float/Float 类型 |
| isDouble | 是否 double/Double 类型 |
| isInteger | 是否 int/Integer 类型 |
| isBoolean | 是否 boolean/Boolean 类型 |
| isCharacter | 是否 char/Character 类型 |
| isByte | 是否 byte/Byte 类型 |
| isShort | 是否 short/Short 类型 |
| isString | 是否 String 类型 |
| isSerializable | 判断是否序列化 |
| isInvalid | 是否静态常量或者内部结构属性 |
| isStatic | 是否静态变量 |
| isFinal | 是否常量 |
| isStaticFinal | 是否静态变量 |
| isSynthetic | 是否内部结构属性 |
| getGenericType | 获取字段的泛型类型, 如果不带泛型返回 null |
| getComponentType | 获取数组的类型 |
| getAllDeclaredFields | 获取全部 Field, 包括父类 |


* **文件 ( IO 流 ) 工具类 ->** [FileIOUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/FileIOUtils.java)

| 方法 | 注释 |
| :- | :- |
| setBufferSize | 设置缓冲区的大小, 默认大小等于 8192 字节 |
| getFileInputStream | 获取输入流 |
| getFileOutputStream | 获取输出流 |
| writeFileFromIS | 通过输入流写入文件 |
| writeFileFromBytesByStream | 通过字节流写入文件 |
| writeFileFromBytesByChannel | 通过 FileChannel 把字节流写入文件 |
| writeFileFromBytesByMap | 通过 MappedByteBuffer 把字节流写入文件 |
| writeFileFromString | 通过字符串写入文件 |
| readFileToList | 读取文件内容, 返回换行 List |
| readFileToString | 读取文件内容, 返回字符串 |
| readFileToBytesByStream | 读取文件内容, 返回 byte[] |
| readFileToBytesByChannel | 通过 FileChannel, 读取文件内容, 返回 byte[] |
| readFileToBytesByMap | 通过 MappedByteBuffer, 读取文件内容, 返回 byte[] |
| copyLarge | 复制 InputStream 到 OutputStream |


* **文件操作工具类 ->** [FileUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/FileUtils.java)

| 方法 | 注释 |
| :- | :- |
| getFile | 获取文件 |
| getFileByPath | 获取文件 |
| getFileCreateFolder | 获取路径, 并且进行创建目录 |
| getFilePathCreateFolder | 获取路径, 并且进行创建目录 |
| createFolder | 判断某个文件夹是否创建, 未创建则创建 ( 纯路径无文件名 ) |
| createFolderByPath | 创建文件夹目录 ( 可以传入文件名 ) |
| createFolderByPaths | 创建多个文件夹, 如果不存在则创建 |
| createOrExistsDir | 判断目录是否存在, 不存在则判断是否创建成功 |
| createOrExistsFile | 判断文件是否存在, 不存在则判断是否创建成功 |
| createFileByDeleteOldFile | 判断文件是否存在, 存在则在创建之前删除 |
| createTimestampFileName | 通过文件后缀创建时间戳文件名 |
| createTimestampFileNameByName | 通过文件名创建时间戳文件名 |
| createTimestampFileNameByFile | 通过文件创建时间戳文件名 |
| createTimestampFileNameByPath | 通过文件路径创建时间戳文件名 |
| convertFiles | Path List 转 File List |
| convertPaths | File List 转 Path List |
| getPath | 获取文件路径 |
| getAbsolutePath | 获取文件绝对路径 |
| getName | 获取文件名 |
| getFileSuffix | 获取文件后缀名 ( 无 "." 单独后缀 ) |
| getFileNotSuffix | 获取文件名 ( 无后缀 ) |
| getFileNotSuffixToPath | 获取文件名 ( 无后缀 ) |
| getFileNameNoExtension | 获取路径中的不带扩展名的文件名 |
| getFileExtension | 获取路径中的文件扩展名 |
| isFileExists | 检查是否存在某个文件 |
| isFile | 判断是否文件 |
| isDirectory | 判断是否文件夹 |
| isHidden | 判断是否隐藏文件 |
| canRead | 文件是否可读 |
| canWrite | 文件是否可写 |
| canReadWrite | 文件是否可读写 |
| getFileLastModified | 获取文件最后修改的毫秒时间戳 |
| getFileCharsetSimple | 获取文件编码格式 |
| getFileLines | 获取文件行数 |
| getFileSize | 获取文件大小 |
| getDirSize | 获取目录大小 |
| getFileLength | 获取文件大小 |
| getDirLength | 获取目录全部文件大小 |
| getFileLengthNetwork | 获取文件大小 ( 网络资源 ) |
| getFileName | 获取路径中的文件名 |
| getDirName | 获取路径中的最长目录地址 |
| rename | 重命名文件 ( 同个目录下, 修改文件名 ) |
| formatFileSize | 传入文件路径, 返回对应的文件大小 |
| formatByteMemorySize | 字节数转合适内存大小 保留 3 位小数 |
| deleteFile | 删除文件 |
| deleteFiles | 删除多个文件 |
| deleteFolder | 删除文件夹 |
| saveFile | 保存文件 |
| appendFile | 追加文件 |
| readFileBytes | 读取文件 |
| readFile | 读取文件 |
| copyFile | 复制单个文件 |
| copyFolder | 复制文件夹 |
| moveFile | 移动 ( 剪切 ) 文件 |
| moveFolder | 移动 ( 剪切 ) 文件夹 |
| copyOrMoveDir | 复制或移动目录 |
| copyOrMoveFile | 复制或移动文件 |
| copyDir | 复制目录 |
| moveDir | 移动目录 |
| deleteDir | 删除目录 |
| deleteAllInDir | 删除目录下所有文件 |
| deleteFilesInDir | 删除目录下所有文件 |
| deleteFilesInDirWithFilter | 删除目录下所有过滤的文件 |
| listFilesInDir | 获取目录下所有文件 ( 不递归进子目录 ) |
| listFilesInDirWithFilter | 获取目录下所有过滤的文件 ( 不递归进子目录 ) |
| listFilesInDirBean | 获取目录下所有文件 ( 不递归进子目录 ) |
| listFilesInDirWithFilterBean | 获取目录下所有过滤的文件 ( 不递归进子目录 ) |
| listOrEmpty | 获取文件夹下的文件目录列表 ( 非全部子目录 ) |
| listFilesOrEmpty | 获取文件夹下的文件目录列表 ( 非全部子目录 ) |
| isImageFormats | 根据文件名判断文件是否为图片 |
| isAudioFormats | 根据文件名判断文件是否为音频 |
| isVideoFormats | 根据文件名判断文件是否为视频 |
| isFileFormats | 根据文件名判断文件是否为指定格式 |
| getFileMD5 | 获取文件 MD5 值 |
| getFileMD5ToHexString | 获取文件 MD5 值 |


* **循环工具类 ->** [ForUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/ForUtils.java)

| 方法 | 注释 |
| :- | :- |
| forArgs | 循环可变数组 |
| forSimpleArgs | 循环可变数组 |
| forInts | 循环可变数组 |
| forDoubles | 循环可变数组 |
| forFloats | 循环可变数组 |
| forLongs | 循环可变数组 |
| forBooleans | 循环可变数组 |
| forBytes | 循环可变数组 |
| forChars | 循环可变数组 |
| forShorts | 循环可变数组 |
| accept | 循环消费方法 |


* **Html 工具类 ->** [HtmlUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/HtmlUtils.java)

| 方法 | 注释 |
| :- | :- |
| addRemovePaddingMargin | 为给定的 Html 移除 padding、margin |
| addHtmlColor | 为给定的字符串添加 HTML 颜色标记 |
| addHtmlBold | 为给定的字符串添加 HTML 加粗标记 |
| addHtmlColorAndBold | 为给定的字符串添加 HTML 颜色标记并加粗 |
| addHtmlUnderline | 为给定的字符串添加 HTML 下划线 |
| addHtmlStrikeThruLine | 为给定的字符串添加 HTML 中划线 |
| addHtmlOverLine | 为给定的字符串添加 HTML 上划线 |
| addHtmlIncline | 为给定的字符串添加 HTML 字体倾斜 |
| addHtmlSPAN | 为给定的字符串添加 HTML SPAN 标签 |
| addHtmlP | 为给定的字符串添加 HTML P 标签 |
| addHtmlIMG | 为给定的字符串添加 HTML IMG 标签 |
| addHtmlIMGByWidth | 为给定的字符串添加 HTML IMG 标签 |
| addHtmlIMGByHeight | 为给定的字符串添加 HTML IMG 标签 |
| addHtmlDIV | 为给定的字符串添加 HTML DIV 标签 |
| addHtmlDIVByMargin | 为给定的字符串添加 HTML DIV 标签 |
| addHtmlDIVByPadding | 为给定的字符串添加 HTML DIV 标签 |
| addHtmlDIVByMarginPadding | 为给定的字符串添加 HTML DIV 标签 |
| keywordReplaceHtmlColor | 将给定的字符串中所有给定的关键字标色 |


* **Http 参数工具类 ->** [HttpParamsUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/HttpParamsUtils.java)

| 方法 | 注释 |
| :- | :- |
| getUrlParams | 获取 Url 携带参数 |
| getUrlParamsArray | 获取 Url、携带参数 数组 |
| existsParams | 判断是否存在参数 |
| existsParamsByURL | 通过 Url 判断是否存在参数 |
| joinUrlParams | 拼接 Url 及携带参数 |
| getUrlParamsJoinSymbol | 获取 Url 及携带参数 拼接符号 |
| splitParamsByUrl | 通过 Url 拆分参数 |
| splitParams | 拆分参数 |
| joinParams | 拼接请求参数 |
| joinParamsObj | 拼接请求参数 |
| convertObjToMS | 进行转换对象处理 ( 请求发送对象 ) |
| convertObjToMO | 进行转换对象处理 ( 请求发送对象 ) |
| urlEncode | 进行 URL 编码, 默认 UTF-8 |


* **HttpURLConnection 网络工具类 ->** [HttpURLConnectionUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/HttpURLConnectionUtils.java)

| 方法 | 注释 |
| :- | :- |
| doGetAsync | 异步的 Get 请求 |
| doPostAsync | 异步的 Post 请求 |
| request | 发送请求 |
| getNetTime | 获取网络时间 ( 默认使用百度链接 ) |


* **Map 工具类 ->** [MapUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/MapUtils.java)

| 方法 | 注释 |
| :- | :- |
| isEmpty | 判断 Map 是否为 null |
| isNotEmpty | 判断 Map 是否不为 null |
| length | 获取 Map 长度 |
| isLength | 获取长度 Map 是否等于期望长度 |
| greaterThan | 判断 Map 长度是否大于指定长度 |
| greaterThanOrEqual | 判断 Map 长度是否大于等于指定长度 |
| lessThan | 判断 Map 长度是否小于指定长度 |
| lessThanOrEqual | 判断 Map 长度是否小于等于指定长度 |
| getCount | 获取 Map 数组长度总和 |
| get | 获取 value |
| getKeyByValue | 通过 value 获取 key |
| getKeysByValue | 通过 value 获取 key 集合 ( 返回等于 value 的 key 集合 ) |
| getKeys | 通过 Map 获取 key 集合 |
| getKeysToArrays | 通过 Map 获取 key 数组 |
| getValues | 通过 Map 获取 value 集合 |
| getValuesToArrays | 通过 Map 获取 value 数组 |
| getFirst | 获取第一条数据 |
| getLast | 获取最后一条数据 |
| getNext | 根据指定 key 获取 key 所在位置的下一条数据 |
| getPrevious | 根据指定 key 获取 key 所在位置的上一条数据 |
| put | 添加一条数据 |
| putNotNull | 添加一条数据 ( 不允许 key 为 null ) |
| putAll | 添加多条数据 |
| putAllNotNull | 添加多条数据, 不允许 key 为 null |
| remove | 移除一条数据 |
| removeToKeys | 移除多条数据 |
| removeToValue | 移除等于 value 的所有数据 |
| removeToValues | 移除等于 value 的所有数据 ( Collection<Value> ) |
| equals | 判断两个值是否一样 |
| toggle | 切换保存状态 |
| isNullToValue | 判断指定 key 的 value 是否为 null |
| containsKey | 判断 Map 是否存储 key |
| containsValue | 判断 Map 是否存储 value |
| putToList | 添加一条数据 |
| removeToList | 移除一条数据 |
| removeToLists | 移除多条数据 |
| removeToMap | 移除多条数据 ( 通过 Map 进行移除 ) |
| mapToString | 键值对拼接 |


* **数字 ( 计算 ) 工具类 ->** [NumberUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/NumberUtils.java)

| 方法 | 注释 |
| :- | :- |
| addZero | 补 0 处理 ( 小于 10, 则自动补充 0x ) |
| subZeroAndDot | 去掉结尾多余的 . 与 0 |
| calculateUnit | 计算指定单位倍数 |
| calculateUnitI | 计算指定单位倍数 |
| calculateUnitL | 计算指定单位倍数 |
| percentD | 计算百分比值 ( 最大 100% ) |
| percentI | 计算百分比值 ( 最大 100% ) |
| percentL | 计算百分比值 ( 最大 100% ) |
| percentF | 计算百分比值 ( 最大 100% ) |
| percentD2 | 计算百分比值 ( 可超出 100% ) |
| percentI2 | 计算百分比值 ( 可超出 100% ) |
| percentL2 | 计算百分比值 ( 可超出 100% ) |
| percentF2 | 计算百分比值 ( 可超出 100% ) |
| multipleD | 获取倍数 |
| multipleI | 获取倍数 |
| multipleL | 获取倍数 |
| multipleF | 获取倍数 |
| multiple | 获取整数倍数 ( 自动补 1 ) |
| clamp | 返回的 value 介于 max、min 之间, 若 value 小于 min, 返回 min, 若大于 max, 返回 max |
| numberToCHN | 数字转中文数值 |
| isNumber | 检验数字 |
| isNumberDecimal | 检验数字或包含小数点 |


* **对象相关工具类 ->** [ObjectUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/ObjectUtils.java)

| 方法 | 注释 |
| :- | :- |
| isEmpty | 判断对象是否为空 |
| isNotEmpty | 判断对象是否非空 |
| equals | 判断两个值是否一样 |
| requireNonNull | 检查对象是否为 null, 为 null 则抛出异常, 不为 null 则返回该对象 |
| getOrDefault | 获取非空或默认对象 |
| hashCode | 获取对象哈希值 |
| getObjectTag | 获取一个对象的独一无二的标记 |
| convert | Object 转换所需类型对象 |


* **随机工具类 ->** [RandomUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/RandomUtils.java)

| 方法 | 注释 |
| :- | :- |
| nextBoolean | 获取伪随机 boolean 值 |
| nextBytes | 获取伪随机 byte[] |
| nextDouble | 获取伪随机 double 值 |
| nextGaussian | 获取伪随机高斯分布值 |
| nextFloat | 获取伪随机 float 值 |
| nextInt | 获取伪随机 int 值 |
| nextLong | 获取伪随机 long 值 |
| getRandomNumbers | 获取数字自定义长度的随机数 |
| getRandomLowerCaseLetters | 获取小写字母自定义长度的随机数 |
| getRandomCapitalLetters | 获取大写字母自定义长度的随机数 |
| getRandomLetters | 获取大小写字母自定义长度的随机数 |
| getRandomNumbersAndLetters | 获取数字、大小写字母自定义长度的随机数 |
| getRandom | 获取自定义数据自定义长度的随机数 |
| shuffle | 洗牌算法 ( 第一种 ) 随机置换指定的数组使用的默认源的随机性 ( 随机数据源小于三个, 则无效 ) |
| shuffle2 | 洗牌算法 ( 第二种 ) 随机置换指定的数组使用的默认源的随机性 |
| nextIntRange | 获取指定范围 int 值 |
| nextLongRange | 获取指定范围 long 值 |
| nextDoubleRange | 获取指定范围 double 值 |
| ints | 获取随机 int[] |
| longs | 获取随机 long[] |
| doubles | 获取随机 double[] |


* **反射相关工具类 ->** [Reflect2Utils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/Reflect2Utils.java)

| 方法 | 注释 |
| :- | :- |
| setProperty | 设置某个对象变量值 ( 可设置静态变量 ) |
| getProperty | 获取某个对象的变量 ( 可获取静态变量 ) |
| getStaticProperty | 获取某个类的静态变量 ( 只能获取静态变量 ) |
| invokeMethod | 执行某个对象方法 ( 可执行静态方法 ) |
| invokeStaticMethod | 执行某个类的静态方法 ( 只能执行静态方法 ) |
| newInstance | 新建实例 ( 构造函数创建 ) |
| isInstance | 是不是某个类的实例 |
| getArgsClass | 获取参数类型 |
| getPropertyByParent | 获取父类中的变量对象 |
| getDeclaredFieldParent | 获取父类中的变量对象 ( 循环向上转型, 获取对象的 DeclaredField ) |


* **反射相关工具类 ->** [ReflectUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/ReflectUtils.java)

| 方法 | 注释 |
| :- | :- |
| reflect | 设置要反射的类 |
| newInstance | 实例化反射对象 |
| field | 设置反射的字段 |
| setEnumVal | 设置枚举值 |
| method | 设置反射的方法 |
| proxy | 根据类, 代理创建并返回对象 |
| type | 获取类型 |
| get | 获取反射想要获取的 |
| hashCode | 获取 HashCode |
| equals | 判断反射的两个对象是否一样 |
| toString | 获取反射获取的对象 |


* **计算比例工具类 ->** [ScaleUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/ScaleUtils.java)

| 方法 | 注释 |
| :- | :- |
| calcScale | 计算比例 ( 商 ) |
| calcScaleToMath | 计算比例 ( 被除数 ( 最大值 ) / 除数 ( 最小值 ) ) |
| calcScaleToWidth | 计算缩放比例 ( 根据宽度比例转换高度 ) |
| calcScaleToHeight | 计算缩放比例 ( 根据高度比例转换宽度 ) |
| calcWidthHeightToScale | 通过宽度、高度根据对应的比例, 转换成对应的比例宽度高度 ( 智能转换 ) |
| calcWidthToScale | 以宽度为基准, 转换对应比例的高度 |
| calcHeightToScale | 以高度为基准, 转换对应比例的宽度 |
| calcScaleToWidthI | 计算缩放比例 ( 根据宽度比例转换高度 ) |
| calcScaleToHeightI | 计算缩放比例 ( 根据高度比例转换宽度 ) |
| calcWidthHeightToScaleI | 通过宽度、高度根据对应的比例, 转换成对应的比例宽度高度 ( 智能转换 ) |
| calcWidthToScaleI | 以宽度为基准, 转换对应比例的高度 |
| calcHeightToScaleI | 以高度为基准, 转换对应比例的宽度 |
| calcScaleToWidthL | 计算缩放比例 ( 根据宽度比例转换高度 ) |
| calcScaleToHeightL | 计算缩放比例 ( 根据高度比例转换宽度 ) |
| calcWidthHeightToScaleL | 通过宽度、高度根据对应的比例, 转换成对应的比例宽度高度 ( 智能转换 ) |
| calcWidthToScaleL | 以宽度为基准, 转换对应比例的高度 |
| calcHeightToScaleL | 以高度为基准, 转换对应比例的宽度 |
| calcScaleToWidthF | 计算缩放比例 ( 根据宽度比例转换高度 ) |
| calcScaleToHeightF | 计算缩放比例 ( 根据高度比例转换宽度 ) |
| calcWidthHeightToScaleF | 通过宽度、高度根据对应的比例, 转换成对应的比例宽度高度 ( 智能转换 ) |
| calcWidthToScaleF | 以宽度为基准, 转换对应比例的高度 |
| calcHeightToScaleF | 以高度为基准, 转换对应比例的宽度 |
| calcXY | 计算 XY 比 |


* **流操作工具类 ->** [StreamUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/StreamUtils.java)

| 方法 | 注释 |
| :- | :- |
| inputToOutputStream | 输入流转输出流 |
| outputToInputStream | 输出流转输入流 |
| inputStreamToBytes | 输入流转 byte[] |
| bytesToInputStream | byte[] 转输出流 |
| outputStreamToBytes | 输出流转 byte[] |
| bytesToOutputStream | byte[] 转 输出流 |
| inputStreamToString | 输入流转 String |
| stringToInputStream | String 转换输入流 |
| outputStreamToString | 输出流转 String |
| stringToOutputStream | String 转 输出流 |


* **字符串工具类 ->** [StringUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/StringUtils.java)

| 方法 | 注释 |
| :- | :- |
| isEmpty | 判断字符串是否为 null |
| isEmptyClear | 判断字符串是否为 null ( 调用 clearSpaceTabLineTrim ) |
| isNotEmpty | 判断字符串是否不为 null |
| isNotEmptyClear | 判断字符串是否不为 null ( 调用 clearSpaceTabLineTrim ) |
| isNull | 判断字符串是否为 "null" |
| isNullClear | 判断字符串是否为 "null" ( 调用 clearSpaceTabLineTrim ) |
| isNotNull | 判断字符串是否不为 "null" |
| isNotNullClear | 判断字符串是否不为 "null" ( 调用 clearSpaceTabLineTrim ) |
| length | 获取字符串长度 |
| isLength | 获取字符串长度 是否等于期望长度 |
| equals | 判断两个值是否一样 |
| equalsNotNull | 判断两个值是否一样 ( 非 null 判断 ) |
| isEquals | 判断多个字符串是否相等, 只有全相等才返回 true ( 对比大小写 ) |
| isOrEquals | 判断多个字符串, 只要有一个符合条件则通过 |
| isContains | 判断一堆值中, 是否存在符合该条件的 ( 包含 ) |
| isStartsWith | 判断内容, 是否属于特定字符串开头 ( 对比大小写 ) |
| isEndsWith | 判断内容, 是否属于特定字符串结尾 ( 对比大小写 ) |
| countMatches | 统计字符串匹配个数 |
| countMatches2 | 统计字符串匹配个数 |
| isSpace | 判断字符串是否为 null 或全为空白字符 |
| getBytes | 字符串 转 byte[] |
| clearSpace | 清空字符串全部空格 |
| clearTab | 清空字符串全部 Tab |
| clearLine | 清空字符串全部换行符 |
| clearLine2 | 清空字符串全部换行符 |
| clearSpaceTrim | 清空字符串前后全部空格 |
| clearTabTrim | 清空字符串前后全部 Tab |
| clearLineTrim | 清空字符串前后全部换行符 |
| clearLineTrim2 | 清空字符串前后全部换行符 |
| clearSpaceTabLine | 清空字符串全部空格、Tab、换行符 |
| clearSpaceTabLineTrim | 清空字符串前后全部空格、Tab、换行符 |
| appendSpace | 追加空格 |
| appendTab | 追加 Tab |
| appendLine | 追加换行 |
| appendLine2 | 追加换行 |
| forString | 循环指定数量字符串 |
| joinArgs | 循环拼接 |
| join | 循环拼接 |
| colonSplit | 冒号分割处理 |
| getString | 获取字符串 ( 判 null ) |
| checkValue | 检查字符串 |
| checkValues | 检查字符串 ( 多个值 ) |
| checkValuesSpace | 检查字符串 ( 多个值, 删除前后空格对比判断 ) |
| getFormatString | 获取格式化后的字符串 |
| getAutoFormatString | 获取自动数量格式化后的字符串 ( 可变参数 ) |
| getAutoFormatString2 | 获取自动数量格式化后的字符串 ( 可变参数 ) |
| concat | 字符串连接, 将参数列表拼接为一个字符串 |
| concatSpiltWith | 字符串连接, 将参数列表拼接为一个字符串 |
| concatSpiltWithIgnoreLast | 字符串连接, 将参数列表拼接为一个字符串 ( 最后一个不追加间隔 ) |
| appends | StringBuilder 拼接处理 |
| appendsIgnoreLast | StringBuilder 拼接处理 ( 最后一个不追加间隔 ) |
| gbkEncode | 字符串进行 GBK 编码 |
| gbk2312Encode | 字符串进行 GBK2312 编码 |
| utf8Encode | 字符串进行 UTF-8 编码 |
| strEncode | 进行字符串编码 |
| urlEncode | 进行 URL 编码, 默认 UTF-8 |
| urlDecode | 进行 URL 解码, 默认 UTF-8 |
| urlDecodeWhile | 进行 URL 解码, 默认 UTF-8 ( 循环到非 URL 编码为止 ) |
| ascii | 将字符串转移为 ASCII 码 |
| unicode | 将字符串转移为 Unicode 码 |
| unicodeString | 将字符数组转移为 Unicode 码 |
| dbc | 转化为半角字符 |
| sbc | 转化为全角字符 如: a = ａ, A = Ａ |
| checkChineseToString | 检测字符串是否全是中文 |
| isChinese | 判断输入汉字 |
| upperFirstLetter | 首字母大写 |
| lowerFirstLetter | 首字母小写 |
| reverse | 反转字符串 |
| underScoreCaseToCamelCase | 下划线命名转为驼峰命名 |
| camelCaseToUnderScoreCase | 驼峰命名法转为下划线命名 |
| sqliteEscape | 字符串数据库字符转义 |
| convertHideMobile | 转换手机号 |
| convertSymbolHide | 转换符号处理 |
| subEllipsize | 裁剪超出的内容, 并且追加符号 ( 如 ... ) |
| subSymbolHide | 裁剪符号处理 |
| subSetSymbol | 裁剪内容 ( 设置符号处理 ) |
| substring | 裁剪字符串 |
| replaceSEWith | 替换特定字符串开头、结尾的字符串 |
| replaceStartsWith | 替换开头字符串 |
| replaceEndsWith | 替换结尾字符串 |
| clearSEWiths | 清空特定字符串开头、结尾的字符串 |
| clearStartsWith | 清空特定字符串开头的字符串 |
| clearEndsWith | 清空特定字符串结尾的字符串 |
| replaceAll | 替换字符串 |
| replaceAllToNull | 替换字符串 |
| replaceAlls | 替换字符串 |
| split | 拆分字符串 |


* **异常处理工具类 ->** [ThrowableUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/ThrowableUtils.java)

| 方法 | 注释 |
| :- | :- |
| getThrowable | 获取异常信息 |
| getThrowableStackTrace | 获取异常栈信息 |


* **类型工具类 ->** [TypeUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/TypeUtils.java)

| 方法 | 注释 |
| :- | :- |
| getArrayType | 获取 Array Type |
| getListType | 获取 List Type |
| getSetType | 获取 Set Type |
| getMapType | 获取 Map Type |
| getType | 获取 Type |


* **压缩相关工具类 ->** [ZipUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/ZipUtils.java)

| 方法 | 注释 |
| :- | :- |
| zipFiles | 批量压缩文件 |
| zipFile | 压缩文件 |
| unzipFile | 解压文件 |
| unzipFileByKeyword | 解压带有关键字的文件 |
| getFilesPath | 获取压缩文件中的文件路径链表 |
| getComments | 获取压缩文件中的注释链表 |


## <span id="devutilscommonassist">**`dev.utils.common.assist`**</span>


* **均值计算 ( 用以统计平均数 ) 辅助类 ->** [Averager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/assist/Averager.java)

| 方法 | 注释 |
| :- | :- |
| add | 添加一个数字 |
| clear | 清除全部 |
| size | 获取参与均值计算的数字个数 |
| getAverage | 获取平均数 |
| print | 输出参与均值计算的数字 |


* **标记值计算存储 ( 位运算符 ) ->** [FlagsValue.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/assist/FlagsValue.java)

| 方法 | 注释 |
| :- | :- |
| getFlags | 获取 flags value |
| setFlags | 设置 flags value |
| addFlags | 添加 flags value |
| clearFlags | 移除 flags value |
| hasFlags | 是否存在 flags value |
| notHasFlags | 是否不存在 flags value |


* **时间均值计算辅助类 ->** [TimeAverager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/assist/TimeAverager.java)

| 方法 | 注释 |
| :- | :- |
| start | 开始计时 ( 毫秒 ) |
| end | 结束计时 ( 毫秒 ) |
| endAndRestart | 结束计时, 并重新启动新的计时 |
| average | 求全部计时均值 |
| print | 输出全部时间值 |
| clear | 清除计时数据 |


* **时间计时辅助类 ->** [TimeCounter.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/assist/TimeCounter.java)

| 方法 | 注释 |
| :- | :- |
| start | 开始计时 ( 毫秒 ) |
| durationRestart | 获取持续的时间并重新启动 ( 毫秒 ) |
| duration | 获取持续的时间 ( 毫秒 ) |
| getStartTime | 获取开始时间 ( 毫秒 ) |


* **堵塞时间辅助类 ->** [TimeKeeper.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/assist/TimeKeeper.java)

| 方法 | 注释 |
| :- | :- |
| waitForEndAsync | 设置等待一段时间后, 通知方法 ( 异步 ) |
| waitForEnd | 设置等待一段时间后, 通知方法 ( 同步 ) |


* **弱引用辅助类 ->** [WeakReferenceAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/assist/WeakReferenceAssist.java)

| 方法 | 注释 |
| :- | :- |
| getSingleWeak | 获取单个弱引用对象 |
| getSingleWeakValue | 获取单个弱引用对象值 |
| setSingleWeakValue | 保存单个弱引用对象值 |
| removeSingleWeak | 移除单个弱引用持有对象 |
| getWeak | 获取弱引用对象 |
| getWeakValue | 获取弱引用对象值 |
| setWeakValue | 保存弱引用对象值 |
| removeWeak | 移除指定弱引用持有对象 |
| clear | 清空全部弱引用持有对象 |


## <span id="devutilscommonassistrecord">**`dev.utils.common.assist.record`**</span>


* **文件记录分析工具类 ->** [FileRecordUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/assist/record/FileRecordUtils.java)

| 方法 | 注释 |
| :- | :- |
| isSuccessful | 校验记录方法返回字符串是否成功 |
| isHandler | 是否处理记录 |
| setHandler | 设置是否处理记录 |
| getRecordInsert | 获取日志记录插入信息 |
| setRecordInsert | 设置日志记录插入信息 |
| setCallback | 设置文件记录回调 |
| getLogContent | 获取日志内容 |
| record | 记录方法 |


* **日志记录配置信息 ->** [RecordConfig.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/assist/record/RecordConfig.java)

| 方法 | 注释 |
| :- | :- |
| get | 获取配置信息 |
| getStoragePath | 获取存储路径 |
| getFileName | 获取文件名 ( 固定 ) |
| getFolderName | 获取文件夹名 ( 模块名 ) |
| getFileIntervalTime | 获取文件记录间隔时间 |
| isHandler | 是否处理记录 |
| setHandler | 设置是否处理记录 |
| isInsertHeaderData | 是否插入头数据 |
| setInsertHeaderData | 设置是否插入头数据 |
| getRecordInsert | 获取日志记录插入信息 |
| setRecordInsert | 设置日志记录插入信息 |
| getFinalPath | 获取文件地址 |


* **日志记录插入信息 ->** [RecordInsert.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/assist/record/RecordInsert.java)

| 方法 | 注释 |
| :- | :- |
| getFileInfo | getFileInfo |
| setFileInfo | setFileInfo |
| getLogHeader | getLogHeader |
| setLogHeader | setLogHeader |
| getLogTail | getLogTail |
| setLogTail | setLogTail |


## <span id="devutilscommonassistsearch">**`dev.utils.common.assist.search`**</span>


* **文件广度优先搜索算法 ( 多线程 + 队列, 搜索某个目录下的全部文件 ) ->** [FileBreadthFirstSearchUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/assist/search/FileBreadthFirstSearchUtils.java)

| 方法 | 注释 |
| :- | :- |
| setSearchHandler | 设置搜索处理接口 |
| getQueueSameTimeNumber | 获取任务队列同时进行数量 |
| setQueueSameTimeNumber | 任务队列同时进行数量 |
| isRunning | 是否搜索中 |
| stop | 停止搜索 |
| isStop | 是否停止搜索 |
| getStartTime | 获取开始搜索时间 ( 毫秒 ) |
| getEndTime | 获取结束搜索时间 ( 毫秒 ) |
| getDelayTime | 获取延迟校验时间 ( 毫秒 ) |
| setDelayTime | 设置延迟校验时间 ( 毫秒 ) |
| query | 搜索目录 |


* **文件深度优先搜索算法 ( 递归搜索某个目录下的全部文件 ) ->** [FileDepthFirstSearchUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/assist/search/FileDepthFirstSearchUtils.java)

| 方法 | 注释 |
| :- | :- |
| setSearchHandler | 设置搜索处理接口 |
| isRunning | 是否搜索中 |
| stop | 停止搜索 |
| isStop | 是否停止搜索 |
| getStartTime | 获取开始搜索时间 ( 毫秒 ) |
| getEndTime | 获取结束搜索时间 ( 毫秒 ) |
| query | 搜索目录 |


## <span id="devutilscommonassisturl">**`dev.utils.common.assist.url`**</span>


* **Dev 库 Java 通用 Url 解析器 ->** [DevJavaUrlParser.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/assist/url/DevJavaUrlParser.java)

| 方法 | 注释 |
| :- | :- |
| reset | reset |
| setUrl | setUrl |
| getUrl | getUrl |
| getUrlByPrefix | getUrlByPrefix |
| getUrlByParams | getUrlByParams |
| getUrlParams | getUrlParams |
| getUrlParamsDecode | getUrlParamsDecode |
| isConvertMap | isConvertMap |
| setConvertMap | setConvertMap |


* **Url 携带信息解析 ->** [UrlExtras.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/assist/url/UrlExtras.java)

| 方法 | 注释 |
| :- | :- |
| getUrl | 获取完整 Url |
| getUrlByPrefix | 获取 Url 前缀 ( 去除参数部分 ) |
| getUrlByParams | 获取 Url 参数部分字符串 |
| getUrlParams | 获取 Url Params Map |
| getUrlParamsDecode | 获取 Url Params Map ( 参数值进行 UrlDecode ) |
| getParser | 获取 Url 解析器 |
| setParser | 设置 Url 解析器 |
| reset | 重置并返回一个新的解析器 |
| setUrl | 设置完整 Url |
| isConvertMap | 是否解析、转换 Param Map |
| setConvertMap | 设置是否解析、转换 Param Map |


## <span id="devutilscommoncipher">**`dev.utils.common.cipher`**</span>


* **Base64 工具类 ->** [Base64.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/cipher/Base64.java)

| 方法 | 注释 |
| :- | :- |
| decode | Decode the Base64-encoded data in input and return the data in |
| encodeToString | Base64-encode the given data and return a newly allocated |
| encode | Base64-encode the given data and return a newly allocated |


* **Base64 编解码 ( 并进行 ) 加解密 ->** [Base64Cipher.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/cipher/Base64Cipher.java)

| 方法 | 注释 |
| :- | :- |
| decrypt | 解码 |
| encrypt | 编码 |


* **加密工具类 ->** [CipherUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/cipher/CipherUtils.java)

| 方法 | 注释 |
| :- | :- |
| encrypt | 加密方法 |
| decrypt | 解密方法 |


## <span id="devutilscommoncomparator">**`dev.utils.common.comparator`**</span>


* **排序比较器工具类 ->** [ComparatorUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/ComparatorUtils.java)

| 方法 | 注释 |
| :- | :- |
| reverse | List 反转处理 |
| sort | List 排序处理 |
| sortAsc | List 升序处理 |
| sortDesc | List 降序处理 |
| sortFileLastModifiedAsc | 文件修改时间升序排序 |
| sortFileLastModifiedDesc | 文件修改时间降序排序 |
| sortFileLengthAsc | 文件大小升序排序 |
| sortFileLengthDesc | 文件大小降序排序 |
| sortFileNameAsc | 文件名升序排序 |
| sortFileNameDesc | 文件名降序排序 |
| sortFileAsc | 文件升序排序 |
| sortFileDesc | 文件降序排序 |
| sortDateAsc | Date 升序排序 |
| sortDateDesc | Date 降序排序 |
| sortDoubleAsc | Double 升序排序 |
| sortDoubleDesc | Double 降序排序 |
| sortFloatAsc | Float 升序排序 |
| sortFloatDesc | Float 降序排序 |
| sortIntAsc | Int 升序排序 |
| sortIntDesc | Int 降序排序 |
| sortLongAsc | Long 升序排序 |
| sortLongDesc | Long 降序排序 |
| sortStringAsc | String 升序排序 |
| sortStringDesc | String 降序排序 |
| sortStringWindowsSimpleAsc | String Windows 排序比较器简单实现升序排序 |
| sortStringWindowsSimpleDesc | String Windows 排序比较器简单实现降序排序 |
| sortStringWindowsSimple2Asc | String Windows 排序比较器简单实现升序排序 ( 实现方式二 ) |
| sortStringWindowsSimple2Desc | String Windows 排序比较器简单实现降序排序 ( 实现方式二 ) |
| sortWindowsExplorerFileSimpleComparatorAsc | Windows 目录资源文件升序排序 |
| sortWindowsExplorerFileSimpleComparatorDesc | Windows 目录资源文件降序排序 |
| sortWindowsExplorerFileSimpleComparator2Asc | Windows 目录资源文件升序排序 ( 实现方式二 ) |
| sortWindowsExplorerFileSimpleComparator2Desc | Windows 目录资源文件降序排序 ( 实现方式二 ) |
| sortWindowsExplorerStringSimpleComparatorAsc | Windows 目录资源文件名升序排序 |
| sortWindowsExplorerStringSimpleComparatorDesc | Windows 目录资源文件名降序排序 |
| sortWindowsExplorerStringSimpleComparator2Asc | Windows 目录资源文件名升序排序 ( 实现方式二 ) |
| sortWindowsExplorerStringSimpleComparator2Desc | Windows 目录资源文件名降序排序 ( 实现方式二 ) |


## <span id="devutilscommoncomparatorsort">**`dev.utils.common.comparator.sort`**</span>


* **Date 排序值 ->** [DateSort.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/DateSort.java)

| 方法 | 注释 |
| :- | :- |
| getDateSortValue | getDateSortValue |


* **Date 升序排序 ->** [DateSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/DateSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Date 降序排序 ->** [DateSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/DateSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Double 排序值 ->** [DoubleSort.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/DoubleSort.java)

| 方法 | 注释 |
| :- | :- |
| getDoubleSortValue | getDoubleSortValue |


* **Double 升序排序 ->** [DoubleSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/DoubleSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Double 降序排序 ->** [DoubleSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/DoubleSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **文件修改时间升序排序 ->** [FileLastModifiedSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/FileLastModifiedSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **文件修改时间降序排序 ->** [FileLastModifiedSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/FileLastModifiedSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **文件大小升序排序 ->** [FileLengthSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/FileLengthSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **文件大小降序排序 ->** [FileLengthSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/FileLengthSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **文件名升序排序 ->** [FileNameSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/FileNameSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **文件名降序排序 ->** [FileNameSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/FileNameSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **文件升序排序 ->** [FileSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/FileSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **文件降序排序 ->** [FileSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/FileSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Float 排序值 ->** [FloatSort.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/FloatSort.java)

| 方法 | 注释 |
| :- | :- |
| getFloatSortValue | getFloatSortValue |


* **Float 升序排序 ->** [FloatSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/FloatSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Float 降序排序 ->** [FloatSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/FloatSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Int 排序值 ->** [IntSort.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/IntSort.java)

| 方法 | 注释 |
| :- | :- |
| getIntSortValue | getIntSortValue |


* **Int 升序排序 ->** [IntSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/IntSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Int 降序排序 ->** [IntSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/IntSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Long 排序值 ->** [LongSort.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/LongSort.java)

| 方法 | 注释 |
| :- | :- |
| getLongSortValue | getLongSortValue |


* **Long 升序排序 ->** [LongSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/LongSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Long 降序排序 ->** [LongSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/LongSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **String 排序值 ->** [StringSort.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/StringSort.java)

| 方法 | 注释 |
| :- | :- |
| getStringSortValue | getStringSortValue |


* **String 升序排序 ->** [StringSortAsc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/StringSortAsc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **String 降序排序 ->** [StringSortDesc.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/StringSortDesc.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **String Windows 排序比较器简单实现 ->** [StringSortWindowsSimple.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/StringSortWindowsSimple.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **String Windows 排序比较器简单实现 ->** [StringSortWindowsSimple2.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/StringSortWindowsSimple2.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Windows 目录资源文件排序比较器 ->** [WindowsExplorerFileSimpleComparator.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/WindowsExplorerFileSimpleComparator.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Windows 目录资源文件排序比较器 ->** [WindowsExplorerFileSimpleComparator2.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/WindowsExplorerFileSimpleComparator2.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Windows 目录资源文件名排序比较器 ->** [WindowsExplorerStringSimpleComparator.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/WindowsExplorerStringSimpleComparator.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


* **Windows 目录资源文件名排序比较器 ->** [WindowsExplorerStringSimpleComparator2.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/comparator/sort/WindowsExplorerStringSimpleComparator2.java)

| 方法 | 注释 |
| :- | :- |
| compare | compare |


## <span id="devutilscommonencrypt">**`dev.utils.common.encrypt`**</span>


* **AES 对称加密工具类 ->** [AESUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/encrypt/AESUtils.java)

| 方法 | 注释 |
| :- | :- |
| initKey | 生成密钥 |
| encrypt | AES 加密 |
| decrypt | AES 解密 |


* **CRC 工具类 ->** [CRCUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/encrypt/CRCUtils.java)

| 方法 | 注释 |
| :- | :- |
| getCRC32 | 获取 CRC32 值 |
| getCRC32ToHexString | 获取 CRC32 值 |
| getFileCRC32 | 获取文件 CRC32 值 |


* **DES 对称加密工具类 ->** [DESUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/encrypt/DESUtils.java)

| 方法 | 注释 |
| :- | :- |
| getDESKey | 获取可逆算法 DES 的密钥 |
| encrypt | DES 加密 |
| decrypt | DES 解密 |


* **加解密通用工具类 ->** [EncryptUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/encrypt/EncryptUtils.java)

| 方法 | 注释 |
| :- | :- |
| encryptMD2 | MD2 加密 |
| encryptMD2ToHexString | MD2 加密 |
| encryptMD5 | MD5 加密 |
| encryptMD5ToHexString | MD5 加密 |
| encryptMD5File | 获取文件 MD5 值 |
| encryptMD5FileToHexString | 获取文件 MD5 值 |
| encryptSHA1 | SHA1 加密 |
| encryptSHA1ToHexString | SHA1 加密 |
| encryptSHA224 | SHA224 加密 |
| encryptSHA224ToHexString | SHA224 加密 |
| encryptSHA256 | SHA256 加密 |
| encryptSHA256ToHexString | SHA256 加密 |
| encryptSHA384 | SHA384 加密 |
| encryptSHA384ToHexString | SHA384 加密 |
| encryptSHA512 | SHA512 加密 |
| encryptSHA512ToHexString | SHA512 加密 |
| hashTemplate | Hash 加密模版方法 |
| encryptHmacMD5 | HmacMD5 加密 |
| encryptHmacMD5ToHexString | HmacMD5 加密 |
| encryptHmacSHA1 | HmacSHA1 加密 |
| encryptHmacSHA1ToHexString | HmacSHA1 加密 |
| encryptHmacSHA224 | HmacSHA224 加密 |
| encryptHmacSHA224ToHexString | HmacSHA224 加密 |
| encryptHmacSHA256 | HmacSHA256 加密 |
| encryptHmacSHA256ToHexString | HmacSHA256 加密 |
| encryptHmacSHA384 | HmacSHA384 加密 |
| encryptHmacSHA384ToHexString | HmacSHA384 加密 |
| encryptHmacSHA512 | HmacSHA512 加密 |
| encryptHmacSHA512ToHexString | HmacSHA512 加密 |
| hmacTemplate | Hmac 加密模版方法 |
| encryptDES | DES 加密 |
| encryptDESToBase64 | DES 加密 |
| encryptDESToHexString | DES 加密 |
| decryptDES | DES 解密 |
| decryptDESToBase64 | DES 解密 |
| decryptDESToHexString | DES 解密 |
| encrypt3DES | 3DES 加密 |
| encrypt3DESToBase64 | 3DES 加密 |
| encrypt3DESToHexString | 3DES 加密 |
| decrypt3DES | 3DES 解密 |
| decrypt3DESToBase64 | 3DES 解密 |
| decrypt3DESToHexString | 3DES 解密 |
| encryptAES | AES 加密 |
| encryptAESToBase64 | AES 加密 |
| encryptAESToHexString | AES 加密 |
| decryptAES | AES 解密 |
| decryptAESToBase64 | AES 解密 |
| decryptAESToHexString | AES 解密 |
| symmetricTemplate | 对称加密模版方法 |
| encryptRSA | RSA 加密 |
| encryptRSAToBase64 | RSA 加密 |
| encryptRSAToHexString | RSA 加密 |
| decryptRSA | RSA 解密 |
| decryptRSAToBase64 | RSA 解密 |
| decryptRSAToHexString | RSA 解密 |
| rsaTemplate | RSA 加解密模版方法 |


* **字符串 ( 编解码 ) 工具类 ->** [EscapeUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/encrypt/EscapeUtils.java)

| 方法 | 注释 |
| :- | :- |
| escape | 编码 |
| unescape | 解码 |


* **MD5 加密工具类 ->** [MD5Utils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/encrypt/MD5Utils.java)

| 方法 | 注释 |
| :- | :- |
| md5 | 加密内容 ( 32 位小写 MD5 ) |
| md5Upper | 加密内容 ( 32 位大写 MD5 ) |
| getFileMD5 | 获取文件 MD5 值 |
| getFileMD5ToHexString | 获取文件 MD5 值 |


* **SHA 加密工具类 ->** [SHAUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/encrypt/SHAUtils.java)

| 方法 | 注释 |
| :- | :- |
| sha1 | 加密内容 SHA1 |
| sha224 | 加密内容 SHA224 |
| sha256 | 加密内容 SHA256 |
| sha384 | 加密内容 SHA384 |
| sha512 | 加密内容 SHA512 |
| getFileSHA1 | 获取文件 SHA1 值 |
| getFileSHA256 | 获取文件 SHA256 值 |
| shaHex | 加密内容 SHA 模板 |
| getFileSHA | 获取文件 SHA 值 |


* **3DES 对称加密工具类 ->** [TripleDESUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/encrypt/TripleDESUtils.java)

| 方法 | 注释 |
| :- | :- |
| initKey | 生成密钥 |
| encrypt | 3DES 加密 |
| decrypt | 3DES 解密 |


* **异或工具类 ->** [XorUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/encrypt/XorUtils.java)

| 方法 | 注释 |
| :- | :- |
| encryptAsFix | 加解密 ( 固定 Key 方式 ) 这种方式 加解密 方法共用 |
| encrypt | 加密 ( 非固定 Key 方式 ) |
| decrypt | 解密 ( 非固定 Key 方式 ) |
| xorChecksum | 数据异或校验位计算 |


## <span id="devutilscommonfile">**`dev.utils.common.file`**</span>


* **文件分片辅助类 ->** [FilePartAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/file/FilePartAssist.java)

| 方法 | 注释 |
| :- | :- |
| getFile | 获取文件 |
| getFileName | 获取文件名 |
| getFilePartItems | 获取文件分片信息集合 |
| getFilePartItem | 获取指定索引文件分片信息 |
| getPartCount | 获取分片总数 |
| existsPart | 是否存在分片 |
| isOnlyOne | 是否只有一个分片 |
| getPartName | 获取分片文件名 ( 后缀索引拼接 ) |


* **文件分片信息 Item ->** [FilePartItem.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/file/FilePartItem.java)

| 方法 | 注释 |
| :- | :- |
| isFirstItem | 判断是否 First Item |
| isLastItem | 判断是否 Last Item |
| existsPart | 是否存在分片 |
| isOnlyOne | 是否只有一个分片 |
| getPartName | 获取分片文件名 ( 后缀索引拼接 ) |


* **文件分片工具类 ->** [FilePartUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/file/FilePartUtils.java)

| 方法 | 注释 |
| :- | :- |
| getPartName | 获取分片文件名 ( 后缀索引拼接 ) |
| getFilePartAssist | 获取文件分片辅助类 |
| isFilePart | 是否符合文件分片条件 |
| fileSplit | 文件拆分 |
| fileSplitSave | 文件拆分并存储 |
| fileSplitSaves | 文件拆分并存储 |
| fileSplitDelete | 删除拆分文件 |
| fileSplitDeletes | 删除拆分文件 |
| fileSplitMergePaths | 分片合并 |
| fileSplitMergeFiles | 分片合并 |
| fileSplitMerge | 分片合并 |


## <span id="devutilscommonrandom">**`dev.utils.common.random`**</span>


* **随机概率采样算法 ->** [AliasMethod.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/random/AliasMethod.java)

| 方法 | 注释 |
| :- | :- |
| next | 获取随机索引 ( 对应几率索引 ) |


## <span id="devutilscommonthread">**`dev.utils.common.thread`**</span>


* **线程池管理工具类 ->** [DevThreadManager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/thread/DevThreadManager.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | 获取 DevThreadManager 实例 |
| initConfig | 初始化配置信息 |
| putConfig | 添加配置信息 |
| removeConfig | 移除配置信息 |


* **线程池 ( 构建类 ) ->** [DevThreadPool.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/thread/DevThreadPool.java)

| 方法 | 注释 |
| :- | :- |
| getThreads | 获取线程数 |
| getCalcThreads | 获取线程数 |
| execute | 加入到线程池任务队列 |
| shutdown | shutdown 会等待所有提交的任务执行完成, 不管是正在执行还是保存在任务队列中的已提交任务 |
| shutdownNow | shutdownNow 会尝试中断正在执行的任务 ( 其主要是中断一些指定方法如 sleep 方法 ) , 并且停止执行等待队列中提交的任务 |
| isShutdown | 判断线程池是否已关闭 ( isShutDown 当调用 shutdown() 方法后返回为 true ) |
| isTerminated | 若关闭后所有任务都已完成, 则返回 true |
| awaitTermination | 请求关闭、发生超时或者当前线程中断 |
| submit | 提交一个 Callable 任务用于执行 |
| invokeAll | 执行给定的任务 |
| invokeAny | 执行给定的任务 |
| schedule | 延迟执行 Runnable 命令 |
| scheduleWithFixedRate | 延迟并循环执行命令 |
| scheduleWithFixedDelay | 延迟并以固定休息时间循环执行命令 |


## <span id="devutilscommonvalidator">**`dev.utils.common.validator`**</span>


* **银行卡管理工具类 ->** [BankCheckUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/validator/BankCheckUtils.java)

| 方法 | 注释 |
| :- | :- |
| checkBankCard | 校验银行卡卡号是否合法 |
| getBankCardCheckCode | 从不含校验位的银行卡卡号采用 Luhn 校验算法获取校验位 |
| getNameOfBank | 通过银行卡的 前六位确定 判断银行开户行及卡种 |


* **居民身份证工具类 ->** [IDCardUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/validator/IDCardUtils.java)

| 方法 | 注释 |
| :- | :- |
| validateIdCard15 | 身份证校验规则, 验证 15 位身份编码是否合法 |
| validateIdCard18 | 身份证校验规则, 验证 18 位身份编码是否合法 |
| convert15CardTo18 | 将 15 位身份证号码转换为 18 位 |
| validateTWCard | 验证台湾身份证号码 |
| validateHKCard | 验证香港身份证号码 ( 部份特殊身份证无法检查 ) |
| validateIdCard10 | 判断 10 位数的身份证号, 是否合法 |
| validateCard | 验证身份证是否合法 |
| getAgeByIdCard | 根据身份编号获取年龄 |
| getBirthByIdCard | 根据身份编号获取生日 |
| getBirthdayByIdCard | 根据身份编号获取生日 |
| getYearByIdCard | 根据身份编号获取生日 ( 年份 ) |
| getMonthByIdCard | 根据身份编号获取生日 ( 月份 ) |
| getDateByIdCard | 根据身份编号获取生日 ( 天数 ) |
| getGenderByIdCard | 根据身份编号获取性别 |
| getProvinceByIdCard | 根据身份编号获取户籍省份 |
| getPowerSum | 将身份证的每位和对应位的加权因子相乘之后, 再获取和值 |
| getCheckCode18 | 将 POWER 和值与 11 取模获取余数进行校验码判断 |


* **校验工具类 ->** [ValidatorUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/validator/ValidatorUtils.java)

| 方法 | 注释 |
| :- | :- |
| match | 通用匹配函数 |
| isNumber | 检验数字 |
| isNumberDecimal | 检验数字或包含小数点 |
| isLetter | 判断字符串是不是全是字母 |
| isContainNumber | 判断字符串是不是包含数字 |
| isNumberLetter | 判断字符串是不是只含字母和数字 |
| isSpec | 检验特殊符号 |
| isWx | 检验微信号 |
| isRealName | 检验真实姓名 |
| isNickName | 校验昵称 |
| isUserName | 校验用户名 |
| isPassword | 校验密码 |
| isEmail | 校验邮箱 |
| isUrl | 校验 URL |
| isIPAddress | 校验 IP 地址 |
| isChinese | 校验汉字 ( 无符号, 纯汉字 ) |
| isChineseAll | 判断字符串是不是全是中文 |
| isContainChinese | 判断字符串中包含中文、包括中文字符标点等 |


* **检验联系 ( 手机号、座机 ) 工具类 ->** [ValiToPhoneUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/src/main/java/dev/utils/common/validator/ValiToPhoneUtils.java)

| 方法 | 注释 |
| :- | :- |
| isPhoneCheck | 中国手机号格式验证, 在输入可以调用该方法, 点击发送验证码, 使用 isPhone |
| isPhone | 是否中国手机号 |
| isPhoneToChinaTelecom | 是否中国电信手机号码 |
| isPhoneToChinaUnicom | 是否中国联通手机号码 |
| isPhoneToChinaMobile | 是否中国移动手机号码 |
| isPhoneToHkMobile | 判断是否香港手机号 |
| isPhoneCallNum | 验证电话号码的格式 |