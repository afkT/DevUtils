package afkt.project.ui.widget.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

/**
 * detail: RecyclerView 分割线
 * @author Ttt
 * 使用: [RecyclerView.addItemDecoration]
 * <p></p>
 * 也可以使用内置 [DividerItemDecoration]
 * 自定义分割线使用方法
 * DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
 * decoration.setDrawable(Drawable)
 * recyclerView.addItemDecoration(decoration)
 * <p></p>
 * 如果不想要最后一条数据添加分割线可以复制 [DividerItemDecoration] 代码修改
 * for (int i = 0; i < childCount; i++) 改为 for (int i = 0; i < childCount - 1; i++)
 */
class LineItemDecoration(
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
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.set(0, 0, 0, 0)
        } else {
            outRect.set(0, lineHeight.toInt(), 0, 0)
        }
    }

    override fun onDrawOver(
        canvas: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.onDrawOver(canvas, parent, state)
        val childCount = parent.childCount
        var child: View
        for (i in 0 until childCount) {
            child = parent.getChildAt(i)
            if (parent.getChildAdapterPosition(child) != 0) {
                canvas.drawRect(
                    child.left.toFloat(),
                    child.top - lineHeight,
                    child.right.toFloat(),
                    child.top.toFloat(),
                    paint
                )
            }
        }
    }
}