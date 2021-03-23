package dev.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import dev.base.DevObject;
import dev.base.DevPage;
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

    // ===============
    // = 对外公开方法 =
    // ===============

    // 通用 Object
    protected final DevObject<T>            mObject = new DevObject<>();
    // Page 实体类
    protected       DevPage<?>              mPage;
    // 通用回调
    protected       DevCallback<T>          mCallback;
    // 通用 Item Click 回调
    protected       DevItemClickCallback<T> mItemCallback;

    /**
     * 获取通用 Object
     * @return {@link DevObject}
     */
    public DevObject<T> getObject() {
        return mObject;
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
}