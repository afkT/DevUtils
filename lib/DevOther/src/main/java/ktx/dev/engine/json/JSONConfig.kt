package ktx.dev.engine.json

import com.google.gson.Gson
import dev.engine.json.IJSONEngine

/**
 * detail: JSON Config
 * @author Ttt
 */
class JSONConfig : IJSONEngine.EngineConfig() {
    @JvmField
    var gson: Gson? = null
}