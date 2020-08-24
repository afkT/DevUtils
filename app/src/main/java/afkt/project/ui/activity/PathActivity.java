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
import dev.utils.app.PathUtils;
import dev.utils.app.logger.DevLogger;
import dev.utils.app.toast.ToastTintUtils;
import dev.utils.common.StringUtils;

/**
 * detail: 路径信息
 * @author Ttt
 */
public class PathActivity extends BaseActivity<BaseViewRecyclerviewBinding> {

    @Override
    public int layoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initValue() {
        super.initValue();

        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getPathButtonValues());
        binding.vidBvrRecy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                StringBuilder builder = new StringBuilder();
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {
                    case ButtonValue.BTN_PATH_INTERNAL:
                        StringUtils.appendsIgnoreLast(builder, StringUtils.NEW_LINE_STR,
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
                                PathUtils.getInternal().getAppFilesPath("Temp"),
                                PathUtils.getInternal().getAppFilesDir("Temp"),
                                PathUtils.getInternal().getAppSpPath(),
                                PathUtils.getInternal().getAppSpDir(),
                                PathUtils.getInternal().getAppSpPath("SPConfig.xml"),
                                PathUtils.getInternal().getAppSpFile("SPConfig.xml"),
                                PathUtils.getInternal().getAppNoBackupFilesPath(),
                                PathUtils.getInternal().getAppNoBackupFilesDir(),
                                PathUtils.getInternal().getAppMusicPath(),
                                PathUtils.getInternal().getAppMusicDir(),
                                PathUtils.getInternal().getAppPodcastsPath(),
                                PathUtils.getInternal().getAppPodcastsDir(),
                                PathUtils.getInternal().getAppRingtonesPath(),
                                PathUtils.getInternal().getAppRingtonesDir(),
                                PathUtils.getInternal().getAppAlarmsPath(),
                                PathUtils.getInternal().getAppAlarmsDir(),
                                PathUtils.getInternal().getAppNotificationsPath(),
                                PathUtils.getInternal().getAppNotificationsDir(),
                                PathUtils.getInternal().getAppPicturesPath(),
                                PathUtils.getInternal().getAppPicturesDir(),
                                PathUtils.getInternal().getAppMoviesPath(),
                                PathUtils.getInternal().getAppMoviesDir(),
                                PathUtils.getInternal().getAppDownloadPath(),
                                PathUtils.getInternal().getAppDownloadDir(),
                                PathUtils.getInternal().getAppDCIMPath(),
                                PathUtils.getInternal().getAppDCIMDir(),
                                PathUtils.getInternal().getAppDocumentsPath(),
                                PathUtils.getInternal().getAppDocumentsDir(),
                                PathUtils.getInternal().getAppAudiobooksPath(),
                                PathUtils.getInternal().getAppAudiobooksDir(),
                                ""
                        );
                        DevLogger.dTag(TAG, builder.toString());
                        showToast(true, "信息已打印, 请查看 Logcat");
                        break;
                    case ButtonValue.BTN_PATH_APP_EXTERNAL:
                        StringUtils.appendsIgnoreLast(builder, StringUtils.NEW_LINE_STR,
                                "应用外部存储路径类",
                                PathUtils.getAppExternal().getAppDataPath(),
                                PathUtils.getAppExternal().getAppDataDir(),
                                PathUtils.getAppExternal().getAppDataPath("temp"),
                                PathUtils.getAppExternal().getAppDataDir("temp"),
                                PathUtils.getAppExternal().getAppCachePath(),
                                PathUtils.getAppExternal().getAppCacheDir(),
                                PathUtils.getAppExternal().getAppCachePath("devutils"),
                                PathUtils.getAppExternal().getAppCacheDir("devutils"),
                                PathUtils.getAppExternal().getExternalFilesPath(null),
                                PathUtils.getAppExternal().getExternalFilesDir(null),
                                PathUtils.getAppExternal().getAppFilesPath(),
                                PathUtils.getAppExternal().getAppFilesDir(),
                                PathUtils.getAppExternal().getAppFilesPath("project"),
                                PathUtils.getAppExternal().getAppFilesDir("project"),
                                PathUtils.getAppExternal().getAppMusicPath(),
                                PathUtils.getAppExternal().getAppMusicDir(),
                                PathUtils.getAppExternal().getAppPodcastsPath(),
                                PathUtils.getAppExternal().getAppPodcastsDir(),
                                PathUtils.getAppExternal().getAppRingtonesPath(),
                                PathUtils.getAppExternal().getAppRingtonesDir(),
                                PathUtils.getAppExternal().getAppAlarmsPath(),
                                PathUtils.getAppExternal().getAppAlarmsDir(),
                                PathUtils.getAppExternal().getAppNotificationsPath(),
                                PathUtils.getAppExternal().getAppNotificationsDir(),
                                PathUtils.getAppExternal().getAppPicturesPath(),
                                PathUtils.getAppExternal().getAppPicturesDir(),
                                PathUtils.getAppExternal().getAppMoviesPath(),
                                PathUtils.getAppExternal().getAppMoviesDir(),
                                PathUtils.getAppExternal().getAppDownloadPath(),
                                PathUtils.getAppExternal().getAppDownloadDir(),
                                PathUtils.getAppExternal().getAppDCIMPath(),
                                PathUtils.getAppExternal().getAppDCIMDir(),
                                PathUtils.getAppExternal().getAppDocumentsPath(),
                                PathUtils.getAppExternal().getAppDocumentsDir(),
                                PathUtils.getAppExternal().getAppAudiobooksPath(),
                                PathUtils.getAppExternal().getAppAudiobooksDir(),
                                PathUtils.getAppExternal().getAppObbPath(),
                                PathUtils.getAppExternal().getAppObbDir(),
                                ""
                        );
                        DevLogger.dTag(TAG, builder.toString());
                        showToast(true, "信息已打印, 请查看 Logcat");
                        break;
                    case ButtonValue.BTN_PATH_SDCARD:
                        StringUtils.appendsIgnoreLast(builder, StringUtils.NEW_LINE_STR,
                                "外部存储路径 ( SDCard )",
                                PathUtils.getSDCard().isSDCardEnable(),
                                PathUtils.getSDCard().getSDCardFile(),
                                PathUtils.getSDCard().getSDCardPath(),
                                PathUtils.getSDCard().getSDCardFile("DevUtils"),
                                PathUtils.getSDCard().getSDCardPath("DevUtils"),
                                PathUtils.getSDCard().getExternalStoragePublicPath("project"),
                                PathUtils.getSDCard().getExternalStoragePublicDir("project"),
                                PathUtils.getSDCard().getMusicPath(),
                                PathUtils.getSDCard().getMusicDir(),
                                PathUtils.getSDCard().getPodcastsPath(),
                                PathUtils.getSDCard().getPodcastsDir(),
                                PathUtils.getSDCard().getRingtonesPath(),
                                PathUtils.getSDCard().getRingtonesDir(),
                                PathUtils.getSDCard().getAlarmsPath(),
                                PathUtils.getSDCard().getAlarmsDir(),
                                PathUtils.getSDCard().getNotificationsPath(),
                                PathUtils.getSDCard().getNotificationsDir(),
                                PathUtils.getSDCard().getPicturesPath(),
                                PathUtils.getSDCard().getPicturesDir(),
                                PathUtils.getSDCard().getMoviesPath(),
                                PathUtils.getSDCard().getMoviesDir(),
                                PathUtils.getSDCard().getDownloadPath(),
                                PathUtils.getSDCard().getDownloadDir(),
                                PathUtils.getSDCard().getDCIMPath(),
                                PathUtils.getSDCard().getDCIMDir(),
                                PathUtils.getSDCard().getDocumentsPath(),
                                PathUtils.getSDCard().getDocumentsDir(),
                                PathUtils.getSDCard().getAudiobooksPath(),
                                PathUtils.getSDCard().getAudiobooksDir(),
                                ""
                        );
                        DevLogger.dTag(TAG, builder.toString());
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