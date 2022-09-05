package dev.capture

/**
 * detail: 抓包存储 Item
 * @author Ttt
 */
class CaptureItem(
    // 存储时间 - 年月日 ( 文件夹名 )
    val yyyyMMdd: String
) {
    // 存储数据 - 时分
    val data: Map<String, List<CaptureFile>> = LinkedHashMap()
}