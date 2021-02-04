package dev.utils.app;

import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.KeyListener;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import java.util.UUID;

import dev.utils.LogPrintUtils;

/**
 * detail: EditText 工具类
 * @author Ttt
 * <pre>
 *     EditText 属性大全 ( 不局限于仅仅是 EditText )
 *     @see <a href="https://my.oschina.net/xsjayz/blog/121685"/>
 *     <p></p>
 *     EditText 多行显示及所有属性
 *     @see <a href="https://www.cnblogs.com/zhujiabin/p/5736470.html"/>
 *     <p></p>
 *     EditText 设置不自动获取焦点, 点击后才获取, 并弹出软键盘
 *     @see <a href="https://blog.csdn.net/juvary/article/details/80151358"/>
 *     <p></p>
 *     EditText 点击无反应解决办法
 *     @see <a href="https://blog.csdn.net/cccheer/article/details/79218143"/>
 *     <p></p>
 *     EditText 限制输入的 4 种方法
 *     @see <a href="https://blog.csdn.net/zhoujn90/article/details/44983905"/>
 *     <p></p>
 *     自定义 EditText 光标和下划线颜色
 *     @see <a href="https://segmentfault.com/a/1190000009507919"/>
 * </pre>
 */
public final class EditTextUtils {

    private EditTextUtils() {
    }

    // 日志 TAG
    private static final String TAG = EditTextUtils.class.getSimpleName();

    // =================
    // = 获取 EditText =
    // =================

    /**
     * 获取 EditText
     * @param view {@link View}
     * @param <T>  泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T getEditText(final View view) {
        if (view != null) {
            try {
                return (T) view;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getEditText");
            }
        }
        return null;
    }

    // =

    /**
     * 获取输入的内容
     * @param editText {@link EditText}
     * @param <T>      泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> String getText(final T editText) {
        if (editText != null) {
            return editText.getText().toString();
        }
        return "";
    }

    /**
     * 获取输入的内容长度
     * @param editText {@link EditText}
     * @param <T>      泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> int getTextLength(final T editText) {
        return getText(editText).length();
    }

    // =

    /**
     * 设置内容
     * @param editText {@link EditText}
     * @param content  文本内容
     * @param <T>      泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T setText(
            final T editText,
            final CharSequence content
    ) {
        return setText(editText, content, true);
    }

    /**
     * 设置内容
     * @param editText {@link EditText}
     * @param content  文本内容
     * @param isSelect 是否设置光标
     * @param <T>      泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T setText(
            final T editText,
            final CharSequence content,
            final boolean isSelect
    ) {
        if (editText != null && content != null) {
            editText.setText(content);
            // 设置光标
            if (isSelect) {
                editText.setSelection(editText.getText().toString().length());
            }
        }
        return editText;
    }

    /**
     * 设置多个 EditText 文本
     * @param content 文本内容
     * @param views   View(EditText)[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setTexts(
            final CharSequence content,
            final View... views
    ) {
        if (views != null) {
            for (View view : views) {
                setText(getEditText(view), content);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置多个 EditText 文本
     * @param content 文本内容
     * @param views   EditText[]
     * @param <T>     泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends EditText> boolean setTexts(
            final CharSequence content,
            final T... views
    ) {
        if (views != null) {
            for (T view : views) {
                setText(view, content);
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 追加内容 ( 当前光标位置追加 )
     * @param editText {@link EditText}
     * @param content  文本内容
     * @param isSelect 是否设置光标
     * @param <T>      泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T insert(
            final T editText,
            final CharSequence content,
            final boolean isSelect
    ) {
        if (editText != null) {
            return insert(editText, content, editText.getSelectionStart(), isSelect);
        }
        return editText;
    }

    /**
     * 追加内容
     * @param editText {@link EditText}
     * @param content  文本内容
     * @param start    开始添加的位置
     * @param isSelect 是否设置光标
     * @param <T>      泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T insert(
            final T editText,
            final CharSequence content,
            final int start,
            final boolean isSelect
    ) {
        if (editText != null && !TextUtils.isEmpty(content)) {
            try {
                Editable editable = editText.getText();
                // 在指定位置 追加内容
                editable.insert(start, content);
                // 设置光标
                if (isSelect) {
                    editText.setSelection(editText.getText().toString().length());
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "insert");
            }
        }
        return editText;
    }

    // =

    /**
     * 设置长度限制
     * @param editText  {@link EditText}
     * @param maxLength 长度限制
     * @param <T>       泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T setMaxLength(
            final T editText,
            final int maxLength
    ) {
        if (editText != null && maxLength > 0) {
            // 设置最大长度限制
            InputFilter[] filters = {new InputFilter.LengthFilter(maxLength)};
            editText.setFilters(filters);
        }
        return editText;
    }

    /**
     * 设置长度限制, 并且设置内容
     * @param editText  {@link EditText}
     * @param content   文本内容
     * @param maxLength 长度限制
     * @param <T>       泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T setMaxLengthAndText(
            final T editText,
            final CharSequence content,
            final int maxLength
    ) {
        return setText(setMaxLength(editText, maxLength), content);
    }

    // ========
    // = 光标 =
    // ========

    /**
     * 是否显示光标
     * @param editText {@link EditText}
     * @param <T>      泛型
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static <T extends EditText> boolean isCursorVisible(final T editText) {
        if (editText != null) {
            return editText.isCursorVisible();
        }
        return false;
    }

    /**
     * 设置是否显示光标
     * @param editText {@link EditText}
     * @param visible  是否显示光标
     * @param <T>      泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T setCursorVisible(
            final T editText,
            final boolean visible
    ) {
        if (editText != null) {
            editText.setCursorVisible(visible);
        }
        return editText;
    }

    /**
     * 获取光标位置
     * @param editText {@link EditText}
     * @param <T>      泛型
     * @return 光标位置
     */
    public static <T extends EditText> int getSelectionStart(final T editText) {
        if (editText != null) {
            return editText.getSelectionStart();
        }
        return 0;
    }

    /**
     * 设置光标在第一位
     * @param editText {@link EditText}
     * @param <T>      泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T setSelectionToTop(final T editText) {
        return setSelection(editText, 0);
    }

    /**
     * 设置光标在最后一位
     * @param editText {@link EditText}
     * @param <T>      泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T setSelectionToBottom(final T editText) {
        return setSelection(editText, getTextLength(editText));
    }

    /**
     * 设置光标位置
     * @param editText {@link EditText}
     * @param index    光标位置
     * @param <T>      泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T setSelection(
            final T editText,
            final int index
    ) {
        if (editText != null && index >= 0) {
            // 获取数据长度
            int length = editText.getText().toString().length();
            // 设置光标
            editText.setSelection((index > length) ? length : index);
        }
        return editText;
    }

    // =

    /**
     * 设置输入类型
     * @param editText {@link EditText}
     * @param <T>      泛型
     * @return 输入类型
     */
    public static <T extends EditText> int getInputType(final T editText) {
        if (editText != null) {
            return editText.getInputType();
        }
        return 0;
    }

    /**
     * 设置输入类型
     * @param editText {@link EditText}
     * @param type     类型
     * @param <T>      泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T setInputType(
            final T editText,
            final int type
    ) {
        if (editText != null) {
            editText.setInputType(type);
        }
        return editText;
    }

    // =

    /**
     * 设置软键盘右下角按钮类型
     * @param editText {@link EditText}
     * @param <T>      泛型
     * @return 软键盘右下角按钮类型
     */
    public static <T extends EditText> int getImeOptions(final T editText) {
        if (editText != null) {
            return editText.getImeOptions();
        }
        return 0;
    }

    /**
     * 设置软键盘右下角按钮类型
     * @param editText   {@link EditText}
     * @param imeOptions 软键盘按钮类型
     * @param <T>        泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T setImeOptions(
            final T editText,
            final int imeOptions
    ) {
        if (editText != null) {
            editText.setImeOptions(imeOptions);
        }
        return editText;
    }

    // =

    /**
     * 获取文本视图显示转换
     * @param editText {@link EditText}
     * @param <T>      泛型
     * @return {@link TransformationMethod}
     */
    public static <T extends EditText> TransformationMethod getTransformationMethod(final T editText) {
        if (editText != null) {
            return editText.getTransformationMethod();
        }
        return null;
    }

    /**
     * 设置文本视图显示转换
     * @param editText {@link EditText}
     * @param method   {@link TransformationMethod}
     * @param <T>      泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T setTransformationMethod(
            final T editText,
            final TransformationMethod method
    ) {
        if (editText != null) {
            editText.setTransformationMethod(method);
        }
        return editText;
    }

    // =

    /**
     * 设置密码文本视图显示转换
     * @param editText          {@link EditText}
     * @param isDisplayPassword 是否显示密码
     * @param <T>               泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T setTransformationMethod(
            final T editText,
            final boolean isDisplayPassword
    ) {
        return setTransformationMethod(editText, isDisplayPassword, true);
    }

    /**
     * 设置密码文本视图显示转换
     * @param editText          {@link EditText}
     * @param isDisplayPassword 是否显示密码
     * @param isSelectBottom    是否设置光标到最后
     * @param <T>               泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T setTransformationMethod(
            final T editText,
            final boolean isDisplayPassword,
            final boolean isSelectBottom
    ) {
        if (editText != null) {
            // 获取光标位置
            int curSelect = 0;
            if (!isSelectBottom) {
                curSelect = getSelectionStart(editText);
            }
            editText.setTransformationMethod(isDisplayPassword ?
                    HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
            if (isSelectBottom) { // 设置光标到最后
                setSelectionToBottom(editText);
            } else { // 设置光标到之前的位置
                setSelection(editText, curSelect);
            }
        }
        return editText;
    }

    // =

    /**
     * 添加输入监听事件
     * @param editText {@link EditText}
     * @param watcher  输入监听
     * @param <T>      泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T addTextChangedListener(
            final T editText,
            final TextWatcher watcher
    ) {
        if (editText != null && watcher != null) {
            editText.addTextChangedListener(watcher);
        }
        return editText;
    }

    /**
     * 移除输入监听事件
     * @param editText {@link EditText}
     * @param watcher  输入监听
     * @param <T>      泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T removeTextChangedListener(
            final T editText,
            final TextWatcher watcher
    ) {
        if (editText != null && watcher != null) {
            editText.removeTextChangedListener(watcher);
        }
        return editText;
    }

    // ========================
    // = Key Listener 快捷处理 =
    // ========================

    // 0123456789
    private static final char[] NUMBERS = {
            48, 49, 50, 51, 52, 53, 54, 55, 56, 57
    };

    // abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ
    private static final char[] LETTERS = {
            97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110,
            111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 65, 66,
            67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83,
            84, 85, 86, 87, 88, 89, 90
    };

    // 0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ
    private static final char[] NUMBERS_AND_LETTERS = {
            48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102,
            103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115,
            116, 117, 118, 119, 120, 121, 122, 65, 66, 67, 68, 69, 70, 71, 72,
            73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90
    };

    /**
     * 设置 KeyListener
     * @param editText    {@link EditText}
     * @param keyListener {@link KeyListener}
     * @param <T>         泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T setKeyListener(
            final T editText,
            final KeyListener keyListener
    ) {
        if (editText != null) {
            editText.setKeyListener(keyListener);
        }
        return editText;
    }

    /**
     * 设置 KeyListener
     * @param editText {@link EditText}
     * @param accepted 允许输入的内容, 如: 0123456789
     * @param <T>      泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T setKeyListener(
            final T editText,
            final String accepted
    ) {
        if (editText != null) {
            // editText.setKeyListener(DigitsKeyListener.getInstance(accepted));
            editText.setKeyListener(createDigitsKeyListener(-1, accepted));
        }
        return editText;
    }

    /**
     * 设置 KeyListener
     * @param editText {@link EditText}
     * @param accepted 允许输入的内容
     * @param <T>      泛型
     * @return {@link EditText}
     */
    public static <T extends EditText> T setKeyListener(
            final T editText,
            final char[] accepted
    ) {
        if (editText != null) {
            editText.setKeyListener(createDigitsKeyListener(-1, accepted));
        }
        return editText;
    }

    // =

    /**
     * 获取 DigitsKeyListener ( 限制只能输入字母, 默认弹出英文软键盘 )
     * @return {@link DigitsKeyListener}
     */
    public static DigitsKeyListener getLettersKeyListener() {
        return createDigitsKeyListener((InputType.TYPE_TEXT_VARIATION_PASSWORD), LETTERS);
    }

    /**
     * 获取 DigitsKeyListener ( 限制只能输入字母和数字, 默认弹出英文软键盘 )
     * @return {@link DigitsKeyListener}
     */
    public static DigitsKeyListener getNumberAndLettersKeyListener() {
        return createDigitsKeyListener((InputType.TYPE_TEXT_VARIATION_PASSWORD), NUMBERS_AND_LETTERS);
    }

    /**
     * 获取 DigitsKeyListener ( 限制只能输入数字, 默认弹出数字列表 )
     * @return {@link DigitsKeyListener}
     */
    public static DigitsKeyListener getNumberKeyListener() {
        return createDigitsKeyListener((InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL), NUMBERS);
    }

    // =

    /**
     * 创建 DigitsKeyListener
     * @param accepted 允许输入的内容 ( 可以传入 "", 这样无法输入内容 )
     * @return {@link DigitsKeyListener}
     */
    public static DigitsKeyListener createDigitsKeyListener(final String accepted) {
        return createDigitsKeyListener(-1, (accepted == null) ? null : accepted.toCharArray());
    }

    /**
     * 创建 DigitsKeyListener
     * @param inputType 输入类型
     * @param accepted  允许输入的内容 ( 可以传入 "", 这样无法输入内容 )
     * @return {@link DigitsKeyListener}
     */
    public static DigitsKeyListener createDigitsKeyListener(
            final int inputType,
            final String accepted
    ) {
        return createDigitsKeyListener(inputType, (accepted == null) ? null : accepted.toCharArray());
    }

    // =

    /**
     * 创建 DigitsKeyListener
     * @param accepted 允许输入的内容
     * @return {@link DigitsKeyListener}
     */
    public static DigitsKeyListener createDigitsKeyListener(final char[] accepted) {
        return createDigitsKeyListener(-1, accepted);
    }

    /**
     * 创建 DigitsKeyListener
     * @param inputType 输入类型
     * @param accepted  允许输入的内容
     * @return {@link DigitsKeyListener}
     */
    public static DigitsKeyListener createDigitsKeyListener(
            final int inputType,
            final char[] accepted
    ) {
        return new DigitsKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                if (accepted != null) {
                    return accepted;
                }
                return super.getAcceptedChars();
            }

            @Override
            public int getInputType() {
                if (inputType != -1) {
                    return inputType;
                }
                return super.getInputType();
            }
        };
    }

    // ===============
    // = TextWatcher =
    // ===============

    /**
     * detail: 开发输入监听抽象类
     * @author Ttt
     * <pre>
     *     @see <a href="https://blog.csdn.net/zhuwentao2150/article/details/51546773"/>
     *     editText.addTextChangedListener(DevTextWatcher);
     * </pre>
     */
    public static abstract class DevTextWatcher
            implements TextWatcher {

        // uuid ( 一定程度上唯一 )
        private final int     markId       = UUID.randomUUID().hashCode();
        // 判断是否操作中
        private       boolean operate      = false;
        // 标记状态, 特殊需求处理
        private       int     operateState = -1;
        // 类型
        private       int     type         = -1;

        /**
         * 构造函数
         */
        public DevTextWatcher() {
        }

        /**
         * 构造函数
         * @param type 类型
         */
        public DevTextWatcher(int type) {
            this.type = type;
        }

        /**
         * 获取标记 id
         * @return 标记 id
         */
        public final int getMarkId() {
            return markId;
        }

        /**
         * 判断是否操作中
         * @return {@code true} yes, {@code false} no
         */
        public final boolean isOperate() {
            return operate;
        }

        /**
         * 设置是否操作中
         * @param operate {@code true} yes, {@code false} no
         * @return {@link DevTextWatcher}
         */
        public final DevTextWatcher setOperate(boolean operate) {
            this.operate = operate;
            return this;
        }

        /**
         * 获取操作状态
         * @return 操作状态
         */
        public final int getOperateState() {
            return operateState;
        }

        /**
         * 设置操作状态
         * @param operateState 操作状态
         * @return {@link DevTextWatcher}
         */
        public final DevTextWatcher setOperateState(int operateState) {
            this.operateState = operateState;
            return this;
        }

        /**
         * 获取类型
         * @return 类型
         */
        public int getType() {
            return type;
        }

        /**
         * 设置类型
         * @param type 类型
         * @return {@link DevTextWatcher}
         */
        public DevTextWatcher setType(int type) {
            this.type = type;
            return this;
        }

        // ===========
        // = 回调接口 =
        // ===========

        /**
         * 在文本变化前调用
         * @param s     修改之前的文字
         * @param start 字符串中即将发生修改的位置
         * @param count 字符串中即将被修改的文字的长度, 如果是新增的话则为 0
         * @param after 被修改的文字修改之后的长度, 如果是删除的话则为 0
         */
        @Override
        public void beforeTextChanged(
                CharSequence s,
                int start,
                int count,
                int after
        ) {
        }

        /**
         * 在文本变化后调用
         * @param s      改变后的字符串
         * @param start  有变动的字符串的位置
         * @param before 被改变的字符串长度, 如果是新增则为 0
         * @param count  添加的字符串长度, 如果是删除则为 0
         */
        @Override
        public void onTextChanged(
                CharSequence s,
                int start,
                int before,
                int count
        ) {
        }

        /**
         * 在文本变化后调用
         * @param s 修改后的文字
         */
        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}