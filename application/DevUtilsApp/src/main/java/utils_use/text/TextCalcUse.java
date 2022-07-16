package utils_use.text;

import static dev.kotlin.engine.log.LogKt.log_dTag;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import dev.utils.app.TextViewUtils;

/**
 * detail: 计算字体宽度、高度
 * @author Ttt
 */
public final class TextCalcUse {

    private TextCalcUse() {
    }

    // 日志 TAG
    private static final String TAG = TextCalcUse.class.getSimpleName();

    /**
     * 计算字体宽度、高度
     */
    protected void textCalcUse(Context context) {
        LinearLayout vid_linear = null;
        // 打印信息
        for (int i = 0, len = vid_linear.getChildCount(); i < len; i++) {
            View view = vid_linear.getChildAt(i);
            if (view instanceof TextView) {
                printInfo((TextView) view);
            }
        }

//        // 计算第几位超过宽度 (600)
//        int pos = TextViewUtils.calcTextWidth(vid_tv.getPaint(), "测试内容", 600);

        TextView tv = new TextView(context);
        // 获取字体高度
        TextViewUtils.getTextHeight(tv);
        // 获取字体大小
        TextViewUtils.reckonTextSizeByHeight(90); // 获取字体高度为 90 的字体大小
    }

    // =

    /**
     * 打印信息
     */
    private void printInfo(TextView textView) {
        StringBuilder builder = new StringBuilder();
        builder.append("内容: ").append(textView.getText().toString());
        builder.append("\n字体总数: ").append(textView.getText().length());
        builder.append("\n字体高度: ").append(TextViewUtils.getTextHeight(textView));
        builder.append("\n偏移高度: ").append(TextViewUtils.getTextTopOffsetHeight(textView));
        builder.append("\n字体宽度: ").append(TextViewUtils.getTextWidth(textView));
        builder.append("\n字体大小: ").append(textView.getTextSize());
        builder.append("\n计算字体大小: ").append(TextViewUtils.reckonTextSizeByHeight(TextViewUtils.getTextHeight(textView)));
        builder.append("\n计算行数: ").append(TextViewUtils.calcTextLine(textView, textView.getMeasuredWidth()));
        // 打印日志
        log_dTag(TAG, null, builder.toString());
    }
}