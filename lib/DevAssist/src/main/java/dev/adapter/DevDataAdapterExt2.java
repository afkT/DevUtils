package dev.adapter;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedHashMap;
import java.util.List;

import dev.base.multiselect.IMultiSelectEdit;

/**
 * detail: DataManager RecyclerView Adapter Extend
 * @author Ttt
 * <pre>
 *     在 {@link DevDataAdapterExt} 基础上实现多选辅助
 * </pre>
 */
public abstract class DevDataAdapterExt2<T, VH extends RecyclerView.ViewHolder>
        extends DevDataAdapterExt<T, VH>
        implements IMultiSelectEdit<DevDataAdapterExt2<T, VH>> {

    public DevDataAdapterExt2() {
    }

    public DevDataAdapterExt2(Context context) {
        super(context);
    }

    public DevDataAdapterExt2(Activity activity) {
        super(activity);
    }

    // =============
    // = 对外公开方法 =
    // =============

    // 是否通知适配器 ( 通用: 如多选操作后是否通知适配器 )
    protected boolean mNotifyAdapter = true;

    /**
     * 是否通知适配器 ( 通用: 如多选操作后是否通知适配器 )
     * @return {@code true} yes, {@code false} no
     */
    public boolean isNotifyAdapter() {
        return mNotifyAdapter;
    }

    /**
     * 设置是否通知适配器 ( 通用: 如多选操作后是否通知适配器 )
     * @param notifyAdapter {@code true} yes, {@code false} no
     * @return {@link DevDataAdapterExt2}
     */
    public DevDataAdapterExt2<T, VH> setNotifyAdapter(boolean notifyAdapter) {
        mNotifyAdapter = notifyAdapter;
        return this;
    }

    // ====================
    // = IMultiSelectEdit =
    // ====================

    // 是否编辑状态
    protected boolean mEdit;

    @Override
    public boolean isEditState() {
        return mEdit;
    }

    @Override
    public DevDataAdapterExt2<T, VH> setEditState(boolean isEdit) {
        this.mEdit = isEdit;
        if (mNotifyAdapter) mAssist.notifyDataChanged();
        return this;
    }

    @Override
    public DevDataAdapterExt2<T, VH> toggleEditState() {
        return setEditState(!mEdit);
    }

    @Override
    public DevDataAdapterExt2<T, VH> clearSelectAll() {
        mMultiSelectMap.clearSelects();
        if (mNotifyAdapter) mAssist.notifyDataChanged();
        return this;
    }

    @Override
    public boolean isSelectAll() {
        int size = mMultiSelectMap.getSelectSize();
        if (size == 0) return false;
        return getItemCount() == size;
    }

    @Override
    public boolean isSelect() {
        return mMultiSelectMap.isSelect();
    }

    @Override
    public boolean isNotSelect() {
        return !mMultiSelectMap.isSelect();
    }

    @Override
    public int getSelectSize() {
        return mMultiSelectMap.getSelectSize();
    }

    @Override
    public int getDataCount() {
        return getItemCount();
    }

    // =

    @Override
    public DevDataAdapterExt2<T, VH> selectAll() {
        LinkedHashMap<String, T> maps = new LinkedHashMap<>();
        for (int i = 0, len = getDataCount(); i < len; i++) {
            T item = getDataItem(i);
            maps.put(getMultiSelectKey(item, i), item);
        }
        mMultiSelectMap.putSelects(maps);
        if (mNotifyAdapter) mAssist.notifyDataChanged();
        return this;
    }

    @Override
    public DevDataAdapterExt2<T, VH> inverseSelect() {
        if (isNotSelect()) return selectAll();

        List<String> keys = mMultiSelectMap.getSelectKeys();

        LinkedHashMap<String, T> maps = new LinkedHashMap<>();
        for (int i = 0, len = getDataCount(); i < len; i++) {
            T item = getDataItem(i);
            maps.put(getMultiSelectKey(item, i), item);
        }
        mMultiSelectMap.putSelects(maps);

        for (String key : keys) {
            mMultiSelectMap.unselect(key);
        }
        if (mNotifyAdapter) mAssist.notifyDataChanged();
        return this;
    }

    // =

    /**
     * 获取多选标记 Key
     * <pre>
     *     用于 {@link #selectAll()}、{@link #inverseSelect()}
     * </pre>
     * @param item     泛型实体类 Item
     * @param position 索引
     * @return 多选标记 Key
     */
    public abstract String getMultiSelectKey(
            T item,
            int position
    );
}