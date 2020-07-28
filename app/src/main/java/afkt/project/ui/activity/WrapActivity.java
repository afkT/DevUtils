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
import dev.utils.app.helper.QuickHelper;
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
                        initValues();
                    }
                }).getView();
        vid_bt_toolbar.addView(view);
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
//        // 快捷设置两个边距
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
            vid_aw_wrapview.addView(createView(randomText, layoutParams, drawable));
        }
    }

    private BaseTextView createView(String text, ViewGroup.LayoutParams layoutParams, GradientDrawable drawable) {
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