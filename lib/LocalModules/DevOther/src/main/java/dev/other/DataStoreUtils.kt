package dev.other

import android.content.Context
import android.text.TextUtils
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.Preferences.Key
import androidx.datastore.preferences.preferencesDataStoreFile
import dev.DevUtils
import dev.utils.DevFinal
import dev.utils.LogPrintUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * detail: DataStore 工具类
 * @author Ttt
 * Google 再见 SharedPreferences 拥抱 Jetpack DataStore
 * @see https://juejin.im/post/6881442312560803853
 * Google 再见 SharedPreferences 拥抱 Jetpack DataStore ( 二 )
 * @see https://juejin.im/post/6888847647802097672
 * <p></p>
 * DataStore 文件存储目录: /data/data/<包名>/files/datastore
 * 仅支持 Int、String、Boolean、Float、Long、Double
 * 具体查看 [androidx.datastore.preferences.core.PreferencesKeys]
 * <p></p>
 * 注意事项: 限制同一个 name 只能创建一次 DataStore 并存储对象进行存储复用
 * 多次创建 SingleProcessDataStore 会抛出
 * There are multiple DataStores active for the same file: $file.
 * You should either maintain your DataStore as a singleton
 * or confirm that there is no two DataStore's active on the same file
 * (by confirming that the scope  is cancelled)
 */
object DataStoreUtils {

    // 日志 TAG
    private val TAG: String = DataStoreUtils::class.java.simpleName

    // Map
    private val cacheMap = HashMap<String, InnerDataStore>()

    // 默认值
    const val INT_VALUE: Int = DevFinal.DEFAULT.INT
    const val LONG_VALUE: Long = DevFinal.DEFAULT.LONG
    const val FLOAT_VALUE: Float = DevFinal.DEFAULT.FLOAT
    const val DOUBLE_VALUE: Double = DevFinal.DEFAULT.DOUBLE
    const val BOOLEAN_VALUE: Boolean = DevFinal.DEFAULT.BOOLEAN
    const val STRING_VALUE: String = DevFinal.DEFAULT.STRING

    /**
     * 获取 DataStore 操作类
     * @param storeName DataStore Name
     * @return [InnerDataStore]
     */
    fun get(storeName: String?): InnerDataStore {
        val key = if (TextUtils.isEmpty(storeName)) TAG else storeName!!
        var value = cacheMap[key]
        if (value != null) return value
        value = InnerDataStore(key)
        cacheMap[key] = value
        return value
    }

    /**
     * 获取 DataStore 操作类
     * @param dataStore [DataStore]
     * @return [InnerDataStore]
     */
    fun get(dataStore: DataStore<Preferences>?): InnerDataStore {
        return InnerDataStore(dataStore)
    }

    /**
     * SharedPreferences 迁移到 DataStore
     * @param storeName DataStore Name
     * @param spNames SharedPreferences Name Array
     * @return [InnerDataStore]
     * <p></p>
     * 进行迁移前, 不能使用该 storeName 进行创建 DataStore
     * 具体看顶部注意事项
     */
    @Throws(Exception::class)
    fun migrationSPToDataStore(
        storeName: String,
        vararg spNames: String
    ): InnerDataStore {
        if (spNames.isEmpty()) throw Exception("spNames size is zero")

        val context = getContext()
        val lists = ArrayList<DataMigration<Preferences>>()
        for (name in spNames) {
            if (!TextUtils.isEmpty(name)) {
                lists.add(SharedPreferencesMigration(context, name))
            }
        }
        // 传入 migrations 参数, 构建一个 DataStore 之后
        // 需要执行一次读或写, DataStore 才会自动合并 SharedPreference 文件内容
        val dataStore = PreferenceDataStoreFactory.create(
            migrations = lists
        ) {
            getContext().preferencesDataStoreFile(storeName)
        }
        val value = InnerDataStore(dataStore)
        cacheMap[storeName] = value
        return value
    }

    /**
     * 移除 InnerDataStore 缓存
     * @param key storeName
     * @return `true` success, `false` fail
     */
    fun removeCache(key: String?): Boolean {
        if (cacheMap.containsKey(key)) {
            cacheMap.remove(key)
            return true
        }
        return false
    }

    /**
     * 清空 InnerDataStore 缓存
     */
    fun clearCache() {
        cacheMap.clear()
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 获取全局 Context
     * @return Context
     */
    private fun getContext(): Context {
        return DevUtils.getContext()
    }

    // =========
    // = 内部类 =
    // =========

    /**
     * detail: DataStore 内部操作类
     * @author Ttt
     */
    class InnerDataStore private constructor() {

        private var dataStore: DataStore<Preferences>? = null

        constructor(storeName: String) : this() {
            this.dataStore = PreferenceDataStoreFactory.create {
                getContext().preferencesDataStoreFile(storeName)
            }
        }

        constructor(dataStore: DataStore<Preferences>?) : this() {
            this.dataStore = dataStore
        }

        // ==========
        // = 内部方法 =
        // ==========

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        private suspend fun <T> innerPut(
            key: Key<T>,
            value: T
        ) {
            dataStore?.edit { mutablePreferences ->
                mutablePreferences[key] = value
            }
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @param defaultValue 不存在 key 返回默认 Value
         * @return [Flow]
         */
        private fun <T> innerGetFlow(
            key: Key<T>,
            defaultValue: T
        ): Flow<T>? {
            return dataStore?.data?.catch {
                LogPrintUtils.eTag(TAG, it, key.name)
                // 当读取数据遇到错误时, 如果是 IOException 异常, 发送一个 emptyPreferences, 来重新使用
                // 但是如果是其他的异常, 最好将它抛出去, 不要隐藏问题
                if (it is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }?.map { preferences -> preferences[key] ?: defaultValue }
        }

        /**
         * 获取值
         * @param key [Preferences.Key]
         * @param defaultValue 不存在 key 返回默认 Value
         * @return Value
         */
        private suspend fun <T> innerGetValue(
            key: Key<T>,
            defaultValue: T
        ): T {
            return innerGetFlow(key, defaultValue)?.first()!!
        }

        // =============
        // = 对外公开方法 =
        // =============

        /**
         * 获取 DataStore
         * @return [DataStore]
         */
        fun getDataStore(): DataStore<Preferences>? {
            return dataStore
        }

        // =======
        // = 存储 =
        // =======

        /**
         * 保存数据
         * @param key Key
         * @param value Value
         */
        suspend fun put(
            key: String,
            value: Int
        ) {
            innerPut(key = intPreferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(
            key: Key<Int>,
            value: Int
        ) {
            innerPut(key = key, value = value)
        }

        /**
         * 保存数据
         * @param key Key
         * @param value Value
         */
        suspend fun put(
            key: String,
            value: String
        ) {
            innerPut(key = stringPreferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(
            key: Key<String>,
            value: String
        ) {
            innerPut(key = key, value = value)
        }

        /**
         * 保存数据
         * @param key Key
         * @param value Value
         */
        suspend fun put(
            key: String,
            value: Boolean
        ) {
            innerPut(key = booleanPreferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(
            key: Key<Boolean>,
            value: Boolean
        ) {
            innerPut(key = key, value = value)
        }

        /**
         * 保存数据
         * @param key Key
         * @param value Value
         */
        suspend fun put(
            key: String,
            value: Float
        ) {
            innerPut(key = floatPreferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(
            key: Key<Float>,
            value: Float
        ) {
            innerPut(key = key, value = value)
        }

        /**
         * 保存数据
         * @param key Key
         * @param value Value
         */
        suspend fun put(
            key: String,
            value: Long
        ) {
            innerPut(key = longPreferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(
            key: Key<Long>,
            value: Long
        ) {
            innerPut(key = key, value = value)
        }

        /**
         * 保存数据
         * @param key Key
         * @param value Value
         */
        suspend fun put(
            key: String,
            value: Double
        ) {
            innerPut(key = doublePreferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(
            key: Key<Double>,
            value: Double
        ) {
            innerPut(key = key, value = value)
        }

        // ========
        // = Flow =
        // ========

        /**
         * 获取数据
         * @param key Key
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getIntFlow(
            key: String,
            defaultValue: Int = INT_VALUE
        ): Flow<Int>? {
            return innerGetFlow(key = intPreferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getIntFlow(
            key: Key<Int>,
            defaultValue: Int = INT_VALUE
        ): Flow<Int>? {
            return innerGetFlow(key = key, defaultValue = defaultValue)
        }

        /**
         * 获取数据
         * @param key Key
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getStringFlow(
            key: String,
            defaultValue: String = STRING_VALUE
        ): Flow<String>? {
            return innerGetFlow(key = stringPreferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getStringFlow(
            key: Key<String>,
            defaultValue: String = STRING_VALUE
        ): Flow<String>? {
            return innerGetFlow(key = key, defaultValue = defaultValue)
        }

        /**
         * 获取数据
         * @param key Key
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getBooleanFlow(
            key: String,
            defaultValue: Boolean = BOOLEAN_VALUE
        ): Flow<Boolean>? {
            return innerGetFlow(key = booleanPreferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getBooleanFlow(
            key: Key<Boolean>,
            defaultValue: Boolean = BOOLEAN_VALUE
        ): Flow<Boolean>? {
            return innerGetFlow(key = key, defaultValue = defaultValue)
        }

        /**
         * 获取数据
         * @param key Key
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getFloatFlow(
            key: String,
            defaultValue: Float = FLOAT_VALUE
        ): Flow<Float>? {
            return innerGetFlow(key = floatPreferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getFloatFlow(
            key: Key<Float>,
            defaultValue: Float = FLOAT_VALUE
        ): Flow<Float>? {
            return innerGetFlow(key = key, defaultValue = defaultValue)
        }

        /**
         * 获取数据
         * @param key Key
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getLongFlow(
            key: String,
            defaultValue: Long = LONG_VALUE
        ): Flow<Long>? {
            return innerGetFlow(key = longPreferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getLongFlow(
            key: Key<Long>,
            defaultValue: Long = LONG_VALUE
        ): Flow<Long>? {
            return innerGetFlow(key = key, defaultValue = defaultValue)
        }

        /**
         * 获取数据
         * @param key Key
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getDoubleFlow(
            key: String,
            defaultValue: Double = DOUBLE_VALUE
        ): Flow<Double>? {
            return innerGetFlow(key = doublePreferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getDoubleFlow(
            key: Key<Double>,
            defaultValue: Double = DOUBLE_VALUE
        ): Flow<Double>? {
            return innerGetFlow(key = key, defaultValue = defaultValue)
        }

        // =========
        // = Value =
        // =========

        /**
         * 获取值
         * @param key Key
         * @param defaultValue 默认 Value
         * @return Value
         */
        suspend fun getInt(
            key: String,
            defaultValue: Int = INT_VALUE
        ): Int {
            return innerGetValue(key = intPreferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取值
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return Value
         */
        suspend fun getInt(
            key: Key<Int>,
            defaultValue: Int = INT_VALUE
        ): Int {
            return innerGetValue(key = key, defaultValue = defaultValue)
        }

        /**
         * 获取值
         * @param key Key
         * @param defaultValue 默认 Value
         * @return Value
         */
        suspend fun getString(
            key: String,
            defaultValue: String = STRING_VALUE
        ): String {
            return innerGetValue(key = stringPreferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取值
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return Value
         */
        suspend fun getString(
            key: Key<String>,
            defaultValue: String = STRING_VALUE
        ): String {
            return innerGetValue(key = key, defaultValue = defaultValue)
        }

        /**
         * 获取值
         * @param key Key
         * @param defaultValue 默认 Value
         * @return Value
         */
        suspend fun getBoolean(
            key: String,
            defaultValue: Boolean = BOOLEAN_VALUE
        ): Boolean {
            return innerGetValue(key = booleanPreferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取值
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return Value
         */
        suspend fun getBoolean(
            key: Key<Boolean>,
            defaultValue: Boolean = BOOLEAN_VALUE
        ): Boolean {
            return innerGetValue(key = key, defaultValue = defaultValue)
        }

        /**
         * 获取值
         * @param key Key
         * @param defaultValue 默认 Value
         * @return Value
         */
        suspend fun getFloat(
            key: String,
            defaultValue: Float = FLOAT_VALUE
        ): Float {
            return innerGetValue(key = floatPreferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取值
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return Value
         */
        suspend fun getFloat(
            key: Key<Float>,
            defaultValue: Float = FLOAT_VALUE
        ): Float {
            return innerGetValue(key = key, defaultValue = defaultValue)
        }

        /**
         * 获取值
         * @param key Key
         * @param defaultValue 默认 Value
         * @return Value
         */
        suspend fun getLong(
            key: String,
            defaultValue: Long = LONG_VALUE
        ): Long {
            return innerGetValue(key = longPreferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取值
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return Value
         */
        suspend fun getLong(
            key: Key<Long>,
            defaultValue: Long = LONG_VALUE
        ): Long {
            return innerGetValue(key = key, defaultValue = defaultValue)
        }

        /**
         * 获取值
         * @param key Key
         * @param defaultValue 默认 Value
         * @return Value
         */
        suspend fun getDouble(
            key: String,
            defaultValue: Double = DOUBLE_VALUE
        ): Double {
            return innerGetValue(key = doublePreferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取值
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return Value
         */
        suspend fun getDouble(
            key: Key<Double>,
            defaultValue: Double = DOUBLE_VALUE
        ): Double {
            return innerGetValue(key = key, defaultValue = defaultValue)
        }
    }
}