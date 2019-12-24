package afkt.project.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * detail: Base TextView
 * @author Ttt
 * <pre>
 *     便于全局控制, 替换字体、样式等
 * </pre>
 */
public class BaseTextView extends AppCompatTextView {

    public BaseTextView(Context context) {
        super(context);
    }

    public BaseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
