package dev.capture.model

import dev.capture.CaptureFile
import dev.capture.CaptureItem
import dev.capture.compiler.R
import dev.utils.app.ResourceUtils
import dev.utils.common.ConvertUtils
import dev.utils.common.StringUtils

internal class Items {

    /**
     * 数据来源类型
     */
    enum class DataType(
        val type: String
    ) {

        // 全部
        T_ALL("1"),

        // 0-9 分钟
        T_0_9("2"),

        // 10-19 分钟
        T_10_19("3"),

        // 20-29 分钟
        T_20_29("4"),

        // 30-39 分钟
        T_30_39("5"),

        // 40-49 分钟
        T_40_49("6"),

        // 50-59 分钟
        T_50_59("7"),

        ;

        open fun getTitle(): String {
            val res = when (this) {
                T_0_9 -> R.string.dev_http_capture_data_type_0_9
                T_10_19 -> R.string.dev_http_capture_data_type_10_19
                T_20_29 -> R.string.dev_http_capture_data_type_20_29
                T_30_39 -> R.string.dev_http_capture_data_type_30_39
                T_40_49 -> R.string.dev_http_capture_data_type_40_49
                T_50_59 -> R.string.dev_http_capture_data_type_50_59
                else -> R.string.dev_http_capture_data_type_all
            }
            return ResourceUtils.getString(res)
        }
    }

    /**
     * 分组条件类型
     */
    enum class GroupType(
        val type: String
    ) {

        // 抓包时间
        T_TIME("1"),

        // 请求链接, 以请求接口前缀 ( ? 号前 )
        T_URL("2"),

        ;

        open fun getTitle(): String {
            val res = when (this) {
                T_URL -> R.string.dev_http_capture_group_type_url
                else -> R.string.dev_http_capture_group_type_time
            }
            return ResourceUtils.getString(res)
        }
    }

    /**
     * 首页适配器数据包装类
     */
    class MainItem(
        var moduleName: String,
        var lists: List<CaptureItem>
    )

    /**
     * 抓包列表数据包装类
     */
    class GroupItem(
        var title: String?,
        var lists: List<CaptureFile>
    ) {
        var function: String? = null

        fun setFunction(function: String?): GroupItem {
            this.function = function
            return this
        }

        init {
            this.title = convertTitleByHHMM(title)
        }
    }

    /**
     * 抓包文件数据包装类
     */
    class FileItem(
        var title: String,
        var value: String
    )

    // =============
    // = 内部转换方法 =
    // =============

    companion object {

        /**
         * 根据时间转换数据类型
         * @param key HHMM
         * @return 数据类型
         */
        fun convertDataType(key: String?): DataType? {
            // 获取分钟
            val minute = StringUtils.substring(
                key, 2, 4, false
            )
            val mm: Int = ConvertUtils.toInt(minute, -1)
            if (mm == -1) return null
            // 存储间隔以 10 分钟为单位
            return if (mm < 10) { // 00-09
                DataType.T_0_9
            } else if (mm < 20) { // 10-19
                DataType.T_10_19
            } else if (mm < 30) { // 20-29
                DataType.T_20_29
            } else if (mm < 40) { // 30-39
                DataType.T_30_39
            } else if (mm < 50) { // 40-49
                DataType.T_40_49
            } else { // 50-59
                DataType.T_50_59
            }
        }

        /**
         * 通过时间转换标题
         * @param title HHMM
         * @return 转换后的标题
         * 1000 转换为 10 hour 00-09 minute
         * 1010 转换为 10 hour 10-19 minute
         * 1020 转换为 10 hour 20-29 minute
         * 1030 转换为 10 hour 30-39 minute
         * 1040 转换为 10 hour 40-49 minute
         * 1050 转换为 10 hour 50-59 minute
         */
        fun convertTitleByHHMM(title: String?): String? {
            if (StringUtils.isLength(title, 4)) {
                val builder = StringBuilder()
                builder.append(title, 0, 2).append(" hour ")
                // 获取分钟
                var minute = StringUtils.substring(
                    title, 2, 4, false
                )
                val mm = ConvertUtils.toInt(minute)
                // 存储间隔以 10 分钟为单位
                minute = if (mm < 10) { // 00-09
                    "00"
                } else if (mm < 20) { // 10-19
                    "10"
                } else if (mm < 30) { // 20-29
                    "20"
                } else if (mm < 40) { // 30-39
                    "30"
                } else if (mm < 50) { // 40-49
                    "40"
                } else { // 50-59
                    "50"
                }
                builder.append(minute).append(" minute")
                return builder.toString()
            }
            return title
        }

        /**
         * 拆分 Url 用于匹配接口所属功能注释
         * @param url 请求接口链接
         * @return 处理后的 Url
         */
        fun convertUrlKey(url: String?): String? {
            if (!StringUtils.isSpace(url)) {
                val key = StringUtils.split(url, "\\?", 0)
                return StringUtils.replaceEndsWith(key, "/", "")
            }
            return null
        }
    }
}