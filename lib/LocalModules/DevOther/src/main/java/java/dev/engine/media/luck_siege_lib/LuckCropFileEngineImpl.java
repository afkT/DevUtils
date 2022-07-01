package java.dev.engine.media.luck_siege_lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.engine.CropFileEngine;
import com.luck.picture.lib.utils.ActivityCompatHelper;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropImageEngine;

import java.util.ArrayList;

/**
 * detail: PictureSelector 相册裁剪引擎
 * @author luck
 */
public class LuckCropFileEngineImpl
        implements CropFileEngine {

    // UCrop.Options
    private final UCrop.Options mOptions;

    // ==========
    // = 构造函数 =
    // ==========

    public LuckCropFileEngineImpl(@NonNull final UCrop.Options options) {
        this.mOptions = options;
    }

    // ==================
    // = CropFileEngine =
    // ==================

    @Override
    public void onStartCrop(
            Fragment fragment,
            Uri srcUri,
            Uri destinationUri,
            ArrayList<String> dataSource,
            int requestCode
    ) {
        UCrop uCrop = UCrop.of(srcUri, destinationUri, dataSource);
        uCrop.setImageEngine(uCropImageEngine);
        uCrop.withOptions(mOptions);
        uCrop.start(fragment.requireContext(), fragment, requestCode);
    }

    // ==========
    // = 内部方法 =
    // ==========

    // 裁剪库图片加载引擎
    protected final UCropImageEngine uCropImageEngine = new UCropImageEngine() {
        @Override
        public void loadImage(
                Context context,
                String url,
                ImageView imageView
        ) {
            if (!ActivityCompatHelper.assertValidRequest(context)) {
                return;
            }
            Glide.with(context)
                    .load(url)
                    .into(imageView);
        }

        @Override
        public void loadImage(
                Context context,
                Uri url,
                int maxWidth,
                int maxHeight,
                OnCallbackListener<Bitmap> call
        ) {
            if (!ActivityCompatHelper.assertValidRequest(context)) {
                return;
            }
            Glide.with(context)
                    .asBitmap()
                    .override(maxWidth, maxHeight)
                    .load(url)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(
                                @NonNull Bitmap resource,
                                @Nullable Transition<? super Bitmap> transition
                        ) {
                            if (call != null) {
                                call.onCall(resource);
                            }
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            if (call != null) {
                                call.onCall(null);
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
        }
    };
}