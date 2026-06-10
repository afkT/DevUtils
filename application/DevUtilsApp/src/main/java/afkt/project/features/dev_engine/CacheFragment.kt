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
        "put ( 有效期 5 秒 ): $result".toast_showShort()
    }

    val clickGet = View.OnClickListener {
        val value = KEY.cache_getString()
        "getString: $value".toast_showShort()
    }

    val clickContains = View.OnClickListener {
        val result = KEY.cache_contains()
        "contains: $result".toast_showShort()
    }

    val clickIsDue = View.OnClickListener {
        val result = KEY.cache_isDue()
        "isDue 是否过期: $result".toast_showShort()
    }

    val clickInfo = View.OnClickListener {
        "count: ${cache_getCount()}, size: ${cache_getSize()}".toast_showShort()
    }

    val clickClearDue = View.OnClickListener {
        cache_clearDue()
        "clearDue 清除过期缓存完成".toast_showShort()
    }

    val clickClear = View.OnClickListener {
        cache_clear()
        "clear 清空全部完成".toast_showShort()
    }
}