package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEngineCacheBinding
import android.view.View
import dev.engine.extensions.cache.*
import dev.engine.extensions.toast.toast_showShort

/**
 * detail: Cache Engine 有效期键值对缓存
 * @author Ttt
 */
class CacheFragment : AppFragment<FragmentDevEngineCacheBinding, CacheViewModel>(
    R.layout.fragment_dev_engine_cache, BR.viewModel
)

/**
 * detail: Cache Engine 有效期键值对缓存 ViewModel
 * @author Ttt
 */
class CacheViewModel : AppViewModel() {

    private companion object {

        const val KEY = "cache_key_string"

        // 有效期 5 秒
        const val VALID_TIME = 5000L
    }

    val clickPut = View.OnClickListener {
        val result = KEY.cache_put(value = "DevUtils 缓存内容", validTime = VALID_TIME)
        toast_showShort(text = "put ( 有效期 5 秒 ): $result")
    }

    val clickGet = View.OnClickListener {
        val value = KEY.cache_getString()
        toast_showShort(text = "getString: $value")
    }

    val clickContains = View.OnClickListener {
        val result = KEY.cache_contains()
        toast_showShort(text = "contains: $result")
    }

    val clickIsDue = View.OnClickListener {
        val result = KEY.cache_isDue()
        toast_showShort(text = "isDue 是否过期: $result")
    }

    val clickInfo = View.OnClickListener {
        toast_showShort(text = "count: ${cache_getCount()}, size: ${cache_getSize()}")
    }

    val clickClearDue = View.OnClickListener {
        cache_clearDue()
        toast_showShort(text = "clearDue 清除过期缓存完成")
    }

    val clickClear = View.OnClickListener {
        cache_clear()
        toast_showShort(text = "clear 清空全部完成")
    }
}