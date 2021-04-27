package afkt.project.ui.widget.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

/**
 * detail: RecyclerView 分割线 ( 在结尾添加分割线 )
 * @author Ttt
 */
class LastLineItemDecoration(
    private val lineHeight: Float,
    @ColorInt val lineColor: Int
) : RecyclerView.ItemDecoration() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = lineColor
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        parent.adapter?.itemCount?.let { count ->
            var position = parent.getChildAdapterPosition(view)
            if (position == count - 1) {
                outRect.set(0, 0, 0, lineHeight.toInt())
            } else {
                outRect.set(0, 0, 0, 0)
            }
        }
    }

    override fun onDrawOver(
        canvas: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.onDrawOver(canvas, parent, state)
        parent.adapter?.itemCount?.let { count ->
            if (count - 1 >= 0) {
                var child = parent.getChildAt(count - 1)
                var position = parent.getChildAdapterPosition(child)
                if (position == count - 1) {
                    canvas.drawRect(
                        child.left.toFloat(),
                        child.top.toFloat(),
                        child.right.toFloat(),
                        child.bottom.toFloat() + lineHeight,
                        paint
                    )
                }
            }
        }
    }
}