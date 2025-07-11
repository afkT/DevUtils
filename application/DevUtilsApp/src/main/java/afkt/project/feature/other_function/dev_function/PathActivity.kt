package afkt.project.feature.other_function.dev_function

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.app.AppViewModel
import afkt.project.base.project.bindAdapter
import afkt.project.model.data.button.ButtonList
import afkt.project.model.data.button.ButtonValue
import afkt.project.model.data.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import com.therouter.router.Route
import dev.expand.engine.log.log_dTag
import dev.utils.DevFinal
import dev.utils.app.PathUtils
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.StringUtils

/**
 * detail: 路径信息
 * @author Ttt
 */
@Route(path = RouterPath.OTHER_FUNCTION.PathActivity_PATH)
class PathActivity : BaseProjectActivity<BaseViewRecyclerviewBinding, AppViewModel>(
    R.layout.base_view_recyclerview, simple_Agile = {
        if (it is PathActivity) {
            it.apply {
                binding.vidRv.bindAdapter(ButtonList.pathButtonValues) { buttonValue ->
                    val builder = StringBuilder()
                    when (buttonValue.type) {
                        ButtonValue.BTN_PATH_INTERNAL -> {
                            StringUtils.appendsIgnoreLast(
                                builder, DevFinal.SYMBOL.NEW_LINE,
                                "内部存储路径",
                                PathUtils.getInternal().rootPath,
                                PathUtils.getInternal().rootDirectory,
                                PathUtils.getInternal().dataPath,
                                PathUtils.getInternal().dataDirectory,
                                PathUtils.getInternal().downloadCachePath,
                                PathUtils.getInternal().downloadCacheDirectory,
                                PathUtils.getInternal().appDataPath,
                                PathUtils.getInternal().appDataDir,
                                PathUtils.getInternal().getAppDataPath("app_webview"),
                                PathUtils.getInternal().getAppDataDir("app_webview"),
                                PathUtils.getInternal().appCachePath,
                                PathUtils.getInternal().appCacheDir,
                                PathUtils.getInternal().getAppCachePath("ttt"),
                                PathUtils.getInternal().getAppCacheDir("ttt"),
                                PathUtils.getInternal().appCodeCachePath,
                                PathUtils.getInternal().appCodeCacheDir,
                                PathUtils.getInternal().appDbsPath,
                                PathUtils.getInternal().appDbsDir,
                                PathUtils.getInternal().getAppDbPath("dbName"),
                                PathUtils.getInternal().getAppDbFile("dbName"),
                                PathUtils.getInternal().appFilesPath,
                                PathUtils.getInternal().appFilesDir,
                                PathUtils.getInternal().getAppFilesPath("Temp"),
                                PathUtils.getInternal().getAppFilesDir("Temp"),
                                PathUtils.getInternal().appSpPath,
                                PathUtils.getInternal().appSpDir,
                                PathUtils.getInternal().getAppSpPath("SPConfig.xml"),
                                PathUtils.getInternal().getAppSpFile("SPConfig.xml"),
                                PathUtils.getInternal().appNoBackupFilesPath,
                                PathUtils.getInternal().appNoBackupFilesDir,
                                PathUtils.getInternal().appMusicPath,
                                PathUtils.getInternal().appMusicDir,
                                PathUtils.getInternal().appPodcastsPath,
                                PathUtils.getInternal().appPodcastsDir,
                                PathUtils.getInternal().appRingtonesPath,
                                PathUtils.getInternal().appRingtonesDir,
                                PathUtils.getInternal().appAlarmsPath,
                                PathUtils.getInternal().appAlarmsDir,
                                PathUtils.getInternal().appNotificationsPath,
                                PathUtils.getInternal().appNotificationsDir,
                                PathUtils.getInternal().appPicturesPath,
                                PathUtils.getInternal().appPicturesDir,
                                PathUtils.getInternal().appMoviesPath,
                                PathUtils.getInternal().appMoviesDir,
                                PathUtils.getInternal().appDownloadPath,
                                PathUtils.getInternal().appDownloadDir,
                                PathUtils.getInternal().appDCIMPath,
                                PathUtils.getInternal().appDCIMDir,
                                PathUtils.getInternal().appDocumentsPath,
                                PathUtils.getInternal().appDocumentsDir,
                                PathUtils.getInternal().appAudiobooksPath,
                                PathUtils.getInternal().appAudiobooksDir,
                                ""
                            )
                            TAG.log_dTag(
                                message = builder.toString()
                            )
                            showToast(true, "信息已打印, 请查看 Logcat")
                        }

                        ButtonValue.BTN_PATH_APP_EXTERNAL -> {
                            StringUtils.appendsIgnoreLast(
                                builder, DevFinal.SYMBOL.NEW_LINE,
                                "应用外部存储路径类",
                                PathUtils.getAppExternal().appDataPath,
                                PathUtils.getAppExternal().appDataDir,
                                PathUtils.getAppExternal().getAppDataPath("temp"),
                                PathUtils.getAppExternal().getAppDataDir("temp"),
                                PathUtils.getAppExternal().appCachePath,
                                PathUtils.getAppExternal().appCacheDir,
                                PathUtils.getAppExternal().getAppCachePath("devutils"),
                                PathUtils.getAppExternal().getAppCacheDir("devutils"),
                                PathUtils.getAppExternal().getExternalFilesPath(null),
                                PathUtils.getAppExternal().getExternalFilesDir(null),
                                PathUtils.getAppExternal().appFilesPath,
                                PathUtils.getAppExternal().appFilesDir,
                                PathUtils.getAppExternal().getAppFilesPath("project"),
                                PathUtils.getAppExternal().getAppFilesDir("project"),
                                PathUtils.getAppExternal().appMusicPath,
                                PathUtils.getAppExternal().appMusicDir,
                                PathUtils.getAppExternal().appPodcastsPath,
                                PathUtils.getAppExternal().appPodcastsDir,
                                PathUtils.getAppExternal().appRingtonesPath,
                                PathUtils.getAppExternal().appRingtonesDir,
                                PathUtils.getAppExternal().appAlarmsPath,
                                PathUtils.getAppExternal().appAlarmsDir,
                                PathUtils.getAppExternal().appNotificationsPath,
                                PathUtils.getAppExternal().appNotificationsDir,
                                PathUtils.getAppExternal().appPicturesPath,
                                PathUtils.getAppExternal().appPicturesDir,
                                PathUtils.getAppExternal().appMoviesPath,
                                PathUtils.getAppExternal().appMoviesDir,
                                PathUtils.getAppExternal().appDownloadPath,
                                PathUtils.getAppExternal().appDownloadDir,
                                PathUtils.getAppExternal().appDCIMPath,
                                PathUtils.getAppExternal().appDCIMDir,
                                PathUtils.getAppExternal().appDocumentsPath,
                                PathUtils.getAppExternal().appDocumentsDir,
                                PathUtils.getAppExternal().appAudiobooksPath,
                                PathUtils.getAppExternal().appAudiobooksDir,
                                PathUtils.getAppExternal().appObbPath,
                                PathUtils.getAppExternal().appObbDir,
                                ""
                            )
                            TAG.log_dTag(
                                message = builder.toString()
                            )
                            showToast(true, "信息已打印, 请查看 Logcat")
                        }

                        ButtonValue.BTN_PATH_SDCARD -> {
                            StringUtils.appendsIgnoreLast(
                                builder, DevFinal.SYMBOL.NEW_LINE,
                                "外部存储路径 ( SDCard )",
                                PathUtils.getSDCard().isSDCardEnable,
                                PathUtils.getSDCard().sdCardFile,
                                PathUtils.getSDCard().sdCardPath,
                                PathUtils.getSDCard().getSDCardFile("DevUtils"),
                                PathUtils.getSDCard().getSDCardPath("DevUtils"),
                                PathUtils.getSDCard().getExternalStoragePublicPath("project"),
                                PathUtils.getSDCard().getExternalStoragePublicDir("project"),
                                PathUtils.getSDCard().musicPath,
                                PathUtils.getSDCard().musicDir,
                                PathUtils.getSDCard().podcastsPath,
                                PathUtils.getSDCard().podcastsDir,
                                PathUtils.getSDCard().ringtonesPath,
                                PathUtils.getSDCard().ringtonesDir,
                                PathUtils.getSDCard().alarmsPath,
                                PathUtils.getSDCard().alarmsDir,
                                PathUtils.getSDCard().notificationsPath,
                                PathUtils.getSDCard().notificationsDir,
                                PathUtils.getSDCard().picturesPath,
                                PathUtils.getSDCard().picturesDir,
                                PathUtils.getSDCard().moviesPath,
                                PathUtils.getSDCard().moviesDir,
                                PathUtils.getSDCard().downloadPath,
                                PathUtils.getSDCard().downloadDir,
                                PathUtils.getSDCard().dcimPath,
                                PathUtils.getSDCard().dcimDir,
                                PathUtils.getSDCard().documentsPath,
                                PathUtils.getSDCard().documentsDir,
                                PathUtils.getSDCard().audiobooksPath,
                                PathUtils.getSDCard().audiobooksDir,
                                ""
                            )
                            TAG.log_dTag(
                                message = builder.toString()
                            )
                            showToast(true, "信息已打印, 请查看 Logcat")
                        }

                        else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
                    }
                }
            }
        }
    }
)