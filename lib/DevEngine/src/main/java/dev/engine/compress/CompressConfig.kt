package dev.engine.compress

/**
 * detail: Image Compress Config
 * @author Ttt
 */
class CompressConfig @JvmOverloads constructor(
    // 单位 KB 默认 100 kb 以下不压缩
    val ignoreSize: Int,
    // 是否保留透明通道
    val focusAlpha: Boolean = true,
    // 压缩图片存储路径
    val targetDir: String? = null
) : ICompressEngine.EngineConfig() {

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

    fun setFailFinish(failFinish: Boolean): CompressConfig {
        mFailFinish = failFinish
        return this
    }
}