package afkt.project.ui.activity;

import android.view.View;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import butterknife.BindView;
import butterknife.OnClick;
import dev.widget.ui.FlowLikeView;

/**
 * detail: 自定义角标 View
 * @author Ttt
 */
public class CornerLabelActivity extends BaseToolbarActivity {

    @BindView(R.id.vid_afl_flowlike)
    FlowLikeView vid_afl_flowlike;

    @Override
    public int getLayoutId() {
        return R.layout.activity_corner_label;
    }

    @Override
    public void initValues() {
        super.initValues();


//        app:dev_animDuration="2000"
//        app:dev_iconHeight="30.0dp"
//        app:dev_iconWidth="30.0dp"

//        // 设置动画时间
//        vid_afl_flowlike.setAnimDuration(2000);
//        // 设置图标宽度
//        vid_afl_flowlike.setIconWidth(SizeUtils.dipConvertPx(30));
//        // 设置图标高度
//        vid_afl_flowlike.setIconHeight(SizeUtils.dipConvertPx(30));
//
//        // 设置漂浮图标
//        List<Drawable> lists = new ArrayList<>();
//        lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_1));
//        lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_2));
//        lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_3));
//        lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_4));
//        lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_5));
//        vid_afl_flowlike.setDrawables(lists);

        // 设置漂浮图标
        vid_afl_flowlike.setDrawablesById(R.drawable.icon_live_brow_1, R.drawable.icon_live_brow_2,
            R.drawable.icon_live_brow_3, R.drawable.icon_live_brow_4, R.drawable.icon_live_brow_5);
    }

    @OnClick({R.id.vid_afl_flowlike})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.vid_afl_flowlike:
                vid_afl_flowlike.like();
                vid_afl_flowlike.like(); // 演示效果
                break;
        }
    }
}