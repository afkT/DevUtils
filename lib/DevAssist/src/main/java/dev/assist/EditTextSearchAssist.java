package dev.assist;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import dev.utils.app.assist.DelayAssist;

/**
 * detail: EditText 搜索辅助类
 * @author Ttt
 * <pre>
 *     用于 EditText 输入实时搜索
 *     降低每触发一次 {@link TextWatcher#onTextChanged} 进行一次查询频率
 *     <p></p>
 *     使用:
 *     第一种
 *     在 {@link TextWatcher#onTextChanged} 中调用 {@link #post(CharSequence)}
 *     第二种
 *     调用 {@link #setCallback(SearchCallback, EditText)} 传入需要监听的 EditText
 *     或使用 {@link #bindEditText(EditText)} 绑定 EditText 输入事件
 * </pre>
 */
public class EditTextSearchAssist {

    // 搜索回调
    private       SearchCallback mCallback;
    // 延迟触发回调类
    private final DelayAssist    mDelayAssist = new DelayAssist(object -> {
        if (mCallback != null && object instanceof CharSequence) {
            mCallback.callback((CharSequence) object);
        }
    });

    // ===========
    // = 构造函数 =
    // ===========

    public EditTextSearchAssist() {
        this(DelayAssist.DELAY_MILLIS, null);
    }

    public EditTextSearchAssist(long delayMillis) {
        this(delayMillis, null);
    }

    public EditTextSearchAssist(SearchCallback callback) {
        this(DelayAssist.DELAY_MILLIS, callback);
    }

    public EditTextSearchAssist(
            long delayMillis,
            SearchCallback callback
    ) {
        mCallback = callback;
        mDelayAssist.setDelayMillis(delayMillis);
    }

    /**
     * detail: 搜索回调接口
     * @author Ttt
     */
    public interface SearchCallback {

        /**
         * 搜索回调
         * @param content 搜索内容
         */
        void callback(CharSequence content);
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 移除消息
     */
    public void remove() {
        mDelayAssist.remove();
    }

    /**
     * 发送消息 ( 功能由该方法实现 )
     * @param charSequence 输入内容
     */
    public void post(final CharSequence charSequence) {
        mDelayAssist.post(charSequence);
    }

    // =

    /**
     * 设置搜索延迟时间
     * @param delayMillis 延迟时间
     * @return {@link EditTextSearchAssist}
     */
    public EditTextSearchAssist setDelayMillis(final long delayMillis) {
        mDelayAssist.setDelayMillis(delayMillis);
        return this;
    }

    /**
     * 设置搜索回调接口
     * @param callback {@link SearchCallback}
     * @return {@link EditTextSearchAssist}
     */
    public EditTextSearchAssist setCallback(final SearchCallback callback) {
        mCallback = callback;
        return this;
    }

    /**
     * 设置搜索回调接口并监听 EditText 输入
     * @param callback {@link SearchCallback}
     * @param editText {@link EditText}
     * @return {@link EditTextSearchAssist}
     */
    public EditTextSearchAssist setCallback(
            final SearchCallback callback,
            final EditText editText
    ) {
        bindEditText(editText);
        return setCallback(callback);
    }

    /**
     * 绑定 EditText 输入事件
     * @param editText {@link EditText}
     * @return {@link EditTextSearchAssist}
     */
    public EditTextSearchAssist bindEditText(final EditText editText) {
        if (editText != null) {
            initTextWatcher();
            editText.removeTextChangedListener(mTextWatcher);
            editText.addTextChangedListener(mTextWatcher);
        }
        return this;
    }

    // ===========
    // = 内部逻辑 =
    // ===========

    private TextWatcher mTextWatcher;

    private void initTextWatcher() {
        if (mTextWatcher != null) return;
        mTextWatcher = new TextWatcher() {
            @Override
            public void onTextChanged(
                    CharSequence charSequence,
                    int start,
                    int before,
                    int count
            ) {
                post(charSequence);
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
}