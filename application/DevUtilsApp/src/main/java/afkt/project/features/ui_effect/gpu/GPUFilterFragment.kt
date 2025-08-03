package afkt.project.features.ui_effect.gpu

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppContext
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectGpuFilterBinding
import afkt.project.model.basic.AdapterModel
import android.graphics.Bitmap
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import dev.expand.engine.log.log_eTag
import dev.simple.app.base.asFragment
import dev.utils.app.RecyclerViewUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.ScreenUtils
import dev.utils.app.activity_result.ActivityResultAssist
import dev.utils.app.assist.DelayAssist
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.app.image.ImageUtils
import dev.widget.decoration.linear.FirstLinearColorItemDecoration
import dev.widget.decoration.linear.LastLinearColorItemDecoration
import me.tatarka.bindingcollectionadapter2.ItemBinding
import java.lang.ref.WeakReference

/**
 * detail: GPU 滤镜效果
 * @author Ttt
 */
class GPUFilterFragment : AppFragment<FragmentUiEffectGpuFilterBinding, GPUFilterViewModel>(
    R.layout.fragment_ui_effect_gpu_filter, BR.viewModel,
    simple_Agile = { frg ->
        frg.asFragment<GPUFilterFragment> {
            viewModel.initializeRecyclerView(binding.vidRv)
            viewModel.adapter.addAllAndClear(GPUFilterItem.FILTER_LISTS)
            // 初始化相册选择图片处理
            viewModel.initializeImageSelect(this)
        }
    }
)

class GPUFilterViewModel : AppViewModel() {

    val adapter = GPUFilterAdapter()

    // 滤镜名
    val filterName = ObservableField<String>()

    // 滤镜后的 Bitmap
    val filterBitmap = ObservableField<Bitmap>()

    // 选择的图片
    var selectBitmap: Bitmap? = null

    // 点击选择图片
    val clickSelect = View.OnClickListener { view ->
        // 设置 MIME 类型为图片，且限制单选
        imageAssist?.launch("image/*")
    }

    // ============
    // = 初始化方法 =
    // ============

    /**
     * 初始化 RecyclerView
     */
    fun initializeRecyclerView(recyclerView: RecyclerView) {
        weakView = WeakReference<RecyclerView>(recyclerView)
        // 设置 ItemDecoration
        QuickHelper.get(recyclerView).removeAllItemDecoration()
            .addItemDecoration(
                FirstLinearColorItemDecoration(
                    false, ScreenUtils.getScreenWidth() * 0.4f
                )
            )
            .addItemDecoration(
                LastLinearColorItemDecoration(
                    false, ScreenUtils.getScreenWidth() * 0.4f
                )
            )
        // 设置 RecyclerView Gravity 效果
        helper.attachToRecyclerView(recyclerView)

        // 获取选中的索引
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 滑动停止，执行滤镜操作
                    delayFilter.post()
                }
            }
        })
    }

    // ==========
    // = 选择图片 =
    // ==========

    // 持有 RecyclerView
    private var weakView = WeakReference<RecyclerView>(null)

    // 相册选择图片回传辅助类
    var imageAssist: ActivityResultAssist<String, Uri?>? = null

    /**
     * 初始化相册选择图片处理
     */
    fun initializeImageSelect(caller: ActivityResultCaller) {
        this.imageAssist = ActivityResultAssist(
            caller, ActivityResultContracts.GetContent()
        ) { result ->
            if (result != null) {
                val stream = ResourceUtils.openInputStream(result)
                selectBitmap = ImageUtils.decodeStream(stream)
                // 设置滤镜
                delayFilter.post()
            }
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    // 延迟滤镜任务
    private val delayFilter = DelayAssist(200L) {
        resetFilter()
    }

    // RecyclerView Gravity Helper
    private val helper = GravitySnapHelper(Gravity.CENTER)

    /**
     * 获取当前选中的 GPUFilter Item
     */
    private fun currentItem(): GPUFilterItem? {
        var position = helper.currentSnappedPosition
        // 第一个和最后一个会为 -1
        if (position == RecyclerView.NO_POSITION) {
            // 最后一条可见 Item 索引
            val lastIndex = RecyclerViewUtils.findLastVisibleItemPosition(
                weakView.get()
            )
            if (lastIndex != -1) {
                // 如果属于最后一条 Item 则返回最后一条索引
                position = if (lastIndex == (adapter.count() - 1)) {
                    lastIndex
                } else {
                    // 如果为原本为 -1 并且最后一条不可见则表示为第一条
                    0
                }
            }
        }
        val item = adapter.getOrNull(position)
        filterName.set(item?.filterName)
        return item
    }

    /**
     * 重置滤镜效果
     */
    private fun resetFilter() {
        try {
            if (selectBitmap == null) return
            // 获取当前选中的 GPUFilter Item
            val filterItem = currentItem()
            if (filterItem == null) return
            // 根据类型创建滤镜
            val gpuFilter = if (filterItem.isFilterType()) {
                filterItem.filterType.createGPUImageFilter()
            } else {
                GPUFilterUtils.getGPUImageToneCurveFilter(
                    ResourceUtils.open(filterItem.acvPath)
                )
            }
            // 设置图片滤镜
            val bitmap = GPUFilterUtils.getFilterBitmap(
                AppContext.context(), selectBitmap, gpuFilter
            )
            filterBitmap.set(bitmap)
        } catch (e: Exception) {
            TAG.log_eTag(
                message = "setFilter",
                throwable = e
            )
        }
    }
}

class GPUFilterAdapter : AdapterModel<GPUFilterItem>() {

    // Item Binding
    val itemBinding = ItemBinding.of<GPUFilterItem>(
        BR.itemValue, R.layout.adapter_item_gpu_filter_text
    )
}

@BindingAdapter("binding_filter_bitmap")
fun ImageView.bindingFilterBitmap(
    filterBitmap: Bitmap?
) {
    setImageBitmap(filterBitmap)
}