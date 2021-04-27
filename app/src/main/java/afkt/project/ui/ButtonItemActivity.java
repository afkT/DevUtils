package afkt.project.ui;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.BaseViewRecyclerviewBinding;
import afkt.project.model.item.ButtonList;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.activity.ViewAssistActivity;
import afkt.project.ui.activity.ViewAssistRecyclerViewLoadActivity;
import afkt.project.ui.adapter.ButtonAdapter;
import afkt.project.util.SkipUtils;
import dev.callback.DevItemClickCallback;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: Button 列表 Activity
 * @author Ttt
 */
public class ButtonItemActivity
        extends BaseActivity<BaseViewRecyclerviewBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initValue() {
        super.initValue();
        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getButtonValues(getModuleType()));
        binding.vidBvrRecy.setAdapter(buttonAdapter);
        buttonAdapter.setItemCallback(new DevItemClickCallback<ButtonValue>() {
            @Override
            public void onItemClick(
                    ButtonValue buttonValue,
                    int param
            ) {
                switch (buttonValue.type) {

                    // =============
                    // = DevWidget =
                    // =============

                    case ButtonValue.BTN_VIEW_ASSIST_RECYCLER: // RecyclerView ( loading )
                        SkipUtils.startActivity(ViewAssistRecyclerViewLoadActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_VIEW_ASSIST_ERROR: // Error ( failed )
                    case ButtonValue.BTN_VIEW_ASSIST_EMPTY: // Empty ( data )
                    case ButtonValue.BTN_VIEW_ASSIST_CUSTOM: // Custom Type
                        SkipUtils.startActivity(ViewAssistActivity.class, buttonValue);
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
        // 注册观察者
        registerAdapterDataObserver(binding.vidBvrRecy, true);
    }
}