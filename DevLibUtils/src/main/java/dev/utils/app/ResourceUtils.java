package dev.utils.app;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.R;

/**
 * detail: 资源文件工具类
 * @author Ttt
 */
public final class ResourceUtils {

    private ResourceUtils() {
    }

    // 日志 TAG
    private static final String TAG = ResourceUtils.class.getSimpleName();

    // =

    public static final String LAYTOUT = "layout";
    public static final String DRAWABLE = "drawable";
    public static final String MIPMAP = "mipmap";
    public static final String MENU = "menu";
    public static final String RAW = "raw";
    public static final String ANIM = "anim";
    public static final String STRING = "string";
    public static final String STYLE = "style";
    public static final String STYLEABLE = "styleable";
    public static final String INTEGER = "integer";
    public static final String ID = "id";
    public static final String DIMEN = "dimen";
    public static final String COLOR = "color";
    public static final String BOOL = "bool";
    public static final String ATTR = "attr";

    // ================
    // = 快捷获取方法 =
    // ================

    /**
     * 获取 View
     * @param resource
     * @return
     */
    public static View getView(@LayoutRes final int resource) {
        return getView(resource, null);
    }

    /**
     * 获取View
     * @param resource
     * @param root
     * @return
     */
    public static View getView(@LayoutRes final int resource, final ViewGroup root) {
        try {
            return ((LayoutInflater) DevUtils.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(resource, root);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getView");
        }
        return null;
    }

    /**
     * 获取 Resources
     * @return
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
     * @return
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
     * @return
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
     * 获取 ColorStateList
     * @return
     */
    public static ColorStateList getColorStateList(final int id) {
        try {
            return ContextCompat.getColorStateList(DevUtils.getContext(), id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getColorStateList");
        }
        return null;
    }

    // =

    /**
     * 获取字符串
     * @param strId 字符串id
     * @return 字符串
     */
    public static String getString(final int strId) {
        try {
            return DevUtils.getContext().getResources().getString(strId);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getString");
        }
        return "";
    }

    /**
     * 获取 String
     * @return
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
     * @param colorId 颜色id
     * @return 颜色
     */
    public static int getColor(final int colorId) {
        try {
            return DevUtils.getContext().getResources().getColor(colorId);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getColor");
        }
        return -1;
    }

    /**
     * 获取 Drawable
     * @param drawableId Drawable 的 id
     * @return
     */
    public static Drawable getDrawable(final int drawableId) {
        try {
            return DevUtils.getContext().getResources().getDrawable(drawableId);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDrawable");
        }
        return null;
    }

    /**
     * 获取 Dimen 资源
     * @param id
     * @return
     */
    public static float getDimension(final int id) {
        try {
            return DevUtils.getContext().getResources().getDimension(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDimension");
        }
        return 0f;
    }

    // =

    /**
     * 根据资源名获取资源id
     * @param name 资源名
     * @param type 资源类型
     * @return 资源id，找不到返回0
     */
    public static int getResourceId(final String name, final String type) {
        try {
            //PackageManager pm = DevUtils.getContext().getPackageManager();
            Resources resources = DevUtils.getContext().getResources();
            return resources.getIdentifier(name, type, DevUtils.getContext().getPackageName());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getResourceId");
        }
        return 0;
    }

    /**
     * 获取 layout 布局文件 id
     * @param resName layout xml 的文件名
     * @return layout
     */
    public static int getLayoutId(final String resName) {
        try {
            return DevUtils.getContext().getResources().getIdentifier(resName, "layout", DevUtils.getContext().getPackageName());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLayoutId");
        }
        return 0;
    }

    /**
     * 获取 string id
     * @param resName string name的名称
     * @return
     */
    public static int getStringId(final String resName) {
        try {
            return DevUtils.getContext().getResources().getIdentifier(resName, "string", DevUtils.getContext().getPackageName());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getStringId");
        }
        return 0;
    }

    /**
     * 获取 drawable id
     * @param resName drawable 的名称
     * @return
     */
    public static int getDrawableId(final String resName) {
        try {
            return DevUtils.getContext().getResources().getIdentifier(resName, "drawable", DevUtils.getContext().getPackageName());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDrawableId");
        }
        return 0;
    }

    /**
     * 获取 drawable id
     * @param imageName
     * @return
     */
    public static int getDrawableId2(final String imageName) {
        Class mipmap = R.drawable.class;
        try {
            Field field = mipmap.getField(imageName);
            int resId = field.getInt(imageName);
            return resId;
        } catch (NoSuchFieldException e) { // 如果没有在"drawable"下找到imageName,将会返回0
            LogPrintUtils.eTag(TAG, e, "getDrawableId2");
            return 0;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDrawableId2");
            return 0;
        }
    }

    /**
     * 获取 mipmap id
     * @param resName
     * @return
     */
    public static int getMipmapId(final String resName) {
        try {
            return DevUtils.getContext().getResources().getIdentifier(resName, "mipmap", DevUtils.getContext().getPackageName());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMipmapId");
        }
        return 0;
    }


    /**
     * 获取 style id
     * @param resName style的名称
     * @return style
     */
    public static int getStyleId(final String resName) {
        try {
            return DevUtils.getContext().getResources().getIdentifier(resName, "style", DevUtils.getContext().getPackageName());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getStyleId");
        }
        return 0;
    }

    /**
     * 获取 styleable id
     * @param resName styleable 的名称
     * @return styleable
     */
    public static Object getStyleableId(final String resName) {
        try {
            return DevUtils.getContext().getResources().getIdentifier(resName, "styleable", DevUtils.getContext().getPackageName());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getStyleableId");
        }
        return 0;
    }

    /**
     * 获取 anim id
     * @param resName anim xml 文件名称
     * @return anim
     */
    public static int getAnimId(final String resName) {
        try {
            return DevUtils.getContext().getResources().getIdentifier(resName, "anim", DevUtils.getContext().getPackageName());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAnimId");
        }
        return 0;
    }

    /**
     * 获取 id
     * @param resName id 的名称
     * @return
     */
    public static int getId(final String resName) {
        try {
            return DevUtils.getContext().getResources().getIdentifier(resName, "id", DevUtils.getContext().getPackageName());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getId");
        }
        return 0;
    }

    /**
     * 获取 color id
     * @param resName color 名称
     * @return
     */
    public static int getColorId(final String resName) {
        try {
            return DevUtils.getContext().getResources().getIdentifier(resName, "color", DevUtils.getContext().getPackageName());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getColorId");
        }
        return 0;
    }

    // =

    /**
     * 获取 Assets 资源文件数据
     * @param fileName 资源文件名，可分成，如根目录，a.txt 或者子目录 /www/a.html
     * @return
     */
    public static byte[] readBytesFromAssets(final String fileName) {
        if (DevUtils.getContext() != null && !TextUtils.isEmpty(fileName)) {
            InputStream iStream = null;
            try {
                iStream = DevUtils.getContext().getResources().getAssets().open(fileName);
                int length = iStream.available();
                byte[] buffer = new byte[length];
                iStream.read(buffer);
                iStream.close();
                iStream = null;
                return buffer;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "readBytesFromAssets");
            } finally {
                if (iStream != null) {
                    try {
                        iStream.close();
                    } catch (Exception e) {
                    }
                }
            }
        }
        return null;
    }

    /**
     * 读取字符串 来自 Assets文件
     * @param fileName
     * @return
     */
    public static String readStringFromAssets(final String fileName) {
        try {
            return new String(readBytesFromAssets(fileName), "UTF-8");
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "readStringFromAssets");
        }
        return null;
    }

    /**
     * 从res/raw 中获取内容。
     * @param resId 资源id
     * @return
     */
    public static byte[] readBytesFromRaw(final int resId) {
        if (DevUtils.getContext() != null) {
            InputStream iStream = null;
            try {
                iStream = DevUtils.getContext().getResources().openRawResource(resId);
                int length = iStream.available();
                byte[] buffer = new byte[length];
                iStream.read(buffer);
                iStream.close();
                iStream = null;
                return buffer;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "readBytesFromRaw");
            } finally {
                if (iStream != null) {
                    try {
                        iStream.close();
                    } catch (Exception e) {
                    }
                }
            }
        }
        return null;
    }

    /**
     * 读取字符串 来自Raw 文件
     * @param resId
     * @return
     */
    public static String readStringFromRaw(final int resId) {
        try {
            return new String(readBytesFromRaw(resId), "UTF-8");
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "readStringFromRaw");
        }
        return null;
    }

    // =

    /**
     * 获取 Assets 资源文件数据(返回List<String> 一行的全部内容属于一个索引)
     * @param fileName 资源文件名，可分成，如根目录，a.txt 或者子目录 /www/a.html
     * @return
     */
    public static List<String> geFileToListFromAssets(final String fileName) {
        if (DevUtils.getContext() != null && !TextUtils.isEmpty(fileName)) {
            InputStream iStream = null;
            InputStreamReader inReader = null;
            BufferedReader bufReader = null;
            try {
                iStream = DevUtils.getContext().getResources().getAssets().open(fileName);
                inReader = new InputStreamReader(iStream);
                bufReader = new BufferedReader(inReader);
                List<String> fileContent = new ArrayList<>();
                String line;
                while ((line = bufReader.readLine()) != null) {
                    fileContent.add(line);
                }
                return fileContent;
            } catch (IOException e) {
                LogPrintUtils.eTag(TAG, e, "geFileToListFromAssets");
            } finally {
                try {
                    bufReader.close();
                    inReader.close();
                    iStream.close();
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    /**
     * 从res/raw 中获取内容。(返回List<String>一行的全部内容属于一个索引)
     * @param resId 资源id
     * @return
     */
    public static List<String> geFileToListFromRaw(final int resId) {
        if (DevUtils.getContext() != null) {
            InputStream iStream = null;
            InputStreamReader inReader = null;
            BufferedReader bufReader = null;
            try {
                iStream = DevUtils.getContext().getResources().openRawResource(resId);
                inReader = new InputStreamReader(iStream);
                bufReader = new BufferedReader(inReader);
                List<String> fileContent = new ArrayList<>();
                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    fileContent.add(line);
                }
                return fileContent;
            } catch (IOException e) {
                LogPrintUtils.eTag(TAG, e, "geFileToListFromRaw");
            } finally {
                try {
                    bufReader.close();
                    inReader.close();
                    iStream.close();
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    // =

    /**
     * 从Assets 资源中获取内容并保存到本地
     * @param fileName 资源文件名，可分成，如根目录，a.txt 或者子目录 /www/a.html
     * @param file     保存地址
     * @return 是否保存成功
     */
    public static boolean saveAssetsFormFile(final String fileName, final File file) {
        if (DevUtils.getContext() != null) {
            try {
                // 获取 Assets 文件
                InputStream iStream = DevUtils.getContext().getResources().getAssets().open(fileName);
                // 存入SDCard
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                // 设置数据缓冲
                byte[] buffer = new byte[1024];
                // 创建输入输出流
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                int len = 0;
                while ((len = iStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                // 保存数据
                byte[] bytes = outStream.toByteArray();
                // 写入保存的文件
                fileOutputStream.write(bytes);
                // 关闭流
                outStream.close();
                iStream.close();
                // =
                fileOutputStream.flush();
                fileOutputStream.close();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "saveAssetsFormFile");
            }
        }
        return false;
    }

    /**
     * 从res/raw 中获取内容并保存到本地
     * @param resId 资源id
     * @param file  保存地址
     * @return 是否保存成功
     */
    public static boolean saveRawFormFile(final int resId, final File file) {
        if (DevUtils.getContext() != null) {
            try {
                // 获取raw 文件
                InputStream iStream = DevUtils.getContext().getResources().openRawResource(resId);
                // 存入SDCard
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                // 设置数据缓冲
                byte[] buffer = new byte[1024];
                // 创建输入输出流
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                int len = 0;
                while ((len = iStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                // 保存数据
                byte[] bytes = outStream.toByteArray();
                // 写入保存的文件
                fileOutputStream.write(bytes);
                // 关闭流
                outStream.close();
                iStream.close();
                // =
                fileOutputStream.flush();
                fileOutputStream.close();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "saveRawFormFile");
            }
        }
        return false;
    }
}
