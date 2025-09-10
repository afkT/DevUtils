package dev.engine.core.json

import com.google.gson.Gson
import dev.engine.json.IJSONEngine

/**
 * detail: JSON Config
 * @author Ttt
 */
open class JSONConfig : IJSONEngine.EngineConfig {
    @JvmField
    var gson: Gson? = null
}