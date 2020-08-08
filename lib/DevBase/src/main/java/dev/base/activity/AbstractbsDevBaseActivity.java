package dev.base.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import dev.base.able.IDevBaseContent;
import dev.base.able.IDevBaseMethod;
import dev.base.able.IDevBaseUIOperation;
import dev.utils.LogPrintUtils;
import dev.utils.app.ActivityUtils;
import dev.utils.app.DialogUtils;
import dev.utils.app.toast.ToastUtils;

/**
 * detail: Activity 抽象基类
 * @author Ttt
 */
abstract class AbstractbsDevBaseActivity extends AppCompatActivity implements IDevBaseMethod, IDevBaseUIOperation, IDevBaseContent {

    // ==========
    // = Object =
    // ==========

    // 日志 TAG
    protected String mTag = AbstractbsDevBaseActivity.class.getSimpleName();

    // Context
    protected Context  mContext           = null;
    // Activity
    protected Activity mActivity          = null;
    // Content View
    protected View     mContentView       = null;
    // 当前页面是否可见 ( 生命周期 )
    protected boolean  mIsActivityVisible = true;

    // ==============
    // = UI Operate =
    // ==============

    // 基类 Dialog
    protected Dialog         mDialog;
    // 基类 DialogFragment
    protected DialogFragment mDialogFragment;
    // 基类 PopupWindow
    protected PopupWindow    mPopupWindow;

    // ============
    // = 生命周期 =
    // ============

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printLogPri("onCreate");
        // 标记属于可见
        mIsActivityVisible = true;
        // 获取当前类名
        mTag = this.getClass().getSimpleName();
        // 获取 Context、Activity
        mContext = mActivity = this;
        // 保存 Activity
        ActivityUtils.getManager().addActivity(this);
        // Content View 初始化处理
        layoutInit();
        // 判断是否为 null
        if (mContentView != null) setContentView(mContentView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        printLogPri("onStart");
        // 标记属于可见
        mIsActivityVisible = true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        printLogPri("onRestart");
        // 标记属于可见
        mIsActivityVisible = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        printLogPri("onResume");
        // 标记属于可见
        mIsActivityVisible = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        printLogPri("onPause");
        // 标记属于不可见
        mIsActivityVisible = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        printLogPri("onStop");
        // 标记属于不可见
        mIsActivityVisible = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        printLogPri("onDestroy");
        // 标记属于不可见
        mIsActivityVisible = false;
        // 移除当前 Activity
        ActivityUtils.getManager().removeActivity(this);
    }

    /**
     * 返回键点击触发
     * <pre>
     *     需要注意的是, 重新实现该方法必须保留 super.onBackPressed();
     * </pre>
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        printLogPri("onBackPressed");
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

    // ===================
    // = IDevBaseContent =
    // ===================

    /**
     * 布局初始化处理
     */
    private void layoutInit() {
        if (mContentView != null) return;
        // 使用 contentId()
        if (contentId() != 0) {
            try {
                mContentView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(contentId(), null);
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
     *     initView() => initValue() => initListener() => initOther()
     * </pre>
     */
    @Override
    public void initOrder() {
        initView();
        initValue();
        initListener();
        initOther();
    }

    // ==============
    // = 初始化方法 =
    // ==============

    /**
     * 初始化 View
     */
    @Override
    public void initView() {
        printLogPri("initView");
    }

    /**
     * 初始化参数、配置
     */
    @Override
    public void initValue() {
        printLogPri("initValue");
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
        printLogPri("initListener");
    }

    /**
     * 初始化其他操作
     */
    @Override
    public void initOther() {
        printLogPri("initOther");
    }

    // =======================
    // = IDevBaseUIOperation =
    // =======================

    // =========
    // = Toast =
    // =========

    /**
     * 显示 Toast
     * @param text       Toast 提示文本
     * @param formatArgs 格式化参数
     */
    @Override
    public void showToast(String text, Object... formatArgs) {
        ToastUtils.showShort(mContext, text, formatArgs);
    }

    /**
     * 显示 Toast
     * @param resId      R.string.id
     * @param formatArgs 格式化参数
     */
    @Override
    public void showToast(int resId, Object... formatArgs) {
        ToastUtils.showShort(mContext, resId, formatArgs);
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
     * 判断 Activity 是否可见状态
     * @return {@code true} yes, {@code false} no
     */
    protected final boolean isActivityVisible() {
        return mIsActivityVisible;
    }
}