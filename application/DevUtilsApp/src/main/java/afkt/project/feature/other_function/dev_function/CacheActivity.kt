package afkt.project.feature.other_function.dev_function

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.base.project.ext.bindAdapter
import afkt.project.data_model.button.ButtonList.cacheButtonValues
import afkt.project.data_model.button.ButtonValue
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import com.therouter.router.Route
import dev.utils.app.PathUtils
import dev.utils.app.cache.DevCache
import dev.utils.app.toast.ToastTintUtils
import utils_use.cache.CacheUse
import java.io.Serializable

/**
 * detail: DevCache 缓存工具类
 * @author Ttt
 * [CacheUse]
 */
@Route(path = RouterPath.OTHER_FUNCTION.CacheActivity_PATH)
class CacheActivity : BaseProjectActivity<BaseViewRecyclerviewBinding, BaseProjectViewModel>(
    R.layout.base_view_recyclerview, simple_Agile = {
        if (it is CacheActivity) {
            it.apply {
                binding.vidRv.bindAdapter(cacheButtonValues) { buttonValue ->
                    // 获取字符串
                    val str: String?
                    when (buttonValue.type) {
                        ButtonValue.BTN_CACHE_STRING -> {
                            DevCache.newCache().put("str", "这是字符串", -1)
                            showToast(true, "存储字符串成功")
                        }

                        ButtonValue.BTN_CACHE_STRING_TIME -> {
                            DevCache.newCache().put("str", "这是有效期 3 秒字符串", 3)
                            showToast(true, "存储有效期字符串成功")
                        }

                        ButtonValue.BTN_CACHE_STRING_GET -> {
                            str = DevCache.newCache().getString("str")
                            showToast(str != null, str, "未存储 key 为 str 的字符串")
                        }

                        ButtonValue.BTN_CACHE_BEAN -> {
                            DevCache.newCache().put("bean", CacheVo("这是实体类"), -1)
                            showToast(true, "存储实体类成功")
                        }

                        ButtonValue.BTN_CACHE_BEAN_TIME -> {
                            DevCache.newCache().put("bean", CacheVo("这是有效期 3 秒实体类"), 3)
                            showToast(true, "存储有效期实体类成功")
                        }

                        ButtonValue.BTN_CACHE_BEAN_GET -> {
                            val obj = DevCache.newCache().getSerializable("bean")
                            str = if (obj != null) (obj as CacheVo).toString() else null
                            showToast(obj != null, str, "未存储 key 为 bean 的实体类")
                        }

                        ButtonValue.BTN_CACHE_FILE -> {
                            // 保存到指定文件夹下
                            DevCache.newCache(
                                PathUtils.getSDCard().getSDCardFile("DevCache").absolutePath
                            ).put("fileStr", "这是指定位置字符串", -1)
                            showToast(true, "存储到指定位置成功")
                        }

                        ButtonValue.BTN_CACHE_FILE_GET -> {
                            str = DevCache.newCache(
                                PathUtils.getSDCard().getSDCardFile("DevCache").absolutePath
                            ).getString("fileStr")
                            showToast(str != null, str, "未存储 key 为 fileStr 的字符串")
                        }

                        ButtonValue.BTN_CACHE_CLEAR -> {
                            DevCache.newCache().clear()
                            DevCache.newCache(PathUtils.getSDCard().getSDCardPath("DevCache"))
                                .clear()
                            showToast(true, "清除全部数据成功")
                        }

                        else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
                    }
                }
            }
        }
    }
) {

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
}