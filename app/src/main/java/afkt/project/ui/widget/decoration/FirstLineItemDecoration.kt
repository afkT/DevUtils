package afkt.project.ui.widget.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

/**
 * detail: RecyclerView 分割线 ( 在开头添加分割线 )
 * @author Ttt
 */
class FirstLineItemDecoration(
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
        var position = parent.getChildAdapterPosition(view)
        if (position == 0) {
            outRect.set(0, lineHeight.toInt(), 0, 0)
        } else {
            outRect.set(0, 0, 0, 0)
        }
    }

    override fun onDrawOver(
        canvas: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.onDrawOver(canvas, parent, state)

        var child = parent.getChildAt(0)
        var position = parent.getChildAdapterPosition(child)
        if (position == 0) {
            canvas.drawRect(
                child.left.toFloat(),
                child.top.toFloat() + lineHeight,
                child.right.toFloat(),
                child.bottom.toFloat(),
                paint
            )
        }
    }
}