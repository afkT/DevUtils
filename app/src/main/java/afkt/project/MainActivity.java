package afkt.project;

import android.Manifest;
import android.os.Handler;
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
import dev.engine.permission.DevPermissionEngine;
import dev.engine.permission.IPermissionEngine;
import dev.utils.app.AppCommonUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.app.logger.DevLogger;
import dev.utils.app.timer.DevTimer;
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

        timer();

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

        DevPermissionEngine.getEngine().request(
                this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                new IPermissionEngine.Callback() {
                    @Override
                    public void onGranted() {
                        DevLogEngine.getEngine().d("permission granted");
                    }

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
                        DevPermissionEngine.getEngine().againRequest(MainActivity.this, this, deniedList);
                        // Toast
                        ToastUtils.showShort("请开启读写手机存储权限.");
                    }
                }
        );
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

    private void timer() {
        new DevTimer.Builder(15000L, 1500L)
                .setDelay(0L).setPeriod(100L).setTag("在吗").setLimit(0)
                .build().setHandler(new Handler())
                .setCallback((timer, number, end, infinite) -> {
                    StringBuilder builder = new StringBuilder()
                            .append("触发次数: " + number)
                            .append("\n是否结束: " + end)
                            .append("\n是否无限循环: " + infinite)
                            .append("\n是否主线程: " + HandlerUtils.isMainThread());
                    append(builder, timer);
                    DevLogger.dTag("QPQPAKSADL", builder.toString());

                    if (number >= 5) {
                        timer.stop();
                        builder = new StringBuilder();
                        append(builder, timer);
                        DevLogger.dTag("QPQPAKSADL", builder.toString());
                    }
                }).start();
    }

    private void append(
            StringBuilder builder,
            DevTimer timer
    ) {
        builder.append("\n")
                .append("\nTAG: " + timer.getTag())
                .append("\nDelay: " + timer.getDelay())
                .append("\nPeriod: " + timer.getPeriod())
                .append("\nLimit: " + timer.getTriggerLimit())
                .append("\nNumber: " + timer.getTriggerNumber())
                .append("\nUUID: " + timer.getUUID())
                .append("\nisInfinite: " + timer.isInfinite())
                .append("\nisMarkSweep: " + timer.isMarkSweep())
                .append("\nisRunning: " + timer.isRunning())
                .append("\nisTriggerEnd: " + timer.isTriggerEnd());

    }
}