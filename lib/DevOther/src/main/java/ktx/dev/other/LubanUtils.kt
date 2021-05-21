package ktx.dev.other

import android.net.Uri
import android.text.TextUtils
import dev.DevUtils
import top.zibin.luban.CompressionPredicate
import top.zibin.luban.InputStreamProvider
import top.zibin.luban.Luban
import top.zibin.luban.OnRenameListener
import java.io.File
import java.io.FileNotFoundException
import java.util.*

/**
 * detail: Luban 工具类
 * @author Ttt
 * Luban 鲁班图片压缩
 * @see https://github.com/Curzibn/Luban
 */
object LubanUtils {

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 压缩方法
     * @param data             待压缩图片
     * @param config           配置信息
     * @param compressListener 压缩回调接口
     * @return `true` success, `false` fail
     */
    fun compress(
        data: Any?,
        config: Config?,
        compressListener: OnCompressListener?
    ): Boolean {
        return compress(data, config, null, null, compressListener)
    }

    /**
     * 压缩方法
     * @param data             待压缩图片
     * @param config           配置信息
     * @param predicate        开启压缩条件
     * @param renameListener   压缩前重命名接口
     * @param compressListener 压缩回调接口
     * @return `true` success, `false` fail
     */
    fun compress(
        data: Any?,
        config: Config?,
        predicate: CompressionPredicate?,
        renameListener: OnRenameListener?,
        compressListener: OnCompressListener?
    ): Boolean {
        if (data == null || config == null || compressListener == null) return false
        val lists = ArrayList<Any?>()
        lists.add(data)
        return compress(lists, config, predicate, renameListener, compressListener)
    }

    // =

    /**
     * 压缩方法
     * @param lists            待压缩图片集合
     * @param config           配置信息
     * @param compressListener 压缩回调接口
     * @return `true` success, `false` fail
     */
    fun compress(
        lists: List<*>?,
        config: Config?,
        compressListener: OnCompressListener?
    ): Boolean {
        return compress(lists, config, null, null, compressListener)
    }

    /**
     * 压缩方法
     * @param lists            待压缩图片集合
     * @param config           配置信息
     * @param predicate        开启压缩条件
     * @param renameListener   压缩前重命名接口
     * @param compressListener 压缩回调接口
     * @return `true` success, `false` fail
     */
    fun compress(
        lists: List<*>?,
        config: Config?,
        predicate: CompressionPredicate?,
        renameListener: OnRenameListener?,
        compressListener: OnCompressListener?
    ): Boolean {
        if (lists == null || config == null || compressListener == null) return false
        var number = 0
        val builder = Luban.with(DevUtils.getContext())
        for (src in lists) {
            if (src is String) {
                builder.load(src)
                number++
            } else if (src is File) {
                builder.load(src)
                number++
            } else if (src is Uri) {
                builder.load(src)
                number++
            } else if (src is InputStreamProvider) {
                builder.load(src)
                number++
            }
        }
        if (number == 0) return false
        val count = number
        val fileMaps: MutableMap<Int, File?> = LinkedHashMap()
        // 配置信息
        builder.ignoreBy(config.ignoreSize)
            .setFocusAlpha(config.focusAlpha)
            .setTargetDir(config.targetDir)
            .filter { path ->
                if (predicate != null) return@filter predicate.apply(path)
                return@filter !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"))
            }
            .setRenameListener(renameListener)
            .setCompressListener(object : top.zibin.luban.OnCompressListener {
                override fun onStart() {
                    val size = fileMaps.size
                    fileMaps[size] = null
                    compressListener.onStart(size, count)
                }

                override fun onSuccess(file: File?) {
                    if (file == null) {
                        onError(NullPointerException("file is null"))
                        return
                    }
                    if (!file.exists()) { // 文件不存在则触发异常回调
                        onError(FileNotFoundException(file.absolutePath))
                        return
                    }
                    val size = fileMaps.size
                    val index = size - 1
                    fileMaps[index] = file
                    compressListener.onSuccess(file, index, count)
                    if (size >= count) {
                        compressListener.onComplete(getLists(), fileMaps, count)
                    }
                }

                override fun onError(e: Throwable) {
                    val size = fileMaps.size
                    compressListener.onError(e, size - 1, count)
                    if (size >= count) {
                        compressListener.onComplete(getLists(), fileMaps, count)
                    }
                }

                private fun getLists(): List<File> {
                    val files: MutableList<File> = ArrayList()
                    val iterator: Iterator<File?> = fileMaps.values.iterator()
                    while (iterator.hasNext()) {
                        val file = iterator.next()
                        if (file != null && file.exists()) {
                            files.add(file)
                        }
                    }
                    return files
                }
            }).launch()
        return true
    }

    // =======
    // = 配置 =
    // =======

    /**
     * detail: Image Compress Config
     * @author Ttt
     */
    class Config @JvmOverloads constructor(
        // 单位 KB 默认 100 kb 以下不压缩
        val ignoreSize: Int,
        // 是否保留透明通道
        val focusAlpha: Boolean = true,
        // 压缩图片存储路径
        val targetDir: String? = null
    ) {
        // 压缩失败、异常是否结束压缩
        private var mFailFinish = false

        constructor(targetDir: String?) : this(100, true, targetDir)

        constructor(
            ignoreSize: Int,
            targetDir: String?
        ) : this(ignoreSize, true, targetDir)

        // =

        fun isFailFinish(): Boolean {
            return mFailFinish
        }

        fun setFailFinish(failFinish: Boolean): Config {
            mFailFinish = failFinish
            return this
        }
    }

    // =======
    // = 接口 =
    // =======

    /**
     * detail: 压缩回调接口
     * @author Ttt
     */
    interface OnCompressListener {

        /**
         * 开始压缩前调用
         * @param index 当前压缩索引
         * @param count 压缩总数
         */
        fun onStart(
            index: Int,
            count: Int
        )

        /**
         * 压缩成功后调用
         * @param file  压缩成功文件
         * @param index 当前压缩索引
         * @param count 压缩总数
         */
        fun onSuccess(
            file: File?,
            index: Int,
            count: Int
        )

        /**
         * 当压缩过程出现问题时触发
         * @param error 异常信息
         * @param index 当前压缩索引
         * @param count 压缩总数
         */
        fun onError(
            error: Throwable,
            index: Int,
            count: Int
        )

        /**
         * 压缩完成 ( 压缩结束 )
         * @param lists 压缩成功存储 List
         * @param maps  每个索引对应压缩存储地址
         * @param count 压缩总数
         */
        fun onComplete(
            lists: List<File>,
            maps: Map<Int, File?>,
            count: Int
        )
    }
}