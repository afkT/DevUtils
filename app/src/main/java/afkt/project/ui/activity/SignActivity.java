package afkt.project.ui.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.LinearLayout;

import androidx.viewbinding.ViewBinding;

import afkt.project.base.app.BaseActivity;
import dev.widget.function.SignView;

/**
 * detail: 签名 View
 * @author Ttt
 */
public class SignActivity extends BaseActivity<ViewBinding> {

    @Override
    public boolean isViewBinding() {
        return false;
    }

    @Override
    public int baseLayoutId() {
        return 0;
    }

    @Override
    public View baseLayoutView() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        SignView signView = new SignView(this);
        signView.setLayoutParams(layoutParams);

        // 设置画笔
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30);
        paint.setColor(Color.BLACK);
        signView.setPaint(paint);
        return signView;
    }
}