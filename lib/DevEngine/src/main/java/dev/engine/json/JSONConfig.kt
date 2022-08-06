package dev.engine.json

import com.google.gson.Gson

/**
 * detail: JSON Config
 * @author Ttt
 */
open class JSONConfig : IJSONEngine.EngineConfig() {
    @JvmField
    var gson: Gson? = null
}