package afkt.project.ui.activity;

import android.graphics.Color;
import android.view.View;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityLineBinding;
import dev.utils.app.helper.QuickHelper;
import dev.utils.common.ChineseUtils;
import dev.utils.common.RandomUtils;
import dev.widget.function.LineTextView;

/**
 * detail: 自动换行 View
 * @author Ttt
 */
public class LineActivity extends BaseActivity<ActivityLineBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.activity_line;
    }

    @Override
    public void initValue() {
        super.initValue();

        // 设置监听
        binding.vidAlContentTv.setNewLineCallback(new LineTextView.OnNewLineCallback() {
            @Override
            public void onNewLine(
                    boolean isNewLine,
                    int line
            ) {
                StringBuilder builder = new StringBuilder();
                builder.append("是否换行: ").append(isNewLine);
                builder.append("\n换行数量: ").append(line);
                binding.vidAlTv.setText(builder.toString());
            }
        });
        binding.vidAlContentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 随机字符串
                String text       = ChineseUtils.randomWord(RandomUtils.getRandom(300)) + RandomUtils.getRandomLetters(RandomUtils.getRandom(50));
                String randomText = RandomUtils.getRandom(text.toCharArray(), text.length());
                // 设置内容
                QuickHelper.get(binding.vidAlContentTv)
                        .setTextColor(Color.BLACK)
                        .setTextSizeBySp(RandomUtils.getRandom(13, 25))
                        .setBold(RandomUtils.nextBoolean())
                        .setText(randomText);
            }
        });
        // 默认点击
        binding.vidAlContentTv.performClick();
    }
}