package dev.assist;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * detail: 解决 Adapter 多个 Item 存在 EditText 监听输入问题
 * @author Ttt
 */
public class EditTextWatcherAssist<T> {

    // =============
    // = 对外公开方法 =
    // =============

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
        bindListener(text, position, editText, null, listener, null);
    }

    /**
     * 绑定事件
     * @param text          待设置文本
     * @param position      索引
     * @param editText      EditText
     * @param object        Object
     * @param listener      输入监听回调事件
     * @param otherListener 其他事件触发扩展抽象类
     */
    public void bindListener(
            final CharSequence text,
            final int position,
            final EditText editText,
            final T object,
            final InputListener<T> listener,
            final OtherListener<T> otherListener
    ) {
        if (editText != null) {
            // 设置内容
            editText.setText(text);
            // 清空焦点
            editText.clearFocus();
            // 设置获取焦点事件
            editText.setOnFocusChangeListener(new FocusListener(
                    position, editText, object, listener, otherListener
            ));
        }
    }

    // =============
    // = 内部判断方法 =
    // =============

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
        // 其他事件触发扩展抽象类
        private final OtherListener<T> otherListener;

        /**
         * 构造函数
         * @param position      索引
         * @param editText      EditText
         * @param object        Object
         * @param listener      输入监听回调事件
         * @param otherListener 其他事件触发扩展抽象类
         */
        public FocusListener(
                int position,
                EditText editText,
                T object,
                InputListener<T> listener,
                OtherListener<T> otherListener
        ) {
            this.position      = position;
            this.editText      = editText;
            this.object        = object;
            this.listener      = listener;
            this.otherListener = otherListener;
        }

        @Override
        public void onFocusChange(
                View v,
                boolean hasFocus
        ) {
            if (mFocusPos == position) {
                if (otherListener != null) {
                    otherListener.onFocusChange(
                            true, hasFocus,
                            editText, position, object
                    );
                }
            }

            if (hasFocus) {
                // 获得焦点设置 View 操作
                focusChange(editText, position);
                // 判断是否为 null
                if (mTextWatcher == null) {
                    mTextWatcher = new TextWatcher() {
                        @Override
                        public void onTextChanged(
                                CharSequence text,
                                int start,
                                int before,
                                int count
                        ) {
                            if (mFocusPos == position) {
                                if (otherListener != null) {
                                    otherListener.onTextChanged(
                                            text, start, before, count,
                                            editText, position, object
                                    );
                                }

                                if (listener != null) { // 触发回调
                                    listener.onTextChanged(text, editText, position, object);
                                }
                            }
                        }

                        @Override
                        public void beforeTextChanged(
                                CharSequence text,
                                int start,
                                int count,
                                int after
                        ) {
                            if (mFocusPos == position) {
                                if (otherListener != null) {
                                    otherListener.beforeTextChanged(
                                            text, start, count, after,
                                            editText, position, object
                                    );
                                }
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable text) {
                            if (mFocusPos == position) {
                                if (otherListener != null) {
                                    otherListener.afterTextChanged(
                                            text, editText, position, object
                                    );
                                }
                            }
                        }
                    };
                }
                if (mFocusEdit != null) { // 增加监听
                    mFocusEdit.addTextChangedListener(mTextWatcher);
                }
            } else { // 失去焦点, 清空操作
                focusChange(null, -1);
            }
            if (mFocusPos == position) {
                if (otherListener != null) {
                    otherListener.onFocusChange(
                            false, hasFocus,
                            editText, position, object
                    );
                }
            }
        }
    }

    // ==========
    // = 事件相关 =
    // ==========

    /**
     * detail: 输入监听回调事件
     * @param <T> 泛型
     * @author Ttt
     */
    public interface InputListener<T> {

        /**
         * 文本改变监听
         * @param text     改变文本
         * @param editText EditText
         * @param position 索引
         * @param object   Object
         */
        void onTextChanged(
                CharSequence text,
                EditText editText,
                int position,
                T object
        );
    }

    /**
     * detail: 其他事件触发扩展抽象类
     * @param <T> 泛型
     * @author Ttt
     */
    public static abstract class OtherListener<T> {

        // =========================
        // = OnFocusChangeListener =
        // =========================

        /**
         * 焦点触发方法
         * @param before   {@code true} 进入方法先触发, {@code false} 逻辑处理后再次触发
         * @param hasFocus 是否获取焦点
         * @param editText EditText
         * @param position 索引
         * @param object   Object
         */
        public void onFocusChange(
                boolean before,
                boolean hasFocus,
                EditText editText,
                int position,
                T object
        ) {
        }

        // ===============
        // = TextWatcher =
        // ===============

        /**
         * 在文本变化前调用
         * @param text     修改之前的文字
         * @param start    字符串中即将发生修改的位置
         * @param count    字符串中即将被修改的文字的长度, 如果是新增的话则为 0
         * @param after    被修改的文字修改之后的长度, 如果是删除的话则为 0
         * @param editText EditText
         * @param position 索引
         * @param object   Object
         */
        public void beforeTextChanged(
                CharSequence text,
                int start,
                int count,
                int after,
                EditText editText,
                int position,
                T object
        ) {
        }

        /**
         * 在文本变化后调用
         * @param text     改变后的字符串
         * @param start    有变动的字符串的位置
         * @param before   被改变的字符串长度, 如果是新增则为 0
         * @param count    添加的字符串长度, 如果是删除则为 0
         * @param editText EditText
         * @param position 索引
         * @param object   Object
         */
        public void onTextChanged(
                CharSequence text,
                int start,
                int before,
                int count,
                EditText editText,
                int position,
                T object
        ) {
        }

        /**
         * 在文本变化后调用
         * @param text     修改后的文字
         * @param editText EditText
         * @param position 索引
         * @param object   Object
         */
        public void afterTextChanged(
                Editable text,
                EditText editText,
                int position,
                T object
        ) {
        }
    }
}