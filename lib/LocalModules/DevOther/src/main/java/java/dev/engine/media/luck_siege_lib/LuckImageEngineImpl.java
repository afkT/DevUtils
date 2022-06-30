package java.dev.engine.media.luck_siege_lib;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.utils.ActivityCompatHelper;

import dev.other.R;

/**
 * detail: PictureSelector 相册图片加载引擎
 * @author luck
 * <pre>
 *     @see <a href="https://github.com/LuckSiege/PictureSelector/wiki"/>
 * </pre>
 */
public class LuckImageEngineImpl
        implements ImageEngine {

    // ==========
    // = 构造函数 =
    // ==========

    private LuckImageEngineImpl() {
    }

    private static final class Holder {
        static final LuckImageEngineImpl instance = new LuckImageEngineImpl();
    }

    public static LuckImageEngineImpl createEngine() {
        return Holder.instance;
    }

    // ===============
    // = ImageEngine =
    // ===============

    /**
     * 加载图片
     * @param context   Context
     * @param url       资源 URL
     * @param imageView 图片承载控件
     */
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
            ImageView imageView,
            String url,
            int maxWidth,
            int maxHeight
    ) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .override(maxWidth, maxHeight)
                .into(imageView);
    }

    /**
     * 加载相册目录封面
     * @param context   Context
     * @param url       图片路径
     * @param imageView 图片承载控件
     */
    @Override
    public void loadAlbumCover(
            Context context,
            String url,
            ImageView imageView
    ) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return;
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .override(180, 180)
                .sizeMultiplier(0.5f)
                .transform(new CenterCrop(), new RoundedCorners(8))
                .placeholder(R.drawable.ps_image_placeholder)
                .into(imageView);
    }

    /**
     * 加载图片列表图片
     * @param context   Context
     * @param url       图片路径
     * @param imageView 图片承载控件
     */
    @Override
    public void loadGridImage(
            Context context,
            String url,
            ImageView imageView
    ) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .override(200, 200)
                .centerCrop()
                .placeholder(R.drawable.ps_image_placeholder)
                .into(imageView);
    }

    @Override
    public void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }

    @Override
    public void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }
}