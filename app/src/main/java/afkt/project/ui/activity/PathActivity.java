package afkt.project.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
import butterknife.BindView;
import dev.utils.app.PathUtils;
import dev.utils.app.logger.DevLogger;
import dev.utils.app.toast.ToastTintUtils;
import dev.utils.common.StringUtils;

/**
 * detail: 路径信息
 * @author Ttt
 */
public class PathActivity extends BaseToolbarActivity {

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
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonValue.getPathButtonValues());
        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this));
        vid_bvr_recy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                StringBuilder builder;
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {
                    case ButtonValue.BTN_PATH_INTERNAL:
                        builder = StringUtils.appends(StringUtils.NEW_LINE_STR,
                                "内部存储路径",
                                PathUtils.getInternal().getRootPath(),
                                PathUtils.getInternal().getRootDirectory(),
                                PathUtils.getInternal().getDataPath(),
                                PathUtils.getInternal().getDataDirectory(),
                                PathUtils.getInternal().getDownloadCachePath(),
                                PathUtils.getInternal().getDownloadCacheDirectory(),
                                PathUtils.getInternal().getAppDataPath(),
                                PathUtils.getInternal().getAppDataDir(),
                                PathUtils.getInternal().getAppDataPath("app_webview"),
                                PathUtils.getInternal().getAppDataDir("app_webview"),
                                PathUtils.getInternal().getAppCachePath(),
                                PathUtils.getInternal().getAppCacheDir(),
                                PathUtils.getInternal().getAppCachePath("ttt"),
                                PathUtils.getInternal().getAppCacheDir("ttt"),
                                PathUtils.getInternal().getAppCodeCachePath(),
                                PathUtils.getInternal().getAppCodeCacheDir(),
                                PathUtils.getInternal().getAppDbsPath(),
                                PathUtils.getInternal().getAppDbsDir(),
                                PathUtils.getInternal().getAppDbPath("dbName"),
                                PathUtils.getInternal().getAppDbFile("dbName"),
                                PathUtils.getInternal().getAppFilesPath(),
                                PathUtils.getInternal().getAppFilesDir(),
                                PathUtils.getInternal().getAppSpPath(),
                                PathUtils.getInternal().getAppSpDir(),
                                PathUtils.getInternal().getAppSpPath("SPConfig.xml"),
                                PathUtils.getInternal().getAppSpFile("SPConfig.xml"),
                                PathUtils.getInternal().getAppNoBackupFilesPath(),
                                PathUtils.getInternal().getAppNoBackupFilesDir(),
                                ""
                        );
                        DevLogger.dTag(mTag, builder.toString());
                        showToast(true, "信息已打印, 请查看 Logcat");
                        break;
                    case ButtonValue.BTN_PATH_EXTERNAL:
                        builder = StringUtils.appends(StringUtils.NEW_LINE_STR,
                                "外部存储路径",
                                ""
                        );
                        DevLogger.dTag(mTag, builder.toString());
                        showToast(true, "信息已打印, 请查看 Logcat");
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
    }
}