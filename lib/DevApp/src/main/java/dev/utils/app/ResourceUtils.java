package dev.utils.app;

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
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
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
import java.io.OutputStream;
import java.util.List;

import dev.utils.app.assist.ResourceAssist;
import dev.utils.common.FileIOUtils;

/**
 * detail: 资源文件工具类
 * @author Ttt
 */
public final class ResourceUtils {

    private ResourceUtils() {
    }

    /**
     * 获取 Resources
     * @return {@link Resources}
     */
    public static Resources getResources() {
        return ResourceAssist.getInstance().getResources();
    }

    /**
     * 获取 Resources
     * @param context {@link Context}
     * @return {@link Resources}
     */
    public static Resources getResources(final Context context) {
        return ResourceAssist.staticResources(context);
    }

    /**
     * 获取 Resources.Theme
     * @return {@link Resources.Theme}
     */
    public static Resources.Theme getTheme() {
        return ResourceAssist.staticTheme();
    }

    /**
     * 获取 Resources.Theme
     * @param context {@link Context}
     * @return {@link Resources.Theme}
     */
    public static Resources.Theme getTheme(final Context context) {
        return ResourceAssist.staticTheme(context);
    }

    /**
     * 获取 ContentResolver
     * @return {@link ContentResolver}
     */
    public static ContentResolver getContentResolver() {
        return ResourceAssist.staticContentResolver();
    }

    /**
     * 获取 ContentResolver
     * @param context {@link Context}
     * @return {@link ContentResolver}
     */
    public static ContentResolver getContentResolver(final Context context) {
        return ResourceAssist.staticContentResolver(context);
    }

    /**
     * 获取 DisplayMetrics
     * @return {@link DisplayMetrics}
     */
    public static DisplayMetrics getDisplayMetrics() {
        return ResourceAssist.getInstance().getDisplayMetrics();
    }

    /**
     * 获取 DisplayMetrics
     * @param context {@link Context}
     * @return {@link DisplayMetrics}
     */
    public static DisplayMetrics getDisplayMetrics(final Context context) {
        return ResourceAssist.staticDisplayMetrics(context);
    }

    /**
     * 获取 DisplayMetrics
     * @param resource {@link Resources}
     * @return {@link DisplayMetrics}
     */
    public static DisplayMetrics getDisplayMetrics(final Resources resource) {
        return ResourceAssist.staticDisplayMetrics(resource);
    }

    /**
     * 获取 Configuration
     * @return {@link Configuration}
     */
    public static Configuration getConfiguration() {
        return ResourceAssist.getInstance().getConfiguration();
    }

    /**
     * 获取 Configuration
     * @param context {@link Context}
     * @return {@link Configuration}
     */
    public static Configuration getConfiguration(final Context context) {
        return ResourceAssist.staticConfiguration(context);
    }

    /**
     * 获取 Configuration
     * @param resource {@link Resources}
     * @return {@link Configuration}
     */
    public static Configuration getConfiguration(final Resources resource) {
        return ResourceAssist.staticConfiguration(resource);
    }

    /**
     * 获取 AssetManager
     * @return {@link AssetManager}
     */
    public static AssetManager getAssets() {
        return ResourceAssist.getInstance().getAssets();
    }

    /**
     * 获取 AssetManager
     * @param context {@link Context}
     * @return {@link AssetManager}
     */
    public static AssetManager getAssets(final Context context) {
        return ResourceAssist.staticAssets(context);
    }

    /**
     * 获取 AssetManager
     * @param resource {@link Resources}
     * @return {@link AssetManager}
     */
    public static AssetManager getAssets(final Resources resource) {
        return ResourceAssist.staticAssets(resource);
    }

    /**
     * 获取资源 id
     * @param resName 资源名
     * @param defType 资源类型
     * @return 资源 id
     */
    public static int getIdentifier(
            final String resName,
            final String defType
    ) {
        return ResourceAssist.getInstance().getIdentifier(resName, defType);
    }

    /**
     * 获取给定资源标识符的全名
     * @param id resource identifier
     * @return Integer
     */
    public static String getResourceName(@AnyRes final int id) {
        return ResourceAssist.getInstance().getResourceName(id);
    }

    // ===========
    // = 资源获取 =
    // ===========

    /**
     * 获取 String id
     * @param resName resource name
     * @return String id
     */
    public static int getStringId(final String resName) {
        return ResourceAssist.getInstance().getStringId(resName);
    }

    /**
     * 获取 String
     * @param resName resource name
     * @return String
     */
    public static String getString(final String resName) {
        return ResourceAssist.getInstance().getString(resName);
    }

    /**
     * 获取 String
     * @param resName    resource name
     * @param formatArgs 格式化参数
     * @return String
     */
    public static String getString(
            final String resName,
            final Object... formatArgs
    ) {
        return ResourceAssist.getInstance().getString(resName, formatArgs);
    }

    /**
     * 获取 String
     * @param id R.string.id
     * @return String
     */
    public static String getString(@StringRes final int id) {
        return ResourceAssist.getInstance().getString(id);
    }

    /**
     * 获取 String
     * @param id         R.string.id
     * @param formatArgs 格式化参数
     * @return String
     */
    public static String getString(
            @StringRes final int id,
            final Object... formatArgs
    ) {
        return ResourceAssist.getInstance().getString(id, formatArgs);
    }

    // =

    /**
     * 获取 Dimension id
     * @param resName resource name
     * @return Dimension id
     */
    public static int getDimenId(final String resName) {
        return ResourceAssist.getInstance().getDimenId(resName);
    }

    /**
     * 获取 Dimension
     * @param resName resource name
     * @return Dimension
     */
    public static float getDimension(final String resName) {
        return ResourceAssist.getInstance().getDimension(resName);
    }

    /**
     * 获取 Dimension
     * @param resName resource name
     * @return Dimension
     */
    public static int getDimensionInt(final String resName) {
        return ResourceAssist.getInstance().getDimensionInt(resName);
    }

    /**
     * 获取 Dimension
     * @param id resource identifier
     * @return Dimension
     */
    public static float getDimension(@DimenRes final int id) {
        return ResourceAssist.getInstance().getDimension(id);
    }

    /**
     * 获取 Dimension
     * @param id resource identifier
     * @return Dimension
     */
    public static int getDimensionInt(@DimenRes final int id) {
        return ResourceAssist.getInstance().getDimensionInt(id);
    }

    // =

    /**
     * 获取 Color id
     * @param resName resource name
     * @return Color id
     */
    public static int getColorId(final String resName) {
        return ResourceAssist.getInstance().getColorId(resName);
    }

    /**
     * 获取 Color
     * @param resName resource name
     * @return Color
     */
    public static int getColor(final String resName) {
        return ResourceAssist.getInstance().getColor(resName);
    }

    /**
     * 获取 Color
     * <pre>
     *     {@link ContextCompat#getColor(Context, int)}
     * </pre>
     * @param colorId R.color.id
     * @return Color
     */
    public static int getColor(@ColorRes final int colorId) {
        return ResourceAssist.getInstance().getColor(colorId);
    }

    // =

    /**
     * 获取 Drawable id
     * @param resName resource name
     * @return Drawable id
     */
    public static int getDrawableId(final String resName) {
        return ResourceAssist.getInstance().getDrawableId(resName);
    }

    /**
     * 获取 Drawable
     * @param resName resource name
     * @return {@link Drawable}
     */
    public static Drawable getDrawable(final String resName) {
        return ResourceAssist.getInstance().getDrawable(resName);
    }

    /**
     * 获取 .9 Drawable
     * @param resName resource name
     * @return .9 {@link NinePatchDrawable}
     */
    public static NinePatchDrawable getNinePatchDrawable(final String resName) {
        return ResourceAssist.getInstance().getNinePatchDrawable(resName);
    }

    /**
     * 获取 Drawable
     * <pre>
     *     {@link ContextCompat#getDrawable(Context, int)}
     * </pre>
     * @param drawableId R.drawable.id
     * @return {@link Drawable}
     */
    public static Drawable getDrawable(@DrawableRes final int drawableId) {
        return ResourceAssist.getInstance().getDrawable(drawableId);
    }

    /**
     * 获取 .9 Drawable
     * @param drawableId R.drawable.id
     * @return .9 {@link NinePatchDrawable}
     */
    public static NinePatchDrawable getNinePatchDrawable(@DrawableRes final int drawableId) {
        return ResourceAssist.getInstance().getNinePatchDrawable(drawableId);
    }

    // =

    /**
     * 获取 Bitmap
     * @param resName resource name
     * @return {@link Bitmap}
     */
    public static Bitmap getBitmap(final String resName) {
        return ResourceAssist.getInstance().getBitmap(resName);
    }

    /**
     * 获取 Bitmap
     * @param resName resource name
     * @param options {@link BitmapFactory.Options}
     * @return {@link Bitmap}
     */
    public static Bitmap getBitmap(
            final String resName,
            final BitmapFactory.Options options
    ) {
        return ResourceAssist.getInstance().getBitmap(resName, options);
    }

    /**
     * 获取 Bitmap
     * @param resId resource identifier
     * @return {@link Bitmap}
     */
    public static Bitmap getBitmap(final int resId) {
        return ResourceAssist.getInstance().getBitmap(resId);
    }

    /**
     * 获取 Bitmap
     * @param resId   resource identifier
     * @param options {@link BitmapFactory.Options}
     * @return {@link Bitmap}
     */
    public static Bitmap getBitmap(
            final int resId,
            final BitmapFactory.Options options
    ) {
        return ResourceAssist.getInstance().getBitmap(resId, options);
    }

    // =

    /**
     * 获取 Mipmap id
     * @param resName resource name
     * @return Mipmap id
     */
    public static int getMipmapId(final String resName) {
        return ResourceAssist.getInstance().getMipmapId(resName);
    }

    /**
     * 获取 Mipmap Drawable
     * @param resName resource name
     * @return {@link Drawable}
     */
    public static Drawable getDrawableMipmap(final String resName) {
        return ResourceAssist.getInstance().getDrawableMipmap(resName);
    }

    /**
     * 获取 Mipmap .9 Drawable
     * @param resName resource name
     * @return .9 {@link NinePatchDrawable}
     */
    public static NinePatchDrawable getNinePatchDrawableMipmap(final String resName) {
        return ResourceAssist.getInstance().getNinePatchDrawableMipmap(resName);
    }

    /**
     * 获取 Mipmap Bitmap
     * @param resName resource name
     * @return {@link Bitmap}
     */
    public static Bitmap getBitmapMipmap(final String resName) {
        return ResourceAssist.getInstance().getBitmapMipmap(resName);
    }

    /**
     * 获取 Mipmap Bitmap
     * @param resName resource name
     * @param options {@link BitmapFactory.Options}
     * @return {@link Bitmap}
     */
    public static Bitmap getBitmapMipmap(
            final String resName,
            final BitmapFactory.Options options
    ) {
        return ResourceAssist.getInstance().getBitmapMipmap(resName, options);
    }

    // =

    /**
     * 获取 Anim id
     * @param resName resource name
     * @return Anim id
     */
    public static int getAnimId(final String resName) {
        return ResourceAssist.getInstance().getAnimId(resName);
    }

    /**
     * 获取 Animation Xml
     * @param resName resource name
     * @return {@link XmlResourceParser}
     */
    public static XmlResourceParser getAnimationXml(final String resName) {
        return ResourceAssist.getInstance().getAnimationXml(resName);
    }

    /**
     * 获取 Animation Xml
     * @param id resource identifier
     * @return {@link XmlResourceParser}
     */
    public static XmlResourceParser getAnimationXml(@AnimatorRes @AnimRes final int id) {
        return ResourceAssist.getInstance().getAnimationXml(id);
    }

    /**
     * 获取 Animation
     * @param resName resource name
     * @return {@link XmlResourceParser}
     */
    public static Animation getAnimation(final String resName) {
        return ResourceAssist.getInstance().getAnimation(resName);
    }

    /**
     * 获取 Animation
     * @param resName resource name
     * @param context {@link Context}
     * @return {@link XmlResourceParser}
     */
    public static Animation getAnimation(
            final String resName,
            final Context context
    ) {
        return ResourceAssist.getInstance().getAnimation(resName, context);
    }

    /**
     * 获取 Animation
     * @param id resource identifier
     * @return {@link XmlResourceParser}
     */
    public static Animation getAnimation(@AnimatorRes @AnimRes final int id) {
        return ResourceAssist.getInstance().getAnimation(id);
    }

    /**
     * 获取 Animation
     * @param id      resource identifier
     * @param context {@link Context}
     * @return {@link XmlResourceParser}
     */
    public static Animation getAnimation(
            @AnimatorRes @AnimRes final int id,
            final Context context
    ) {
        return ResourceAssist.getInstance().getAnimation(id, context);
    }

    // =

    /**
     * 获取 Boolean id
     * @param resName resource name
     * @return Boolean id
     */
    public static int getBooleanId(final String resName) {
        return ResourceAssist.getInstance().getBooleanId(resName);
    }

    /**
     * 获取 Boolean
     * @param resName resource name
     * @return Boolean
     */
    public static boolean getBoolean(final String resName) {
        return ResourceAssist.getInstance().getBoolean(resName);
    }

    /**
     * 获取 Boolean
     * @param id resource identifier
     * @return Boolean
     */
    public static boolean getBoolean(@BoolRes final int id) {
        return ResourceAssist.getInstance().getBoolean(id);
    }

    // =

    /**
     * 获取 Integer id
     * @param resName resource name
     * @return Integer id
     */
    public static int getIntegerId(final String resName) {
        return ResourceAssist.getInstance().getIntegerId(resName);
    }

    /**
     * 获取 Integer
     * @param resName resource name
     * @return Integer
     */
    public static int getInteger(final String resName) {
        return ResourceAssist.getInstance().getInteger(resName);
    }

    /**
     * 获取 Integer
     * @param id resource identifier
     * @return Integer
     */
    public static int getInteger(@IntegerRes final int id) {
        return ResourceAssist.getInstance().getInteger(id);
    }

    // =

    /**
     * 获取 Array id
     * @param resName resource name
     * @return Array id
     */
    public static int getArrayId(final String resName) {
        return ResourceAssist.getInstance().getArrayId(resName);
    }

    /**
     * 获取 int[]
     * @param resName resource name
     * @return int[]
     */
    public static int[] getIntArray(final String resName) {
        return ResourceAssist.getInstance().getIntArray(resName);
    }

    /**
     * 获取 String[]
     * @param resName resource name
     * @return String[]
     */
    public static String[] getStringArray(final String resName) {
        return ResourceAssist.getInstance().getStringArray(resName);
    }

    /**
     * 获取 CharSequence[]
     * @param resName resource name
     * @return CharSequence[]
     */
    public static CharSequence[] getTextArray(final String resName) {
        return ResourceAssist.getInstance().getTextArray(resName);
    }

    /**
     * 获取 int[]
     * @param id resource identifier
     * @return int[]
     */
    public static int[] getIntArray(@ArrayRes final int id) {
        return ResourceAssist.getInstance().getIntArray(id);
    }

    /**
     * 获取 String[]
     * @param id resource identifier
     * @return String[]
     */
    public static String[] getStringArray(@ArrayRes final int id) {
        return ResourceAssist.getInstance().getStringArray(id);
    }

    /**
     * 获取 CharSequence[]
     * @param id resource identifier
     * @return CharSequence[]
     */
    public static CharSequence[] getTextArray(@ArrayRes final int id) {
        return ResourceAssist.getInstance().getTextArray(id);
    }

    // =

    /**
     * 获取 id ( view )
     * @param resName resource name
     * @return id
     */
    public static int getId(final String resName) {
        return ResourceAssist.getInstance().getId(resName);
    }

    /**
     * 获取 Layout id
     * <pre>
     *     {@link android.view.LayoutInflater#inflate(int, ViewGroup)}
     * </pre>
     * @param resName resource name
     * @return Layout id
     */
    public static int getLayoutId(final String resName) {
        return ResourceAssist.getInstance().getLayoutId(resName);
    }

    /**
     * 获取 Menu id
     * <pre>
     *     {@link android.view.MenuInflater#inflate(int, Menu)}
     * </pre>
     * @param resName resource name
     * @return Menu id
     */
    public static int getMenuId(final String resName) {
        return ResourceAssist.getInstance().getMenuId(resName);
    }

    /**
     * 获取 Raw id
     * @param resName resource name
     * @return Raw id
     */
    public static int getRawId(final String resName) {
        return ResourceAssist.getInstance().getRawId(resName);
    }

    /**
     * 获取 Attr id
     * @param resName resource name
     * @return Attr id
     */
    public static int getAttrId(final String resName) {
        return ResourceAssist.getInstance().getAttrId(resName);
    }

    /**
     * 获取 Style id
     * @param resName resource name
     * @return Style id
     */
    public static int getStyleId(final String resName) {
        return ResourceAssist.getInstance().getStyleId(resName);
    }

    /**
     * 获取 Styleable id
     * @param resName resource name
     * @return Styleable id
     */
    public static int getStyleableId(final String resName) {
        return ResourceAssist.getInstance().getStyleableId(resName);
    }

    /**
     * 获取 Animator id
     * @param resName resource name
     * @return Animator id
     */
    public static int getAnimatorId(final String resName) {
        return ResourceAssist.getInstance().getAnimatorId(resName);
    }

    /**
     * 获取 Xml id
     * @param resName resource name
     * @return Xml id
     */
    public static int getXmlId(final String resName) {
        return ResourceAssist.getInstance().getXmlId(resName);
    }

    /**
     * 获取 Interpolator id
     * @param resName resource name
     * @return Interpolator id
     */
    public static int getInterpolatorId(final String resName) {
        return ResourceAssist.getInstance().getInterpolatorId(resName);
    }

    /**
     * 获取 Plurals id
     * @param resName resource name
     * @return Plurals id
     */
    public static int getPluralsId(final String resName) {
        return ResourceAssist.getInstance().getPluralsId(resName);
    }

    // =

    /**
     * 获取 ColorStateList
     * @param resName resource Name
     * @return {@link ColorStateList}
     */
    public static ColorStateList getColorStateList(final String resName) {
        return ResourceAssist.getInstance().getColorStateList(resName);
    }

    /**
     * 获取 ColorStateList
     * <pre>
     *     {@link ContextCompat#getColorStateList(Context, int)}
     * </pre>
     * @param id resource identifier of a {@link ColorStateList}
     * @return {@link ColorStateList}
     */
    public static ColorStateList getColorStateList(@ColorRes final int id) {
        return ResourceAssist.getInstance().getColorStateList(id);
    }

    /**
     * 获取十六进制颜色值 Drawable
     * @param color 十六进制颜色值
     * @return 十六进制颜色值 Drawable
     */
    public static ColorDrawable getColorDrawable(final String color) {
        return ResourceAssist.getInstance().getColorDrawable(color);
    }

    /**
     * 获取指定颜色 Drawable
     * @param color 颜色值
     * @return 指定颜色 Drawable
     */
    public static ColorDrawable getColorDrawable(@ColorInt final int color) {
        return ResourceAssist.getInstance().getColorDrawable(color);
    }

    // ===================
    // = ContentResolver =
    // ===================

    /**
     * 获取 Uri InputStream
     * @param uri {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @return Uri InputStream
     */
    public static InputStream openInputStream(final Uri uri) {
        return ResourceAssist.getInstance().openInputStream(uri);
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
    public static InputStream openInputStream(
            final Uri uri,
            final ContentResolver resolver
    ) {
        return ResourceAssist.getInstance().openInputStream(uri, resolver);
    }

    /**
     * 获取 Uri OutputStream
     * @param uri {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @return Uri OutputStream
     */
    public static OutputStream openOutputStream(final Uri uri) {
        return ResourceAssist.getInstance().openOutputStream(uri);
    }

    /**
     * 获取 Uri OutputStream
     * @param uri      {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @param resolver {@link ContentResolver}
     * @return Uri OutputStream
     */
    public static OutputStream openOutputStream(
            final Uri uri,
            final ContentResolver resolver
    ) {
        return ResourceAssist.getInstance().openOutputStream(uri, resolver);
    }

    /**
     * 获取 Uri OutputStream
     * @param uri  {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @param mode 读写模式
     * @return Uri OutputStream
     */
    public static OutputStream openOutputStream(
            final Uri uri,
            final String mode
    ) {
        return ResourceAssist.getInstance().openOutputStream(uri, mode);
    }

    /**
     * 获取 Uri OutputStream
     * @param uri      {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @param mode     读写模式
     * @param resolver {@link ContentResolver}
     * @return Uri OutputStream
     */
    public static OutputStream openOutputStream(
            final Uri uri,
            final String mode,
            final ContentResolver resolver
    ) {
        return ResourceAssist.getInstance().openOutputStream(uri, mode, resolver);
    }

    /**
     * 获取 Uri ParcelFileDescriptor
     * @param uri  {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @param mode 读写模式
     * @return Uri ParcelFileDescriptor
     */
    public static ParcelFileDescriptor openFileDescriptor(
            final Uri uri,
            final String mode
    ) {
        return ResourceAssist.getInstance().openFileDescriptor(uri, mode);
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
    public static ParcelFileDescriptor openFileDescriptor(
            final Uri uri,
            final String mode,
            final ContentResolver resolver
    ) {
        return ResourceAssist.getInstance().openFileDescriptor(uri, mode, resolver);
    }

    /**
     * 获取 Uri AssetFileDescriptor
     * @param uri  {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @param mode 读写模式
     * @return Uri AssetFileDescriptor
     */
    public static AssetFileDescriptor openAssetFileDescriptor(
            final Uri uri,
            final String mode
    ) {
        return ResourceAssist.getInstance().openAssetFileDescriptor(uri, mode);
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
    public static AssetFileDescriptor openAssetFileDescriptor(
            final Uri uri,
            final String mode,
            final ContentResolver resolver
    ) {
        return ResourceAssist.getInstance().openAssetFileDescriptor(uri, mode, resolver);
    }

    // ================
    // = AssetManager =
    // ================

    /**
     * 获取 AssetManager 指定资源 InputStream
     * @param fileName 文件名
     * @return {@link InputStream}
     */
    public static InputStream open(final String fileName) {
        return ResourceAssist.getInstance().open(fileName);
    }

    /**
     * 获取 AssetManager 指定资源 AssetFileDescriptor
     * @param fileName 文件名
     * @return {@link AssetFileDescriptor}
     */
    public static AssetFileDescriptor openFd(final String fileName) {
        return ResourceAssist.getInstance().openFd(fileName);
    }

    /**
     * 获取 AssetManager 指定资源 AssetFileDescriptor
     * @param fileName 文件名
     * @return {@link AssetFileDescriptor}
     */
    public static AssetFileDescriptor openNonAssetFd(final String fileName) {
        return ResourceAssist.getInstance().openNonAssetFd(fileName);
    }

    /**
     * 获取对应资源 InputStream
     * @param id resource identifier
     * @return {@link InputStream}
     */
    public static InputStream openRawResource(@RawRes final int id) {
        return ResourceAssist.getInstance().openRawResource(id);
    }

    /**
     * 获取对应资源 AssetFileDescriptor
     * @param id resource identifier
     * @return {@link AssetFileDescriptor}
     */
    public static AssetFileDescriptor openRawResourceFd(@RawRes final int id) {
        return ResourceAssist.getInstance().openRawResourceFd(id);
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
    public static byte[] readBytesFromAssets(final String fileName) {
        return ResourceAssist.getInstance().readBytesFromAssets(fileName);
    }

    /**
     * 获取 Assets 资源文件数据
     * @param fileName 文件名
     * @return 文件字符串内容
     */
    public static String readStringFromAssets(final String fileName) {
        return ResourceAssist.getInstance().readStringFromAssets(fileName);
    }

    // =

    /**
     * 获取 Raw 资源文件数据
     * @param resId 资源 id
     * @return 文件 byte[] 数据
     */
    public static byte[] readBytesFromRaw(@RawRes final int resId) {
        return ResourceAssist.getInstance().readBytesFromRaw(resId);
    }

    /**
     * 获取 Raw 资源文件数据
     * @param resId 资源 id
     * @return 文件字符串内容
     */
    public static String readStringFromRaw(@RawRes final int resId) {
        return ResourceAssist.getInstance().readStringFromRaw(resId);
    }

    // =

    /**
     * 获取 Assets 资源文件数据 ( 返回 List<String> 一行的全部内容属于一个索引 )
     * @param fileName 文件名
     * @return {@link List <String>}
     */
    public static List<String> geFileToListFromAssets(final String fileName) {
        return ResourceAssist.getInstance().geFileToListFromAssets(fileName);
    }

    /**
     * 获取 Raw 资源文件数据 ( 返回 List<String> 一行的全部内容属于一个索引 )
     * @param resId 资源 id
     * @return {@link List<String>}
     */
    public static List<String> geFileToListFromRaw(@RawRes final int resId) {
        return ResourceAssist.getInstance().geFileToListFromRaw(resId);
    }

    // =

    /**
     * 获取 Assets 资源文件数据并保存到本地
     * @param fileName 文件名
     * @param file     文件保存地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveAssetsFormFile(
            final String fileName,
            final File file
    ) {
        return ResourceAssist.getInstance().saveAssetsFormFile(fileName, file);
    }

    /**
     * 获取 Raw 资源文件数据并保存到本地
     * @param resId 资源 id
     * @param file  文件保存地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveRawFormFile(
            @RawRes final int resId,
            final File file
    ) {
        return ResourceAssist.getInstance().saveRawFormFile(resId, file);
    }
}