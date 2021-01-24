package afkt.project.ui.activity;

import android.os.Bundle;

import com.google.android.material.imageview.ShapeableImageView;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityShapeableImageViewBinding;
import dev.utils.app.SizeUtils;
import dev.utils.app.ViewUtils;

/**
 * detail: Material ShapeableImageView
 * @author Ttt
 * <pre>
 *     描边需设置 padding 大小为描边宽度一半, 否则显示不全
 * </pre>
 */
public class ShapeableImageViewActivity
        extends BaseActivity<ActivityShapeableImageViewBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.activity_shapeable_image_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initValue() {
        super.initValue();

        int margin = SizeUtils.dipConvertPx(15);

        for (int i = 0, len = binding.vidAsivLinear.getChildCount(); i < len; i++) {
            ShapeableImageView imageView = ViewUtils.getChildAt(binding.vidAsivLinear, i);
            // 随机图片
//            GlideUtils.with().displayImage("https://picsum.photos/300", imageView);
            ViewUtils.setMargin(imageView, 0, margin, 0, 0);
        }
    }
}