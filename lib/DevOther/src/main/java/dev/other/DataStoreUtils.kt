package dev.other

import android.content.Context
import android.text.TextUtils
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.Preferences.Key
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore

/**
 * detail: DataStore 工具类
 * @author Ttt
 * [Google] 再见 SharedPreferences 拥抱 Jetpack DataStore
 * @see <a href="https://juejin.im/post/6881442312560803853"/>
 * [Google] 再见 SharedPreferences 拥抱 Jetpack DataStore ( 二 )
 * @see <a href="https://juejin.im/post/6888847647802097672"/>
 * <p></p>
 * DataStore 文件存放目录: /data/data/<包名>/files/datastore
 * 仅支持 Int、String、Boolean、Float、Long、Double
 * 具体查看 [androidx.datastore.preferences.core.preferencesKey]
 */
object DataStoreUtils {

    // 日志 TAG
    val TAG = DataStoreUtils::class.java.simpleName

    // Map
    private val cacheMap = HashMap<String, InnerDataStore>()

    /**
     * 获取 DataStore 操作类
     * @param context [Context]
     * @param storeName DataStore Name
     * @return [InnerDataStore]
     */
    fun get(context: Context, storeName: String?): InnerDataStore {
        var key = if (TextUtils.isEmpty(storeName)) TAG else storeName!!
        var value = cacheMap[key]
        if (value != null) return value
        value = InnerDataStore(context, key)
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
     * @param context [Context]
     * @param storeName DataStore Name
     * @param spNames SharedPreferences Name Array
     * @return [InnerDataStore]
     */
    @Throws(Exception::class)
    fun migrationSPToDataStore(
        context: Context,
        storeName: String,
        vararg spNames: String
    ): InnerDataStore {
        if (spNames.isEmpty()) throw Exception("spNames size is zero")

        var lists = ArrayList<DataMigration<Preferences>>()
        for (name in spNames) {
            if (!TextUtils.isEmpty(name)) {
                lists.add(SharedPreferencesMigration(context, name))
            }
        }
        // 传入 migrations 参数, 构建一个 DataStore 之后
        // 需要执行一次读或写, DataStore 才会自动合并 SharedPreference 文件内容
        var dataStore = context.createDataStore(
            name = storeName,
            migrations = lists
        )
        return InnerDataStore(dataStore)
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

        constructor(context: Context, storeName: String) : this() {
            this.dataStore = context.createDataStore(
                name = storeName
            )
        }

        constructor(dataStore: DataStore<Preferences>?) : this() {
            this.dataStore = dataStore
        }

        // ===============
        // = 对外公开方法 =
        // ===============

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
        suspend fun put(key: String, value: Int) {
            _put(key = preferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(key: Key<Int>, value: Int) {
            _put(key = key, value = value)
        }

        /**
         * 保存数据
         * @param key Key
         * @param value Value
         */
        suspend fun put(key: String, value: String) {
            _put(key = preferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(key: Key<String>, value: String) {
            _put(key = key, value = value)
        }

        /**
         * 保存数据
         * @param key Key
         * @param value Value
         */
        suspend fun put(key: String, value: Boolean) {
            _put(key = preferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(key: Key<Boolean>, value: Boolean) {
            _put(key = key, value = value)
        }

        /**
         * 保存数据
         * @param key Key
         * @param value Value
         */
        suspend fun put(key: String, value: Float) {
            _put(key = preferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(key: Key<Float>, value: Float) {
            _put(key = key, value = value)
        }

        /**
         * 保存数据
         * @param key Key
         * @param value Value
         */
        suspend fun put(key: String, value: Long) {
            _put(key = preferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(key: Key<Long>, value: Long) {
            _put(key = key, value = value)
        }

        /**
         * 保存数据
         * @param key Key
         * @param value Value
         */
        suspend fun put(key: String, value: Double) {
            _put(key = preferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(key: Key<Double>, value: Double) {
            _put(key = key, value = value)
        }

        // ===========
        // = 内部方法 =
        // ===========

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        private suspend fun <T> _put(key: Key<T>, value: T) {
            dataStore?.edit { mutablePreferences ->
                mutablePreferences[key] = value
            }
        }
    }
}