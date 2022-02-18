package afkt.project.feature.ui_effect.recy_adapter.adapter_multitype.adapter

import afkt.project.R
import afkt.project.databinding.AdapterConcatShapeableImageBinding
import afkt.project.feature.ui_effect.recy_adapter.ShapeableImageBeanItem
import android.view.LayoutInflater
import android.view.ViewGroup
import com.drakeet.multitype.ItemViewBinder
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.engine.DevEngine
import dev.utils.app.ResourceUtils

/**
 * detail: ShapeableImage Adapter
 * @author Ttt
 */
class ShapeableImageItemViewBinder : ItemViewBinder<ShapeableImageBeanItem, DevBaseViewBindingVH<AdapterConcatShapeableImageBinding>>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): DevBaseViewBindingVH<AdapterConcatShapeableImageBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_concat_shapeable_image)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterConcatShapeableImageBinding>,
        item: ShapeableImageBeanItem
    ) {
        val itemObj = item.obj

        DevEngine.getImage()?.display(
            holder.binding.vidIv,
            itemObj.imageUrl
        )

        when (itemObj.type) {
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
}