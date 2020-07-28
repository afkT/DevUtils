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
import dev.utils.app.helper.QuickHelper;
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

            BaseTextView view = QuickHelper.get(new BaseTextView(this))
                    .setPadding(30)
                    .setMarginTop(40)
                    .setMarginBottom(20)
                    .setTextColor(Color.BLACK)
                    .setTextSizeBySp(RandomUtils.getRandom(13, 20))
                    .setBold(RandomUtils.nextBoolean())
                    .setText(randomText).setOnClicks(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BaseTextView textView = (BaseTextView) v;
                            String text = textView.getText().toString();

                            StringBuilder builder = new StringBuilder();
                            builder.append("字体总数: " + text.length());
                            builder.append("\n字体高度: " + TextViewUtils.getTextHeight(textView));
                            builder.append("\n偏移高度: " + TextViewUtils.getTextTopOffsetHeight(textView));
                            builder.append("\n字体宽度: " + TextViewUtils.getTextWidth(textView));
                            builder.append("\n字体大小: " + textView.getTextSize());
                            builder.append("\n计算字体大小: " + TextViewUtils.reckonTextSizeByHeight(TextViewUtils.getTextHeight(textView)));
                            builder.append("\n计算行数: " + TextViewUtils.calcTextLine(textView, textView.getMeasuredWidth()));

                            String content = builder.toString();
                            ToastTintUtils.normal(content);
                        }
                    }).getView();
            vid_atc_linear.addView(view);
        }
    }
}