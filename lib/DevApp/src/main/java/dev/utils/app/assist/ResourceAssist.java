package dev.utils.app.assist;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.AnyRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;

/**
 * detail: Resources 辅助类
 * @author Ttt
 * <pre>
 *     DevApp Utils 最终 Resources 获取工具类
 *     方便对 Resources 进行适配、统一控制、代码复用等
 * </pre>
 */
public final class ResourceAssist {

    // ResourceAssist 实例
    private volatile static ResourceAssist sInstance;

    /**
     * 获取 ResourceAssist 实例
     * @return {@link ResourceAssist}
     */
    public static ResourceAssist getInstance() {
        if (sInstance == null) {
            synchronized (ResourceAssist.class) {
                if (sInstance == null) {
                    sInstance = new ResourceAssist();
                }
            }
        }
        return sInstance;
    }

    // 日志 TAG
    private static final String TAG = ResourceAssist.class.getSimpleName();

    // Resources
    private Resources mResource;
    // 应用包名
    private String    mPackageName;

    // ===========
    // = 构造函数 =
    // ===========

    private ResourceAssist() {
        this(staticResources(), AppUtils.getPackageName());
    }

    private ResourceAssist(final Context context) {
        this(staticResources(context), AppUtils.getPackageName());
    }

    private ResourceAssist(final Resources resource) {
        this(resource, AppUtils.getPackageName());
    }

    private ResourceAssist(final Resources resource, final String packageName) {
        this.mResource = resource;
        this.mPackageName = packageName;
    }

    // ===============
    // = 快捷获取方法 =
    // ===============

    /**
     * 获取 ResourceAssist
     * @return {@link ResourceAssist}
     */
    public static ResourceAssist get() {
        return new ResourceAssist();
    }

    /**
     * 获取 ResourceAssist
     * @param context {@link Context}
     * @return {@link ResourceAssist}
     */
    public static ResourceAssist get(final Context context) {
        return new ResourceAssist(context);
    }

    /**
     * 获取 ResourceAssist
     * @param resource {@link Resources}
     * @return {@link ResourceAssist}
     */
    public static ResourceAssist get(final Resources resource) {
        return new ResourceAssist(resource);
    }

    /**
     * 获取 ResourceAssist
     * @param resource    {@link Resources}
     * @param packageName 应用包名
     * @return {@link ResourceAssist}
     */
    public static ResourceAssist get(final Resources resource, final String packageName) {
        return new ResourceAssist(resource, packageName);
    }

    // ===========
    // = 静态方法 =
    // ===========

    /**
     * 获取 Resources
     * @return {@link Resources}
     */
    public static Resources staticResources() {
        return staticResources(DevUtils.getContext());
    }

    /**
     * 获取 Resources
     * @param context {@link Context}
     * @return {@link Resources}
     */
    public static Resources staticResources(final Context context) {
        try {
            return context.getResources();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "staticResources");
        }
        return null;
    }

    /**
     * 获取 Resources.Theme
     * @return {@link Resources.Theme}
     */
    public static Resources.Theme staticTheme() {
        return staticTheme(DevUtils.getContext());
    }

    /**
     * 获取 Resources.Theme
     * @param context {@link Context}
     * @return {@link Resources.Theme}
     */
    public static Resources.Theme staticTheme(final Context context) {
        try {
            return context.getTheme();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "staticTheme");
        }
        return null;
    }

    /**
     * 获取 ContentResolver
     * @return {@link ContentResolver}
     */
    public static ContentResolver staticContentResolver() {
        return staticContentResolver(DevUtils.getContext());
    }

    /**
     * 获取 ContentResolver
     * @return {@link ContentResolver}
     */
    public static ContentResolver staticContentResolver(final Context context) {
        try {
            return context.getContentResolver();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "staticContentResolver");
        }
        return null;
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 重置操作
     * @param resource    {@link Resources}
     * @param packageName 应用包名
     * @return {@link ResourceAssist}
     */
    public ResourceAssist reset(final Resources resource, final String packageName) {
        this.mResource = resource;
        this.mPackageName = packageName;
        return this;
    }

    /**
     * 获取应用包名
     * @return 应用包名
     */
    public String getPackageName() {
        return mPackageName;
    }

//    /**
//     * 获取 Resources
//     * @return {@link Resources}
//     */
//    public Resources getResources() {
//        return mResource;
//    }

    /**
     * 获取 DisplayMetrics
     * @return {@link DisplayMetrics}
     */
    public DisplayMetrics getDisplayMetrics() {
        try {
            return mResource.getDisplayMetrics();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDisplayMetrics");
        }
        return null;
    }

    /**
     * 获取 Configuration
     * @return {@link Configuration}
     */
    public Configuration getConfiguration() {
        try {
            return mResource.getConfiguration();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getConfiguration");
        }
        return null;
    }

    /**
     * 获取 AssetManager
     * @return {@link AssetManager}
     */
    public AssetManager getAssets() {
        try {
            return mResource.getAssets();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAssets");
        }
        return null;
    }

    /**
     * 获取资源 id
     * @param resName 资源名
     * @param defType 资源类型
     * @return 资源 id
     */
    public int getIdentifier(final String resName, final String defType) {
        try {
            return mResource.getIdentifier(resName, defType, mPackageName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getIdentifier - " + resName + " " + defType + ": " + mPackageName);
        }
        return 0;
    }

    /**
     * 获取给定资源标识符的全名
     * @param id resource identifier
     * @return Integer
     */
    public String getResourceName(@AnyRes final int id) {
        try {
            return mResource.getResourceName(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getResourceName");
        }
        return null;
    }

    // ===========
    // = 资源获取 =
    // ===========

    /**
     * 获取 String id
     * @param resName resource name
     * @return String id
     */
    public int getStringId(final String resName) {
        return getIdentifier(resName, "string");
    }

    /**
     * 获取 String
     * @param resName resource name
     * @return String
     */
    public String getString(final String resName) {
        return getString(getStringId(resName));
    }

    /**
     * 获取 String
     * @param resName    resource name
     * @param formatArgs 格式化参数
     * @return String
     */
    public String getString(final String resName, final Object... formatArgs) {
        return getString(getStringId(resName), formatArgs);
    }

    /**
     * 获取 String
     * @param id R.string.id
     * @return String
     */
    public String getString(@StringRes final int id) {
        try {
            return mResource.getString(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getString");
        }
        return null;
    }

    /**
     * 获取 String
     * @param id         R.string.id
     * @param formatArgs 格式化参数
     * @return String
     */
    public String getString(@StringRes final int id, final Object... formatArgs) {
        try {
            return mResource.getString(id, formatArgs);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getString");
        }
        return null;
    }

    // =

    /**
     * 获取 Dimension id
     * @param resName resource name
     * @return Dimension id
     */
    public int getDimenId(final String resName) {
        return getIdentifier(resName, "dimen");
    }

    /**
     * 获取 Dimension
     * @param resName resource name
     * @return Dimension
     */
    public float getDimension(final String resName) {
        return getDimension(getDimenId(resName));
    }

    /**
     * 获取 Dimension
     * @param resName resource name
     * @return Dimension
     */
    public int getDimensionInt(final String resName) {
        return getDimensionInt(getDimenId(resName));
    }

    /**
     * 获取 Dimension
     * @param id resource identifier
     * @return Dimension
     */
    public float getDimension(@DimenRes final int id) {
        try {
            return mResource.getDimension(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDimension");
        }
        return 0f;
    }

    /**
     * 获取 Dimension
     * @param id resource identifier
     * @return Dimension
     */
    public int getDimensionInt(@DimenRes final int id) {
        return (int) getDimension(id);
    }

    // =

    /**
     * 获取 Color id
     * @param resName resource name
     * @return Color id
     */
    public int getColorId(final String resName) {
        return getIdentifier(resName, "color");
    }

    /**
     * 获取 Color
     * @param resName resource name
     * @return Color
     */
    public int getColor(final String resName) {
        return getColor(getColorId(resName));
    }

    /**
     * 获取 Color
     * <pre>
     *     {@link ContextCompat#getColor(Context, int)}
     * </pre>
     * @param colorId R.color.id
     * @return Color
     */
    public int getColor(@ColorRes final int colorId) {
        try {
            return mResource.getColor(colorId);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getColor");
        }
        return -1;
    }

    // =

    /**
     * 获取 Drawable id
     * @param resName resource name
     * @return Drawable id
     */
    public int getDrawableId(final String resName) {
        return getIdentifier(resName, "drawable");
    }

    /**
     * 获取 Drawable
     * @param resName resource name
     * @return {@link Drawable}
     */
    public Drawable getDrawable(final String resName) {
        return getDrawable(getDrawableId(resName));
    }

    /**
     * 获取 .9 Drawable
     * @param resName resource name
     * @return .9 {@link NinePatchDrawable}
     */
    public NinePatchDrawable getNinePatchDrawable(final String resName) {
        return getNinePatchDrawable(getDrawableId(resName));
    }

    /**
     * 获取 Drawable
     * <pre>
     *     {@link ContextCompat#getDrawable(Context, int)}
     * </pre>
     * @param drawableId R.drawable.id
     * @return {@link Drawable}
     */
    public Drawable getDrawable(@DrawableRes final int drawableId) {
        try {
            return mResource.getDrawable(drawableId);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDrawable");
        }
        return null;
    }

    /**
     * 获取 .9 Drawable
     * @param drawableId R.drawable.id
     * @return .9 {@link NinePatchDrawable}
     */
    public NinePatchDrawable getNinePatchDrawable(@DrawableRes final int drawableId) {
        try {
            return (NinePatchDrawable) getDrawable(drawableId);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getNinePatchDrawable");
        }
        return null;
    }

    // =

    /**
     * 获取 Bitmap
     * @param resName resource name
     * @return {@link Bitmap}
     */
    public Bitmap getBitmap(final String resName) {
        return getBitmap(getDrawableId(resName));
    }

    /**
     * 获取 Bitmap
     * @param resName resource name
     * @param options {@link BitmapFactory.Options}
     * @return {@link Bitmap}
     */
    public Bitmap getBitmap(final String resName, final BitmapFactory.Options options) {
        return getBitmap(getDrawableId(resName), options);
    }

    /**
     * 获取 Bitmap
     * @param resId resource identifier
     * @return {@link Bitmap}
     */
    public Bitmap getBitmap(final int resId) {
        try {
            return BitmapFactory.decodeResource(mResource, resId);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmap");
        }
        return null;
    }

    /**
     * 获取 Bitmap
     * @param resId   resource identifier
     * @param options {@link BitmapFactory.Options}
     * @return {@link Bitmap}
     */
    public Bitmap getBitmap(final int resId, final BitmapFactory.Options options) {
        try {
            return BitmapFactory.decodeResource(mResource, resId, options);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmap");
        }
        return null;
    }

    // =

    /**
     * 获取 Mipmap id
     * @param resName resource name
     * @return Mipmap id
     */
    public int getMipmapId(final String resName) {
        return getIdentifier(resName, "mipmap");
    }

    /**
     * 获取 Mipmap Drawable
     * @param resName resource name
     * @return {@link Drawable}
     */
    public Drawable getDrawableMipmap(final String resName) {
        return getDrawable(getMipmapId(resName));
    }

    /**
     * 获取 Mipmap .9 Drawable
     * @param resName resource name
     * @return .9 {@link NinePatchDrawable}
     */
    public NinePatchDrawable getNinePatchDrawableMipmap(final String resName) {
        return getNinePatchDrawable(getMipmapId(resName));
    }

    /**
     * 获取 Mipmap Bitmap
     * @param resName resource name
     * @return {@link Bitmap}
     */
    public Bitmap getBitmapMipmap(final String resName) {
        return getBitmap(getMipmapId(resName));
    }

    /**
     * 获取 Mipmap Bitmap
     * @param resName resource name
     * @param options {@link BitmapFactory.Options}
     * @return {@link Bitmap}
     */
    public Bitmap getBitmapMipmap(final String resName, final BitmapFactory.Options options) {
        return getBitmap(getMipmapId(resName), options);
    }

    // =

    /**
     * 获取 anim id
     * @param resName resource name
     * @return anim id
     */
    public int getAnimId(final String resName) {
        return getIdentifier(resName, "anim");
    }

    /**
     * 获取 Animation Xml
     * @param resName resource name
     * @return {@link XmlResourceParser}
     */
    public XmlResourceParser getAnimationXml(final String resName) {
        return getAnimationXml(getAnimId(resName));
    }

    /**
     * 获取 Animation Xml
     * @param id resource identifier
     * @return {@link XmlResourceParser}
     */
    public XmlResourceParser getAnimationXml(@AnimatorRes @AnimRes final int id) {
        try {
            return mResource.getAnimation(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAnimationXml");
        }
        return null;
    }

    /**
     * 获取 Animation
     * @param resName resource name
     * @return {@link XmlResourceParser}
     */
    public Animation getAnimation(final String resName) {
        return getAnimation(getAnimId(resName));
    }

    /**
     * 获取 Animation
     * @param resName resource name
     * @param context {@link Context}
     * @return {@link XmlResourceParser}
     */
    public Animation getAnimation(final String resName, final Context context) {
        return getAnimation(getAnimId(resName), context);
    }

    /**
     * 获取 Animation
     * @param id resource identifier
     * @return {@link XmlResourceParser}
     */
    public Animation getAnimation(@AnimatorRes @AnimRes final int id) {
        return getAnimation(id, DevUtils.getContext());
    }

    /**
     * 获取 Animation
     * @param id      resource identifier
     * @param context {@link Context}
     * @return {@link XmlResourceParser}
     */
    public Animation getAnimation(@AnimatorRes @AnimRes final int id, final Context context) {
        try {
            return AnimationUtils.loadAnimation(context, id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAnimation");
        }
        return null;
    }
}