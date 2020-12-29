package afkt.project.ui.activity;

import android.view.View;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityFlowLikeBinding;

/**
 * detail: 自定义点赞效果 View
 * @author Ttt
 */
public class FlowLikeActivity
        extends BaseActivity<ActivityFlowLikeBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.activity_flow_like;
    }

    @Override
    public void initValue() {
        super.initValue();

//        app:dev_animDuration="2000"
//        app:dev_iconHeight="30.0dp"
//        app:dev_iconWidth="30.0dp"

//        // 设置动画时间
//        binding.vidAflFlowlike.setAnimDuration(2000);
//        // 设置图标宽度
//        binding.vidAflFlowlike.setIconWidth(SizeUtils.dipConvertPx(30));
//        // 设置图标高度
//        binding.vidAflFlowlike.setIconHeight(SizeUtils.dipConvertPx(30));
//
//        // 设置漂浮图标
//        List<Drawable> lists = new ArrayList<>();
//        lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_1));
//        lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_2));
//        lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_3));
//        lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_4));
//        lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_5));
//        binding.vidAflFlowlike.setDrawables(lists);

        // 设置漂浮图标
        binding.vidAflFlowlike.setDrawablesById(R.drawable.icon_live_brow_1, R.drawable.icon_live_brow_2,
                R.drawable.icon_live_brow_3, R.drawable.icon_live_brow_4, R.drawable.icon_live_brow_5);

        binding.vidAflFlowlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.vidAflFlowlike.like();
                binding.vidAflFlowlike.like(); // 演示效果
            }
        });
    }
}