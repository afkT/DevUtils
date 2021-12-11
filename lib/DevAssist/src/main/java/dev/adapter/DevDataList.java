package dev.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dev.assist.DataAssist;
import dev.assist.EditTextWatcherAssist;
import dev.base.DevObject;
import dev.base.DevPage;
import dev.base.data.DataChanged;
import dev.base.data.DataManager;
import dev.base.multiselect.DevMultiSelectMap;
import dev.base.state.CommonState;
import dev.base.state.RequestState;
import dev.callback.DevCallback;
import dev.callback.DevItemClickCallback;

/**
 * detail: DataManager List
 * @author Ttt
 * <pre>
 *     适用于不方便继承 {@link DevDataAdapter} 及扩展类, 但提供相同功能 DataManager
 * </pre>
 */
public abstract class DevDataList<T>
        implements DataManager<T>,
        DataChanged<T> {

    // 数据辅助类
    protected DataAssist<T>                mAssist            = new DataAssist<>(this);
    // Context
    protected Context                      mContext;
    // Activity
    protected Activity                     mActivity;
    // 通用回调
    protected DevCallback<T>               mCallback;
    // 通用 Item Click 回调
    protected DevItemClickCallback<T>      mItemCallback;
    // 通用 Object
    protected DevObject<T>                 mObject            = new DevObject<>();
    // Page 实体类
    protected DevPage<T>                   mPage              = DevPage.getDefault();
    // 通用状态
    protected CommonState<T>               mState             = new CommonState<>();
    // 请求状态
    protected RequestState<T>              mRequestState      = new RequestState<>();
    // EditText 输入监听辅助类
    protected EditTextWatcherAssist<T>     mTextWatcherAssist = new EditTextWatcherAssist<>();
    // 多选辅助类
    protected DevMultiSelectMap<String, T> mMultiSelectMap    = new DevMultiSelectMap<>();

    public DevDataList() {
    }

    public DevDataList(final DevPage.PageConfig pageConfig) {
        setPage(pageConfig);
    }

    public DevDataList(
            final int page,
            final int pageSize
    ) {
        setPage(page, pageSize);
    }

    public DevDataList(final DevPage<T> page) {
        setPage(page);
    }

    // ==================
    // = DevDataAdapter =
    // ==================

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
     * @return {@link DevDataList}
     */
    public DevDataList<T> setContext(final Context context) {
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
     * @return {@link DevDataList}
     */
    public DevDataList<T> setActivity(Activity activity) {
        this.mActivity = activity;
        return this;
    }

    /**
     * 通过 ViewGroup 设置 Context
     * <pre>
     *     在 {@link androidx.recyclerview.widget.RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)} 中调用, 传入 ViewGroup
     * </pre>
     * @param parent {@link ViewGroup}
     * @return {@link DevDataList}
     */
    public DevDataList<T> parentContext(ViewGroup parent) {
        if (parent != null && mContext == null) {
            this.mContext = parent.getContext();
        }
        return this;
    }

    // ========================
    // = RecyclerView.Adapter =
    // ========================

    public int getItemCount() {
        return getDataSize();
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

    // =====================
    // = DevDataAdapterExt =
    // =====================

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
     * @return {@link DevDataList}
     */
    public DevDataList<T> setCallback(final DevCallback<T> callback) {
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
     * @return {@link DevDataList}
     */
    public DevDataList<T> setItemCallback(final DevItemClickCallback<T> itemCallback) {
        this.mItemCallback = itemCallback;
        return this;
    }

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
     * @return {@link DevDataList}
     */
    public DevDataList<T> setObject(final DevObject<T> object) {
        this.mObject = object;
        return this;
    }

    /**
     * 获取 Page 实体类
     * @return {@link DevPage}
     */
    public DevPage<T> getPage() {
        return mPage;
    }

    /**
     * 设置 Page 实体类
     * @param pageConfig 页数配置信息
     * @return {@link DevDataList}
     */
    public DevDataList<T> setPage(final DevPage.PageConfig pageConfig) {
        return setPage(new DevPage<>(pageConfig));
    }

    /**
     * 设置 Page 实体类
     * @param page     页数
     * @param pageSize 每页请求条数
     * @return {@link DevDataList}
     */
    public DevDataList<T> setPage(
            final int page,
            final int pageSize
    ) {
        return setPage(new DevPage<>(page, pageSize));
    }

    /**
     * 设置 Page 实体类
     * @param page Page 实体类
     * @return {@link DevDataList}
     */
    public DevDataList<T> setPage(final DevPage<T> page) {
        this.mPage = page;
        return this;
    }

    /**
     * 通用状态实体类
     * @return {@link CommonState}
     */
    public CommonState<T> getState() {
        return mState;
    }

    /**
     * 设置通用状态实体类
     * @param state {@link CommonState}
     * @return {@link DevDataList}
     */
    public DevDataList<T> setState(final CommonState<T> state) {
        this.mState = state;
        return this;
    }

    /**
     * 请求状态实体类
     * @return {@link RequestState}
     */
    public RequestState<T> getRequestState() {
        return mRequestState;
    }

    /**
     * 设置请求状态实体类
     * @param requestState {@link RequestState}
     * @return {@link DevDataList}
     */
    public DevDataList<T> setRequestState(final RequestState<T> requestState) {
        this.mRequestState = requestState;
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
     * @return {@link DevDataList}
     */
    public DevDataList<T> setTextWatcherAssist(final EditTextWatcherAssist<T> textWatcherAssist) {
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
     * @return {@link DevDataList}
     */
    public DevDataList<T> setMultiSelectMap(final DevMultiSelectMap<String, T> multiSelectMap) {
        this.mMultiSelectMap = multiSelectMap;
        return this;
    }
}