package afkt.project.ui.adapter.multitype

import afkt.project.R
import afkt.project.databinding.AdapterConcatShapeableImageBinding
import afkt.project.model.bean.ShapeableImageBeanItem
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
            holder.binding.vidAcsiIgview,
            itemObj.imageUrl
        )

        when (itemObj.type) {
            1 -> { // 圆形
                holder.binding.vidAcsiIgview.shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setAllCorners(RoundedCornerTreatment())
                    .setAllCornerSizes(RelativeCornerSize(0.5f))
                    .build()
            }
            2 -> { // 圆角
                holder.binding.vidAcsiIgview.shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setAllCorners(
                        CornerFamily.ROUNDED,
                        ResourceUtils.getDimension(R.dimen.un_dp_30)
                    )
                    .build()
            }
            3 -> { // 水滴形
                holder.binding.vidAcsiIgview.shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setAllCorners(
                        CornerFamily.ROUNDED,
                        ResourceUtils.getDimension(R.dimen.un_dp_25)
                    )
                    .setTopRightCornerSize(RelativeCornerSize(0.7f))
                    .setTopLeftCornerSize(RelativeCornerSize(0.7f))
                    .build()
            }
            4 -> { // 叶子形状
                holder.binding.vidAcsiIgview.shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setTopRightCorner(CornerFamily.ROUNDED, RelativeCornerSize(0.5f))
                    .setBottomLeftCorner(CornerFamily.ROUNDED, RelativeCornerSize(0.5f))
                    .build()
            }
        }
    }
}