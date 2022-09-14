package dev.capture.base

import android.app.Activity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import dev.capture.UtilsCompiler

/**
 * detail: DevHttpCapture Base Activity
 * @author Ttt
 */
abstract class BaseDevHttpActivity<VB : ViewBinding> : Activity() {

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = createBinding()
        // 添加 Activity
        UtilsCompiler.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 移除 Activity
        UtilsCompiler.removeActivity(this)
    }

    override fun onBackPressed() {
        finishOperate()
    }

    // ============
    // = abstract =
    // ============

    abstract fun createBinding(): VB

    abstract fun finishOperate()
}