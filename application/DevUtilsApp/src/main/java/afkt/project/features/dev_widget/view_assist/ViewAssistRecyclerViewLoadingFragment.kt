package afkt.project.features.dev_widget.view_assist

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.AdapterItemRecyclerLoadingBinding
import afkt.project.databinding.FragmentOtherFunctionViewAssistRecyclerBinding
import afkt.project.model.helper.RandomHelper
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.ViewDataBinding
import dev.base.DevSource
import dev.engine.extensions.image.display
import dev.engine.image.listener.DrawableListener
import dev.simple.core.adapter.AdapterModel
import dev.simple.extensions.toSource
import dev.widget.assist.ViewAssist
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * detail: ViewAssist RecyclerView ( loading )
 * @author Ttt
 */
class ViewAssistRecyclerViewLoadingFragment : AppFragment<FragmentOtherFunctionViewAssistRecyclerBinding, ViewAssistRecyclerViewLoadingViewModel>(
    R.layout.fragment_other_function_view_assist_recycler, BR.viewModel
)

class ViewAssistRecyclerViewLoadingViewModel : AppViewModel() {

    val adapter = RecyclerLoadingAdapter()

    val adapterModel = RecyclerLoadingAdapterModel().apply {
        val lists = mutableListOf<String>()
        for (i in 0..19) {
            lists.add(RandomHelper.randomImage1080x1920(500))
        }
        addAll(lists)
    }
}

class RecyclerLoadingAdapterModel : AdapterModel<String>() {

    // Item Binding
    val itemBinding = ItemBinding.of<String>(
        BR.itemValue, R.layout.adapter_item_recycler_loading
    )
}

class RecyclerLoadingAdapter : BindingRecyclerViewAdapter<String>() {

    override fun onBindBinding(
        binding: ViewDataBinding,
        variableId: Int,
        layoutRes: Int,
        position: Int,
        item: String?
    ) {
        super.onBindBinding(binding, variableId, layoutRes, position, item)
        if (binding is AdapterItemRecyclerLoadingBinding) {
            binding.apply {
                val viewAssist = ViewAssist.wrap(vidSkeleton)
                ViewAssistUtils.registerRecyclerLoading(viewAssist) {
                    loadImage(vidIv, viewAssist, item)
                }
                loadImage(vidIv, viewAssist, item)
            }
        }
    }

    private fun loadImage(
        imageView: AppCompatImageView,
        viewAssist: ViewAssist,
        url: String?
    ) {
        viewAssist.showIng()
        // 加载图片
        imageView.display(
            source = url?.toSource(),
            listener = object : DrawableListener() {
                override fun onStart(source: DevSource) {}
                override fun onResponse(
                    source: DevSource,
                    value: Drawable
                ) {
                    viewAssist.showSuccess()
                }

                override fun onFailure(
                    source: DevSource,
                    throwable: Throwable
                ) {
                    viewAssist.showFailed()
                }
            }
        )
    }
}