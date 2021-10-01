package dev.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dev.assist.DataAssist;
import dev.base.data.DataChanged;
import dev.base.data.DataManager;
import dev.utils.app.ActivityUtils;

/**
 * detail: DataManager RecyclerView Adapter
 * @author Ttt
 */
public abstract class DevDataAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH>
        implements DataManager<T>,
        DataChanged<T> {

    // 数据辅助类
    protected DataAssist<T> mAssist = new DataAssist<>(this);
    // Context
    protected Context       mContext;
    // Activity
    protected Activity      mActivity;
    // RecyclerView
    protected RecyclerView  mRecyclerView;

    public DevDataAdapter() {
    }

    public DevDataAdapter(Context context) {
        this.mContext  = context;
        this.mActivity = ActivityUtils.getActivity(context);
    }

    public DevDataAdapter(Activity activity) {
        this.mContext  = activity;
        this.mActivity = activity;
    }

    public DevDataAdapter(RecyclerView recyclerView) {
        bindAdapter(recyclerView);
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取 Context
     * @return {@link Context}
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * 设置 Context
     * @param context {@link Context}
     * @return {@link DevDataAdapter}
     */
    public DevDataAdapter<T, VH> setContext(final Context context) {
        this.mContext = context;
        return this;
    }

    /**
     * 获取 Activity
     * @return {@link Activity}
     */
    public Activity getActivity() {
        return mActivity;
    }

    /**
     * 设置 Activity
     * @param activity {@link Activity}
     * @return {@link DevDataAdapter}
     */
    public DevDataAdapter<T, VH> setActivity(Activity activity) {
        this.mActivity = activity;
        return this;
    }

    /**
     * 通过 ViewGroup 设置 Context
     * <pre>
     *     在 {@link #onCreateViewHolder(ViewGroup, int)} 中调用, 传入 ViewGroup
     * </pre>
     * @param parent {@link ViewGroup}
     * @return {@link DevDataAdapter}
     */
    public DevDataAdapter<T, VH> parentContext(ViewGroup parent) {
        if (parent != null && mContext == null) {
            this.mContext = parent.getContext();
        }
        return this;
    }

    // ========================
    // = RecyclerView.Adapter =
    // ========================

    @Override
    public int getItemCount() {
        return getDataSize();
    }

    // ================
    // = RecyclerView =
    // ================

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public DevDataAdapter<T, VH> setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        return this;
    }

    public DevDataAdapter<T, VH> bindAdapter(RecyclerView recyclerView) {
        return bindAdapter(recyclerView, true);
    }

    public DevDataAdapter<T, VH> bindAdapter(
            RecyclerView recyclerView,
            boolean set
    ) {
        if (recyclerView != null) {
            // 进行设置 Context、Activity
            if (mContext == null) {
                mContext = recyclerView.getContext();
            }
            if (mActivity == null) {
                mActivity = ActivityUtils.getActivity(recyclerView.getContext());
            }
            recyclerView.setAdapter(this);
        }
        if (set) setRecyclerView(recyclerView);
        return this;
    }

    // ===============
    // = DataManager =
    // ===============

    // ==========
    // = 获取相关 =
    // ==========

    @Override
    public List<T> getDataList() {
        return mAssist.getDataList();
    }

    @Override
    public ArrayList<T> getDataArrayList() {
        return mAssist.getDataArrayList();
    }

    @Override
    public int getDataSize() {
        return mAssist.getDataSize();
    }

    @Override
    public T getDataItem(int position) {
        return mAssist.getDataItem(position);
    }

    @Override
    public int getDataItemPosition(T value) {
        return mAssist.getDataItemPosition(value);
    }

    @Override
    public T getFirstData() {
        return mAssist.getFirstData();
    }

    @Override
    public T getLastData() {
        return mAssist.getLastData();
    }

    @Override
    public int getLastPosition() {
        return mAssist.getLastPosition();
    }

    // ==========
    // = 快捷方法 =
    // ==========

    @Override
    public boolean isDataEmpty() {
        return mAssist.isDataEmpty();
    }

    @Override
    public boolean isDataNotEmpty() {
        return mAssist.isDataNotEmpty();
    }

    @Override
    public boolean isFirstPosition(int position) {
        return mAssist.isFirstPosition(position);
    }

    @Override
    public boolean isLastPosition(int position) {
        return mAssist.isLastPosition(position);
    }

    @Override
    public boolean isLastPosition(
            int position,
            int size
    ) {
        return mAssist.isLastPosition(position, size);
    }

    @Override
    public boolean isLastPositionAndGreaterThanOrEqual(
            int position,
            int value
    ) {
        return mAssist.isLastPositionAndGreaterThanOrEqual(position, value);
    }

    @Override
    public boolean isLastPositionAndGreaterThanOrEqual(
            int position,
            int value,
            int size
    ) {
        return mAssist.isLastPositionAndGreaterThanOrEqual(position, value, size);
    }

    @Override
    public boolean equalsFirstData(T value) {
        return mAssist.equalsFirstData(value);
    }

    @Override
    public boolean equalsLastData(T value) {
        return mAssist.equalsLastData(value);
    }

    @Override
    public boolean equalsPositionData(
            int position,
            T value
    ) {
        return mAssist.equalsPositionData(position, value);
    }

    // =====
    // = 增 =
    // =====

    @Override
    public boolean addData(T value) {
        return mAssist.addData(value);
    }

    @Override
    public boolean addDataAt(
            int position,
            T value
    ) {
        return mAssist.addDataAt(position, value);
    }

    @Override
    public boolean addDatas(Collection<T> collection) {
        return mAssist.addDatas(collection);
    }

    @Override
    public boolean addDatasAt(
            int position,
            Collection<T> collection
    ) {
        return mAssist.addDatasAt(position, collection);
    }

    @Override
    public boolean addDatasChecked(Collection<T> collection) {
        return mAssist.addDatasChecked(collection);
    }

    @Override
    public boolean addDatasCheckedAt(
            int position,
            Collection<T> collection
    ) {
        return mAssist.addDatasCheckedAt(position, collection);
    }

    @Override
    public boolean addLists(
            boolean append,
            Collection<T> collection
    ) {
        return mAssist.addLists(append, collection);
    }

    // =====
    // = 删 =
    // =====

    @Override
    public boolean removeData(T value) {
        return mAssist.removeData(value);
    }

    @Override
    public T removeDataAt(int position) {
        return mAssist.removeDataAt(position);
    }

    @Override
    public boolean removeDatas(Collection<T> collection) {
        return mAssist.removeDatas(collection);
    }

    // =====
    // = 改 =
    // =====

    @Override
    public boolean replaceData(
            T oldValue,
            T newValue
    ) {
        return mAssist.replaceData(oldValue, newValue);
    }

    @Override
    public boolean replaceDataAt(
            int position,
            T value
    ) {
        return mAssist.replaceDataAt(position, value);
    }

    @Override
    public boolean swipePosition(
            int fromPosition,
            int toPosition
    ) {
        return mAssist.swipePosition(fromPosition, toPosition);
    }

    @Override
    public boolean contains(T value) {
        return mAssist.contains(value);
    }

    @Override
    public void clearDataList() {
        mAssist.clearDataList();
    }

    @Override
    public void clearDataList(boolean notify) {
        mAssist.clearDataList(notify);
    }

    @Override
    public boolean setDataList(Collection<T> collection) {
        return mAssist.setDataList(collection);
    }

    @Override
    public boolean setDataList(
            Collection<T> collection,
            boolean notify
    ) {
        return mAssist.setDataList(collection, notify);
    }

    // ==========
    // = 通知方法 =
    // ==========

    @Override
    public void notifyDataChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void notifyElementChanged(T value) {
        notifyDataSetChanged();
    }
}