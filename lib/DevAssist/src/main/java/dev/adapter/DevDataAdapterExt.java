package dev.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import dev.base.DevObject;
import dev.base.DevPage;

/**
 * detail: DataManager RecyclerView Adapter Extend
 * @author Ttt
 */
public abstract class DevDataAdapterExt<T, VH extends RecyclerView.ViewHolder>
        extends DevDataAdapter<T, VH> {

    // 通用 Object
    protected final DevObject<T> mObject = new DevObject<>();
    // Page 实体类
    protected       DevPage      mPage;

    public DevDataAdapterExt() {
    }

    public DevDataAdapterExt(Context context) {
        super(context);
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    public DevObject<T> getObject() {
        return mObject;
    }

    public DevPage getPage() {
        return mPage;
    }

    public DevDataAdapterExt<T, VH> setPage(final DevPage page) {
        this.mPage = page;
        return this;
    }
}