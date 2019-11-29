package dev.base.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import dev.base.able.IDevBaseMethod;
import dev.base.able.IDevBaseUIOperation;
import dev.base.able.IDevBaseViewOperation;
import dev.utils.LogPrintUtils;
import dev.utils.app.DialogUtils;
import dev.utils.app.toast.ToastUtils;

/**
 * detail: Fragment 抽象基类
 * @author Ttt
 */
abstract class AbstractDevBaseFragment extends Fragment implements IDevBaseMethod, IDevBaseUIOperation, IDevBaseViewOperation {

    // ==========
    // = Object =
    // ==========

    // 日志 TAG
    protected String mTag = AbstractDevBaseFragment.class.getSimpleName();
    // Context
    protected Context mContext = null;
    // Fragment View
    protected View mContentView = null;
    // 当前 Fragment 是否可见 ( 生命周期 )
    protected boolean mIsFragmentVisible = true;

    // ==============
    // = UI Operate =
    // ==============

    // 基类 Dialog
    protected Dialog mDialog;
    // 基类 DialogFragment
    protected DialogFragment mDialogFragment;
    // 基类 PopupWindow
    protected PopupWindow mPopupWindow;

    // ============
    // = 生命周期 =
    // ============

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        printLogPri("onAttach");
        // 保存 Context
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        printLogPri("onDetach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printLogPri("onCreate");
        // 获取当前类名
        mTag = this.getClass().getSimpleName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        printLogPri("onCreateView");
        if (mContentView != null) {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            // 删除以及在显示的 View 防止切回来不加载一片空白
            if (parent != null) parent.removeView(mContentView);
            return mContentView;
        }
        // View 初始化处理
        layoutInit(inflater, container);
        // 获取 Context
        mContext = mContentView.getContext();
        // 触发初始化方法
        onInit(mContentView, container, savedInstanceState);
        return mContentView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        printLogPri("onHiddenChanged - hidden: " + hidden);
        // 设置 Fragment 可见状态
        mIsFragmentVisible = !hidden;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        printLogPri("setUserVisibleHint");
        // 设置 Fragment 可见状态
        mIsFragmentVisible = getUserVisibleHint();
    }

    @Override
    public void onStart() {
        super.onStart();
        printLogPri("onStart");
        // 标记属于可见
        mIsFragmentVisible = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        printLogPri("onResume");
        // 标记属于可见
        mIsFragmentVisible = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        printLogPri("onPause");
        // 标记属于不可见
        mIsFragmentVisible = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        printLogPri("onStop");
        // 标记属于不可见
        mIsFragmentVisible = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        printLogPri("onDestroy");
        // 标记属于不可见
        mIsFragmentVisible = false;
    }

    // ============
    // = 日志处理 =
    // ============

    /**
     * 统一打印日志 ( 内部封装调用 )
     * @param message 打印内容
     */
    private final void printLogPri(String message) {
        printLogPri(mTag, message);
    }

    /**
     * 统一打印日志 ( 内部封装调用 )
     * @param tag     日志 TAG
     * @param message 打印内容
     */
    private final void printLogPri(String tag, String message) {
        LogPrintUtils.dTag(mTag, String.format("%s -> %s", new Object[]{tag, message}));
    }

    // =========================
    // = IDevBaseViewOperation =
    // =========================

    @Override
    public View getContentView() {
        return mContentView;
    }

    @Override
    public abstract int contentId();

    @Override
    public abstract View contentView();

    /**
     * 布局初始化处理
     * @param inflater  {@link LayoutInflater}
     * @param container {@link ViewGroup}
     */
    private void layoutInit(LayoutInflater inflater, ViewGroup container) {
        if (mContentView != null) return;
        // 使用 contentId()
        if (contentId() != 0) {
            try {
                mContentView = inflater.inflate(contentId(), container, false);
            } catch (Exception e) {
                LogPrintUtils.eTag(mTag, e, "layoutInit - contentId");
            }
        }
        // 如果 View 等于 null, 则使用 contentView()
        if (mContentView == null) mContentView = contentView();
    }

    // ==================
    // = IDevBaseMethod =
    // ==================

    /**
     * 初始化方法顺序
     * <pre>
     *     initViews() => initValues() => initListeners() => initOtherOperate()
     * </pre>
     */
    @Override
    public void initMethodOrder() {
        initViews();
        initValues();
        initListeners();
        initOtherOperate();
    }

    // ==============
    // = 初始化方法 =
    // ==============

    /**
     * 初始化 Views
     */
    @Override
    public void initViews() {
        printLogPri("initViews");
    }

    /**
     * 初始化全部参数、配置
     */
    @Override
    public void initValues() {
        printLogPri("initValues");
    }

    /**
     * 初始化绑定事件
     */
    @Override
    public void initListeners() {
        printLogPri("initListeners");
    }

    /**
     * 初始化其他操作
     */
    @Override
    public void initOtherOperate() {
        printLogPri("initOtherOperate");
    }

    // =======================
    // = IDevBaseUIOperation =
    // =======================

    // =========
    // = Toast =
    // =========

    /**
     * 显示 Toast
     * @param text Toast 提示文本
     * @param objs 格式化参数
     */
    @Override
    public void showToast(String text, Object... objs) {
        ToastUtils.showShort(mContext, text, objs);
    }

    /**
     * 显示 Toast
     * @param resId R.string.id
     * @param objs  格式化参数
     */
    @Override
    public void showToast(int resId, Object... objs) {
        ToastUtils.showShort(mContext, resId, objs);
    }

    // ===============
    // = PopupWindow =
    // ===============

    /**
     * 获取 PopupWindow
     * @return {@link PopupWindow}
     */
    @Override
    public PopupWindow getPopupWindow() {
        return mPopupWindow;
    }

    /**
     * 设置 PopupWindow
     * @param popupWindow {@link PopupWindow}
     * @return {@link PopupWindow}
     */
    @Override
    public <T extends PopupWindow> T setPopupWindow(T popupWindow) {
        return setPopupWindow(true, popupWindow);
    }

    /**
     * 设置 PopupWindow
     * @param isClose     是否关闭之前的 PopupWindow
     * @param popupWindow {@link PopupWindow}
     * @return {@link PopupWindow}
     */
    @Override
    public <T extends PopupWindow> T setPopupWindow(boolean isClose, T popupWindow) {
        if (isClose) DialogUtils.closePopupWindow(mPopupWindow);
        this.mPopupWindow = popupWindow;
        return popupWindow;
    }

    // ==========
    // = Dialog =
    // ==========

    /**
     * 获取 Dialog
     * @return {@link Dialog}
     */
    @Override
    public Dialog getDialog() {
        return mDialog;
    }

    /**
     * 设置 Dialog
     * @param dialog {@link Dialog}
     * @return {@link Dialog}
     */
    @Override
    public <T extends Dialog> T setDialog(T dialog) {
        return setDialog(true, dialog);
    }

    /**
     * 设置 Dialog
     * @param isClose 是否关闭之前的 Dialog
     * @param dialog  {@link Dialog}
     * @return {@link Dialog}
     */
    @Override
    public <T extends Dialog> T setDialog(boolean isClose, T dialog) {
        if (isClose) DialogUtils.closeDialog(dialog);
        this.mDialog = dialog;
        return dialog;
    }

    // ==================
    // = DialogFragment =
    // ==================

    /**
     * 获取 DialogFragment
     * @return {@link DialogFragment}
     */
    @Override
    public DialogFragment getDialogFragment() {
        return mDialogFragment;
    }

    /**
     * 设置 DialogFragment
     * @param dialog {@link DialogFragment}
     * @return {@link DialogFragment}
     */
    @Override
    public <T extends DialogFragment> T setDialogFragment(T dialog) {
        return setDialogFragment(true, dialog);
    }

    /**
     * 设置 DialogFragment
     * @param isClose 是否关闭之前的 DialogFragment
     * @param dialog  {@link DialogFragment}
     * @return {@link DialogFragment}
     */
    @Override
    public <T extends DialogFragment> T setDialogFragment(boolean isClose, T dialog) {
        if (isClose && mDialogFragment != null) {
            try {
                mDialogFragment.dismiss();
            } catch (Exception e) {
            }
        }
        this.mDialogFragment = dialog;
        return dialog;
    }

    // ================
    // = 对外提供方法 =
    // ================

    /**
     * 设置 Fragment 可见状态状态
     * @return {@code true} yes, {@code false} no
     */
    protected final boolean isFragmentVisible() {
        return mIsFragmentVisible;
    }

    /**
     * onCreateView 初始化触发方法
     * <pre>
     *     需在此方法中调用 initMethodOrder
     * </pre>
     * @param view               {@link View}
     * @param container          {@link ViewGroup}
     * @param savedInstanceState {@link Bundle}
     */
    protected abstract void onInit(View view, ViewGroup container, Bundle savedInstanceState);
}