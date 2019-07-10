package dev.utils.app;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.widget.EditText;

import java.util.UUID;

import dev.utils.LogPrintUtils;

/**
 * detail: EditText 工具类
 * @author Ttt
 * <pre>
 *     Android EditText 属性大全 ( 不局限于仅仅是 EditText)
 *     @see <a href="https://my.oschina.net/xsjayz/blog/121685"/>
 *     <p></p>
 *     Android EditText 多行显示及所有属性
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
 *     Android 自定义 EditText 光标和下划线颜色
 *     @see <a href="https://segmentfault.com/a/1190000009507919"/>
 * </pre>
 */
public final class EditTextUtils {

    private EditTextUtils() {
    }

    // 日志 TAG
    private static final String TAG = EditTextUtils.class.getSimpleName();

    /**
     * 设置是否显示光标
     * @param editText {@link EditText}
     * @param visible 是否显示光标
     * @return {@link EditText}
     */
    public static <T extends EditText> T setCursorVisible(final T editText, final boolean visible) {
        if (editText != null) {
            editText.setCursorVisible(visible);
        }
        return editText;
    }

    /**
     * 获取光标位置
     * @param editText {@link EditText}
     * @return 光标位置
     */
    public static int getSelectionStart(final EditText editText) {
        if (editText != null) {
            return editText.getSelectionStart();
        }
        return 0;
    }

    /**
     * 设置长度限制, 并且设置内容
     * @param editText  {@link EditText}
     * @param content 文本内容
     * @param maxLength 长度限制
     * @return {@link EditText}
     */
    public static <T extends EditText> T setMaxLengthAndText(final T editText, final String content, final int maxLength) {
        return setText(setMaxLength(editText, maxLength), content);
    }

    /**
     * 设置长度限制
     * @param editText  {@link EditText}
     * @param maxLength 长度限制
     * @return {@link EditText}
     */
    public static <T extends EditText> T setMaxLength(final T editText, final int maxLength) {
        if (editText != null) {
            if (maxLength > 0) {
                // 设置最大长度限制
                InputFilter[] filters = {new InputFilter.LengthFilter(maxLength)};
                editText.setFilters(filters);
            }
        }
        return editText;
    }

    /**
     * 获取输入的内容
     * @param editText {@link EditText}
     * @return
     */
    public static String getText(final EditText editText) {
        if (editText != null) {
            return editText.getText().toString();
        }
        return "";
    }

    /**
     * 获取输入的内容长度
     * @param editText {@link EditText}
     * @return
     */
    public static int getTextLength(final EditText editText) {
        return getText(editText).length();
    }

    // =

    /**
     * 设置内容
     * @param editText {@link EditText}
     * @param content
     * @return {@link EditText}
     */
    public static <T extends EditText> T setText(final T editText, final String content) {
        return setText(editText, content, true);
    }

    /**
     * 设置内容
     * @param editText {@link EditText}
     * @param content
     * @param isSelect 是否设置光标
     * @return {@link EditText}
     */
    public static <T extends EditText> T setText(final T editText, final String content, final boolean isSelect) {
        if (editText != null) {
            if (content != null) {
                // 设置文本
                editText.setText(content);
                if (isSelect) {
                    // 设置光标
                    editText.setSelection(editText.getText().toString().length());
                }
            }
        }
        return editText;
    }

    /**
     * 追加内容 - 当前光标位置追加
     * @param editText {@link EditText}
     * @param content
     * @param isSelect
     * @return
     */
    public static <T extends EditText> T insert(final T editText, final String content, final boolean isSelect) {
        if (editText != null) {
            return insert(editText, content, editText.getSelectionStart(), isSelect);
        }
        return editText;
    }

    /**
     * 追加内容
     * @param editText {@link EditText}
     * @param content
     * @param start    开始添加的位置
     * @param isSelect
     * @return
     */
    public static <T extends EditText> T insert(final T editText, final String content, final int start, final boolean isSelect) {
        if (editText != null) {
            if (!TextUtils.isEmpty(content)) {
                try {
                    Editable editable = editText.getText();
                    // 在指定位置 追加内容
                    editable.insert(start, content);
                    // 判断是否选中
                    if (isSelect) {
                        // 设置光标
                        editText.setSelection(editText.getText().toString().length());
                    }
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "insert");
                }
            }
        }
        return editText;
    }

    // =

    /**
     * 设置光标在第一位
     * @param editText {@link EditText}
     * @return
     */
    public static <T extends EditText> T setSelectTop(final T editText) {
        return setSelect(editText, 0);
    }

    /**
     * 设置光标在最后一位
     * @param editText {@link EditText}
     * @return
     */
    public static <T extends EditText> T setSelectBottom(final T editText) {
        if (editText != null) {
            // 设置光标
            editText.setSelection(editText.getText().toString().length());
        }
        return editText;
    }

    /**
     * 设置光标位置
     * @param editText {@link EditText}
     * @param select
     * @return
     */
    public static <T extends EditText> T setSelect(final T editText, final int select) {
        if (editText != null) {
            if (select >= 0) {
                // 判断是否超过限制
                int length = editText.getText().toString().length();
                // 如果超过长度, 则设置最后
                if (select > length) {
                    // 设置光标
                    editText.setSelection(length);
                } else {
                    // 设置光标
                    editText.setSelection(select);
                }
            }
        }
        return editText;
    }

    // =========================
    // = Key Listener 快捷处理 =
    // =========================

    // 0123456789
    private static final char[] NUMBERS = new char[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57};

    // abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ
    private static final char[] LETTERS = new char[]{97, 98, 99, 100, 101,
            102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114,
            115, 116, 117, 118, 119, 120, 121, 122, 65, 66, 67, 68, 69, 70, 71,
            72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88,
            89, 90};

    // 0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ
    private static final char[] NUMBERS_AND_LETTERS = new char[]{48, 49, 50,
            51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102, 103, 104,
            105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117,
            118, 119, 120, 121, 122, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74,
            75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90};

    /**
     * 设置 KeyListener
     * @param editText    {@link EditText}
     * @param keyListener {@link KeyListener}
     * @return {@link EditText}
     */
    public static <T extends EditText> T setKeyListener(final T editText, final KeyListener keyListener) {
        if (editText != null) {
            editText.setKeyListener(keyListener);
        }
        return editText;
    }

    /**
     * 设置 KeyListener
     * @param editText {@link EditText}
     * @param accepted 允许输入的内容, 如: 0123456789
     * @return {@link EditText}
     */
    public static <T extends EditText> T setKeyListener(final T editText, final String accepted) {
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
     * @return {@link EditText}
     */
    public static <T extends EditText> T setKeyListener(final T editText, final char[] accepted) {
        if (editText != null) {
            editText.setKeyListener(createDigitsKeyListener(-1, accepted));
        }
        return editText;
    }

    // =

    /**
     * 获取 DigitsKeyListener ( 限制只能输入字母, 默认弹出英文输入法 )
     * @return {@link DigitsKeyListener}
     */
    public static DigitsKeyListener getLettersKeyListener() {
        return createDigitsKeyListener((InputType.TYPE_TEXT_VARIATION_PASSWORD), LETTERS);
    }

    /**
     * 获取 DigitsKeyListener ( 限制只能输入字母和数字, 默认弹出英文输入法 )
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
    public static DigitsKeyListener createDigitsKeyListener(final int inputType, final String accepted) {
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
    public static DigitsKeyListener createDigitsKeyListener(final int inputType, final char[] accepted) {
        DigitsKeyListener digitsKeyListener = new DigitsKeyListener() {
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
        return digitsKeyListener;
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
    public static abstract class DevTextWatcher implements TextWatcher {

        // 标记 id - 一定程度上唯一
        private final int markId;
        // 判断是否操作中
        private boolean operate = false;
        // 标记状态, 特殊需求处理
        private int operateState = -1;
        // 类型
        private int type = -1;

        /**
         * 构造函数
         */
        public DevTextWatcher() {
            // 初始化 id
            this.markId = UUID.randomUUID().hashCode();
        }

        /**
         * 构造函数
         * @param type 类型
         */
        public DevTextWatcher(int type) {
            this.type = type;
            // 初始化 id
            this.markId = UUID.randomUUID().hashCode();
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
         */
        public final void setOperate(boolean operate) {
            this.operate = operate;
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
         */
        public final void setOperateState(int operateState) {
            this.operateState = operateState;
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
         */
        public void setType(int type) {
            this.type = type;
        }

        // ============
        // = 回调接口 =
        // ============

        /**
         * 在文本变化前调用
         * @param s
         * @param start
         * @param count
         * @param after
         */
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        /**
         * 在文本变化后调用
         * @param s
         * @param start
         * @param before
         * @param count
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        /**
         * 在文本变化后调用
         * @param s
         */
        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}