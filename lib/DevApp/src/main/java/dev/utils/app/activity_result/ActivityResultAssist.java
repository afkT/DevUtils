package dev.utils.app.activity_result;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.LifecycleOwner;

import dev.utils.app.ActivityResultUtils;

/**
 * detail: Activity Result 封装辅助类
 * @author Ttt
 * <pre>
 *     Activity Result API
 *     @see <a href="https://developer.android.google.cn/training/basics/intents/result"/>
 *     <p></p>
 *     封装 {@link ActivityResultUtils} 无需考虑异常崩溃等各种情况
 *     注意事项:
 *     虽然在 fragment 或 activity 创建完毕之前可安全地调用 registerForActivityResult()
 *     但在 fragment 或 activity 的 Lifecycle 变为 CREATED 状态之前, 您无法启动 ActivityResultLauncher
 *     并且必须在 STARTED 前调用 registerForActivityResult 可查看下方 register 方法 throw IllegalStateException
 *     {@link androidx.activity.result.ActivityResultRegistry#register}
 *     Activity Result API 有三个重要的类:
 *     ActivityResultContract: 协议, 这是一个抽象类, 定义如何传递数据和如何接收数据
 *     ActivityResultLauncher: 启动器, 相当于以前的 startActivityForResult()
 *     ActivityResultCallback: 结果回调, 相当于以前的 onActivityResult()
 *     <p></p>
 *     系统内置常用 ActivityResultContract 具体点击 ActivityResultContracts 进行查看
 *     {@link ActivityResultContracts.StartActivityForResult} 通用 Contract
 *     {@link ActivityResultContracts.RequestMultiplePermissions} 申请一组权限
 *     {@link ActivityResultContracts.RequestPermission} 申请单个权限
 *     {@link ActivityResultContracts.TakePicturePreview} 拍照 ( 返回 Bitmap )
 *     {@link ActivityResultContracts.TakePicture} 拍照 ( 保存指定 Uri 地址, 返回 true 表示保存成功 )
 *     {@link ActivityResultContracts.CaptureVideo} 拍视频 ( 保存指定 Uri 地址, 返回 true 表示保存成功 )
 *     {@link ActivityResultContracts.PickContact} 从通讯录获取联系人
 *     {@link ActivityResultContracts.CreateDocument} 选择一个文档 ( 返回 Uri )
 *     {@link ActivityResultContracts.OpenDocumentTree} 选择一个目录 ( 返回 Uri )
 *     {@link ActivityResultContracts.OpenMultipleDocuments} 选择多个文档 ( 返回多个 Uri )
 *     {@link ActivityResultContracts.GetContent} 选择一条内容 ( 返回 Uri )
 * </pre>
 */
public final class ActivityResultAssist<I, O>
        extends ActivityResultLauncher<I> {

    // 跳转回传值启动器
    private ActivityResultLauncher<I> mLauncher;
    // 操作回调
    private OperateCallback<I>        mCallback;

    public static final int LAUNCH         = 1;
    public static final int LAUNCH_OPTIONS = 2;
    public static final int UNREGISTER     = 3;

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 判断启动器是否为 null
     * @return {@code true} yes, {@code false} no
     */
    public boolean isLauncherEmpty() {
        return mLauncher == null;
    }

    /**
     * 判断启动器是否不为 null
     * @return {@code true} yes, {@code false} no
     */
    public boolean isLauncherNotEmpty() {
        return mLauncher != null;
    }

    /**
     * 设置操作回调
     * @param callback OperateCallback
     * @return ActivityResultAssist
     */
    public ActivityResultAssist<I, O> setOperateCallback(final OperateCallback<I> callback) {
        this.mCallback = callback;
        return this;
    }

    // ==========================
    // = ActivityResultLauncher =
    // ==========================

    @Override
    public void launch(final I input) {
        if (mCallback != null) {
            mCallback.onStart(LAUNCH, input, null);
        }
        boolean result = ActivityResultUtils.launch(mLauncher, input);
        if (mCallback != null) {
            mCallback.onState(LAUNCH, input, null, result);
        }
    }

    @Override
    public void launch(
            final I input,
            final ActivityOptionsCompat options
    ) {
        if (mCallback != null) {
            mCallback.onStart(LAUNCH_OPTIONS, input, options);
        }
        boolean result = ActivityResultUtils.launch(mLauncher, input, options);
        if (mCallback != null) {
            mCallback.onState(LAUNCH_OPTIONS, input, options, result);
        }
    }

    @Override
    public void unregister() {
        if (mCallback != null) {
            mCallback.onStart(UNREGISTER, null, null);
        }
        boolean result = ActivityResultUtils.unregister(mLauncher);
        if (mCallback != null) {
            mCallback.onState(UNREGISTER, null, null, result);
        }
        // 注销后调用 launch 将会失效
        mLauncher = null;
    }

    @NonNull
    @Override
    public ActivityResultContract<I, ?> getContract() {
        return ActivityResultUtils.getContract(mLauncher);
    }

    // ====================
    // = Operate Callback =
    // ====================

    /**
     * detail: 操作回调
     * @author Ttt
     */
    public static abstract class OperateCallback<I> {

        /**
         * 操作前回调
         * @param type    类型
         * @param input   输入参数
         * @param options Activity 启动选项
         */
        public void onStart(
                final int type,
                final I input,
                final ActivityOptionsCompat options
        ) {
        }

        /**
         * 操作状态回调
         * @param type    类型
         * @param input   输入参数
         * @param options Activity 启动选项
         * @param result  操作结果
         */
        public abstract void onState(
                final int type,
                final I input,
                final ActivityOptionsCompat options,
                final boolean result
        );
    }

    // ==========
    // = 构造函数 =
    // ==========

    // ========================
    // = ActivityResultCaller =
    // ========================

    /**
     * 注册创建跳转回传值启动器并返回
     * @param caller   ActivityResultCaller ( 只要属于继承 Fragment、FragmentActivity 传入 this 即可 )
     * @param contract ActivityResultContract
     * @param callback ActivityResultCallback 回传回调
     */
    public ActivityResultAssist(
            final ActivityResultCaller caller,
            final ActivityResultContract<I, O> contract,
            final ActivityResultCallback<O> callback
    ) {
        mLauncher = ActivityResultUtils.registerForActivityResult(
                caller, contract, callback
        );
    }

    /**
     * 注册创建跳转回传值启动器并返回
     * @param caller   ActivityResultCaller ( 只要属于继承 Fragment、FragmentActivity 传入 this 即可 )
     * @param contract ActivityResultContract
     * @param registry ActivityResultRegistry
     * @param callback ActivityResultCallback 回传回调
     */
    public ActivityResultAssist(
            final ActivityResultCaller caller,
            final ActivityResultContract<I, O> contract,
            final ActivityResultRegistry registry,
            final ActivityResultCallback<O> callback
    ) {
        mLauncher = ActivityResultUtils.registerForActivityResult(
                caller, contract, registry, callback
        );
    }

    // ==========================
    // = ActivityResultRegistry =
    // ==========================

    /**
     * 注册创建跳转回传值启动器并返回
     * <pre>
     *     ActivityResultRegistry ( 可通过 ComponentActivity、Fragment getActivityResultRegistry() 获取 )
     * </pre>
     * @param registry       ActivityResultRegistry
     * @param key            唯一值字符串
     * @param lifecycleOwner 生命周期监听
     * @param contract       ActivityResultContract
     * @param callback       ActivityResultCallback 回传回调
     */
    public ActivityResultAssist(
            final ActivityResultRegistry registry,
            final String key,
            final LifecycleOwner lifecycleOwner,
            final ActivityResultContract<I, O> contract,
            final ActivityResultCallback<O> callback
    ) {
        mLauncher = ActivityResultUtils.register(
                registry, key, lifecycleOwner, contract, callback
        );
    }

    /**
     * 注册创建跳转回传值启动器并返回
     * <pre>
     *     ActivityResultRegistry ( 可通过 ComponentActivity、Fragment getActivityResultRegistry() 获取 )
     * </pre>
     * @param registry ActivityResultRegistry
     * @param key      唯一值字符串
     * @param contract ActivityResultContract
     * @param callback ActivityResultCallback 回传回调
     */
    public ActivityResultAssist(
            final ActivityResultRegistry registry,
            final String key,
            final ActivityResultContract<I, O> contract,
            final ActivityResultCallback<O> callback
    ) {
        mLauncher = ActivityResultUtils.register(
                registry, key, contract, callback
        );
    }
}