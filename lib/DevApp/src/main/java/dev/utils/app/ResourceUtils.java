package dev.utils.app;

import android.content.ContentResolver;
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
import dev.utils.common.CloseUtils;
import dev.utils.common.FileIOUtils;

/**
 * detail: 资源文件工具类
 * @author Ttt
 */
public final class ResourceUtils {

    private ResourceUtils() {
    }

    // 日志 TAG
    private static final String TAG = ResourceUtils.class.getSimpleName();

    // ================
    // = 快捷获取方法 =
    // ================

    /**
     * 获取 Resources
     * @return {@link Resources}
     */
    public static Resources getResources() {
        try {
            return DevUtils.getContext().getResources();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getResources");
        }
        return null;
    }

    /**
     * 获取 Resources.Theme
     * @return {@link Resources.Theme}
     */
    public static Resources.Theme getTheme() {
        try {
            return DevUtils.getContext().getTheme();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getTheme");
        }
        return null;
    }

    /**
     * 获取 AssetManager
     * @return {@link AssetManager}
     */
    public static AssetManager getAssets() {
        try {
            return DevUtils.getContext().getAssets();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAssets");
        }
        return null;
    }

    /**
     * 获取 ContentResolver
     * @return {@link ContentResolver}
     */
    public static ContentResolver getContentResolver() {
        try {
            return DevUtils.getContext().getContentResolver();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getContentResolver");
        }
        return null;
    }

    /**
     * 获取 DisplayMetrics
     * @return {@link DisplayMetrics}
     */
    public static DisplayMetrics getDisplayMetrics() {
        try {
            return DevUtils.getContext().getResources().getDisplayMetrics();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDisplayMetrics");
        }
        return null;
    }

    /**
     * 获取 Configuration
     * @return {@link Configuration}
     */
    public static Configuration getConfiguration() {
        try {
            return DevUtils.getContext().getResources().getConfiguration();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getConfiguration");
        }
        return null;
    }

    /**
     * 获取 ColorStateList
     * @param id resource identifier of a {@link ColorStateList}
     * @return {@link ColorStateList}
     */
    public static ColorStateList getColorStateList(@ColorRes final int id) {
        try {
            return ContextCompat.getColorStateList(DevUtils.getContext(), id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getColorStateList");
        }
        return null;
    }

    /**
     * 获取 String
     * @param id R.string.id
     * @return String
     */
    public static String getString(@StringRes final int id) {
        try {
            return DevUtils.getContext().getResources().getString(id);
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
    public static String getString(@StringRes final int id, final Object... formatArgs) {
        try {
            return DevUtils.getContext().getResources().getString(id, formatArgs);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getString");
        }
        return null;
    }

    /**
     * 获取 Color
     * @param colorId R.color.id
     * @return Color
     */
    public static int getColor(@ColorRes final int colorId) {
        try {
            return ContextCompat.getColor(DevUtils.getContext(), colorId);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getColor");
        }
        return -1;
    }

    /**
     * 获取 Drawable
     * @param drawableId R.drawable.id
     * @return {@link Drawable}
     */
    public static Drawable getDrawable(@DrawableRes final int drawableId) {
        try {
            return ContextCompat.getDrawable(DevUtils.getContext(), drawableId);
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
    public static NinePatchDrawable getNinePatchDrawable(@DrawableRes final int drawableId) {
        try {
            return (NinePatchDrawable) getDrawable(drawableId);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getNinePatchDrawable");
        }
        return null;
    }

    /**
     * 获取指定颜色 Drawable
     * @param color 颜色值
     * @return 指定颜色 Drawable
     */
    public static ColorDrawable getColorDrawable(@ColorInt final int color) {
        try {
            return new ColorDrawable(color);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getColorDrawable");
        }
        return null;
    }

    /**
     * 获取十六进制颜色值 Drawable
     * @param color 十六进制颜色值
     * @return 十六进制颜色值 Drawable
     */
    public static ColorDrawable getColorDrawable(final String color) {
        try {
            return new ColorDrawable(Color.parseColor(color));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getColorDrawable");
        }
        return null;
    }

    /**
     * 获取 Bitmap
     * @param resId resource identifier
     * @return {@link Bitmap}
     */
    public static Bitmap getBitmap(final int resId) {
        try {
            return BitmapFactory.decodeResource(DevUtils.getContext().getResources(), resId);
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
    public static Bitmap getBitmap(final int resId, final BitmapFactory.Options options) {
        try {
            return BitmapFactory.decodeResource(DevUtils.getContext().getResources(), resId, options);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmap");
        }
        return null;
    }

    /**
     * 获取 Dimension
     * @param id resource identifier
     * @return Dimension
     */
    public static float getDimension(@DimenRes final int id) {
        try {
            return DevUtils.getContext().getResources().getDimension(id);
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
    public static int getDimensionInt(@DimenRes final int id) {
        return (int) ResourceUtils.getDimension(id);
    }

    /**
     * 获取 Boolean
     * @param id resource identifier
     * @return Boolean
     */
    public static boolean getBoolean(@BoolRes final int id) {
        try {
            return DevUtils.getContext().getResources().getBoolean(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBoolean");
        }
        return false;
    }

    /**
     * 获取 Integer
     * @param id resource identifier
     * @return Integer
     */
    public static int getInteger(@IntegerRes final int id) {
        try {
            return DevUtils.getContext().getResources().getInteger(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getInteger");
        }
        return -1;
    }

    /**
     * 获取 Animation
     * @param id resource identifier
     * @return XmlResourceParser
     */
    public static XmlResourceParser getAnimation(@AnimatorRes @AnimRes final int id) {
        try {
            return DevUtils.getContext().getResources().getAnimation(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAnimation");
        }
        return null;
    }

    /**
     * 获取给定资源标识符的全名
     * @param id resource identifier
     * @return Integer
     */
    public static String getResourceName(@AnyRes final int id) {
        try {
            return DevUtils.getContext().getResources().getResourceName(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getResourceName");
        }
        return null;
    }

    /**
     * 获取 int[]
     * @param id resource identifier
     * @return int[]
     */
    public static int[] getIntArray(@ArrayRes final int id) {
        try {
            return DevUtils.getContext().getResources().getIntArray(id);
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
    public static String[] getStringArray(@ArrayRes final int id) {
        try {
            return DevUtils.getContext().getResources().getStringArray(id);
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
    public static CharSequence[] getTextArray(@ArrayRes final int id) {
        try {
            return DevUtils.getContext().getResources().getTextArray(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getTextArray");
        }
        return null;
    }

    // =

    /**
     * 获取 layout id
     * @param resName layout xml fileName
     * @return layout id
     */
    public static int getLayoutId(final String resName) {
        return getIdentifier(resName, "layout");
    }

    /**
     * 获取 drawable id
     * @param resName drawable name
     * @return drawable id
     */
    public static int getDrawableId(final String resName) {
        return getIdentifier(resName, "drawable");
    }

    /**
     * 获取 mipmap id
     * @param resName mipmap name
     * @return mipmap id
     */
    public static int getMipmapId(final String resName) {
        return getIdentifier(resName, "mipmap");
    }

    /**
     * 获取 menu id
     * @param resName menu name
     * @return menu id
     */
    public static int getMenuId(final String resName) {
        return getIdentifier(resName, "menu");
    }

    /**
     * 获取 raw id
     * @param resName raw name
     * @return raw id
     */
    public static int getRawId(final String resName) {
        return getIdentifier(resName, "raw");
    }

    /**
     * 获取 anim id
     * @param resName anim xml fileName
     * @return anim id
     */
    public static int getAnimId(final String resName) {
        return getIdentifier(resName, "anim");
    }

    /**
     * 获取 color id
     * @param resName color name
     * @return color id
     */
    public static int getColorId(final String resName) {
        return getIdentifier(resName, "color");
    }

    /**
     * 获取 dimen id
     * @param resName dimen name
     * @return dimen id
     */
    public static int getDimenId(final String resName) {
        return getIdentifier(resName, "dimen");
    }

    /**
     * 获取 attr id
     * @param resName attr name
     * @return attr id
     */
    public static int getAttrId(final String resName) {
        return getIdentifier(resName, "attr");
    }

    /**
     * 获取 style id
     * @param resName style name
     * @return style id
     */
    public static int getStyleId(final String resName) {
        return getIdentifier(resName, "style");
    }

    /**
     * 获取 styleable id
     * @param resName styleable name
     * @return styleable id
     */
    public static int getStyleableId(final String resName) {
        return getIdentifier(resName, "styleable");
    }

    /**
     * 获取 id
     * @param resName id name
     * @return id
     */
    public static int getId(final String resName) {
        return getIdentifier(resName, "id");
    }

    /**
     * 获取 string id
     * @param resName string name
     * @return string id
     */
    public static int getStringId(final String resName) {
        return getIdentifier(resName, "string");
    }

    /**
     * 获取 bool id
     * @param resName bool name
     * @return bool id
     */
    public static int getBoolId(final String resName) {
        return getIdentifier(resName, "bool");
    }

    /**
     * 获取 integer id
     * @param resName integer name
     * @return integer id
     */
    public static int getIntegerId(final String resName) {
        return getIdentifier(resName, "integer");
    }

    /**
     * 获取资源 id
     * @param resName 资源名
     * @param defType 资源类型
     * @return 资源 id
     */
    public static int getIdentifier(final String resName, final String defType) {
        return getIdentifier(resName, defType, AppUtils.getPackageName());
    }

    /**
     * 获取资源 id
     * @param resName     资源名
     * @param defType     资源类型
     * @param packageName 应用包名
     * @return 资源 id
     */
    public static int getIdentifier(final String resName, final String defType, final String packageName) {
        try {
            return DevUtils.getContext().getResources().getIdentifier(resName, defType, packageName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getIdentifier - " + resName + " " + defType + ": " + packageName);
        }
        return 0;
    }

    // =

    /**
     * 获取 AssetManager 指定资源 InputStream
     * @param fileName 文件名
     * @return {@link InputStream}
     */
    public static InputStream open(final String fileName) {
        try {
            return DevUtils.getContext().getAssets().open(fileName);
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
    public static AssetFileDescriptor openFd(final String fileName) {
        try {
            return DevUtils.getContext().getAssets().openFd(fileName);
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
    public static AssetFileDescriptor openNonAssetFd(final String fileName) {
        try {
            return DevUtils.getContext().getAssets().openNonAssetFd(fileName);
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
    public static InputStream openRawResource(@RawRes final int id) {
        try {
            return DevUtils.getContext().getResources().openRawResource(id);
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
    public static AssetFileDescriptor openRawResourceFd(@RawRes final int id) {
        try {
            return DevUtils.getContext().getResources().openRawResourceFd(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openRawResourceFd");
        }
        return null;
    }

    /**
     * 获取 Uri InputStream
     * <pre>
     *     主要用于获取到分享的 FileProvider Uri 存储起来 {@link FileIOUtils#writeFileFromIS(File, InputStream)}
     * </pre>
     * @param uri {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @return Uri InputStream
     */
    public static InputStream openInputStream(final Uri uri) {
        if (uri == null) return null;
        try {
            return ResourceUtils.getContentResolver().openInputStream(uri);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openInputStream " + uri.toString());
        }
        return null;
    }

    /**
     * 获取 Uri OutputStream
     * @param uri {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @return Uri OutputStream
     */
    public static OutputStream openOutputStream(final Uri uri) {
        if (uri == null) return null;
        try {
            return ResourceUtils.getContentResolver().openOutputStream(uri);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openOutputStream " + uri.toString());
        }
        return null;
    }

    /**
     * 获取 Uri OutputStream
     * @param uri  {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @param mode 读写模式
     * @return Uri OutputStream
     */
    public static OutputStream openOutputStream(final Uri uri, final String mode) {
        if (uri == null) return null;
        try {
            return ResourceUtils.getContentResolver().openOutputStream(uri, mode);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openOutputStream mode: " + mode + ", " + uri.toString());
        }
        return null;
    }

    /**
     * 获取 Uri ParcelFileDescriptor
     * <pre>
     *     通过 new FileInputStream(openFileDescriptor().getFileDescriptor()) 进行文件操作
     * </pre>
     * @param uri  {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @param mode 读写模式
     * @return Uri ParcelFileDescriptor
     */
    public static ParcelFileDescriptor openFileDescriptor(final Uri uri, final String mode) {
        if (uri == null || TextUtils.isEmpty(mode)) return null;
        try {
            return ResourceUtils.getContentResolver().openFileDescriptor(uri, mode);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openFileDescriptor mode: " + mode + ", " + uri.toString());
        }
        return null;
    }

    /**
     * 获取 Uri AssetFileDescriptor
     * <pre>
     *     通过 new FileInputStream(openAssetFileDescriptor().getFileDescriptor()) 进行文件操作
     * </pre>
     * @param uri  {@link Uri} FileProvider Uri、Content Uri、File Uri
     * @param mode 读写模式
     * @return Uri AssetFileDescriptor
     */
    public static AssetFileDescriptor openAssetFileDescriptor(final Uri uri, final String mode) {
        if (uri == null || TextUtils.isEmpty(mode)) return null;
        try {
            return ResourceUtils.getContentResolver().openAssetFileDescriptor(uri, mode);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openAssetFileDescriptor mode: " + mode + ", " + uri.toString());
        }
        return null;
    }

    // ================
    // = 读取资源文件 =
    // ================

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
        InputStream is = null;
        try {
            is = open(fileName);
            int length = is.available();
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
    public static String readStringFromAssets(final String fileName) {
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
    public static byte[] readBytesFromRaw(@RawRes final int resId) {
        InputStream is = null;
        try {
            is = openRawResource(resId);
            int length = is.available();
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
    public static String readStringFromRaw(@RawRes final int resId) {
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
     * @return {@link List<String>}
     */
    public static List<String> geFileToListFromAssets(final String fileName) {
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = open(fileName);
            br = new BufferedReader(new InputStreamReader(is));

            List<String> lists = new ArrayList<>();
            String line;
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
    public static List<String> geFileToListFromRaw(@RawRes final int resId) {
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = openRawResource(resId);
            br = new BufferedReader(new InputStreamReader(is));

            List<String> lists = new ArrayList<>();
            String line;
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
    public static boolean saveAssetsFormFile(final String fileName, final File file) {
        try {
            // 获取 Assets 文件
            InputStream is = open(fileName);
            // 存入 SDCard
            FileOutputStream fos = new FileOutputStream(file);
            // 设置数据缓冲
            byte[] buffer = new byte[1024];
            // 创建输入输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            // 保存数据
            byte[] bytes = baos.toByteArray();
            // 写入保存的文件
            fos.write(bytes);
            // 关闭流
            CloseUtils.closeIOQuietly(baos, is);
            fos.flush();
            CloseUtils.closeIOQuietly(fos);
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
    public static boolean saveRawFormFile(@RawRes final int resId, final File file) {
        try {
            // 获取 raw 文件
            InputStream is = openRawResource(resId);
            // 存入 SDCard
            FileOutputStream fos = new FileOutputStream(file);
            // 设置数据缓冲
            byte[] buffer = new byte[1024];
            // 创建输入输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            // 保存数据
            byte[] bytes = baos.toByteArray();
            // 写入保存的文件
            fos.write(bytes);
            // 关闭流
            CloseUtils.closeIOQuietly(baos, is);
            fos.flush();
            CloseUtils.closeIOQuietly(fos);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "saveRawFormFile");
        }
        return false;
    }
}