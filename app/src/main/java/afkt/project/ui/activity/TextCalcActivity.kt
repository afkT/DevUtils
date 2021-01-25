package afkt.project.ui.activity;

import android.graphics.Color;
import android.view.View;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityTextCalcBinding;
import dev.base.widget.BaseTextView;
import dev.utils.app.TextViewUtils;
import dev.utils.app.helper.QuickHelper;
import dev.utils.app.toast.ToastTintUtils;
import dev.utils.common.ChineseUtils;
import dev.utils.common.RandomUtils;

/**
 * detail: 计算字体宽度、高度
 * @author Ttt
 */
public class TextCalcActivity
        extends BaseActivity<ActivityTextCalcBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.activity_text_calc;
    }

    @Override
    public void initValue() {
        super.initValue();

        for (int i = 0; i < 15; i++) {
            // 随机字符串
            String text       = ChineseUtils.randomWord(RandomUtils.getRandom(100)) + RandomUtils.getRandomLetters(RandomUtils.getRandom(20));
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
                            String       text     = textView.getText().toString();

                            StringBuilder builder = new StringBuilder();
                            builder.append("字体总数: ").append(text.length());
                            builder.append("\n字体高度: ").append(TextViewUtils.getTextHeight(textView));
                            builder.append("\n偏移高度: ").append(TextViewUtils.getTextTopOffsetHeight(textView));
                            builder.append("\n字体宽度: ").append(TextViewUtils.getTextWidth(textView));
                            builder.append("\n字体大小: ").append(textView.getTextSize());
                            builder.append("\n计算字体大小: ").append(TextViewUtils.reckonTextSizeByHeight(TextViewUtils.getTextHeight(textView)));
                            builder.append("\n计算行数: ").append(TextViewUtils.calcTextLine(textView, textView.getMeasuredWidth()));

                            String content = builder.toString();
                            ToastTintUtils.normal(content);
                        }
                    }).getView();
            binding.vidAtcLinear.addView(view);
        }
    }
}