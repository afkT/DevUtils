package dev.assist;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * detail: 解决 Adapter 多个 Item 存在 EditText 监听输入问题
 * @author Ttt
 */
public class EditTextWatcherAssist<T> {

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 绑定事件
     * @param text     待设置文本
     * @param position 索引
     * @param editText EditText
     * @param listener 输入监听回调事件
     */
    public void bindListener(
            final CharSequence text,
            final int position,
            final EditText editText,
            final InputListener<T> listener
    ) {
        bindListener(text, position, editText, null, listener);
    }

    /**
     * 绑定事件
     * @param text     待设置文本
     * @param position 索引
     * @param editText EditText
     * @param object   Object
     * @param listener 输入监听回调事件
     */
    public void bindListener(
            final CharSequence text,
            final int position,
            final EditText editText,
            final T object,
            final InputListener<T> listener
    ) {
        if (editText != null) {
            // 设置内容
            editText.setText(TextUtils.isEmpty(text) ? "" : text);
            // 清空焦点
            editText.clearFocus();
            // 设置获取焦点事件
            editText.setOnFocusChangeListener(new FocusListener(position, editText, object, listener));
        }
    }

    // ===============
    // = 内部判断方法 =
    // ===============

    /**
     * detail: 输入监听回调事件
     * @param <T> 泛型
     * @author Ttt
     */
    public interface InputListener<T> {

        /**
         * 文本改变监听
         * @param charSequence 改变文本
         * @param editText     EditText
         * @param position     索引
         * @param object       Object
         */
        void onTextChanged(
                CharSequence charSequence,
                EditText editText,
                int position,
                T object
        );
    }

    // =================================
    // = 处理 Adapter Item ( EditText ) =
    // =================================

    // Text 改变事件
    private TextWatcher mTextWatcher;
    // 获得焦点的 EditText
    private EditText    mFocusEdit;
    // 获得焦点的索引
    private int         mFocusPos;

    /**
     * 焦点改变
     * @param editText EditText
     * @param position 索引
     */
    private void focusChange(
            final EditText editText,
            final int position
    ) {
        if (mTextWatcher != null) {
            if (mFocusEdit != null) {
                mFocusEdit.removeTextChangedListener(mTextWatcher);
            }
            mTextWatcher = null;
        }
        // 保存获得焦点的 EditText
        mFocusEdit = editText;
        // 保存索引
        mFocusPos = position;
    }

    // =

    /**
     * detail: 焦点事件监听
     * @author Ttt
     */
    private class FocusListener
            implements View.OnFocusChangeListener {

        // 当前索引
        private final int              position;
        // EditText
        private final EditText         editText;
        // Object
        private final T                object;
        // 输入监听事件
        private final InputListener<T> listener;

        /**
         * 构造函数
         * @param position 索引
         * @param editText EditText
         * @param object   Object
         * @param listener 输入监听回调事件
         */
        public FocusListener(
                int position,
                EditText editText,
                T object,
                InputListener<T> listener
        ) {
            this.position = position;
            this.editText = editText;
            this.object = object;
            this.listener = listener;
        }

        @Override
        public void onFocusChange(
                View v,
                boolean hasFocus
        ) {
            if (hasFocus) {
                // 获得焦点设置 View 操作
                focusChange(editText, position);
                // 判断是否为 null
                if (mTextWatcher == null) {
                    mTextWatcher = new TextWatcher() {
                        @Override
                        public void onTextChanged(
                                CharSequence charSequence,
                                int start,
                                int before,
                                int count
                        ) {
                            if (mFocusPos == position) {
                                if (listener != null) { // 触发回调
                                    listener.onTextChanged(charSequence, editText, position, object);
                                }
                            }
                        }

                        @Override
                        public void beforeTextChanged(
                                CharSequence s,
                                int start,
                                int count,
                                int after
                        ) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    };
                }
                if (mFocusEdit != null) { // 增加监听
                    mFocusEdit.addTextChangedListener(mTextWatcher);
                }
            } else { // 失去焦点, 清空操作
                focusChange(null, -1);
            }
        }
    }
}