package java.dev.engine.media.luck_siege_lib.listener;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnMediaEditInterceptListener;
import com.luck.picture.lib.utils.DateUtils;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import dev.utils.common.FileUtils;

/**
 * detail: PictureSelector 编辑拦截器
 * @author luck
 * <pre>
 *     图片预览 - 左下角编辑点击触发
 * </pre>
 */
public class LuckMediaEditInterceptListener
        implements OnMediaEditInterceptListener {

    // 编辑保存路径
    private final String        mOutputCropPath;
    // UCrop.Options
    private final UCrop.Options mOptions;

    // ==========
    // = 构造函数 =
    // ==========

    public LuckMediaEditInterceptListener(
            @NonNull final String outputCropPath,
            @NonNull final UCrop.Options options
    ) {
        this.mOutputCropPath = outputCropPath;
        this.mOptions        = options;
        // 创建文件夹防止未创建导致无法写入
        FileUtils.createFolder(outputCropPath);
    }

    // ================================
    // = OnMediaEditInterceptListener =
    // ================================

    @Override
    public void onStartMediaEdit(
            Fragment fragment,
            LocalMedia currentLocalMedia,
            int requestCode
    ) {
        String currentEditPath = currentLocalMedia.getAvailablePath();
        Uri    inputUri;
        if (PictureMimeType.isContent(currentEditPath)) {
            inputUri = Uri.parse(currentEditPath);
        } else {
            inputUri = Uri.fromFile(new File(currentEditPath));
        }
        Uri destinationUri = Uri.fromFile(new File(
                mOutputCropPath, DateUtils.getCreateFileName("CROP_") + ".jpeg"
        ));
        UCrop uCrop = UCrop.of(inputUri, destinationUri);
        uCrop.withOptions(mOptions);
        uCrop.startEdit(fragment.requireContext(), fragment, requestCode);
    }
}