package afkt.project.model.basic

import android.content.Intent
import android.os.Bundle
import dev.base.DevIntent
import dev.simple.agile.intent.BaseIntent

private object IntentKey {

    // 通用 ID
    const val ID = "id"

    // 通用数据
    const val DATA = "data"

    // 通用类型
    const val TYPE = "type"

    // 通用标题
    const val TITLE = "title"
}

/**
 * detail: Intent 传参读写辅助类
 * @author Ttt
 * 在 [DevIntent] 基础上新增, 根据 IntentKey 自动生成通用方法
 * 通过 IntentDataKotlinGenerateMain 生成, 自行 copy 代码到该类
 */
class IntentData private constructor() : BaseIntent<IntentData>() {

    // ==========
    // = 静态方法 =
    // ==========

    companion object {

        /**
         * 创建 IntentData
         * @return [IntentData]
         */
        fun with(): IntentData {
            return IntentData()
        }

        /**
         * 创建 IntentData
         * @param intent [Intent]
         * @return [IntentData]
         */
        fun with(intent: Intent?): IntentData {
            return IntentData().reader(intent)
        }

        /**
         * 创建 IntentData
         * @param bundle [Bundle]
         * @return [IntentData]
         */
        fun with(bundle: Bundle?): IntentData {
            return IntentData().reader(bundle)
        }
    }

    // =============
    // = 对外公开方法 =
    // =============

    override fun returnT(): IntentData {
        return this
    }

    // ====================
    // = 以下属于自动生成代码 =
    // ====================

    // =

    /**
     * 获取 Key ( ID ) 对应的 Value
     * @return Value
     */
    fun getID(): String? {
        return get(IntentKey.ID)
    }

    /**
     * 设置 Key ( ID ) 对应的 Value
     * @param value 保存的 value
     * @return IntentData
     */
    fun setID(value: String?): IntentData {
        return put(IntentKey.ID, value)
    }

    /**
     * 移除 Key ( ID )
     * @return IntentData
     */
    fun removeID(): IntentData {
        return remove(IntentKey.ID)
    }

    /**
     * 是否存在 Key ( ID )
     * @return `true` yes, `false` no
     */
    fun containsID(): Boolean {
        return containsKey(IntentKey.ID)
    }

    /**
     * Key ( ID ) 保存的 Value 是否为 null
     * @return `true` yes, `false` no
     */
    fun isNullValueID(): Boolean {
        return isNullValue(IntentKey.ID)
    }

    // =

    /**
     * 获取 Key ( DATA ) 对应的 Value
     * @return Value
     */
    fun getData(): String? {
        return get(IntentKey.DATA)
    }

    /**
     * 设置 Key ( DATA ) 对应的 Value
     * @param value 保存的 value
     * @return IntentData
     */
    fun setData(value: String?): IntentData {
        return put(IntentKey.DATA, value)
    }

    /**
     * 移除 Key ( DATA )
     * @return IntentData
     */
    fun removeData(): IntentData {
        return remove(IntentKey.DATA)
    }

    /**
     * 是否存在 Key ( DATA )
     * @return `true` yes, `false` no
     */
    fun containsData(): Boolean {
        return containsKey(IntentKey.DATA)
    }

    /**
     * Key ( DATA ) 保存的 Value 是否为 null
     * @return `true` yes, `false` no
     */
    fun isNullValueData(): Boolean {
        return isNullValue(IntentKey.DATA)
    }

    // =

    /**
     * 获取 Key ( TITLE ) 对应的 Value
     * @return Value
     */
    fun getTitle(): String? {
        return get(IntentKey.TITLE)
    }

    /**
     * 设置 Key ( TITLE ) 对应的 Value
     * @param value 保存的 value
     * @return IntentData
     */
    fun setTitle(value: String?): IntentData {
        return put(IntentKey.TITLE, value)
    }

    /**
     * 移除 Key ( TITLE )
     * @return IntentData
     */
    fun removeTitle(): IntentData {
        return remove(IntentKey.TITLE)
    }

    /**
     * 是否存在 Key ( TITLE )
     * @return `true` yes, `false` no
     */
    fun containsTitle(): Boolean {
        return containsKey(IntentKey.TITLE)
    }

    /**
     * Key ( TITLE ) 保存的 Value 是否为 null
     * @return `true` yes, `false` no
     */
    fun isNullValueTitle(): Boolean {
        return isNullValue(IntentKey.TITLE)
    }

    // =

    /**
     * 获取 Key ( TYPE ) 对应的 Value
     * @return Value
     */
    fun getType(): String? {
        return get(IntentKey.TYPE)
    }

    /**
     * 设置 Key ( TYPE ) 对应的 Value
     * @param value 保存的 value
     * @return IntentData
     */
    fun setType(value: String?): IntentData {
        return put(IntentKey.TYPE, value)
    }

    /**
     * 移除 Key ( TYPE )
     * @return IntentData
     */
    fun removeType(): IntentData {
        return remove(IntentKey.TYPE)
    }

    /**
     * 是否存在 Key ( TYPE )
     * @return `true` yes, `false` no
     */
    fun containsType(): Boolean {
        return containsKey(IntentKey.TYPE)
    }

    /**
     * Key ( TYPE ) 保存的 Value 是否为 null
     * @return `true` yes, `false` no
     */
    fun isNullValueType(): Boolean {
        return isNullValue(IntentKey.TYPE)
    }
}