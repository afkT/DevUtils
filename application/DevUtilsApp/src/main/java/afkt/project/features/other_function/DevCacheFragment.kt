package afkt.project.features.other_function

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentOtherFunctionDevCacheBinding
import android.view.View
import dev.expand.engine.toast.toast_showShort
import dev.utils.app.PathUtils
import dev.utils.app.cache.DevCache
import java.io.Serializable

/**
 * detail: DevCache 缓存工具类
 * @author Ttt
 */
class DevCacheFragment : AppFragment<FragmentOtherFunctionDevCacheBinding, DevCacheViewModel>(
    R.layout.fragment_other_function_dev_cache, BR.viewModel
)

/**
 * detail: 缓存实体类
 * @author Ttt
 */
internal class CacheVo(var name: String) : Serializable {

    var time = System.currentTimeMillis()

    constructor(
        name: String,
        time: Long
    ) : this(name) {
        this.time = time
    }

    override fun toString(): String {
        return "name: $name, time: $time"
    }
}

/**
 * [utils_use.cache.CacheUse]
 */
class DevCacheViewModel : AppViewModel() {

    // 获取 DevCache【默认通用】
    private val devCache = DevCache.newCache()

    // 存储字符串
    val clickString = View.OnClickListener { view ->
        devCache.put("str", "这是字符串", -1)
        toast_showShort(text = "存储字符串成功")
    }

    // 存储有效期字符串
    val clickStringTime = View.OnClickListener { view ->
        devCache.put("str", "这是有效期 3 秒字符串", 3000L)
        toast_showShort(text = "存储有效期字符串成功")
    }

    // 获取字符串
    val clickStringGet = View.OnClickListener { view ->
        val value = devCache.getString("str")
        if (value != null) {
            toast_showShort(text = "key[str]，value：${value}")
        } else {
            toast_showShort(text = "未存储 key 为 str 的字符串")
        }
    }

    // 存储实体类
    val clickBean = View.OnClickListener { view ->
        devCache.put("bean", CacheVo("这是实体类"), -1)
        toast_showShort(text = "存储实体类成功")
    }

    // 存储有效期实体类
    val clickBeanTime = View.OnClickListener { view ->
        devCache.put("bean", CacheVo("这是有效期 3 秒实体类"), 3000L)
        toast_showShort(text = "存储有效期实体类成功")
    }

    // 获取实体类
    val clickBeanGet = View.OnClickListener { view ->
        val obj = devCache.getSerializable("bean")
        val value = if (obj != null) (obj as CacheVo).toString() else null
        if (value != null) {
            toast_showShort(text = "key[bean]，value：${value}")
        } else {
            toast_showShort(text = "未存储 key 为 bean 的实体类")
        }
    }

    // 存储到指定位置
    val clickFile = View.OnClickListener { view ->
        // 保存到指定文件夹下
        devCacheCustomPath.put("fileStr", "这是指定位置字符串", -1)
        toast_showShort(text = "存储到指定位置成功")
    }

    // 获取指定位置缓存数据
    val clickFileGet = View.OnClickListener { view ->
        val value = devCacheCustomPath.getString("fileStr")
        if (value != null) {
            toast_showShort(text = "key[fileStr]，value：${value}")
        } else {
            toast_showShort(text = "未存储 key 为 fileStr 的实体类")
        }
    }

    // 清除全部数据
    val clickClear = View.OnClickListener { view ->
        devCache.clear()
        devCacheCustomPath.clear()
        toast_showShort(text = "清除全部数据成功")
    }

    // 自定义存储路径
    private var devCacheCustomPath = DevCache.newCache(
        PathUtils.getAppExternal().getAppCachePath("DevCache")
    )
}