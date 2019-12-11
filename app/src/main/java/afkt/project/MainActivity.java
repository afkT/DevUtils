package afkt.project;

import android.Manifest;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.Arrays;
import java.util.List;

import afkt.project.base.app.BaseActivity;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.ModuleActivity;
import afkt.project.ui.adapter.ButtonAdapter;
import afkt.project.ui.widget.BaseTextView;
import afkt.project.util.SkipUtils;
import butterknife.BindView;
import dev.utils.app.AppCommonUtils;
import dev.utils.app.PathUtils;
import dev.utils.app.logger.DevLogger;
import dev.utils.app.permission.PermissionUtils;
import dev.utils.app.toast.ToastTintUtils;
import dev.utils.app.toast.ToastUtils;
import dev.utils.common.HttpURLConnectionUtils;
import dev.utils.common.StringUtils;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vid_am_android_tv)
    BaseTextView vid_am_android_tv;
    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initOtherOperate() {
        super.initOtherOperate();

        StringBuilder builder = StringUtils.appends(StringUtils.NEW_LINE_STR,
                "外部存储路径 ( SDCard )",
                ""
        );

        DevLogger.dTag("PATH_PATH_PATH", builder.toString());

//        // 跳转 Home
//        ActivityUtils.startHomeActivity();

        // ============
        // = 时间校验 =
        // ============

        HttpURLConnectionUtils.getNetTime(new HttpURLConnectionUtils.TimeCallBack() {
            @Override
            public void onResponse(long time) {
                // 获取当前时间
                long cTime = System.currentTimeMillis();
                if (time >= 1) {
                    // 获取误差时间
                    final long diffTime = Math.abs(cTime - time);
                    // 判断是否误差超过 10 秒
                    if (diffTime >= 10000l) {
                        ToastUtils.showShort("当前时间与网络时间不一致, 误差: " + (diffTime / 1000) + "秒");
                    }
                }
            }

            @Override
            public void onFail(Exception e) {
                DevLogger.eTag(mTag, e, "getNetTime");
            }
        });

        // ============
        // = 申请权限 =
        // ============

        PermissionUtils.permission(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).callBack(new PermissionUtils.PermissionCallBack() {
            /**
             * 授权通过权限回调
             */
            @Override
            public void onGranted() {
                DevLogger.d("permission granted");
            }

            /**
             * 授权未通过权限回调
             * @param grantedList  申请通过的权限
             * @param deniedList   申请未通过的权限
             * @param notFoundList 查询不到的权限 ( 包含未注册 )
             */
            @Override
            public void onDenied(List<String> grantedList, List<String> deniedList, List<String> notFoundList) {
                StringBuilder builder = new StringBuilder();
                builder.append("permission");
                builder.append("\ngrantedList: " + Arrays.toString(grantedList.toArray()));
                builder.append("\ndeniedList: " + Arrays.toString(deniedList.toArray()));
                builder.append("\nnotFoundList: " + Arrays.toString(notFoundList.toArray()));
                DevLogger.d(builder.toString());
            }
        }).request(this);
    }

    @Override
    public void initValues() {
        super.initValues();
        // 设置 Android 版本信息
        vid_am_android_tv.setText(AppCommonUtils.convertSDKVersion());
        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonValue.getMainButtonValues());
        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this));
        vid_bvr_recy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {
                    case ButtonValue.MODULE_FRAMEWORK:
                    case ButtonValue.MODULE_LIB:
                    case ButtonValue.MODULE_UI:
                    case ButtonValue.MODULE_OTHER:
                        SkipUtils.startActivity(ModuleActivity.class, buttonValue);
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
    }
}
