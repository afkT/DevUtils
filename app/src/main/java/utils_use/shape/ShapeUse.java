package utils_use.shape;

import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.dev.R;

import dev.DevUtils;
import dev.utils.app.ShapeUtils;
import dev.utils.app.StateListUtils;
import dev.utils.app.ViewUtils;

/**
 * detail: ShapeUtils 使用方法
 * @author Ttt
 */
class ShapeUse {

    private void shapeUse() {
        Button vid_btn1 = null;

        // 默认就设置背景色
        ShapeUtils.Builder builder = new ShapeUtils.Builder();
        builder.setRadiusLeft(10f).setColor(R.color.black);
        ViewUtils.setBackground(vid_btn1, builder.build().getDrawable());

        // 设置点击效果
        GradientDrawable drawable1 = ShapeUtils.newBuilder(10f, R.color.black).setStroke(5, R.color.green).build().getDrawable();
        GradientDrawable drawable2 = ShapeUtils.newBuilder(10f, R.color.sky_blue).setStroke(5, R.color.grey).build().getDrawable();

        ViewUtils.setBackground(vid_btn1, StateListUtils.newSelector(drawable2, drawable1)); // 设置点击 View 背景变色, 不用写 shape xml 文件
        vid_btn1.setTextColor(StateListUtils.createColorStateList(R.color.red, R.color.white)); // 设置点击字体变色

        // 设置渐变
        View vid_view1 = null;
        // int[] colors = new int[]{ Color.RED, Color.BLUE, Color.GREEN };

        int[] colors = new int[3];
        colors[0] = ContextCompat.getColor(DevUtils.getContext(), R.color.black);
        colors[1] = ContextCompat.getColor(DevUtils.getContext(), R.color.sky_blue);
        colors[2] = ContextCompat.getColor(DevUtils.getContext(), R.color.orange);

        // ShapeUtils.newBuilderToGradient(GradientDrawable.Orientation.BR_TL, colors).build().setDrawable(vid_view1);

        GradientDrawable drawable = ShapeUtils.newBuilderToGradient(GradientDrawable.Orientation.BR_TL, colors).build().getDrawable();
        // drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT); // 线性渐变, 这是默认设置
        // drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT); // 放射性渐变, 以开始色为中心
        drawable.setGradientType(GradientDrawable.SWEEP_GRADIENT); // 扫描线式的渐变
        ViewUtils.setBackground(vid_view1, drawable);
    }
}
