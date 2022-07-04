package java.dev.engine.media.luck_siege_lib;

import android.content.Context;
import android.net.Uri;

import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.utils.DateUtils;

import java.dev.other.LubanUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.utils.app.PathUtils;
import dev.utils.app.assist.ResourceAssist;
import dev.utils.common.FileUtils;
import top.zibin.luban.InputStreamProvider;
import top.zibin.luban.OnRenameListener;

/**
 * detail: PictureSelector 相册压缩引擎
 * @author luck
 */
public class LuckCompressFileEngineImpl
        implements CompressFileEngine {

    // 图片大于多少才进行压缩 ( kb )
    protected final int mMinimumCompressSize;

    // ==========
    // = 构造函数 =
    // ==========

    public LuckCompressFileEngineImpl(final int minimumCompressSize) {
        this.mMinimumCompressSize = minimumCompressSize;
    }

    // ======================
    // = CompressFileEngine =
    // ======================

    @Override
    public void onStartCompress(
            Context context,
            ArrayList<Uri> source,
            OnKeyValueResultCallbackListener call
    ) {
        if (call != null) {
            // 统一压缩存储路径 ( PictureSelector 库压缩存储 )
            final String targetDir = PathUtils.getInternal().getAppCachePath(
                    "Compress/PictureSelector"
            );
            FileUtils.createFolder(targetDir);
            // 压缩配置
            LubanUtils.Config compressConfig = new LubanUtils.Config(
                    mMinimumCompressSize, true, targetDir
            );
            // 转换待压缩数据
            List<InputStreamProvider> lists = toInputStreamList(context, source);
            LubanUtils.compress(lists, compressConfig, null, new OnRenameListener() {
                @Override
                public String rename(String filePath) {
                    int    indexOf = filePath.lastIndexOf(".");
                    String postfix = indexOf != -1 ? filePath.substring(indexOf) : ".jpg";
                    return DateUtils.getCreateFileName("CMP_") + postfix;
                }
            }, new LubanUtils.OnCompressListener() {
                @Override
                public void onStart(
                        int index,
                        int count
                ) {
                }

                @Override
                public void onSuccess(
                        File file,
                        int index,
                        int count
                ) {
                    InputStreamProvider stream  = lists.get(index);
                    String              srcPath = stream.getPath();
                    call.onCallback(srcPath, FileUtils.getAbsolutePath(file));
                }

                @Override
                public void onError(
                        Throwable error,
                        int index,
                        int count
                ) {
                    InputStreamProvider stream  = lists.get(index);
                    String              srcPath = stream.getPath();
                    call.onCallback(srcPath, srcPath);
                }

                @Override
                public void onComplete(
                        List<File> lists,
                        Map<Integer, File> maps,
                        int count
                ) {
                }
            });
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 转换待压缩数据
     * @param context Context
     * @param sources 待转换数据
     * @return Uris
     */
    protected List<InputStreamProvider> toInputStreamList(
            final Context context,
            final List<Uri> sources
    ) {
        List<InputStreamProvider> lists = new ArrayList<>();
        for (Uri uri : sources) {
            if (uri != null) {
                lists.add(new InputStreamProvider() {
                    @Override
                    public InputStream open()
                            throws IOException {
                        return ResourceAssist.get(context).openInputStream(uri);
                    }

                    @Override
                    public String getPath() {
                        return toUriPath(uri);
                    }
                });
            }
        }
        return lists;
    }

    /**
     * 获取 Uri 原始路径
     * @param uri Uri
     * @return Uri 原始路径
     */
    protected String toUriPath(final Uri uri) {
        if (uri != null) {
            String uriString = uri.toString();
            return PictureMimeType.isContent(uriString) ? uriString : uri.getPath();
        }
        return null;
    }
}