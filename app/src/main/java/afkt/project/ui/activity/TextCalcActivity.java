package afkt.project.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.ui.widget.BaseTextView;
import butterknife.BindView;
import dev.temp.ChineseUtils;
import dev.utils.app.TextViewUtils;
import dev.utils.app.helper.ViewHelper;
import dev.utils.app.toast.ToastTintUtils;
import dev.utils.common.RandomUtils;

/**
 * detail: 计算字体宽度、高度
 * @author Ttt
 */
public class TextCalcActivity extends BaseToolbarActivity {

    @BindView(R.id.vid_atc_linear)
    LinearLayout vid_atc_linear;

    @Override
    public int getLayoutId() {
        return R.layout.activity_text_calc;
    }

    @Override
    public void initValues() {
        super.initValues();

        for (int i = 0; i < 15; i++) {
            // 随机字符串
            String text = ChineseUtils.getRandomWord(RandomUtils.getRandom(100)) + RandomUtils.getRandomLetters(RandomUtils.getRandom(20));
            String randomText = RandomUtils.getRandom(text.toCharArray(), text.length());

            BaseTextView baseTextView = new BaseTextView(this);
            ViewHelper.get().setPadding(baseTextView, 30)
                .setMarginTop(baseTextView, 40)
                .setMarginBottom(baseTextView, 20)
                .setTextColor(baseTextView, Color.BLACK)
                .setTextSizeBySp(baseTextView, RandomUtils.getRandom(13, 20))
                .setBold(baseTextView, RandomUtils.nextBoolean())
                .setText(baseTextView, randomText).setOnClicks(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseTextView baseTextView = (BaseTextView) v;
                    String text = baseTextView.getText().toString();

                    StringBuilder builder = new StringBuilder();
                    builder.append("字体总数: " + text.length());
                    builder.append("\n字体高度: " + TextViewUtils.getTextHeight(baseTextView));
                    builder.append("\n偏移高度: " + TextViewUtils.getTextTopOffsetHeight(baseTextView));
                    builder.append("\n字体宽度: " + TextViewUtils.getTextWidth(baseTextView));
                    builder.append("\n字体大小: " + baseTextView.getTextSize());
                    builder.append("\n计算字体大小: " + TextViewUtils.reckonTextSize(TextViewUtils.getTextHeight(baseTextView)));
                    builder.append("\n计算行数: " + TextViewUtils.calcTextLine(baseTextView, text, baseTextView.getMeasuredWidth()));

                    String content = builder.toString();
                    ToastTintUtils.normal(content);
                }
            }, baseTextView);
            vid_atc_linear.addView(baseTextView);
        }
    }
}