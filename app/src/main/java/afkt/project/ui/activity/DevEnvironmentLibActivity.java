package afkt.project.ui.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.BaseViewRecyclerviewBinding;
import afkt.project.model.item.ButtonList;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
import dev.engine.log.DevLogEngine;
import dev.environment.DevEnvironment;
import dev.environment.DevEnvironmentActivity;
import dev.environment.RestartCallback;
import dev.environment.bean.EnvironmentBean;
import dev.environment.bean.ModuleBean;
import dev.environment.listener.OnEnvironmentChangeListener;
import dev.utils.app.ActivityUtils;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: DevEnvironment 环境配置切换库
 * @author Ttt
 */
public class DevEnvironmentLibActivity
        extends BaseActivity<BaseViewRecyclerviewBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initValue() {
        super.initValue();

        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getModuleDevEnvironmentButtonValues());
        binding.vidBvrRecy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(
                    BaseQuickAdapter adapter,
                    View view,
                    int position
            ) {
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                boolean     result;
                switch (buttonValue.type) {
                    case ButtonValue.BTN_DEV_ENVIRONMENT:
                        result = DevEnvironmentActivity.start(mContext, new RestartCallback() {
                            @Override
                            public void onRestart() {
                                ActivityUtils.getManager().exitApplication();
                            }
                        });
                        showToast(result, "跳转成功", "跳转失败");
                        break;
                    case ButtonValue.BTN_USE_CUSTOM:
                        // 如果准备设置环境等于当前选中的环境, 则会返回 false
                        EnvironmentBean custom = new EnvironmentBean("自定义配置",
                                "https://custom.com", "动态自定义", DevEnvironment.getServiceModule());
                        result = DevEnvironment.setServiceEnvironment(mContext, custom);
                        showToast(result, "设置成功", "设置失败");
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });

        // 环境改变通知
        DevEnvironment.addOnEnvironmentChangeListener(new OnEnvironmentChangeListener() {
            @Override
            public void onEnvironmentChanged(
                    ModuleBean module,
                    EnvironmentBean oldEnvironment,
                    EnvironmentBean newEnvironment
            ) {
                // 可以进行重新请求等操作

                StringBuilder builder = new StringBuilder();
                builder.append("module");
                builder.append("\nname: ").append(module.getName());
                builder.append("\nalias: ").append(module.getAlias());
                builder.append("\n\n");
                builder.append("oldEnvironment");
                builder.append("\nname: ").append(oldEnvironment.getName());
                builder.append("\nvalue: ").append(oldEnvironment.getValue());
                builder.append("\nalias: ").append(oldEnvironment.getAlias());
                builder.append("\n\n");
                builder.append("newEnvironment");
                builder.append("\nname: ").append(newEnvironment.getName());
                builder.append("\nvalue: ").append(newEnvironment.getValue());
                builder.append("\nalias: ").append(newEnvironment.getAlias());
                ToastTintUtils.normal(builder.toString());

                DevLogEngine.getEngine().dTag(TAG, builder.toString());
            }
        });
    }
}