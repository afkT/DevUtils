package dev.sku

import android.text.TextUtils
import dev.base.multiselect.DevMultiSelectMap
import dev.utils.DevFinal

/**
 * detail: SKU 封装类
 * @author Ttt
 */
class SKU {

    /**
     * 数据集基本模型
     * @property stock 库存
     * @property price 价格
     * @property value 原始数据
     */
    class Model<T>(
        val stock: Int,
        val price: Double,
        val value: T? = null
    ) {
        // 库存集合 ( 所有组合库存集, 可进行排序获取最大最小库存 )
        val stockList: MutableList<Int> = mutableListOf()

        // 价格集合 ( 所有组合价格集, 可进行排序获取最高最低价格 )
        val priceList: MutableList<Double> = mutableListOf()
    }

    /**
     * 属性
     * @property id id
     * @property name 属性名
     * @property attrList 属性值列表
     */
    class Attr(
        val id: Int,
        val name: String?,
        val attrList: List<AttrValue>
    )

    /**
     * 属性值
     * @property id id
     * @property value 属性值
     * @property state 属性值状态
     */
    class AttrValue(
        val id: Int,
        val value: String?,
        private var state: State = State.NOT_OPTIONAL
    ) {

        /**
         * 是否可选 ( 可点击 )
         * @return `true` yes, `false` no
         */
        fun isOptional(): Boolean {
            return state == State.OPTIONAL
        }

        /**
         * 是否不可选 ( 不可点击 )
         * @return `true` yes, `false` no
         */
        fun isNotOptional(): Boolean {
            return state == State.NOT_OPTIONAL
        }

        // =

        /**
         * 设置可选 ( 可点击 )
         * @return `true` yes, `false` no
         */
        fun setOptional(): AttrValue {
            state = State.OPTIONAL
            return this
        }

        /**
         * 设置不可选 ( 不可点击 )
         * @return `true` yes, `false` no
         */
        fun setNotOptional(): AttrValue {
            state = State.NOT_OPTIONAL
            return this
        }
    }

    /**
     * 属性值状态
     * @property desc 状态描述
     */
    enum class State(
        val desc: String
    ) {

        OPTIONAL("可选 ( 可点击 )"),

        NOT_OPTIONAL("不可选 ( 不可点击 )"),
    }
}

/**
 * detail: SKU 数据处理包装 ( 对外公开快捷使用 )
 * @author Ttt
 */
class SKUData<T> {

    // SKU 控制器 ( 内部数据处理 )
    private val mSKUController = SKUController<T>()

    // 选中的属性值 ( key = SKU.Attr.id, value = SKU.AttrValue )
    private val mSelect = DevMultiSelectMap<Int, SKU.AttrValue>()

    /**
     * 初始化方法
     * @param attrs 属性集
     * @param skuModels SKU 数据集
     * @return SKUData<T>
     */
    fun initialize(
        attrs: List<SKU.Attr>,
        skuModels: Map<List<Int>, SKU.Model<T>>
    ): SKUData<T> {
        mSelect.clearSelects()
        mSKUController.initialize(attrs, skuModels)
        return this
    }

    /**
     * 自动选中属性
     * @param attrIds 待选中属性值 id
     * @return SKUData<T>
     */
    fun autoSelectAttr(attrIds: List<Int>): SKUData<T> {
        mSelect.clearSelects()
        mSKUController.mOriginal.forEach { attr ->
            attr.attrList.forEach VALUE_FOR@{ attrValue ->
                // 如果集合中包含对应属性值 id 则表示选中该属性
                if (attrIds.contains(attrValue.id)) {
                    mSelect.select(attr.id, attrValue)
                    return@VALUE_FOR
                }
            }
        }
        return this
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 是否选择属性
     * @return `true` yes, `false` no
     */
    fun isSelectAttr(): Boolean {
        return mSelect.isSelect
    }

    /**
     * 是否全选每个属性 ( 每个属性的属性值集合都选中 )
     * @return `true` yes, `false` no
     */
    fun isAllSelectAttr(): Boolean {
        val size = mSelect.selectSize
        return size != 0 && size == mSKUController.mOriginal.size
    }

    // ==========
    // = 多选相关 =
    // ==========

    /**
     * 获取属性值多选辅助类
     * @return DevMultiSelectMap<Int, SKU.AttrValue>
     */
    fun getSelect(): DevMultiSelectMap<Int, SKU.AttrValue> {
        return mSelect
    }

    /**
     * 设置非选中操作
     * @param attrId SKU.Attr.id
     * @return SKUData<T>
     */
    fun unselect(attrId: Int): SKUData<T> {
        mSelect.unselect(attrId)
        return this
    }

    /**
     * 设置选中操作
     * @param attrId SKU.Attr.id
     * @param attrValue SKU.AttrValue
     * @return SKUData<T>
     */
    fun select(
        attrId: Int,
        attrValue: SKU.AttrValue
    ): SKUData<T> {
        mSelect.select(attrId, attrValue)
        return this
    }

    /**
     * 是否选中对应属性
     * @param attrId SKU.Attr.id
     * @param attrValue SKU.AttrValue
     * @return `true` yes, `false` no
     */
    fun isSelect(
        attrId: Int,
        attrValue: SKU.AttrValue
    ): Boolean {
        return mSelect.getSelectValue(attrId)?.id == attrValue.id
    }

    // =============
    // = 属性状态相关 =
    // =============

    /**
     * 刷新状态数据
     * @return List<SKU.Attr>
     */
    fun refreshStateData(): List<SKU.Attr> {
        val adapterData = mutableListOf<SKU.Attr>()
        adapterData.addAll(mSKUController.mOriginal)

        val hasSelectAttr = isSelectAttr()
        // 循环所有属性集
        adapterData.forEach { attr ->
            attr.attrList.forEach { attrValue ->
                attrValue.setNotOptional()
                mSKUController.mSKUModelAll["${attrValue.id}"]?.apply {
                    if (stock > 0) {
                        attrValue.setOptional()
                    }
                }

                // =============
                // = 校验已选属性 =
                // =============

                if (hasSelectAttr) {
                    val cacheSelect = DevMultiSelectMap<Int, SKU.AttrValue>()
                    cacheSelect.putSelects(mSelect.selects)
                    cacheSelect.unselect(attr.id)
                    cacheSelect.select(attr.id, attrValue)

                    // 获取选中属性对应的数据集基本模型
                    getModel(cacheSelect.selectValues)?.apply {
                        if (stock > 0) {
                            attrValue.setOptional()
                        } else {
                            attrValue.setNotOptional()
                        }
                    }
                }
            }
        }
        // 重置数据
        return mSKUController.mStateData.apply {
            clear()
            addAll(adapterData)
        }
    }

    /**
     * 获取选中属性对应的数据集基本模型
     * @return SKU.Model
     */
    fun getModel(): SKU.Model<T>? {
        return getModel(mSelect.selectValues)
    }

    /**
     * 获取选中属性对应的数据集基本模型 ( 内部复用 )
     * @param value List<AttrValue>
     * @return SKU.Model
     */
    private fun getModel(value: List<SKU.AttrValue>): SKU.Model<T>? {
        val attrIds = toAttrIds(value)
        val key = SKUUtils.joinKeySort(attrIds)
        return mSKUController.mSKUModelAll[key]
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 转换属性值 id List
     * @param value List<AttrValue>
     * @return List<Int>
     */
    private fun toAttrIds(value: List<SKU.AttrValue>): List<Int> {
        val list = mutableListOf<Int>()
        value.forEach {
            list.add(it.id)
        }
        return list
    }
}

/**
 * detail: SKU 控制器 ( 内部数据处理 )
 * @author Ttt
 */
internal class SKUController<T> {

    // 动态状态数据 ( 直接用于 Adapter 显示 )
    val mStateData: MutableList<SKU.Attr> = mutableListOf()

    // 原始属性集 ( 初始化传入 )
    val mOriginal: MutableList<SKU.Attr> = mutableListOf()

    // SKU 数据集基本模型 ( key = List<SKU.AttrValue.id>.join, value = SKU.Model )
    val mSKUModel: MutableMap<String, SKU.Model<T>> = mutableMapOf()

    // SKU 数据集基本模型全部组合数 ( key = List<SKU.AttrValue.id>.join, value = SKU.Model )
    val mSKUModelAll: MutableMap<String, SKU.Model<T>> = mutableMapOf()

    /**
     * 初始化方法
     * @param attrs 属性集
     * @param skuModels SKU 数据集
     * @return SKUController<T>
     */
    fun initialize(
        attrs: List<SKU.Attr>,
        skuModels: Map<List<Int>, SKU.Model<T>>
    ): SKUController<T> {
        mOriginal.apply {
            clear()
            addAll(attrs)
        }
        mSKUModel.apply {
            clear()
            skuModels.iterator().forEach {
                // 为防止 Key Ids 顺序不同先进行排序
                val key = SKUUtils.joinKeySort(it.key)
                put(key, it.value)
            }
        }
        mSKUModelAll.apply {
            clear()
            // 补全 SKU 数据集基本模型 ( 防止返回非完全的属性集合 )
            putAll(complementSKUModel(mOriginal, mSKUModel))
            // 组合 SKU 数据集基本模型 ( 全部组合数添加 )
            putAll(combineSKUModel())
        }
        return this
    }

    // ==========================
    // = SKU 数据集基本模型补全处理 =
    // ==========================

    /**
     * 补全 SKU 数据集基本模型 ( 防止返回非完全的属性集合 )
     * @param attrs 原始属性集 ( 初始化传入 )
     * @param skuModels SKU 数据集基本模型
     * @return LinkedHashMap<String, SKU.Model<T>>
     */
    private fun <T> complementSKUModel(
        attrs: List<SKU.Attr>,
        skuModels: Map<String, SKU.Model<T>>
    ): MutableMap<String, SKU.Model<T>> {
        // SKU 数据集基本模型全部组合数 ( key = List<SKU.AttrValue.id>.join, value = SKU.Model )
        val skuModelAll: MutableMap<String, SKU.Model<T>> = mutableMapOf()
        val count = attrs.size
        attrs.firstOrNull()?.attrList?.forEach {
            forSKUAttr(1, count, mutableListOf(it.id), attrs, skuModels, skuModelAll)
        }
        return skuModelAll
    }

    /**
     * 循环 SKU 属性 ( 递归方法 )
     * @param index 属性层级
     * @param count 属性总层数
     * @param attrIds 每层累加属性值 id
     * @param attrs 原始属性集 ( 初始化传入 )
     * @param skuModels SKU 数据集基本模型
     * @param skuModelAll SKU 数据集基本模型全部组合数
     */
    private fun <T> forSKUAttr(
        index: Int,
        count: Int,
        attrIds: List<Int>,
        attrs: List<SKU.Attr>,
        skuModels: Map<String, SKU.Model<T>>,
        skuModelAll: MutableMap<String, SKU.Model<T>>
    ) {
        if (index >= count) {
            val key = SKUUtils.joinKeySort(attrIds)
            val value = skuModels[key]
            if (value != null) {
                skuModelAll[key] = value
            } else {
                skuModelAll[key] = SKU.Model(0, 0.0)
            }
        } else {
            attrs[index].attrList.forEach {
                forSKUAttr(
                    index + 1, count,
                    attrIds.plus(it.id),
                    attrs, skuModels, skuModelAll
                )
            }
        }
    }

    // ==========
    // = 组合处理 =
    // ==========

    /**
     * 组合 SKU 数据集基本模型 ( 全部组合数添加 )
     */
    private fun combineSKUModel(): MutableMap<String, SKU.Model<T>> {
        // SKU 数据集基本模型全部组合数
        val maps = mSKUModelAll.toMutableMap()
        mSKUModelAll.keys.forEach { key ->
            mSKUModelAll[key]?.let { value ->
                // 拆分 SKU.AttrValue.id
                val attrIds = SKUUtils.splitKey(key)
                // 获取所有组合
                val combineIds = SKUUtils.arrayCombine(attrIds)
                combineIds.forEach { ids ->
                    add2SKUResult(ids, value, maps)
                }
            }
        }
        return maps
    }

    /**
     * 添加到数据集合
     * @param keys id 组合
     * @param model SKU 数据集基本模型
     * @param maps SKU 数据集基本模型全部组合数
     */
    private fun add2SKUResult(
        keys: ArrayList<String>,
        model: SKU.Model<T>,
        maps: MutableMap<String, SKU.Model<T>>
    ) {
        val key = SKUUtils.joinKeyByString(keys)
        val value = maps[key]
        if (value != null) {
            value.stockList.add(model.stock)
            value.priceList.add(model.price)
        } else {
            maps[key] = model
        }
    }
}

/**
 * detail: SKU 快捷工具类
 * @author Ttt
 * 电商平台商品 SKU 组合查询算法实现
 * @see https://hooray.github.io/posts/8b2bd6f8
 * SKU 算法探讨 ( Android 版 )
 * @see https://www.jianshu.com/p/45c7d9dfe5fc
 * SKU 选择解决方案
 * @see https://juejin.cn/post/6915321356540198926
 * Java 计算组合数以及生成组合排列
 * @see https://blog.csdn.net/haiyoushui123456/article/details/84338494
 */
internal object SKUUtils {

    /**
     * 拼接 Key
     * @param attrIds List<SKU.AttrValue.id>
     * @return String
     */
    private fun joinKey(attrIds: List<Int>): String {
        return TextUtils.join(DevFinal.SYMBOL.SEMICOLON, attrIds)
    }

    /**
     * 拼接 Key ( 进行排序 )
     * @param attrIds List<SKU.AttrValue.id>
     * @return String
     */
    fun joinKeySort(attrIds: List<Int>): String {
        return joinKey(attrIds.sorted())
    }

    /**
     * 拼接 Key
     * @param attrIds List<SKU.AttrValue.id>
     * @return String
     */
    fun joinKeyByString(attrIds: List<String>): String {
        return TextUtils.join(DevFinal.SYMBOL.SEMICOLON, attrIds)
    }

    /**
     * 拆分 Key
     * @param key 待拆分 Key
     * @return Array<String>
     */
    fun splitKey(key: String): Array<String> {
        return key.split(DevFinal.SYMBOL.SEMICOLON.toRegex()).toTypedArray()
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 获取所有的组合放到 ArrayList 里面
     * @param attrIds List<SKU.AttrValue.id>
     * @return ArrayList
     */
    fun arrayCombine(attrIds: Array<String>?): ArrayList<ArrayList<String>> {
        attrIds?.let {
            val len = it.size
            val result = ArrayList<ArrayList<String>>()
            for (n in 1 until len) {
                val flags: ArrayList<Array<Int>> = getCombineFlags(len, n)
                for (i in flags.indices) {
                    val flag = flags[i]
                    val combine = ArrayList<String>()
                    for (j in flag.indices) {
                        if (flag[j] == 1) {
                            combine.add(it[j])
                        }
                    }
                    result.add(combine)
                }
            }
            return result
        }
        return ArrayList()
    }

    /**
     * 从 len 中取 number 的所有组合
     * @param len 元素总数
     * @param number 需要组合数
     * @return ArrayList
     */
    private fun getCombineFlags(
        len: Int,
        number: Int
    ): ArrayList<Array<Int>> {
        if (number <= 0) return ArrayList()
        val result = ArrayList<Array<Int>>()
        val flag = arrayOfNulls<Int>(len)
        var hasNext = true
        var cnt: Int // 计数
        // 初始化
        for (i in 0 until len) {
            flag[i] = if (i < number) 1 else 0
        }
        result.cloneAdd(flag.clone())
        while (hasNext) {
            cnt = 0
            for (i in 0 until len - 1) {
                if (flag[i] == 1 && flag[i + 1] == 0) {
                    for (j in 0 until i) {
                        flag[j] = if (j < cnt) 1 else 0
                    }
                    flag[i] = 0
                    flag[i + 1] = 1
                    val temp = flag.clone()
                    result.cloneAdd(temp)
                    if (
                        !TextUtils.join("", temp)
                            .substring(len - number)
                            .contains("0")
                    ) {
                        hasNext = false
                    }
                    break
                }
                if (flag[i] == 1) {
                    cnt++
                }
            }
        }
        return result
    }

    /**
     * 添加克隆数组
     * @param element Array<Int?>
     */
    private fun ArrayList<Array<Int>>.cloneAdd(element: Array<Int?>) {
        try {
            add(element.requireNoNulls())
        } catch (e: Exception) {
        }
    }
}