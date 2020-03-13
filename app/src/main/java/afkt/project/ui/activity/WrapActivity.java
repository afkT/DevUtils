package afkt.project.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.ui.widget.BaseTextView;
import butterknife.BindView;
import dev.temp.ChineseUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ShapeUtils;
import dev.utils.app.helper.ViewHelper;
import dev.utils.common.RandomUtils;
import dev.widget.ui.WrapView;

/**
 * detail: 自动换行 View
 * @author Ttt
 */
public class WrapActivity extends BaseToolbarActivity {

    @BindView(R.id.vid_aw_wrapview)
    WrapView vid_aw_wrapview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wrap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 刷新按钮
        BaseTextView baseTextView = new BaseTextView(this);
        ViewHelper.get().setText(baseTextView, "刷新").setBold(baseTextView)
                .setTextColor(baseTextView, ResourceUtils.getColor(R.color.red))
                .setTextSizeBySp(baseTextView, 15.0f)
                .setPaddingLeft(baseTextView, 30)
                .setPaddingRight(baseTextView, 30)
                .setOnClicks(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initValues();
                    }
                }, baseTextView);
        vid_bt_toolbar.addView(baseTextView);
    }

    @Override
    public void initValues() {
        super.initValues();

        // 清空全部 View
        vid_aw_wrapview.removeAllViews();
        // 设置最大行数
        vid_aw_wrapview.setMaxLine(RandomUtils.getRandom(10, 30));
//        // 设置每一行向上的边距 ( 行间隔 )
//        vid_aw_wrapview.setRowTopMargin(40);
//        // 设置每个 View 之间的 Left 边距
//        vid_aw_wrapview.setViewLeftMargin(30);
        // 设置每一行第一个 View Left 边距
        vid_aw_wrapview.setRowFristLeftMargin(20);
//        // 快捷设置三个边距
//        vid_aw_wrapview.setRowViewMargin(40, 30, 20);

        // 设置内边距
        vid_aw_wrapview.setPadding(0, 0, 20, 0);

        // LayoutParams
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置点击效果
        GradientDrawable drawable = ShapeUtils.newBuilder(30f, R.color.color_88).build().getDrawable();

        for (int i = 0; i < 50; i++) {
            // 随机字符串
            String text = ChineseUtils.getRandomWord(RandomUtils.getRandom(20)) + RandomUtils.getRandomLetters(RandomUtils.getRandom(10));
            String randomText = RandomUtils.getRandom(text.toCharArray(), text.length());

            BaseTextView baseTextView = new BaseTextView(this);
            ViewHelper.get().setLayoutParams(baseTextView, layoutParams)
                    .setPadding(baseTextView, 30, 15, 30, 15)
                    .setBackground(baseTextView, drawable)
                    .setMaxLines(baseTextView, 1)
                    .setEllipsize(baseTextView, TextUtils.TruncateAt.END)
                    .setTextColor(baseTextView, Color.WHITE)
                    .setTextSizeBySp(baseTextView, 15f)
                    .setBold(baseTextView, RandomUtils.nextBoolean())
                    .setText(baseTextView, randomText);
            vid_aw_wrapview.addView(baseTextView);
        }
    }
}