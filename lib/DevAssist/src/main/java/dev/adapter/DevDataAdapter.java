package dev.adapter;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dev.assist.DataAssist;
import dev.base.data.DataChanged;
import dev.base.data.DataManager;

/**
 * detail: DataManager RecyclerView Adapter
 * @author Ttt
 */
public abstract class DevDataAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH>
        implements DataManager<T>,
        DataChanged<T> {

    private DataAssist<T> mAssist = new DataAssist<>(this);

    // ========================
    // = RecyclerView.Adapter =
    // ========================

    @Override
    public int getItemCount() {
        return getDataSize();
    }

    // ===============
    // = DataManager =
    // ===============

    // ===========
    // = 获取相关 =
    // ===========

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

    // ===========
    // = 快速判断 =
    // ===========

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

    // ======
    // = 增 =
    // ======

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

    // ======
    // = 删 =
    // ======

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

    // ======
    // = 改 =
    // ======

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
    public void setDataList(List<T> lists) {
        mAssist.setDataList(lists);
    }

    @Override
    public void setDataList(
            List<T> lists,
            boolean notify
    ) {
        mAssist.setDataList(lists, notify);
    }

    // ===========
    // = 通知方法 =
    // ===========

    @Override
    public void notifyDataChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void notifyElementChanged(T value) {
        notifyDataSetChanged();
    }
}