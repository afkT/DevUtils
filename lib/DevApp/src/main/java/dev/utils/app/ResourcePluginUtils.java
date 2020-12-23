package dev.utils.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.animation.Animation;

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

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.assist.ResourceAssist;
import dev.utils.app.info.ApkInfoItem;
import dev.utils.app.info.AppInfoBean;
import dev.utils.app.info.AppInfoUtils;
import dev.utils.common.FileUtils;

/**
 * detail: APK Resource 工具类
 * @author JiZhi-Error <a href="https://github.com/JiZhi-Error"/>
 * @author Ttt
 * <pre>
 *     从 APK 中读取 Resources ( 可实现换肤、组件化工具类 )
 * </pre>
 */
public final class ResourcePluginUtils {

    private ResourcePluginUtils() {
    }

    // 日志 TAG
    private static final String TAG = ResourcePluginUtils.class.getSimpleName();

    // Resources 辅助类
    private ResourceAssist mResourceAssist = ResourceAssist.EMPTY_IMPL;
    // APK 文件路径
    private String         mAPKPath;
    // APK 信息 Item
    private ApkInfoItem    mApkInfoItem;

    // =======================
    // = invokeByPackageName =
    // =======================

    /**
     * 通过 packageName 获取 APK Resources
     * @param packageName 应用包名
     * @return {@link ResourcePluginUtils}
     */
    public static ResourcePluginUtils invokeByPackageName(final String packageName) {
        return invokeByPackageName(packageName, DevUtils.getContext());
    }

    /**
     * 通过 packageName 获取 APK Resources
     * @param packageName 应用包名
     * @param context     {@link Context}
     * @return {@link ResourcePluginUtils}
     */
    public static ResourcePluginUtils invokeByPackageName(
            final String packageName,
            final Context context
    ) {
        DisplayMetrics metrics   = null;
        Configuration  config    = null;
        Resources      resources = ResourceAssist.staticResources(context);
        if (resources != null) {
            metrics = resources.getDisplayMetrics();
            config = resources.getConfiguration();
        }
        return invokeByPackageName(packageName, metrics, config);
    }

    /**
     * 通过 packageName 获取 APK Resources
     * @param packageName 应用包名
     * @param metrics     {@link DisplayMetrics}
     * @param config      {@link Configuration}
     * @return {@link ResourcePluginUtils}
     */
    public static ResourcePluginUtils invokeByPackageName(
            final String packageName,
            final DisplayMetrics metrics,
            final Configuration config
    ) {
        AppInfoBean appInfoBean = AppInfoUtils.getAppInfoBean(packageName);
        String      sourceDir   = (appInfoBean != null) ? appInfoBean.getSourceDir() : null;
        return invokeByAPKPath(sourceDir, metrics, config);
    }

    // ===================
    // = invokeByAPKPath =
    // ===================

    /**
     * 通过 APK 文件获取 APK Resources
     * @param apkPath APK 文件路径
     * @return {@link ResourcePluginUtils}
     */
    public static ResourcePluginUtils invokeByAPKPath(final String apkPath) {
        return invokeByAPKPath(apkPath, DevUtils.getContext());
    }

    /**
     * 通过 APK 文件获取 APK Resources
     * @param apkPath APK 文件路径
     * @param context {@link Context}
     * @return {@link ResourcePluginUtils}
     */
    public static ResourcePluginUtils invokeByAPKPath(
            final String apkPath,
            final Context context
    ) {
        DisplayMetrics metrics   = null;
        Configuration  config    = null;
        Resources      resources = ResourceAssist.staticResources(context);
        if (resources != null) {
            metrics = resources.getDisplayMetrics();
            config = resources.getConfiguration();
        }
        return invokeByAPKPath(apkPath, metrics, config);
    }

    /**
     * 通过 APK 文件获取 APK Resources
     * @param apkPath APK 文件路径
     * @param metrics {@link DisplayMetrics}
     * @param config  {@link Configuration}
     * @return {@link ResourcePluginUtils}
     */
    public static ResourcePluginUtils invokeByAPKPath(
            final String apkPath,
            final DisplayMetrics metrics,
            final Configuration config
    ) {
        ResourcePluginUtils utils = new ResourcePluginUtils();
        utils.mAPKPath = apkPath;
        // 文件存在才进行处理
        if (FileUtils.isFileExists(apkPath)) {
            try {
                AssetManager asset        = AssetManager.class.newInstance();
                Method       addAssetPath = asset.getClass().getMethod("addAssetPath", String.class);
                addAssetPath.invoke(asset, apkPath);
                Resources resources = new Resources(
                        asset, metrics, config
                );
                PackageInfo packageInfo = AppUtils.getPackageManager().getPackageArchiveInfo(
                        apkPath, PackageManager.GET_ACTIVITIES
                );
                utils.mResourceAssist = ResourceAssist.get(
                        resources, packageInfo.packageName
                );
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "invokeByAPKPath - apkPath: %s", apkPath);
            }
        }
        return utils;
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 获取 Resources 辅助类
     * @return {@link ResourceAssist}
     */
    public ResourceAssist getResourceAssist() {
        return mResourceAssist;
    }

    /**
     * 获取 Resources
     * @return {@link Resources}
     */
    public Resources getResources() {
        return mResourceAssist.getResources();
    }

    /**
     * 获取 APK 包名
     * @return APK 包名
     */
    public String getPackageName() {
        return mResourceAssist.getPackageName();
    }

    /**
     * 获取 APK 文件路径
     * @return APK 文件路径
     */
    public String getAPKPath() {
        return mAPKPath;
    }

    /**
     * 获取 APK 信息 Item
     * @return {@link ApkInfoItem}
     */
    public ApkInfoItem getApkInfoItem() {
        if (mApkInfoItem == null) {
            mApkInfoItem = AppInfoUtils.getApkInfoItem(mAPKPath);
        }
        return mApkInfoItem;
    }

    // =

    /**
     * 获取 DisplayMetrics
     * @return {@link DisplayMetrics}
     */
    public DisplayMetrics getDisplayMetrics() {
        return mResourceAssist.getDisplayMetrics();
    }

    /**
     * 获取 Configuration
     * @return {@link Configuration}
     */
    public Configuration getConfiguration() {
        return mResourceAssist.getConfiguration();
    }

    /**
     * 获取 AssetManager
     * @return {@link AssetManager}
     */
    public AssetManager getAssets() {
        return mResourceAssist.getAssets();
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
        return mResourceAssist.getIdentifier(resName, defType);
    }

    /**
     * 获取给定资源标识符的全名
     * @param id resource identifier
     * @return Integer
     */
    public String getResourceName(@AnyRes final int id) {
        return mResourceAssist.getResourceName(id);
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
        return mResourceAssist.getStringId(resName);
    }

    /**
     * 获取 String
     * @param resName resource name
     * @return String
     */
    public String getString(final String resName) {
        return mResourceAssist.getString(resName);
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
        return mResourceAssist.getString(resName, formatArgs);
    }

    /**
     * 获取 String
     * @param id R.string.id
     * @return String
     */
    public String getString(@StringRes final int id) {
        return mResourceAssist.getString(id);
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
        return mResourceAssist.getString(id, formatArgs);
    }

    // =

    /**
     * 获取 Dimension id
     * @param resName resource name
     * @return Dimension id
     */
    public int getDimenId(final String resName) {
        return mResourceAssist.getDimenId(resName);
    }

    /**
     * 获取 Dimension
     * @param resName resource name
     * @return Dimension
     */
    public float getDimension(final String resName) {
        return mResourceAssist.getDimension(resName);
    }

    /**
     * 获取 Dimension
     * @param resName resource name
     * @return Dimension
     */
    public int getDimensionInt(final String resName) {
        return mResourceAssist.getDimensionInt(resName);
    }

    /**
     * 获取 Dimension
     * @param id resource identifier
     * @return Dimension
     */
    public float getDimension(@DimenRes final int id) {
        return mResourceAssist.getDimension(id);
    }

    /**
     * 获取 Dimension
     * @param id resource identifier
     * @return Dimension
     */
    public int getDimensionInt(@DimenRes final int id) {
        return mResourceAssist.getDimensionInt(id);
    }

    // =

    /**
     * 获取 Color id
     * @param resName resource name
     * @return Color id
     */
    public int getColorId(final String resName) {
        return mResourceAssist.getColorId(resName);
    }

    /**
     * 获取 Color
     * @param resName resource name
     * @return Color
     */
    public int getColor(final String resName) {
        return mResourceAssist.getColor(resName);
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
        return mResourceAssist.getColor(colorId);
    }

    // =

    /**
     * 获取 Drawable id
     * @param resName resource name
     * @return Drawable id
     */
    public int getDrawableId(final String resName) {
        return mResourceAssist.getDrawableId(resName);
    }

    /**
     * 获取 Drawable
     * @param resName resource name
     * @return {@link Drawable}
     */
    public Drawable getDrawable(final String resName) {
        return mResourceAssist.getDrawable(resName);
    }

    /**
     * 获取 .9 Drawable
     * @param resName resource name
     * @return .9 {@link NinePatchDrawable}
     */
    public NinePatchDrawable getNinePatchDrawable(final String resName) {
        return mResourceAssist.getNinePatchDrawable(resName);
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
        return mResourceAssist.getDrawable(drawableId);
    }

    /**
     * 获取 .9 Drawable
     * @param drawableId R.drawable.id
     * @return .9 {@link NinePatchDrawable}
     */
    public NinePatchDrawable getNinePatchDrawable(@DrawableRes final int drawableId) {
        return mResourceAssist.getNinePatchDrawable(drawableId);
    }

    // =

    /**
     * 获取 Bitmap
     * @param resName resource name
     * @return {@link Bitmap}
     */
    public Bitmap getBitmap(final String resName) {
        return mResourceAssist.getBitmap(resName);
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
        return mResourceAssist.getBitmap(resName, options);
    }

    /**
     * 获取 Bitmap
     * @param resId resource identifier
     * @return {@link Bitmap}
     */
    public Bitmap getBitmap(final int resId) {
        return mResourceAssist.getBitmap(resId);
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
        return mResourceAssist.getBitmap(resId, options);
    }

    // =

    /**
     * 获取 Mipmap id
     * @param resName resource name
     * @return Mipmap id
     */
    public int getMipmapId(final String resName) {
        return mResourceAssist.getMipmapId(resName);
    }

    /**
     * 获取 Mipmap Drawable
     * @param resName resource name
     * @return {@link Drawable}
     */
    public Drawable getDrawableMipmap(final String resName) {
        return mResourceAssist.getDrawableMipmap(resName);
    }

    /**
     * 获取 Mipmap .9 Drawable
     * @param resName resource name
     * @return .9 {@link NinePatchDrawable}
     */
    public NinePatchDrawable getNinePatchDrawableMipmap(final String resName) {
        return mResourceAssist.getNinePatchDrawableMipmap(resName);
    }

    /**
     * 获取 Mipmap Bitmap
     * @param resName resource name
     * @return {@link Bitmap}
     */
    public Bitmap getBitmapMipmap(final String resName) {
        return mResourceAssist.getBitmapMipmap(resName);
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
        return mResourceAssist.getBitmapMipmap(resName, options);
    }

    // =

    /**
     * 获取 Anim id
     * @param resName resource name
     * @return Anim id
     */
    public int getAnimId(final String resName) {
        return mResourceAssist.getAnimId(resName);
    }

    /**
     * 获取 Animation Xml
     * @param resName resource name
     * @return {@link XmlResourceParser}
     */
    public XmlResourceParser getAnimationXml(final String resName) {
        return mResourceAssist.getAnimationXml(resName);
    }

    /**
     * 获取 Animation Xml
     * @param id resource identifier
     * @return {@link XmlResourceParser}
     */
    public XmlResourceParser getAnimationXml(@AnimatorRes @AnimRes final int id) {
        return mResourceAssist.getAnimationXml(id);
    }

    /**
     * 获取 Animation
     * @param resName resource name
     * @return {@link XmlResourceParser}
     */
    public Animation getAnimation(final String resName) {
        return mResourceAssist.getAnimation(resName);
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
        return mResourceAssist.getAnimation(resName, context);
    }

    /**
     * 获取 Animation
     * @param id resource identifier
     * @return {@link XmlResourceParser}
     */
    public Animation getAnimation(@AnimatorRes @AnimRes final int id) {
        return mResourceAssist.getAnimation(id);
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
        return mResourceAssist.getAnimation(id, context);
    }

    // =

    /**
     * 获取 Boolean id
     * @param resName resource name
     * @return Boolean id
     */
    public int getBooleanId(final String resName) {
        return mResourceAssist.getBooleanId(resName);
    }

    /**
     * 获取 Boolean
     * @param resName resource name
     * @return Boolean
     */
    public boolean getBoolean(final String resName) {
        return mResourceAssist.getBoolean(resName);
    }

    /**
     * 获取 Boolean
     * @param id resource identifier
     * @return Boolean
     */
    public boolean getBoolean(@BoolRes final int id) {
        return mResourceAssist.getBoolean(id);
    }

    // =

    /**
     * 获取 Integer id
     * @param resName resource name
     * @return Integer id
     */
    public int getIntegerId(final String resName) {
        return mResourceAssist.getIntegerId(resName);
    }

    /**
     * 获取 Integer
     * @param resName resource name
     * @return Integer
     */
    public int getInteger(final String resName) {
        return mResourceAssist.getInteger(resName);
    }

    /**
     * 获取 Integer
     * @param id resource identifier
     * @return Integer
     */
    public int getInteger(@IntegerRes final int id) {
        return mResourceAssist.getInteger(id);
    }

    // =

    /**
     * 获取 Array id
     * @param resName resource name
     * @return Array id
     */
    public int getArrayId(final String resName) {
        return mResourceAssist.getArrayId(resName);
    }

    /**
     * 获取 int[]
     * @param resName resource name
     * @return int[]
     */
    public int[] getIntArray(final String resName) {
        return mResourceAssist.getIntArray(resName);
    }

    /**
     * 获取 String[]
     * @param resName resource name
     * @return String[]
     */
    public String[] getStringArray(final String resName) {
        return mResourceAssist.getStringArray(resName);
    }

    /**
     * 获取 CharSequence[]
     * @param resName resource name
     * @return CharSequence[]
     */
    public CharSequence[] getTextArray(final String resName) {
        return mResourceAssist.getTextArray(resName);
    }

    /**
     * 获取 int[]
     * @param id resource identifier
     * @return int[]
     */
    public int[] getIntArray(@ArrayRes final int id) {
        return mResourceAssist.getIntArray(id);
    }

    /**
     * 获取 String[]
     * @param id resource identifier
     * @return String[]
     */
    public String[] getStringArray(@ArrayRes final int id) {
        return mResourceAssist.getStringArray(id);
    }

    /**
     * 获取 CharSequence[]
     * @param id resource identifier
     * @return CharSequence[]
     */
    public CharSequence[] getTextArray(@ArrayRes final int id) {
        return mResourceAssist.getTextArray(id);
    }

    // =

    /**
     * 获取 id ( view )
     * @param resName resource name
     * @return id
     */
    public int getId(final String resName) {
        return mResourceAssist.getId(resName);
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
        return mResourceAssist.getLayoutId(resName);
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
        return mResourceAssist.getMenuId(resName);
    }

    /**
     * 获取 Raw id
     * @param resName resource name
     * @return Raw id
     */
    public int getRawId(final String resName) {
        return mResourceAssist.getRawId(resName);
    }

    /**
     * 获取 Attr id
     * @param resName resource name
     * @return Attr id
     */
    public int getAttrId(final String resName) {
        return mResourceAssist.getAttrId(resName);
    }

    /**
     * 获取 Style id
     * @param resName resource name
     * @return Style id
     */
    public int getStyleId(final String resName) {
        return mResourceAssist.getStyleId(resName);
    }

    /**
     * 获取 Styleable id
     * @param resName resource name
     * @return Styleable id
     */
    public int getStyleableId(final String resName) {
        return mResourceAssist.getStyleableId(resName);
    }

    /**
     * 获取 Animator id
     * @param resName resource name
     * @return Animator id
     */
    public int getAnimatorId(final String resName) {
        return mResourceAssist.getAnimatorId(resName);
    }

    /**
     * 获取 Xml id
     * @param resName resource name
     * @return Xml id
     */
    public int getXmlId(final String resName) {
        return mResourceAssist.getXmlId(resName);
    }

    /**
     * 获取 Interpolator id
     * @param resName resource name
     * @return Interpolator id
     */
    public int getInterpolatorId(final String resName) {
        return mResourceAssist.getInterpolatorId(resName);
    }

    /**
     * 获取 Plurals id
     * @param resName resource name
     * @return Plurals id
     */
    public int getPluralsId(final String resName) {
        return mResourceAssist.getPluralsId(resName);
    }

    // =

    /**
     * 获取 ColorStateList
     * @param resName resource Name
     * @return {@link ColorStateList}
     */
    public ColorStateList getColorStateList(final String resName) {
        return mResourceAssist.getColorStateList(resName);
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
        return mResourceAssist.getColorStateList(id);
    }

    /**
     * 获取十六进制颜色值 Drawable
     * @param color 十六进制颜色值
     * @return 十六进制颜色值 Drawable
     */
    public ColorDrawable getColorDrawable(final String color) {
        return mResourceAssist.getColorDrawable(color);
    }

    /**
     * 获取指定颜色 Drawable
     * @param color 颜色值
     * @return 指定颜色 Drawable
     */
    public ColorDrawable getColorDrawable(@ColorInt final int color) {
        return mResourceAssist.getColorDrawable(color);
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
        return mResourceAssist.open(fileName);
    }

    /**
     * 获取 AssetManager 指定资源 AssetFileDescriptor
     * @param fileName 文件名
     * @return {@link AssetFileDescriptor}
     */
    public AssetFileDescriptor openFd(final String fileName) {
        return mResourceAssist.openFd(fileName);
    }

    /**
     * 获取 AssetManager 指定资源 AssetFileDescriptor
     * @param fileName 文件名
     * @return {@link AssetFileDescriptor}
     */
    public AssetFileDescriptor openNonAssetFd(final String fileName) {
        return mResourceAssist.openNonAssetFd(fileName);
    }

    /**
     * 获取对应资源 InputStream
     * @param id resource identifier
     * @return {@link InputStream}
     */
    public InputStream openRawResource(@RawRes final int id) {
        return mResourceAssist.openRawResource(id);
    }

    /**
     * 获取对应资源 AssetFileDescriptor
     * @param id resource identifier
     * @return {@link AssetFileDescriptor}
     */
    public AssetFileDescriptor openRawResourceFd(@RawRes final int id) {
        return mResourceAssist.openRawResourceFd(id);
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
        return mResourceAssist.readBytesFromAssets(fileName);
    }

    /**
     * 获取 Assets 资源文件数据
     * @param fileName 文件名
     * @return 文件字符串内容
     */
    public String readStringFromAssets(final String fileName) {
        return mResourceAssist.readStringFromAssets(fileName);
    }

    // =

    /**
     * 获取 Raw 资源文件数据
     * @param resId 资源 id
     * @return 文件 byte[] 数据
     */
    public byte[] readBytesFromRaw(@RawRes final int resId) {
        return mResourceAssist.readBytesFromRaw(resId);
    }

    /**
     * 获取 Raw 资源文件数据
     * @param resId 资源 id
     * @return 文件字符串内容
     */
    public String readStringFromRaw(@RawRes final int resId) {
        return mResourceAssist.readStringFromRaw(resId);
    }

    // =

    /**
     * 获取 Assets 资源文件数据 ( 返回 List<String> 一行的全部内容属于一个索引 )
     * @param fileName 文件名
     * @return {@link List <String>}
     */
    public List<String> geFileToListFromAssets(final String fileName) {
        return mResourceAssist.geFileToListFromAssets(fileName);
    }

    /**
     * 获取 Raw 资源文件数据 ( 返回 List<String> 一行的全部内容属于一个索引 )
     * @param resId 资源 id
     * @return {@link List<String>}
     */
    public List<String> geFileToListFromRaw(@RawRes final int resId) {
        return mResourceAssist.geFileToListFromRaw(resId);
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
        return mResourceAssist.saveAssetsFormFile(fileName, file);
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
        return mResourceAssist.saveRawFormFile(resId, file);
    }
}