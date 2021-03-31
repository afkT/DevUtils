package dev.adapter;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import dev.assist.EditTextWatcherAssist;
import dev.base.DevObject;
import dev.base.DevPage;
import dev.base.expand.RequestState;
import dev.base.multiselect.DevMultiSelectMap;
import dev.callback.DevCallback;
import dev.callback.DevItemClickCallback;

/**
 * detail: DataManager RecyclerView Adapter Extend
 * @author Ttt
 */
public abstract class DevDataAdapterExt<T, VH extends RecyclerView.ViewHolder>
        extends DevDataAdapter<T, VH> {

    public DevDataAdapterExt() {
    }

    public DevDataAdapterExt(Context context) {
        super(context);
    }

    public DevDataAdapterExt(Activity activity) {
        super(activity);
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    // 通用 Object
    protected DevObject<T>                 mObject = new DevObject<>();
    // Page 实体类
    protected DevPage<?>                   mPage;
    // 通用回调
    protected DevCallback<T>               mCallback;
    // 通用 Item Click 回调
    protected DevItemClickCallback<T>      mItemCallback;
    // 请求状态
    protected RequestState<T>              mState;
    // EditText 输入监听辅助类
    protected EditTextWatcherAssist<T>     mTextWatcherAssist;
    // 多选辅助类
    protected DevMultiSelectMap<String, T> mMultiSelectMap;

    /**
     * 获取通用 Object
     * @return {@link DevObject}
     */
    public DevObject<T> getObject() {
        return mObject;
    }

    /**
     * 设置通用 Object
     * @param object {@link DevObject}
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setObject(final DevObject<T> object) {
        this.mObject = object;
        return this;
    }

    /**
     * 获取 Page 实体类
     * @return {@link DevPage}
     */
    public DevPage getPage() {
        return mPage;
    }

    /**
     * 设置 Page 实体类
     * @param page {@link DevPage}
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setPage(final DevPage<?> page) {
        this.mPage = page;
        return this;
    }

    /**
     * 获取通用回调
     * @return {@link DevCallback}
     */
    public DevCallback<T> getCallback() {
        return mCallback;
    }

    /**
     * 设置通用回调
     * @param callback {@link DevCallback}
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setCallback(final DevCallback<T> callback) {
        this.mCallback = callback;
        return this;
    }

    /**
     * 获取通用 Item Click 回调
     * @return {@link DevItemClickCallback}
     */
    public DevItemClickCallback<T> getItemCallback() {
        return mItemCallback;
    }

    /**
     * 设置通用 Item Click 回调
     * @param itemCallback {@link DevItemClickCallback}
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setItemCallback(final DevItemClickCallback<T> itemCallback) {
        this.mItemCallback = itemCallback;
        return this;
    }

    /**
     * 请求状态实体类
     * @return {@link RequestState}
     */
    public RequestState<T> getState() {
        return mState;
    }

    /**
     * 设置请求状态实体类
     * @param state {@link RequestState}
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setState(final RequestState<T> state) {
        this.mState = state;
        return this;
    }

    /**
     * 获取 EditText 输入监听辅助类
     * @return {@link EditTextWatcherAssist}
     */
    public EditTextWatcherAssist<T> getTextWatcherAssist() {
        return mTextWatcherAssist;
    }

    /**
     * 设置 EditText 输入监听辅助类
     * @param textWatcherAssist {@link EditTextWatcherAssist}
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setTextWatcherAssist(final EditTextWatcherAssist<T> textWatcherAssist) {
        this.mTextWatcherAssist = textWatcherAssist;
        return this;
    }

    /**
     * 获取多选辅助类
     * @return {@link DevMultiSelectMap}
     */
    public DevMultiSelectMap<String, T> getMultiSelectMap() {
        return mMultiSelectMap;
    }

    /**
     * 设置多选辅助类
     * @param multiSelectMap {@link DevMultiSelectMap}
     * @return {@link DevDataAdapterExt}
     */
    public DevDataAdapterExt<T, VH> setMultiSelectMap(final DevMultiSelectMap<String, T> multiSelectMap) {
        this.mMultiSelectMap = multiSelectMap;
        return this;
    }
}