package afkt.project.ui.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.model.item.ButtonList;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
import butterknife.BindView;
import dev.utils.app.ResourceUtils;
import dev.utils.app.toast.ToastTintUtils;
import utils_use.toast.ToastTintUse;

/**
 * detail: ToastTint ( 着色美化 Toast )
 * @author Ttt
 * <pre>
 *     {@link ToastTintUse}
 * </pre>
 */
public class ToastTintActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;

    @Override
    public int getLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initValues() {
        super.initValues();

        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getToastButtonValues());
        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this));
        vid_bvr_recy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {
                    case ButtonValue.BTN_TOAST_TINT_SUCCESS:
                        ToastTintUtils.success("Success Style Toast");
                        break;
                    case ButtonValue.BTN_TOAST_TINT_ERROR:
                        ToastTintUtils.error("Error Style Toast");
                        break;
                    case ButtonValue.BTN_TOAST_TINT_INFO:
                        ToastTintUtils.info("Info Style Toast");
                        break;
                    case ButtonValue.BTN_TOAST_TINT_NORMAL:
                        ToastTintUtils.normal("Normal Style Toast");
                        break;
                    case ButtonValue.BTN_TOAST_TINT_WARNING:
                        ToastTintUtils.warning("Warning Style Toast");
                        break;
                    case ButtonValue.BTN_TOAST_TINT_CUSTOM_STYLE:
                        ToastTintUtils.custom(new TempStyle(), "Custom Style Toast", ResourceUtils.getDrawable(R.mipmap.icon_launcher_round));
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
    }

    /**
     * 自定义实现样式
     * {@link ToastTintUtils.SuccessStyle}
     * {@link ToastTintUtils.ErrorStyle}
     * {@link ToastTintUtils.InfoStyle}
     * {@link ToastTintUtils.WarningStyle}
     * {@link ToastTintUtils.NormalStyle}
     * {@link ToastTintUtils.DefaultStyle}
     */
    private static class TempStyle implements ToastTintUtils.Style {

        /**
         * 文本颜色
         * @return
         */
        @Override
        public int getTextColor() {
            return Color.WHITE;
        }

        /**
         * 字体大小
         * @return
         */
        @Override
        public float getTextSize() {
            return 16f;
        }

        /**
         * 背景着色颜色
         * @return
         */
        @Override
        public int getBackgroundTintColor() {
            return 0;
        }

        /**
         * 背景图片
         * @return
         */
        @Override
        public Drawable getBackground() {
            return null;
        }

        /**
         * 最大行数
         * @return
         */
        @Override
        public int getMaxLines() {
            return 0;
        }

        /**
         * Ellipsize 效果
         * @return
         */
        @Override
        public TextUtils.TruncateAt getEllipsize() {
            return null;
        }

        /**
         * 字体样式
         * @return
         */
        @Override
        public Typeface getTypeface() {
            // return Typeface.create("sans-serif-condensed", Typeface.NORMAL);
            return null;
        }

        /**
         * 获取图标着色颜色
         * @return
         */
        @Override
        public int getTintIconColor() {
            return Color.WHITE;
        }

        /**
         * 是否渲染图标 - getTintIconColor() 着色渲染
         * @return
         */
        @Override
        public boolean isTintIcon() {
            return false;
        }
    }
}