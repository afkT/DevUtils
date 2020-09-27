package dev.utils.app;

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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import dev.utils.LogPrintUtils;
import dev.utils.app.info.ApkInfoItem;
import dev.utils.app.info.AppInfoBean;
import dev.utils.app.info.AppInfoUtils;
import dev.utils.common.CloseUtils;
import dev.utils.common.FileUtils;

/**
 * detail: APK Resource 工具类
 * @author JiZhi-Error <a href="https://github.com/JiZhi-Error"/>
 * @author Ttt
 */
public final class ResourcePluginUtils {

    private ResourcePluginUtils() {
    }

    // 日志 TAG
    private static final String TAG = ResourcePluginUtils.class.getSimpleName();

    // APK Resources
    private Resources   mResources;
    // 包名
    private String      mPackageName;
    // APK 文件路径
    private String      mAPKPath;
    // APK 信息 Item
    private ApkInfoItem mApkInfoItem;

    // ==========
    // = invoke =
    // ==========

    /**
     * 通过 packageName 获取 APK Resources
     * @param packageName 应用包名
     * @return {@link ResourcePluginUtils}
     */
    public static final ResourcePluginUtils invokeByPackageName(final String packageName) {
        AppInfoBean appInfoBean = AppInfoUtils.getAppInfoBean(packageName);
        String sourceDir = (appInfoBean != null) ? appInfoBean.getSourceDir() : null;
        return invokeByAPKPath(sourceDir);
    }

    /**
     * 通过 APK 文件获取 APK Resources
     * @param apkPath APK 文件路径
     * @return {@link ResourcePluginUtils}
     */
    public static final ResourcePluginUtils invokeByAPKPath(final String apkPath) {
        ResourcePluginUtils utils = new ResourcePluginUtils();
        utils.mAPKPath = apkPath;
        // 文件存在才进行处理
        if (FileUtils.isFileExists(apkPath)) {
            try {
                AssetManager asset = AssetManager.class.newInstance();
                Method addAssetPath = asset.getClass().getMethod("addAssetPath", String.class);
                addAssetPath.invoke(asset, apkPath);
                Resources resources = new Resources(
                        asset,
                        ResourceUtils.getDisplayMetrics(),
                        ResourceUtils.getConfiguration()
                );
                PackageInfo packageInfo = AppUtils.getPackageManager().getPackageArchiveInfo(
                        apkPath, PackageManager.GET_ACTIVITIES
                );
                utils.mPackageName = packageInfo.packageName;
                utils.mResources = resources;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "invokeByAPKPath - apkPath: " + apkPath);
            }
        }
        return utils;
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 获取 Resources
     * @return {@link Resources}
     */
    public Resources getResources() {
        return mResources;
    }

    /**
     * 获取 APK 包名
     * @return APK 包名
     */
    public String getPackageName() {
        return mPackageName;
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
     * 获取 AssetManager
     * @return {@link AssetManager}
     */
    public AssetManager getAssets() {
        try {
            return mResources.getAssets();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAssets");
        }
        return null;
    }

    /**
     * 获取 DisplayMetrics
     * @return {@link DisplayMetrics}
     */
    public DisplayMetrics getDisplayMetrics() {
        try {
            return mResources.getDisplayMetrics();
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
            return mResources.getConfiguration();
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
    public ColorStateList getColorStateList(@ColorRes final int id) {
        try {
            return mResources.getColorStateList(id);
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
    public String getString(@StringRes final int id) {
        try {
            return mResources.getString(id);
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
            return mResources.getString(id, formatArgs);
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
    public int getColor(@ColorRes final int colorId) {
        try {
            return mResources.getColor(colorId);
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
    public Drawable getDrawable(@DrawableRes final int drawableId) {
        try {
            return mResources.getDrawable(drawableId);
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
     * 获取 Bitmap
     * @param resId resource identifier
     * @return {@link Bitmap}
     */
    public Bitmap getBitmap(final int resId) {
        try {
            return BitmapFactory.decodeResource(mResources, resId);
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
            return BitmapFactory.decodeResource(mResources, resId, options);
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
    public float getDimension(@DimenRes final int id) {
        try {
            return mResources.getDimension(id);
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

    /**
     * 获取 Boolean
     * @param id resource identifier
     * @return Boolean
     */
    public boolean getBoolean(@BoolRes final int id) {
        try {
            return mResources.getBoolean(id);
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
    public int getInteger(@IntegerRes final int id) {
        try {
            return mResources.getInteger(id);
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
    public XmlResourceParser getAnimation(@AnimatorRes @AnimRes final int id) {
        try {
            return mResources.getAnimation(id);
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
    public String getResourceName(@AnyRes final int id) {
        try {
            return mResources.getResourceName(id);
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
    public int[] getIntArray(@ArrayRes final int id) {
        try {
            return mResources.getIntArray(id);
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
            return mResources.getStringArray(id);
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
            return mResources.getTextArray(id);
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
    public int getLayoutId(final String resName) {
        return getIdentifier(resName, "layout");
    }

    /**
     * 获取 drawable id
     * @param resName drawable name
     * @return drawable id
     */
    public int getDrawableId(final String resName) {
        return getIdentifier(resName, "drawable");
    }

    /**
     * 获取 mipmap id
     * @param resName mipmap name
     * @return mipmap id
     */
    public int getMipmapId(final String resName) {
        return getIdentifier(resName, "mipmap");
    }

    /**
     * 获取 menu id
     * @param resName menu name
     * @return menu id
     */
    public int getMenuId(final String resName) {
        return getIdentifier(resName, "menu");
    }

    /**
     * 获取 raw id
     * @param resName raw name
     * @return raw id
     */
    public int getRawId(final String resName) {
        return getIdentifier(resName, "raw");
    }

    /**
     * 获取 anim id
     * @param resName anim xml fileName
     * @return anim id
     */
    public int getAnimId(final String resName) {
        return getIdentifier(resName, "anim");
    }

    /**
     * 获取 color id
     * @param resName color name
     * @return color id
     */
    public int getColorId(final String resName) {
        return getIdentifier(resName, "color");
    }

    /**
     * 获取 dimen id
     * @param resName dimen name
     * @return dimen id
     */
    public int getDimenId(final String resName) {
        return getIdentifier(resName, "dimen");
    }

    /**
     * 获取 attr id
     * @param resName attr name
     * @return attr id
     */
    public int getAttrId(final String resName) {
        return getIdentifier(resName, "attr");
    }

    /**
     * 获取 style id
     * @param resName style name
     * @return style id
     */
    public int getStyleId(final String resName) {
        return getIdentifier(resName, "style");
    }

    /**
     * 获取 styleable id
     * @param resName styleable name
     * @return styleable id
     */
    public int getStyleableId(final String resName) {
        return getIdentifier(resName, "styleable");
    }

    /**
     * 获取 id
     * @param resName id name
     * @return id
     */
    public int getId(final String resName) {
        return getIdentifier(resName, "id");
    }

    /**
     * 获取 string id
     * @param resName string name
     * @return string id
     */
    public int getStringId(final String resName) {
        return getIdentifier(resName, "string");
    }

    /**
     * 获取 bool id
     * @param resName bool name
     * @return bool id
     */
    public int getBoolId(final String resName) {
        return getIdentifier(resName, "bool");
    }

    /**
     * 获取 integer id
     * @param resName integer name
     * @return integer id
     */
    public int getIntegerId(final String resName) {
        return getIdentifier(resName, "integer");
    }

    /**
     * 获取资源 id
     * @param resName 资源名
     * @param defType 资源类型
     * @return 资源 id
     */
    public int getIdentifier(final String resName, final String defType) {
        try {
            return mResources.getIdentifier(resName, defType, mPackageName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getIdentifier - " + resName + " " + defType + ": " + mPackageName);
        }
        return 0;
    }

    // =

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
            return mResources.openRawResource(id);
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
            return mResources.openRawResourceFd(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openRawResourceFd");
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
    public byte[] readBytesFromAssets(final String fileName) {
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
    public List<String> geFileToListFromRaw(@RawRes final int resId) {
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
    public boolean saveAssetsFormFile(final String fileName, final File file) {
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
    public boolean saveRawFormFile(@RawRes final int resId, final File file) {
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