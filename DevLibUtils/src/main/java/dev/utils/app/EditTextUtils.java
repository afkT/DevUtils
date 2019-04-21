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
 *      @see <a href="https://blog.csdn.net/zhoujn90/article/details/44983905"/>
 * </pre>
 */
public final class EditTextUtils {

    private EditTextUtils() {
    }

    // 日志 TAG
    private static final String TAG = EditTextUtils.class.getSimpleName();

    /**
     * 设置是否显示光标
     * @param editText
     * @param visible
     * @return
     */
    public static EditText setCursorVisible(final EditText editText, final boolean visible) {
        if (editText != null) {
            editText.setCursorVisible(visible);
        }
        return editText;
    }

    // =

    /**
     * 设置长度限制，并且设置内容
     * @param editText
     * @param content
     * @param maxLength
     * @return
     */
    public static EditText setMaxLengthAnText(final EditText editText, final String content, final int maxLength) {
        if (editText != null) {
            // 设置长度限制, 并且设置内容
            setText(setMaxLength(editText, maxLength), content);
        }
        return editText;
    }

    /**
     * 设置长度限制
     * @param editText
     * @param maxLength
     * @return
     */
    public static EditText setMaxLength(final EditText editText, final int maxLength) {
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
     * 获取光标位置
     * @param editText
     * @return
     */
    public static int getSelectionStart(final EditText editText) {
        if (editText != null) {
            return editText.getSelectionStart();
        }
        return 0;
    }

    /**
     * 获取输入的内容
     * @param editText
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
     * @param editText
     * @return
     */
    public static int getTextLength(final EditText editText) {
        return getText(editText).length();
    }

    // =

    /**
     * 设置内容
     * @param editText
     * @param content
     */
    public static EditText setText(final EditText editText, final String content) {
        return setText(editText, content, true);
    }

    /**
     * 设置内容
     * @param editText
     * @param content
     * @param isSelect 是否设置光标
     */
    public static EditText setText(final EditText editText, final String content, final boolean isSelect) {
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
     * @param editText
     * @param content
     * @param isSelect
     * @return
     */
    public static EditText insert(final EditText editText, final String content, final boolean isSelect) {
        if (editText != null) {
            return insert(editText, content, editText.getSelectionStart(), isSelect);
        }
        return editText;
    }

    /**
     * 追加内容
     * @param editText
     * @param content
     * @param start    开始添加的位置
     * @param isSelect
     * @return
     */
    public static EditText insert(final EditText editText, final String content, final int start, final boolean isSelect) {
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
     * @param editText
     * @return
     */
    public static EditText setSelectTop(final EditText editText) {
        return setSelect(editText, 0);
    }

    /**
     * 设置光标在最后一位
     * @param editText
     * @return
     */
    public static EditText setSelectBottom(final EditText editText) {
        if (editText != null) {
            // 设置光标
            editText.setSelection(editText.getText().toString().length());
        }
        return editText;
    }

    /**
     * 设置光标位置
     * @param editText
     * @param select
     * @return
     */
    public static EditText setSelect(final EditText editText, final int select) {
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

    // =

    /**
     * 设置输入限制
     * @param editText
     * @param keyListener
     * @return
     */
    public static EditText setKeyListener(final EditText editText, final KeyListener keyListener) {
        if (editText != null) {
            editText.setKeyListener(keyListener);
        }
        return editText;
    }

    /**
     * 设置输入限制
     * @param editText
     * @param digits   只能输入的内容 -> 1234567890
     * @return
     */
    public static EditText setKeyListener(final EditText editText, final String digits) {
        if (editText != null) {
            if (TextUtils.isEmpty(digits)) {
                editText.setKeyListener(null);
            } else {
                editText.setKeyListener(DigitsKeyListener.getInstance(digits));
            }
        }
        return editText;
    }

    // ===============================
    // = 输入法Key Listener 快捷处理 =
    // ===============================

    /**
     * 限制只能输入字母和数字，默认弹出英文输入法
     * @return
     */
    public static DigitsKeyListener getNumberAndEnglishKeyListener() {
        /** 限制只能输入字母和数字，默认弹出英文输入法 */
        DigitsKeyListener digitsKeyListener = new DigitsKeyListener() {
            @Override
            public int getInputType() {
                return InputType.TYPE_TEXT_VARIATION_PASSWORD;
            }

            @Override
            protected char[] getAcceptedChars() {
                char[] data = "qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM1234567890".toCharArray();
                return data;
            }
        };
        return digitsKeyListener;
    }

    /**
     * 限制只能输入数字，默认弹出数字列表
     * @return
     */
    public static DigitsKeyListener getNumberKeyListener() {
        /** 限制只能输入数字，默认弹出数字列表 */
        DigitsKeyListener digitsKeyListener = new DigitsKeyListener() {
            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL;
            }

            @Override
            protected char[] getAcceptedChars() {
                char[] data = "1234567890".toCharArray();
                return data;
            }
        };
        return digitsKeyListener;
    }

    // =

    /**
     * detail: 开发输入监听抽象类
     * @author Ttt
     * <pre>
     *      @see <a href="https://blog.csdn.net/zhuwentao2150/article/details/51546773"/>
     *      editText.addTextChangedListener(DevTextWatcher);
     * </pre>
     */
    public static abstract class DevTextWatcher implements TextWatcher {

        // 标记id - 一定程度上唯一
        private final int markId;
        // 判断是否操作中 - 操作状态 -> 如果是否使用搜索数据等
        private boolean operate = false;
        // 标记状态, 特殊需求处理
        private int operateState = -1;

        public DevTextWatcher() {
            // 初始化id
            this.markId = UUID.randomUUID().hashCode();
        }

        /**
         * 获取标记id
         * @return
         */
        public final int getMarkId() {
            return markId;
        }

        /**
         * 判断是否操作中
         * @return
         */
        public final boolean isOperate() {
            return operate;
        }

        /**
         * 设置是否操作中
         * @param operate
         */
        public final void setOperate(boolean operate) {
            this.operate = operate;
        }

        /**
         * 获取操作状态
         * @return
         */
        public final int getOperateState() {
            return operateState;
        }

        /**
         * 设置操作状态
         * @param operateState
         */
        public final void setOperateState(int operateState) {
            this.operateState = operateState;
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
