package afkt.project.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.BaseViewRecyclerviewBinding;
import afkt.project.model.bean.CommodityEvaluateBean;
import afkt.project.ui.adapter.MultiSelectAdapter;
import dev.base.widget.BaseTextView;
import dev.engine.log.DevLogEngine;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.QuickHelper;
import dev.utils.app.helper.ViewHelper;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: 多选辅助类 MultiSelectAssist
 * @author Ttt
 */
public class MultiSelectActivity
        extends BaseActivity<BaseViewRecyclerviewBinding> {

    // 适配器
    MultiSelectAdapter multiSelectAdapter;

    @Override
    public int baseLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 增加 Toolbar 按钮
        addToolbarButton();

        ViewGroup parent = (ViewGroup) binding.vidBvrRecy.getParent();
        // 根布局处理
        ViewHelper.get().setPadding(parent, 0)
                .setBackgroundColor(parent, ResourceUtils.getColor(R.color.color_33));
    }

    @Override
    public void initValue() {
        super.initValue();

        List<CommodityEvaluateBean> lists = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            lists.add(CommodityEvaluateBean.newCommodityEvaluateBean());
        }

        // 初始化布局管理器、适配器
        multiSelectAdapter = new MultiSelectAdapter(lists)
                .setSelectListener(new MultiSelectAdapter.OnSelectListener() {
                    @Override
                    public void onClickSelect(
                            int pos,
                            boolean now
                    ) {
                        CommodityEvaluateBean commodityEvaluateBean = multiSelectAdapter.getData().get(pos);
                        DevLogEngine.getEngine().eTag(TAG, "新状态: %s, 商品名: %s", now, commodityEvaluateBean.commodityName);
                    }
                });
        binding.vidBvrRecy.setAdapter(multiSelectAdapter);
    }

    // ===============
    // = 增加按钮处理 =
    // ===============

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
        getToolbar().addView(editView = createTextView("编辑", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectAdapter.setEditState(true);
                // 显示其他按钮、隐藏编辑按钮
                ViewUtils.toggleVisibilitys(new View[]{cancelView, confirmView, allSelectView, unAllSelectView, inverseSelectView}, editView);
            }
        }));
        getToolbar().addView(cancelView = createTextView("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectAdapter.setEditState(false).clearSelectAll();
                // 显示编辑按钮、隐藏其他按钮
                ViewUtils.toggleVisibilitys(editView, cancelView, confirmView, allSelectView, unAllSelectView, inverseSelectView);
            }
        }));
        getToolbar().addView(confirmView = createTextView("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder builder = new StringBuilder();
                builder.append("是否全选: ").append(multiSelectAdapter.isSelectAll());
                builder.append("\n是否选中: ").append(multiSelectAdapter.isSelect());
                builder.append("\n选中数量: ").append(multiSelectAdapter.getSelectSize());
                builder.append("\n总数: ").append(multiSelectAdapter.getDataCount());
                ToastTintUtils.normal(builder.toString());
            }
        }));
        getToolbar().addView(allSelectView = createTextView("全选", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectAdapter.selectAll();
            }
        }));
        getToolbar().addView(unAllSelectView = createTextView("非全选", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectAdapter.clearSelectAll();
            }
        }));
        getToolbar().addView(inverseSelectView = createTextView("反选", new View.OnClickListener() {
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
    private BaseTextView createTextView(
            String text,
            View.OnClickListener onClickListener
    ) {
        return QuickHelper.get(new BaseTextView(this))
                .setVisibility(false) // 默认隐藏
                .setText(text)
                .setBold()
                .setTextColor(ResourceUtils.getColor(R.color.white))
                .setTextSizeBySp(13.0f)
                .setPaddingLeft(30)
                .setPaddingRight(30)
                .setOnClicks(onClickListener)
                .getView();
    }
}