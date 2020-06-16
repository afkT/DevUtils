package afkt.project.ui.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.ui.adapter.KeyValueAdapter;
import butterknife.BindView;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ScreenUtils;
import dev.utils.app.SizeUtils;
import dev.utils.app.helper.ViewHelper;
import dev.utils.app.info.KeyValueBean;

/**
 * detail: 屏幕信息
 * @author Ttt
 */
public class ScreenInfoActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;

    @Override
    public int getLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup parent = (ViewGroup) vid_bvr_recy.getParent();
        // 根布局处理
        ViewHelper.get().setPadding(parent, 0)
                .setBackgroundColor(parent, ResourceUtils.getColor(R.color.color_33));
    }

    @Override
    public void initValues() {
        super.initValues();

        vid_bvr_recy.setAdapter(new KeyValueAdapter(getScreenInfoLists()));
    }

    /**
     * 获取屏幕信息集合
     * @return 屏幕信息集合
     */
    private List<KeyValueBean> getScreenInfoLists() {
        List<KeyValueBean> lists = new ArrayList<>();
        // 获取屏幕尺寸 ( 英寸 )
        lists.add(KeyValueBean.get(R.string.screen, ScreenUtils.getScreenSizeOfDevice()));
        // 获取屏幕分辨率
        lists.add(KeyValueBean.get(R.string.screen_size, ScreenUtils.getScreenSize()));
        // 获取屏幕高度
        lists.add(KeyValueBean.get(R.string.height_pixels, ScreenUtils.getScreenHeight() + ""));
        // 获取屏幕宽度
        lists.add(KeyValueBean.get(R.string.width_pixels, ScreenUtils.getScreenWidth() + ""));
        // 获取 X 轴 DPI
        lists.add(KeyValueBean.get(R.string.xdpi, ScreenUtils.getXDpi() + ""));
        // 获取 Y 轴 DPI
        lists.add(KeyValueBean.get(R.string.ydpi, ScreenUtils.getYDpi() + ""));
        // 获取屏幕密度
        lists.add(KeyValueBean.get(R.string.density, ScreenUtils.getDensity() + ""));
        // 获取屏幕密度 DPI
        lists.add(KeyValueBean.get(R.string.density_dpi, ScreenUtils.getDensityDpi() + ""));
        // 获取屏幕缩放密度
        lists.add(KeyValueBean.get(R.string.scaled_density, ScreenUtils.getScaledDensity() + ""));
        // 获取高度 DPI 基准
        lists.add(KeyValueBean.get(R.string.height_dpi, ScreenUtils.getHeightDpi() + ""));
        // 获取宽度 DPI 基准
        lists.add(KeyValueBean.get(R.string.width_dpi, ScreenUtils.getWidthDpi() + ""));
        // =
        StringBuilder builder = new StringBuilder();
        builder.append("1dp=" + SizeUtils.dipConvertPxf(1f) + "px");
        builder.append(", 1sp=" + SizeUtils.spConvertPxf(1f) + "px");
        // 转换 DPI
        lists.add(KeyValueBean.get(R.string.convert_dpi, builder.toString()));
        return lists;
    }
}