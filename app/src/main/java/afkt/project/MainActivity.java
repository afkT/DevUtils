package afkt.project;

import android.Manifest;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import java.util.Arrays;
import java.util.List;

import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityMainBinding;
import afkt.project.model.item.ButtonList;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.ModuleActivity;
import afkt.project.ui.activity.DevEnvironmentLibActivity;
import afkt.project.ui.adapter.ButtonAdapter;
import afkt.project.util.SkipUtils;
import dev.engine.log.DevLogEngine;
import dev.utils.app.AppCommonUtils;
import dev.utils.app.permission.PermissionUtils;
import dev.utils.app.toast.ToastTintUtils;
import dev.utils.app.toast.ToastUtils;
import dev.utils.common.HttpURLConnectionUtils;

public class MainActivity
        extends BaseActivity<ActivityMainBinding> {

    @Override
    public boolean isToolBar() {
        return false;
    }

    @Override
    public int baseLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initOther() {
        super.initOther();

        // ===========
        // = 时间校验 =
        // ===========

        HttpURLConnectionUtils.getNetTime(new HttpURLConnectionUtils.TimeCallback() {
            @Override
            public void onResponse(long time) {
                // 获取当前时间
                long curTime = System.currentTimeMillis();
                if (time >= 1) {
                    // 获取误差时间
                    final long diffTime = Math.abs(curTime - time);
                    // 判断是否误差超过 10 秒
                    if (diffTime >= 10000L) {
                        ToastUtils.showShort("当前时间与网络时间不一致, 误差: " + (diffTime / 1000) + "秒");
                    }
                }
            }

            @Override
            public void onFail(Exception e) {
                DevLogEngine.getEngine().eTag(TAG, e, "getNetTime");
            }
        });

        // ===========
        // = 申请权限 =
        // ===========

        PermissionUtils.permission(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).callback(new PermissionUtils.PermissionCallback() {
            /**
             * 授权通过权限回调
             */
            @Override
            public void onGranted() {
                DevLogEngine.getEngine().d("permission granted");
            }

            /**
             * 授权未通过权限回调
             * @param grantedList  申请通过的权限
             * @param deniedList   申请未通过的权限
             * @param notFoundList 查询不到的权限 ( 包含未注册 )
             */
            @Override
            public void onDenied(
                    List<String> grantedList,
                    List<String> deniedList,
                    List<String> notFoundList
            ) {
                StringBuilder builder = new StringBuilder();
                builder.append("permission");
                builder.append("\ngrantedList: ").append(Arrays.toString(grantedList.toArray()));
                builder.append("\ndeniedList: ").append(Arrays.toString(deniedList.toArray()));
                builder.append("\nnotFoundList: ").append(Arrays.toString(notFoundList.toArray()));
                DevLogEngine.getEngine().d(builder.toString());
                // 拒绝了则再次请求处理
                PermissionUtils.againRequest(MainActivity.this, this, deniedList);
                // Toast
                ToastUtils.showShort("请开启读写手机存储权限.");
            }
        }).request(this);
    }

    @Override
    public void initValue() {
        super.initValue();
        // 设置 Android 版本信息
        binding.vidAmAndroidTv.setText(AppCommonUtils.convertSDKVersion());
        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getMainButtonValues());
        binding.vidBaseRecy.vidBvrRecy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(
                    BaseQuickAdapter adapter,
                    View view,
                    int position
            ) {
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {
                    case ButtonValue.MODULE_FRAMEWORK:
                    case ButtonValue.MODULE_LIB:
                    case ButtonValue.MODULE_UI:
                    case ButtonValue.MODULE_OTHER:
                    case ButtonValue.MODULE_DEV_WIDGET:
                        SkipUtils.startActivity(ModuleActivity.class, buttonValue);
                        break;
                    case ButtonValue.MODULE_DEV_ENVIRONMENT:
                        SkipUtils.startActivity(DevEnvironmentLibActivity.class, buttonValue);
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
    }
}