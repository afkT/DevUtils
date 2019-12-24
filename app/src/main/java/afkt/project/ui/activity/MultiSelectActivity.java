package afkt.project.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.model.bean.CommodityEvaluateBean;
import afkt.project.ui.adapter.MultiSelectAdapter;
import afkt.project.ui.widget.BaseTextView;
import butterknife.BindView;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.ViewHelper;
import dev.utils.app.logger.DevLogger;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: 多选辅助类 MultiSelectAssist
 * @author Ttt
 */
public class MultiSelectActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;
    // 适配器
    MultiSelectAdapter multiSelectAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 增加 Toolbar 按钮
        addToolbarButton();

        ViewGroup parent = (ViewGroup) vid_bvr_recy.getParent();
        // 根布局处理
        ViewHelper.get().setPadding(parent, 0)
                .setBackgroundColor(parent, ResourceUtils.getColor(R.color.color_33));
    }

    @Override
    public void initValues() {
        super.initValues();

        List<CommodityEvaluateBean> lists = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            lists.add(CommodityEvaluateBean.newCommodityEvaluateBean());
        }

        // 初始化布局管理器、适配器
        multiSelectAdapter = new MultiSelectAdapter(lists)
                .setSelectListener(new MultiSelectAdapter.OnSelectListener() {
                    @Override
                    public void onClickSelect(int pos, boolean now) {
                        CommodityEvaluateBean commodityEvaluateBean = multiSelectAdapter.getData().get(pos);
                        DevLogger.eTag(mTag, "新状态: " + now + ", 商品名: " + commodityEvaluateBean.commodityName);
                    }
                });
        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this));
        vid_bvr_recy.setAdapter(multiSelectAdapter);
    }

    // ================
    // = 增加按钮处理 =
    // ================

    // 编辑按钮
    BaseTextView editView;
    // 取消编辑按钮
    BaseTextView cancelView;
    // 确定按钮
    BaseTextView confirmView;
    // 全选按钮
    BaseTextView allSelectView;
    // 非全选按钮
    BaseTextView unAllSelectView;
    // 反选按钮
    BaseTextView inverseSelectView;

    /**
     * 增加 Toolbar 按钮
     */
    private void addToolbarButton() {
        vid_bt_toolbar.addView(editView = createTextView("编辑", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectAdapter.setEditState(true);
                // 显示其他按钮、隐藏编辑按钮
                ViewUtils.toggleVisibilitys(new View[]{cancelView, confirmView, allSelectView, unAllSelectView, inverseSelectView}, editView);
            }
        }));
        vid_bt_toolbar.addView(cancelView = createTextView("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectAdapter.setEditState(false).clearSelectAll();
                // 显示编辑按钮、隐藏其他按钮
                ViewUtils.toggleVisibilitys(editView, cancelView, confirmView, allSelectView, unAllSelectView, inverseSelectView);
            }
        }));
        vid_bt_toolbar.addView(confirmView = createTextView("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder builder = new StringBuilder();
                builder.append("是否全选: " + multiSelectAdapter.isSelectAll());
                builder.append("\n是否选中: " + multiSelectAdapter.isSelect());
                builder.append("\n选中数量: " + multiSelectAdapter.getSelectSize());
                builder.append("\n总数: " + multiSelectAdapter.getDataCount());
                ToastTintUtils.normal(builder.toString());
            }
        }));
        vid_bt_toolbar.addView(allSelectView = createTextView("全选", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectAdapter.selectAll();
            }
        }));
        vid_bt_toolbar.addView(unAllSelectView = createTextView("非全选", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectAdapter.clearSelectAll();
            }
        }));
        vid_bt_toolbar.addView(inverseSelectView = createTextView("反选", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectAdapter.inverseSelect();
            }
        }));

        // 显示编辑按钮
        ViewUtils.setVisibility(true, editView);
    }

    /**
     * 创建 TextView
     * @param text            文案
     * @param onClickListener 点击事件
     * @return {@link BaseTextView}
     */
    private BaseTextView createTextView(String text, View.OnClickListener onClickListener) {
        BaseTextView baseTextView = new BaseTextView(this);
        ViewHelper.get().setVisibility(false, baseTextView) // 默认隐藏
                .setText(baseTextView, text)
                .setBold(baseTextView)
                .setTextColor(baseTextView, ResourceUtils.getColor(R.color.white))
                .setTextSizeBySp(baseTextView, 13.0f)
                .setPaddingLeft(baseTextView, 30)
                .setPaddingRight(baseTextView, 30)
                .setOnClicks(onClickListener, baseTextView);
        return baseTextView;
    }
}