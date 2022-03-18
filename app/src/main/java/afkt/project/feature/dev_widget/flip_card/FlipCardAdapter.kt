package afkt.project.feature.dev_widget.flip_card

import afkt_replace.core.lib.utils.image.IMAGE_ROUND_10
import afkt_replace.core.lib.utils.image.toImageConfig
import android.content.Context
import android.view.View
import dev.base.DevSource
import dev.base.widget.BaseImageView
import dev.engine.DevEngine
import dev.widget.ui.FlipCardView

/**
 * detail: 翻转卡片适配器
 * @author Ttt
 */
class FlipCardAdapter(val lists: List<DevSource>) : FlipCardView.Adapter {

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun getItemView(
        context: Context?,
        position: Int,
        isFrontView: Boolean
    ): View? {
        context?.let {
            val imageView = BaseImageView(it)
            DevEngine.getImage()?.display(
                imageView, lists[position],
                IMAGE_ROUND_10.toImageConfig()
            )
            return imageView
        }
        return null
    }
}