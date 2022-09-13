package dev.capture.base

import android.app.Activity
import android.os.Bundle
import dev.capture.UtilsCompiler

/**
 * detail: DevHttpCapture Base Activity
 * @author Ttt
 */
open class BaseDevHttpActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 添加 Activity
        UtilsCompiler.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 移除 Activity
        UtilsCompiler.removeActivity(this)
    }
}