package afkt.project.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityWrapBinding;
import dev.base.widget.BaseTextView;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ShapeUtils;
import dev.utils.app.helper.QuickHelper;
import dev.utils.common.ChineseUtils;
import dev.utils.common.RandomUtils;

/**
 * detail: 自动换行 View
 * @author Ttt
 */
public class WrapActivity extends BaseActivity<ActivityWrapBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.activity_wrap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 刷新按钮
        View view = QuickHelper.get(new BaseTextView(this))
                .setText("刷新")
                .setBold()
                .setTextColor(ResourceUtils.getColor(R.color.red))
                .setTextSizeBySp(15.0f)
                .setPaddingLeft(30)
                .setPaddingRight(30)
                .setOnClicks(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initValue();
                    }
                }).getView();
        getToolbar().addView(view);
    }

    @Override
    public void initValue() {
        super.initValue();

        binding.vidAwWrapview
                // 设置最大行数
//                .setMaxLine(RandomUtils.getRandom(10, 30))
                // 设置每一行向上的边距 ( 行间隔 )
                .setRowTopMargin(30)
                // 设置每个 View 之间的 Left 边距
                .setViewLeftMargin(30)
                // 快捷设置两个边距
                .setRowViewMargin(30, 30)
                .removeAllViews();

        // LayoutParams
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        // 设置点击效果
        GradientDrawable drawable = ShapeUtils.newShape(30f, ResourceUtils.getColor(R.color.color_88)).getDrawable();

        for (int i = 1; i <= 20; i++) {
            // 随机字符串
            String text       = ChineseUtils.randomWord(RandomUtils.getRandom(7)) + RandomUtils.getRandomLetters(RandomUtils.getRandom(5));
            String randomText = i + "." + RandomUtils.getRandom(text.toCharArray(), text.length());
            binding.vidAwWrapview.addView(createView(randomText, layoutParams, drawable));
        }
    }

    private BaseTextView createView(
            String text,
            ViewGroup.LayoutParams layoutParams,
            GradientDrawable drawable
    ) {
        return QuickHelper.get(new BaseTextView(this))
                .setLayoutParams(layoutParams)
                .setPadding(30, 15, 30, 15)
                .setBackground(drawable)
                .setMaxLines(1)
                .setEllipsize(TextUtils.TruncateAt.END)
                .setTextColor(Color.WHITE)
                .setTextSizeBySp(15f)
                .setBold(RandomUtils.nextBoolean())
                .setText(text)
                .getView();
    }
}