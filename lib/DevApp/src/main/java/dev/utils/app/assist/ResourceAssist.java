package dev.utils.app.assist;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.AnyRes;
import androidx.annotation.ArrayRes;
import androidx.annotation.BoolRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.RawRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;
import dev.utils.common.CloseUtils;
import dev.utils.common.FileIOUtils;

/**
 * detail: Resources 辅助类
 * @author Ttt
 * <pre>
 *     整合 DevApp Utils 代码, 最终统一通过该辅助工具类进行 Resources 获取
 *     方便对 Resources 进行适配、统一控制、代码复用等
 * </pre>
 */
public final class ResourceAssist {

    // ResourceAssist 实例
    private static volatile ResourceAssist sInstance;

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

    // 空实现 ResourceAssist ( 用于 ResourcePluginUtils 简化判断代码 )
    public static final ResourceAssist EMPTY_IMPL = new ResourceAssist(null, null);

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

    private ResourceAssist(
            final Resources resource,
            final String packageName
    ) {
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
//        return new ResourceAssist();
        return getInstance();
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
    public static ResourceAssist get(
            final Resources resource,
            final String packageName
    ) {
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
        if (context == null) return null;
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
        if (context == null) return null;
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
     * @param context {@link Context}
     * @return {@link ContentResolver}
     */
    public static ContentResolver staticContentResolver(final Context context) {
        if (context == null) return null;
        try {
            return context.getContentResolver();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "staticContentResolver");
        }
        return null;
    }

    /**
     * 获取 DisplayMetrics
     * @return {@link DisplayMetrics}
     */
    public static DisplayMetrics staticDisplayMetrics() {
        return staticDisplayMetrics(staticResources());
    }

    /**
     * 获取 DisplayMetrics
     * @param context {@link Context}
     * @return {@link DisplayMetrics}
     */
    public static DisplayMetrics staticDisplayMetrics(final Context context) {
        return staticDisplayMetrics(staticResources(context));
    }

    /**
     * 获取 DisplayMetrics
     * @param resource {@link Resources}
     * @return {@link DisplayMetrics}
     */
    public static DisplayMetrics staticDisplayMetrics(final Resources resource) {
        if (resource == null) return null;
        try {
            return resource.getDisplayMetrics();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "staticDisplayMetrics");
        }
        return null;
    }

    /**
     * 获取 Configuration
     * @return {@link Configuration}
     */
    public static Configuration staticConfiguration() {
        return staticConfiguration(staticResources());
    }

    /**
     * 获取 Configuration
     * @param context {@link Context}
     * @return {@link Configuration}
     */
    public static Configuration staticConfiguration(final Context context) {
        return staticConfiguration(staticResources(context));
    }

    /**
     * 获取 Configuration
     * @param resource {@link Resources}
     * @return {@link Configuration}
     */
    public static Configuration staticConfiguration(final Resources resource) {
        if (resource == null) return null;
        try {
            return resource.getConfiguration();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "staticConfiguration");
        }
        return null;
    }

    /**
     * 获取 AssetManager
     * @return {@link AssetManager}
     */
    public static AssetManager staticAssets() {
        return staticAssets(staticResources());
    }

    /**
     * 获取 AssetManager
     * @param context {@link Context}
     * @return {@link AssetManager}
     */
    public static AssetManager staticAssets(final Context context) {
        return staticAssets(staticResources(context));
    }

    /**
     * 获取 AssetManager
     * @param resource {@link Resources}
     * @return {@link AssetManager}
     */
    public static AssetManager staticAssets(final Resources resource) {
        if (resource == null) return null;
        try {
            return resource.getAssets();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "staticAssets");
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
    public ResourceAssist reset(
            final Resources resource,
            final String packageName
    ) {
        if (this == EMPTY_IMPL) return this;
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

    /**
     * 获取 Resources
     * @return {@link Resources}
     */
    public Resources getResources() {
        return mResource;
    }

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
    public int getIdentifier(
            final String resName,
            final String defType
    ) {
        try {
            return mResource.getIdentifier(resName, defType, mPackageName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getIdentifier - %s %s: %s", resName, defType, mPackageName);
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
    public String getString(
            final String resName,
            final Object... formatArgs
    ) {
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
    public String getString(
            @StringRes final int id,
            final Object... formatArgs
    ) {
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
    public Bitmap getBitmap(
            final String resName,
            final BitmapFactory.Options options
    ) {
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
    public Bitmap getBitmap(
            final int resId,
            final BitmapFactory.Options options
    ) {
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
    public Bitmap getBitmapMipmap(
            final String resName,
            final BitmapFactory.Options options
    ) {
        return getBitmap(getMipmapId(resName), options);
    }

    // =

    /**
     * 获取 Anim id
     * @param resName resource name
     * @return Anim id
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
    public Animation getAnimation(
            final String resName,
            final Context context
    ) {
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
    public Animation getAnimation(
            @AnimatorRes @AnimRes final int id,
            final Context context
    ) {
        try {
            return AnimationUtils.loadAnimation(context, id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAnimation");
        }
        return null;
    }

    // =

    /**
     * 获取 Boolean id
     * @param resName resource name
     * @return Boolean id
     */
    public int getBooleanId(final String resName) {
        return getIdentifier(resName, "bool");
    }

    /**
     * 获取 Boolean
     * @param resName resource name
     * @return Boolean
     */
    public boolean getBoolean(final String resName) {
        return getBoolean(getBooleanId(resName));
    }

    /**
     * 获取 Boolean
     * @param id resource identifier
     * @return Boolean
     */
    public boolean getBoolean(@BoolRes final int id) {
        try {
            return mResource.getBoolean(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBoolean");
        }
        return false;
    }

    // =

    /**
     * 获取 Integer id
     * @param resName resource name
     * @return Integer id
     */
    public int getIntegerId(final String resName) {
        return getIdentifier(resName, "integer");
    }

    /**
     * 获取 Integer
     * @param resName resource name
     * @return Integer
     */
    public int getInteger(final String resName) {
        return getInteger(getIntegerId(resName));
    }

    /**
     * 获取 Integer
     * @param id resource identifier
     * @return Integer
     */
    public int getInteger(@IntegerRes final int id) {
        try {
            return mResource.getInteger(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getInteger");
        }
        return -1;
    }

    // =

    /**
     * 获取 Array id
     * @param resName resource name
     * @return Array id
     */
    public int getArrayId(final String resName) {
        return getIdentifier(resName, "array");
    }

    /**
     * 获取 int[]
     * @param resName resource name
     * @return int[]
     */
    public int[] getIntArray(final String resName) {
        return getIntArray(getArrayId(resName));
    }

    /**
     * 获取 String[]
     * @param resName resource name
     * @return String[]
     */
    public String[] getStringArray(final String resName) {
        return getStringArray(getArrayId(resName));
    }

    /**
     * 获取 CharSequence[]
     * @param resName resource name
     * @return CharSequence[]
     */
    public CharSequence[] getTextArray(final String resName) {
        return getTextArray(getArrayId(resName));
    }

    /**
     * 获取 int[]
     * @param id resource identifier
     * @return int[]
     */
    public int[] getIntArray(@ArrayRes final int id) {
        try {
            return mResource.getIntArray(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getIntArray");
        }
        return null;
    }

    /**
     * 获取 String[]
     * @param id resource identifier
     * @return String[]
     */
    public String[] getStringArray(@ArrayRes final int id) {
        try {
            return mResource.getStringArray(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getStringArray");
        }
        return null;
    }

    /**
     * 获取 CharSequence[]
     * @param id resource identifier
     * @return CharSequence[]
     */
    public CharSequence[] getTextArray(@ArrayRes final int id) {
        try {
            return mResource.getTextArray(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getTextArray");
        }
        return null;
    }

    // =

    /**
     * 获取 id ( view )
     * @param resName resource name
     * @return id
     */
    public int getId(final String resName) {
        return getIdentifier(resName, "id");
    }

    /**
     * 获取 Layout id
     * <pre>
     *     {@link android.view.LayoutInflater#inflate(int, ViewGroup)}
     * </pre>
     * @param resName resource name
     * @return Layout id
     */
    public int getLayoutId(final String resName) {
        return getIdentifier(resName, "layout");
    }

    /**
     * 获取 Menu id
     * <pre>
     *     {@link android.view.MenuInflater#inflate(int, Menu)}
     * </pre>
     * @param resName resource name
     * @return Menu id
     */
    public int getMenuId(final String resName) {
        return getIdentifier(resName, "menu");
    }

    /**
     * 获取 Raw id
     * @param resName resource name
     * @return Raw id
     */
    public int getRawId(final String resName) {
        return getIdentifier(resName, "raw");
    }

    /**
     * 获取 Attr id
     * @param resName resource name
     * @return Attr id
     */
    public int getAttrId(final String resName) {
        return getIdentifier(resName, "attr");
    }

    /**
     * 获取 Style id
     * @param resName resource name
     * @return Style id
     */
    public int getStyleId(final String resName) {
        return getIdentifier(resName, "style");
    }

    /**
     * 获取 Styleable id
     * @param resName resource name
     * @return Styleable id
     */
    public int getStyleableId(final String resName) {
        return getIdentifier(resName, "styleable");
    }

    /**
     * 获取 Animator id
     * @param resName resource name
     * @return Animator id
     */
    public int getAnimatorId(final String resName) {
        return getIdentifier(resName, "animator");
    }

    /**
     * 获取 Xml id
     * @param resName resource name
     * @return Xml id
     */
    public int getXmlId(final String resName) {
        return getIdentifier(resName, "xml");
    }

    /**
     * 获取 Interpolator id
     * @param resName resource name
     * @return Interpolator id
     */
    public int getInterpolatorId(final String resName) {
        return getIdentifier(resName, "interpolator");
    }

    /**
     * 获取 Plurals id
     * @param resName resource name
     * @return Plurals id
     */
    public int getPluralsId(final String resName) {
        return getIdentifier(resName, "plurals");
    }

    // =

    /**
     * 获取 ColorStateList
     * @param resName resource Name
     * @return {@link ColorStateList}
     */
    public ColorStateList getColorStateList(final String resName) {
        return getColorStateList(getColorId(resName));
    }

    /**
     * 获取 ColorStateList
     * <pre>
     *     {@link ContextCompat#getColorStateList(Context, int)}
     * </pre>
     * @param id resource identifier of a {@link ColorStateList}
     * @return {@link ColorStateList}
     */
    public ColorStateList getColorStateList(@ColorRes final int id) {
        try {
            return mResource.getColorStateList(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getColorStateList");
        }
        return null;
    }

    /**
     * 获取十六进制颜色值 Drawable
     * @param color 十六进制颜色值
     * @return 十六进制颜色值 Drawable
     */
    public ColorDrawable getColorDrawable(final String color) {
        try {
            return new ColorDrawable(Color.parseColor(color));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getColorDrawable");
        }
        return null;
    }

    /**
     * 获取指定颜色 Drawable
     * @param color 颜色值
     * @return 指定颜色 Drawable
     */
    public ColorDrawable getColorDrawable(@ColorInt final int color) {
        try {
            return new ColorDrawable(color);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getColorDrawable");
        }
        return null;
    }

    // ===================
    // = ContentResolver =
    // ===================

    /**
     * 获取 Uri InputStream
     * @param uri {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @return Uri InputStream
     */
    public InputStream openInputStream(final Uri uri) {
        return openInputStream(uri, staticContentResolver());
    }

    /**
     * 获取 Uri InputStream
     * <pre>
     *     主要用于获取到分享的 FileProvider Uri 存储起来 {@link FileIOUtils#writeFileFromIS(File, InputStream)}
     * </pre>
     * @param uri      {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @param resolver {@link ContentResolver}
     * @return Uri InputStream
     */
    public InputStream openInputStream(
            final Uri uri,
            final ContentResolver resolver
    ) {
        if (uri == null) return null;
        try {
            return resolver.openInputStream(uri);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openInputStream %s", uri.toString());
        }
        return null;
    }

    /**
     * 获取 Uri OutputStream
     * @param uri {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @return Uri OutputStream
     */
    public OutputStream openOutputStream(final Uri uri) {
        return openOutputStream(uri, staticContentResolver());
    }

    /**
     * 获取 Uri OutputStream
     * @param uri      {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @param resolver {@link ContentResolver}
     * @return Uri OutputStream
     */
    public OutputStream openOutputStream(
            final Uri uri,
            final ContentResolver resolver
    ) {
        if (uri == null) return null;
        try {
            return resolver.openOutputStream(uri);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openOutputStream %s", uri.toString());
        }
        return null;
    }

    /**
     * 获取 Uri OutputStream
     * @param uri  {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @param mode 读写模式
     * @return Uri OutputStream
     */
    public OutputStream openOutputStream(
            final Uri uri,
            final String mode
    ) {
        return openOutputStream(uri, mode, staticContentResolver());
    }

    /**
     * 获取 Uri OutputStream
     * @param uri      {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @param mode     读写模式
     * @param resolver {@link ContentResolver}
     * @return Uri OutputStream
     */
    public OutputStream openOutputStream(
            final Uri uri,
            final String mode,
            final ContentResolver resolver
    ) {
        if (uri == null) return null;
        try {
            return resolver.openOutputStream(uri, mode);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openOutputStream mode: %s, %s", mode, uri.toString());
        }
        return null;
    }

    /**
     * 获取 Uri ParcelFileDescriptor
     * @param uri  {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @param mode 读写模式
     * @return Uri ParcelFileDescriptor
     */
    public ParcelFileDescriptor openFileDescriptor(
            final Uri uri,
            final String mode
    ) {
        return openFileDescriptor(uri, mode, staticContentResolver());
    }

    /**
     * 获取 Uri ParcelFileDescriptor
     * <pre>
     *     通过 new FileInputStream(openFileDescriptor().getFileDescriptor()) 进行文件操作
     * </pre>
     * @param uri      {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @param mode     读写模式
     * @param resolver {@link ContentResolver}
     * @return Uri ParcelFileDescriptor
     */
    public ParcelFileDescriptor openFileDescriptor(
            final Uri uri,
            final String mode,
            final ContentResolver resolver
    ) {
        if (uri == null || TextUtils.isEmpty(mode)) return null;
        try {
            return resolver.openFileDescriptor(uri, mode);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openFileDescriptor mode: %s, %s", mode, uri.toString());
        }
        return null;
    }

    /**
     * 获取 Uri AssetFileDescriptor
     * @param uri  {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @param mode 读写模式
     * @return Uri AssetFileDescriptor
     */
    public AssetFileDescriptor openAssetFileDescriptor(
            final Uri uri,
            final String mode
    ) {
        return openAssetFileDescriptor(uri, mode, staticContentResolver());
    }

    /**
     * 获取 Uri AssetFileDescriptor
     * <pre>
     *     通过 new FileInputStream(openAssetFileDescriptor().getFileDescriptor()) 进行文件操作
     * </pre>
     * @param uri      {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @param mode     读写模式
     * @param resolver {@link ContentResolver}
     * @return Uri AssetFileDescriptor
     */
    public AssetFileDescriptor openAssetFileDescriptor(
            final Uri uri,
            final String mode,
            final ContentResolver resolver
    ) {
        if (uri == null || TextUtils.isEmpty(mode)) return null;
        try {
            return resolver.openAssetFileDescriptor(uri, mode);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openAssetFileDescriptor mode: %s, %s", mode, uri.toString());
        }
        return null;
    }

    // ================
    // = AssetManager =
    // ================

    /**
     * 获取 AssetManager 指定资源 InputStream
     * @param fileName 文件名
     * @return {@link InputStream}
     */
    public InputStream open(final String fileName) {
        try {
            return getAssets().open(fileName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "open");
        }
        return null;
    }

    /**
     * 获取 AssetManager 指定资源 AssetFileDescriptor
     * @param fileName 文件名
     * @return {@link AssetFileDescriptor}
     */
    public AssetFileDescriptor openFd(final String fileName) {
        try {
            return getAssets().openFd(fileName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openFd");
        }
        return null;
    }

    /**
     * 获取 AssetManager 指定资源 AssetFileDescriptor
     * @param fileName 文件名
     * @return {@link AssetFileDescriptor}
     */
    public AssetFileDescriptor openNonAssetFd(final String fileName) {
        try {
            return getAssets().openNonAssetFd(fileName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openNonAssetFd");
        }
        return null;
    }

    /**
     * 获取对应资源 InputStream
     * @param id resource identifier
     * @return {@link InputStream}
     */
    public InputStream openRawResource(@RawRes final int id) {
        try {
            return mResource.openRawResource(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openRawResource");
        }
        return null;
    }

    /**
     * 获取对应资源 AssetFileDescriptor
     * @param id resource identifier
     * @return {@link AssetFileDescriptor}
     */
    public AssetFileDescriptor openRawResourceFd(@RawRes final int id) {
        try {
            return mResource.openRawResourceFd(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openRawResourceFd");
        }
        return null;
    }

    // ===============
    // = 读取资源文件 =
    // ===============

    /**
     * 获取 Assets 资源文件数据
     * <pre>
     *     直接传入文件名、文件夹 / 文件名 等
     *     根目录 a.txt
     *     子目录 /www/a.html
     * </pre>
     * @param fileName 文件名
     * @return 文件 byte[] 数据
     */
    public byte[] readBytesFromAssets(final String fileName) {
        InputStream is = null;
        try {
            is = open(fileName);
            int    length = is.available();
            byte[] buffer = new byte[length];
            is.read(buffer);
            return buffer;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "readBytesFromAssets");
        } finally {
            CloseUtils.closeIOQuietly(is);
        }
        return null;
    }

    /**
     * 获取 Assets 资源文件数据
     * @param fileName 文件名
     * @return 文件字符串内容
     */
    public String readStringFromAssets(final String fileName) {
        try {
            return new String(readBytesFromAssets(fileName), "UTF-8");
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "readStringFromAssets");
        }
        return null;
    }

    // =

    /**
     * 获取 Raw 资源文件数据
     * @param resId 资源 id
     * @return 文件 byte[] 数据
     */
    public byte[] readBytesFromRaw(@RawRes final int resId) {
        InputStream is = null;
        try {
            is = openRawResource(resId);
            int    length = is.available();
            byte[] buffer = new byte[length];
            is.read(buffer);
            return buffer;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "readBytesFromRaw");
        } finally {
            CloseUtils.closeIOQuietly(is);
        }
        return null;
    }

    /**
     * 获取 Raw 资源文件数据
     * @param resId 资源 id
     * @return 文件字符串内容
     */
    public String readStringFromRaw(@RawRes final int resId) {
        try {
            return new String(readBytesFromRaw(resId), "UTF-8");
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "readStringFromRaw");
        }
        return null;
    }

    // =

    /**
     * 获取 Assets 资源文件数据 ( 返回 List<String> 一行的全部内容属于一个索引 )
     * @param fileName 文件名
     * @return {@link List <String>}
     */
    public List<String> geFileToListFromAssets(final String fileName) {
        InputStream    is = null;
        BufferedReader br = null;
        try {
            is = open(fileName);
            br = new BufferedReader(new InputStreamReader(is));

            List<String> lists = new ArrayList<>();
            String       line;
            while ((line = br.readLine()) != null) {
                lists.add(line);
            }
            return lists;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "geFileToListFromAssets");
        } finally {
            CloseUtils.closeIOQuietly(is, br);
        }
        return null;
    }

    /**
     * 获取 Raw 资源文件数据 ( 返回 List<String> 一行的全部内容属于一个索引 )
     * @param resId 资源 id
     * @return {@link List<String>}
     */
    public List<String> geFileToListFromRaw(@RawRes final int resId) {
        InputStream    is = null;
        BufferedReader br = null;
        try {
            is = openRawResource(resId);
            br = new BufferedReader(new InputStreamReader(is));

            List<String> lists = new ArrayList<>();
            String       line;
            while ((line = br.readLine()) != null) {
                lists.add(line);
            }
            return lists;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "geFileToListFromRaw");
        } finally {
            CloseUtils.closeIOQuietly(is, br);
        }
        return null;
    }

    // =

    /**
     * 获取 Assets 资源文件数据并保存到本地
     * @param fileName 文件名
     * @param file     文件保存地址
     * @return {@code true} success, {@code false} fail
     */
    public boolean saveAssetsFormFile(
            final String fileName,
            final File file
    ) {
        try {
            // 获取 Assets 文件
            InputStream is = open(fileName);
            // 存入 SDCard
            FileOutputStream fos = new FileOutputStream(file);
            // 设置数据缓冲
            byte[] buffer = new byte[1024];
            // 创建输入输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int                   len;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            // 保存数据
            byte[] bytes = baos.toByteArray();
            // 写入保存的文件
            fos.write(bytes);
            // 关闭流
            CloseUtils.closeIOQuietly(baos, is);
            CloseUtils.flushCloseIOQuietly(fos);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "saveAssetsFormFile");
        }
        return false;
    }

    /**
     * 获取 Raw 资源文件数据并保存到本地
     * @param resId 资源 id
     * @param file  文件保存地址
     * @return {@code true} success, {@code false} fail
     */
    public boolean saveRawFormFile(
            @RawRes final int resId,
            final File file
    ) {
        try {
            // 获取 raw 文件
            InputStream is = openRawResource(resId);
            // 存入 SDCard
            FileOutputStream fos = new FileOutputStream(file);
            // 设置数据缓冲
            byte[] buffer = new byte[1024];
            // 创建输入输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int                   len;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            // 保存数据
            byte[] bytes = baos.toByteArray();
            // 写入保存的文件
            fos.write(bytes);
            // 关闭流
            CloseUtils.closeIOQuietly(baos, is);
            CloseUtils.flushCloseIOQuietly(fos);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "saveRawFormFile");
        }
        return false;
    }
}