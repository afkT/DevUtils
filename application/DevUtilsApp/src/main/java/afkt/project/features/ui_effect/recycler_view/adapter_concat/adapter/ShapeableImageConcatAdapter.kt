package afkt.project.features.ui_effect.recycler_view.adapter_concat.adapter

import afkt.project.R
import afkt.project.databinding.AdapterConcatShapeableImageBinding
import afkt.project.features.ui_effect.recycler_view.adapter_concat.ShapeableImageBean
import android.view.ViewGroup
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.expand.engine.image.display
import dev.mvvm.utils.toSource
import dev.utils.app.ResourceUtils

/**
 * detail: ShapeableImage Adapter
 * @author Ttt
 */
class ShapeableImageConcatAdapter(data: List<ShapeableImageBean>) :
    DevDataAdapter<ShapeableImageBean, DevBaseViewBindingVH<AdapterConcatShapeableImageBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterConcatShapeableImageBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_concat_shapeable_image)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterConcatShapeableImageBinding>,
        position: Int
    ) {
        val item = getDataItem(position)
        holder.binding.vidIv.display(
            source = item.imageUrl.toSource(),
        )

        when (item.type) {
            1 -> { // 圆形
                holder.binding.vidIv.shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setAllCorners(RoundedCornerTreatment())
                    .setAllCornerSizes(RelativeCornerSize(0.5F))
                    .build()
            }

            2 -> { // 圆角
                holder.binding.vidIv.shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setAllCorners(
                        CornerFamily.ROUNDED,
                        ResourceUtils.getDimension(R.dimen.dp_30)
                    )
                    .build()
            }

            3 -> { // 水滴形
                holder.binding.vidIv.shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setAllCorners(
                        CornerFamily.ROUNDED,
                        ResourceUtils.getDimension(R.dimen.dp_25)
                    )
                    .setTopRightCornerSize(RelativeCornerSize(0.7F))
                    .setTopLeftCornerSize(RelativeCornerSize(0.7F))
                    .build()
            }

            4 -> { // 叶子形状
                holder.binding.vidIv.shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setTopRightCorner(CornerFamily.ROUNDED, RelativeCornerSize(0.5F))
                    .setBottomLeftCorner(CornerFamily.ROUNDED, RelativeCornerSize(0.5F))
                    .build()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.adapter_concat_shapeable_image
    }
}