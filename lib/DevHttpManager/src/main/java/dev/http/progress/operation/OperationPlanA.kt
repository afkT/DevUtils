package dev.http.progress.operation

import dev.http.progress.Progress
import dev.http.progress.ProgressOperation

/**
 * detail: Progress Operation 实现方式一
 * @author Ttt
 * 实现方式差异可以查看 [ProgressOperation] 类注释
 */
internal class OperationPlanA constructor(
    key: String,
    // 全局默认操作对象
    globalDefault: Boolean,
    // 内部拦截器监听类型
    type: Int
) : BaseOperation(key, globalDefault, type) {

    // =================
    // = BaseOperation =
    // =================

    override fun getPlanCallback(extras: Progress.Extras?): Progress.Callback {
        TODO("Not yet implemented")
    }

    override fun addListener(
        isRequest: Boolean,
        url: String,
        callback: Progress.Callback
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun clearListener(
        isRequest: Boolean,
        url: String
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun clearListener(
        isRequest: Boolean,
        progress: Progress?
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeListener(
        isRequest: Boolean,
        url: String,
        callback: Progress.Callback
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeListener(
        isRequest: Boolean,
        progress: Progress?,
        callback: Progress.Callback
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeRecycleList(
        progress: Progress,
        recycleList: List<Progress.Callback>
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCallbackList(progress: Progress): Array<Progress.Callback?> {
        TODO("Not yet implemented")
    }
}