package afkt.project.feature.ui_effect.recy_adapter.adapter_concat.adapter

import afkt.project.R
import afkt.project.base.IMAGE_ROUND_10
import afkt.project.databinding.AdapterConcatBannerBinding
import afkt.project.databinding.AdapterConcatBannerImageBinding
import afkt.project.feature.ui_effect.recy_adapter.BannerBean
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.indicator.CircleIndicator
import dev.expand.engine.image.display
import dev.mvvm.utils.image.toImageConfig
import dev.mvvm.utils.toSource

/**
 * detail: Banner Adapter
 * @author Ttt
 */
class BannerConcatAdapter(
    private val owner: LifecycleOwner,
    private val bannerLists: List<BannerBean>
) : RecyclerView.Adapter<BannerConcatAdapter.ItemHolder>() {

    var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemHolder {
        context = parent.context

        return ItemHolder(
            AdapterConcatBannerBinding.inflate(
                LayoutInflater.from(context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ItemHolder,
        position: Int
    ) {
        holder.binding.vidBanner.setAdapter(
            object : BannerAdapter<BannerBean, BannerViewHolder>(bannerLists) {
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

    override fun getItemCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.adapter_concat_banner
    }

    class ItemHolder(val binding: AdapterConcatBannerBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    class BannerViewHolder(val binding: AdapterConcatBannerImageBinding) : RecyclerView.ViewHolder(
        binding.root
    )
}