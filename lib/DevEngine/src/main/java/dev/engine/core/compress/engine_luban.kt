package dev.engine.core.compress

import dev.engine.compress.ICompressEngine
import dev.engine.compress.listener.CompressFilter
import dev.engine.compress.listener.OnCompressListener
import dev.engine.compress.listener.OnRenameListener
import top.zibin.luban.CompressionPredicate
import java.io.File

/**
 * detail: Luban Image Compress Engine 实现
 * @author Ttt
 */
open class LubanEngineImpl : ICompressEngine<CompressConfig> {

    /**
     * 压缩方法
     * @param data 待压缩图片
     * @param config 压缩配置参数
     * @param compressListener 压缩回调接口
     * @return `true` success, `false` fail
     */
    override fun compress(
        data: Any?,
        config: CompressConfig?,
        compressListener: OnCompressListener?
    ): Boolean {
        return compress(data, config, null, null, compressListener)
    }

    /**
     * 压缩方法
     * @param data 待压缩图片
     * @param config 压缩配置参数
     * @param filter 开启压缩条件
     * @param renameListener 压缩前重命名接口
     * @param compressListener 压缩回调接口
     * @return `true` success, `false` fail
     */
    override fun compress(
        data: Any?,
        config: CompressConfig?,
        filter: CompressFilter?,
        renameListener: OnRenameListener?,
        compressListener: OnCompressListener?
    ): Boolean {
        if (data == null || config == null || compressListener == null) return false
        return compress(
            mutableListOf<Any?>(data), config,
            filter, renameListener, compressListener
        )
    }

    /**
     * 压缩方法
     * @param lists 待压缩图片集合
     * @param config 压缩配置参数
     * @param compressListener 压缩回调接口
     * @return `true` success, `false` fail
     */
    override fun compress(
        lists: MutableList<*>?,
        config: CompressConfig?,
        compressListener: OnCompressListener?
    ): Boolean {
        return compress(lists, config, null, null, compressListener)
    }

    /**
     * 压缩方法
     * @param lists 待压缩图片集合
     * @param config 压缩配置参数
     * @param filter 开启压缩条件
     * @param renameListener 压缩前重命名接口
     * @param compressListener 压缩回调接口
     * @return `true` success, `false` fail
     */
    override fun compress(
        lists: MutableList<*>?,
        config: CompressConfig?,
        filter: CompressFilter?,
        renameListener: OnRenameListener?,
        compressListener: OnCompressListener?
    ): Boolean {
        if (lists == null || config == null || compressListener == null) return false
        var predicate: CompressionPredicate? = null
        if (filter != null) {
            predicate = CompressionPredicate { path: String? -> filter.apply(path) }
        }
        var rename: top.zibin.luban.OnRenameListener? = null
        if (renameListener != null) {
            rename = top.zibin.luban.OnRenameListener { filePath: String? ->
                renameListener.rename(filePath)
            }
        }
        return LubanUtils.compress(
            lists, LubanUtils.Config(
                config.ignoreSize, config.focusAlpha, config.targetDir
            ).setFailFinish(config.isFailFinish()), predicate, rename,
            object : LubanUtils.OnCompressListener {
                /**
                 * 开始压缩前调用
                 * @param index 当前压缩索引
                 * @param count 压缩总数
                 */
                override fun onStart(
                    index: Int,
                    count: Int
                ) {
                    compressListener.onStart(index, count)
                }

                /**
                 * 压缩成功后调用
                 * @param file 压缩成功文件
                 * @param index 当前压缩索引
                 * @param count 压缩总数
                 */
                override fun onSuccess(
                    file: File?,
                    index: Int,
                    count: Int
                ) {
                    compressListener.onSuccess(file, index, count)
                }

                /**
                 * 当压缩过程出现问题时触发
                 * @param error 异常信息
                 * @param index 当前压缩索引
                 * @param count 压缩总数
                 */
                override fun onError(
                    error: Throwable,
                    index: Int,
                    count: Int
                ) {
                    compressListener.onError(error, index, count)
                }

                /**
                 * 压缩完成 ( 压缩结束 )
                 * @param lists 压缩成功存储 List
                 * @param maps 每个索引对应压缩存储地址
                 * @param count 压缩总数
                 */
                override fun onComplete(
                    lists: List<File>,
                    maps: Map<Int, File?>,
                    count: Int
                ) {
                    compressListener.onComplete(lists, maps, count)
                }
            }
        )
    }
}