package afkt.project.feature.ui_effect.recy_adapter.adapter_multitype.adapter

import afkt.project.R
import afkt.project.base.IMAGE_ROUND_10
import afkt.project.databinding.AdapterConcatBannerBinding
import afkt.project.databinding.AdapterConcatBannerImageBinding
import afkt.project.feature.ui_effect.recy_adapter.BannerBean
import afkt.project.feature.ui_effect.recy_adapter.BannerBeanItem
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.indicator.CircleIndicator
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.expand.engine.image.display
import dev.mvvm.utils.image.toImageConfig
import dev.mvvm.utils.toSource

/**
 * detail: Banner Adapter
 * @author Ttt
 */
class BannerItemViewBinder(
    private val owner: LifecycleOwner
) : ItemViewBinder<BannerBeanItem, DevBaseViewBindingVH<AdapterConcatBannerBinding>>() {

    var context: Context? = null

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): DevBaseViewBindingVH<AdapterConcatBannerBinding> {
        context = parent.context
        return newBindingViewHolder(parent, R.layout.adapter_concat_banner)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterConcatBannerBinding>,
        item: BannerBeanItem
    ) {
        holder.binding.vidBanner.setAdapter(
            object : BannerAdapter<BannerBean, BannerViewHolder>(item.obj) {
                override fun onCreateHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): BannerViewHolder {
                    return BannerViewHolder(
                        AdapterConcatBannerImageBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent, false
                        )
                    )
                }

                override fun onBindView(
                    holder: BannerViewHolder,
                    data: BannerBean,
                    position: Int,
                    size: Int
                ) {
                    holder.binding.vidIv.display(
                        source = data.imageUrl.toSource(),
                        config = IMAGE_ROUND_10.toImageConfig()
                    )
                }
            }
        ).addBannerLifecycleObserver(owner)
            .indicator = CircleIndicator(context)
    }

    class BannerViewHolder(val binding: AdapterConcatBannerImageBinding) : RecyclerView.ViewHolder(
        binding.root
    )
}