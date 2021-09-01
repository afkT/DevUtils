package afkt.project.ui.adapter.multitype

import afkt.project.R
import afkt.project.databinding.AdapterConcatBannerBinding
import afkt.project.databinding.AdapterConcatBannerImageBinding
import afkt.project.model.bean.BannerBean
import afkt.project.model.bean.BannerBeanItem
import afkt.project.util.ProjectUtils
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
import dev.engine.image.DevImageEngine

/**
 * detail: Banner Adapter
 * @author Ttt
 */
class BannerItemViewBinder(
    val owner: LifecycleOwner
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
        holder.binding.vidAcbBanner.setAdapter(
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
                    DevImageEngine.getEngine()?.display(
                        holder.binding.vidAcbiIgview,
                        data.imageUrl,
                        ProjectUtils.roundConfig10
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