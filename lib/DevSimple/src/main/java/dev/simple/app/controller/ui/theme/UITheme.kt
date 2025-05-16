package dev.simple.app.controller.ui.theme

import android.content.Intent
import android.os.Bundle

// =================================
// = App Activity、Fragment 通用样式 =
// =================================

/**
 * detail: App Activity UI 样式控制
 * @author Ttt
 */
open class ActivityUITheme private constructor() : BaseUITheme<ActivityUITheme>() {

    override fun returnT(): ActivityUITheme {
        return this
    }

    init {
        defaultActivityUITheme()
    }

    // ==========
    // = 静态方法 =
    // ==========

    companion object {

        /**
         * 创建 ActivityUITheme
         * @return [ActivityUITheme]
         */
        fun with(): ActivityUITheme {
            return ActivityUITheme()
        }

        /**
         * 创建 ActivityUITheme
         * @param intent [Intent]
         * @return [ActivityUITheme]
         */
        fun with(intent: Intent?): ActivityUITheme {
            return ActivityUITheme().reader(intent)
        }

        /**
         * 创建 ActivityUITheme
         * @param bundle [Bundle]
         * @return [ActivityUITheme]
         */
        fun with(bundle: Bundle?): ActivityUITheme {
            return ActivityUITheme().reader(bundle)
        }
    }
}

/**
 * detail: App Fragment UI 样式控制
 * @author Ttt
 */
open class FragmentUITheme private constructor() : BaseUITheme<FragmentUITheme>() {

    override fun returnT(): FragmentUITheme {
        return this
    }

    init {
        defaultFragmentUITheme()
    }

    // ==========
    // = 静态方法 =
    // ==========

    companion object {

        /**
         * 创建 FragmentUITheme
         * @return [FragmentUITheme]
         */
        fun with(): FragmentUITheme {
            return FragmentUITheme()
        }

        /**
         * 创建 FragmentUITheme
         * @param intent [Intent]
         * @return [FragmentUITheme]
         */
        fun with(intent: Intent?): FragmentUITheme {
            return FragmentUITheme().reader(intent)
        }

        /**
         * 创建 FragmentUITheme
         * @param bundle [Bundle]
         * @return [FragmentUITheme]
         */
        fun with(bundle: Bundle?): FragmentUITheme {
            return FragmentUITheme().reader(bundle)
        }
    }
}