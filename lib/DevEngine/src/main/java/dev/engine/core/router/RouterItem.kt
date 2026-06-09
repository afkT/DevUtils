package dev.engine.core.router

import android.content.ClipData
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import dev.engine.router.IRouterEngine
import java.io.Serializable

/**
 * detail: Router Item Params
 * @author Ttt
 */
open class RouterItem private constructor(
    path: String?
) : IRouterEngine.EngineItem {

    // 路由 Path / Url
    private var mPath: String? = path

    // 跳转请求码
    private var mRequestCode = RouterConst.UNSET

    // 是否 pending
    private var mPending = false

    // Intent Flags
    private var mFlags = RouterConst.UNSET

    // 进入动画资源 id
    private var mAnimIn = RouterConst.UNSET

    // 退出动画资源 id
    private var mAnimOut = RouterConst.UNSET

    // 跳转 Context
    private var mContext: Context? = null

    // 跳转 Fragment
    private var mFragment: Fragment? = null

    // Activity Options Bundle
    private var mOptionsCompat: Bundle? = null

    // Intent Data
    private var mIntentData: Uri? = null

    // Intent Identifier
    private var mIntentIdentifier: String? = null

    // Intent ClipData
    private var mIntentClipData: ClipData? = null

    // 跳转结果回调
    private var mNavigationCallback: IRouterEngine.OnNavigationCallback? = null

    // 路由参数 Bundle
    private var mExtras: Bundle? = null

    // Object 类型参数
    private var mObjectParams: MutableMap<String, Any>? = null

    companion object {

        /**
         * 创建 Router Item
         * @return [RouterItem]
         */
        fun create(): RouterItem {
            return RouterItem(null)
        }

        /**
         * 创建 Router Item
         * @param path 路由 Path / Url
         * @return [RouterItem]
         */
        fun create(path: String?): RouterItem {
            return RouterItem(path)
        }

        /**
         * 创建 Router Item
         * @param item Router Item
         * @return [RouterItem]
         */
        fun create(item: RouterItem?): RouterItem {
            val result = RouterItem(item?.mPath)
            item ?: return result
            result.mRequestCode = item.mRequestCode
            result.mPending = item.mPending
            result.mFlags = item.mFlags
            result.mAnimIn = item.mAnimIn
            result.mAnimOut = item.mAnimOut
            result.mContext = item.mContext
            result.mFragment = item.mFragment
            result.mOptionsCompat = item.mOptionsCompat?.let { Bundle(it) }
            result.mIntentData = item.mIntentData
            result.mIntentIdentifier = item.mIntentIdentifier
            result.mIntentClipData = item.mIntentClipData
            result.mNavigationCallback = item.mNavigationCallback
            result.mExtras = item.mExtras?.let { Bundle(it) }
            result.mObjectParams = item.mObjectParams?.toMutableMap()
            return result
        }
    }

    // ===========
    // = get/set =
    // ===========

    override fun path(): String? {
        return mPath
    }

    open fun setPath(path: String?): RouterItem {
        mPath = path
        return this
    }

    override fun requestCode(): Int {
        return mRequestCode
    }

    open fun setRequestCode(requestCode: Int): RouterItem {
        mRequestCode = requestCode
        return this
    }

    override fun pending(): Boolean {
        return mPending
    }

    open fun setPending(pending: Boolean): RouterItem {
        mPending = pending
        return this
    }

    override fun flags(): Int {
        return mFlags
    }

    open fun setFlags(flags: Int): RouterItem {
        mFlags = flags
        return this
    }

    override fun animIn(): Int {
        return mAnimIn
    }

    open fun setAnimIn(animIn: Int): RouterItem {
        mAnimIn = animIn
        return this
    }

    override fun animOut(): Int {
        return mAnimOut
    }

    open fun setAnimOut(animOut: Int): RouterItem {
        mAnimOut = animOut
        return this
    }

    override fun context(): Any? {
        return mContext
    }

    open fun setContext(context: Context?): RouterItem {
        mContext = context
        return this
    }

    override fun fragment(): Any? {
        return mFragment
    }

    open fun setFragment(fragment: Fragment?): RouterItem {
        mFragment = fragment
        return this
    }

    override fun optionsCompat(): Any? {
        return mOptionsCompat
    }

    open fun setOptionsCompat(optionsCompat: Bundle?): RouterItem {
        mOptionsCompat = optionsCompat
        return this
    }

    override fun intentData(): Any? {
        return mIntentData
    }

    open fun setIntentData(intentData: Uri?): RouterItem {
        mIntentData = intentData
        return this
    }

    override fun intentIdentifier(): String? {
        return mIntentIdentifier
    }

    open fun setIntentIdentifier(intentIdentifier: String?): RouterItem {
        mIntentIdentifier = intentIdentifier
        return this
    }

    override fun intentClipData(): Any? {
        return mIntentClipData
    }

    open fun setIntentClipData(intentClipData: ClipData?): RouterItem {
        mIntentClipData = intentClipData
        return this
    }

    override fun navigationCallback(): IRouterEngine.OnNavigationCallback? {
        return mNavigationCallback
    }

    open fun setNavigationCallback(
        navigationCallback: IRouterEngine.OnNavigationCallback?
    ): RouterItem {
        mNavigationCallback = navigationCallback
        return this
    }

    override fun extras(): Bundle? {
        return mExtras
    }

    open fun setExtras(extras: Bundle?): RouterItem {
        mExtras = extras
        return this
    }

    override fun objectParams(): Map<String, Any>? {
        return mObjectParams
    }

    open fun setObjectParams(objectParams: Map<String, Any>?): RouterItem {
        mObjectParams = objectParams?.toMutableMap()
        return this
    }

    open fun putObjectParam(
        key: String,
        value: Any
    ): RouterItem {
        if (mObjectParams == null) {
            mObjectParams = LinkedHashMap()
        }
        mObjectParams?.put(key, value)
        return this
    }

    open fun putInt(
        key: String,
        value: Int
    ): RouterItem {
        ensureExtras().putInt(key, value)
        return this
    }

    open fun putLong(
        key: String,
        value: Long
    ): RouterItem {
        ensureExtras().putLong(key, value)
        return this
    }

    open fun putDouble(
        key: String,
        value: Double
    ): RouterItem {
        ensureExtras().putDouble(key, value)
        return this
    }

    open fun putFloat(
        key: String,
        value: Float
    ): RouterItem {
        ensureExtras().putFloat(key, value)
        return this
    }

    open fun putChar(
        key: String,
        value: Char
    ): RouterItem {
        ensureExtras().putChar(key, value)
        return this
    }

    open fun putByte(
        key: String,
        value: Byte
    ): RouterItem {
        ensureExtras().putByte(key, value)
        return this
    }

    open fun putBoolean(
        key: String,
        value: Boolean
    ): RouterItem {
        ensureExtras().putBoolean(key, value)
        return this
    }

    open fun putString(
        key: String,
        value: String?
    ): RouterItem {
        ensureExtras().putString(key, value)
        return this
    }

    open fun putSerializable(
        key: String,
        value: Serializable?
    ): RouterItem {
        ensureExtras().putSerializable(key, value)
        return this
    }

    open fun putParcelable(
        key: String,
        value: Parcelable?
    ): RouterItem {
        ensureExtras().putParcelable(key, value)
        return this
    }

    open fun putBundle(
        key: String,
        value: Bundle?
    ): RouterItem {
        ensureExtras().putBundle(key, value)
        return this
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 获取或创建 extras
     * @return [Bundle]
     */
    protected open fun ensureExtras(): Bundle {
        if (mExtras == null) {
            mExtras = Bundle()
        }
        return mExtras!!
    }
}