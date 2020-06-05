package afkt.project;

import android.Manifest;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.tencent.mmkv.MMKV;

import java.util.Arrays;
import java.util.List;

import afkt.project.base.app.BaseActivity;
import afkt.project.base.constants.KeyConstants;
import afkt.project.model.item.ButtonList;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.ModuleActivity;
import afkt.project.ui.activity.DevEnvironmentLibActivity;
import afkt.project.ui.adapter.ButtonAdapter;
import afkt.project.ui.widget.BaseTextView;
import afkt.project.util.SkipUtils;
import butterknife.BindView;
import dev.utils.app.AppCommonUtils;
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

        // MMKV 简单使用
        mmkvSimple();

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
                // 拒绝了则再次请求处理
                PermissionUtils.againRequest(MainActivity.this, this, deniedList);
                // Toast
                ToastUtils.showLong("请开启读写手机存储权限.");
            }
        }).request(this);
    }

    @Override
    public void initValues() {
        super.initValues();
        // 设置 Android 版本信息
        vid_am_android_tv.setText(AppCommonUtils.convertSDKVersion());
        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getMainButtonValues());
        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this));
        vid_bvr_recy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
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

    /**
     * MMKV 简单使用
     */
    private void mmkvSimple() {
        MMKV defaultMMKV = MMKV.defaultMMKV();

        String mmapID = defaultMMKV.mmapID();

        defaultMMKV.putString(KeyConstants.Common.KEY_DATA, "defaultMMKV 存储数据");
        String data = defaultMMKV.getString(KeyConstants.Common.KEY_DATA, null);

        MMKV tempMMKV = MMKV.mmkvWithID("temp");
        String tempData = tempMMKV.getString(KeyConstants.Common.KEY_DATA, null);

        MMKV mmapIDMMKV = MMKV.mmkvWithID(mmapID);
        String mmapIdData = mmapIDMMKV.getString(KeyConstants.Common.KEY_DATA, null);

        StringBuilder builder = new StringBuilder();
        builder.append("default MMKV")
                .append(StringUtils.NEW_LINE_STR)
                .append("\t\tmmapID: " + mmapID)
                .append(StringUtils.NEW_LINE_STR)
                .append("\t\tdata: " + data);

        builder.append(StringUtils.NEW_LINE_STR)
                .append(StringUtils.NEW_LINE_STR);

        builder.append("temp MMKV")
                .append(StringUtils.NEW_LINE_STR)
                .append("\t\tmmapID: " + tempMMKV.mmapID())
                .append(StringUtils.NEW_LINE_STR)
                .append("\t\tdata: " + tempData);

        builder.append(StringUtils.NEW_LINE_STR)
                .append(StringUtils.NEW_LINE_STR);

        builder.append("mmapID MMKV")
                .append(StringUtils.NEW_LINE_STR)
                .append("\t\tmmapID: " + mmapIDMMKV.mmapID())
                .append(StringUtils.NEW_LINE_STR)
                .append("\t\tdata: " + mmapIdData);

        DevLogger.dTag(mTag, builder.toString());
    }
}